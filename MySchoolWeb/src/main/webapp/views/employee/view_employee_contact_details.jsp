<%@page import="com.myschool.employee.constant.EmployeeNotificationTo"%>

<c:set var="EmployeeNotificationToValues" value="<%=EmployeeNotificationTo.values()%>"/>
<c:if test="${EmployeeContact != null}">
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td width="40%" class="label">Present Address</td>
    <td width="60%" class="value">${EmployeeContact.presentAddress}</td>
  </tr>
  <tr>
    <td width="40%" class="label">Permanent Address</td>
    <td width="60%" class="value">${EmployeeContact.permanentAddress}</td>
  </tr>
  <tr>
    <td width="40%" class="label">Personal Mobile Number</td>
    <td width="60%" class="value">${EmployeeContact.personalMobileNumber}</td>
  </tr>
  <tr>
    <td width="40%" class="label">Personal Email ID</td>
    <td width="60%" class="value">${EmployeeContact.personalEmailId}</td>
  </tr>
  <tr>
    <td width="40%" class="label">Emergency Contact Number</td>
    <td width="60%" class="value">${EmployeeContact.emergencyContactNumber}</td>
  </tr>
  <tr>
    <td width="40%" class="label">Emergency Contact Relationship</td>
    <td width="60%" class="value">${EmployeeContact.emergencyContactRelationship.name}</td>
  </tr>
  <tr>
    <td width="40%" class="label">Office Desk Phone Number</td>
    <td width="60%" class="value">${EmployeeContact.officeDeskPhoneNumber}</td>
  </tr>
  <tr>
    <td width="40%" class="label">Office Desk Extension</td>
    <td width="60%" class="value">${EmployeeContact.officeDeskExtension}</td>
  </tr>
  <tr>
    <td width="40%" class="label">Office Mobile Number</td>
    <td width="60%" class="value">${EmployeeContact.officeMobileNumber}</td>
  </tr>
  <tr>
    <td width="40%" class="label">Office Email ID</td>
    <td width="60%" class="value">${EmployeeContact.officeEmailId}</td>
  </tr>
  <tr>
    <td width="40%" class="label">Send Email Notifications To</td>
    <td width="60%" class="value">${EmployeeContact.emailNotificationTo}</td>
  </tr>
  <tr>
    <td width="40%" class="label">Send SMS Notifications To</td>
    <td width="60%" class="value">${EmployeeContact.smsNotificationTo}</td>
  </tr>
</table>
</c:if>
