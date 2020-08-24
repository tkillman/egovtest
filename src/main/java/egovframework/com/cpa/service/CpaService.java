package egovframework.com.cpa.service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;

import egovframework.com.cpa.model.CpaVO;

import egovframework.com.utl.fcc.service.EgovFormBasedFileVo;

public interface CpaService {

	public void validate(CpaVO cpaVo, Errors errors);
	
	public void excelDbInsert(EgovFormBasedFileVo vo) throws FileNotFoundException, Exception;
	
	/**
	 * @param uploadFileList
	 */
	public void setDefaultStudentInfo(List<EgovFormBasedFileVo> uploadFileList, List<Map<String, String>> studentInfoList) throws FileNotFoundException, Exception;
}
