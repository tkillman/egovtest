package egovframework.com.cpa.service;

import java.io.FileNotFoundException;

import org.springframework.validation.Errors;

import egovframework.com.cpa.model.CpaVO;
import egovframework.com.utl.fcc.service.EgovFormBasedFileVo;

public interface CpaService {

	public void validate(CpaVO cpaVo, Errors errors);
	
	public void excelDbInsert(EgovFormBasedFileVo vo) throws FileNotFoundException, Exception;
}
