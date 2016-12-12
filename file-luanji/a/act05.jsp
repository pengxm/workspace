<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<title>抽奖</title>
<link rel="stylesheet"
	href="${request.contextPath}/dojoroot/dijit/themes/claro/document.css" />
<link rel="stylesheet"
	href="${request.contextPath}/dojoroot/dijit/themes/claro/claro.css" />
<link rel="stylesheet"
	href="${request.contextPath}/web/pg/act05/act05.css" />
<script type="text/javascript"
	src="${request.contextPath}/dojoroot/dojo/dojo.js"
	data-dojo-config="parseOnLoad: true, async:true"></script>
<script type="text/javascript"
	src="${request.contextPath}/web/pg/act05/act05.js"></script>
</head>
<body class="claro">
	<s:form id="frm_act05" action="act005" theme="simple" method='post'
		enctype="multipart/form-data">
		<jsp:include page="../../head/h.jsp" flush="true" />
		<jsp:include page="../local/local.jsp" flush="true" />
		<input type="hidden" id="hdnLtrID" name="ltrId"
			value="${ltrInf.ltrID}" />
		<input type="hidden" id="hdnReqTyp" name="reqTyp" value="${reqTyp}" />
		<div>
			<p>${ltrInf.ltrID}</p>
		</div>
		<div>
			<p>${ltrInf.ltrTtl}</p>
		</div>
		<div>
			<p>${ltrInf.ltrCon}</p>
		</div>
		<div>
			<p>${ltrInf.endCon}</p>
		</div>
		<div>
			<p>${przNmJson}</p>
		</div>
		<div>
			<p>${przInf}</p>
		</div>
		<div>
			<img alt="" src="${ltrInf.adPicInf.path}">
		</div>
		<div>
			<img alt="" src="${ltrInf.bgPicInf.path}">
		</div>
		<div>
			<img alt="" src="${ltrInf.btnPicInf.path}">
		</div>
		<div>
			<div id="btnSubmit" onclick="submit()">抽 奖</div>
		</div>
		<jsp:include page="/web/inf/inf.jsp" flush="true" />
		<jsp:include page="../../foot/f.jsp" flush="true" />
	</s:form>
</body>
</html>