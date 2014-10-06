<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>
<style>
#StudentPersonalDetailsTable tr th,
#StudentPersonalDetailsTable tr td {
  font-size: 0.8em;
}
</style>
<script type="text/javascript">
$(document).ready(function(){
    $('#CorrespondenceAddress').textcounter({
        id: 'CorrespondenceAddress'
    });
    $('#PermanentAddress').textcounter({
        id: 'PermanentAddress'
    });
    $('#IdentificationMarks').textcounter({
        id: 'IdentificationMarks'
    });
    $('.chosen-select').chosen({width: "100%"});
});
</script>

<c:if test="${StudentPersonalDetails != null}">
<table width="80%" id="StudentPersonalDetailsTable" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td width="40%" class="label"><spring:message code="common.firstName"/></td>
    <td width="60%" class="value">${StudentPersonalDetails.firstName}</td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.middleName"/></td>
    <td width="60%" class="value">${StudentPersonalDetails.middleName}</td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.lastName"/></td>
    <td width="60%" class="value">${StudentPersonalDetails.lastName}</td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.gender"/></td>
    <td width="60%" class="value"><myschool:gender uiControl="label" value="${StudentPersonalDetails.gender}" /></td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.dateOfBirth"/></td>
    <td width="60%" class="value">${StudentPersonalDetails.dateOfBirth}</td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.religion"/></td>
    <td width="60%" class="value"><myschool:religion uiControl="label" value="${StudentPersonalDetails.religion}" /></td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.caste"/></td>
    <td width="60%" class="value">${StudentPersonalDetails.caste}</td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.nationality"/></td>
    <td width="60%" class="value"><myschool:nationality uiControl="label" value="${StudentPersonalDetails.nationality}" /></td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.motherTongue"/></td>
    <td width="60%" class="value">${StudentPersonalDetails.motherTongue}</td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.mobileNumber"/></td>
    <td width="60%" class="value">${StudentPersonalDetails.mobileNumber}</td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.bloodGroup"/></td>
    <td width="60%" class="value">${StudentPersonalDetails.bloodGroup}</td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.permanentAddress"/></td>
    <td width="60%" class="value">${StudentPersonalDetails.permanentAddress}</td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.correspondenceAddress"/></td>
    <td width="60%" class="value">${StudentPersonalDetails.correspondenceAddress}</td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.identificationMarks"/></td>
    <td width="60%" class="value">${StudentPersonalDetails.identificationMarks}</td>
  </tr>
</table>
</c:if>
