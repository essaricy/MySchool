<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<style>
#EmployeeRegistrationTabs {
    font-size: 1.1em;
}
</style>

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/widgets/jquery.magnific-popup/magnific-popup.css" />
<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/widgets/jquery.magnific-popup/jquery.magnific-popup.min.js"></script>
<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/scripts/myschool-employee-attributes.js"></script>
<script>
jQuery(document).ready(function() {
  $('#EmployeeRegistrationTabs').tabs({id: 'EmployeeRegistrationTabs'});
  $("#EmployeeRegistrationTabs").tabs("option", "active", 0);

  var searchUrl = null;
  if ($('#RECORD_STATUS').val() == 'VERIFIED') {
      searchUrl='<%=request.getContextPath()%>/employee/launchVerifiedEmployeesSearch.htm';
  } else if ($('#RECORD_STATUS').val() == 'UNVERIFIED') {
      searchUrl='<%=request.getContextPath()%>/employee/launchUnverifiedEmployeesSearch.htm';
  }

  $('#AddEmployee').click(function () {
    document.forms[0].action='<%=request.getContextPath()%>/employee/launch.htm';
    document.forms[0].submit();
  });

  $('#SearchEmployees').click(function () {
    document.forms[0].action=searchUrl;
    document.forms[0].submit();
  });

  $('#ShowPreviousEmployee').click(function () {
    document.forms[0].action='<%=request.getContextPath()%>/employee/getPreviousEmployee.htm?EmployeeNumber=' + $('#EmployeeNumber').val() + "&Type=" + $('#RECORD_STATUS').val();;
    document.forms[0].submit();
  });

  $('#ShowNextEmployee').click(function () {
    document.forms[0].action='<%=request.getContextPath()%>/employee/getNextEmployee.htm?EmployeeNumber=' + $('#EmployeeNumber').val() + "&Type=" + $('#RECORD_STATUS').val();;
    document.forms[0].submit();
  });

  if ($('#EmployeeId').val() == '0') {
      $('#AddEmployee').hide();
      $('#ShowPreviousEmployee').hide();
      $('#ShowNextEmployee').hide();
      $('#uploadImage').attr('title', 'Add Picture');
      $('#PrintEmployeeData').hide();
  } else {
      $('#uploadImage').attr('title', 'Update Picture');
      if ($('#Verified').val() == 'YES') {
          $('#VerifyEmployeeData').hide();
      }
  }
  $('#AddEmployee').tooltipster();
  $('#SearchEmployees').tooltipster();
  $('#ShowNextEmployee').tooltipster();
  $('#ShowPreviousEmployee').tooltipster();
  $('#uploadImage').tooltipster();
  $('#SaveEmployeeData').tooltipster();
  $('#VerifyEmployeeData').tooltipster();
  $('#PrintEmployeeData').tooltipster();

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
  });

  jQuery('#LastEmployeeNumber').click(function () {
    jQuery.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/employee/getLastEmployeeNumber.htm",
      data: {
        sid: new Date().getTime()
      },
      context: this
    }).done(function(result) {
      if (result.successful) {
        jQuery('#EmployeeNumber').val(result.value);
      }
    });
  });

  jQuery('#SaveEmployeeData').click(function () {
      saveEmployee(false);
  });

  jQuery('#VerifyEmployeeData').click(function () {
      saveEmployee(true);
  });

  function saveEmployee (verify) {
    var EmployeeData = getPersonalDetails();
    var EmploymentData = getEmploymentDetails();
    EmployeeData.EmployeeId = $('#EmployeeId').val();
    //EmployeeData.Verified = $('#Verified').val();
    EmployeeData.EmployeeNumber = EmploymentData.EmployeeNumber;
    EmployeeData.EmployedAtBranch = EmploymentData.EmployedAtBranch;
    EmployeeData.ImageName=$('#ImageReferenceNumber').val();
    EmployeeData.DesignationId = EmploymentData.DesignationId;
    EmployeeData.EmploymentStatusId = EmploymentData.EmploymentStatusId;
    EmployeeData.EmploymentStartDate = EmploymentData.EmploymentStartDate;
    EmployeeData.EmploymentEndDate = EmploymentData.EmploymentEndDate;
    EmployeeData.ReportingTo = EmploymentData.ReportingTo;
    EmployeeData.Remarks = EmploymentData.Remarks;
    if (verify) {
        //EmployeeData.Verified = 'YES';
        EmployeeData.Verify = 'YES';
    } else {
        EmployeeData.Verify = 'NO';
        //EmployeeData.Verified = $('#Verified').val();
    }

    EmployeeData.EmployeeContact = getContactDetails();
    EmployeeData.EmployeeDocuments = getEmployeeDocuments();
    EmployeeData.EmployeeEducations = getEmployeeEducations();
    EmployeeData.EmployeeExperiences = getEmployeeExperiences();
    EmployeeData.EmployeePromotions = getEmployeePromotions();
    EmployeeData.EmployeeTeachingSubjects = getEmployeeTeachingSubjects();
    //alert(JSON.stringify(EmployeeData.EmployeeTeachingSubjects));

    jQuery.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/employee/registerEmployee.htm",
      data: {
        EmployeeData: JSON.stringify(EmployeeData)
      },
      context: this
    }).done(function(result) {
      if (result.Successful) {
        var message = result.StatusMessage;
        if (message != null && typeof(message) != 'undefined' && message != '' && message != 'null') {
          notifySuccess(message);
          if (verify) {
              $('#Verified').val('YES');
              $('#VerifyEmployeeData').hide();
          }
        } else {
          notifySuccess('Data has been updated successfully.');
        }
        var prevEmployeeId = $('#EmployeeId').val();
        // set employee id
        if (typeof(result.ReferenceNumber) != 'undefined' && result.ReferenceNumber != null && result.ReferenceNumber != '') {
            $('#EmployeeId').val(result.ReferenceNumber);
            if (prevEmployeeId == '0') {
              $('#EmployeeNumber').attr('disabled', true);
              $('#LastEmployeeNumber').hide();
              // enable add icon
              $('#AddEmployee').show();
              // update picture icon
              $('#uploadImage').attr('title', 'Update Picture');
              $('#uploadImage').tooltipster();
              // enable print icon
              $('#PrintEmployeeData').show();
            }
            prevEmployeeId = result.ReferenceNumber;
        }
        var Reference = result.Reference;
        if (Reference != null && typeof(Reference) != 'undefined') {
          var ImageAccess = Reference.ImageAccess;
          if (ImageAccess != null && typeof(ImageAccess) != 'undefined') {
            var employeeImage = $('#employeeImage');
            console.log('ImageAccess.PassportLink=' + ImageAccess.PassportLink);
            employeeImage.fadeOut(1000, function () {
              employeeImage.attr('src', ImageAccess.PassportLink);
              employeeImage.fadeIn(3000);
            });
          }
        }
      } else {
        var message = result.StatusMessage;
        if (message != null && typeof(message) != 'undefined' && message != '' && message != 'null') {
          attendError(message);
        } else {
          attendError("Server has sent an unexpected response. Please contact support for assistance.");
        }
      }
      $('#ImageReferenceNumber').val('');
    });
  }
});
</script>

<input type="hidden" id="RECORD_STATUS" name="RECORD_STATUS" value="${RECORD_STATUS}" />
<c:if test="${Employee == null}">
  <input type="hidden" id="EmployeeId" value="0" />
  <input type="hidden" id="Verified" value="NO" />
</c:if>
<c:if test="${Employee != null}">
  <input type="hidden" id="EmployeeId" value="${Employee.employeeId}" />
  <c:set var="EmployeeContact" value="${Employee.employeeContact}" />
  <c:set var="EmployeeDocuments" value="${Employee.employeeDocuments}" />
  <c:set var="EmployeeEducations" value="${Employee.employeeDocuments}" />
  <c:set var="EmployeeExperiences" value="${Employee.employeeExperiences}" />
  <c:set var="EmployeePromotions" value="${Employee.employeePromotions}" />
  <c:set var="EmployeeTeachingSubjects" value="${Employee.employeeSubjects}" />
  <c:if test="${Employee.verified}">
    <input type="hidden" id="Verified" value="YES" />
  </c:if>
  <c:if test="${ ! Employee.verified}">
    <input type="hidden" id="Verified" value="NO" />
  </c:if>
</c:if>

<table class="formTable_Container">
  <caption>Employee Registration<c:if test="${RECORD_STATUS == 'UNVERIFIED'}"> (Portal) </c:if></caption>
  <tr>
    <td colspan="2" align="right" valign="middle" style="padding-top: 8px;">
      <input type="hidden" id="ImageReferenceNumber" value="" />
      <img id="AddEmployee" src="<%=request.getContextPath()%>/images/icons/add.png" class="iconImage" title="Create Employee" />
      <img id="SearchEmployees" src="<%=request.getContextPath()%>/images/icons/magnifier.png" class="iconImage" title="Search Employees" />
      <img id="ShowPreviousEmployee" src="<%=request.getContextPath()%>/images/icons/back.png" class="iconImage" title="Previous Employee" />
      <img id="ShowNextEmployee" src="<%=request.getContextPath()%>/images/icons/forward.png" class="iconImage" title="Next Employee" />
      <img id="uploadImage" src="<%=request.getContextPath()%>/images/icons/picture_edit.png" class="iconImage" title="" />
      <img id="SaveEmployeeData" src="<%=request.getContextPath()%>/images/icons/save.png" class="iconImage" title="Save Employee" />
      <img id="VerifyEmployeeData" src="<%=request.getContextPath()%>/images/icons/save_accept.png" class="iconImage" title="Save & Verify Employee" />
      <img id="PrintEmployeeData" src="<%=request.getContextPath()%>/images/icons/print.png" class="iconImage" title="Print Employee" />
    </td>
  </tr>
  <tr>
    <td width="15%" valign="top">
      <!-- Employee Photo -->
      <c:if test="${Employee == null}">
      <table class="formTable_Data">
        <tr>
          <td align="center">
            <img id="employeeImage" name="employeeImage" src="<%=request.getContextPath()%>/images/icons/no-image-yet.png" width="150px" height="180px" class="no-image"/>
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
</table>
