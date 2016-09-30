<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<style>
img.headerIcon {
  width: 18px;
  height: 18px;
  padding-right: 5px;
}
.img-round {
    float: right;
}
</style>

<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/widgets/jquery.roundImage/jquery.roundimage.js"></script>
<script>
$(document).ready(function() {
  $('#goup').goup({
    appear: 200,
    scrolltime: 800,
    imgsrc: '<%=request.getContextPath()%>/images/icons/goup.png',
    width: 40,
    place: 'bottom-right',
    fadein: 500,
    fadeout: 500,
    opacity: 0.5,
    marginX: 2,
    marginY: 2,
  });

  $.fn.scrollTo = function(target, options, callback ) {
    if(typeof options == 'function' && arguments.length == 2){ callback = options; options = target; }
    var settings = $.extend({
      scrollTarget  : target,
      offsetTop     : 50,
      duration      : 500,
      easing        : 'swing'
    }, options);
    return this.each(function() {
      var scrollPane = $(this);
      var scrollTarget = (typeof settings.scrollTarget == "number") ? settings.scrollTarget : $(settings.scrollTarget);
      var scrollY = (typeof scrollTarget == "number") ? scrollTarget : scrollTarget.offset().top + scrollPane.scrollTop() - parseInt(settings.offsetTop);
      scrollPane.animate({scrollTop : scrollY }, parseInt(settings.duration), settings.easing, function() {
        if (typeof callback == 'function') { callback.call(this); }
      });
    });
  }
  // Display menu if user has logged in.
  <c:if test="${USER_CONTEXT != null}">
  ddmegamenu.docinit({
    menuid:'solidmenu',
    dur:200,
    easing:'easeOutBack'
  });
  </c:if>

  $('.img-round').roundImage();
  //$('.img-round').tooltipster();
});
<c:if test="${USER_CONTEXT != null && USER_CONTEXT.userPreference != null}">
  var recordsPerPage = ${USER_CONTEXT.userPreference.recordsPerPage};
</c:if>
var iDisplayLength = (typeof recordsPerPage == 'undefined') ? 10 : recordsPerPage;

</script>
<div id="goup"></div>

<c:set var="ORGANIZATION_PROFILE" value="${sessionScope.ORGANIZATION_PROFILE}"/>
<c:set var="MYSCHOOL_PROFILE" value="${sessionScope.MYSCHOOL_PROFILE}"/>
<c:set var="SIGNIN_SECURITY" value="${sessionScope.SIGNIN_SECURITY}"/>

<table cellpadding="0" cellspacing="0" class="headerTable" border="0">
  <tr>
    <td width="109px" style="padding-left:8px;padding-top:2px;">
      <a href="<%=request.getContextPath()%>"><img src="<%=request.getContextPath()%>/images/organization/LOGO.png" class="logo"/></a>
    </td>
    <td style="color:white;font-size:30px;letter-spacing:2px;word-spacing:4px;text-transform:uppercase;">Your School Name Here</td>

    <c:if test="${USER_CONTEXT == null}">
    <td align="right" valign="top" style="color: white;padding-top: 3px; padding-right: 3px;">
        <a href="<%=request.getContextPath()%>" class="headerLink">Home</a>
        | <a href="<%=request.getContextPath()%>/acl/employee.htm" class="headerLink">Employee Login</a>
        | <a href="<%=request.getContextPath()%>/acl/student.htm" class="headerLink">Student Login</a>
        | <a href="<%=request.getContextPath()%>/acl/admin.htm" class="headerLink">Admin</a>
    </td>
    </c:if>

    <c:if test="${USER_CONTEXT != null}">
    <td width="30%" valign="top">
      <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
        <tr>
          <td style="color:white;font-size:14px;font-weight:bold;" valign="bottom" align="right">
            <a href="#" id="Product" style="color: white;">&nbsp;</a>
          </td>
        </tr>
        <tr>
          <td align="right" style="padding-right: 5px;">
              <!-- Values are equal to the constants used in com.myschool.common.dto.UserTypeDto class-->
              <c:if test="${USER_CONTEXT.userType == 'ADMIN'}">
              <img src="<%=request.getContextPath()%>/images/icons/admin.png" width="30px" height="30x" border="0"/>
              </c:if>
              <c:if test="${USER_CONTEXT.userType == 'EMPLOYEE'}">
                <c:if test="${USER_CONTEXT.login.userDetails.imageAccess.thumbnailLink == null}">
                  <img src="<%=request.getContextPath()%>/images/icons/student.png" width="30px" height="30x" border="0"/>
                </c:if>
                <c:if test="${USER_CONTEXT.login.userDetails.imageAccess.thumbnailLink != null}">
                  <img class="img-round" src="${USER_CONTEXT.login.userDetails.imageAccess.thumbnailLink}" width="30px" height="40x" title="${USER_CONTEXT.login.loginId}" />
                </c:if>
              </c:if>
              <c:if test="${USER_CONTEXT.userType == 'STUDENT'}">
                <c:if test="${USER_CONTEXT.login.userDetails.imageAccess.thumbnailLink == null}">
                  <img src="<%=request.getContextPath()%>/images/icons/student.png" width="30px" height="30x" border="0"/>
                </c:if>
                <c:if test="${USER_CONTEXT.login.userDetails.imageAccess.thumbnailLink != null}">
                  <img class="img-round" src="${USER_CONTEXT.login.userDetails.imageAccess.thumbnailLink}" width="30px" height="40x"title="Welcome, ${USER_CONTEXT.login.loginId}" />
                </c:if>
              </c:if>
          </td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>
            <table border="0" cellpadding="0" cellspacing="0" width="100%" valign="bottom">
              <tr>
                <td>&nbsp;</td>
                <td width="16px" align="center">
                  <a href="<%=request.getContextPath()%>/settings/launch.htm"><img src="<%=request.getContextPath()%>/images/icons/cog.png" id="settings" title='<spring:message code="user.settings"/>' class="headerIcon" /></a>
                </td>
                <td width="16px" align="center">
                  <a href="<%=request.getContextPath()%>/acl/signout.htm"><img src="<%=request.getContextPath()%>/images/icons/door_open.png" id="logout" title='<spring:message code="user.logout"/>' class="headerIcon" /></a>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
    </c:if>
  </tr>
</table>
