<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<script src="<%=request.getContextPath()%>/widgets/jquery.jrumble.1.3/jquery.jrumble.1.3.min.js"></script>
<script>
$(document).ready(function() {
  var wall = new freewall("#freewall");

  $('.card').jrumble({
    x:5, y:0,
    rotation: 0, 
    speed: 60
  });

  $('.card').hover(
    function(){
      var that = this;
      $(that).trigger('startRumble');
      setTimeout(function() {
        $(that).trigger('stopRumble');
      }, 500);
    },
    function(){
      $(this).trigger('stopRumble');
    }
  );
});
</script>

<div id="freewall">
  <c:if test="${organization != null}">
  <div class="brick size00">
    <div class="TileHeader" align="center">
      <strong><spring:message code="contact.organization" /></strong>
    </div>
    <div class="TileContent card">
      <h3>${organization.organizationName}</h3>
      <i class="fa fa-institution fa-2x"></i> ${organization.address}<br/>
      <i class="fa fa-phone fa-2x"></i> ${organization.phoneNumber}<br/>
      <i class="fa fa-fax fa-2x"></i> ${organization.faxNumber}<br />
    </div>
  </div>
  </c:if>

  <c:if test="${branches != null}">
  <div class="brick size00">
    <div class="TileHeader" align="center">
      <strong><spring:message code="contact.branch" /></strong>
    </div>
    <div class="TileContent">
      <c:forEach var="branch" items="${branches}">
      <div class="card">
        <h3>${branch.branchCode}</h3>
        <i class="fa fa-institution fa-2x"></i> ${branch.address}<br/>
        <i class="fa fa-phone fa-2x"></i> ${branch.phoneNumber}<br/>
        <i class="fa fa-envelope fa-2x"></i> ${branch.emailId}<br />
      </div>
      </c:forEach>
    </div>
  </div>
  </c:if>


  <c:if test="${schools != null}">
  <div class="brick size00">
    <div class="TileHeader" align="center">
      <strong><spring:message code="contact.school" /></strong>
    </div>
    <div class="TileContent">
      <c:forEach var="school" items="${schools}">
      <div class="card">
        <h3>${school.schoolName}</h3>
        <i class="fa fa-institution fa-2x"></i> ${school.address}<br/>
        <i class="fa fa-phone fa-2x"></i> ${school.primaryPhoneNumber},  ${school.secondaryPhoneNumber}<br/>
        <i class="fa fa-mobile fa-2x"></i> ${school.mobileNumber}<br/>
        <i class="fa fa-fax fa-2x"></i> ${school.faxNumber}<br/>
        <i class="fa fa-envelope fa-2x"></i> ${school.emailId}<br />
      </div>
      </c:forEach>
    </div>
  </div>
  </c:if>
</div>
