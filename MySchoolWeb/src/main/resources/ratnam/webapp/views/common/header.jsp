<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table cellpadding="0" cellspacing="0" class="headerTable" border="0">
  <tr>
    <td width="109px" style="padding-left:8px;padding-top:2px;">
		<img src="<%=request.getContextPath()%>/image/getImage.htm?type=logo" width="100px" height="100px" border="0"/>
    </td>
    <td colspan="2">&nbsp;</td>
		<td>
			<table cellpadding="0" cellspacing="0" width="100%" border="0">
				<tr>
					<td style="color:white;font-size:30px;letter-spacing:2px;word-spacing:4px;text-transform:uppercase;">
						Dr. Kishore's Ratnam Schools
					</td>
				</tr>
			</table>
		</td>
    <c:if test="${USER_CONTEXT == null}">
			<td width="*" style="color:white;font-size:14px;font-weight:bold;" valign="bottom" align="right">Student Information Management System</td>
    </c:if>
    <c:if test="${USER_CONTEXT != null}">
		<td width="30%" valign="top">
			<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
				<tr>
					<td style="color:white;font-size:14px;font-weight:bold;" valign="bottom" align="right">Student Information Management System</td>
				</tr>
				<tr>
					<td>
						<table border="0" cellpadding="0" cellspacing="0" width="100%" valign="top">
							<tr>
								<td align="right" class="headerLabel">Welcome,&nbsp;${USER_CONTEXT.login.loginId}</td>
								<td width="60px" align="center">&nbsp;
								<!-- Values are equal to the constants used in com.myschool.common.dto.UserTypeDto class-->
								<c:if test="${USER_CONTEXT.userType == 'ADMIN'}">
									<img src="<%=request.getContextPath()%>/images/icons/admin.png" width="36px" height="36x" border="0"/>
								</c:if>
									<c:if test="${USER_CONTEXT.userType == 'EMPLOYEE'}">
										<img src="<%=request.getContextPath()%>/images/icons/employee.png" width="36px" height="36x" border="0"/>
									</c:if>
									<c:if test="${USER_CONTEXT.userType == 'STUDENT'}">
										<img src="<%=request.getContextPath()%>/images/icons/student.png" width="36px" height="36x" border="0"/>
									</c:if>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>
						<table border="0" cellpadding="0" cellspacing="0" width="100%" valign="bottom">
							<tr>
								<td>&nbsp;</td>
								<td width="60px" align="center">
									<a href="<%=request.getContextPath()%>/settings.htm" class="headerLink">Settings</a>
								</td>
								<td width="10px" align="center" class="headerSeperator">|</td>
								<td width="60px" align="center">
									<a href="<%=request.getContextPath()%>/logout.htm" class="headerLink">Logout</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
   </c:if>
  </tr>
</table>