<c:set var="ORGANIZATION_PROFILE" value="${sessionScope.ORGANIZATION_PROFILE}"/>
<c:set var="MYSCHOOL_PROFILE" value="${sessionScope.MYSCHOOL_PROFILE}"/>

<c:if test="${USER_CONTEXT == null}">
  <div class="row">
    <span class="title odd">Portfolio</span>
      <a href="<%=request.getContextPath()%>/noticeBoard/organization.htm">About Organization</a>
      <a href="<%=request.getContextPath()%>/noticeBoard/director.htm">About Director</a>
      <a href="<%=request.getContextPath()%>/noticeBoard/achievements.htm">Our Achievements</a>
    </span>
  </div>
  <div class="row">
    <span class="title even">Reach us</span>
      <a href="<%=request.getContextPath()%>/findus/contactUs.htm">Contact Us</a>
      <a href="<%=request.getContextPath()%>/findus/locateUs.htm">Locate Us</a>
      <a href="<%=request.getContextPath()%>/issue/launchIssue.htm">Have a Question?</a>
    </span>
  </div>
  <div class="row">
    <span class="title odd">Whats New?</span>
      <a href="<%=request.getContextPath()%>/download/brochures.htm">Download Brochures</a>
      <a href="<%=request.getContextPath()%>/noticeBoard/list.htm">Notice Board</a>
      <a href="<%=request.getContextPath()%>/gallery/launchGallery.htm">Gallery</a>
    </span>
  </div>
</c:if>
<c:if test="${USER_CONTEXT == null}">
<c:if test="${MYSCHOOL_PROFILE.useEmployeeSelfSubmit == true || MYSCHOOL_PROFILE.useStudentSelfSubmit == true}">
  <div class="row">
    <span class="title even">Self Submit</span>
      <c:if test="${MYSCHOOL_PROFILE.useEmployeeSelfSubmit == true}"><a href="demo/portal-employee/launchSelfSubmit.htm">Employee Information</a></c:if>
      <c:if test="${MYSCHOOL_PROFILE.useEmployeeSelfSubmit == false}">&nbsp;</c:if>
      <c:if test="${MYSCHOOL_PROFILE.useStudentSelfSubmit == true}"><a href="demo/portal-student/launchSelfSubmit.htm">Student Information</a></c:if>
      <c:if test="${MYSCHOOL_PROFILE.useStudentSelfSubmit == false}">&nbsp;</c:if>
    </span>
  </div>
</c:if>
</c:if>
