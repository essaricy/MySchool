<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<style>
#StudentAccordion p {
  font-size: 0.7em;
  font-weight: bold;
  text-align: left;
}
</style>

<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/scripts/myschool-student-attributes.js"></script>
<script>
jQuery(document).ready(function() {
  $(this).myAccordion({id: 'StudentAccordion'});
  $("#StudentAccordion").accordion( "option", "active", 0);

  var searchUrl = null;
  if ($('#RECORD_STATUS').val() == 'VERIFIED') {
    searchUrl = '<%=request.getContextPath()%>/student/launchVerifiedStudentsSearch.htm';
  } else if ($('#RECORD_STATUS').val() == 'UNVERIFIED') {
    searchUrl = '<%=request.getContextPath()%>/student/launchUnverifiedStudentsSearch.htm';
  }

  $('#add').click(function () {
    document.forms[0].action='<%=request.getContextPath()%>/student/launch.htm';
    document.forms[0].submit();
  });

  $('#search').click(function () {
    document.forms[0].action=searchUrl;
    document.forms[0].submit();
  });

  $('#previous').click(function () {
    document.forms[0].action='<%=request.getContextPath()%>/student/getPreviousStudent.htm?AdmissionNumber=' + $('#AdmissionNumber').val() + "&Type=" + $('#RECORD_STATUS').val();;
    document.forms[0].submit();
  });

  $('#next').click(function () {
    document.forms[0].action='<%=request.getContextPath()%>/student/getNextStudent.htm?AdmissionNumber=' + $('#AdmissionNumber').val() + "&Type=" + $('#RECORD_STATUS').val();;
    document.forms[0].submit();
  });

  if ($('#StudentId').val() == '0') {
      $('#add').hide();
	  $('#previous').hide();
	  $('#next').hide();
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
  $('#next').tooltipster();
  $('#previous').tooltipster();
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
      showSuccess('Image has been successfully uploaded and will be updated when saved.');
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
      showError('Unable to upload the image now. Please try again.');
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

  jQuery('#save').click(function () {
      saveStudent(false);
  });

  jQuery('#save_verify').click(function () {
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
          showSuccess(message);
          if (verify) {
              $('#Verified').val('YES');
              $('#save_verify').hide();
          }
        } else {
          showSuccess('Data has been updated successfully.');
        }
        var prevStudentId = $('#StudentId').val();
        // set Student id
        if (typeof(result.ReferenceNumber) != 'undefined' && result.ReferenceNumber != null && result.ReferenceNumber != '') {
            $('#StudentId').val(result.ReferenceNumber);
            if (prevStudentId == '0') {
              $('#AdmissionNumber').attr('disabled', true);
              $('#LastAdmissionNumber').hide();
              // enable add icon
              $('#add').show();
              // update picture icon
              $('#uploadImage').attr('title', 'Update Picture');
              $('#uploadImage').tooltipster();
              // enable print icon
              $('#print').show();
            }
            prevStudentId = result.ReferenceNumber;
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

<input type="hidden" id="RECORD_STATUS" name="RECORD_STATUS" value="${RECORD_STATUS}" />
<c:if test="${Student == null}">
  <input type="hidden" id="StudentId" value="0" />
</c:if>
<c:if test="${Student != null}">
  <input type="hidden" id="StudentId" value="${Student.studentId}" />
  <c:set var="StudentPersonalDetails" value="${Student.personalDetails}" />
  <c:set var="StudentDocuments" value="${Student.documentsSubmitted}" />
  <c:set var="StudentFamilyMembers" value="${Student.familyMembers}" />
</c:if>

<c:if test="${Student == null}">
  <input type="hidden" id="Verified" value="NO" />
</c:if>
<c:if test="${Student != null}">
  <c:if test="${Student.verified}">
    <input type="hidden" id="Verified" value="YES" />
  </c:if>
  <c:if test="${ ! Student.verified}">
    <input type="hidden" id="Verified" value="NO" />
  </c:if>
</c:if>

<table cellpadding="2" width="90%" align="center" cellspacing="0" border="0">
  <caption class="dataTableCaption">
    Student Registration
    <c:if test="${RECORD_STATUS == 'UNVERIFIED'}"> (Portal) </c:if>
  </caption>
  <tr>
    <td colspan="2" align="right" valign="top" style="padding-top: 8px;">
      <input type="hidden" id="ImageReferenceNumber" value="" />
      <img id="add" src="<%=request.getContextPath()%>/images/icons/add.png" class="iconImage" title="Create Student" />
      <img id="search" src="<%=request.getContextPath()%>/images/icons/magnifier.png" class="iconImage" title="Search Students" />
	  <img id="previous" src="<%=request.getContextPath()%>/images/icons/back.png" class="iconImage" title="Previous Student" />
	  <img id="next" src="<%=request.getContextPath()%>/images/icons/forward.png" class="iconImage" title="Next Student" />
      <img id="uploadImage" src="<%=request.getContextPath()%>/images/icons/picture_edit.png" class="iconImage" title="" />
      <img id="save" src="<%=request.getContextPath()%>/images/icons/save.png" class="iconImage" title="Save Student" />
      <img id="save_verify" src="<%=request.getContextPath()%>/images/icons/save_accept.png" class="iconImage" title="Save & Verify Student" />
      <img id="print" src="<%=request.getContextPath()%>/images/icons/print.png" class="iconImage" title="Print Student" />
    </td>
  </tr>
  <tr>
    <td width="15%" valign="top">
      <!-- Student Photo -->
      <c:if test="${Student == null}">
      <table cellpadding="5" cellspacing="0" border="0" width="100%" height="100%" class="formTable">
        <tr>
          <td align="center">
            <img id="studentImage" name="studentImage" src="<%=request.getContextPath()%>/image/getImage.htm?type=no-image" border="1" width="150px" height="180px"/>
          </td>
        </tr>
      </table>
      </c:if>
      <c:if test="${Student != null}">
      <table cellpadding="5" cellspacing="0" border="0" width="100%" height="100%" class="formTable">
        <tr>
          <td align="center">
            <img id="studentImage" name="studentImage" src="<%=request.getContextPath()%>/image/getImage.htm?type=student&imageSize=ORIGINAL&contentId=${Student.admissionNumber}&sid=<%= new java.util.Date().getTime()%>" border="1" width="150px" height="180px"/>
          </td>
        </tr>
      </table>
      </c:if>
    </td>
    <td width="85%" valign="top">
      <div id="StudentAccordion">
        <p class="title"><spring:message code="student.admission.details"/></p>
        <div><%@ include file="/views/student/maintain_student_admission_details.jsp" %></div>
        <p class="title"><spring:message code="student.personal.details"/></p>
        <div><%@ include file="/views/student/maintain_student_personal_details.jsp" %></div>
        <p class="title"><spring:message code="student.family.details"/></p>
        <div><%@ include file="/views/student/maintain_student_family_members.jsp" %></div>
        <p class="title">Student Documents</p>
        <div><%@ include file="/views/student/maintain_student_documents.jsp" %></div>
      </div>
    </td>
  </tr>
</table>
