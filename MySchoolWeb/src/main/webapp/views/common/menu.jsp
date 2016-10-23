<%@page import="com.myschool.web.application.constants.WebConstants"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<style>
img.menuItemImage {
    width: 14px;
    height: 14px;
}
</style>

<c:if test="${USER_CONTEXT != null && ORGANIZATION_PREFERENCES != null}">
    <c:if test="${USER_CONTEXT.moduleAccess != null}">
        <ul id="solidmenu" class="solidblockmenu">
            <li>
                <a href="<%=request.getContextPath()%>/user/viewDashboard.htm">
                    <img src="<%=request.getContextPath()%>/images/icons/home.png" style="float:left;" />&nbsp;Home
                </a>
            </li>
            <c:forEach var="moduleAccess" items="${USER_CONTEXT.moduleAccess}">
                <c:if test="${moduleAccess.moduleAccessible == true}">
                    <li>
                        <a href="${moduleAccess.accessUrl}" rel='${fn:replace(moduleAccess.moduleName," ", "_")}_Menu'>${moduleAccess.moduleName}</a>
                    </li>
                </c:if>
            </c:forEach>
        </ul>

        <c:forEach var="moduleAccess" items="${USER_CONTEXT.moduleAccess}">
            <c:if test="${moduleAccess.functionAccess != null}">
                <div id='${fn:replace(moduleAccess.moduleName, " ", "_")}_Menu' class="mega solidblocktheme">
                    <div class="column">
                        <ul>
                            <c:forEach var="functionAccess" items="${moduleAccess.functionAccess}">
                                <c:if test="${functionAccess.view == true}">
                                    <li>
                                        <c:if test="${ORGANIZATION_PREFERENCES.useMenuIcons == true && functionAccess.iconUrl != null}">
                                        <img src="<%=request.getContextPath()%>/images/icons/${functionAccess.iconUrl}" class="menuItemImage" />
                                        &nbsp;
                                        </c:if>
                                        <a href="<%=request.getContextPath()%>/${functionAccess.accessUrl}">${functionAccess.functionName}</a>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </c:if>
        </c:forEach>
    </c:if>
</c:if>