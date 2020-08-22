package egovframework.com.cer.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.cer.dao.CertificationDAO;
import egovframework.com.cer.service.CertificationService;

@Service("CertificationService")
public class CertificationServiceImpl implements CertificationService {

	@Resource(name = "CertificationDAO")
	CertificationDAO certificationDAO;
	
	@Override
	public String returnAMethod() {
		return "a";
	}
}
