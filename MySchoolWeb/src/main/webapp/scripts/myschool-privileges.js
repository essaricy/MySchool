  var PRIVILEGE_TYPES = ["CanView", "CanCreate", "CanUpdate", "CanDelete"];
  function showPrivileges(url, data, images_src) {
    $.ajax({
      type: "POST",
      url: url,
      data: data,
      context: this
    }).done(function(result) {
      $('#PrivilegesMasterTable').show();
      populatePrivileges(result.Privileges, images_src.expand_src);
      $('.expand_collapse_toggle').click(function() {
        var name = $(this).attr('name');
        var id = $(this).attr('id');
        var target = null;
        if (id.indexOf('expand_collapse_module_') != -1) {
          id = id.substring('expand_collapse_module_'.length);
          target = 'module_functions_table_' + id;
        }

        var img_src = $(this).attr('src');
        if (img_src == images_src.expand_src){
          $('#module_functions_div_' + id).slideDown(1000);
          $(this).attr('src', images_src.collapse_src);
        } else if (img_src == images_src.collapse_src){
          $('#module_functions_div_' + id).slideUp(1000);
          $(this).attr('src', images_src.expand_src);
        }
      });
    });
  }

  function populatePrivileges(Privileges, expand_src) {
    $('#PrivilegesDataContainer').html('');
    if (Privileges != null && Privileges.Modules != null) {
      var Modules = Privileges.Modules;
      var PrivilegesDataContainer = $('#PrivilegesDataContainer');

      for (var moduleIndex=0; moduleIndex < Modules.length; moduleIndex++) {
        var Module = Modules[moduleIndex];
        createModuleTable(PrivilegesDataContainer, Module, expand_src);
      }
    }
  }

  function createModuleTable(PrivilegesDataContainer, Module, expand_src) {
    var id = Module.ModuleId;
    var module_table = $('<table class="module_table" cellpadding="3" cellspacing="0" width="100%" id="module_table_' + id + '" style="margin-top: 3px; margin-bottom: 3px; border: 1px solid #555;">');
    var module_functions_div = $('<div id="module_functions_div_' + id + '" style="display:none;" class="module_functions_div" >');
    var module_functions_table = $('<table cellpadding="0" cellspacing="0" border="0" width="100%" style="border:1px black solid;" >');
    var module_row = $('<tr style="background-color:#FFFFFF;">');
    module_table.append(module_row);
    module_functions_div.append(module_functions_table);

    addCheckboxes(module_row, 'module', Module, expand_src);

    if (Module.Functions != null) {
      var Functions = Module.Functions;
      for (var functionIndex=0; functionIndex < Functions.length; functionIndex++) {
        var Function = Functions[functionIndex];
        var tr = $('<tr>');
        module_functions_table.append(tr);
        addCheckboxes(tr, 'function', Function, expand_src);
      }
    }
    PrivilegesDataContainer.append(module_table);
    PrivilegesDataContainer.append(module_functions_div);
  }

  function addCheckboxes(tr, type, object, expand_src) {
    var id = null;
    var name = null;
    // Expand collapse toggler
    var td_expand_collapse = $('<td width="5%">');
    if (type == 'module') {
      id = object.ModuleId;
      name = object.ModuleName;
      td_expand_collapse.append('<input type="hidden" name="ModuleId" value="' + id + '" />');
      td_expand_collapse.append('<img src="' + expand_src + '" id="expand_collapse_module_' + id + '" class="iconImage expand_collapse_toggle" width="10%" />');
    } else {
      id = object.FunctionId;
      name = object.FunctionName;
      td_expand_collapse.append('<input type="hidden" name="FunctionId" value="' + id + '" />');
    }
    tr.append(td_expand_collapse);

    // Module/Function name
    var td_name = $('<td width="76%" align="left" style="font-size: 0.85em;">');
    td_name.append(name);
    tr.append(td_name);

    // Module level checkboxes
    for (var index=0; index < PRIVILEGE_TYPES.length; index++) {
      var td = $('<td>');
      var chkbox = $('<input type="checkbox" name="' + PRIVILEGE_TYPES[index] + '" />');
      if (object[PRIVILEGE_TYPES[index]]) {
        chkbox.attr('checked', 'checked');
      }
      chkbox.click(function() { checkCheckbox($(this), id, type); });

      td.append(chkbox);
      tr.append(td);
    }
  }

  function checkCheckbox(chkbox, id, type) {
    var name = $(chkbox).attr('name');
    var checked = $(chkbox).is(':checked');
    if (type == 'module') {
      // checkbox, td, tr, tbody, table, div
      var target = $(chkbox).parent().parent().parent().parent().next();
      target.find('input[type="checkbox"][name="' + name + '"]').each(function() {
        $(this).prop('checked', checked);
      });
    } else {
      var target = $(chkbox).parent().parent().parent().parent().parent().prev();
      if (checked) {
        // If at least one checkbox is not checked then uncheck the master check box
        var checkBoxesTable = $(chkbox).parent().parent().parent().parent();
        var everythingChecked = true;
        checkBoxesTable.find('input[type="checkbox"][name="' + name + '"]').each(function() {
          if (!$(this).is(':checked')) {
            everythingChecked = false;
          }
        });
        target.find('input[type="checkbox"][name="' + name + '"]').each(function() {
          $(this).prop('checked', everythingChecked);
        });
      } else {
        // Uncheck the master check box
        target.find('input[type="checkbox"][name="' + name + '"]').each(function() {
          if (!$(chkbox).is(':checked')) {
            $(this).prop('checked', checked);
          }
        });
      }
    }
  }

  function updatePrivileges(url) {
    var userTypeId = $('#UserTypeID').val();
    var PrivilegesData = new Object();
    var Modules = new Array();

    PrivilegesData.UserTypeID=$('#UserTypeID').val();
	PrivilegesData.UserID=$('#UserID').val();
    PrivilegesData.Modules=Modules;

    var module_tables = $('#PrivilegesDataContainer .module_table');
    for (var moduleIndex=0; moduleIndex<module_tables.length; moduleIndex++) {
      var module_table = module_tables[moduleIndex];
      var Module = new Object();
      var Functions = new Array();
      Modules[Modules.length]=Module;
      Module.Functions=Functions;
      Module.ModuleId=$(module_table).find('input[type="hidden"]').val();
      for (var prevIndex=0; prevIndex < PRIVILEGE_TYPES.length; prevIndex++) {
        var priv = PRIVILEGE_TYPES[prevIndex];
        Module[priv]=$(module_table).find('input[type="checkbox"][name="' + priv + '"]').is(':checked');
      }

      var module_functions_table = $(module_table).next().find('table');
      $(module_functions_table).find('tr').each(function(index, value) {
        var Function = new Object();
        Function.FunctionId=$(this).find('input[type="hidden"]').val();
        for (var prevIndex=0; prevIndex < PRIVILEGE_TYPES.length; prevIndex++) {
          var priv = PRIVILEGE_TYPES[prevIndex];
          Function[priv]=$(this).find('input[type="checkbox"][name="' + priv + '"]').is(':checked');
        }
        Functions[Functions.length]=Function;
      });
    }
    //console.log(PrivilegesData);
    //alert(JSON.stringify(PrivilegesData));
    $.ajax({
      type: "POST",
      url: url,
      data: {
        PrivilegesData: JSON.stringify(PrivilegesData),
        sid: new Date().getTime()
      },
      context: this
    }).done(function(result) {
    	handleServerResponseOnPage(result, false);
      //setTimeout(function(){ window.location.reload(); }, 3000);
    });
  }
