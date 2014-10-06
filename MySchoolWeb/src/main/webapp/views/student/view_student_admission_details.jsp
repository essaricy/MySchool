<style>
#StudentAdmissionTable tr th,
#StudentAdmissionTable tr td {
  font-size: 0.8em;
}
</style>
<c:if test="${Student != null}">
<table width="80%" id="StudentAdmissionTable" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td width="40%" class="label"><spring:message code="student.admissionNumber"/></td>
    <td width="60%" class="value"><b>${Student.admissionNumber}</b></td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="student.class"/></td>
    <td width="60%" class="value">
      ${Student.registeredClassDto.classDto.className}, ${Student.registeredClassDto.medium.description}, ${Student.registeredClassDto.section.sectionName}
    </td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.dateOfJoining"/></td>
    <td width="60%" class="value">${Student.dateOfJoining}</td>
  </tr>
  <tr>
    <td width="40%" class="label">Admission Status</td>
    <td width="60%" class="value">${Student.admissionStatus.description}</td>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.remarks"/></td>
    <td width="60%" class="value">${Student.remarks}</td>
  </tr>
</table>
</c:if>