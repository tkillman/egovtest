package egovframework.com.cpa.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;
import org.terracotta.agent.repkg.de.schlichtherle.io.FileInputStream;

import egovframework.com.cmm.EgovWebUtil;
import egovframework.com.cpa.model.CpaExcelMappingEnum;
import egovframework.com.cpa.model.CpaVO;
import egovframework.com.cpa.service.CpaService;
import egovframework.com.utl.fcc.service.EgovFormBasedFileVo;
import egovframework.rte.fdl.excel.EgovExcelService;
import egovframework.rte.fdl.property.EgovPropertyService;

@Service("cpaService")
public class CpaServiceImpl implements CpaService {
	
	private static final Logger Logger = LoggerFactory.getLogger(CpaServiceImpl.class);
	
	public static final String SEPERATOR = File.separator;
	
    @Resource(name = "excelCpaService")
    private EgovExcelService excelCpaService;
	
    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
    
    private final String errorField = "uploadFile";
	
    
    @Override
	public void validate(CpaVO cpaVo, Errors errors){
		
		MultipartFile uploadFile = cpaVo.getUploadFile();		
		Workbook wbT = null;
				
		//excelFile인지 확인
		if (!(uploadFile.getOriginalFilename().endsWith(".xls") || uploadFile.getOriginalFilename().endsWith(".XLS")
				|| uploadFile.getOriginalFilename().endsWith(".xlsx") || uploadFile.getOriginalFilename().endsWith(".XLSX"))) {
			errors.rejectValue(errorField, "uploadFile.notExcel");
			return;
		}
		
		try {
			
			if (uploadFile.getOriginalFilename().endsWith(".xls") || uploadFile.getOriginalFilename().endsWith(".XLS")) {
				wbT = excelCpaService.loadWorkbook(cpaVo.getUploadFile().getInputStream());
			} else if (uploadFile.getOriginalFilename().endsWith(".xlsx") || uploadFile.getOriginalFilename().endsWith(".XLSX")) {
				wbT = excelCpaService.loadWorkbook(cpaVo.getUploadFile().getInputStream(), new XSSFWorkbook());
			}

			Sheet sheet = wbT.getSheetAt(0);
			Logger.debug("sheet.getLastRowNum() ::" + sheet.getLastRowNum());
			int excelStartRowNum = sheet.getLastRowNum() == 0 ? 0 : 1;
			IntStream.range(excelStartRowNum, sheet.getLastRowNum() + 1)
						.forEach(i -> {
							Row row = sheet.getRow(i);
							Logger.debug("CpaExcelMappingEnum.values().length :" + CpaExcelMappingEnum.values().length);
							IntStream.range(0, CpaExcelMappingEnum.values().length)
										.forEach(j -> {
											Cell cell = row.getCell(j);
											if (cell == null) {
												Integer[] excelRowAndColIndexInfo = {i + 1, j + 1};
												errors.rejectValue(errorField,"cpa.uploadFile.cell.empty", excelRowAndColIndexInfo,"");
											} else {
												validateCellTypeAndValue(cell, errors);
											}
											
										});
						});
		} catch (Exception e) {
			errors.rejectValue(errorField, "uploadFile.notKnowError");
			e.printStackTrace();
		}
	}
	
	//cell 유효성 체크
	public void validateCellTypeAndValue(Cell cell, Errors errors) {
		int colIndex = cell.getColumnIndex();
		if (colIndex == CpaExcelMappingEnum.GOUBUN.getExcelNum()) { //교번
			validateGouBunCellTypeAndValue(cell, errors);	
		} else if (colIndex == CpaExcelMappingEnum.NAME.getExcelNum()) { //이름
			validateNameCellTypeAndValue(cell, errors);
		} else if (colIndex == CpaExcelMappingEnum.ADDR.getExcelNum()) { //주소
			validateAddrCellTypeAndValue(cell, errors);
		}
	}
	
	public void validateGouBunCellTypeAndValue(Cell cell, Errors errors) {
		if (!(cell.getCellType() == 0)) { //숫자형식이 아닌경우
 			errors.rejectValue(errorField, "cpa.uploadFile.cell.notNumeric", getRowAndColIndexByCell(cell), "");
		}
	}
	
	public void validateNameCellTypeAndValue(Cell cell, Errors errors) {
		
		if (!(cell.getCellType() == 1)) { //문자형식이 아닌경우
			errors.rejectValue(errorField, "cpa.uploadFile.cell.notString", getRowAndColIndexByCell(cell), "");
		} else if(StringUtils.isEmpty(cell.getStringCellValue())){ //숫자형식이지만 값이 없는 경우	
			errors.rejectValue(errorField, "cpa.uploadFile.cell.empty", getRowAndColIndexByCell(cell), "");
		}
	}
	
	public void validateAddrCellTypeAndValue(Cell cell, Errors errors) {
		if (!(cell.getCellType() == 1)) { //문자형식이 아닌경우
 			errors.rejectValue(errorField, "cpa.uploadFile.cell.notString", getRowAndColIndexByCell(cell), "");
		} else if(!Optional.ofNullable(cell.getStringCellValue()).isPresent()){ //숫자형식이지만 값이 없는 경우	
			errors.rejectValue(errorField, "cpa.uploadFile.cell.empty", getRowAndColIndexByCell(cell), "");
		}
	}
	
	public Integer[] getRowAndColIndexByCell(Cell cell) {
		Integer[] excelRowAndColIndexInfo = new Integer[2];
		excelRowAndColIndexInfo[0] = cell.getRowIndex() + 1;
		excelRowAndColIndexInfo[1] = cell.getColumnIndex() + 1;
		return excelRowAndColIndexInfo;
	}

	@Override
	public void excelDbInsert(EgovFormBasedFileVo vo){
		try {
			Workbook wb = null;
			String where = propertiesService.getString("cpaExcelUploadFilePath"); 
			File file = new File(EgovWebUtil.filePathBlackList(where) + SEPERATOR + vo.getServerSubPath() + SEPERATOR + vo.getPhysicalName());
			
	    	if (vo.getFileName().endsWith(".xls") || vo.getFileName().endsWith(".XLS")) {
				wb = excelCpaService.loadWorkbook(new FileInputStream(file));
				Sheet sheet = wb.getSheetAt(0);
				excelCpaService.uploadExcel("CpaDAO.insertStudent", sheet, 1, 5000);
	    	} else if (vo.getFileName().endsWith(".xlsx") || vo.getFileName().endsWith(".XLSX")) {
				excelCpaService.uploadExcel("CpaDAO.insertStudent"
											,new FileInputStream(file)
											, (short)0
											, 1
											, (long)5000
											, new XSSFWorkbook());
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
