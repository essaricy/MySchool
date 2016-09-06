<%@page import="com.myschool.user.constants.UserType"%>
<%@page import="com.myschool.application.constants.IssueStatus"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<script type="text/javascript" charset="utf-8">
var oTable = null;
$(document).ready(function() {
  activateActionButtons(actionButtons);
  $('#update').tooltipster();

  oTable = $('#IssuesTable').dataTable({
    "bPaginate": true,
    "bAutoWidth": false,
    "bRetrieve": true,
    "bDestroy": true,
	"iDisplayLength": iDisplayLength,
    "sAjaxSource": '<%=request.getContextPath()%>/dummy/jsonList.htm?sid=' + new Date().getTime(),
    "aoColumnDefs": [ {
      "bSearchable": false,
      "bVisible": false,
      "aTargets": [ 0, 1, 3 ]
    }],
    "sPaginationType": "full_numbers",
    "bJQueryUI": false
  });

  $("#IssuesTable tbody").click(function(event) {
    $(oTable.fnSettings().aoData).each(function (){
      $(this.nTr).removeClass('row_selected');
    });
    $(event.target.parentNode).addClass('row_selected');
  });

  $('#update').click( function() {
    var anSelected = fnGetSelected(oTable);
    if (anSelected == null) {
      info_ac('<spring:message code="common.selectRow.update"/>');
    } else {
      var IssueId = $(oTable.fnGetData(anSelected)[0]).html();
      var subject = $(oTable.fnGetData(anSelected)[5]).html();
      openWindow('<%=request.getContextPath()%>/issue/maintainIssue.htm?IssueId=' + IssueId + '&sid=' + new Date().getTime(),
        'Issue# ' + subject, $(window).width()-100, $(window).height()-50);
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
function reloadIssuesTable(url) {
  $('#IssuesTableContainer').show();

  oSettings = oTable.fnSettings();
  oSettings.sAjaxSource = url;

  oTable.fnClearTable(oTable);
  var jsonFunction = $.getJSON(oSettings.sAjaxSource, function(respnose) {
    activateActionButtons(actionButtons);
    if (respnose.aaData != '') {
      for (var i=0; i<respnose.aaData.length; i++) {
        oTable.oApi._fnAddData(oSettings, respnose.aaData[i]);
      }
      oTable.fnDraw(oTable);
      oTable.oApi._fnProcessingDisplay(oSettings, false);
      // activateActionButtons(respnose.aaData.length > 0);
      activateActionButtons(actionButtons);
    }
  });
}
function activateActionButtons(actionButtons) {
  if (actionButtons == null || typeof(actionButtons) == 'undefined') {
      $("#update").hide(1000);
  } else {
      $("#update").show(1000);
  }
}
</script>

<%@ include file="/views/application/issue_search_criteria.jsp" %>

<table width="90%" align="center" cellpadding="0" cellspacing="0" border="0" class="display" id="IssuesTable">
  <thead>
    <tr>
      <th>Issue ID</th>
      <th>User Type ID</th>
      <th>User Type</th>
      <th>Status ID</th>
      <th>Status</th>
      <th>Reported Date</th>
      <th>Closed Date</th>
      <th>Subject</th>
      <th>Description</th>
      <th>Contact Email Id</th>
    </tr>
  </thead>
  <tbody></tbody>
  <tfoot>
    <tr>
      <th colspan="10" align="right">
        <img id="update" src="<%=request.getContextPath()%>/images/icons/update.png" class="iconImage" title="Update Issue" />
      </th>
    </tr>
  </tfoot>
</table>

<myschool:actions />
