package egovframework.com.cpa.web;

import java.io.File;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.terracotta.agent.repkg.de.schlichtherle.io.FileInputStream;

import egovframework.com.cmm.EgovWebUtil;
import egovframework.com.utl.fcc.service.EgovFormBasedFileVo;
import egovframework.rte.fdl.excel.EgovExcelService;
import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class CpaExcelUploadController {
	
	private static final Logger Logger = LoggerFactory.getLogger(CpaExcelUploadController.class);
	
	public static final String SEPERATOR = File.separator;
	
    @Resource(name = "excelCpaService")
    private EgovExcelService excelCpaService;

	/** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
    
	@RequestMapping(value = "/cpa/upload/excelUpload.do", method = RequestMethod.POST)
	public void excelUpload(@ModelAttribute("EgovFormBasedFileVo") EgovFormBasedFileVo vo) {
		
		Logger.debug("/cpa/upload/excelUpload.do exec");
		
		if (vo == null) {
			return;
		}
		
		Logger.debug("egovFormBasedFileVo ::" + vo.toString());
		Logger.debug("egovFormBasedFileVo ::" + vo.getPhysicalName());
		
		try {
			Workbook wb = null;
			String where = propertiesService.getString("cpaExcelUploadFilePath"); 
			File file = new File(EgovWebUtil.filePathBlackList(where) + SEPERATOR + vo.getServerSubPath() + SEPERATOR + vo.getPhysicalName());
			
			if (vo.getFileName().endsWith(".xls") || vo.getFileName().endsWith(".XLS")) {
				wb = excelCpaService.loadWorkbook(new FileInputStream(file));
			} else if (vo.getFileName().endsWith(".xlsx") || vo.getFileName().endsWith(".XLSX")) {
				wb = excelCpaService.loadWorkbook(new FileInputStream(file), new XSSFWorkbook());
			}
			
			if (wb == null) {
				return;
			}
			
			Sheet sheet = wb.getSheetAt(0);
			excelCpaService.uploadExcel("CpaDAO.insertStudent", sheet, 1, 5000);
			
		} catch (Exception e) {
			Logger.debug(e.getMessage());
		}
	}
}
