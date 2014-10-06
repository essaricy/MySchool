<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<script>
//alert(announcementType);
$(document).ready(function() {
  var announcementType = '<%=request.getParameter("ANNOUNCEMENT_TYPE")%>';
  var announcementsOf = '<%=request.getParameter("ANNOUNCEMENTS_OF")%>';
  var url = '';
  if (announcementType == 'PUBLIC') {
    url = '<%=request.getContextPath()%>/announcement/jsonPublicAnnouncements.htm';
  } else if (announcementType == 'PRIVATE') {
      if (announcementsOf == 'STUDENT') {
          url = '<%=request.getContextPath()%>/student-dashboard/privateAnnouncements.htm';
      }
  }

  jQuery.ajax({
    type: "POST",
    url: url,
    async: false
  }).done(function(announcements) {
    var bullet = '<img src="<%=request.getContextPath()%>/images/icons/star.png" style="padding-right: 5px;" width="10px" height="10px"/>'

    <c:if test="${USER_CONTEXT.userType == 'ADMIN'}">
      var maxAnnouncements = 5;
    </c:if>
    <c:if test="${USER_CONTEXT.userType == 'STUDENT'}">
      var maxAnnouncements = 8;
    </c:if>
    <c:if test="${USER_CONTEXT.userType == 'EMPLOYEE'}">
      var maxAnnouncements = 8;
    </c:if>

    for (var index=0; index<announcements.length; index++) {
      var announcementDataRow = $('<tr>');
      var announcementData = $('<td>');
      announcementData.append(bullet).append(announcements[index]);
      announcementDataRow.append(announcementData);
      $('#<%=request.getParameter("ANNOUNCEMENT_TYPE")%>_ANNOUNCEMENTS tbody').append(announcementDataRow);

      if ((index+1) == maxAnnouncements) {
          break;
      }
    }
  });
});
</script>
<div class="TileHeader" align="left">
  <strong><%=request.getParameter("ANNOUNCEMENT_TYPE")%> Announcements</strong>
  <a href="#"><img src="<%=request.getContextPath()%>/images/icons/more.png" title="Findout more..." style="cursor: pointer;"/></a>
</div>
<div class="TileContent" align="left">
  <table cellpadding="0" cellspacing="0" width="100%" id="<%=request.getParameter("ANNOUNCEMENT_TYPE")%>_ANNOUNCEMENTS">
    <tbody></tbody>
  </table>
</div>
