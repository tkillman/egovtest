package egovframework.com.cer.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unitils.UnitilsJUnit4TestClassRunner;

import egovframework.com.cer.service.CertificationService;

@RunWith(UnitilsJUnit4TestClassRunner.class)
class CertificationControllerTest {

	private Logger Logger = LoggerFactory.getLogger(CertificationControllerTest.class);
	
	@Resource(name = "CertificationService")
	CertificationService certificationService;
	
	@Test
	void aaa() {
		Logger.debug("시발");
		Logger.debug(certificationService.toString());
		assertEquals(certificationService.returnAMethod(),"a");
	}

	
	
}
