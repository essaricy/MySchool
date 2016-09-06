<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<script src="<%=request.getContextPath()%>/scripts/myschool-privileges.js" type="text/javascript"></script>

<style>
.inactive[type="button"] {
    background-color: gray;
    -moz-border-radius: 5px;
    -webkit-border-radius: 5px;
    border-radius: 5px;
    border:1px solid #797D80;
    display: inline-block;
    color: #ffffff;
    font-family: arial;
    font-size: 12px;
    font-weight: bold;
    height: 30px;
    margin-right: 5px;
    padding-left: 10px;
    padding-right: 10px;
    text-decoration: none;
    letter-spacing: 1px;
}
</style>
<script type="text/javascript" charset="utf-8">
$(document).ready(function () {
  var url = '<%=request.getContextPath()%>/privileges/jsonDefaultPrivileges.htm';
  var images_src = new Object();
  images_src.expand_src='<%=request.getContextPath()%>/images/icons/triangle_down.png';
  images_src.collapse_src='<%=request.getContextPath()%>/images/icons/triangle_up.png';

  $('#UserTypeID').change(function(value) {
    var data = {
      UserTypeID: $("#UserTypeID").val(),
      sid: new Date().getTime()
    };
    showPrivileges(url, data, images_src);
  });
  $('#UpdatePrivileges').click(function() {
    updatePrivileges('<%=request.getContextPath()%>/privileges/jsonSaveDefaultPrivileges.htm');
  });

  $('#PrivilegesMasterTable').hide();
  $('#ExpandAll').tooltipster();
  $('#CollapseAll').tooltipster();
  $('.chosen-select').chosen({width: "95%"});
  $('#UserTypeID').change();

  $('.iconImage').tooltipster();
});

</script>

<table class="formTable_Container" style="width: 70%; font-size: 1em; color: #555;">
  <caption>Default Privileges</caption>
  <tr>
    <td width="50%" class="label">User Type</td>
    <td width="50%" class="value">
    <c:if test="${UserTypes != null}">
      <select id="UserTypeID" class="chosen-select">
      <c:forEach var="UserType" items="${UserTypes}">
      <c:if test="${UserType.userTypeId == UserTypeID}">
        <option value="${UserType.userTypeId}" selected>${UserType.description}</option> 
      </c:if>
      <c:if test="${UserType.userTypeId != UserTypeID}">
        <option value="${UserType.userTypeId}">${UserType.description}</option> 
      </c:if>
      </c:forEach> 
      </select> 
    </c:if>
    </td>
  </tr>
  <tr>
    <td colspan="2" valign="top">
      <table width="100%" cellpadding="2" cellspacing="0">
        <thead style="background: url('<%=request.getContextPath()%>/images/icons/blockdefault.gif') repeat-x left center;">
          <tr>
            <th width="5%" align="left">
              &nbsp;
            </th>
            <th width="75%" align="left" style="color: white;">Module</th>
            <th width="5%" align="left"><img src="<%=request.getContextPath()%>/images/icons/view.png" class="iconImage" title="View" /></th>
            <th width="5%" align="left"><img src="<%=request.getContextPath()%>/images/icons/add.png" class="iconImage" title="Add" /></th>
            <th width="5%" align="left"><img src="<%=request.getContextPath()%>/images/icons/update.png" class="iconImage" title="Update" /></th>
            <th width="5%" align="left"><img src="<%=request.getContextPath()%>/images/icons/delete.png" class="iconImage" title="Delete" /></th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td width="100%" colspan="100%" id="PrivilegesDataContainer">
          </tr>
        </tbody>
        <tfoot/>
      </table>
    </td>
  </tr>
  <tr>
    <td colspan="3" align="right">
      <c:choose>
        <c:when test="${PAGE_ACCESS != null && PAGE_ACCESS.update}">
          <input type="button" id="UpdatePrivileges" value="Update" />
        </c:when>
        <c:otherwise>
          <input type="button" value="Update" disabled />
        </c:otherwise>
      </c:choose>
    </td>
  </tr>
</table>
