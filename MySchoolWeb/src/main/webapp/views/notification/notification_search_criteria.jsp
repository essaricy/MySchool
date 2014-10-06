<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<style>
.ui-tabs .ui-tabs-nav li a {font-size: 0.6em !important;}
</style>

<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/widgets/jquery-ui-1.10.2/ui/jquery.ui.tabs.js"></script>
<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
  $("#SearchTabs").tabs();

  $(this).lazySelect({id: "Search_EndPoint", url: '<%=request.getContextPath()%>/notification/jsonListNotificationEndPoints.htm'});
  $(this).lazySelect({id: "Search_Mode", url: '<%=request.getContextPath()%>/notification/jsonListNotificationModes.htm'});
  $(this).lazySelect({id: "Search_Type", url: '<%=request.getContextPath()%>/notification/jsonListNotificationTypes.htm'});

  $('#Search').click(function() {
    var NotificationCriteria = new Object();
    NotificationCriteria.EndPoint=$('#Search_EndPoint').val();
    NotificationCriteria.Mode=$('#Search_Mode').val();
    NotificationCriteria.Type=$('#Search_Type').val();
	NotificationCriteria.Status='';
	NotificationCriteria.DateMin='';
	NotificationCriteria.DateMax='';
    var searchString = 'NotificationCriteria=' + JSON.stringify(NotificationCriteria) + '&sid=' + new Date().getTime();
    reloadNotificationTeplatesTable('<%=request.getContextPath()%>/notification/jsonList.htm?' + searchString);
  });
});

</script>

<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <caption class="dataTableCaption">Notification Templates</caption>
  <tr>
    <td>
      <div id="SearchTabs">
        <ul>
          <li><a href="#SearchCriteria">Search</a></li>
        </ul>
        <div id="SearchCriteria">
          <table class="formDataTable" align="center" border="0" cellspacing="5" cellpadding="5" width="100%">
            <tr>
              <td align="right">End Point</td>
              <td>
                <select id="Search_EndPoint" class="chosen-select">
                  <option value="">Any</option>
                </select>
              </td>
              <td align="right">Mode</td>
              <td>
                <select id="Search_Mode" class="chosen-select">
                  <option value="">Any</option>
                </select>
              </td>
              <td align="right">Type</td>
              <td>
                <select id="Search_Type" class="chosen-select">
                  <option value="">Any</option>
                </select>
              </td>
              <td align="right">&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td align="right" colspan="6">
                <input type="button" id="Search" value="Search" class="formButton" />
              </td>
            </tr>
          </table>
        </div>
      </div>
    </td>
  </tr>
</table>
