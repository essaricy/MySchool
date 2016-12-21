<%@page import="com.myschool.user.constants.UserType"%>
<%@page import="com.myschool.common.constants.DocumentApplicability"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<c:set var="UserTypes" value="<%=UserType.values()%>"/>
<c:set var="DocumentApplicabilities" value="<%=DocumentApplicability.values()%>"/>

<style>
  #ReportCriteriaTokensContainer { height: 214px; overflow: scroll; }
  #ReportCriteriaTokens .ui-selected { background: #4682B4; color: white; }
  #ReportCriteriaTokens { list-style-type: none; margin: 0; padding: 0; width: 99%; }
  #ReportCriteriaTokens li { margin: 1px; padding: 0.3em; font-size: 1em; height: 16px; }
  #ReportCriteriaValuesContainer {
      width: 100% !important;
  }
</style>
<script src="<%=request.getContextPath()%>/widgets/jquery-ui-1.10.2/ui/jquery-ui.js"></script>
<script type="text/javascript" charset="utf-8">

var URL_MAP = new Object();
URL_MAP.ACADEMIC_YEAR='<%=request.getContextPath()%>/academic/jsonList.htm';
URL_MAP.REGION='<%=request.getContextPath()%>/region/jsonList.htm';
URL_MAP.DESIGNATION='<%=request.getContextPath()%>/designation/jsonList.htm';
URL_MAP.BRANCH='<%=request.getContextPath()%>/branch/jsonList.htm';
URL_MAP.DIVISION='<%=request.getContextPath()%>/division/jsonList.htm';
URL_MAP.EMPLOYMENT_STATUS='<%=request.getContextPath()%>/employment/jsonList.htm';
URL_MAP.REPORTING_TO='<%=request.getContextPath()%>/employee/verifiedEmployeesJSONList.htm';
URL_MAP.SCHOOL='<%=request.getContextPath()%>/school/jsonList.htm';
URL_MAP.CLASS='<%=request.getContextPath()%>/class/jsonList.htm';
URL_MAP.SECTION='<%=request.getContextPath()%>/section/jsonList.htm';
URL_MAP.MEDIUM='<%=request.getContextPath()%>/medium/jsonList.htm';

function getUrl(key) {
    if (key != null && key != '' && key != 'null') {
        return URL_MAP[key];
    }
    return null;
}

$(document).ready(function() {
  var numberOfTokens = 0;
  var urlFromMap = null;

  <c:if test="${REPORT != null}">
    <c:if test="${REPORT.reportCriteriaTokens == null}">
      var ReportData = new Object();
      var ReportCriteriaTokens = new Array();
      ReportData.ReportKey=$('#ReportKey').val();
      ReportData.ReportCriteriaTokens=ReportCriteriaTokens;
      var url = '<%=request.getContextPath()%>/reports/generateReport.htm?ReportData=' + encodeURI(JSON.stringify(ReportData).trim());
      openWindow(url, $('#ReportName').val(), $(window).width()-100, $(window).height()-60);
    </c:if>

    <c:if test="${REPORT.reportCriteriaTokens != null}">
      numberOfTokens = '${fn:length(REPORT.reportCriteriaTokens)}';
      <c:forEach var="ReportCriteriaToken" items="${REPORT.reportCriteriaTokens}" varStatus="count">
        <c:set var="ReportCriteriaIdVal" value="${REPORT.reportKey}_${ReportCriteriaToken.criteriaName}" />
        <c:set var="ReportCriteriaId" value="${fn:replace(ReportCriteriaIdVal, ' ', '_')}" />
          
        <c:choose>
          <c:when test="${ReportCriteriaToken.controlType == 'TEXT'}">
            // Test script execution
          </c:when>
          <c:when test="${ReportCriteriaToken.controlType == 'DATE'}">
            // Date script execution
            $(this).datePicker({id: '${ReportCriteriaId}'});
          </c:when>
          <c:when test="${ReportCriteriaToken.controlType == 'DATE_RANGE'}">
            // Date script execution
            $(this).datePicker({
              rangeId1: '${ReportCriteriaId}_Min',
              rangeId2: '${ReportCriteriaId}_Max'
            });
          </c:when>
          <c:when test="${ReportCriteriaToken.controlType == 'YES_NO'}">
            // Decision script execution
          </c:when>
          <c:when test="${ReportCriteriaToken.controlType == 'SELECTION'}">
            urlFromMap = getUrl('${ReportCriteriaToken.reference}');
            if (urlFromMap == null) {
                $('#${ReportCriteriaId}').chosen({width: "92%"});
            } else {
              <c:choose>
                <c:when test="${ReportCriteriaToken.reference == 'ACADEMIC_YEAR'}">
                  $(this).lazySelect({
                    id: '${ReportCriteriaId}', url: urlFromMap, valueIndices: [0]
                  });     
                </c:when>
                <c:when test="${ReportCriteriaToken.reference == 'BRANCH'}">
                  $(this).lazySelect({
                    id: '${ReportCriteriaId}', url: urlFromMap, codeIndex: 0, valueIndices: [2]
                  });     
                </c:when>
                <c:when test="${ReportCriteriaToken.reference == 'DESIGNATION'}">
                  $(this).lazySelect({
                    id: '${ReportCriteriaId}', url: urlFromMap, prefixCode: true
                  });     
                </c:when>
                <c:when test="${ReportCriteriaToken.reference == 'REPORTING_TO'}">
                  $(this).lazySelect({
                    id: '${ReportCriteriaId}', url: urlFromMap, prefixCode: true, codeIndex: 1, valueIndices: [2, 3, 4]
                  });     
                </c:when>
                <c:when test="${ReportCriteriaToken.reference == 'SCHOOL'}">
                  $(this).lazySelect({
                    id: '${ReportCriteriaId}', url: urlFromMap, valueIndices: [2]
                  });     
                </c:when>
                <c:otherwise>
                  $(this).lazySelect({
                    id: '${ReportCriteriaId}', url: urlFromMap
                  });
                </c:otherwise>
              </c:choose>
            }
          </c:when>
        </c:choose>
      </c:forEach>
    </c:if>
  </c:if>

  $( "#ReportCriteriaTokens" ).selectable({
    stop:function(event, ui) {
      $(event.target).children('.ui-selected').not(':first').removeClass('ui-selected');
    },
    selected: function( event, ui ) {
      $('#ReportCriteriaTokens li').each(function(index, value) {
        if ($(this).hasClass('ui-selected')) {
          var selectedTokenOptionValue = $(this).attr('value');
          var selectedTokenOptionIndex = selectedTokenOptionValue.split('_')[1];
          for (var index=0; index<numberOfTokens; index++) {
            if (index == selectedTokenOptionIndex) {
              $('#TOKEN_VALUE_CONTAINER_' + selectedTokenOptionIndex).show();
            } else {
              $('#TOKEN_VALUE_CONTAINER_' + index).hide();
            }
          }
        }
      });
    }
  });
  for (var index=0; index<numberOfTokens; index++) {
   $('#TOKEN_VALUE_CONTAINER_' + index).hide();
  }

  $('#GenerateReport').click(function() {
    var ReportCriteriaToken = null;
    var ReportData = new Object();
    var ReportCriteriaTokens = new Array();

    <c:if test="${REPORT.reportCriteriaTokens != null}">
      <c:forEach var="ReportCriteriaToken" items="${REPORT.reportCriteriaTokens}" varStatus="count">
        <c:set var="ReportCriteriaIdVal" value="${REPORT.reportKey}_${ReportCriteriaToken.criteriaName}" />
        <c:set var="ReportCriteriaId" value="${fn:replace(ReportCriteriaIdVal, ' ', '_')}" />

        ReportCriteriaToken = new Object();
        ReportCriteriaToken.CriteriaName='${ReportCriteriaToken.criteriaName}';
        ReportCriteriaToken.CriteriaValue='';

        <c:choose>
          <c:when test="${ReportCriteriaToken.controlType == 'TEXT'}">
            // Get text value from text field
            ReportCriteriaToken.CriteriaValue=$('#${ReportCriteriaId}').val();
          </c:when>
          <c:when test="${ReportCriteriaToken.controlType == 'TEXT_RANGE'}">
            // Test script execution
            ReportCriteriaToken.CriteriaValue=$('#${ReportCriteriaId}_Min').val() + ',' + $('#${ReportCriteriaId}_Max').val();
          </c:when>
          <c:when test="${ReportCriteriaToken.controlType == 'DATE'}">
            // Get text value from date field
            ReportCriteriaToken.CriteriaValue=$('#${ReportCriteriaId}').val();
          </c:when>
          <c:when test="${ReportCriteriaToken.controlType == 'DATE_RANGE'}">
            // Date range script execution
            ReportCriteriaToken.CriteriaValue=$('#${ReportCriteriaId}_Min').val() + ',' + $('#${ReportCriteriaId}_Max').val();
          </c:when>
          <c:when test="${ReportCriteriaToken.controlType == 'YES_NO'}">
            // Get text value from date field
          </c:when>
          <c:when test="${ReportCriteriaToken.controlType == 'SELECTION'}">
            // Get text value from select field
            <c:choose>
              <c:when test="${ReportCriteriaToken.use == 'CODE'}">
                ReportCriteriaToken.CriteriaValue=$('#${ReportCriteriaId} :selected').val();
              </c:when>
              <c:when test="${ReportCriteriaToken.use == 'VALUE'}">
                if ($('#${ReportCriteriaId} :selected').val() != '') {
                  ReportCriteriaToken.CriteriaValue=$('#${ReportCriteriaId} :selected').text();
                }
              </c:when>
              <c:otherwise>
                  ReportCriteriaToken.CriteriaValue='';
              </c:otherwise>
            </c:choose>
          </c:when>
        </c:choose>
        if (ReportCriteriaToken.CriteriaValue!='') {
            ReportCriteriaTokens[ReportCriteriaTokens.length]=ReportCriteriaToken;
        }
      </c:forEach>
    </c:if>

    ReportData.ReportKey=$('#ReportKey').val();
    ReportData.ReportCriteriaTokens=ReportCriteriaTokens;
    //alert(JSON.stringify(ReportData).trim());
    //alert(encodeURIComponent(JSON.stringify(ReportData).trim()));
    var url = '<%=request.getContextPath()%>/reports/generateReport.htm?ReportData=' + encodeURIComponent(JSON.stringify(ReportData).trim());
    closeCurrentWindow();

    openWindow(url, $('#ReportName').val(), $(window).width()-100, $(window).height()-60);
  });
});

</script>

<input type="hidden" id="ReportData" name="ReportData" value="" />
<c:if test="${REPORT == null}">
There is no such report.
</c:if>
<c:if test="${REPORT != null}">
<input type="hidden" id="ReportKey" value="${REPORT.reportKey}" />
<input type="hidden" id="ReportName" value="${REPORT.reportName}" />
<table class="formTable_Container">
  <caption>${REPORT.reportName}</caption>
  <thead>
    <tr>
      <td width="50%"><b>Report Criteria</b></td>
      <td width="50%"><b>Criteria Value</b></td>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td width="50%">
       <div id="ReportCriteriaTokensContainer">
       <c:if test="${REPORT.reportCriteriaTokens != null}">
        <ol id="ReportCriteriaTokens">
        <c:forEach var="ReportCriteriaToken" items="${REPORT.reportCriteriaTokens}" varStatus="count">
          <li class="ui-widget-content" value="TOKEN_${count.index}">${ReportCriteriaToken.criteriaName}</li>
        </c:forEach>
        </ol>
        </c:if>
        </div>
      </td>
      <td width="50%" valign="top">
        <div id="ReportCriteriaValuesContainer">
        <c:if test="${REPORT.reportCriteriaTokens != null}">
        <c:forEach var="ReportCriteriaToken" items="${REPORT.reportCriteriaTokens}" varStatus="count">
          <c:set var="ReportCriteriaIdVal" value="${REPORT.reportKey}_${ReportCriteriaToken.criteriaName}" />
          <c:set var="ReportCriteriaId" value="${fn:replace(ReportCriteriaIdVal, ' ', '_')}" />
          <div id="TOKEN_VALUE_CONTAINER_${count.index}">
            <c:choose>
              <c:when test="${ReportCriteriaToken.controlType == 'TEXT'}">
                <input type="text" id="${ReportCriteriaId}"/>
              </c:when>
              <c:when test="${ReportCriteriaToken.controlType == 'TEXT_RANGE'}">
                Between <br/>
                <input type="text" id="${ReportCriteriaId}_Min" /><br/>
                and <br/>
                <input type="text" id="${ReportCriteriaId}_Max" /><br/>
              </c:when>
              <c:when test="${ReportCriteriaToken.controlType == 'DATE'}">
                <input type="text" id="${ReportCriteriaId}" class="datepicker" />
              </c:when>
              <c:when test="${ReportCriteriaToken.controlType == 'DATE_RANGE'}">
                Between <br/>
                <input type="text" id="${ReportCriteriaId}_Min" class="datepicker" /><br/>
                and <br/>
                <input type="text" id="${ReportCriteriaId}_Max" class="datepicker" /><br/>
              </c:when>
              <c:when test="${ReportCriteriaToken.controlType == 'YES_NO'}">
                <input type="checkbox" value="Y"/>
                <input type="checkbox" value="N"/>
              </c:when>
              <c:when test="${ReportCriteriaToken.controlType == 'SELECTION'}">
                <c:choose>
                  <c:when test="${ReportCriteriaToken.reference == 'BLOOD_GROUP'}">
                    <myschool:bloodgroup id="${ReportCriteriaId}" uiControl="select" clazz="chosen-select" showFirstSelectOption="true" firstSelectOptionValue="" firstSelectOptionText="Any" />
                  </c:when>
                  <c:when test="${ReportCriteriaToken.reference == 'DOCUMENT_APPLICABLE_FOR'}">
                    <select id="${ReportCriteriaId}" class="chosen-select">
                      <option value="">Any</option>
                      <option value="EMPLOYEE">Employee</option>
                      <option value="STUDENT">Student</option>
                    </select>
                  </c:when>
                  <c:when test="${ReportCriteriaToken.reference == 'DOCUMENT_APPLICABILITY'}">
                    <select id="${ReportCriteriaId}" class="chosen-select">
                      <option value="">Any</option>
                      <c:forEach var="DocumentApplicability" items="${DocumentApplicabilities}">
                      <option value="${DocumentApplicability.applicabilityCode}">${DocumentApplicability}</option>
                      </c:forEach>
                    </select>
                  </c:when>
                  <c:when test="${ReportCriteriaToken.reference == 'GENDER'}">
                    <myschool:gender id="${ReportCriteriaId}" uiControl="select" clazz="chosen-select" showFirstSelectOption="true" firstSelectOptionValue="" firstSelectOptionText="Any" />
                  </c:when>
                  <c:when test="${ReportCriteriaToken.reference == 'RELIGION'}">
                    <myschool:religion id="${ReportCriteriaId}" uiControl="select" clazz="chosen-select" showFirstSelectOption="true" firstSelectOptionValue="" firstSelectOptionText="Any" />
                  </c:when>
                  <c:when test="${ReportCriteriaToken.reference == 'VERIFIED_STATUS'}">
                    <select id="${ReportCriteriaId}" class="chosen-select">
                      <option value="">Any</option>
                      <option value="Y">Verified</option>
                      <option value="N">Unverified</option>
                    </select>
                  </c:when>
                  <c:otherwise>
                    <select id="${ReportCriteriaId}" class="chosen-select">
                      <option value="">Any</option>
                    </select>
                  </c:otherwise>
                </c:choose>
              </c:when>
              <c:otherwise>Unsupported data type.</c:otherwise>
            </c:choose>
          </div>
        </c:forEach>
        </c:if>
        </div>
      </td>
    </tr>
    <tr>
      <td colspan="2" align="right">
        <input type="button" id="GenerateReport" value="Generate Report" />
      </td>
    </tr>
  </body>
</table>
</c:if>
