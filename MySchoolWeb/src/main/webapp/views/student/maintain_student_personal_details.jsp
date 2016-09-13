<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<style>
#StudentPersonalDetailsTable {
  font-size: 0.65em;
}
</style>
<script type="text/javascript">
$(document).ready(function(){
  $('#CorrespondenceAddress').textcounter({id: 'CorrespondenceAddress'});
  $('#PermanentAddress').textcounter({id: 'PermanentAddress'});
  $('#IdentificationMarks').textcounter({id: 'IdentificationMarks'});
  $('#Gender').chosen();
  $('#Religion').chosen();
  $('#Nationality').chosen();
  $('#BloodGroup').chosen();
  $(this).datePicker({id: 'DateOfBirth', future: false});
});

function getPersonalDetails() {
  var PersonalDetails = new Object();
  PersonalDetails.FirstName=$('#FirstName').val();
  PersonalDetails.MiddleName=$('#MiddleName').val();
  PersonalDetails.LastName=$('#LastName').val();
  PersonalDetails.Gender=$('#Gender').val();
  PersonalDetails.DateOfBirth=$('#DateOfBirth').val();
  PersonalDetails.Religion=$('#Religion').val();
  PersonalDetails.Caste=$('#Caste').val();
  PersonalDetails.Nationality=$('#Nationality').val();
  PersonalDetails.MotherTongue=$('#MotherTongue').val();
  PersonalDetails.MobileNumber=$('#MobileNumber').val();
  PersonalDetails.BloodGroup=$('#BloodGroup').val();
  PersonalDetails.CorrespondenceAddress=$('#CorrespondenceAddress').val();
  PersonalDetails.PermanentAddress=$('#PermanentAddress').val();
  PersonalDetails.IdentificationMarks=$('#IdentificationMarks').val();
  return PersonalDetails;
}
</script>

<c:if test="${Student == null}">
<table class="formTable_Data">
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.firstName"/><label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <input type="text" id="FirstName" maxlength="16" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.middleName"/><label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <input type="text" id="MiddleName" maxlength="16" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.lastName"/><label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <input type="text" id="LastName" maxlength="16" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.gender"/><label class="mandatory">*</label>
    </td>
    <td align="left">
      <myschool:gender id="Gender" value="" uiControl="select" clazz="chosen-select" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.dateOfBirth"/><label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <input type="text" id="DateOfBirth" class="datepicker" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.religion"/>
    </td>
    <td align="left">
      <myschool:religion id="Religion" value="" uiControl="select" clazz="chosen-select" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.caste"/>
    </td>
    <td width="60%" class="value">
      <input type="text" id="Caste" maxlength="16" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.nationality"/>
    </td>
    <td align="left">
      <myschool:nationality id="Nationality" value="" uiControl="select" clazz="chosen-select" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.motherTongue"/>
    </td>
    <td width="60%" class="value">
      <input type="text" id="MotherTongue" maxlength="20" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.mobileNumber"/>
    </td>
    <td width="60%" class="value">
      <input type="text" id="MobileNumber" maxlength="10" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.bloodGroup"/>
    </td>
    <td align="left">
      <myschool:bloodgroup id="BloodGroup" value="" uiControl="select" clazz="chosen-select" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.correspondenceAddress"/><label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <textarea id="CorrespondenceAddress" rows="5" maxlength="128"></textarea>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.permanentAddress"/><label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <textarea id="PermanentAddress" rows="5" maxlength="128"></textarea>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.identificationMarks"/><label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <textarea id="IdentificationMarks" rows="5" maxlength="64"></textarea>
    </td>
  </tr>
</table>
</c:if>

<c:if test="${Student != null}">
<c:set var="PersonalDetails" value="${Student.personalDetails}" />
<table class="formTable_Data">
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.firstName"/><label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <input type="text" id="FirstName" maxlength="16" value="${PersonalDetails.firstName}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.middleName"/><label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <input type="text" id="MiddleName" maxlength="16" value="${PersonalDetails.middleName}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.lastName"/><label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <input type="text" id="LastName" maxlength="16" value="${PersonalDetails.lastName}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.gender"/><label class="mandatory">*</label>
    </td>
    <td align="left">
      <myschool:gender id="Gender" value="${PersonalDetails.gender}" uiControl="select" clazz="chosen-select" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.dateOfBirth"/><label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <input type="text" id="DateOfBirth" class="datepicker" value="${PersonalDetails.dateOfBirth}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.religion"/>
    </td>
    <td align="left">
      <myschool:religion id="Religion" value="${PersonalDetails.religion}" uiControl="select" clazz="chosen-select" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.caste"/>
    </td>
    <td width="60%" class="value">
      <input type="text" id="Caste" maxlength="16" value="${PersonalDetails.caste}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.nationality"/>
    </td>
    <td align="left">
      <myschool:nationality id="Nationality" value="${PersonalDetails.nationality}" uiControl="select" clazz="chosen-select" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.motherTongue"/>
    </td>
    <td width="60%" class="value">
      <input type="text" id="MotherTongue" maxlength="20" value="${PersonalDetails.motherTongue}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.mobileNumber"/>
    </td>
    <td width="60%" class="value">
      <input type="text" id="MobileNumber" maxlength="10" value="${PersonalDetails.mobileNumber}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.bloodGroup"/>
    </td>
    <td align="left">
      <myschool:bloodgroup id="BloodGroup" value="${PersonalDetails.bloodGroup}" uiControl="select" clazz="chosen-select" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.correspondenceAddress"/><label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <textarea id="CorrespondenceAddress" rows="5" maxlength="128">${PersonalDetails.correspondenceAddress}</textarea>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.permanentAddress"/><label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <textarea id="PermanentAddress" rows="5" maxlength="128">${PersonalDetails.permanentAddress}</textarea>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.identificationMarks"/><label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <textarea id="IdentificationMarks" rows="5" maxlength="64">${PersonalDetails.identificationMarks}</textarea>
    </td>
  </tr>
</table>
</c:if>
