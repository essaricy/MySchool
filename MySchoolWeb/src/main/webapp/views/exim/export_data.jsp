<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript" charset="utf-8">
    $(document).ready(function() {
        $('#exportButton').click(function() {
            var exportType = $('#exports').val();
            submitPage('<%=request.getContextPath()%>/export/exportData.htm', '');
            var viewEmployeeDialog = dhtmlwindow.progress();
        });
        $('.chosen-select').chosen({width: "100%"});
    });
</script>

<c:if test="${result != null && result.statusMessage != null}">
<table cellpadding="0" cellspacing="0" width="60%" border="0">
    <tr>
        <td colspan="3" class="error">${result.statusMessage}</td>
    </tr>
</table>
</c:if>
<table cellpadding="10" cellspacing="0" width="60%" border="0" class="formTable">
    <caption class="dataTableCaption"><spring:message code="export.title"/></caption>
    <tr>
        <td class="formLabel" width="49%"><spring:message code="export.label"/></td>
        <td width="2%">&nbsp;</td>
        <td width="49%" align="left">
            <select id="exportKey" name="exportKey" class="chosen-select">
                <c:if test="${exims != null}">
                    <c:forEach var="exim" items="${exims}">
                        <c:if test="${exim.eximKey == exportKey}">
                            <option value="${exim.eximKey}" selected>${exim.description}</option>
                        </c:if>
                        <c:if test="${exim.eximKey != exportKey}">
                            <option value="${exim.eximKey}">${exim.description}</option>
                        </c:if>
                    </c:forEach>
                </c:if>
            </select>
        </td>
    </tr>
    <tr>
        <td colspan="3" align="center">
            <input id="exportButton" type="button" class="formButton" value='<spring:message code="export.label"/>'/>
        </td>
    </tr>
</table>
</p>