<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<style>
img.headerIcon {
  width: 18px;
  height: 18px;
  padding-right: 5px;
}
</style>

<script>
$(document).ready(function() {
  $('#goup').goup({
    appear: 200,
    scrolltime: 800,
    imgsrc: '<%=request.getContextPath()%>/images/icons/goup.png',
    width: 72,
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
/*
$(document).ajaxStart($.blockUI(
    {
      css: { 
        border: 'none', 
        padding: 0, 
        margin: 0, 
        backgroundColor: '#FFF',
        '-webkit-border-radius': '10px', 
        '-moz-border-radius': '10px', 
        opacity: 1, 
        // for circular
        //border: "5px solid #dadada",
        //height: '70px',
        //color: '#999' ,
        //"letter-spacing": "5px",

        //for horizontal
        border: "2px solid #dadada",
        //"background-image": "url('<%=request.getContextPath()%>/images/background/loading9.gif')",
        //height: '20px',
        //"background-image": "url('<%=request.getContextPath()%>/images/background/loading10.gif')",
        //height: '30px',
        //"background-image": "url('<%=request.getContextPath()%>/images/background/loading14.gif')",
        //height: '24px',
        //"background-image": "url('<%=request.getContextPath()%>/images/background/loading15.gif')",
        //height: '34px',
        "background-image": "url('<%=request.getContextPath()%>/images/background/loading16.gif')",
        height: '78px',
        "background-repeat": 'repeat-x',
        "border-radius": "10px",
        color: '#FFF' ,
        "background-size":"100%",
        "letter-spacing": "5px",
      },
      fadeIn:  1000, 
      // fadeOut time in millis; set to 0 to disable fadeOut on unblock 
      fadeOut:  400,
      //message: '<h2><img src="<%=request.getContextPath()%>/images/background/loading21.gif" /> Loading</h2>' }));
      //message: '<h2><img src="<%=request.getContextPath()%>/images/background/loading5.gif" width="30px" /> Loading</h2>' }));
      //message: '<h2><img src="<%=request.getContextPath()%>/images/background/loading6.gif" width="30px" /> Loading</h2>' }));
      //message: '<h2>Loading<img src="<%=request.getContextPath()%>/images/background/loading8.gif" width="30px" /></h2>' }));
      //message: '<h2><img src="<%=request.getContextPath()%>/images/background/loading17.gif" width="30px" /> Loading</h2>' }));
      //message: '<h2>Loading...</h2>' }));
      message: '<h2></h2>'
    })
  ).ajaxStop($.unblockUI);
*/
  // Display menu if user has logged in.
  <c:if test="${USER_CONTEXT != null}">
  ddmegamenu.docinit({
    menuid:'solidmenu',
    dur:200,
    easing:'easeOutBack'
  });
  </c:if>

  $('#Product').click(function() {
	  var modelDialog = openDialog('<%=request.getContextPath()%>/views/common/credits.jsp', 'About Product', $(document).width()-20, $(document).height()-10);
  });
});
<c:if test="${USER_CONTEXT != null && USER_CONTEXT.userPreference != null}">
  var recordsPerPage = ${USER_CONTEXT.userPreference.recordsPerPage};
</c:if>
var iDisplayLength = (typeof recordsPerPage == 'undefined') ? 10 : recordsPerPage;

</script>
<div id="goup"></div>
<table cellpadding="0" cellspacing="0" class="headerTable" border="0">
  <tr>
    <td width="109px" style="padding-left:8px;padding-top:2px;">
      <a href="<%=request.getContextPath()%>"><img src="<%=request.getContextPath()%>/image/getImage.htm?type=logo" class="logo"/></a>
    </td>
    <td colspan="2">&nbsp;</td>
    <td>
      <table cellpadding="0" cellspacing="0" width="100%" border="0">
        <tr>
          <td style="color:white;font-size:30px;letter-spacing:2px;word-spacing:4px;text-transform:uppercase;">
          Your School Name Here
          </td>
        </tr>
      </table>
    </td>
    <c:if test="${USER_CONTEXT == null}">
    <td width="*" style="color:white;font-size:14px;font-weight:bold;" valign="bottom" align="right"><spring:message code="product.name"/></td>
    </c:if>
    <c:if test="${USER_CONTEXT != null}">
    <td width="30%" valign="top">
      <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
        <tr>
          <td style="color:white;font-size:14px;font-weight:bold;" valign="bottom" align="right">
		    <a href="#" id="Product" class="formLink" style="color: white;"><spring:message code="product.name" /></a>
		  </td>
        </tr>
        <tr>
          <td>
            <table border="0" cellpadding="0" cellspacing="0" width="100%" valign="top">
              <tr>
                <td align="right" class="headerLabel">Welcome,&nbsp;${USER_CONTEXT.login.loginId}</td>
                <td width="60px" align="center">&nbsp;
                  <!-- Values are equal to the constants used in com.myschool.common.dto.UserTypeDto class-->
                  <c:if test="${USER_CONTEXT.userType == 'ADMIN'}">
                  <img src="<%=request.getContextPath()%>/images/icons/admin.png" width="30px" height="30x" border="0"/>
                  </c:if>
                  <c:if test="${USER_CONTEXT.userType == 'EMPLOYEE'}">
                  <img src="<%=request.getContextPath()%>/images/icons/employee.png" width="30px" height="30x" border="0"/>
                  </c:if>
                  <c:if test="${USER_CONTEXT.userType == 'STUDENT'}">
                  <img src="<%=request.getContextPath()%>/images/icons/student.png" width="30px" height="30x" border="0"/>
                  </c:if>
                </td>
              </tr>
            </table>
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
                  <a href="<%=request.getContextPath()%>/logout.htm"><img src="<%=request.getContextPath()%>/images/icons/door_open.png" id="logout" title='<spring:message code="user.logout"/>' class="headerIcon" /></a>
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
