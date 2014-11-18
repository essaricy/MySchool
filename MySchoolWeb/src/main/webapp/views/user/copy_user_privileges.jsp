<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" charset="utf-8">
$(document).ready(function () {
  $('.chosen-select').chosen({width: "98%"});

  $('#Copy').click(function() {
    var CopyFromId = $('#CopyFrom option:selected').val();
    var CopyToIDs = new Array();

    var CopyUserPrivilegesData = new Object();
    CopyUserPrivilegesData.CopyFromID=CopyFromId;
    CopyUserPrivilegesData.CopyToIDs=CopyToIDs;

    if ($('#CopyTo option:selected').length == 0) {
      error_ac('Please select Copy Privileges "To".');
      return;
    }

    var selectedCopyToOptions = $('#CopyTo option:selected');
    for (var index=0; index<selectedCopyToOptions.length; index++) {
        var selectedCopyToOption = selectedCopyToOptions[index]
        if (CopyFromId == $(selectedCopyToOption).val()) {
            error_ac('Cannot copy privileges "From" and "To" the same user.');
            return;
        }
        CopyToIDs[CopyToIDs.length]=$(selectedCopyToOption).val();
    }

      $.ajax({
        type: "POST",
        url: '<%=request.getContextPath()%>/privileges/copyUserPrivileges.htm',
        data: {
          CopyUserPrivilegesData: JSON.stringify(CopyUserPrivilegesData),
          sid: new Date().getTime()
        },
        context: this
      }).done(function(result) {
        //alert(JSON.stringify(result));
        parseModelResponse(result);
      });

  });
});
</script>
<table cellpadding="5" cellspacing="0" width="100%" class="userFormTable">
  <tbody>
    <tr>
      <td width="30%" align="right">Copy From</td>
      <td width="70%" align="left">
      <c:if test="${Users != null}">
        <select id="CopyFrom" class="chosen-select">
          <c:forEach var="User" items="${Users}">
          <option value="${User.id}">${User.displayName}</option>
          </c:forEach>
        </select>
      </c:if>
      </td>
    </tr>
    <tr>
      <td width="30%" align="right">Copy To</td>
      <td width="70%" align="left">
      <c:if test="${Users != null}">
        <select id="CopyTo" multiple class="chosen-select">
          <c:forEach var="User" items="${Users}">
          <option value="${User.id}">${User.displayName}</option>
          </c:forEach>
        </select>
      </c:if>
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <input type="button" id="Copy" value="Copy Privileges" class="formButton" />
      </td>
    </tr>
  </tbody>
</table>