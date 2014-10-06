<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		$('#resetPassword').click(function() {
			var loginName = $('#loginName').val();
			if (loginName == null || loginName == '') {
				alert('<spring:message code="loginName.required"/>');
				return false;
			}
			var securityAnswer = $('#securityAnswer').val();
			if (securityAnswer == null || securityAnswer == '') {
				alert('<spring:message code="securityAnswer.required"/>');
				return false;
			}
			//document.forms[0].action='<%=request.getContextPath()%>/settings/resetPassword.htm';
			//document.forms[0].submit();
			submitPage('<%=request.getContextPath()%>/settings/resetPassword.htm', '');
		});
		$('#loginButton').click(function() {
			//document.forms[0].action='<%=request.getContextPath()%>';
			//document.forms[0].submit();
			submitPage('<%=request.getContextPath()%>', '');
		});
	});
</script>

<table cellpadding="0" cellspacing="0" border="0" width="60%">
	<caption class="dataTableCaption"><spring:message code="forgotPassword.title"/></caption>
	<tr>
		<td colspan="3" class="error">
			<c:if test="${result != null && result.statusMessage != null}">
				<table border="0" cellpadding="0" cellspacing="0" align="center">
					<tr><td class="error">${result.statusMessage}<br>
				</table>
			</c:if>
		</td>
	</tr>
	<tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0" border="0" class="display">
				<thead>
				</thead>
				<tbody>
					<tr>
						<td width="20%">&nbsp;</td>
						<td width="30%" align="right"><spring:message code="forgotPassword.loginName"/><label class="mandatory">*</label></td>
						<td width="20px">&nbsp;</td>
						<td width="30%">
							<input type="text" id="loginName" name="loginName" class="formInputText" maxlength="32" />
						</td>
						<td width="20%">&nbsp;</td>
					</tr>
					<tr>
						<td width="10%">&nbsp;</td>
						<td width="30%" align="right"><spring:message code="forgotPassword.securityQuestion"/><label class="mandatory">*</label></td>
						<td width="20px">&nbsp;</td>
						<td width="50%">
							<select id="securityQuestion" name="securityQuestion" class="chosen-select">
							<c:forEach var="securityQuestion" items="${securityQuestions}">
									<option value="${securityQuestion}">${securityQuestion.question}</option>
							</c:forEach>
							</select>
						</td>
						<td width="10%">&nbsp;</td>
					</tr>
					<tr>
						<td width="20%">&nbsp;</td>
						<td width="30%" align="right"><spring:message code="forgotPassword.securityAnswer"/><label class="mandatory">*</label></td>
						<td width="20px">&nbsp;</td>
						<td width="30%">
							<input type="password" id="securityAnswer" name="securityAnswer" class="formInputText" />
						</td>
						<td width="20%">&nbsp;</td>
					</tr>
					<tr>
						<td width="20%">&nbsp;</td>
						<td width="30%" align="right">
							&nbsp;
						</td>
						<td width="20px">&nbsp;</td>
						<td width="30%">
							<input type="button" id="loginButton" value='Goto Login' class="formButton" />&nbsp;<input type="button" id="resetPassword" value='<spring:message code="forgotPassword.resetPassword"/>' class="formButton" />
						</td>
						<td width="20%">&nbsp;</td>
					</tr>
				</tbody>
				<tfoot>
				</tfoot>
			</table>			
		</td>
	</tr>
</table>