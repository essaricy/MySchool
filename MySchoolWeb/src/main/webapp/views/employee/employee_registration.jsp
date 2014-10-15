<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<style>
#EmployeeAccordion p {
  font-size: 0.7em;
  font-weight: bold;
  text-align: left;
}
</style>

<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/scripts/myschool-employee-attributes.js"></script>
<script>
jQuery(document).ready(function() {
  $(this).myAccordion({id: 'EmployeeAccordion'});
  $("#EmployeeAccordion").accordion( "option", "active", 0);

  var destinationUrl = null;
    if ($('#SEARCH_MODE').val() == 'VERIFIED') {
      destinationUrl = '<%=request.getContextPath()%>/employee/launchVerifiedEmployeesSearch.htm';
    } else if ($('#SEARCH_MODE').val() == 'UNVERIFIED') {
      destinationUrl = '<%=request.getContextPath()%>/employee/launchUnverifiedEmployeesSearch.htm';
  }

  $('#add').click(function () {
    document.forms[0].action = '<%=request.getContextPath()%>/employee/launch.htm';
    document.forms[0].submit();
  });

  $('#search').click(function () {
    document.forms[0].action = destinationUrl;
    document.forms[0].submit();
  });

  if ($('#EmployeeId').val() == '0') {
      $('#add').hide();
      $('#uploadImage').attr('title', 'Add Picture');
      $('#print').hide();
  } else {
      $('#uploadImage').attr('title', 'Update Picture');
      if ($('#Verified').val() == 'YES') {
          $('#save_verify').hide();
      }
  }
  $('#add').tooltipster();
  $('#search').tooltipster();
  $('#uploadImage').tooltipster();
  $('#save').tooltipster();
  $('#save_verify').tooltipster();
  $('#print').tooltipster();

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

  jQuery('#save').click(function () {
      saveEmployee(false);
  });

  jQuery('#save_verify').click(function () {
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
        EmployeeData.Verified = 'YES';
    } else {
        EmployeeData.Verified = $('#Verified').val();
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
          showSuccess(message);
          if (verify) {
              $('#Verified').val('YES');
              $('#save_verify').hide();
          }
        } else {
          showSuccess('Data has been updated successfully.');
        }
        var prevEmployeeId = $('#EmployeeId').val();
        // set employee id
        if (typeof(result.ReferenceNumber) != 'undefined' && result.ReferenceNumber != null && result.ReferenceNumber != '') {
            $('#EmployeeId').val(result.ReferenceNumber);
            if (prevEmployeeId == '0') {
              $('#EmployeeNumber').attr('disabled', true);
              $('#LastEmployeeNumber').hide();
              // enable add icon
              $('#add').show();
              // update picture icon
              $('#uploadImage').attr('title', 'Update Picture');
              $('#uploadImage').tooltipster();
              // enable print icon
              $('#print').show();
            }
            prevEmployeeId = result.ReferenceNumber;
        }
      } else {
        var message = result.StatusMessage;
        if (message != null && typeof(message) != 'undefined' && message != '' && message != 'null') {
          showError(message);
        } else {
          showError("Server has sent an unexpected response. Please contact support for assistance.");
        }
      }
      $('#ImageReferenceNumber').val('');
    });
  }
});
</script>

<input type="hidden" id="SEARCH_MODE" name="SEARCH_MODE" value="${SEARCH_MODE}" />
<c:if test="${Employee == null}">
  <input type="hidden" id="EmployeeId" value="0" />
</c:if>
<c:if test="${Employee != null}">
  <input type="hidden" id="EmployeeId" value="${Employee.employeeId}" />
  <c:set var="EmployeeContact" value="${Employee.employeeContact}" />
  <c:set var="EmployeeDocuments" value="${Employee.employeeDocuments}" />
  <c:set var="EmployeeEducations" value="${Employee.employeeDocuments}" />
  <c:set var="EmployeeExperiences" value="${Employee.employeeExperiences}" />
  <c:set var="EmployeePromotions" value="${Employee.employeePromotions}" />
  <c:set var="EmployeeTeachingSubjects" value="${Employee.employeeSubjects}" />
</c:if>

<table cellpadding="2" width="90%" align="center" cellspacing="0" border="0">
  <caption class="dataTableCaption">
    Employee Registration
    <c:if test="${SEARCH_MODE == 'UNVERIFIED'}"> (Portal) </c:if>
  </caption>
  <tr>
    <td colspan="2" align="right" valign="top" style="padding-top: 8px;">
      <input type="hidden" id="ImageReferenceNumber" value="" />
      <c:if test="${Employee == null}">
        <input type="hidden" id="Verified" value="NO" />
      </c:if>
      <c:if test="${Employee != null}">
        <c:if test="${Employee.verified}">
          <input type="hidden" id="Verified" value="YES" />
        </c:if>
        <c:if test="${ ! Employee.verified}">
          <input type="hidden" id="Verified" value="NO" />
        </c:if>
      </c:if>

      <img id="add" src="<%=request.getContextPath()%>/images/icons/add.png" class="iconImage" title="Create Employee" />
      <img id="search" src="<%=request.getContextPath()%>/images/icons/magnifier.png" class="iconImage" title="Search Employees" />
      |
      <img id="uploadImage" src="<%=request.getContextPath()%>/images/icons/picture_edit.png" class="iconImage" title="" />
      <img id="save" src="<%=request.getContextPath()%>/images/icons/save.png" class="iconImage" title="Save Employee" />
      <img id="save_verify" src="<%=request.getContextPath()%>/images/icons/save_accept.png" class="iconImage" title="Save & Verify Employee" />
      <img id="print" src="<%=request.getContextPath()%>/images/icons/print.png" class="iconImage" title="Print Employee" />
    </td>
  </tr>
  <tr>
    <td width="15%" valign="top">
      <!-- Employee Photo -->
      <c:if test="${Employee == null}">
      <table cellpadding="5" cellspacing="0" border="0" width="100%" height="100%" class="formTable">
        <tr>
          <td align="center">
            <img id="employeeImage" name="employeeImage" src="<%=request.getContextPath()%>/images/icons/no-image.jpg" border="1" width="150px" height="180px"/>
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
</table>