<script type="text/javascript">
$(document).ready(function(){
  $('#Gender').chosen({width: "208px"});
  $('#BloodGroup').chosen({width: "208px"});
  $('#Nationality').chosen({width: "208px"});

  $('#MaritalStatus').chosen({width: "208px"})
  .change(function(index, value) {
    setWeddingDateOption(value.selected);
  });
  $(this).datePicker({
    rangeId1: 'DateOfBirth',
    rangeId2: 'WeddingDay',
    future: false
  });

  function setWeddingDateOption(selected) {
    if (selected == 'Not Married') {
      $('#WeddingDay').val('');
      $("#WeddingDay").datepicker( "option", "disabled", true);
    } else {
      $("#WeddingDay").datepicker( "option", "disabled", false);
    }
  }
  setWeddingDateOption($("#MaritalStatus").val());
});

function getPersonalDetails() {
  var PersonalDetails = new Object();
  PersonalDetails.FirstName=jQuery('#FirstName').val();
  PersonalDetails.MiddleName=jQuery('#MiddleName').val();
  PersonalDetails.LastName=jQuery('#LastName').val();
  PersonalDetails.Gender=jQuery('#Gender').val();
  PersonalDetails.DateOfBirth=jQuery('#DateOfBirth').val();
  PersonalDetails.BloodGroup=jQuery('#BloodGroup').val();
  PersonalDetails.Nationality=jQuery('#Nationality').val();
  PersonalDetails.MaritalStatus=jQuery('#MaritalStatus').val();
  PersonalDetails.WeddingDay=jQuery('#WeddingDay').val();
  return PersonalDetails;
}
</script>

<c:if test="${Employee == null}">
<table class="formTable_Data">
  <tr>
    <td width="40%" class="label"><spring:message code="common.firstName"/><label class="mandatory">*</label></td>
    <td width="60%" class="value"><input type="text" id="FirstName" maxlength="16" /></td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.middleName"/><label class="mandatory">*</label></td>
    <td width="60%" class="value"><input type="text" id="MiddleName" maxlength="16" /></td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.lastName"/><label class="mandatory">*</label></td>
    <td width="60%" class="value"><input type="text" id="LastName" maxlength="16" /></td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.gender"/><label class="mandatory">*</label></td>
    <td  width="60%" class="value">
      <myschool:gender id="Gender" uiControl="select" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.dateOfBirth"/><label class="mandatory">*</label></td>
    <td width="60%" class="value"><input type="text" id="DateOfBirth" class="datepicker" /></td>
  </tr>
  <tr>
    <td width="40%" class="label">Blood Group</td>
    <td width="60%" class="value">
      <myschool:bloodgroup id="BloodGroup" uiControl="select" clazz="chosen-select" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Nationality</td>
    <td width="60%" class="value">
      <myschool:nationality id="Nationality" uiControl="select" clazz="chosen-select" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Marital Status<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <myschool:marital-status id="MaritalStatus" uiControl="select" clazz="chosen-select" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Wedding Day</td>
    <td width="60%" class="value"><input type="text" id="WeddingDay" maxlength="10" class="datepicker" /></td>
  </tr>
</table>
</c:if>

<c:if test="${Employee != null}">
<table class="formTable_Data">
  <tr>
    <td width="40%" class="label"><spring:message code="common.firstName"/><label class="mandatory">*</label></td>
    <td width="60%" class="value"><input type="text" id="FirstName" maxlength="16" value="${Employee.firstName}" /></td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.middleName"/><label class="mandatory">*</label></td>
    <td width="60%" class="value"><input type="text" id="MiddleName" maxlength="16" value="${Employee.middleName}" /></td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.lastName"/><label class="mandatory">*</label></td>
    <td width="60%" class="value"><input type="text" id="LastName" maxlength="16" value="${Employee.lastName}" /></td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.gender"/><label class="mandatory">*</label></td>
    <td  width="60%" class="value">
      <myschool:gender id="Gender" uiControl="select" value="${Employee.gender}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.dateOfBirth"/><label class="mandatory">*</label></td>
    <td width="60%" class="value"><input type="text" id="DateOfBirth" class="datepicker" value="${Employee.dateOfBirth}" /></td>
  </tr>
  <tr>
    <td width="40%" class="label">Blood Group</td>
    <td width="60%" class="value">
      <myschool:bloodgroup id="BloodGroup" uiControl="select" value="${Employee.bloodGroup}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Nationality</td>
    <td width="60%" class="value">
      <myschool:nationality id="Nationality" uiControl="select" value="${Employee.nationality}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Marital Status<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <myschool:marital-status id="MaritalStatus" uiControl="select" value="${Employee.maritalStatus}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Wedding Day</td>
    <td width="60%" class="value">
      <input type="text" id="WeddingDay" maxlength="10" class="datepicker" value="${Employee.weddingDay}" />
    </td>
  </tr>
</table>
</c:if>
