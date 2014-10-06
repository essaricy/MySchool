<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
  $('#ActiveTable').activeTable({
    width: "60%",
    caption: 'Brouchers',
    columns: [
      'Brochure',
      '<spring:message code="download.title"/>',
      '<spring:message code="download.type"/>',
      '<spring:message code="common.lastUpdated"/>'
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
      title: 'View Brochure',
      url: '<%=request.getContextPath()%>/download/getBrochure.htm',
      width: $(window).width() - 100,
      height: $(window).height() - 60,
      selectRowMessage: 'Select a record to view.',
      sendParams: [ {
        refTable: 'self',
        columnIndex: 0,
        paramName: 'brochureFileName'
      } ]
    },
  });
});
</script>

<div id="ActiveTable"></div>
<myschool:actions add="false" update="false" delete="false" report="true" print="true" />
