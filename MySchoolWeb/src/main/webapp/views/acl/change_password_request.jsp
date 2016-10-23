<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<script src='https://www.google.com/recaptcha/api.js'></script>
<script language="javascript" type="text/javascript">
$(document).ready( function () {
  $('#Submit').click(function() {
    var verificationCode = $('#g-recaptcha-response').val();
    if (verificationCode == '') {
      notifyError('Click on "I\'m not a robot"');
      return false;
    }
    var emailId = $('#EmailID').val();
    if (emailId == '') {
      notifyError('Provide email address');
      return false;
    }
    jQuery.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/acl/changePasswordRequest.htm",
      data: {
        CaptchaResponse: verificationCode,
        EmailID: emailId
      },
      context: this
    }).done(function(result) {
      if (result.Successful) {
        alertInfo('email has been sent.');
      } else {
        alertError(result.StatusMessage);
      }
    });
  });
});
</script>

<form>

<table class="formTable_Container" width="60%">
  <caption>Change Password Request</caption>
  <tr>
    <td style="padding-top: 20px;">If you have forgotten the password, please eneter email address that is associated with your account and you will be sent an email with instructions on how to change the password.
    </td>
  </tr>
  <tr>
    <td style="padding-top: 20px;" colspan="2">
      <input type="text" id="EmailID" style="width:100%; height:38px; font-size: 14pt;" placeholder="Enter email id" />
    </td>
  </tr>
  <tr>
    <td style="padding-top: 20px;" colspan="2" align="center">
      <div class="g-recaptcha" data-sitekey="${ORGANIZATION_MANIFEST.captchaKey}"></div>
    </td>
  </tr>
  <tr>
    <td style="padding-top: 20px;" colspan="2" align="center">
      <input type="button" id="Submit" value="Submit" />
    </td>
  </tr>
</table>
</form>
