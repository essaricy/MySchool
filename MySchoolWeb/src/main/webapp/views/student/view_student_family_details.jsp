<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<style>
#StudentFamilyMembersTable tr th,
#StudentFamilyMembersTable tr td {
  font-size: 0.8em;
}
</style>

<script type="text/javascript">
var StudentFamilyMember_columnNames = [
  'Family Member ID',
  'Relationship Code',
  '<spring:message code="common.relationship"/>',
  '<spring:message code="common.name"/>',
  '<spring:message code="common.occupation"/>',
  '<spring:message code="common.mobileNumber"/>',
  '<spring:message code="common.emailid"/>',
  '<spring:message code="common.avail.sms"/>',
  '<spring:message code="common.avail.email"/>'];
var StudentFamilyMember_url = '<%=request.getContextPath()%>/student-attribute/jsonList.htm?attribute=StudentFamilyMember&AdmissionNumber=' + $('#AdmissionNumber').val() + '&sid=' + new Date().getTime();
var StudentFamilyMember_dataTableSettings = {
  "bPaginate": false,
  "bFilter": false,
  "bInfo": false,
  "bJQueryUI": false,
  "sPaginationType": "full_numbers",
  "aoColumnDefs": [ { "bSearchable": false, "bVisible": false, "aTargets": [ 0, 1 ] } ],
  "sAjaxSource": StudentFamilyMember_url,
  "aoColumns": [
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      { 
        // Avail SMS
        "fnRender": function ( o, val ) {
          if (o.aData[7]) {
              return '<img src="<%=request.getContextPath()%>/images/icons/checked.png" class="iconImage" />';
          } else {
              return ' ';
          }
        }
      }, { 
        // Avail Email
        "fnRender": function ( o, val ) {
          if (o.aData[8]) {
              return '<img src="<%=request.getContextPath()%>/images/icons/checked.png" class="iconImage" />';
          } else {
              return ' ';
          }
        }
      }
    ]
}

$(document).ready(function(){
  $('#StudentFamilyMembersTable').activeTable( {
    containerName: 'StudentFamilyMembersTable',
    title: 'Teaching Subject',
    width: "100%",
    columns: StudentFamilyMember_columnNames,
    dataTableSettings: StudentFamilyMember_dataTableSettings,
  });
});

</script>
<div id="StudentFamilyMembersTable"></div>
