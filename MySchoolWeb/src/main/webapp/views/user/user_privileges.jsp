<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<script src="<%=request.getContextPath()%>/widgets/chosen.ImageSelect/ImageSelect.jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/scripts/myschool-privileges.js" type="text/javascript"></script>

<script type="text/javascript" charset="utf-8">
$(document).ready(function () {
  var url = '<%=request.getContextPath()%>/privileges/jsonUserPrivileges.htm';
  var images_src = new Object();
  images_src.expand_src='<%=request.getContextPath()%>/images/icons/triangle_down.png';
  images_src.collapse_src='<%=request.getContextPath()%>/images/icons/triangle_up.png';

  $('#UserTypeID').change(function(value) {
    loadUsers($("#UserTypeID").val());
  });
  $('#UserID').change(function(value) {
    var data = {
      UserTypeID: $("#UserTypeID").val(),
      UserID: $("#UserID").val(),
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
    updatePrivileges('<%=request.getContextPath()%>/privileges/jsonSaveUserPrivileges.htm');
  });

  $('#PrivilegesMasterTable').hide();
  $('#ExpandAll').tooltipster();
  $('#CollapseAll').tooltipster();
  $('.chosen-select').chosen({width: "95%"});
  $('#UserTypeID').change();

  $('#CopyPrivileges').click(function() {
    openDialog('<%=request.getContextPath()%>/privileges/launchCopyUserPrivileges.htm?UserTypeID=' + $('#UserTypeID').val(),
        'Copy Privileges', $(document).width()/2, $(document).height()/2);
  });

  $('#RestoreDefaultPrivileges').click(function () {
    confirm('Do you want to restore previleges of the user (' + $('#UserID option:selected').text() + ') to default?', RestoreDefaultPrivileges);
  });

  function RestoreDefaultPrivileges(result) {
    if (result == "Yes") {
      $.ajax({
        type: "POST",
        url: '<%=request.getContextPath()%>/privileges/restoreToDefaultPrivileges.htm',
        data: {
          UserID: $('#UserID').val(),
          sid: new Date().getTime()
        },
        context: this
      }).done(function(result) {
        parseWholepageResponse(result, false);
        setTimeout(function () { location.reload(); }, 3000);
      });
    }
  }

  function loadUsers(UserTypeID) {
    $.ajax({
      type: "POST",
      url: '<%=request.getContextPath()%>/user/usersByType.htm',
      data: {
        UserTypeID: UserTypeID,
        sid: new Date().getTime()
      },
      context: this
    }).done(function(result) {
      if (result != null && result.Users != 0) {
        var target = $('#UserID');
        var Users = result.Users;
        $(target).empty();
        for (var index=0; index<Users.length; index++) {
          var User = Users[index];
          var Option = $('<option>');
          Option.val(User.Id);
          Option.text(User.DisplayName);
          target.append(Option);
        }
        $(target).trigger("chosen:updated");
        $('#UserID').change();
      }
    });
  }
});
</script>

<table cellpadding="5" cellspacing="0" width="60%" class="formTable">
  <caption class="dataTableCaption">User Privileges</caption>
  <c:if test="${UserTypes != null}">
  <tr>
    <td align="left" width="50%">
      <table cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="formLabel" width="50%">User Type</td>
          <td>
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
          </td>
        </tr>
      </table>
    </td>
  </tr>
  </c:if>
  <tr>
    <td align="left" width="50%">
      <table cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="formLabel" width="50%">User</td>
          <td>
            <select id="UserID" class="chosen-select">
            </select> 
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td align="left" width="50%">
      <table cellpadding="0" cellspacing="0" width="100%" id="PrivilegesMasterTable">
        <tr>
          <td colspan="2" valign="top">&nbsp;
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
          <br/>
          <c:choose>
            <c:when test="${PAGE_ACCESS != null && PAGE_ACCESS.update}">
              <input type="button" id="UpdatePrivileges" value="Update" class="formButton" />
              <input type="button" id="RestoreDefaultPrivileges" value="Restore" class="formButton" />
              <input type="button" id="CopyPrivileges" value="Copy" class="formButton" />
            </c:when>
            <c:otherwise>
              <input type="button" value="Update" class="inactive" disabled />
              <input type="button" value="Restore Default Privileges" class="inactive" />
              <input type="button" value="Copy" class="inactive" disabled />
            </c:otherwise>
          </c:choose>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
