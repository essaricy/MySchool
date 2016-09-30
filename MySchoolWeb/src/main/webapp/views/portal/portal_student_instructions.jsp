<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/widgets/jquery.expand.collapse/css/example.css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/jquery.expand.collapse/scripts/expand.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/jquery.countdown360/src/jquery.countdown360.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/myschool-timer.js"></script>
<script>
jQuery(document).ready(function() {
  $('#StudentFormRow').hide();
  $('#StudentFormActionButtons').hide();

  $("#SSSInstructions h3.expand").toggler();
  $("#SSSInstructions h3.expand").each(function() {
      $(this).click();
  });

  $('#InstructionsRead').click(function() {
    $(this).hide();
    $("#SSSInstructions h3.expand").each(function() {
      $(this).click();
    });

    $('#StudentFormRow').show(1000);
    $('#StudentFormActionButtons').show();
  });

    $("#TimeToReadInstractions").fadeOut(1000, function() {
      $('#InstructionsRead').fadeIn(500);
    });
  });
  $('#InstructionsRead').hide();
});
</script>

<div id="SSSInstructionsWrapper" class="expand-collapse-wrapper"> 
  <div id="SSSInstructions" class="expand-collapse-content">  
    <div class="expand-collapse">
      <h3 class="expand">Student Alert!!! [Beware of phishing]</h3>
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
  </div>
</div>
<br />
<div id="TimeToReadInstractions"></div><input type="button" id="InstructionsRead" value="I have read the instructions" />
<br />
