package egovframework.com.cer.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cer.model.CertificationVO;
import egovframework.com.cer.service.CertificationService;
import egovframework.com.cmm.annotation.IncludedInfo;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author dkkim
 *
 */
@Controller
public class CertificationController {

	@Resource(name = "CertificationService")
	CertificationService certificationService;
	
	/**
	 * @return
	 */
    @IncludedInfo(name="자격증업로드폼", listUrl="/cer/searchCertificationForm.do", order = 1247 , gid = 50)
	@RequestMapping(value="/cer/searchCertificationForm.do")
	public String searchCertificationForm(CertificationVO certificationVO) {
		
    	System.out.println(certificationService.returnAMethod());
    	
		return null;
	}
}
