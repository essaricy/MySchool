<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<script src='https://www.google.com/recaptcha/api.js'></script>
<style>
#EmployeeRegistrationTabs {
  font-size: 1.1em;
}
#PostSubmitNotes {
  color: green;
}
</style>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/widgets/jquery.waitMe/waitMe.css" />
<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/widgets/jquery.waitMe/waitMe.js"></script> 
<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/scripts/myschool-employee-attributes.js"></script>
<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/scripts/myschool-ajax.js"></script>
<script>
jQuery(document).ready(function() {
  $('#EmployeeRegistrationTabs').tabs({id: 'EmployeeRegistrationTabs'});
  $("#EmployeeRegistrationTabs").tabs("option", "active", 0);
  $('#LastEmploymentNumber').hide();
  $('#PostSubmitNotes').hide();

  var uploader = new plupload.Uploader({
    // General settings
    runtimes : 'html5',
    multipart: true,
    browse_button: 'uploadImage',
    file_data_name: 'uploadFile',
    multipart_params: {multiUploadId:'', uploadName: 'EMPLOYEE'},
    url: '<%=request.getContextPath()%>/upload/uploadImage.htm',
    multi_selection: false,
    max_file_size : '5242880b',
    chunk_size : '5242880b',
    unique_names : true,
    // Resize images on clientside if we can
    //resize : {width : 320, height : 240, quality : 90},
    // Specify what files to browse for
    filters : [
      {title : "Images", extensions : "jpg,gif,png"}
    ]
  });
  uploader.init();
  uploader.bind('FilesAdded', function (up, files) {
    //setTimeout(function () { uploader.start(); }, 1000);
    wait();
    uploader.start();
  });
  uploader.bind('FileUploaded', function(up, file, result) {
    var response = $.parseJSON(result.response);
    if (response.Successful) {
      notifySuccess('Image has been successfully uploaded and will be updated when saved.');
      // Replace image with some fading effect
      var employeeImage = $('#employeeImage');
      var originalImageUrl = '<%=request.getContextPath()%>/image/getEvanescentImage.htm?type=employee&imageSize=ORIGINAL&contentId=' + response.ReferenceNumber + '&sid=' + new Date().getTime();
      var passportImageUrl = '<%=request.getContextPath()%>/image/getEvanescentImage.htm?type=employee&imageSize=PASSPORT&contentId=' + response.ReferenceNumber + '&sid=' + new Date().getTime();
      employeeImage.fadeOut(1000, function () {
        employeeImage.attr('src', passportImageUrl);
        // Magnify image on click
        employeeImage.click(function() {$.magnificPopup.open({ items: { src: originalImageUrl }, type: 'image' })});
        employeeImage.fadeIn(3000);
      });
      $('#ImageReferenceNumber').val(response.ReferenceNumber);
    } else {
      attendError('Unable to upload the image now. Please try again.');
    }
    unwait();
  });

  jQuery('#SaveEmployeeData').click(function () {
    var verificationCode = $('#g-recaptcha-response').val();
    if (verificationCode == '') {
      notifyError('Click on "I\'m not a robot"');
      return false;
    }

    if (!$('#Agree').is(':checked')) {
      notifyError('Please agree to the declaration.');
      return false;
    }
    interactConfirm('Please ensure that you have entered correct information.<br /> You cannot change the information after save is successful.', saveEmployee);
  });

  function saveEmployee() {
    var EmployeeData = getPersonalDetails();
    var EmploymentData = getEmploymentDetails();
    EmployeeData.EmployeeId = 0;
    EmployeeData.Verified = 'NO';
    EmployeeData.EmployeeNumber = EmploymentData.EmployeeNumber;
    EmployeeData.EmployedAtBranch = EmploymentData.EmployedAtBranch;
    EmployeeData.ImageName=$('#ImageReferenceNumber').val();
    EmployeeData.DesignationId = EmploymentData.DesignationId;
    EmployeeData.EmploymentStatusId = EmploymentData.EmploymentStatusId;
    EmployeeData.EmploymentStartDate = EmploymentData.EmploymentStartDate;
    EmployeeData.EmploymentEndDate = EmploymentData.EmploymentEndDate;
    EmployeeData.ReportingTo = EmploymentData.ReportingTo;
    EmployeeData.Remarks = EmploymentData.Remarks;
    EmployeeData.EmployeeContact = getContactDetails();
    EmployeeData.EmployeeDocuments = getEmployeeDocuments();
    EmployeeData.EmployeeEducations = getEmployeeEducations();
    EmployeeData.EmployeeExperiences = getEmployeeExperiences();
    EmployeeData.EmployeePromotions = getEmployeePromotions();
    EmployeeData.EmployeeTeachingSubjects = getEmployeeTeachingSubjects();
    //alert(JSON.stringify(EmployeeData));

    var formData = {
      EmployeeData: JSON.stringify(EmployeeData),
      CaptchaResponse: $('#g-recaptcha-response').val()
    }
    sendAjax("<%=request.getContextPath()%>/portal-employee/submitEmployee.htm", formData, postSuccess, null);
  }

  function postSuccess() {
    // Remove all the content that has an action associated with
    $("#EmployeeRegistrationTabs").find("input,button,textarea,select").attr("disabled", "disabled");
    $(".iconImage,.ui-datepicker-trigger,.g-recaptcha,#uploadImage,#AgreeStatement,#SaveEmployeeData").fadeOut(1000);
    $('.chosen-select').trigger("chosen:updated");
    $('#PostSubmitNotes').fadeIn(1000);
  }
});
</script>

<input type="hidden" id="Verified" value="NO" />
<input type="hidden" id="EmployeeId" value="0" />
<table class="formTable_Container">
  <caption>
    Employee Registration (Self-Submit Service)
  </caption>
  <tr>
    <td colspan="2" align="center" valign="top" style="padding-top: 8px;">
      <div id="Socialize"></div>
      <p/>
      <!-- Employee instructions page -->
      <%@ include file="/views/portal/portal_employee_instructions.jsp" %>
    </td>
  </tr>

  <tr id="EmployeFormRow">
    <td width="15%" valign="top">
      <input type="hidden" id="ImageReferenceNumber" value="" />
      <!-- Employee Photo -->
      <c:if test="${Employee == null}">
      <table class="formTable_Data">
        <tr>
          <td align="center">
            <img id="employeeImage" name="employeeImage" src="<%=request.getContextPath()%>/images/icons/no-image-yet.png" width="150px" height="180px" class="no-image"/>
          </td>
        </tr>
        <tr>
          <td align="center">
            <a href="#" id="uploadImage">Upload Photo</a>
          </td>
        </tr>
      </table>
      </c:if>
      <c:if test="${Employee != null}">
      <table class="formTable_Data">
        <tr>
          <td align="center">
            <c:if test="${Employee.imageAccess == null || Employee.imageAccess.passportLink == null}">
              <img id="employeeImage" name="employeeImage" src="<%=request.getContextPath()%>/images/icons/no-image-yet.png" width="150px" height="180px" class="no-image"/>
            </c:if>
            <c:if test="${Employee.imageAccess != null && Employee.imageAccess.passportLink != null}">
              <img id="employeeImage" name="employeeImage" src="${Employee.imageAccess.passportLink}" border="1" width="150px" height="180px"/>
            </c:if>

          </td>
        </tr>
      </table>
      </c:if>
    </td>
    <td width="85%" valign="top">
      <div id="EmployeeRegistrationTabs">
        <ul>
          <li><a href="#EmploymentDetailsTab">Employment</a></li>
          <li><a href="#EmployeePersonalDetailsTab">Personal</a></li>
          <li><a href="#EmployeeFamilyDetailsTab">Contacts</a></li>
          <li><a href="#EmployeeDocumentDetailsTab">Documents</a></li>
          <li><a href="#EmployeeEducationDetailsTab">Education</a></li>
          <li><a href="#EmployeeExperienceDetailsTab">Experience</a></li>
          <li><a href="#EmployeePromotionDetailsTab">Promotions</a></li>
          <li><a href="#EmployeeTeachingSubjectsDetailsTab">Teaching Subjects</a></li>
        </ul>

        <div id="EmploymentDetailsTab"><%@ include file="/views/employee/maintain_employment_details.jsp" %></div>
        <div id="EmployeePersonalDetailsTab"><%@ include file="/views/employee/maintain_employee_personal_details.jsp" %></div>
        <div id="EmployeeFamilyDetailsTab"><%@ include file="/views/employee/maintain_employee_contact_details.jsp" %></div>
        <div id="EmployeeDocumentDetailsTab"><%@ include file="/views/employee/maintain_employee_document_details.jsp" %></div>
        <div id="EmployeeEducationDetailsTab"><%@ include file="/views/employee/maintain_employee_education_details.jsp" %></div>
        <div id="EmployeeExperienceDetailsTab"><%@ include file="/views/employee/maintain_employee_experience_details.jsp" %></div>
        <div id="EmployeePromotionDetailsTab"><%@ include file="/views/employee/maintain_employee_promotion_details.jsp" %></div>
        <div id="EmployeeTeachingSubjectsDetailsTab"><%@ include file="/views/employee/maintain_employee_teaching_subjects.jsp" %></div>
      </div>
    </td>
  </tr>
    <td width="15%" valign="top">&nbsp;</td>
    <td width="85%" valign="top">
      <div id="EmployeeFormActionButtons" style="margin-top: 10px; margin-bottom: 40px; text-align: center;">
        <div class="g-recaptcha" data-sitekey="${MYSCHOOL_PROFILE.captchaKey}"></div><br/>
        <table>
          <tr>
            <td align="left" colspan="2">
              <input type="checkbox" id="Agree">&nbsp;I, hereby, certify that the information entered is true to the best of my knowledge and belief.
            </td>
          </tr>
          <tr>
            <td align="center" colspan="2">
              <input type="button" id="SaveEmployeeData" value="Save" /><br/>
            </td>
          </tr>
        </table>
      </div>
    </td>
  </tr>
</table>
<form action="">
<input type="hidden" name="UserType" value="EMPLOYEE" />
<div id="PostSubmitNotes">
  <p>
    Thank you for using Employee Self-Submit Service.<br/>
    Your form has been successfully submitted and you will be notified through email when it is approved.<br/>
    You can always enquire the status of your application from <a href="#"
        onclick="document.forms[0].action='<%=request.getContextPath() %>/acl/assistance.htm'">assistance</a> page.<br/>
  </p>
  <p>
    <a href="<%=request.getContextPath() %>">Back to Home</a>
  </p>
</div>
<br/>
</form>
