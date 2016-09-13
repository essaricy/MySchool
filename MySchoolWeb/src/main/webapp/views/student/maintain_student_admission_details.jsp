<%@ taglib tagdir="/WEB-INF/tags" prefix="myschool" %>

<style>
#StudentAdmissionTable {
  font-size: 0.50em;
}
</style>
<script>
var lookupClassDialog = null;
jQuery(document).ready(function() {
  $('#LookupClass').tooltipster();
  $(this).datePicker({id: 'DateOfJoining'});
  $("#AdmissionStatus").lazySelect({
    id: "AdmissionStatus",
    url: '<%=request.getContextPath()%>/admission-status/jsonList.htm',
    selectOnCode: $('#AdmissionStatusId').val()
  });

  jQuery('#LastAdmissionNumber').click(function () {
    jQuery.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/admission/getLastAdmissionNumber.htm",
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

  $('#Remarks').textcounter({id: 'Remarks'});

  jQuery('#LookupClass').click(function () {
    openDialog('<%=request.getContextPath()%>/class/lookupClass.htm?sid=' + new Date().getTime(), 'Lookup Class', 400, 300);
  });
});

function getAdmissionDetails() {
  var AdmissionDetails = new Object();
  AdmissionDetails.AdmissionNumber=$('#AdmissionNumber').val();
  AdmissionDetails.RegisteredClassId=$('#RegisteredClassId').val();
  AdmissionDetails.AdmissionStatusId=$('#AdmissionStatus').val();
  AdmissionDetails.DateOfJoining=$('#DateOfJoining').val();
  AdmissionDetails.Remarks=$('#Remarks').val();
  //alert(AdmissionDetails);
  return AdmissionDetails;
}

function setRegisteredClass(RegisteredClass) {
  $('#RegisteredClassId').val(RegisteredClass.RegisteredClassId);
  var classDetails = '<b><i>' + RegisteredClass.ClassName + ', ' + RegisteredClass.MediumName + ', ' + RegisteredClass.SectionName + '<i></b>';
  $('#RegisteredClassText').html(classDetails);
}
</script>

<c:if test="${Student == null}">
<table width="80%" id="StudentAdmissionTable" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td width="40%" class="label">
      <spring:message code="student.admissionNumber"/><label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <input type="text" id="AdmissionNumber" maxlength="10" />
      <a href="#" id="LastAdmissionNumber" class="formDataLink">Get Last Admission Number</a>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="student.class"/><label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <input type="hidden" id="RegisteredClassId" value="0" />
      <span id="RegisteredClassText"></span>
      <img id="LookupClass" src="<%=request.getContextPath()%>/images/icons/magnifier_zoom_in.png" class="iconImage" title="Select Class" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      Admission Status<label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <input type="hidden" id="AdmissionStatusId"value="0" />
      <select id="AdmissionStatus" class="chosen-select">
        <option value="0">Select</option>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.dateOfJoining"/><label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <input type="text" id="DateOfJoining" class="datepicker" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.remarks"/>
    </td>
    <td width="60%" class="value">
      <textarea id="Remarks" rows="5" cols="50" maxlength="128"></textarea>
    </td>
  </tr>
</table>
</c:if>

<c:if test="${Student != null}">
<c:set var="RegisteredClass" value="${Student.registeredClassDto}" />
<table width="80%" id="StudentAdmissionTable" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td width="40%" class="label">
      <spring:message code="student.admissionNumber"/><label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <input type="hidden" id="AdmissionNumber" value="${Student.admissionNumber}" />
      <b>${Student.admissionNumber}</b>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="student.class"/><label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <input type="hidden" id="RegisteredClassId" value="${RegisteredClass.classId}" />
      <span id="RegisteredClassText">
        <b>${RegisteredClass.classDto.className}, ${RegisteredClass.medium.description}, ${RegisteredClass.section.sectionName}</b>
      </span>
      <img id="LookupClass" src="<%=request.getContextPath()%>/images/icons/magnifier_zoom_in.png" class="iconImage" title="Select Class" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      Admission Status<label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <input type="hidden" id="AdmissionStatusId" value="${Student.admissionStatus.statusId}" />
      <select id="AdmissionStatus" class="chosen-select">
        <option value="0">Select</option>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.dateOfJoining"/><label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <input type="text" id="DateOfJoining" class="datepicker" value="${Student.dateOfJoining}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.remarks"/>
    </td>
    <td width="60%" class="value">
      <textarea id="Remarks" rows="5" cols="50" maxlength="128">${Student.remarks}</textarea>
    </td>
  </tr>
</table>
</c:if>
