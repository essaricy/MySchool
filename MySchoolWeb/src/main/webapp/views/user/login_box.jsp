<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
 
<script language="javascript">
$(document).ready(function () {

  // Submit on enter key
  $('input').keypress(function (e) {
    if (e.which == 13) {
      $('#Login').click();
    }
  });

  $('#Login').click(function() {
    var LoginId = $('#LoginId');
    var Password = $('#Password');
    if (LoginId.val() == '') {
      showFieldError(LoginId, 'Enter your Login Name');
      LoginId.focus();
    } else if (Password.val() == '') {
      showFieldError(Password, 'Enter your Login Password');
      Password.focus();
    } else {
      document.forms[0].action = '<%=request.getContextPath() %>/log/in.htm';
      document.forms[0].submit();
    }
  });
  $('#LoginId').focus();
});
</script>

<form method="POST">
<table class="formTable_Data" style="font-size: 1.25em;">
  <caption>LOGIN</caption>
  <tbody>
    <tr>
      <td colspan="2" class="label" style="text-align: left;"><label style="color: red;">${error}</label></td>
    </tr>
    <tr>
      <td width="40%" class="label">User</td>
      <td width="60%" class="value">
        <input type="text" id="LoginId" name="LoginId" />
      </td>
    </tr>
    <tr>
      <td width="40%" class="label"><spring:message code="login.password"/></td>
      <td width="60%" class="value">
        <input type="password" id="Password" name="Password" />
      </td>
    </tr>
    <tr>
      <td align="right" colspan="2">
        <input type="button" id="Login" value='<spring:message code="login.loginButton"/>' />
      </td>
    </tr>
    <tr>
      <td width="40%" class="label">&nbsp;</td>
      <td width="60%" align="right">
        <a href="<%=request.getContextPath()%>/settings/launchForgotPassword.htm">Forgot Password?</a>
      </td>
    </tr>
  </tbody>
</table>
</form>
