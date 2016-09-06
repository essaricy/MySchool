<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<style>
.LastVisit {
  font-weight: bold;
  text-transform:capitalize;
}
</style>

<script type="text/javascript" language="javascript" src="<%=request.getContextPath() %>/widgets/jquery.passstrength/jquery.passstrength.js"></script>
<script type="text/javascript" language="javascript" src="<%=request.getContextPath() %>/widgets/jquery.dateFormat/jquery-dateFormat.min.js"></script>
<script type="text/javascript" language="javascript" src="<%=request.getContextPath() %>/widgets/jquery.timeago/jquery.timeago.js"></script>
<script type="text/javascript" charset="utf-8">
jQuery(document).ready(function() {
  $('#SettingsContainer').tabs({id: 'SettingsContainer'});
  $("#SettingsContainer").tabs("option", "active", 2);

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
        handleServerResponseOnPage(result, false);
    });
  });

  $('#ChangePreferences').click(function() {
    var ChangePreferenceDetails = new Object();
    ChangePreferenceDetails.UserId=$('#UserId').val();
    ChangePreferenceDetails.ThemeCode=$('#ThemeCode').val();
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
        handleServerResponseOnPage(result, false);
    });
  });
  $('#NewPassword').passStrengthify();
  $('.chosen-select').chosen({width: "98%"});
});
</script>


<table class="formTable_Data" style="width: 70%">
  <caption>Settings</caption>
  <tr>
    <td>
      <div id="SettingsContainer">
        <ul>
          <li><a href="#UserAccountDetailsTab"><spring:message code="user.account.details"/></a></li>
          <li><a href="#ChangePasswordTab"><spring:message code="password.change"/></a></li>
          <li><a href="#DisplayPreferencesTab"><spring:message code="user.display.prefernces"/></a></li>
        </ul>

        <div id="UserAccountDetailsTab">
          <table class="formTable_Data" style="font-size: 0.75em;">
            <tr>
              <td width="50%" class="label"><spring:message code="user.login.name"/></td>
              <td width="50%" class="left"><b>${USER_CONTEXT.login.loginId}</b></td>
            </tr>
            <tr>
              <td width="50%" class="label"><spring:message code="user.type"/></td>
              <td width="50%" align="left"><b>${USER_CONTEXT.userType}</b></td>
            </tr>
            <tr>
              <td width="50%" class="label"><spring:message code="user.number.of.visits"/></td>
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
              <td width="50%" class="label"><spring:message code="user.last.visit"/></td>
              <td width="50%" align="left">
                <abbr id="LastVisitOn" title="${USER_CONTEXT.userStatistics.lastVisitOn}" class="LastVisit">
                  ${USER_CONTEXT.userStatistics.lastVisitOn}
                </abbr>
              </td>
            </tr>
          </table>
        </div>

        <div id="ChangePasswordTab" style="font-size: 0.75em;">
          <input type="hidden" id="UserId" value="${USER_CONTEXT.login.id}" />
          <table cellpadding="5" cellspacing="0" align="center" width="70%" class="formDataTable" border="0">
            <tr>
              <td width="50%" class="label"><spring:message code="password.current"/><label class="mandatory">*</label></td>
              <td width="50%">
                <input type="password" id="CurrentPassword"/>
              </td>
            </tr>
            <tr>
              <td width="50%" class="label"><spring:message code="password.new"/><label class="mandatory">*</label></td>
              <td width="50%">
                <input type="password" id="NewPassword" maxlength="128" />
              </td>
            </tr>
            <tr>
              <td width="50%" class="label"><spring:message code="password.confirm"/><label class="mandatory">*</label></td>
              <td width="50%">
                <input type="password" id="ConfirmedPassword" maxlength="128" />
              </td>
            </tr>
            <tr>
              <td width="50%">&nbsp;</td>
              <td width="50%" align="left">
                <input type="button" id="ChangePassword" value='<spring:message code="password.change"/>' />
              </td>
            </tr>
          </table>
        </div>

        <!-- User preferences section -->
        <div id="DisplayPreferencesTab" style="font-size: 0.75em;">
          <table cellpadding="5" cellspacing="0" align="center" width="70%" class="formDataTable" border="0">
            <tr>
              <td width="50%" class="label"><spring:message code="user.display.theme"/></td>
              <td width="50%" align="left">
                <select id="ThemeCode" class="chosen-select">
                  <c:forEach var="UserTheme" items="${UserThemes}">
                    <c:if test="${USER_CONTEXT.userPreference.userTheme.code == UserTheme.code}">
                      <option value="${UserTheme.code}" selected>${UserTheme.name}</option>
                    </c:if>
                    <c:if test="${USER_CONTEXT.userPreference.userTheme.code != UserTheme.code}">
                      <option value="${UserTheme.code}">${UserTheme.name}</option>
                    </c:if>
                  </c:forEach>
                </select>
              </td>
            </tr>
            <tr>
              <td width="50%" class="label"><spring:message code="user.display.records.per.page"/></td>
              <td width="50%" align="left">
                <select id="RecordsPerPage" class="chosen-select">
                  <c:forTokens items="10,25,50,100" delims="," var="recordsPerPage">
                    <c:if test="${USER_CONTEXT.userPreference.recordsPerPage == recordsPerPage}">
                    <option value="${recordsPerPage}" selected>${recordsPerPage}</option>
                    </c:if>
                    <c:if test="${USER_CONTEXT.userPreference.recordsPerPage != recordsPerPage}">
                    <option value="${recordsPerPage}">${recordsPerPage}</option>
                    </c:if>
                  </c:forTokens>
                </select>
              </td>
            </tr>
            <tr>
              <td width="50%" class="label"><spring:message code="user.display.advertisements"/></td>
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
                <input type="button" id="ChangePreferences" value='<spring:message code="user.display.change"/>' />
              </td>
            </tr>
          </table>
        </div>
      </div>
    </td>
  </tr>
</table>
