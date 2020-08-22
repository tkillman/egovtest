package egovframework.com.cpa.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.cpa.dao.CpaRegistDAO;
import egovframework.com.cpa.model.CpaRegistDetailVO;
import egovframework.com.cpa.model.CpaRegistVO;

@Service("CpaRegistService")
public class CpaRegistService {
	
	@Autowired
	CpaRegistDAO cpaRegistDAO;
	
	public CpaRegistVO cpaRegistForm(CpaRegistVO cpaRegistVO) {
		CpaRegistVO dataVo = cpaRegistDAO.selectOne("CpaRegistDAO.cpaRegistForm");
		return Optional.ofNullable(dataVo).orElse(new CpaRegistVO());
	};
	
	public List<CpaRegistDetailVO> cpaRegistDetailList(CpaRegistVO cpaRegistVO){
		List<CpaRegistDetailVO> dataList = cpaRegistDAO.selectList("CpaRegistDAO.cpaRegistDetailList", cpaRegistVO);
		return Optional.ofNullable(dataList).orElse(new ArrayList<CpaRegistDetailVO>());
	}
	
	public List<Map<String, String>> selectCode(){
		Map<String, String> code1 = new HashMap<>();
		code1.put("100", "구분1");
		
		Map<String, String> code2 = new HashMap<>();
		code1.put("200", "구분2");
		
		return Arrays.asList(code1, code2);
	}
	
	public int insertCpaRegist(CpaRegistVO cpaRegistVO) {
		return cpaRegistDAO.insert("CpaRegistDAO.insertCpaRegist", cpaRegistVO);
	}
	
	public int insertCpaRegistDetail(CpaRegistDetailVO cpaRegistDetailVO) {
		return cpaRegistDAO.insert("CpaRegistDAO.insertCpaRegistDetail", cpaRegistDetailVO);
	}
	
	public int updateCpaRegist(CpaRegistVO cpaRegistVO) {
		return cpaRegistDAO.update("CpaRegistDAO.updateCpaRegist", cpaRegistVO);
	}
	
	public int updateCpaRegistDetail(CpaRegistVO cpaRegistVO) {
		int[] returnValue = {0};
		cpaRegistDAO.delete("CpaRegistDAO.deleteCpaRegistDetail", cpaRegistVO);
		
		List<CpaRegistDetailVO> cpaRegistDetailVOList = cpaRegistVO.getCpaRegistDetailList();
		
		if (cpaRegistDetailVOList != null) {
			cpaRegistDetailVOList.stream().forEach(vo -> {
				returnValue[0] += insertCpaRegistDetail(vo);
			});
		}
		
		return returnValue[0];
	}
	
	public int deleteCpaRegist(CpaRegistVO cpaRegistVO) {
		int returnValue = 0;
		returnValue += cpaRegistDAO.delete("CpaRegistDAO.deleteCpaRegist", cpaRegistVO);
		returnValue += cpaRegistDAO.delete("CpaRegistDAO.deleteCpaRegistDetail", cpaRegistVO);
		
		return 0;
	}
}
