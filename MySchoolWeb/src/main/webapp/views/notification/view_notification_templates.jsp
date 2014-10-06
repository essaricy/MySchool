<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript" charset="utf-8">

function reloadNotificationTeplatesTable(url) {
  $('#NotificationTemplatesTableContainer').show();

  oSettings = oTable.fnSettings();
  oSettings.sAjaxSource = url;

  oTable.fnClearTable(oTable);
  var jsonFunction = $.getJSON(oSettings.sAjaxSource, function(respnose) {
    if (respnose.NotificationTemplates != '') {
      for (var i=0; i<respnose.NotificationTemplates.length; i++) {
        oTable.oApi._fnAddData(oSettings, respnose.NotificationTemplates[i]);
      }
      oTable.fnDraw(oTable);
      oTable.oApi._fnProcessingDisplay(oSettings, false);
    }
  });
}

$(document).ready(function() {
  oTable = $('#NotificationTemplatesTable').dataTable({
    "bPaginate": true,
    "bAutoWidth": false,
    "bRetrieve": true,
    "bDestroy": true,
    "sAjaxSource": '<%=request.getContextPath()%>/dummy/jsonList.htm?sid=' + new Date().getTime(),
    "aoColumnDefs": [ {
      "bSearchable": false,
      "bVisible": false,
      "aTargets": [ 0 ]
    }],
    "sPaginationType": "full_numbers",
    "bJQueryUI": false,
    "aoColumns": [ 
        {
          "sWidth": "20%"
        }, {
          "sWidth": "20%"
        }, {
          "sWidth": "20%"
        }, {
          "sWidth": "40%",
          "fnRender": function ( o, val ) {
            return '<a href="javascript: showNotificationTemplate(\'' + o.aData[0] + '\', \'' + o.aData[1] + '\', \'' +  o.aData[2] + '\', \'' + o.aData[3] + '\')">' + o.aData[3] + '</a>'; 
          }
        }
      ]
  });

  $("#NotificationTemplatesTable tbody").click(function(event) {
    $(oTable.fnSettings().aoData).each(function (){
      $(this.nTr).removeClass('row_selected');
    });
    $(event.target.parentNode).addClass('row_selected');
  });
});

function showNotificationTemplate(templateId, endPoint, mode, type) {
  openDialog('<%=request.getContextPath()%>/notification/getTemplate.htm?templateId=' + templateId + '&sid=' + new Date().getTime(),
  endPoint + ' - ' + type + ' (' + mode + ')',
  $(window).width()-100, $(window).height()-50);
}
</script>

<%@ include file="/views/notification/notification_search_criteria.jsp" %>
<div id="NotificationTemplatesTableContainer">
  <table width="90%" align="center" cellpadding="0" cellspacing="0" border="0" class="display" id="NotificationTemplatesTable">
    <thead>
      <tr>
        <th><spring:message code="notification.id"/></th>
        <th><spring:message code="notification.endPoint"/></th>
        <th><spring:message code="notification.mode"/></th>
        <th><spring:message code="notification.type"/></th>
      </tr>
    </thead>
    <tbody></tbody>
  </table>
</div>
