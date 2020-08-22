package egovframework.com.cpa.model;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;

import egovframework.rte.fdl.excel.EgovExcelMapping;
import egovframework.rte.fdl.excel.util.EgovExcelUtil;
public class CpaExcelMapping extends EgovExcelMapping {

	/**
	 * 교육생 맵핑
	 */
	@Override
	public Object mappingColumn(Row row) {
		StudentVO vo = new StudentVO();
		
		Cell cellGoubun = row.getCell(CpaExcelMappingEnum.GOUBUN.getExcelNum());
    	Cell cellName = row.getCell(CpaExcelMappingEnum.NAME.getExcelNum());
    	Cell cellAddr = row.getCell(CpaExcelMappingEnum.ADDR.getExcelNum());

    	vo.setGouBun       (Integer.parseInt(EgovExcelUtil.getValue(cellGoubun))); //교번
		vo.setName             (EgovExcelUtil.getValue(cellName)); //이름
		vo.setAddr       (EgovExcelUtil.getValue(cellAddr)); //주소
		
		return vo;
	}
}
