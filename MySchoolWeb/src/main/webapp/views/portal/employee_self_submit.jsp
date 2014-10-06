<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<style>
#EmployeeAccordion p {
  font-size: 0.8em;
  font-weight: bold;
  text-align: left;
}
</style>

<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/scripts/myschool-employee-attributes.js"></script>
<script>
jQuery(document).ready(function() {
  $(this).myAccordion({id: 'EmployeeAccordion'});
  $("#EmployeeAccordion").accordion( "option", "active", 0);
  $('#LastEmployeeNumber').hide();

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
    setTimeout(function () { uploader.start(); }, 1000);
    uploader.start();
  });
  uploader.bind('FileUploaded', function(up, file, result) {
    var response = $.parseJSON(result.response);
    if (response.Successful) {
      showSuccess('Image has been successfully uploaded and will be updated when saved.');
      // Replace image with some fading effect
      var employeeImage = $('#employeeImage');
      var employeeImageUrl = '<%=request.getContextPath()%>/image/getImage.htm?type=employee&imageSize=ORIGINAL&contentId=' + response.ReferenceNumber + '&sid=' + new Date().getTime();
      employeeImage.fadeOut(1000, function () {
        employeeImage.attr('src', employeeImageUrl);
        // Magnify image on click
        employeeImage.click(function() {$.magnificPopup.open({ items: { src: employeeImageUrl }, type: 'image' })});
        employeeImage.fadeIn(3000);
      });
      $('#ImageReferenceNumber').val(response.ReferenceNumber);
    } else {
      showError('Unable to upload the image now. Please try again.');
    }
  });

  jQuery('#save').click(function () {
    var Captcha_UserFeed = $('#Captcha_UserFeed').val();
    if (Captcha_UserFeed == '') {
      warn_ac('Please type the letters in the image into the textbox and prove that you are not a Robot!!!');
      return false;
    }
    if (!$('#Agree').is(':checked')) {
      warn_ac('Please agree to the declaration.');
      return false;
    }
    confirm('Please ensure that you have entered correct information.<br /> You cannot change the information after save is successful.', confirmSave);
  });

  function confirmSave(result) {
      if (result == "Yes") {
          saveEmployee();
      }
  }

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

    jQuery.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/portal-employee/submitEmployee.htm",
      data: {
        EmployeeData: JSON.stringify(EmployeeData),
        Captcha_UserFeed: $('#Captcha_UserFeed').val()
      },
      context: this
    }).done(function(result) {
      if (result.Successful) {
        info_cb(result.StatusMessage, redirectToLogin);
      } else {
        var message = result.StatusMessage;
        if (message != null && typeof(message) != 'undefined' && message != '' && message != 'null') {
          showError(message);
        } else {
          error("Something really went wrong and we apologize for the inconvenience caused!!!");
        }
      }
      $('#Captcha_ReloadImage').click();
    });
  }

  function redirectToLogin() {
    window.location = '<%=request.getContextPath()%>';
  }
  $(document).social({
    title: '${ORGANIZATION_PROFILE.organizationName} - Employee Self Submit (ESS)'
  });

});
</script>

<input type="hidden" id="Verified" value="NO" />
<input type="hidden" id="EmployeeId" value="0" />
<table cellpadding="2" width="90%" align="center" cellspacing="0" border="0">
  <caption class="dataTableCaption">
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
      <table cellpadding="5" cellspacing="0" border="0" width="100%" height="100%" class="formTable">
        <tr>
          <td align="center">
            <img id="employeeImage" name="employeeImage" src="<%=request.getContextPath()%>/images/icons/no-image.jpg" border="1" width="150px" height="180px"/>
          </td>
        </tr>
        <tr>
          <td align="center">
            <a href="#" id="uploadImage" class="formLink">Upload Photo</a>
          </td>
        </tr>
      </table>
      </c:if>
      <c:if test="${Employee != null}">
      <table cellpadding="5" cellspacing="0" border="0" width="100%" height="100%" class="formTable">
        <tr>
          <td align="center">
            <img id="employeeImage" name="employeeImage" src="<%=request.getContextPath()%>/image/getImage.htm?type=employee&imageSize=ORIGINAL&contentId=${Employee.employeeNumber}&sid=<%= new java.util.Date().getTime()%>" border="1" width="150px" height="180px"/>
          </td>
        </tr>
      </table>
      </c:if>
    </td>
    <td width="85%" valign="top">
      <div id="EmployeeAccordion">
        <p class="title">Employment Details</p>
        <div><%@ include file="/views/employee/maintain_employment_details.jsp" %></div>
        <p class="title">Personal Details</p>
        <div><%@ include file="/views/employee/maintain_employee_personal_details.jsp" %></div>
        <p class="title">Employee Contact Details</p>
        <div><%@ include file="/views/employee/maintain_employee_contact_details.jsp" %></div>
        <p class="title">Employee Documents</p>
        <div><%@ include file="/views/employee/maintain_employee_document_details.jsp" %></div>
        <p class="title">Employee Education</p>
        <div><%@ include file="/views/employee/maintain_employee_education_details.jsp" %></div>
        <p class="title">Employee Experience</p>
        <div><%@ include file="/views/employee/maintain_employee_experience_details.jsp" %></div>
        <p class="title">Employee Promotions</p>
        <div><%@ include file="/views/employee/maintain_employee_promotion_details.jsp" %></div>
        <p class="title">Employee Teaching Subjects</p>
        <div><%@ include file="/views/employee/maintain_employee_teaching_subjects.jsp" %></div>
      </div>
    </td>
  </tr>
    <td width="15%" valign="top">&nbsp;</td>
    <td width="85%" valign="top">
      <div id="EmployeeFormActionButtons" style="margin-top: 10px; margin-bottom: 40px; text-align: center;">
        <%@ include file="/views/common/captcha.jsp" %>
        <table>
          <tr>
            <td align="left" colspan="2">
              <input type="checkbox" id="Agree">&nbsp;I, hereby, certify that the information entered is true to the best of my knowledge and belief.
            </td>
          </tr>
          <tr>
            <td align="center" colspan="2">
              <input type="button" class="formButton" id="save" value="Save" /><br/>
            </td>
          </tr>
        </table>
      </div>
    </td>
  </tr>
</table>
