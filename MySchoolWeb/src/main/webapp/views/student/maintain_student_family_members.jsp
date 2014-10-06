<style>
#StudentFamilyMembersTable tr th,
#StudentFamilyMembersTable tr td {
  font-size: 0.8em;
}
</style>
<script type="text/javascript">
var StudentFamilyMember_dataTableSettings = null;
var studentFamilyMemberAttributeSequence = ['FamilyMemberId', 'RelationshipCode', 'RelationshipName', 'FamilyMemberName', 'Occupation', 'MobileNumber', 'EmailID', 'AvailSMS', 'AvailEmail' ];
var StudentFamilyMember_columnNames = [
    'Family Member Id',
    'Relationship Code',
    '<spring:message code="common.relationship"/>',
    '<spring:message code="common.name"/>',
    '<spring:message code="common.occupation"/>',
    '<spring:message code="common.mobileNumber"/>',
    '<spring:message code="common.emailid"/>',
    '<spring:message code="common.avail.sms"/>',
    '<spring:message code="common.avail.email"/>' ];

<c:if test="${Student == null}">
StudentFamilyMember_dataTableSettings = {
  "bPaginate": false,
  "bFilter": false,
  "bInfo": false,
  "bJQueryUI": false,
  "sPaginationType": "full_numbers",
  "aoColumnDefs": [ { "bSearchable": false, "bVisible": false, "aTargets": [ 0, 1 ] } ],
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
          if (o.aData[7] == 'true') {
              //return '<img src="<%=request.getContextPath()%>/images/icons/checked.png" class="iconImage" />';
              return 'YES';
          } else {
              return 'NO';
          }
        }
      }, { 
        // Avail Email
        "fnRender": function ( o, val ) {
          if (o.aData[8] == 'true') {
              //return '<img src="<%=request.getContextPath()%>/images/icons/checked.png" class="iconImage" />';
              return 'YES';
          } else {
              return 'NO';
          }
        }
      }
    ]
  }
</c:if>

<c:if test="${Student != null}">
var StudentFamilyMember_url = '<%=request.getContextPath()%>/student-attribute/jsonList.htm?attribute=StudentFamilyMember&AdmissionNumber=' + $('#AdmissionNumber').val() + '&sid=' + new Date().getTime();
StudentFamilyMember_dataTableSettings = {
  "bPaginate": false,
  "bFilter": false,
  "bInfo": false,
  "bJQueryUI": false,
  "sPaginationType": "full_numbers",
  "aoColumnDefs": [ { "bSearchable": false, "bVisible": false, "aTargets": [ 0, 1] } ],
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
              //return '<img src="<%=request.getContextPath()%>/images/icons/checked.png" class="iconImage" />';
              return 'YES';
          } else {
              return 'NO';
          }
        }
      }, { 
        // Avail Email
        "fnRender": function ( o, val ) {
          if (o.aData[8]) {
              //return '<img src="<%=request.getContextPath()%>/images/icons/checked.png" class="iconImage" />';
              return 'YES';
          } else {
              return 'NO';
          }
        }
      }
    ]
  }
</c:if>

$(document).ready(function(){
  $('#StudentFamilyMembersTable').activeTable( {
    containerName: 'StudentFamilyMembersTable',
    title: 'Family Member',
    width: "100%",
    columns: StudentFamilyMember_columnNames,
    dataTableSettings: StudentFamilyMember_dataTableSettings,
    buttons: ['add', 'update', 'delete'],
    'add' : {
      title: 'Add Family Member',
      url: '<%=request.getContextPath()%>/student-attribute/launch.htm',
      sendParams: [ {
        refTable: 'none',
        paramName: 'attribute',
        paramValue: 'StudentFamilyMember'
      }],
      width: 420,
      height: 320
    },
    'update': {
      title: 'Update Family Member',
      url: '<%=request.getContextPath()%>/student-attribute/launch.htm',
      width: 420,
      height: 320,
      selectRowMessage: '<spring:message code="common.selectRow.update"/>',
      sendParams: [ {
        refTable: 'none',
        paramName: 'attribute',
        paramValue: 'StudentFamilyMember'
      }, {
        refTable: 'self',
        paramName: 'StudentFamilyMember',
        columnIndex: -1,
        columnNames: studentFamilyMemberAttributeSequence
      } ]
    },
    'delete': {
      title: 'Delete Family Member',
      url: '<%=request.getContextPath()%>/student-attribute/doDelete.htm',
      selectRowMessage: '<spring:message code="common.selectRow.delete"/>',
      confirmMessage: '<spring:message code="branch.delete.warning"/>',
      confirmCallback: deleteStudentFamilyMember
    }
  });

  function deleteStudentFamilyMember(decision) {
    if (decision == "Yes") {
      var anSelected = fnGetSelected( currentDataTable );
      if (anSelected != null) {
        var selectedRow = currentDataTable.fnGetData(anSelected);
        var studentFamilyMemberId = selectedRow[0];
        if (studentFamilyMemberId > 0) {
          $.ajax({
            url: "<%=request.getContextPath()%>/student-attribute/doDelete.htm?attribute=StudentFamilyMember&attributeId=" + studentFamilyMemberId,
            context: document.body,
            success: function(result) {
              $(this).addClass("done");
            }
          });
        }
        currentDataTable.fnDeleteRow(anSelected);
      }
    }
  }
});

function getStudentFamilyMembers() {
  return getStudentAttributesData('CREATE', null, 'StudentFamilyMember', studentFamilyMemberAttributeSequence);
}
</script>

<div id="StudentFamilyMembersTable"></div>
