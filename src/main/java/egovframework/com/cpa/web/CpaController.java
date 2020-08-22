package egovframework.com.cpa.web;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springmodules.validation.commons.DefaultBeanValidator;
import org.terracotta.agent.repkg.de.schlichtherle.io.FileInputStream;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cpa.model.CpaVO;
import egovframework.com.cpa.service.CpaService;
import egovframework.com.cpa.service.Request;
import egovframework.com.cpa.service.Response;
import egovframework.com.utl.fcc.service.EgovFileUploadUtil;
import egovframework.com.utl.fcc.service.EgovFormBasedFileVo;
import egovframework.rte.fdl.excel.EgovExcelService;
import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class CpaController {
    
	private static final Logger Logger = LoggerFactory.getLogger(CpaController.class);
			
	/** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
	
	/** cpaService */
    @Resource(name = "cpaService")
    protected CpaService cpaService;
    
    @Resource(name = "excelCpaService")
    private EgovExcelService excelCpaService;
    
	@Autowired
	private DefaultBeanValidator beanValidator;


	/**
	 * 엑셀업로드하여 DB insert 한다.
	 * @param CpaVO - 엑셀업로드VO
	 * @return String - 리턴 Url
	 *
	 * @param CpaVO
	 */
    @IncludedInfo(name="엑셀업로드리스트", listUrl="/cpa/cpaExcelUploadList.do", order = 1249 , gid = 50)
	@RequestMapping(value="/cpa/cpaExcelUploadList.do")
	public String selectCpaExcelUploadList(@ModelAttribute("loginVO") LoginVO loginVO
											,Model model
			) throws Exception {
    	
    	model.addAttribute("cpaVO", new CpaVO());
    	//종료
		return "egovframework/com/cpa/cpaExcelUploadList";
	}
    
	/**
	 * 엑셀업로드하여 DB insert 한다.
	 * @param CpaVO - 엑셀업로드VO
	 * @return String - 리턴 Url
	 *
	 * @param CpaVO
	 */
	@RequestMapping(value="/cpa/cpaExcelUpload.do", method = RequestMethod.POST)
	public String updateCpaExcelUpload(@ModelAttribute("cpaVO") CpaVO cpaVO
											, BindingResult bindingResult
											, MultipartHttpServletRequest mptRequest
			) throws Exception {
		beanValidator.validate(cpaVO, bindingResult);

		//TODO 파일유효성 검사
    	cpaService.validate(cpaVO, bindingResult);
    	
    	if (bindingResult.hasErrors()) {
    		Logger.debug("bindgingResult error :: " + bindingResult.getFieldError().getDefaultMessage());
    		return "egovframework/com/cpa/cpaExcelUploadList";
    	}
    	
    	String cpaExcelUploadFilePath = propertiesService.getString("cpaExcelUploadFilePath"); 
    	String where = cpaExcelUploadFilePath;
    	
    	Logger.debug("where :: " + where);
    	
    	//TODO 파일업로드
    	List<EgovFormBasedFileVo> uploadFileList = EgovFileUploadUtil.uploadFiles(mptRequest, where, 0);
    	
    	cpaService.excelDbInsert(uploadFileList.get(0));
    	//종료
		return "egovframework/com/cpa/cpaExcelUploadList";
	}
	
}
