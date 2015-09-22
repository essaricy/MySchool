<c:set var="ORGANIZATION_PROFILE" value="${sessionScope.ORGANIZATION_PROFILE}"/>
<c:set var="MYSCHOOL_PROFILE" value="${sessionScope.MYSCHOOL_PROFILE}"/>
<!-- TODO: Remove FooterStaticLinksTable from all css in themes. -->
<style>
.FooterStaticLinksTable {
  width: 100%;
  font-family: arial;
  background-color: #212121;
  border-collapse: collapse; 
  border-top: 1px inset gray;
}
.FooterStaticLinksTable tr td.Heading {
    background-color: #212121;
	color: white;
    font-size: 0.8em;
	font-weight: bold;
    padding-left: 3px;
    padding-right: 3px;
	padding-top: 2px;
	padding-bottom: 2px;
    text-align: left;
    text-decoration: none;
}

.FooterStaticLinksTable tr td a {
    color: #888;
    font-size: 0.70em;
    padding-left: 3px;
    padding-right: 3px;
	padding-top: 2px;
	padding-bottom: 2px;
    text-align: left;
    text-decoration: none;
}
.FooterStaticLinksTable tr td a:hover {
    color: #FFF;
    font-size: 0.70em;
    padding-left: 3px;
    padding-right: 3px;
    text-align: left;
    text-decoration: underline;
}
</style>

<c:if test="${USER_CONTEXT == null}">
<table cellpadding="0" cellspacing="0" width="100%" border="0" class="FooterStaticLinksTable">
  <tr>
    <td class="Heading" width="140px">Portfolio</td>
	<td align="left" width="140px"><a href="<%=request.getContextPath()%>/noticeBoard/organization.htm">About Organization</a></td>
    <td align="left" width="140px"><a href="<%=request.getContextPath()%>/noticeBoard/director.htm">About Director</a></td>
    <td align="left" width="140px"><a href="<%=request.getContextPath()%>/noticeBoard/achievements.htm">Our Achievements</a></td>
	<td>&nbsp;</td>
  </tr>
</table>
<table cellpadding="0" cellspacing="0" width="100%" border="0" class="FooterStaticLinksTable">
  <tr>
	<td class="Heading" width="140px">Reach us</td>
	<td align="left" width="140px"><a href="<%=request.getContextPath()%>/findus/contactUs.htm">Contact us</a></td>
	<td align="left" width="140px"><a href="<%=request.getContextPath()%>/findus/locateUs.htm">Locate us</a></td>
	<td align="left" width="140px"><a href="<%=request.getContextPath()%>/issue/launchIssue.htm">Have a Question?</a></td>
	<td>&nbsp;</td>
  </tr>
</table>
<table cellpadding="0" cellspacing="0" width="100%" border="0" class="FooterStaticLinksTable">
  <tr>
	<td class="Heading" width="140px">Whats New?</td>
	<td align="left" width="140px"><a href="<%=request.getContextPath()%>/download/brochures.htm">Download Brochures</a></td>
	<td align="left" width="140px"><a href="<%=request.getContextPath()%>/noticeBoard/list.htm">View Notice Board</a></td>
	<td align="left" width="140px"><a href="<%=request.getContextPath()%>/noticeBoard/gallery.htm">View Gallery</a></td>
	<td>&nbsp;</td>
  </tr>
</table>
<c:if test="${MYSCHOOL_PROFILE.useEmployeeSelfSubmit == true || MYSCHOOL_PROFILE.useStudentSelfSubmit == true}">
<table cellpadding="0" cellspacing="0" width="100%" border="0" class="FooterStaticLinksTable">
  <tr>
	<td class="Heading" width="140px">Self Submit</td>
	<c:if test="${MYSCHOOL_PROFILE.useEmployeeSelfSubmit == true}">
	<td align="left" width="140px"><a href="<%=request.getContextPath()%>/portal-employee/launchSelfSubmit.htm">Employee Information</a></td>
	</c:if>
	<c:if test="${MYSCHOOL_PROFILE.useStudentSelfSubmit == true}">
	<td align="left" width="140px"><a href="<%=request.getContextPath()%>/portal-student/launchSelfSubmit.htm">Student Information</a></td>
	</c:if>
	<td>&nbsp;</td>
  </tr>
</table>
</c:if>

</c:if>