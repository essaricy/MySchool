<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<script type="text/javascript" charset="utf-8">
var oTable = null;
$(document).ready(function() {
  activateActionButtons(actionButtons);
  $('#add').tooltipster();
  $('#update').tooltipster();

  oTable = $('#StudentsTable').dataTable({
    "bPaginate": true,
    "bAutoWidth": false,
    "bRetrieve": true,
    "bDestroy": true,
    "sAjaxSource": '<%=request.getContextPath()%>/dummy/jsonList.htm?sid=' + new Date().getTime(),
    "aoColumnDefs": [ {
      "bSearchable": false,
      "bVisible": false,
      "aTargets": [ ]
    }],
    "aoColumns": [
      {
        // Image
        "fnRender": function ( o, val ) {
          var imagePath = '<%=request.getContextPath()%>/image/getImage.htm?type=student&imageSize=THUMBNAIL&contentId=' + o.aData[1]
            + '&sid=<%= new java.util.Date().getTime()%>';
          var linkedImage ='<a href="javascript: showImage(' + o.aData[1] + ')"><img src="' + imagePath + '" class="thumbnail" /></a>';
          return linkedImage;
        }
      }, { 
        // Admission Number
        "fnRender": function ( o, val ) {
          var linkContent = '<a href="' + 'javascript: showStudent('
            + '\'' + o.aData[1] + '\', \'' + o.aData[2] + '\', \'' + o.aData[3] + '\', \'' + o.aData[4] + '\')' + '\">'
            + o.aData[1] + '</a>';
          return linkContent;
        }
      }, { 
        // Name
        "fnRender": function ( o, val ) {
          return o.aData[2] + ' ' + o.aData[3] + ' ' + o.aData[4];
        }
      }, { 
        //Gender
        "fnRender": function ( o, val ) {
          return o.aData[5];
        }
      }, { 
        // Blood Group
        "fnRender": function ( o, val ) {
          return o.aData[7];
        }
      }, { 
        // Date Of Birth
        "fnRender": function ( o, val ) {
          return o.aData[6];
        }
      }, { 
        // Date Of JOining
        "fnRender": function ( o, val ) {
          return o.aData[29];
        }
      }, { 
        // Branch
        "fnRender": function ( o, val ) {
          return o.aData[18];
        }
      }, { 
        // Division
        "fnRender": function ( o, val ) {
          return o.aData[20];
        }
      }, { 
        // School
        "fnRender": function ( o, val ) {
          return o.aData[22];
        }
      }, { 
        // Class
        "fnRender": function ( o, val ) {
          return o.aData[24] + '~' + o.aData[26] + '~' + o.aData[28];
        }
      }
    ],
    "sPaginationType": "full_numbers",
    "bJQueryUI": false
  });

  $("#StudentsTable tbody").click(function(event) {
    $(oTable.fnSettings().aoData).each(function (){
      $(this.nTr).removeClass('row_selected');
    });
    $(event.target.parentNode).addClass('row_selected');
  });

  $('#add').click( function() {
    submitPage('<%=request.getContextPath()%>/student/launch.htm', '');
  });

  $('#update').click( function() {
    var anSelected = fnGetSelected(oTable);
    if (anSelected == null) {
      info_ac('<spring:message code="common.selectRow.update"/>');
    } else {
      $('#Selected_AdmissionNumber').val($(oTable.fnGetData(anSelected)[1]).html());
      submitPage('<%=request.getContextPath()%>/student/launch.htm', '');
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

function reloadStudentsTable(url) {
  $('#StudentsTableContainer').show();

  oSettings = oTable.fnSettings();
  oSettings.sAjaxSource = url;

  oTable.fnClearTable(oTable);
  var jsonFunction = $.getJSON(oSettings.sAjaxSource, function(respnose) {
    activateActionButtons(actionButtons);
    if (respnose.StudentsData != '') {
      for (var i=0; i<respnose.StudentsData.length; i++) {
        oTable.oApi._fnAddData(oSettings, respnose.StudentsData[i]);
      }
      oTable.fnDraw(oTable);
      oTable.oApi._fnProcessingDisplay(oSettings, false);
      // activateActionButtons(respnose.StudentsData.length > 0);
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

function showImage(admissionNumber) {
  $.magnificPopup.open({
    items: {
      src: '<%=request.getContextPath()%>/image/getImage.htm?type=student&imageSize=ORIGINAL&contentId=' + admissionNumber
    },
    type: 'image' // this is default type
  });
}

function showStudent(admissionNumber, firstName, middleName, lastName) {
  openDialog('<%=request.getContextPath()%>/student/viewStudent.htm?admissionNumber=' + admissionNumber + '&sid=' + new Date().getTime(),
  admissionNumber + ' - ' + firstName + ' ' + middleName + ' ' + lastName,
  $(window).width()-100, $(window).height()-50);
}

</script>

<%@ include file="/views/student/student_search_criteria.jsp" %>

<input type="hidden" id="Selected_AdmissionNumber" name="AdmissionNumber" value="" />
<table width="90%" align="center" cellpadding="0" cellspacing="0" border="0" class="display" id="StudentsTable">
  <thead>
    <tr>
      <th>&nbsp;</th>
      <th><spring:message code="student.admissionNumber"/></th>
      <th>Name</th>
      <th><spring:message code="common.gender"/></th>
      <th><spring:message code="common.bloodGroup"/></th>
      <th><spring:message code="common.dateOfBirth"/></th>
      <th><spring:message code="common.dateOfJoining"/></th>
      <th>Branch</th>
      <th>Division</th>
      <th>School</th>
      <th>Class</th>
    </tr>
  </thead>
  <tbody></tbody>
  <tfoot>
    <tr>
      <th colspan="14" align="right">
        <c:choose>
          <c:when test="${PAGE_ACCESS != null}">
            <c:if test="${PAGE_ACCESS.create}">
            <img id="add" src="<%=request.getContextPath()%>/images/icons/add.png" class="iconImage" title="Add Student" />
            </c:if>
            <c:if test="${PAGE_ACCESS.update}">
            <img id="update" src="<%=request.getContextPath()%>/images/icons/update.png" class="iconImage" title="Update Student" />
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
<br />

<myschool:actions add="true" update="true" delete="true" />
