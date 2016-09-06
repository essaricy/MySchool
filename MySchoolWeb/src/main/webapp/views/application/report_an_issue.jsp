<%@page import="com.myschool.user.constants.UserType"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<script language="javascript" type="text/javascript">
$(document).ready( function () {
  $('#ChangeCaptcha').click(function() {
    $('#CaptchaImg').attr('src', '');
    $('#CaptchaImg').attr('src', '<%=request.getContextPath()%>/jcaptcha');
    $('#Captcha_UserFeed').val('');
  });

  $('#Description').textcounter({
    id: 'Description'
  });

  $('#Submit').click(function() {
    var Captcha_UserFeed = $('#Captcha_UserFeed').val();
    if (Captcha_UserFeed == '') {
      warn_ac('Please type the letters in the image into the textbox and prove that you are not a Robot!!!');
      return false;
    }

    var IssueData = new Object();
    IssueData.UserTypeID=$('#UserType').val();
    IssueData.EmailID=$('#EmailID').val();
    IssueData.Subject=$('#Subject').val();
    IssueData.Description=$('#Description').val();
    IssueData.Captcha_UserFeed=$('#Captcha_UserFeed').val();

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

<c:set var="UserTypes" value="<%=UserType.values()%>"/>
<table class="formTable_Container">
  <caption>Post Your Question</caption>
  <tr>
    <td width="40%" class="label">
      You are a<label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <select id="UserType" class="chosen-select">
        <c:forEach var="userType" items="${UserTypes}">
          <c:if test="${userType != UserType.ADMIN}">
            <option value="${userType.userTypeValue}">${userType}</option>
          </c:if>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      Your Email Address<label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <input type="text" id="EmailID" maxlength="32" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      Subject<label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <input type="text" id="Subject" maxlength="128"/>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      Description<label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <textarea id="Description" rows="10" maxlength="1024">
      </textarea>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">&nbsp;</td>
    <td width="60%" class="value">
      <%@ include file="/views/common/captcha.jsp" %>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">&nbsp;</td>
    <td width="60%" class="value" align="left">
      <input type="button" id="Submit" value="Submit" />
      <input type="reset" id="Reset" value="Reset" />
    </td>
  </tr>
</table>
<br/>
<br/>
<p/>