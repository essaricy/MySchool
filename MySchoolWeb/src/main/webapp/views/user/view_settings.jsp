<%@page import="com.myschool.user.constants.UserTheme"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<style>
#SettingAccordion p {
  font-size: 0.8em;
  font-weight: bold;
  text-align: left;
}
.LastVisit {
  font-weight: bold;
  text-transform:capitalize;
}
</style>

<script type="text/javascript" language="javascript" src="<%=request.getContextPath() %>/widgets/password-strength/jquery.passstrength.js"></script>
<script type="text/javascript" language="javascript" src="<%=request.getContextPath() %>/widgets/jquery.dateFormat/jquery-dateFormat.min.js"></script>
<script type="text/javascript" language="javascript" src="<%=request.getContextPath() %>/widgets/jquery.timeago/jquery.timeago.js"></script>
<script type="text/javascript" charset="utf-8">
jQuery(document).ready(function() {
  $(this).myAccordion({id: 'SettingAccordion'});
  var formattedLastVisitDate = $.format.date($("#LastVisitOn").attr('title'), "yyyy-MM-ddTHH:mm:ss");
  $("#LastVisitOn").timeago();

  $('#ChangePassword').click(function() {
    var ChangePasswordDetails = new Object();
    ChangePasswordDetails.UserId=$('#UserId').val();
    ChangePasswordDetails.CurrentPassword=$('#CurrentPassword').val();
    ChangePasswordDetails.NewPassword=$('#NewPassword').val();
    ChangePasswordDetails.ConfirmedPassword=$('#ConfirmedPassword').val();

    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/user/changePassword.htm",
      data: {
        ChangePasswordDetails: JSON.stringify(ChangePasswordDetails),
        sid: new Date().getTime()
      },
      context: this
    }).done(function(result) {
        parseWholepageResponse(result, false);
    });
  });

  $('#ChangePreferences').click(function() {
    var ChangePreferenceDetails = new Object();
    ChangePreferenceDetails.UserId=$('#UserId').val();
    ChangePreferenceDetails.ThemeName=$('#ThemeName').val();
    ChangePreferenceDetails.RecordsPerPage=$('#RecordsPerPage').val();
    ChangePreferenceDetails.AllowAds='' + $('#AllowAds').is(':checked');

    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/user/changePreferences.htm",
      data: {
        ChangePreferenceDetails: JSON.stringify(ChangePreferenceDetails),
        sid: new Date().getTime()
      },
      context: this
    }).done(function(result) {
      parseWholepageResponse(result, false);
    });
  });
  $('#NewPassword').passStrengthify();
  $('.chosen-select').chosen({width: "98%"});
});
</script>


<c:set var="UserThemes" value="<%=UserTheme.values()%>"/>
<table width="80%" align="center" border="0" cellspacing="10" cellpadding="5" class="userFormTable">
  <caption class="dataTableCaption">Settings</caption>
  <tr>
    <td>
      <div id="SettingAccordion">
        <p class="title"><spring:message code="user.account.details"/></p>
        <div>
          <table cellpadding="5" cellspacing="0" align="center" width="100%" class="formDataTable" border="0">
            <tr>
              <td width="50%" class="formLabel"><spring:message code="user.login.name"/></td>
              <td width="50%" align="left"><b>${USER_CONTEXT.loginName}</b></td>
            </tr>
            <tr>
              <td width="50%" class="formLabel"><spring:message code="user.type"/></td>
              <td width="50%" align="left"><b>${USER_CONTEXT.userType}</b></td>
            </tr>
            <tr>
              <td width="50%" class="formLabel"><spring:message code="user.number.of.visits"/></td>
              <td width="50%" align="left">
                <c:if test="${USER_CONTEXT.userStatistics.numberOfVisits == 0}">
                <b>Welcome, This is your first login.</b>
                </c:if>
                <c:if test="${USER_CONTEXT.userStatistics.numberOfVisits != 0}">
                <b>${USER_CONTEXT.userStatistics.numberOfVisits}</b>
                </c:if>
              </td>
            </tr>
            <tr>
              <td width="50%" class="formLabel"><spring:message code="user.last.visit"/></td>
              <td width="50%" align="left">
                <abbr id="LastVisitOn" title="${USER_CONTEXT.userStatistics.lastVisitOn}" class="LastVisit">
                  ${USER_CONTEXT.userStatistics.lastVisitOn}
                </abbr>
              </td>
            </tr>
          </table>
        </div>
        <p class="title"><spring:message code="password.change"/></p>
        <div>
          <input type="hidden" id="UserId" value="${USER_CONTEXT.loginId}" />
          <table cellpadding="5" cellspacing="0" align="center" width="70%" class="formDataTable" border="0">
            <tr>
              <td width="50%" class="formLabel"><spring:message code="password.current"/><label class="mandatory">*</label></td>
              <td width="50%">
                <input type="password" id="CurrentPassword" class="formInputText"/>
              </td>
            </tr>
            <tr>
              <td width="50%" class="formLabel"><spring:message code="password.new"/><label class="mandatory">*</label></td>
              <td width="50%">
                <input type="password" id="NewPassword" class="formInputText" maxlength="128" />
              </td>
            </tr>
            <tr>
              <td width="50%" class="formLabel"><spring:message code="password.confirm"/><label class="mandatory">*</label></td>
              <td width="50%">
                <input type="password" id="ConfirmedPassword" class="formInputText" maxlength="128" />
              </td>
            </tr>
            <tr>
              <td width="50%">&nbsp;</td>
              <td width="50%" align="left">
                <input type="button" id="ChangePassword" class="formButton" value='<spring:message code="password.change"/>' />
              </td>
            </tr>
          </table>
        </div>
        <p class="title"><spring:message code="user.display.prefernces"/></p>
        <div>
          <table cellpadding="5" cellspacing="0" align="center" width="70%" class="formDataTable" border="0">
            <tr>
              <td width="50%" class="formLabel"><spring:message code="user.display.theme"/></td>
              <td width="50%" align="left">
                <select id="ThemeName" class="chosen-select">
                  <c:forEach var="UserTheme" items="${UserThemes}">
                    <c:if test="${USER_CONTEXT.userPreference.userTheme == UserTheme}">
                      <option value="${UserTheme}" selected>${UserTheme}</option>
                    </c:if>
                    <c:if test="${USER_CONTEXT.userPreference.userTheme != UserTheme}">
                      <option value="${UserTheme}">${UserTheme}</option>
                    </c:if>
                  </c:forEach>
                </select>
              </td>
            </tr>
            <tr>
              <td width="50%" class="formLabel"><spring:message code="user.display.records.per.page"/></td>
              <td width="50%" align="left">
                <select id="RecordsPerPage" class="chosen-select">
                  <option value="10">10</option>
                  <option value="25">25</option>
                  <option value="50">50</option>
                  <option value="100">100</option>
                </select>
              </td>
            </tr>
            <tr>
              <td width="50%" class="formLabel"><spring:message code="user.display.advertisements"/></td>
              <td width="50%" align="left" style="padding-left: 10px;">
                <c:if test="${USER_CONTEXT.userPreference.allowAds == 'true'}">
                <input type="checkbox" id="AllowAds" checked/>
                </c:if>
                <c:if test="${USER_CONTEXT.userPreference.allowAds == 'false'}">
                <input type="checkbox" id="AllowAds" />
                </c:if>
              </td>
            </tr>
            <tr>
              <td width="50%">&nbsp;</td>
              <td width="50%" align="left">
                <input type="button" id="ChangePreferences" class="formButton" value='<spring:message code="user.display.change"/>' />
              </td>
            </tr>
          </table>
          <br /><br /><br /><br /><br /><br /><br /><br />
        </div>
      </div>
    </td>
  </tr>
</table>
