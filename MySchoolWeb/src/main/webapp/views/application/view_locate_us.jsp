<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/widgets/awesome-grid-master/awesome-grid.js"></script>
<style>
ul.grid {
    list-style: none;
    margin: 10px auto;
    padding: 0;
    max-width: 100%;
}
ul.grid li {
    width: 160px;
    padding: 5px;
    background-color: #f4f4f4;
    border: 1px dashed #d7d7d7;
    color: #525252;
    text-align: center;
    text-shadow: 1px 1px 1px #fff;
}
ul.grid li p {
    font-size: 1em;
}
ul.grid li img {
    margin-top: 10px;
    display: block;
    max-width: 100%;
    height: auto;
}
ul.grid li code {
    background-color: #D8F6C0;
    padding: 4px;
    border: 1px dashed #9CCA77;
    border-radius: 4px;
}
</style>
<script>
$(document).ready(function() {
  $('#OrganizationGrid').AwesomeGrid({
    rowSpacing  : 14,
    colSpacing  : 14,
    columns     : {
      'defaults' : 1,
      '800' : 1
    }
  });
  $('#BranchesGrid').AwesomeGrid({
    rowSpacing  : 14,
    colSpacing  : 14,
    columns     : {
      'defaults' : 3,
      '800' : 3
    }
  });
  $('#SchoolsGrid').AwesomeGrid({
    rowSpacing  : 14,
    colSpacing  : 14,
    columns     : {
      'defaults' : 3,
      '800' : 3
    }
  });
});
</script>

<c:if test="${organization != null}">
<table width="80%" class="userFormTable" align="center" cellspacing="10" cellpadding="5">
  <caption class="dataTableCaption"><spring:message code="contact.organization" /></caption>
  <tr>
    <td>
      <ul id="OrganizationGrid" class="grid">
        <li>
          <h3>${organization.organizationName}</h3>
          <p>${organization.address}</p>
          <c:if test="${organization.phoneNumber != null}"><p>Phone: ${organization.phoneNumber}</c:if></p>
          <c:if test="${organization.faxNumber != null}"><p>Fax: ${organization.faxNumber}</c:if></p>
        </li>
      </ul>
    </td>
  </tr>
</table>
</c:if>

<c:if test="${branches != null}">
<table width="80%" class="userFormTable" align="center" cellspacing="10" cellpadding="5">
  <caption class="dataTableCaption"><spring:message code="contact.branch" /></caption>
  <tr>
    <td>
      <ul id="BranchesGrid" class="grid">
      <c:forEach var="branch" items="${branches}">
        <li>
          <h2>${branch.branchCode}</h2>${branch.description}
          <p>${branch.address}</p>
          <c:if test="${branch.phoneNumber != null}"><p>Phone: ${branch.phoneNumber}</p></c:if>
          <c:if test="${branch.mapUrl != null}"><p><a href="${branch.mapUrl}"><spring:message code="view.map" /></a></p></c:if>
        </li>
      </c:forEach>
      </ul>
    </td>
  </tr>
</table>
</c:if>

<c:if test="${schools != null}">
<table width="80%" class="userFormTable" align="center" cellspacing="10" cellpadding="5">
  <caption class="dataTableCaption"><spring:message code="contact.school" /></caption>
  <tr>
    <td>
      <ul id="SchoolsGrid" class="grid">
      <c:forEach var="school" items="${schools}">
        <li>
          <h2>${school.schoolName}</h2>
          <p>${school.address}</p>
          <c:if test="${school.primaryPhoneNumber != null}"><p>Primary: ${school.primaryPhoneNumber}</p></c:if>
          <c:if test="${school.secondaryPhoneNumber != null}"><p>Secondary: ${school.secondaryPhoneNumber}</p></c:if>
          <c:if test="${organization.faxNumber != null}"><p>Fax: ${school.faxNumber}</p></c:if>
          <c:if test="${school.mapUrl != null}"><p><a href="${school.mapUrl}"><spring:message code="view.map" /></a></p></c:if>
        </li>
      </c:forEach>
      </ul>
    </td>
  </tr>
</table>
</c:if>
