<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<style>
.userFormTable {
  font-size: 0.8em;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
  $("#StudentFamilyMember_Relationship").lazySelect({
    id: "StudentFamilyMember_Relationship",
    width: "94%",
    url: '<%=request.getContextPath()%>/relationship/jsonList.htm',
    selectOnCode: $('#StudentFamilyMember_RelationshipCode').val(),
    excludeCodes: ['D', 'H', 'O', 'W']
  });

  $('#create').click(function() {
    validateStudentAttribute('CREATE',
        '<%=request.getContextPath()%>/student-attribute/doValidate.htm', 'StudentFamilyMember',
        getStudentFamilyMember(), studentFamilyMemberAttributeSequence);
  });

  $('#update').click(function() {
    validateStudentAttribute('UPDATE',
        '<%=request.getContextPath()%>/student-attribute/doValidate.htm', 'StudentFamilyMember',
        getStudentFamilyMember(), studentFamilyMemberAttributeSequence);
  });
});

function getStudentFamilyMember() {
    var StudentFamilyMember = new Object();
    StudentFamilyMember.FamilyMemberId=$('#StudentFamilyMember_FamilyMemberId').val();
    StudentFamilyMember.RelationshipCode=$('#StudentFamilyMember_Relationship').val();
    StudentFamilyMember.RelationshipName=$('#StudentFamilyMember_Relationship option:selected').text();
    StudentFamilyMember.FamilyMemberName=$('#StudentFamilyMember_Name').val();
    StudentFamilyMember.Occupation=$('#StudentFamilyMember_Occupation').val();
    StudentFamilyMember.MobileNumber=$('#StudentFamilyMember_MobileNumber').val();
    StudentFamilyMember.EmailID=$('#StudentFamilyMember_EmailId').val();
    StudentFamilyMember.AvailSMS=$('#StudentFamilyMember_AvailSMS').val();
    StudentFamilyMember.AvailEmail=$('#StudentFamilyMember_AvailEMail').val();
  return StudentFamilyMember;
}
</script>

<c:if test="${StudentFamilyMember == null}">
<table class="formTable_Data">
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.relationship"/><label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <input id="StudentFamilyMember_RelationshipCode" type="hidden" value="${StudentFamilyMember.relationship.code}" />
      <select id="StudentFamilyMember_Relationship">
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.name"/><label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <input id="StudentFamilyMember_Name" type="text" maxlength="32" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.occupation"/>
    </td>
    <td width="60%" class="value">
      <input type="text" id="StudentFamilyMember_Occupation" maxlength="32" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.mobileNumber"/>
    </td>
    <td width="60%" class="value">
      <input id="StudentFamilyMember_MobileNumber" type="text" maxlength="10" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.emailid"/>
    </td>
    <td width="60%" class="value">
      <input id="StudentFamilyMember_EmailId" type="text" maxlength="32" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.avail.sms"/>
    </td>
    <td width="60%" class="value">
      <myschool:checkbox id="StudentFamilyMember_AvailSMS" value="false" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.avail.email"/>
    </td>
    <td width="60%" class="value">
      <myschool:checkbox id="StudentFamilyMember_AvailEMail" value="false" />
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <input type="hidden" id="StudentFamilyMember_FamilyMemberId" value="0" />
      <input type="button" id="create" value='<spring:message code="common.create"/>' />
    </td>
  </tr>
</table>
</c:if>

<c:if test="${StudentFamilyMember != null}">
<table class="formTable_Data">
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.relationship"/><label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <input id="StudentFamilyMember_RelationshipCode" type="hidden" value="${StudentFamilyMember.relationship.code}" />
      <select id="StudentFamilyMember_Relationship">
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.name"/><label class="mandatory">*</label>
    </td>
    <td width="60%" class="value">
      <input id="StudentFamilyMember_Name" type="text" maxlength="32" value="${StudentFamilyMember.name}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.occupation"/>
    </td>
    <td width="60%" class="value">
      <input type="text" id="StudentFamilyMember_Occupation" maxlength="32" value="${StudentFamilyMember.occupation}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.mobileNumber"/>
    </td>
    <td width="60%" class="value">
      <input id="StudentFamilyMember_MobileNumber" type="text" maxlength="10" value="${StudentFamilyMember.mobileNumber}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.emailid"/>
    </td>
    <td width="60%" class="value">
      <input id="StudentFamilyMember_EmailId" type="text" maxlength="32" value="${StudentFamilyMember.emailId}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.avail.sms"/>
    </td>
    <td width="60%" class="value">
      <myschool:checkbox id="StudentFamilyMember_AvailSMS" value="${StudentFamilyMember.availSMS}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">
      <spring:message code="common.avail.email"/>
    </td>
    <td width="60%" class="value">
      <myschool:checkbox id="StudentFamilyMember_AvailEMail" value="${StudentFamilyMember.availEmail}" />
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <input type="hidden" id="StudentFamilyMember_FamilyMemberId" value="${StudentFamilyMember.familyMemberId}" />
      <input type="button" id="update" value='<spring:message code="common.update"/>' />
    </td>
  </tr>
</table>
</c:if>
