<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
  <head>
    <title><spring:message code="student.registration"/></title>
	<style type="text/css">
		@import "<%=request.getContextPath()%>/styles/demo_table.css";
		@import "<%=request.getContextPath()%>/styles/ddmegamenu.css";
		@import "<%=request.getContextPath()%>/styles/dhtmlwindow.css";
		@import "<%=request.getContextPath()%>/widgets/fullcalendar/cupertino/theme.css";
		@import "<%=request.getContextPath()%>/widgets/fullcalendar/fullcalendar.css";
	</style>

	<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/scripts/jquery/jquery-1.8.1.min.js"></script>
	<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/scripts/jquery/jquery.dataTables.js"></script>
	<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/scripts/common.js"></script>
	<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/scripts/ddmegamenu.js"></script>
	<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/scripts/dhtmlwindow.js"></script>
	<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/widgets/fullcalendar/fullcalendar.min.js"></script>

	<script type="text/javascript" charset="utf-8">
	var lookupBySchoolDialog = null;
	var tabNames = new Array();
	tabNames[0] = 'personalDetailsForm';
	tabNames[1] = 'familyDetailsForm';
	tabNames[2] = 'admissionDetailsForm';
	tabNames[3] = 'underatakingForm';
	tabNames[4] = 'awarenessForm';
	tabNames[5] = 'performanceDetails';
	tabNames[6] = 'attendance';

	function showTab(tabId) {
		var tab = null;
		var tabName = null;
		var formTable = null;

		for (var index=0; index<tabNames.length; index++) {
			tabName = tabNames[index];
			tab = document.getElementById('tab_' + index);
			formTable = document.getElementById(tabName);
			if (index == tabId) {
				$('#'+tabName).show();
			} else {
				$('#'+tabName).hide();
			}
		}
	}


	function getTabIndex(tabName) {
		for (var index=0; index<tabNames.length; index++) {
			if (tabNames[index] == tabName) {
				return index;
			}
		}
		return 0;
	}
	var interval;

	function loadImage() {
		clearInterval(interval);
		var contentId = document.getElementById('secureToken').value;
		document.getElementById('studentImage').src = '<%=request.getContextPath()%>/image/getImage.htm?type=student&contentId=' + contentId + '&sid=' + new Date().getTime();
	}

</script>
  </head>
  <body leftmargin="0" topmargin="0" onload="showTab(getTabIndex('${tabName}'))">
	<form:form method="POST" commandName="UploadFileForm" enctype="multipart/form-data" action="">
 	<table cellpadding="0" cellspacing="0" class="documentContentTable">
		<tr class="headerRow">
			<td colspan="2" valign="top"><%@ include file="WEB-INF/common/header.jspf" %><%@ include file="WEB-INF/common/ddmegamenu.jspf" %></td>
		</tr>
    <tr>	
	  <td valign="top" style="padding:0px 25px 0px 25px;">&nbsp;
			<c:if test="${result != null && result.statusMessage != null}">
				<table border="0" cellpadding="0" cellspacing="0" align="center">
					<tr><td class="error">${result.statusMessage}</td></tr>
				</table>
			</c:if>

			<table border="0" width="100%" cellpadding="0" cellspacing="0" align="center">
				<caption class="dataTableCaption">
					<c:if test="${student == null}">
						<font style="color:white;">Student Registration</font>
					</c:if>
					<c:if test="${student != null}">
					 <font style="color:white;">${student.personalDetails.firstName}&nbsp;${student.personalDetails.middleName}&nbsp;${student.personalDetails.lastName}</font>
					</c:if>
				</caption>
				</caption>
				<tr>
					<td width="13%" valign="top">
						<table cellpadding="0" cellspacing="0" class="formTable">
							<tr>
								<td>
									<c:if test="${student == null}">
									<table cellpadding="5" cellspacing="0" border="0" width="100%" height="100%" class="formTable">
										<tr>
											<td align="center">
												<div id="studentImageDiv"><img id="studentImage" name="studentImage" src="<%=request.getContextPath()%>/image/getImage.htm?type=no-image" border="1" width="150px" height="160px"/></div>
											</td>
										</tr>
										<tr>
											<td width="33%">
												<input type="file" id="uploadImage" name="content" onchange="javascript: uploadStudentImage();">
											</td>
										</tr>
									</table>
									</c:if>
									<c:if test="${student != null && student.personalDetails != null}">
									<table cellpadding="5" cellspacing="0" class="formTable">
										<tr>
											<td width="33%">
												<div id="studentImageDiv"><img id="studentImage" name="studentImage" src="<%=request.getContextPath()%>/image/getImage.htm?type=student&contentId=${student.admissionNumber}&sid=<%= new java.util.Date().getTime()%>" border="1" width="150px" height="160px"/></div>
											</td>
										</tr>
									</table>
									</c:if>
								</td>
							</tr>
							<tr>
								<td id="tab_0" style="display:none;">
									<input type="button" class="listButton" onclick="showTab(0);" value='<spring:message code="student.personal.details"/>' />
								</td>
							</tr>
							<tr>
								<td id="tab_1" style="display:none;">
									<input type="button" class="listButton" onclick="showTab(1);" value='<spring:message code="student.family.details"/>' />
								</td>
							</tr>
							<tr>
								<td id="tab_2" style="display:none;">
									<input type="button" class="listButton" onclick="showTab(2);" value='<spring:message code="student.admission.details"/>' />
								</td>
							</tr>
							<tr>
								<td id="tab_3" style="display:none;">
									<input type="button" class="listButton" onclick="showTab(3);" value='<spring:message code="student.undertaking.details"/>' />
								</td>
							</tr>
							<tr>
								<td id="tab_4" style="display:none;">
									<input type="button" class="listButton" onclick="showTab(4);" value='<spring:message code="student.declaration.details"/>' />
								</td>
							</tr>
							<tr>
								<td id="tab_5" style="display:none;">
									<input type="button" class="listButton" onclick="showTab(5);" value='<spring:message code="student.performance.details"/>' />
								</td>
							</tr>
							<tr>
								<td id="tab_6" style="display:none;">
									<input type="button" class="listButton" onclick="showTab(6);" value='Attendance' />
								</td>
							</tr>
						</table>
					</td>
					<td width="2%">&nbsp;</td>
					<td width="85%" valign="top">
						<%@ include file="/views/student/studentPersonalDetails.jsp" %>
						<%@ include file="/views/student/studentFamilyDetails.jsp" %>
						<%@ include file="/views/student/studentAdmissionDetails.jsp" %>
						<%@ include file="/views/student/studentUndertakingDetails.jsp" %>
						<%@ include file="/views/student/studentDeclarationDetails.jsp" %>
						<%@ include file="/views/student/studentPerformanceDetails.jsp" %>
						<%@ include file="/views/attendance/attendance.jsp" %>
					</td>
				</tr>
			</table>
	  </td>
	</tr>
	  <tr id="footerRow" style="height:5%;">
	    <td align="center" colspan="100%"><%@ include file="WEB-INF/common/footer.jspf" %></td>
	  </tr>
  </table>
	<div id="shadow" class="opaqueLayer"></div>
  </form:form>
  </body>
</html>