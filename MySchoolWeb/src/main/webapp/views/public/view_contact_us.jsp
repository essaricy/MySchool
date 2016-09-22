<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<script src='https://www.google.com/recaptcha/api.js'></script>
<script language="javascript" type="text/javascript">
$(document).ready( function () {
  $('#Description').textcounter({
    id: 'Description'
  });

  $('#Submit').click(function() {
    var VerificationCode = $('#g-recaptcha-response').val();
    if (VerificationCode == '') {
      notifyError('Click on "I\'m not a robot"');
      return false;
    }

    var IssueData = new Object();
    IssueData.UserName=$('#UserName').val();
    IssueData.EmailID=$('#EmailID').val();
    IssueData.Subject=$('#Subject').val();
    IssueData.Description=$('#Description').val();
    IssueData.VerificationCode=VerificationCode;

    jQuery.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/issue/doCreate.htm",
      data: {
        IssueData: JSON.stringify(IssueData)
      },
      context: this
    }).done(function(result) {
      $('#Captcha_ReloadImage').click();
      if (result.Successful) {
        info('Issue has been successfully recorded by us.<br/>You will be notified with the status of the issue through email.<br/>We thank you for your initiative and support.');
        $('#Reset').click();
      } else {
        attendError(result.StatusMessage);
      }
    });
  });
  $('.chosen-select').chosen({width: "40%"});
});

</script>

<table class="formTable_Data" style="width: 60%;" cellpadding="10px">
  <caption>Contact Us</caption>
  <tr>
    <td width="50%" class="label">Your Name<label class="mandatory">*</label></td>
    <td width="50%" class="value">
      <input type="text" id="UserName" maxlength="32" />
    </td>
  </tr>
  <tr>
    <td width="50%" class="label">Email Address<label class="mandatory">*</label></td>
    <td width="50%" class="value">
      <input type="text" id="EmailID" maxlength="32" />
    </td>
  </tr>
  <tr>
    <td width="50%" class="label">Subject<label class="mandatory">*</label></td>
    <td width="50%" class="value">
      <input type="text" id="Subject" maxlength="128"/>
    </td>
  </tr>
  <tr>
    <td width="50%" class="label">Description<label class="mandatory">*</label></td>
    <td width="50%" class="value">
      <textarea id="Description" rows="10" maxlength="1024">
      </textarea>
    </td>
  </tr>
  <tr>
    <td width="50%" class="label">&nbsp;</td>
    <td width="50%" class="value">
      <div class="g-recaptcha" data-sitekey="6LeZRQcUAAAAAN-GN8J5Pw0qv3InG7pgk_4jl8P-"></div>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <input type="button" id="Submit" value="Submit" />
    </td>
  </tr>
</table>
<br/>
<br/>
<p/>
