<%@page import="com.myschool.employee.constant.EmployeeNotificationTo"%>
<script type="text/javascript">
$(document).ready(function(){
  $('#PresentAddress').textcounter({id: 'PresentAddress'});
  $('#PermanentAddress').textcounter({id: 'PermanentAddress'});

  $('#EmailNotificationTo').chosen({width: "94%"});
  $('#SmsNotificationTo').chosen({width: "94%"});
  $("#EmergencyContactRelationship").lazySelect({
    id: "EmergencyContactRelationship",
    width: "94%",
    url: '<%=request.getContextPath()%>/relationship/jsonList.htm',
    selectOnCode: $('#EmergencyContactRelationshipCode').val()
  });
});

function getContactDetails() {
  var ContactDetails = new Object();
  ContactDetails.PresentAddress=$('#PresentAddress').val();
  ContactDetails.PermanentAddress=$('#PermanentAddress').val();
  ContactDetails.PersonalMobileNumber=$('#PersonalMobileNumber').val();
  ContactDetails.PersonalEmailId=$('#PersonalEmailId').val();
  ContactDetails.EmergencyContactNumber=$('#EmergencyContactNumber').val();
  ContactDetails.EmergencyContactRelationshipCode=$('#EmergencyContactRelationship').val();
  ContactDetails.EmergencyContactRelationshipName=$('#EmergencyContactRelationship option:selected').text();
  ContactDetails.OfficeDeskPhoneNumber=$('#OfficeDeskPhoneNumber').val();
  ContactDetails.OfficeDeskExtension=$('#OfficeDeskExtension').val();
  ContactDetails.OfficeMobileNumber=$('#OfficeMobileNumber').val();
  ContactDetails.OfficeEmailId=$('#OfficeEmailId').val();
  ContactDetails.EmailNotificationTo=$('#EmailNotificationTo').val();
  ContactDetails.SmsNotificationTo=$('#SmsNotificationTo').val();
  return ContactDetails;
}
</script>

<c:set var="EmployeeNotificationToValues" value="<%=EmployeeNotificationTo.values()%>"/>
<c:if test="${EmployeeContact == null}">
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td width="40%" class="label">Present Address<label class="mandatory">*</label></td>
    <td width="60%" class="value"><textarea id="PresentAddress" rows="5" maxlength="512" ></textarea></td>
  </tr>
  <tr>
    <td width="40%" class="label">Permanent Address</td>
    <td width="60%" class="value"><textarea id="PermanentAddress" rows="5" maxlength="512" ></textarea></td>
  </tr>
  <tr>
    <td width="40%" class="label">Personal Mobile Number<label class="mandatory">*</label></td>
    <td width="60%" class="value"><input type="text" id="PersonalMobileNumber" maxlength="16" /></td>
  </tr>
  <tr>
    <td width="40%" class="label">Personal Email ID</td>
    <td width="60%" class="value"><input type="text" id="PersonalEmailId" maxlength="32" /></td>
  </tr>
  <tr>
    <td width="40%" class="label">Emergency Contact Number<label class="mandatory">*</label></td>
    <td width="60%" class="value"><input type="text" id="EmergencyContactNumber" maxlength="16" /></td>
  </tr>
  <tr>
    <td width="40%" class="label">Emergency Contact Relationship<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <select id="EmergencyContactRelationship" class="chosen-select">
      </select>
	</td>
  </tr>
  <tr>
    <td width="40%" class="label">Office Desk Phone Number</td>
    <td width="60%" class="value">
      <input type="text" id="OfficeDeskPhoneNumber" maxlength="16" style="width: 120px;" />
      Extension <input type="text" id="OfficeDeskExtension" maxlength="10" style="width: 40px;" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Office Mobile Number</td>
    <td width="60%" class="value"><input type="text" id="OfficeMobileNumber" maxlength="16" /></td>
  </tr>
  <tr>
    <td width="40%" class="label">Office Email ID</td>
    <td width="60%" class="value"><input type="text" id="OfficeEmailId" maxlength="32" /></td>
  </tr>
  <tr>
    <td width="40%" class="label">Send Email Notifications To</td>
    <td width="60%" class="value">
      <select id="EmailNotificationTo">
        <option value="">None</option>
        <c:forEach var="EmployeeNotificationToValue" items="${EmployeeNotificationToValues}">
        <option value="${EmployeeNotificationToValue.notificationToCode}">${EmployeeNotificationToValue}</option>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Send SMS Notifications To</td>
    <td width="60%" class="value">
      <select id="SmsNotificationTo">
        <option value="">None</option>
        <c:forEach var="EmployeeNotificationToValue" items="${EmployeeNotificationToValues}">
        <option value="${EmployeeNotificationToValue.notificationToCode}">${EmployeeNotificationToValue}</option>
        </c:forEach>
      </select>
    </td>
  </tr>
</table>
</c:if>

<c:if test="${EmployeeContact != null}">
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td width="40%" class="label">Present Address<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <textarea id="PresentAddress" rows="5" maxlength="512" >${EmployeeContact.presentAddress}</textarea>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Permanent Address</td>
    <td width="60%" class="value">
      <textarea id="PermanentAddress" rows="5" maxlength="512" >${EmployeeContact.permanentAddress}</textarea>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Personal Mobile Number<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="PersonalMobileNumber" maxlength="16" value="${EmployeeContact.personalMobileNumber}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Personal Email ID</td>
    <td width="60%" class="value">
      <input type="text" id="PersonalEmailId" maxlength="32" value="${EmployeeContact.personalEmailId}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Emergency Contact Number<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="EmergencyContactNumber" maxlength="16" value="${EmployeeContact.emergencyContactNumber}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Emergency Contact Relationship<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="hidden" id="EmergencyContactRelationshipCode" value="${EmployeeContact.emergencyContactRelationship.code}" />
      <select id="EmergencyContactRelationship" class="chosen-select">
      </select>
	</td>
  </tr>
  <tr>
    <td width="40%" class="label">Office Desk Phone Number</td>
    <td width="60%" class="value">
      <input type="text" id="OfficeDeskPhoneNumber" maxlength="16" style="width: 120px;" value="${EmployeeContact.officeDeskPhoneNumber}" />
      Extension <input type="text" id="OfficeDeskExtension" maxlength="10" style="width: 40px;" value="${EmployeeContact.officeDeskExtension}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Office Mobile Number</td>
    <td width="60%" class="value">
      <input type="text" id="OfficeMobileNumber" maxlength="16" value="${EmployeeContact.officeMobileNumber}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Office Email ID</td>
    <td width="60%" class="value">
      <input type="text" id="OfficeEmailId" maxlength="32" value="${EmployeeContact.officeEmailId}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Send Email Notifications To</td>
    <td width="60%" class="value">
      <input type="hidden" id="EmailNotificationToCode" value="${EmployeeContact.emailNotificationTo}" />
      <select id="EmailNotificationTo">
        <option value="">None</option>
        <c:forEach var="EmployeeNotificationToValue" items="${EmployeeNotificationToValues}">
        <option value="${EmployeeNotificationToValue.notificationToCode}">${EmployeeNotificationToValue}</option>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Send SMS Notifications To</td>
    <td width="60%" class="value">
      <input type="hidden" id="SmsNotificationToCode" value="${EmployeeContact.smsNotificationTo}" />
      <select id="SmsNotificationTo">
        <option value="">None</option>
        <c:forEach var="EmployeeNotificationToValue" items="${EmployeeNotificationToValues}">
        <option value="${EmployeeNotificationToValue.notificationToCode}">${EmployeeNotificationToValue}</option>
        </c:forEach>
      </select>
    </td>
  </tr>
</table>
</c:if>
