<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<script type="text/javascript" charset="utf-8">
var oTable = null;
$(document).ready(function() {
  oTable = $('#ActiveTable').activeTable({
    width: "60%",
    caption: 'Brouchers',
    columns: [
      'URL',
      '<spring:message code="download.title"/>',
      '<spring:message code="download.type"/>'
    ],
    dataTableSettings: {
      "bPaginate": true,
      "sAjaxSource": '<%=request.getContextPath()%>/download/jsonBrochuresList.htm',
      "aoColumnDefs": [ {
        "bSearchable": false,
        "bVisible": false,
        "aTargets": [ 0 ]
      }],
      "sPaginationType": "full_numbers",
    },
    buttons: actionButtons,
    'report': {
      callback: showBrochure
    },
  });

  function showBrochure() {
    oTable = $("#ActiveTable_Inner").dataTable();
    var anSelected = fnGetSelected(oTable);
    if (anSelected != null) {
      var selectedRow = oTable.fnGetData(anSelected);
      var brochureUrl = selectedRow[0];
      var brochureName = selectedRow[1];
      openWindow(brochureUrl, brochureName, $(window).width()-100, $(window).height()-60);
    }
  }

  function fnGetSelected( oTableLocal ) {
    var selected = null;
    var aTrs = oTableLocal.fnGetNodes();
    for ( var i=0 ; i<aTrs.length ; i++ ) {
      if ( $(aTrs[i]).hasClass('row_selected') ) {
        selected = aTrs[i];
      }
    }
    return selected;
  }
});

</script>

<div id="ActiveTable"></div>
<myschool:actions add="false" update="false" delete="false" report="true" print="true" />
