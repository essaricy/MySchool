<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<script src="<%=request.getContextPath()%>/widgets/chosen.ImageSelect/ImageSelect.jquery.js" type="text/javascript"></script>
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
  $('#ExpandAll').click(function() {
    $('.module_functions_div').slideDown(1000);
    $('.expand_collapse_toggle').attr('src', images_src.collapse_src);
  });
  $('#CollapseAll').click(function() {
    $('.module_functions_div').slideUp(1000);
    $('.expand_collapse_toggle').attr('src', images_src.expand_src);
  });
  $('#UpdatePrivileges').click(function() {
    updatePrivileges('<%=request.getContextPath()%>/privileges/jsonSaveDefaultPrivileges.htm');
  });

  $('#PrivilegesMasterTable').hide();
  $('#ExpandAll').tooltipster();
  $('#CollapseAll').tooltipster();
  $('.chosen-select').chosen({width: "95%"});
  $('#UserTypeID').change();
});

</script>

<table cellpadding="5" cellspacing="0" width="60%" class="formTable">
  <caption class="dataTableCaption">Default Privileges</caption>
  <tr>
    <td class="formLabel" width="50%">User Type</td>
    <td align="left" width="50%">
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
      <table width="100%" cellpadding="0" cellspacing="0" border="0" class="display" valign="top">
        <thead>
          <tr>
            <th width="40px" align="left">
              <img src="<%=request.getContextPath()%>/images/icons/triangle_down.png" class="iconImage" id="ExpandAll" title="Expand All"/><img src="<%=request.getContextPath()%>/images/icons/triangle_up.png" class="iconImage" id="CollapseAll" title="Collapse All"/>
            </th>
            <th width="35%" align="left">Module</th>
            <th align="center">View</th>
            <th align="center">Create</th>
            <th align="center">Update</th>
            <th align="center">Delete</th>
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
          <input type="button" id="UpdatePrivileges" value="Update" class="formButton" />
        </c:when>
        <c:otherwise>
          <input type="button" value="Update" class="inactive" disabled />
        </c:otherwise>
      </c:choose>
    </td>
  </tr>
</table>
