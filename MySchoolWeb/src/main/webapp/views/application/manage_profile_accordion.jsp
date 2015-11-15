<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- TODO: Rename this view name -->
<style>
#ProfilesAccordion p {
  font-size: 0.8em;
  font-weight: bold;
  text-align: left;
}
</style>
<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
  $(this).myAccordion({id: 'ProfilesAccordion'});

  $('#address').textcounter({id: 'address'});
  setEmailOptions($("#emailActive").is(":checked"));
  setSmsOptions($("#smsActive").is(":checked"));

  $("#emailActive").click(function() {
      setEmailOptions($(this).is(":checked"));
  });

  $("#smsActive").click(function() {
      setSmsOptions($(this).is(":checked"));
  });

  function setEmailOptions(checked) {
    $("#emailEmployees").attr('disabled', !checked);
    $("#emailStudents").attr('disabled', !checked);
    if (!checked) {
      $("#emailEmployees").attr('checked', checked);
      $("#emailStudents").attr('checked', checked);
    }
  }

  function setSmsOptions(checked) {
    $("#smsEmployees").attr('disabled', !checked);
    $("#smsStudents").attr('disabled', !checked);
    if (!checked) {
      $("#smsEmployees").attr('checked', checked);
      $("#smsStudents").attr('checked', checked);
    }
  }

  $('#SaveOrganizationProfile').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/profile/updateOrganizationProfile.htm",
      data: {
        address:  $('#address').val(),
        phoneNumber: $('#phoneNumber').val(),
        faxNumber: $('#faxNumber').val(),
        sid: new Date().getTime()
      }, 
      context: this
    }).done(function(result) {
      parseWholepageResponse(result, false);
    });
  });

  $('#SaveMySchoolProfile').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/profile/updateMySchoolProfile.htm",
      data: {
        emailActive: $('#emailActive').is(":checked"),
        emailEmployees: $('#emailEmployees').is(":checked"),
        emailStudents: $('#emailStudents').is(":checked"),
        smsActive: $('#smsActive').is(":checked"),
        smsEmployees: $('#smsEmployees').is(":checked"),
        smsStudents: $('#smsStudents').is(":checked"),
        useMenuIcons: $('#useMenuIcons').is(":checked"),
        useEmployeeSelfSubmit: $('#useEmployeeSelfSubmit').is(":checked"),
        useStudentSelfSubmit: $('#useStudentSelfSubmit').is(":checked"),
        sid: new Date().getTime()
      }, 
      context: this
    }).done(function(result) {
      parseWholepageResponse(result, false);
    });
  });
});
</script>

<table width="70%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <caption class="dataTableCaption">Manage Profiles</caption>
  <tr>
    <td>
      <div id="ProfilesAccordion">
        <p class="title"><spring:message code="organization.profile"/></p>
        <c:if test="${OrganizationProfile == null}">
          <spring:message code="organization.not.defined"/>
        </c:if>
        <c:if test="${OrganizationProfile != null}">
        <div>
          <table cellpadding="5" cellspacing="0" align="center" width="100%" class="formDataTable" border="0">
            <tbody>
              <tr>
                <td width="50%" class="formLabel"><spring:message code="organization.name"/></td>
                <td width="50%" align="left">
                  <input type="hidden" id="organizationName" value="${OrganizationProfile.organizationName}" />
                  <b>${OrganizationProfile.organizationName}</b>
                </td>
              </tr>
              <tr>
                <td width="50%" class="formLabel"><spring:message code="organization.currentAcademicYear"/></td>
                <td width="50%" align="left">
                  <input type="hidden" id="currentAcademicYear" value="${OrganizationProfile.currentAcademicYear}" />
                  <b>${OrganizationProfile.currentAcademicYear}</b>
                </td>
              </tr>
              <tr>
                <td width="50%" class="formLabel" valign="top"><spring:message code="common.address"/><label class="mandatory">*</label></td>
                <td width="50%" align="left">
                  <textarea id="address" rows="5" cols="30" maxlength="128">${OrganizationProfile.address}</textarea>
                </td>
              </tr>
              <tr>
                <td width="50%" class="formLabel" valign="top"><spring:message code="common.phoneNumber"/><label class="mandatory">*</label></td>
                <td width="50%" align="left">
                  <input type="text" id="phoneNumber" maxlength="16" value="${OrganizationProfile.phoneNumber}" />
                </td>
              </tr>
              <tr>
                <td width="50%" class="formLabel" valign="top"><spring:message code="common.faxNumber"/></td>
                <td width="50%" align="left">
                  <input type="text" id="faxNumber" maxlength="16" value="${OrganizationProfile.faxNumber}" />
                </td>
              </tr>
            </tbody>
            <tfoot>
              <tr>
                <td colspan="2" align="center">
                <c:choose>
                  <c:when test="${PAGE_ACCESS != null && PAGE_ACCESS.update}">
                    <input type="button" id="SaveOrganizationProfile" class="formButton" style="width:120px;" value='Save Changes' />
                  </c:when>
                  <c:otherwise>
                    <input type="button" class="inactive" style="width:120px;" value='Save Changes'  disabled />
                  </c:otherwise>
                </c:choose>
                </td>
              </tr>
            </tfoot>
          </table>
        </div>
        </c:if>

        <p class="title">MySchool Profile</p>
        <c:if test="${MySchoolProfile == null}">
          MySchool Profile is not defined.
        </c:if>
        <c:if test="${MySchoolProfile != null}">
        <div>
          <table cellpadding="5" cellspacing="0" align="center" class="formDataTable" border="0" width="100%">
            <tbody>
              <!-- Email service configuration -->
              <tr>
                <td width="50%" class="formLabel">Use Email Service</td>
                <td width="50%" align="left">
                <c:if test="${MySchoolProfile.emailActive}">
                  <input id="emailActive" type="checkbox" checked />
                </c:if>
                <c:if test="${!MySchoolProfile.emailActive}">
                  <input id="emailActive" type="checkbox" />
                </c:if>
                </td>
              </tr>
              <tr>
                <td width="50%" class="formLabel">Use Email Service for Employees</td>
                <td width="50%" align="left">
                <c:if test="${MySchoolProfile.emailEmployees}">
                  <input id="emailEmployees" type="checkbox" checked />
                </c:if>
                <c:if test="${!MySchoolProfile.emailEmployees}">
                  <input id="emailEmployees" type="checkbox" />
                </c:if>
                </td>
              </tr>
              <tr>
                <td width="50%" class="formLabel">Use Email Service for Students</td>
                <td width="50%" align="left">
                <c:if test="${MySchoolProfile.emailStudents}">
                  <input id="emailStudents" type="checkbox" checked />
                </c:if>
                <c:if test="${!MySchoolProfile.emailStudents}">
                  <input id="emailStudents" type="checkbox" />
                </c:if>
                </td>
              </tr>

              <!-- SMS service configuration -->
              <tr>
                <td width="50%" class="formLabel">Use SMS Service</td>
                <td width="50%" align="left">
                <c:if test="${MySchoolProfile.smsActive}">
                  <input id="smsActive" type="checkbox" checked />
                </c:if>
                <c:if test="${!MySchoolProfile.smsActive}">
                  <input id="smsActive" type="checkbox" />
                </c:if>
                </td>
              </tr>
              <tr>
                <td width="50%" class="formLabel">Use SMS Service for Employees</td>
                <td width="50%" align="left">
                <c:if test="${MySchoolProfile.smsEmployees}">
                  <input id="smsEmployees" type="checkbox" checked />
                </c:if>
                <c:if test="${!MySchoolProfile.smsEmployees}">
                  <input id="smsEmployees" type="checkbox" />
                </c:if>
                </td>
              </tr>
              <tr>
                <td width="50%" class="formLabel">Use SMS Service for Students</td>
                <td width="50%" align="left">
                <c:if test="${MySchoolProfile.smsStudents}">
                  <input id="smsStudents" type="checkbox" checked />
                </c:if>
                <c:if test="${!MySchoolProfile.smsStudents}">
                  <input id="smsStudents" type="checkbox" />
                </c:if>
                </td>
              </tr>

              <!-- Menu Options -->
              <tr>
                <td width="50%" class="formLabel">Use Icons in Menu</td>
                <td width="50%" align="left">
                <c:if test="${MySchoolProfile.useMenuIcons}">
                  <input id="useMenuIcons" type="checkbox" checked />
                </c:if>
                <c:if test="${!MySchoolProfile.useMenuIcons}">
                  <input id="useMenuIcons" type="checkbox" />
                </c:if>
                </td>
              </tr>

              <!-- User self submit options Options -->
              <tr>
                <td width="50%" class="formLabel">Use Employee Self-Submit</td>
                <td width="50%" align="left">
                <c:if test="${MySchoolProfile.useEmployeeSelfSubmit}">
                  <input id="useEmployeeSelfSubmit" type="checkbox" checked />
                </c:if>
                <c:if test="${!MySchoolProfile.useEmployeeSelfSubmit}">
                  <input id="useEmployeeSelfSubmit" type="checkbox" />
                </c:if>
                </td>
              </tr>
              <!-- Menu Options -->
              <tr>
                <td width="50%" class="formLabel">Use Student Self-Submit</td>
                <td width="50%" align="left">
                <c:if test="${MySchoolProfile.useStudentSelfSubmit}">
                  <input id="useStudentSelfSubmit" type="checkbox" checked />
                </c:if>
                <c:if test="${!MySchoolProfile.useStudentSelfSubmit}">
                  <input id="useStudentSelfSubmit" type="checkbox" />
                </c:if>
                </td>
              </tr>

            <tbody>
            <tfoot>
              <tr>
                <td colspan="2" align="center">
                <c:choose>
                  <c:when test="${PAGE_ACCESS != null && PAGE_ACCESS.update}">
                    <input type="button" id="SaveMySchoolProfile" class="formButton" style="width:120px;" value='Save Changes' />
                  </c:when>
                  <c:otherwise>
                    <input type="button" class="inactive" style="width:120px;" value='Save Changes' disabled />
                  </c:otherwise>
                </c:choose>
                </td>
              </tr>
            </tfoot>
          </table>
        </div>
        </c:if>
      </div>
    </td>
  </tr>
</table>