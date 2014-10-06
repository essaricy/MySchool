<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<script type="text/javascript" charset="utf-8">
$(document).ready(function () {
    $('.chosen-select').chosen({width: "60%"});
});

var VIEW_COLUMN_INDEX = 2;
var CREATE_COLUMN_INDEX = 3;
var UPDATE_COLUMN_INDEX = 4;
var DELETE_COLUMN_INDEX = 5;

function show(tableRow) {
    var img = document.getElementById('image_' + tableRow);
    if (img.alt == 'Expand'){
        $('#functions_table_div_' + tableRow).slideDown(1000);
        img.src = "<%=request.getContextPath()%>/images/icons/triangle_up.png";
        img.alt = "Collapse";
    } else if (img.alt == 'Collapse'){
        $('#functions_table_div_' + tableRow).slideUp(1000);
        img.src = "<%=request.getContextPath()%>/images/icons/triangle_down.png";
        img.alt = "Expand";
    }
}

function getPrivileges(changeType, id) {
        if (changeType == 'UserType') {
            document.forms[0].userTypeId.value = id;
            if (typeof(document.forms[0].userId) != 'undefined') {
                document.forms[0].userId.value = "";
            }
        } else if (changeType == 'User') {
            if (id > 0) {
                document.forms[0].userId.value = id;
            } else {
                document.forms[0].userId.value = "";
            }
        }
        submitPage('<%=request.getContextPath()%>/privileges/userPrivileges.htm', '');
}

function submitForm() {
    var userTypeId = document.forms[0].userTypeId.value;
    var userId = document.forms[0].userId.value;
    var numberOfModules = parseInt(document.getElementById('numberOfModules').value);
    var privilegesData = '{"userTypeId":"'+ userTypeId + '", "userId": "' + userId + '", "modules":{ \n' ;

    var moduleData = "";
    for (var moduleIndex=1; moduleIndex<numberOfModules+1; moduleIndex++) {
        moduleData = moduleData + ' "' + document.getElementById('module_' + moduleIndex).value + '": {';

        var functionsTable = document.getElementById('functions_table_' + moduleIndex);
        var numberOfFunctions = functionsTable.rows.length;
        var functionData = "";

        for (var functionIndex=1; functionIndex<numberOfFunctions+1; functionIndex++) {
            functionData = functionData
                + ' "' + document.getElementById('module_' + moduleIndex + '_function_' + functionIndex).value
                + '": ['
                + '"' + document.getElementById('module_' + moduleIndex + '_function_' + functionIndex + '_view').checked + '", '
                + '"' + document.getElementById('module_' + moduleIndex + '_function_' + functionIndex + '_create').checked + '", '
                + '"' + document.getElementById('module_' + moduleIndex + '_function_' + functionIndex + '_update').checked + '", '
                + '"' + document.getElementById('module_' + moduleIndex + '_function_' + functionIndex + '_delete').checked + '"'
                + '], ';
        }
        moduleData = moduleData + functionData + '\n\t }, \n';
    }
    privilegesData = privilegesData + moduleData + '\n }' + '\n }';
    sendData(userId, privilegesData);
    //document.getElementById('privilegesData').value = privilegesData;
    //submitPage('<%=request.getContextPath()%>/privileges/jsonUserSave.htm', '');
}

function sendData(userId, privilegesData) {
    $.ajax({
        type: "POST",
        url: '<%=request.getContextPath()%>/privileges/jsonUserSave.htm',
        data: {
            userId: userId,
            privilegesData: privilegesData
        },
        context: this
    }).done(function(result) {
        parseWholepageResponse(result, true);
    });
  }

function masterCheck(masterId, accessType) {
    var columnIndex = 0;
    var masterCheckBox = document.getElementById('master_' + accessType + '_' + masterId);
    var checked = masterCheckBox.checked;

    var masterTable = document.getElementById('functions_table_' + masterId);
    var numberOfFunctions = masterTable.rows.length;

    if (accessType == 'view') {
        columnIndex = VIEW_COLUMN_INDEX;
    } else if (accessType == 'create') {
        columnIndex = CREATE_COLUMN_INDEX;
    } else if (accessType == 'update') {
        columnIndex = UPDATE_COLUMN_INDEX;
    } else if (accessType == 'delete') {
        columnIndex = DELETE_COLUMN_INDEX;
    }

    var childCheckBox = null;
    for (var rowIndex=0; rowIndex<numberOfFunctions; rowIndex++) {
        childCheckBox = masterTable.rows[rowIndex].cells[columnIndex].childNodes[0];
        childCheckBox.checked = checked;

        if (checked) {
            childCheckBox.className = 'accessCheckBox'
        } else {
            childCheckBox.className = 'noAccessCheckBox'
        }
    }

    if (checked) {
        masterCheckBox.className = 'accessCheckBox'
    } else {
        masterCheckBox.className = 'noAccessCheckBox'
    }
}

function childCheck(childObject, masterId, accessType) {
    var masterCheckBox = document.getElementById('master_' + accessType + '_' + masterId);

    if (childObject.checked) {
            var allChecked = true;
            var columnIndex = 0;
            var masterTable = document.getElementById('functions_table_' + masterId);
            var numberOfFunctions = masterTable.rows.length;

            childObject.className = 'accessCheckBox';
            if (accessType == 'view') {
                columnIndex = 2;
            } else if (accessType == 'create') {
                columnIndex = 3;
            } else if (accessType == 'update') {
                columnIndex = 4;
            } else if (accessType == 'delete') {
                columnIndex = 5;
            }

            for (var rowIndex=0; rowIndex<numberOfFunctions; rowIndex++) {
                if (!masterTable.rows[rowIndex].cells[columnIndex].childNodes[0].checked) {
                    allChecked = false;
                    break;
                }
            }
            if (allChecked) {
                masterCheckBox.checked = true;
                masterCheckBox.className = 'accessCheckBox';
            }
    } else {
        masterCheckBox.checked = false;
        childObject.className = 'noAccessCheckBox';
        masterCheckBox.className = 'noAccessCheckBox';
    }
}

function expandAll() {
    var numberOfModules = parseInt(document.getElementById('numberOfModules').value);
    for (var index=1; index<<numberOfModules+1; index++) {
        $('#functions_table_div_' + index).slideDown(1000);
        var img = document.getElementById('image_' + index);
        img.src = "<%=request.getContextPath()%>/images/icons/triangle_up.png";
        img.alt="Collapse";
    }
}

function collapseAll() {
    var numberOfModules = parseInt(document.getElementById('numberOfModules').value);
    for (var index=1; index<numberOfModules+1; index++) {
        $('#functions_table_div_' + index).slideUp(1000);
        var img = document.getElementById('image_' + index);
        img.src = "<%=request.getContextPath()%>/images/icons/triangle_down.png";
        img.alt="Expand";
    }
}

function restorePrivileges() {
    $.ajax({
        type: "POST",
        url: '<%=request.getContextPath()%>/privileges/restoreUserPrivileges.htm',
        data: {
            userId: $('#userId').val()
        },
        context: this
    }).done(function(result) {
        parseWholepageResponse(result, true);
    });
}
</script>

<table cellpadding="5" cellspacing="0" width="60%" class="formTable">
    <caption class="dataTableCaption">User Privileges</caption>
    <tr>
        <td width="50%" class="formLabel">User Type</td>
        <td width="50%" align="left">
            <c:if test="${userTypes != null}">
                <input type="hidden" name="userTypeId" value="${userTypeId}"/>
                <select name="userTypes" class="chosen-select" onchange="getPrivileges('UserType', this.options[selectedIndex].value)">
                    <option value="0" selected>Select</option>
                    <c:forEach var="userType" items="${userTypes}">
                        <c:if test="${userType.userTypeId == userTypeId}">
                            <option value="${userType.userTypeId}" selected>${userType.description}</option> 
                        </c:if>
                        <c:if test="${userType.userTypeId != userTypeId}">
                            <option value="${userType.userTypeId}">${userType.description}</option> 
                        </c:if>
                    </c:forEach> 
                </select> 
            </c:if>
        </td>
    </tr>
    <c:if test="${userTypeId != null && users == null}">
    <tr>
        <td colspan="2" class="error">No Users Found.</td>
    </tr>
    </c:if>
    <c:if test="${userTypeId != null && users != null}">
    <tr>
        <td width="50%" class="formLabel">User Name</td>
        <td width="50%" align="left">
            <c:if test="${users != null}">
                <input type="hidden" name="userId" id= "userId" value="${userId}"/>
                <select name="users" class="chosen-select" onchange="getPrivileges('User', this.options[selectedIndex].value)">
                    <option value="0" selected>Select</option>
                    <c:forEach var="user" items="${users}">
                        <c:if test="${user.id == userId}">
                            <option value="${user.id}" selected>${user.displayName}</option> 
                        </c:if>
                        <c:if test="${user.id != userId}">
                            <option value="${user.id}">${user.displayName}</option> 
                        </c:if>
                    </c:forEach> 
                </select> 
            </c:if>
        </td>
    </tr>
    </c:if>
    <c:if test="${privileges != null}">
    <tr>
        <td colspan="2" valign="top">&nbsp;
            <input type="hidden" id="numberOfModules" value="${fn:length(privileges)}" />
            <table width="100%" cellpadding="0" cellspacing="0" border="0" class="display" id="defaultPrivileges" valign="top">
                <thead>
                    <tr>
                        <th width="48px" align="left">
                            <img src="<%=request.getContextPath()%>/images/icons/triangle_down.png" class="iconImage" onclick="expandAll()" alt="Expand All"/><img src="<%=request.getContextPath()%>/images/icons/triangle_up.png" class="iconImage" onclick="collapseAll()" alt="Collapse All"/>
                        </th>
                        <th width="32%" align="left">Module</th>
                        <th align="center">View</th>
                        <th align="center">Create</th>
                        <th align="center">Update</th>
                        <th align="center">Delete</th>
                    </tr>
                </thead>
                <tbody>
                        <c:forEach var="moduleAccess" items="${privileges}" varStatus="moduleIndex">
                         <tr>
                            <td width="100%" colspan="100%">
                            <input type="hidden" id="module_${moduleIndex.count}" value="${moduleAccess.moduleId}" />
                                <table cellpadding="0" cellspacing="0" width="100%" border="0">
                                    <tr style="background-color:#CCCCFF;">
                                        <td width="30px"><img id="image_${moduleIndex.count}" src="<%=request.getContextPath()%>/images/icons/triangle_down.png" class="iconImage" onclick="show(${moduleIndex.count})" alt="Expand"/></td>
                                        <td width="*">${moduleAccess.moduleName}</td>
                                        <td align="center" width="14%">
                                            <c:if test="${moduleAccess.allView == true}">
                                                <input id="master_view_${moduleIndex.count}" type="checkbox"
                                                onclick="javascript: masterCheck(${moduleIndex.count}, 'view');" checked/>
                                            </c:if>
                                            <c:if test="${moduleAccess.allView == false}">
                                                <input id="master_view_${moduleIndex.count}" type="checkbox"
                                                onclick="javascript: masterCheck(${moduleIndex.count}, 'view');" />
                                            </c:if>
                                        </td>
                                        <td align="center" width="14%">
                                            <c:if test="${moduleAccess.allCreate == true}">
                                                <input id="master_create_${moduleIndex.count}" type="checkbox"
                                                onclick="javascript: masterCheck(${moduleIndex.count}, 'create');" checked/>
                                            </c:if>
                                            <c:if test="${moduleAccess.allCreate == false}">
                                                <input id="master_create_${moduleIndex.count}" type="checkbox"
                                                onclick="javascript: masterCheck(${moduleIndex.count}, 'create');" />
                                            </c:if>
                                        </td>
                                        <td align="center" width="14%">
                                            <c:if test="${moduleAccess.allUpdate == true}">
                                                <input id="master_update_${moduleIndex.count}" type="checkbox"
                                                onclick="javascript: masterCheck(${moduleIndex.count}, 'update');" checked/>
                                            </c:if>
                                            <c:if test="${moduleAccess.allUpdate == false}">
                                                <input id="master_update_${moduleIndex.count}" type="checkbox"
                                                onclick="javascript: masterCheck(${moduleIndex.count}, 'update');" />
                                            </c:if>
                                        </td>
                                        <td align="center" width="14%">
                                            <c:if test="${moduleAccess.allDelete == true}">
                                                <input id="master_delete_${moduleIndex.count}" type="checkbox"
                                                onclick="javascript: masterCheck(${moduleIndex.count}, 'delete');" checked/>
                                            </c:if>
                                            <c:if test="${moduleAccess.allDelete == false}">
                                                <input id="master_delete_${moduleIndex.count}" type="checkbox"
                                                onclick="javascript: masterCheck(${moduleIndex.count}, 'delete');" />
                                            </c:if>
                                        </td>
                                    </tr>
                                </table>
                                <div id="functions_table_div_${moduleIndex.count}" style="display:none;" width="100%">
                                    <table cellpadding="0" cellspacing="0" width="100%" style="border:1px black solid;" id="functions_table_${moduleIndex.count}">
                                        <c:forEach var="functionAccess" items="${moduleAccess.functionAccess}" varStatus="functionIndex">
                                        <input type="hidden" id="module_${moduleIndex.count}_function_${functionIndex.count}" value="${functionAccess.functionId}" />
                                            <tr style="background-color:#EFEFEF;">
                                                <td width="20px">&nbsp;</td>
                                                <td width="*">${functionAccess.functionName}</td>
                                                <td align="center" width="14%">
                                                    <c:if test="${functionAccess.view == true}">
                                                        <input type="checkbox" checked="checked" class="accessCheckBox"
                                                            id="module_${moduleIndex.count}_function_${functionIndex.count}_view"
                                                            onclick="javascript: childCheck(this, ${moduleIndex.count}, 'view');"/>
                                                    </c:if>
                                                    <c:if test="${functionAccess.view == false}">
                                                        <input type="checkbox" class="noAccessCheckBox" 
                                                            id="module_${moduleIndex.count}_function_${functionIndex.count}_view"
                                                            onclick="javascript: childCheck(this, ${moduleIndex.count}, 'view');" />
                                                    </c:if>
                                                </td>
                                                <td align="center" width="14%">
                                                    <c:if test="${functionAccess.create == true}">
                                                        <input type="checkbox" checked="checked"
                                                            id="module_${moduleIndex.count}_function_${functionIndex.count}_create"
                                                            onclick="javascript: childCheck(this, ${moduleIndex.count}, 'create');"/>
                                                    </c:if>
                                                    <c:if test="${functionAccess.create == false}">
                                                        <input type="checkbox"
                                                            id="module_${moduleIndex.count}_function_${functionIndex.count}_create"
                                                            onclick="javascript: childCheck(this, ${moduleIndex.count}, 'create');"/>
                                                    </c:if>
                                                </td>
                                                <td align="center" width="14%">
                                                    <c:if test="${functionAccess.update == true}">
                                                        <input type="checkbox" checked="checked"
                                                            id="module_${moduleIndex.count}_function_${functionIndex.count}_update"
                                                            onclick="javascript: childCheck(this, ${moduleIndex.count}, 'update');"/>
                                                    </c:if>
                                                    <c:if test="${functionAccess.update == false}">
                                                        <input type="checkbox"
                                                            id="module_${moduleIndex.count}_function_${functionIndex.count}_update"
                                                            onclick="javascript: childCheck(this, ${moduleIndex.count}, 'update');"/>
                                                    </c:if>
                                                </td>
                                                <td align="center" width="14%">
                                                    <c:if test="${functionAccess.delete == true}">
                                                        <input type="checkbox" checked="checked"
                                                            id="module_${moduleIndex.count}_function_${functionIndex.count}_delete"
                                                            onclick="javascript: childCheck(this, ${moduleIndex.count}, 'delete');"/>
                                                    </c:if>
                                                    <c:if test="${functionAccess.delete == false}">
                                                        <input type="checkbox"
                                                            id="module_${moduleIndex.count}_function_${functionIndex.count}_delete"
                                                            onclick="javascript: childCheck(this, ${moduleIndex.count}, 'delete');"/>
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </td>
                         </tr>
                        </c:forEach>
                </tbody>
                <tfoot/>
            </table>
        </td>
    </tr>
    </c:if>
    <c:if test="${privileges != null}">
    <tr>
        <td width="50%" align="right">
            <input type="button" name="save" value="Save" class="formButton" onclick="submitForm()"/>
        </td>
        <td width="50%" align="left">
            <input type="button" name="restore" value="Restore Default Privileges" class="formButton" onclick="restorePrivileges()"/>
        </td>
    </tr>
    </c:if>
</table>
<p/>