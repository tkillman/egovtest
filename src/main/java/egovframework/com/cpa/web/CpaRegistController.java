package egovframework.com.cpa.web;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cpa.model.CpaRegistDetailVO;
import egovframework.com.cpa.model.CpaRegistVO;
import egovframework.com.cpa.service.CpaRegistService;

@Controller
public class CpaRegistController {

	private Logger Logger = LoggerFactory.getLogger(CpaRegistController.class);
	
	@Autowired
	CpaRegistService cpaRegistService;
	
	@RequestMapping("/cpaRegist/cpaRegistList.do")
	@IncludedInfo(name="목록 리스트", listUrl="/cpaRegist/cpaRegistList.do", order = 1249 , gid = 50)
	public String cpaRegistList(CpaRegistVO cpaRegistVO, Model model) {
		
		model.addAttribute("cpaRegistVO", cpaRegistVO);
		return "egovframework/com/cpaRegist/cpaRegistList";
	}
	
	@RequestMapping("/cpaRegist/cpaRegistForm.do")
	public String cpaRegistForm(CpaRegistVO cpaRegistVO, Model model) {
		
		List<Map<String, String>> typeCdList = cpaRegistService.selectCode();
		//수정
		if (cpaRegistVO.getTbIdx() != null) {
			CpaRegistVO dataVo = cpaRegistService.cpaRegistForm(cpaRegistVO);
			dataVo.setCpaRegistDetailList(cpaRegistService.cpaRegistDetailList(dataVo));
			model.addAttribute("cpaRegistVO", dataVo);
		} 

		model.addAttribute("typeCdList", typeCdList);
		
		return "egovframework/com/cpaRegist/cpaRegistForm";
	}
	
	@RequestMapping("/cpaRegist/insertCpaRegist.do")
	public String insertCpaRegist(CpaRegistVO cpaRegistVO, Model model) {
		int[] returnValue = {0};
		returnValue[0] += cpaRegistService.insertCpaRegist(cpaRegistVO);
		
		List<CpaRegistDetailVO> cpaRegistDetailList = cpaRegistVO.getCpaRegistDetailList();
		
		if (cpaRegistDetailList != null) {
			cpaRegistDetailList.stream().forEach(vo -> {
				returnValue[0] += cpaRegistService.insertCpaRegistDetail(vo);
			});
		}
		
		if (returnValue[0] > 0) {
			Logger.debug("returnVal is bigger than 0");
		} else {
			Logger.debug("returnVal is 0");
		}
		
		return "egovframework/com/cpaRegist/cpaRegistList";
	}	
	
	@RequestMapping("/cpaRegist/updateCpaRegist.do")
	public String updateCpaRegist(CpaRegistVO cpaRegistVO, Model model) {
		int returnValue = 0;
		returnValue = cpaRegistService.updateCpaRegist(cpaRegistVO);
		returnValue	= cpaRegistService.updateCpaRegistDetail(cpaRegistVO);
		
		return "egovframework/com/cpaRegist/cpaRegistList";
	}
	
	@RequestMapping("/cpaRegist/deleteCpaRegist.do")
	public String deleteCpaRegist(CpaRegistVO cpaRegistVO, Model model) {
		int returnValue = 0;
		returnValue = cpaRegistService.deleteCpaRegist(cpaRegistVO);
		return "egovframework/com/cpaRegist/cpaRegistList";
	}	
}
