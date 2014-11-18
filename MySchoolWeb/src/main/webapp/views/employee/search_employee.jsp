<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %> 

<script type="text/javascript" charset="utf-8">
var oTable = null;
$(document).ready(function() {
  //$('#EmployeesTableContainer').hide();
  activateActionButtons(actionButtons);
  $('#add').tooltipster();
  $('#update').tooltipster();

  oTable = $('#EmployeesTable').dataTable({
    "bPaginate": true,
    "bAutoWidth": false,
    "bRetrieve": true,
    "bDestroy": true,
    "sAjaxSource": '<%=request.getContextPath()%>/dummy/jsonList.htm?sid=' + new Date().getTime(),
    "aoColumnDefs": [ {
      "bSearchable": false,
      "bVisible": false,
      "aTargets": [ 7 ]
    }],
    "aoColumns": [
      {
        "fnRender": function ( o, val ) {
          var imagePath = '<%=request.getContextPath()%>/image/getImage.htm?type=employee&imageSize=THUMBNAIL&contentId=' + o.aData[1]
            + '&sid=<%= new java.util.Date().getTime()%>';
          var linkedImage ='<a href="javascript: showImage(' + o.aData[1] + ')"><img src="' + imagePath + '" class="thumbnail" /></a>';
          return linkedImage;
        }
      }, { 
        "fnRender": function ( o, val ) {
          var linkContent = '<a href="' + 'javascript: showEmployee('
            + '\'' + o.aData[1] + '\', \'' + o.aData[2] + '\', \'' + o.aData[3] + '\', \'' + o.aData[4] + '\')' + '\">'
            + o.aData[1] + '</a>';
          return linkContent;
        }
      }, { 
        "fnRender": function ( o, val ) {
          return o.aData[2] + ' ' + o.aData[3] + ' ' + o.aData[4];
        }
      }, { 
        "fnRender": function ( o, val ) {
          return o.aData[5];
        }
      }, { 
        "fnRender": function ( o, val ) {
          return o.aData[6];
        }
      }, { 
        "fnRender": function ( o, val ) {
          return o.aData[7];
        }
      }, { 
        "fnRender": function ( o, val ) {
          return o.aData[8];
        }
      }, { 
        "fnRender": function ( o, val ) {
          return o.aData[9];
        }
      }, { 
        "fnRender": function ( o, val ) {
          return o.aData[10];
        }
      }, { 
        "fnRender": function ( o, val ) {
          return o.aData[11] + ' - ' + o.aData[12] + ' ' + o.aData[13] + ' ' + o.aData[14];
        }
      }
    ],
    "sPaginationType": "full_numbers",
    "bJQueryUI": false
  });

  $("#EmployeesTable tbody").click(function(event) {
    $(oTable.fnSettings().aoData).each(function (){
      $(this.nTr).removeClass('row_selected');
    });
    $(event.target.parentNode).addClass('row_selected');
  });

  $('#add').click( function() {
    submitPage('<%=request.getContextPath()%>/employee/launch.htm', '');
  });

  $('#update').click( function() {
    var anSelected = fnGetSelected(oTable);
    if (anSelected == null) {
      info_ac('<spring:message code="common.selectRow.update"/>');
    } else {
      $('#employeeNumber').val($(oTable.fnGetData(anSelected)[1]).html());
      submitPage('<%=request.getContextPath()%>/employee/launch.htm', '');
    }
  });

  function fnGetSelected( oTableLocal ){
    var aReturn = new Array();
    var selected = null;
    var aTrs = oTableLocal.fnGetNodes();
    for ( var i=0 ; i<aTrs.length ; i++ ) {
      if ( $(aTrs[i]).hasClass('row_selected') ) {
        aReturn.push( aTrs[i] );
        selected = aTrs[i];
      }
    }
    return selected;
  }
});

function reloadEmployeesTable(url) {
  //$('#TemplatesTable_Outer').hide();
  $('#EmployeesTableContainer').show();

  oSettings = oTable.fnSettings();
  oSettings.sAjaxSource = url;

  oTable.fnClearTable(oTable);
  var jsonFunction = $.getJSON(oSettings.sAjaxSource, function(respnose) {
    activateActionButtons(actionButtons);
    if (respnose.EmployeesData != '') {
      for (var i=0; i<respnose.EmployeesData.length; i++) {
        oTable.oApi._fnAddData(oSettings, respnose.EmployeesData[i]);
      }
      oTable.fnDraw(oTable);
      oTable.oApi._fnProcessingDisplay(oSettings, false);
      // activateActionButtons(respnose.EmployeesData.length > 0);
      activateActionButtons(actionButtons);
    }
  });
}

function activateActionButtons(actionButtons) {
  if (actionButtons == null || typeof(actionButtons) == 'undefined') {
      $("#add").hide(1000);
      $("#update").hide(1000);
  } else {
      for (var index=0; index< actionButtons.length; index++) {
          var actionButton = actionButtons[index];
          if (actionButton == 'add') {
              $("#add").show(1000);
          } else if (actionButton == 'update') {
              if (oTable != null && oTable.fnSettings().fnRecordsTotal() > 0) {
                  $("#update").show(1000);
              }
          }
      }
  }
}

function showImage(employeeNumber) {
  $.magnificPopup.open({
    items: {
      src: '<%=request.getContextPath()%>/image/getImage.htm?type=employee&imageSize=ORIGINAL&contentId=' + employeeNumber
    },
    type: 'image' // this is default type
  });
}

function showEmployee(employeNumber, firstName, middleName, lastName) {
  openDialog('<%=request.getContextPath()%>/employee/viewEmployee.htm?employeeNumber=' + employeNumber + '&sid=' + new Date().getTime(),
  employeNumber + ' - ' + firstName + ' ' + middleName + ' ' + lastName,
  $(window).width()-100, $(window).height()-50);
}
</script>
<div id="imagePopup"></div>
<%@ include file="/views/employee/employee_search_criteria.jsp" %>
<input type="hidden" id="employeeNumber" name="employeeNumber" value="" />

<div id="EmployeesTableContainer">
  <table width="90%" align="center" cellpadding="0" cellspacing="0" border="0" class="display" id="EmployeesTable">
    <thead>
      <tr>
        <th>&nbsp;</th>
        <th><spring:message code="employee.number"/></th>
        <th>Name</th>
        <th><spring:message code="common.gender"/></th>
        <th><spring:message code="common.dateOfBirth"/></th>
        <th><spring:message code="employee.designation"/></th>
        <th>Employed At</th>
        <th>Employment Start Date</th>
        <th>Employment Status</th>
        <th>Reporting To Employee Number</th>
      </tr>
    </thead>
    <tbody></tbody>
    <tfoot>
      <tr>
        <th colspan="10" align="right">
        <c:choose>
          <c:when test="${PAGE_ACCESS != null}">
            <c:if test="${PAGE_ACCESS.create}">
            <img id="add" src="<%=request.getContextPath()%>/images/icons/add.png" class="iconImage" title="Add Employee" />
            </c:if>
            <c:if test="${PAGE_ACCESS.update}">
            <img id="update" src="<%=request.getContextPath()%>/images/icons/update.png" class="iconImage" title="Update Employee" />
            </c:if>
          </c:when>
          <c:otherwise>
            &nbsp;
          </c:otherwise>
        </c:choose>
        </th>
      </tr>
    </tfoot>
  </table>
</div>
<myschool:actions />
