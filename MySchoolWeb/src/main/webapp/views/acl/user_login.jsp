<%@page import="com.myschool.user.constants.UserType"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/styles/bootstrap/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/styles/bootstrap/social-buttons.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/styles/font-awesome/css/font-awesome.min.css" />

<script src='https://www.google.com/recaptcha/api.js'></script>
<script language="javascript">
$(document).ready(function () {

  $('.btn').click(function(evt){evt.preventDefault();});

  // Submit on enter key
  $('input').keypress(function (e) {
    if (e.which == 13) {
      $('#Login').click();
    }
  });

  $('#Login').click(function() {
    var loginId = $('#LoginId');
    var password = $('#Password');
    var verificationCode = $('#g-recaptcha-response').val();

    if (loginId.val() == '') {
      $('#error_message').text('Enter Username');
      loginId.focus();
    } else if (password.val() == '') {
      $('#error_message').text('Enter password');
      password.focus();
    } else if (verificationCode == '') {
        $('#error_message').text('Click on "I\'m not a robot"');
    } else {
      document.forms[0].action = '<%=request.getContextPath() %>/acl/signin.htm';
      document.forms[0].submit();
    }
  });
  $('#LoginId').focus();

  $('#OAuth_Facebook').click(function() {
    $('#error_message').text('Facebook authentication is not permitted yet.');
  });
  $('#OAuth_GooglePls').click(function() {
    $('#error_message').text('Google+ authentication is not permitted yet.');
  });
  $('#OAuth_Twitter').click(function() {
    $('#error_message').text('Twitter authentication is not permitted yet.');
  });
  $('#NeedAssistance').click(function(evt) {
    evt.preventDefault();
    document.forms[0].action = '<%=request.getContextPath()%>/acl/assistance.htm';
    document.forms[0].submit();
  });
});
</script>

<form method="POST">

<input type="hidden" name="USER_TYPE" value="${USER_TYPE}" />
<table class="formTable_Container" style="width: 50%; margin-top: 40px; box-shadow: 0px 0px 50px 5px #888; border-radius: 4px;" cellpadding="10px">
  <caption>
    <c:choose>
      <c:when test="${USER_TYPE eq 'EMPLOYEE'}">
        <img src="<%=request.getContextPath()%>/images/icons/employee.png"/>&nbsp;Employee Login
      </c:when>
      <c:when test="${USER_TYPE eq 'STUDENT'}">
        <img src="<%=request.getContextPath()%>/images/icons/student.png"/>&nbsp;Student Login
      </c:when>
      <c:when test="${USER_TYPE eq 'ADMIN'}">
        <img src="<%=request.getContextPath()%>/images/icons/admin.png"/>&nbsp;Admin Login
      </c:when>
      <c:otherwise>
        Redirect to public dashboard
      </c:otherwise>
    </c:choose>
  </caption>

  <tr>
    <td id="error_message" colspan="2" style="color: red;  font-size: 14px; font-weight: bold; text-align: center;">
      <c:if test="${MESSAGE != null}">
      ${MESSAGE}
      </c:if>
      &nbsp;
    </td>
  </tr>

  <tr>
    <td align="center" valign="top">
      <input type="text" id="LoginId" name="LoginId" style="width:90%; height:38px; font-size: 14pt;" placeholder="Enter Username"><br/>
      <input type="password" id="Password" name="Password" style="width: 90%; height:38px; font-size: 14pt;" placeholder="Enter Password"><br/>
      <div class="g-recaptcha" data-sitekey="6LeZRQcUAAAAAN-GN8J5Pw0qv3InG7pgk_4jl8P-"></div><br/>
      <input type="button" id="Login" value="Sign In" style="width: 95%;height:38px; font-size: 14pt;"/><br/>
      <c:if test="${USER_TYPE != 'ADMIN'}">
      <a href="<%=request.getContextPath() %>/acl/forgotPassword.htm">Forgot Password?</a><br/><br/>
      <a href="#" id="NeedAssistance">Need assistance?</a>
      </c:if>
    </td>
    <td align="center" valign="top">
      <div>
        <button id="OAuth_Facebook" class="btn btn-facebook" style="width: 200px; display: inline-flex; align-items: flex-end;" disabled><i class="fa fa-facebook fa-2x"></i>&nbsp;&nbsp;&nbsp;Sign in with Facebook</button>
        </div>
      <br/>
      <div>
        <button id="OAuth_GooglePls" class="btn btn-google-plus" style="width: 200px; display: inline-flex; align-items: flex-end;" disabled><i class="fa fa-google-plus fa-2x"></i>&nbsp;&nbsp;&nbsp;Sign in with Google+</button>
      </div>
      <br/>
      <div>
        <button id="OAuth_Twitter" class="btn btn-twitter" style="width: 200px; display: inline-flex; align-items: flex-end;" disabled><i class="fa fa-twitter fa-2x"></i>&nbsp;&nbsp;&nbsp;Sign in with Twitter</button>
      </div>
    </td>
  </tr>
</table>
</form>