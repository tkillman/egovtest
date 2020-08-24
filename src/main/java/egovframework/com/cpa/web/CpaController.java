package egovframework.com.cpa.web;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cpa.model.CpaVO;
import egovframework.com.cpa.service.CpaService;
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
    	
    	List<Map<String, String>> studentInfoList = getStudentInfo();
    	
    	cpaService.setDefaultStudentInfo(uploadFileList, studentInfoList);
    	
    	cpaService.excelDbInsert(uploadFileList.get(0));
    	//종료
		return "egovframework/com/cpa/cpaExcelUploadList";
	}
	
	@RequestMapping(value="/cpa/cpaExcelDownload.do")
	public void writeCpaExcelDownload(@ModelAttribute("cpaVO") CpaVO cpaVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setAttribute("downFile", "C:\\Users\\82108\\git\\egovtest\\src\\main\\webapp\\upload\\자격증업로드엑셀.xlsx");
		request.setAttribute("orgFileName", "자격증업로드엑셀.xlsx");
		request.setAttribute("orginFile", "자격증업로드엑셀.xlsx");
		
		EgovFileMngUtil.downFile(request, response);
	}
	
	public List<Map<String, String>> getStudentInfo(){
		List<Map<String, String>> studentInfoList = new ArrayList<>();
    	Map<String, String> studentInfo1 = new HashMap<>();
    	studentInfo1.put("goubun","1");
    	studentInfo1.put("name", "김동규");
    	studentInfo1.put("uniqueIndex", "4");
    	
    	Map<String, String> studentInfo2 = new HashMap<>();
    	studentInfo2.put("goubun","2");
    	studentInfo2.put("name", "김재명");
    	studentInfo2.put("uniqueIndex", "41");
    	
    	Map<String, String> studentInfo3 = new HashMap<>();
    	studentInfo3.put("goubun","3");
    	studentInfo3.put("name", "김성옥");
    	studentInfo3.put("uniqueIndex", "412");
    	
    	studentInfoList.add(studentInfo1);
    	studentInfoList.add(studentInfo2);
    	studentInfoList.add(studentInfo3);
    	return studentInfoList;
	}
}
