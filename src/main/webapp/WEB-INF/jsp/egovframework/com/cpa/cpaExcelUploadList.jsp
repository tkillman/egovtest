<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="validator"
	uri="http://www.springmodules.org/tags/commons-validator"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<title>중앙경찰 교육생 엑셀 업로드</title>
<!-- 중앙경찰 교육생 엑셀 업로드 -->
<link href="<c:url value="/css/egovframework/com/com.css"/>"
	rel="stylesheet" type="text/css">
<link href="<c:url value="/css/egovframework/com/button.css"/>"
	rel="stylesheet" type="text/css">
<link type="text/css" rel="stylesheet"
	href="<c:url value='/css/egovframework/com/cmm/jqueryui.css' />">
<script src="<c:url value='/js/egovframework/com/cmm/jquery.js' />"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jqueryui.js' />"></script>
<script type="text/javascript" src="<c:url value='/validator.do'/>"></script>
<validator:javascript formName="cpaVO" staticJavascript="false" xhtml="true" cdata="false" />

<script type="text/javaScript" language="javascript">
function fn_egov_regist_cpaExcelUpload() {
	var frm = document.getElementById("cpaVO");

	if(!validateCpaVO(frm)){
		return;	
	}
	frm.action = "<c:url value='/cpa/cpaExcelUpload.do'/>";
	frm.submit();
}	
</script>
</head>

<body>
	<form:form commandName="cpaVO" name="cpaVO" id="cpaVO" method="post" enctype="multipart/form-data">
		<div class="note">
			<h1>상단타이틀</h1>

			<!-- 등록  폼 영역  -->
			<table class="tbl_note" summary="교육생기수업로드">
				<caption>캡션</caption>
				<colgroup>
					<col style="width: 20%;">
					<col style="width:;">
				</colgroup>
				<tbody>
				<tr>
						<th>기수</th>
						<td><input type="text" id="gisu" name="gisu"/>
						</td>
					</tr>
					<tr>
						<th>엑셀파일</th>
						<td>
							<input type="text" id="fileNm" name="fileNm"
							class="file_input_textbox" style="width: 200px;" />
							<div class="file_input_div">
								<input type="button" class="file_input_button"
									value="파일선택" />
								<input type="file" name="uploadFile" class="file_input_hidden"
									onchange="javascript: document.getElementById('fileNm').value = this.value" />
							</div>
							from-path: <form:errors path="uploadFile"/><br/>
							<spring:hasBindErrors name="cpaVO">
								<c:set var="abab"><form:errors path="uploadFile"/></c:set>
								<script type="text/javascript">
								alert('abab');
								alert('${abab}');
								</script>
							</spring:hasBindErrors>
							spring-hasBinderrors : 
 							<spring:hasBindErrors name="cpaVO">
 								<c:set var="aaa" value=""></c:set>
								<c:forEach var="error" items="${errors.allErrors}">
									<c:choose>
										<c:when test="${empty error.arguments}">
											<spring:message code="${error.code}"/><br/>
										</c:when>
										<c:otherwise>
											<c:set var="aaa">${aaa}<spring:message code="${error.code}" arguments="${error.arguments}"/> \n</c:set>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								<script type="text/javascript">
 									alert('aaa');
									alert('${aaa}');
 								</script>
							</spring:hasBindErrors>
						</td>
					</tr>

				</tbody>
			</table>
			<!-- 줄간격조정  -->
<table width="700" cellspacing="0" cellpadding="0" border="0">
<tr>
	<td height="3px"></td>
</tr>
</table>
<!-- 목록/저장버튼  -->
<div class="txt-cnt mt20">
		<input type="submit" class="btnStyle02 bg_gray" value="목록" onClick="fn_egov_list_Zip(); return false;" /> <!-- 목록 -->
		<input type="submit" class="btnStyle02" value="저장" onClick="fn_egov_regist_cpaExcelUpload(); return false;" /> <!-- 저장 -->
	</div>
			
		</div>
	</form:form>
</body>
</html>