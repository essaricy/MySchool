<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<style>
#StudentRegistrationTabs {
    font-size: 1.1em;
}
</style>
<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/scripts/myschool-student-attributes.js"></script>
<script>
jQuery(document).ready(function() {
  $('#StudentRegistrationTabs').tabs({id: 'StudentRegistrationTabs'});
  $("#StudentRegistrationTabs").tabs("option", "active", 0);

  var searchUrl = null;
  if ($('#RECORD_STATUS').val() == 'VERIFIED') {
    searchUrl = '<%=request.getContextPath()%>/student/launchVerifiedStudentsSearch.htm';
  } else if ($('#RECORD_STATUS').val() == 'UNVERIFIED') {
    searchUrl = '<%=request.getContextPath()%>/student/launchUnverifiedStudentsSearch.htm';
  }

  $('#AddStudent').click(function () {
    document.forms[0].action='<%=request.getContextPath()%>/student/launch.htm';
    document.forms[0].submit();
  });

  $('#SearchStudents').click(function () {
    document.forms[0].action=searchUrl;
    document.forms[0].submit();
  });

  $('#ShowPreviousStudent').click(function () {
    document.forms[0].action='<%=request.getContextPath()%>/student/getPreviousStudent.htm?AdmissionNumber=' + $('#AdmissionNumber').val() + "&Type=" + $('#RECORD_STATUS').val();;
    document.forms[0].submit();
  });

  $('#ShowNextStudent').click(function () {
    document.forms[0].action='<%=request.getContextPath()%>/student/getNextStudent.htm?AdmissionNumber=' + $('#AdmissionNumber').val() + "&Type=" + $('#RECORD_STATUS').val();;
    document.forms[0].submit();
  });

  if ($('#StudentId').val() == '0') {
      $('#AddStudent').hide();
      $('#ShowPreviousStudent').hide();
      $('#ShowNextStudent').hide();
      $('#uploadImage').attr('title', 'Add Picture');
      $('#PrintStudentData').hide();
  } else {
      $('#uploadImage').attr('title', 'Update Picture');
      if ($('#Verified').val() == 'YES') {
          $('#VerifyStudentData').hide();
      }
  }
  $('#AddStudent').tooltipster();
  $('#SearchStudents').tooltipster();
  $('#ShowNextStudent').tooltipster();
  $('#ShowPreviousStudent').tooltipster();
  $('#uploadImage').tooltipster();
  $('#SaveStudentData').tooltipster();
  $('#VerifyStudentData').tooltipster();
  $('#PrintStudentData').tooltipster();

  var uploader = new plupload.Uploader({
    // General settings
    runtimes : 'html5',
    multipart: true,
    browse_button: 'uploadImage',
    file_data_name: 'uploadFile',
    multipart_params: {multiUploadId:'', uploadName: 'STUDENT'},
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
      var studentImage = $('#studentImage');
      var studentImageUrl = '<%=request.getContextPath()%>/image/getImage.htm?type=student&imageSize=ORIGINAL&contentId=' + response.ReferenceNumber + '&sid=' + new Date().getTime();
      studentImage.fadeOut(1000, function () {
        studentImage.attr('src', studentImageUrl);
        // Magnify image on click
        studentImage.click(function() {$.magnificPopup.open({ items: { src: studentImageUrl }, type: 'image' })});
        studentImage.fadeIn(3000);
      });
      $('#ImageReferenceNumber').val(response.ReferenceNumber);
    } else {
      attendError('Unable to upload the image now. Please try again.');
    }
  });

  jQuery('#LastAdmissionNumber').click(function () {
    jQuery.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/student/getLastAdmissionNumber.htm",
      data: {
        sid: new Date().getTime()
      },
      context: this
    }).done(function(result) {
      if (result.successful) {
        jQuery('#AdmissionNumber').val(result.value);
      }
    });
  });

  jQuery('#SaveStudentData').click(function () {
      saveStudent(false);
  });

  jQuery('#VerifyStudentData').click(function () {
      saveStudent(true);
  });

  function saveStudent(verify) {
    var StudentData = new Object();
    StudentData.StudentId = $('#StudentId').val();
    StudentData.ImageName=$('#ImageReferenceNumber').val();
    StudentData.AdmissionDetails=getAdmissionDetails();
    StudentData.PersonalDetails=getPersonalDetails();
    StudentData.FamilyMemberDetails=getStudentFamilyMembers();
    StudentData.DocumentDetails=getStudentDocuments();
    //alert(JSON.stringify(StudentData)); return;
    if (verify) {
        StudentData.Verified = 'YES';
    } else {
        StudentData.Verified = $('#Verified').val();
    }

    jQuery.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/student/registerStudent.htm",
      data: {
        StudentData: JSON.stringify(StudentData)
      },
      context: this
    }).done(function(result) {
      if (result.Successful) {
        var message = result.StatusMessage;
        if (message != null && typeof(message) != 'undefined' && message != '' && message != 'null') {
          attendSuccess(message);
          if (verify) {
              $('#Verified').val('YES');
              $('#VerifyStudentData').hide();
          }
        } else {
          attendSuccess('Data has been updated successfully.');
        }
        var prevStudentId = $('#StudentId').val();
        // set Student id
        if (typeof(result.ReferenceNumber) != 'undefined' && result.ReferenceNumber != null && result.ReferenceNumber != '') {
            $('#StudentId').val(result.ReferenceNumber);
            if (prevStudentId == '0') {
              $('#AdmissionNumber').attr('disabled', true);
              $('#LastAdmissionNumber').hide();
              // enable add icon
              $('#AddStudent').show();
              // update picture icon
              $('#uploadImage').attr('title', 'Update Picture');
              $('#uploadImage').tooltipster();
              // enable print icon
              $('#PrintStudentData').show();
            }
            prevStudentId = result.ReferenceNumber;
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
<c:if test="${Student == null}">
  <input type="hidden" id="StudentId" value="0" />
  <input type="hidden" id="Verified" value="NO" />
</c:if>
<c:if test="${Student != null}">
  <input type="hidden" id="StudentId" value="${Student.studentId}" />
  <c:set var="StudentPersonalDetails" value="${Student.personalDetails}" />
  <c:set var="StudentDocuments" value="${Student.documentsSubmitted}" />
  <c:set var="StudentFamilyMembers" value="${Student.familyMembers}" />

  <c:if test="${Student.verified}">
    <input type="hidden" id="Verified" value="YES" />
  </c:if>
  <c:if test="${ ! Student.verified}">
    <input type="hidden" id="Verified" value="NO" />
  </c:if>
</c:if>

<table class="formTable_Data">
  <caption>Student Registration <c:if test="${RECORD_STATUS == 'UNVERIFIED'}"> (Portal) </c:if></caption>
  <tr>
    <td colspan="2" align="right" valign="top" style="padding-top: 8px;">
      <input type="hidden" id="ImageReferenceNumber" value="" />
      <img id="AddStudent" src="<%=request.getContextPath()%>/images/icons/add.png" class="iconImage" title="Create Student" />
      <img id="SearchStudents" src="<%=request.getContextPath()%>/images/icons/magnifier.png" class="iconImage" title="Search Students" />
      <img id="ShowPreviousStudent" src="<%=request.getContextPath()%>/images/icons/back.png" class="iconImage" title="Previous Student" />
      <img id="ShowNextStudent" src="<%=request.getContextPath()%>/images/icons/forward.png" class="iconImage" title="Next Student" />
      <img id="uploadImage" src="<%=request.getContextPath()%>/images/icons/picture_edit.png" class="iconImage" title="" />
      <img id="SaveStudentData" src="<%=request.getContextPath()%>/images/icons/save.png" class="iconImage" title="Save Student" />
      <img id="VerifyStudentData" src="<%=request.getContextPath()%>/images/icons/save_accept.png" class="iconImage" title="Save & Verify Student" />
      <img id="PrintStudentData" src="<%=request.getContextPath()%>/images/icons/print.png" class="iconImage" title="Print Student" />
    </td>
  </tr>
  <tr>
    <td width="15%" valign="top">
      <!-- Student Photo -->
      <c:if test="${Student == null}">
      <table cellpadding="5" cellspacing="0" border="0" width="100%" height="100%" class="formTable">
        <tr>
          <td align="center">
            <img id="studentImage" name="studentImage" src="<%=request.getContextPath()%>/images/icons/no-image-yet.png" width="150px" height="180px" class="no-image"/>
          </td>
        </tr>
      </table>
      </c:if>
      <c:if test="${Student != null}">
      <table cellpadding="5" cellspacing="0" border="0" width="100%" height="100%" class="formTable">
        <tr>
          <td align="center">
            <c:if test="${Student.imageAccess == null || Student.imageAccess.passportLink == null}">
              <img id="studentImage" name="studentImage" src="<%=request.getContextPath()%>/images/icons/no-image-yet.png" width="150px" height="180px" class="no-image"/>
            </c:if>
            <c:if test="${Student.imageAccess != null && Student.imageAccess.passportLink != null}">
              <img id="studentImage" name="studentImage" src="${Student.imageAccess.passportLink}" border="1" width="150px" height="180px"/>
            </c:if>
          </td>
        </tr>
      </table>
      </c:if>
    </td>
    <td width="85%" valign="top">
      <div id="StudentRegistrationTabs">
        <ul>
          <li><a href="#StudentAdmissionDetailsTab">Admission</a></li>
          <li><a href="#StudentPersonalDetailsTab">Personal</a></li>
          <li><a href="#StudentFamilyDetailsTab">Family</a></li>
          <li><a href="#StudentDocumentDetailsTab">Documents</a></li>
        </ul>

        <div id="StudentAdmissionDetailsTab"><%@ include file="/views/student/maintain_student_admission_details.jsp" %></div>
        <div id="StudentPersonalDetailsTab"><%@ include file="/views/student/maintain_student_personal_details.jsp" %></div>
        <div id="StudentFamilyDetailsTab"><%@ include file="/views/student/maintain_student_family_members.jsp" %></div>
        <div id="StudentDocumentDetailsTab"><%@ include file="/views/student/maintain_student_documents.jsp" %></div>
      </div>
    </td>
  </tr>
</table>
