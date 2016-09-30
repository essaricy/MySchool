<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<script src='https://www.google.com/recaptcha/api.js'></script>
<style>
#StudentRegistrationTabs {
  font-size: 1.1em;
}
#PostSubmitNotes {
  color: green;
}
</style>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/widgets/jquery.waitMe/waitMe.css" />
<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/widgets/jquery.waitMe/waitMe.js"></script> 
<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/scripts/myschool-student-attributes.js"></script>
<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/scripts/myschool-ajax.js"></script>
<script>
jQuery(document).ready(function() {
  $('#StudentRegistrationTabs').tabs({id: 'StudentRegistrationTabs'});
  $("#StudentRegistrationTabs").tabs("option", "active", 0);
  $('#LastAdmissionNumber').hide();
  $('#PostSubmitNotes').hide();

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
    //setTimeout(function () { uploader.start(); }, 1000);
    wait();
    uploader.start();
  });
  uploader.bind('FileUploaded', function(up, file, result) {
    var response = $.parseJSON(result.response);
    if (response.Successful) {
      notifySuccess('Image has been successfully uploaded and will be updated when saved.');
      // Replace image with some fading effect
      var studentImage = $('#studentImage');
      var originalImageUrl = '<%=request.getContextPath()%>/image/getEvanescentImage.htm?type=student&imageSize=ORIGINAL&contentId=' + response.ReferenceNumber + '&sid=' + new Date().getTime();
      var passportImageUrl = '<%=request.getContextPath()%>/image/getEvanescentImage.htm?type=student&imageSize=PASSPORT&contentId=' + response.ReferenceNumber + '&sid=' + new Date().getTime();
      studentImage.fadeOut(1000, function () {
        studentImage.attr('src', passportImageUrl);
        // Magnify image on click
        studentImage.click(function() {$.magnificPopup.open({ items: { src: originalImageUrl }, type: 'image' })});
        studentImage.fadeIn(3000);
      });
      $('#ImageReferenceNumber').val(response.ReferenceNumber);
    } else {
      attendError('Unable to upload the image now. Please try again.');
    }
    unwait();
  });

  jQuery('#SaveStudentData').click(function () {
    var verificationCode = $('#g-recaptcha-response').val();
    if (verificationCode == '') {
      notifyError('Click on "I\'m not a robot"');
      return false;
    }

    if (!$('#Agree').is(':checked')) {
      notifyError('Please agree to the declaration.');
      return false;
    }
    interactConfirm('Please ensure that you have entered correct information.<br /> You cannot change the information after save is successful.', saveStudent);
  });

  function saveStudent() {
    var StudentData = new Object();
    StudentData.StudentId = 0;
    StudentData.Verified = 'NO';
    StudentData.ImageName=$('#ImageReferenceNumber').val();
    StudentData.AdmissionDetails=getAdmissionDetails();
    StudentData.PersonalDetails=getPersonalDetails();
    StudentData.FamilyMemberDetails=getStudentFamilyMembers();
    StudentData.DocumentDetails=getStudentDocuments();

    var formData = {
      StudentData: JSON.stringify(StudentData),
      CaptchaResponse: $('#g-recaptcha-response').val()
    }

    sendAjax("<%=request.getContextPath()%>/portal-student/submitStudent.htm", formData, postSuccess, null);
  }

  function postSuccess() {
    // Remove all the content that has an action associated with
    $("#StudentRegistrationTabs").find("input,button,textarea,select").attr("disabled", "disabled");
    $(".iconImage,.ui-datepicker-trigger,.g-recaptcha,#uploadImage,#AgreeStatement,#SaveStudentData").fadeOut(1000);
    $('.chosen-select').trigger("chosen:updated");
    $('#PostSubmitNotes').fadeIn(1000);
  }
});
</script>

<input type="hidden" id="Verified" value="NO" />
<input type="hidden" id="StudentId" value="0" />
<table class="formTable_Container">
  <caption>Student Registration (Self-Submit Service)</caption>
  <tr>
    <td colspan="2" align="center" valign="top" style="padding-top: 8px;">
      <!-- Student instructions page -->
      <%@ include file="/views/portal/portal_student_instructions.jsp" %>
    </td>
  </tr>

  <tr id="StudentFormRow">
    <td width="15%" valign="top">
      <input type="hidden" id="ImageReferenceNumber" value="" />
      <!-- Student Photo -->
      <c:if test="${Student == null}">
      <table class="formTable_Data">
        <tr>
          <td align="center">
            <img id="studentImage" name="studentImage" src="<%=request.getContextPath()%>/images/icons/no-image-yet.png" width="150px" height="180px" class="no-image"/>
          </td>
        </tr>
        <tr>
          <td align="center">
            <a href="#" id="uploadImage">Upload Photo</a>
          </td>
        </tr>
      </table>
      </c:if>
      <c:if test="${Student != null}">
      <table class="formTable_Data">
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
    <td width="15%" valign="top">&nbsp;</td>
    <td width="85%" valign="top">
      <div id="StudentFormActionButtons" style="margin-top: 10px; margin-bottom: 40px; text-align: center;">
        <div class="g-recaptcha" data-sitekey="${MYSCHOOL_PROFILE.captchaKey}"></div><br/>
        <table>
          <tr>
            <td align="left" colspan="2">
              <p id="AgreeStatement">
              <input type="checkbox" id="Agree">&nbsp;I, hereby, certify that the information entered is true to the best of my knowledge and belief.
              </p>
            </td>
          </tr>
          <tr>
            <td align="center" colspan="2">
              <input type="button" id="SaveStudentData" value="Save" /><br/>
            </td>
          </tr>
        </table>
      </div>
    </td>
  </tr>
</table>
<form action="">
<input type="hidden" name="UserType" value="STUDENT" />
<div id="PostSubmitNotes">
  <p>
    Thank you for using Student Self-Submit Service.<br/>
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
