<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
 
<script language="javascript">
$(document).ready(function () {
  $('input').keypress(function (e) {
    if (e.which == 13) {
      $('#Login').click();
    }
  });
  $('#Login').click(function() {
    var loginId = $('#loginId');
    var password = $('#password');
    if (loginId.val() == '') {
      showFieldError(loginId, 'Enter your login name');
      loginId.focus();
    } else if (password.val() == '') {
      showFieldError(password, 'Enter your login password');
      password.focus();
    } else {
      document.forms[0].action = '<%=request.getContextPath() %>/login.htm'
      document.forms[0].submit();
    }
  });
  $('#loginId').focus();
});
</script>

<form method="POST">
<table cellpadding="4" cellspacing="0" width="100%" class="formTable">
  <tbody>
    <tr>
      <td colspan="2"><span class="error">${error}</span></td>
    </tr>
    <tr>
      <td width="40%" class="formLabel">User</td>
      <td width="60%">
        <input type="text" id="loginId" name="loginId" class="formInputText" />
      </td>
    </tr>
    <tr>
      <td width="40%" class="formLabel"><spring:message code="login.password"/></td>
      <td width="60%">
        <input type="password" id="password" name="password" class="formInputText" />
      </td>
    </tr>
    <tr>
      <td align="right" colspan="2">
        <input type="button" value='<spring:message code="login.loginButton"/>' class="formButton" id="Login"/>
      </td>
    </tr>
    <tr>
      <td width="40%" class="formLabel">&nbsp;</td>
      <td width="60%" align="right">
        <a href="<%=request.getContextPath()%>/settings/launchForgotPassword.htm" class="formLink">Forgot Password?</a>
      </td>
    </tr>
  </tbody>
</table>
</form>
