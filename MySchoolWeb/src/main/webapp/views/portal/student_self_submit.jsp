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
  $('#LastAdmissionNumber').hide();

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
          saveStudent();
      }
  }

  function saveStudent() {
    var StudentData = new Object();
    StudentData.StudentId = 0;
    StudentData.Verified = 'NO';
    StudentData.ImageName=$('#ImageReferenceNumber').val();
    StudentData.AdmissionDetails=getAdmissionDetails();
    StudentData.PersonalDetails=getPersonalDetails();
    StudentData.FamilyMemberDetails=getStudentFamilyMembers();
    StudentData.DocumentDetails=getStudentDocuments();

    jQuery.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/portal-student/submitStudent.htm",
      data: {
        StudentData: JSON.stringify(StudentData),
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
    title: '${ORGANIZATION_PROFILE.organizationName} - Student Self Submit (ESS)'
  });

});
</script>

<input type="hidden" id="Verified" value="NO" />
<input type="hidden" id="StudentId" value="0" />
<table cellpadding="2" width="90%" align="center" cellspacing="0" border="0">
  <caption class="dataTableCaption">
    Student Registration (Self-Submit Service)
  </caption>
  <tr>
    <td colspan="2" align="center" valign="top" style="padding-top: 8px;">
      <div id="Socialize"></div>
      <p/>
      <!-- Student instructions page -->
      <%@ include file="/views/portal/portal_student_instructions.jsp" %>
    </td>
  </tr>

  <tr id="StudentFormRow">
    <td width="15%" valign="top">
      <input type="hidden" id="ImageReferenceNumber" value="" />
      <!-- Student Photo -->
      <c:if test="${Student == null}">
      <table cellpadding="5" cellspacing="0" border="0" width="100%" height="100%" class="formTable">
        <tr>
          <td align="center">
            <img id="studentImage" name="studentImage" src="<%=request.getContextPath()%>/images/icons/no-image.jpg" border="1" width="150px" height="180px"/>
          </td>
        </tr>
        <tr>
          <td align="center">
            <a href="#" id="uploadImage" class="formLink">Upload Photo</a>
          </td>
        </tr>
      </table>
      </c:if>
      <c:if test="${Student != null}">
      <table cellpadding="5" cellspacing="0" border="0" width="100%" height="100%" class="formTable">
        <tr>
          <td align="center">
            <img id="studentImage" name="studentImage" src="<%=request.getContextPath()%>/image/getImage.htm?type=student&imageSize=ORIGINAL&contentId=${Student.studentNumber}&sid=<%= new java.util.Date().getTime()%>" border="1" width="150px" height="180px"/>
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
    <td width="15%" valign="top">&nbsp;</td>
    <td width="85%" valign="top">
      <div id="StudentFormActionButtons" style="margin-top: 10px; margin-bottom: 40px; text-align: center;">
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
<br/>