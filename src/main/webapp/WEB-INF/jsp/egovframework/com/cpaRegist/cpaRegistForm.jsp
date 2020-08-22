<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<title>중앙경찰 교육생 엑셀 업로드</title>
<!-- 중앙경찰 교육생 엑셀 업로드 -->
<link href="<c:url value="/css/egovframework/com/com.css"/>" rel="stylesheet" type="text/css">
<link href="<c:url value="/css/egovframework/com/button.css"/>" rel="stylesheet" type="text/css">
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/jqueryui.css' />">
<script src="<c:url value='/js/egovframework/com/cmm/jquery.js' />"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jqueryui.js' />"></script>
<script type="text/javascript" src="<c:url value='/validator.do'/>"></script>
<validator:javascript formName="cpaRegistVO" staticJavascript="false" xhtml="true" cdata="false" />

<style type="text/css">
.textAreaContent {
	width: 50%;
}
</style>
<body>
	<form:form commandName="cpaRegistVO" name="cpaRegistVO" id="cpaRegistVO">
	cpaRegistForm.jsp<br/>
	<form:input type="text" path="tbIdx" readonly="readonly"/><br/>
	
	제목 : <form:input type="text" path="title"/><form:errors path="title"></form:errors><br/>
	내용 : <form:textarea rows="5" cols="5" path="content" class="textAreaContent"></form:textarea>
	<br/>
	타입 : 
	<form:select path="typeCd">
		<form:option value="">--선택--</form:option>
		<form:option value="100">구분1</form:option>
		<form:option value="200">구분2</form:option>
	</form:select>
	<br/><br/><br/><br/><br/>
	---- 하위단계 <input type="button" value="행추가" onclick="fnInsertRow()"/><br/>
	
	<c:forEach var= "cpaRegistDetailVO" items="${cpaRegistVO.cpaRegistDetailList}" varStatus="i">
		${i}<br/>
		<input type="text" name="cpaRegistDetailVO.brothersIdx"/>
		<input type="text" name="cpaRegistDetailVO.brothersName"/>
		<input type="text" name="cpaRegistDetailVO.brothersAge"/>
		<input type="text" name="cpaRegistDetailVO.brothersDrinkYn"/>
	</c:forEach>
	
	</form:form>
	
	<input type="button" onclick="fnCpaRegist()" value="저장">
</body>

<script type="text/javascript">
fnCpaRegist = function () {
	alert('fnCpaRegist');
	var frm = document.cpaRegistVO;
	
	if (!validateCpaRegistVO(frm)) {
		return;
	}
	
	frm.action = "<c:url value='/cpaRegist/insertCpaRegist.do' />";
	frm.submit();
}

fnInsertRow = function () {
	alert('fnInsertRow');
	// TODO 행추가
}


</script>

</html>