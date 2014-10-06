<c:if test="${USER_CONTEXT == null}">
<table cellpadding="0" cellspacing="0" id="FooterStaticLinksTable" align="center" border="0">
  <thead>
    <tr>
      <th>Portfolio</th>
      <th>Reach us</th>
      <th>Whats New?</th>
      <th>Self Submit</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td valign="top">
        <table cellpadding="0" cellspacing="0" width="100%" align="center">
          <tr><td><a href="<%=request.getContextPath()%>/noticeBoard/organization.htm">About Organization</a></td></tr>
          <tr><td><a href="<%=request.getContextPath()%>/noticeBoard/director.htm">About Director</a></td></tr>
          <tr><td><a href="<%=request.getContextPath()%>/noticeBoard/achievements.htm">Our Achievements</a></td></tr>
        </table>
      </td>
      <td valign="top">
        <table cellpadding="0" cellspacing="0" align="center">
          <tr><td><a href="<%=request.getContextPath()%>/findus/contactUs.htm">Contact us</a></td></tr>
          <tr><td><a href="<%=request.getContextPath()%>/findus/locateUs.htm">Locate us</a></td></tr>
          <tr><td><a href="<%=request.getContextPath()%>/issue/launchIssue.htm">Have a Question?</a></td></tr>
        </table>
      </td>
      <td valign="top">
        <table cellpadding="0" cellspacing="0" align="center">
          <tr><td><a href="<%=request.getContextPath()%>/download/brochures.htm">Download Brochures</a></td></tr>
          <tr><td><a href="<%=request.getContextPath()%>/noticeBoard/list.htm">View Notice Board</a></td></tr>
          <tr><td><a href="<%=request.getContextPath()%>/noticeBoard/gallery.htm">View Gallery</a></td></tr>
        </table>
      </td>
      <td valign="top">
        <table cellpadding="0" cellspacing="0" align="center">
          <tr><td><a href="<%=request.getContextPath()%>/portal-student/launchSelfSubmit.htm">Student Information</a></td></tr>
          <tr><td><a href="<%=request.getContextPath()%>/portal-employee/launchSelfSubmit.htm">Employee Information</a></td></tr>
        </table>
      </td>
    </tr>
  </tbody>
</table>
</c:if>