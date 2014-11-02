<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<script type="text/javascript" charset="utf-8">
$(document).ready(function () {
  $('.chosen-select').chosen({width: "60%"});
  $('#UserTypeID').change(function(value) {
    //alert($("#UserTypeID").val());
    submitPage('<%=request.getContextPath()%>/privileges/defaultPrivileges.htm?userTypeId='+$("#UserTypeID").val(), '');
  });
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

function submitForm() {
  var userTypeId = $('#UserTypeID').val();
  var numberOfModules = parseInt(document.getElementById('numberOfModules').value);
  var PrivilegesData = new Object();
  var Modules = new Array();

  PrivilegesData.UserTypeId=$('#UserTypeID').val();
  PrivilegesData.Modules=Modules;

  for (var moduleIndex=1; moduleIndex<numberOfModules+1; moduleIndex++) {
    var Module = new Object();
    var Functions = new Array();

    Module.ModuleId=1;
    Module.CanView=true;
    Module.CanCreate=true;
    Module.CanUpdate=true;
    Module.CanDelete=true;
    Module.Functions=Functions;

    var functionsTable = document.getElementById('functions_table_' + moduleIndex);
    var numberOfFunctions = functionsTable.rows.length;
    for (var functionIndex=1; functionIndex<numberOfFunctions+1; functionIndex++) {
      var Function = new Object();
      Function.FunctionId=document.getElementById('module_' + moduleIndex + '_function_' + functionIndex).value;
      Function.CanView=document.getElementById('module_' + moduleIndex + '_function_' + functionIndex + '_view').checked;
      Function.CanCreate=document.getElementById('module_' + moduleIndex + '_function_' + functionIndex + '_create').checked;
      Function.CanUpdate=document.getElementById('module_' + moduleIndex + '_function_' + functionIndex + '_update').checked;
      Function.CanDelete=document.getElementById('module_' + moduleIndex + '_function_' + functionIndex + '_delete').checked;
      Functions[Functions.length]=Function;
    }
    Modules[Modules.length]=Module;
  }
  alert(JSON.stringify(PrivilegesData));

  /*
  var privilegesData = '{"userTypeId":"'+ userTypeId + '", "modules":{ \n' ;
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
    
    sendData(userTypeId, privilegesData);*/
}

function sendData(userTypeId, privilegesData) {
  $.ajax({
    type: "POST",
    url: '<%=request.getContextPath()%>/privileges/jsonDefaultSave.htm',
    data: {
      userTypeId: userTypeId,
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
    childCheckBox = masterTable.rows[rowIndex].cells[columnIndex].childNodes[1];
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

</script>

<table cellpadding="5" cellspacing="0" width="60%" class="formTable">
  <caption class="dataTableCaption">Default Privileges</caption>
  <tr>
    <td class="formLabel">User Type</td>
    <td align="left">
    <c:if test="${userTypes != null}">
      <select id="UserTypeID" class="chosen-select">
      <c:forEach var="UserType" items="${userTypes}">
      <c:if test="${UserType.userTypeId == userTypeId}">
        <option value="${UserType.userTypeId}" selected>${UserType.description}</option> 
      </c:if>
      <c:if test="${UserType.userTypeId != userTypeId}">
        <option value="${UserType.userTypeId}">${UserType.description}</option> 
      </c:if>
      </c:forEach> 
      </select> 
    </c:if>
    </td>
  </tr>
  <tr>
    <td colspan="2" valign="top">&nbsp;
    <c:if test="${privileges != null}">
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
                  <td width="30px">
                    <img id="image_${moduleIndex.count}" src="<%=request.getContextPath()%>/images/icons/triangle_down.png" class="iconImage" onclick="show(${moduleIndex.count})" alt="Expand"/>
                  </td>
                  <td width="*">${moduleAccess.moduleName}</td>
                  <td align="center" width="14%">
                    <c:if test="${moduleAccess.allView == true}">
                    <input id="master_view_${moduleIndex.count}" type="checkbox" onclick="javascript: masterCheck(${moduleIndex.count}, 'view');" checked/>
                    </c:if>
                    <c:if test="${moduleAccess.allView == false}">
                    <input id="master_view_${moduleIndex.count}" type="checkbox" onclick="javascript: masterCheck(${moduleIndex.count}, 'view');" />
                    </c:if>
                  </td>
                  <td align="center" width="14%">
                    <c:if test="${moduleAccess.allCreate == true}">
                    <input id="master_create_${moduleIndex.count}" type="checkbox" onclick="javascript: masterCheck(${moduleIndex.count}, 'create');" checked/>
                    </c:if>
                    <c:if test="${moduleAccess.allCreate == false}">
                    <input id="master_create_${moduleIndex.count}" type="checkbox" onclick="javascript: masterCheck(${moduleIndex.count}, 'create');" />
                    </c:if>
                  </td>
                  <td align="center" width="14%">
                    <c:if test="${moduleAccess.allUpdate == true}">
                    <input id="master_update_${moduleIndex.count}" type="checkbox" onclick="javascript: masterCheck(${moduleIndex.count}, 'update');" checked/>
                    </c:if>
                    <c:if test="${moduleAccess.allUpdate == false}">
                    <input id="master_update_${moduleIndex.count}" type="checkbox" onclick="javascript: masterCheck(${moduleIndex.count}, 'update');" />
                    </c:if>
                  </td>
                  <td align="center" width="14%">
                    <c:if test="${moduleAccess.allDelete == true}">
                    <input id="master_delete_${moduleIndex.count}" type="checkbox" onclick="javascript: masterCheck(${moduleIndex.count}, 'delete');" checked/>
                    </c:if>
                    <c:if test="${moduleAccess.allDelete == false}">
                    <input id="master_delete_${moduleIndex.count}" type="checkbox" onclick="javascript: masterCheck(${moduleIndex.count}, 'delete');" />
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
                      <input type="checkbox" checked="checked" class="accessCheckBox" id="module_${moduleIndex.count}_function_${functionIndex.count}_view" onclick="javascript: childCheck(this, ${moduleIndex.count}, 'view');"/>
                    </c:if>
                    <c:if test="${functionAccess.view == false}">
                      <input type="checkbox" class="noAccessCheckBox" id="module_${moduleIndex.count}_function_${functionIndex.count}_view" onclick="javascript: childCheck(this, ${moduleIndex.count}, 'view');" />
                    </c:if>
                    </td>
                    <td align="center" width="14%">
                      <c:if test="${functionAccess.create == true}">
                      <input type="checkbox" checked="checked" id="module_${moduleIndex.count}_function_${functionIndex.count}_create" onclick="javascript: childCheck(this, ${moduleIndex.count}, 'create');"/>
                      </c:if>
                      <c:if test="${functionAccess.create == false}">
                      <input type="checkbox" id="module_${moduleIndex.count}_function_${functionIndex.count}_create" onclick="javascript: childCheck(this, ${moduleIndex.count}, 'create');"/>
                      </c:if>
                    </td>
                    <td align="center" width="14%">
                      <c:if test="${functionAccess.update == true}">
                      <input type="checkbox" checked="checked" id="module_${moduleIndex.count}_function_${functionIndex.count}_update" onclick="javascript: childCheck(this, ${moduleIndex.count}, 'update');"/>
                      </c:if>
                      <c:if test="${functionAccess.update == false}">
                      <input type="checkbox" id="module_${moduleIndex.count}_function_${functionIndex.count}_update" onclick="javascript: childCheck(this, ${moduleIndex.count}, 'update');"/>
                      </c:if>
                    </td>
                    <td align="center" width="14%">
                      <c:if test="${functionAccess.delete == true}">
                      <input type="checkbox" checked="checked" id="module_${moduleIndex.count}_function_${functionIndex.count}_delete" onclick="javascript: childCheck(this, ${moduleIndex.count}, 'delete');"/>
                      </c:if>
                      <c:if test="${functionAccess.delete == false}">
                        <input type="checkbox" id="module_${moduleIndex.count}_function_${functionIndex.count}_delete" onclick="javascript: childCheck(this, ${moduleIndex.count}, 'delete');"/>
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
      </c:if>
    </td>
  </tr>
  <tr>
    <td colspan="3" align="right">
      <input type="button" name="save" value="Save" class="formButton" onclick="submitForm()"/>
    </td>
  </tr>
</table>
