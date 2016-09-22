<%@page import="com.myschool.user.constants.UserType"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src='https://www.google.com/recaptcha/api.js'></script>
<style>
.card {
    width: 95%;
}
</style>
<script>
  var current_slide = 'Problem';
  $(document).ready(function() {
    //$('.btn').click(function(evt){evt.preventDefault();});
    $('.card').hide();
    $('#' + current_slide).show();

    $('#StartAgain').click(function() {
      location.reload();
    });

    $('#Next').click(function() {
      if (current_slide == 'Problem') {

        var Problem = $("input[name='Problem']:checked"). val();
        if (Problem == 'NewSignUp') {
          $('#Problem').slideUp();
          $('#NewSignupInstructions').slideDown();
          $('#BotFilter').slideDown();
          current_slide = 'NewSignupInstructions';
        } else if (Problem == 'FindAccount') {
          $('#Problem').slideUp();
          $('#SearchByEmail').slideDown();
          $('#BotFilter').slideDown();
          current_slide = 'FindAccount';
        } else if (Problem == 'RegStatus') {
          $('#Problem').slideUp();
          $('#SearchByEmail').slideDown();
          $('#BotFilter').slideDown();
          current_slide = 'RegStatus';
        }
      } else if (current_slide == 'NewSignupInstructions') {
        $('#NewSignupInstructions').slideUp();
        $('#BotFilter').slideUp();
        $('#SelfSubmitForm').slideDown();
        $('#Next').hide();
        $('#StartAgain').hide();
      } else if (current_slide == 'FindAccount') {
        $('#SearchByEmail').slideUp();
        $('#BotFilter').slideUp();
        $('#SearchAccount_Result').slideDown();
        $('#Next').hide();
      } else if (current_slide == 'RegStatus') {
        $('#SearchByEmail').slideUp();
        $('#BotFilter').slideUp();
        $('#RegStatus_Result').slideDown();
        $('#Next').hide();
      }
    });
    $('.Login').click(function(evt) {
      evt.preventDefault();
      <c:if test="${USER_TYPE == 'EMPLOYEE'}">
      document.forms[0].action = '<%=request.getContextPath()%>/acl/employee.htm';
      </c:if>
      <c:if test="${USER_TYPE == 'STUDENT'}">
      document.forms[0].action = '<%=request.getContextPath()%>/acl/student.htm';
      </c:if>
      document.forms[0].submit();
  });
  });
</script>

<form action="">
<input type="hidden" name="USER_TYPE" value="${USER_TYPE}" />
<div id="Problem" class="card">
  <h3>What is your problem statement?</h3>
  <label>
    <input type="radio" name="Problem" value="NewSignUp" />I am new to this and i would like to sign up.
  </label><br/>
  <label>
    <input type="radio" name="Problem" value="FindAccount" />I do not remember if i had signed up to this.
      ( or )
      I have signed up to this but i do not remember anything. Help me find my account.
  </label><br/>
  <label>
    <input type="radio" name="Problem" value="RegStatus" />I would like to know the status of my account.
  </label><br/>
</div>

<div id="SearchByEmail" class="card">
  <h3>Find My Account</h3>
  Enter the email id that may be associated to your account.<br/><br/>
  <input type="text" id="EmailID" name="EmailID" style="width:40%; height:38px; font-size: 14pt;" placeholder="Enter email id"><br/><br/>
</div>

<div id="NewSignupInstructions" class="card">
  <h3>
  <c:if test="${USER_TYPE == 'EMPLOYEE'}">Employee</c:if>
  <c:if test="${USER_TYPE == 'STUDENT'}">Student</c:if>&nbsp;Registration - Instructions</h3>

  <div class="collapse">
    <ul>
      <li>Do not provide your credentials such as Account Number, Password, User id and Card Number. We do not collect such information.</li>
      <li>Fraudulent e-mails contain links of look-alike websites to mislead into entering sensitive financial data.</li>
      <li>Do not respond to pop-up windows asking for your confidential information.</li>
    </ul>
  </div>
  <h3 class="expand">Self-Submit Steps</h3>
  <div class="collapse">
    <ul>
      <li>Fill up the form section-by-section and make sure you fillup all the mandatory columns (marked with <label style="color: red;">*</label>).</li>
      <li>Revisit the details that you have entered and correct them before save.</li>
      <li>SAVE ONLY when you think you are done with the form. Please note that you will not be able to edit the information you entered once you save the form.</li>
      <li>Your form will be submitted for verification.</li>
      <li>You will be notified when your form is approved. This email provides you the necessary information to log into the website.</li>
      <li>Please change your password after your first login for many security reasons.</li>
    </ul>
  </div>
  <h3 class="expand">Please read the instructions below before you start filling up the form.</h3>
  <div class="collapse">
    <ul>
      <li>Avoid uploading group photos, blurred photos or photos with large size. The maximum limit of the photo is 2MB.</li>
      <li>If you report any issues or if you have any concerns, please use 'Have A Question?' link below the page.</li>
    </ul>
  </div>
</div>

<div id="BotFilter" class="card">
  <div class="g-recaptcha" data-sitekey="6LeZRQcUAAAAAN-GN8J5Pw0qv3InG7pgk_4jl8P-"></div>
</div>

<div id="SelfSubmitForm" class="card">
  <h3>Employee/Student Registration</h3>
  Show the Registration Page.
</div>

<div id="SearchAccount_Result" class="card">
  <h3>Search Result</h3>
  This email id is associated with the following  accounts.
  <ul>
    <li>student 1, userid=s1</li>
    <li>student 2, userid=s2</li>
  </ul>

  <p>
  You can now
  <ul>
    <li>Go to the <a href="#" class="Login">Sign In</a> screen, enter username and password to access your account</li>
    <li><a href="<%=request.getContextPath() %>/acl/forgotPassword.htm">Forgot Password</a> screen, if you do not remember your password</li>
    <li>Go <a href="<%=request.getContextPath() %>" class="Login">Back to Home</a> for more options</li>
  </p>
</div>

<div id="RegStatus_Result" class="card">
  <h3>Employee/Student Registration Status</h3>
  You have submitted your form on [date]<br/>
  Your form has not been verified by the approver yet.<br/>
  Your form has been approved on [date]. Please check your email about your user name and password. <br/>

  <p>
  You can now
  <ul>
    <li>Go to the <a href="#" class="Login">Sign In</a> screen, enter username and password to access your account</li>
    <li><a href="<%=request.getContextPath() %>/acl/forgotPassword.htm">Forgot Password</a> screen, if you do not remember your password</li>
    <li>Go <a href="<%=request.getContextPath() %>" class="Login">Back to Home</a> for more options</li>
  </p>
</div>

<div>
    <input type="button" id="StartAgain" value="Start Again" />
    <input type="button" id="Next" value="Next" />
</div>
</form>