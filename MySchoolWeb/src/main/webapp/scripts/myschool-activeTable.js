/*
jQuery.activeTable plugin 
modified by Srikanth Kumar, 2013.
*/

var modelDialog = null;
var currentDataTable = null;

(function($, window, document) {
  $.fn.activeTable = function(settings) {
    var title = null;
    var columns = null;
    var buttons = null;
    var oTable = null;
    var calendar = null;
    var linkedTableExists = false;
    var buttonIconsPath = '../images/icons/';

    var defaultSettings = {
      //title: 'Record',
      containerName: 'ActiveTable',
      width: "100%",
      border: 0,
      caption: null,
      captionClass: 'dataTableCaption',
      icon: null,
      columns: [],
      dataTableSettings: {},
      footerIconsPosition: 'right',
      buttons: [],
      buttonClass: 'iconImage',
      'add' : {
        model: true,
        title: 'Add',
        url: '',
        width: 400,
        height: 200,
        sendParams: []
      },
      'update': {
        title: 'Update',
        url: '',
        width: 400,
        height: 200,
        selectRowMessage: 'Select a row to update.',
        updatable: true,
        noUpdateMessage: 'Update is not allowed',
        sendParams: []
      },
      'delete': {
        url: '',
        selectRowMessage: 'Select a row to delete.',
        confirmMessage: 'Do you really want to delete?',
        confirmCallback: deleteRow,
        deletable: true,
        noDeleteMessage: 'Delete is not allowed',
        sendParams: []
      },
      'map': {
        title: 'View Map',
        selectRowMessage: 'Select a row to view map.',
        mapUrlIndex: 0,
        width: 400,
        height: 200
      },
      'calendar': {
        calendarName: 'Calendar',
        calendarSettings: null
      },
      'linked': {
        containerName: 'ActiveTable2',
        width: "60%",
        caption: 'Table2 Caption',
        selectRowMessage: 'Select a row to view details.',
        columns: [],
        buttons: [],
        dataTableSettings: {},
        sendParams: []
      }
    };

    initActiveTable();

    function initActiveTable() {
      //title = (typeof settings.title == 'undefined') ? defaultSettings.title : settings.title;
      calendar = (typeof settings.calendar == 'undefined') ? defaultSettings.calendar : settings.calendar;
      var dataTableContent = getDataTableContent();
      var calendarContent = getCalendarContent();
      var containerName = (typeof settings.containerName == 'undefined') ? defaultSettings.containerName : settings.containerName;

      // TODO Append this to the selector
      $('#' + containerName).html(dataTableContent + calendarContent);
      if (dataTableContent != '') {
        setDataTable();
        activateIcons();
      }
      if (calendarContent != '') {
        setCalendar();
      }
    }

    function setDataTable() {
      var dataTableSettings = (typeof settings.dataTableSettings == 'undefined') ? defaultSettings.dataTableSettings : settings.dataTableSettings;
      var containerName = (typeof settings.containerName == 'undefined') ? defaultSettings.containerName : settings.containerName;
	  var iDisplayLength = (typeof recordsPerPage == 'undefined') ? 10 : recordsPerPage;
	  dataTableSettings.iDisplayLength=iDisplayLength;
      enableActionButtons(containerName, false);
      oTable = $('#' + containerName + '_Inner').dataTable(dataTableSettings);
      makeSelectableTable();
      enableActionButtons(containerName, true);
    }

    function setCalendar() {
      var calendarName = (typeof calendar.calendarName == 'undefined') ? defaultSettings.calendar.calendarName : calendar.calendarName;
      $('#' + calendarName).fullCalendar(calendar.calendarSettings);
      $('#' + calendarName + '_Outer').hide(0);
      activateTableButton();
    }

    function makeSelectableTable() {
      var containerName = (typeof settings.containerName == 'undefined') ? defaultSettings.containerName : settings.containerName;
      $('#' + containerName + '_Inner tbody').click(function(event) {
      currentDataTable = oTable;
      $(oTable.fnSettings().aoData).each(function () {
        $(this.nTr).removeClass('row_selected');
      });
      $(event.target.parentNode).addClass('row_selected');
        if (linkedTableExists) {
          // TODO do not create this element if the selected row is same as the previous one.
          $('#' + containerName + '_LinkedViewIcon').click();
        }
      });
    }

    function getDataTableContent() {
      var dataTableContent = '';
      if (settings != null) {
        var containerName = (typeof settings.containerName == 'undefined') ? defaultSettings.containerName : settings.containerName;
        var width = (typeof settings.width == 'undefined') ? defaultSettings.width : settings.width;
        var border = (typeof settings.border == 'undefined') ? defaultSettings.border : settings.border;
        var caption = (typeof settings.caption == 'undefined') ? defaultSettings.caption : settings.caption;
        var captionClass = (typeof settings.captionClass == 'undefined') ? defaultSettings.captionClass : settings.captionClass;

        columns = defaultSettings.columns;
        if ((typeof settings.columns != 'undefined') && (settings.columns.length > 0)) {
          columns = settings.columns;
        }
        buttons = defaultSettings.buttons;
        if ((typeof settings.buttons != 'undefined') && (settings.buttons.length > 0)) {
          buttons = settings.buttons;
        }

        if (columns.length != 0) {
          dataTableContent = '<table cellpadding="0" id="' + containerName + '_Outer" cellspacing="0" border="' + border + '" width="' + width + '">';
          if (caption != null) {
            dataTableContent += '<caption class="' + captionClass + '">' + caption + '</caption>';
          }
          dataTableContent += '<tr>';
          dataTableContent += '<td>';
          dataTableContent += getInnerTableContent();
          dataTableContent += '</td>';
          dataTableContent += '</tr>';
          dataTableContent += '</table>';
        } else {
          dataTableContent = '';
        }
      }
      return dataTableContent;
    }

    function getInnerTableContent() {
      var containerName = (typeof settings.containerName == 'undefined') ? defaultSettings.containerName : settings.containerName;
      var innerTableContent = '<table width="100%" cellpadding="0" cellspacing="0" border="0" class="display" id="' + containerName + '_Inner">';

      innerTableContent += getInnerTableHeadContent();
      innerTableContent += '<tbody></tbody>';
      innerTableContent += getInnerTableFootContent();
      innerTableContent += '</table>';
      return innerTableContent;
    }

    function getInnerTableHeadContent() {
      var innerTableHeadContent = '<thead>';
      innerTableHeadContent += '<tr>';
      innerTableHeadContent += getColumnHeadings();
      innerTableHeadContent += '</tr>';
      innerTableHeadContent += '</thead>';
      return innerTableHeadContent;
    }

    function getInnerTableFootContent() {
      var footerIconsPosition = (typeof settings.footerIconsPosition == 'undefined') ? defaultSettings.footerIconsPosition : settings.footerIconsPosition;
      var innerTableFootContent = '<tfoot>';
      innerTableFootContent += '<tr>';
      innerTableFootContent += '<th colspan="' + columns.length + '" align="' + footerIconsPosition + '">';
      innerTableFootContent += getActionButtons();
      innerTableFootContent += '</th>';
      innerTableFootContent += '</tr>';
      innerTableFootContent += '</tfoot>';
      return innerTableFootContent;
    }

    function getColumnHeadings() {
      var columnData = '';
      for (var colIndex=0; colIndex<columns.length; colIndex++) {
        columnData += '<th>' + columns[colIndex] + '</th>';
      }
      return columnData;
    }

    function getButtonContent(button, buttonClass) {
      var buttonContent = '';
      var containerName = (typeof settings.containerName == 'undefined') ? defaultSettings.containerName : settings.containerName;
      if (button == 'add') {
        buttonContent = '<img id="' + containerName + '_AddIcon" src="' + buttonIconsPath + 'add.png" class="' + buttonClass + '" />';
      } else if (button == 'update') {
        buttonContent = '<img id="' + containerName + '_UpdateIcon" src="' + buttonIconsPath + 'update.png" class="' + buttonClass + '" />';
      } else if (button == 'delete') {
        buttonContent = '<img id="' + containerName + '_DeleteIcon" src="' + buttonIconsPath + 'delete.png" class="' + buttonClass + '" />';
      } else if (button == 'export') {
        buttonContent = '<img id="' + containerName + '_ExportIcon" src="' + buttonIconsPath + 'msexcel.png" class="' + buttonClass + '" />';
      } else if (button == 'report') {
        buttonContent = '<img id="' + containerName + '_ReportIcon" src="' + buttonIconsPath + 'report.png" class="' + buttonClass + '" />';
      } else if (button == 'print') {
        buttonContent = '<img id="' + containerName + '_PrintIcon" src="' + buttonIconsPath + 'print.png" class="' + buttonClass + '" />';
      } else if (button == 'table') {
        buttonContent = '<img id="' + containerName + '_TableViewIcon" src="' + buttonIconsPath + 'table.png" class="' + buttonClass + '" />';
      } else if (button == 'calendar') {
        buttonContent = '<img id="' + containerName + '_CalendarViewIcon" src="' + buttonIconsPath + 'calendar.png" class="' + buttonClass + '" />';
      } else if (button == 'map') {
        buttonContent = '<img id="' + containerName + '_MapViewIcon" src="' + buttonIconsPath + 'world.png" class="' + buttonClass + '" />';
      } else if (button == 'linked') {
        buttonContent = '<img id="' + containerName + '_LinkedViewIcon" src="' + buttonIconsPath + 'down.png" class="' + buttonClass + '" />';
        linkedTableExists = true;
      } else if (button == 'notify') {
        buttonContent = '<img id="' + containerName + '_NotifyIcon" src="' + buttonIconsPath + 'notify.png" class="' + buttonClass + '" />';
      }
      return buttonContent;
    }

    function getCalendarContent() {
      var calendarSettings = calendar.calendarSettings;

      if (calendarSettings != null)  {
        var containerName = (typeof settings.containerName == 'undefined') ? defaultSettings.containerName : settings.containerName;
        var calendarName = (typeof calendar.calendarName == 'undefined') ? defaultSettings.calendar.calendarName : calendar.calendarName;
        var width = (typeof settings.width == 'undefined') ? defaultSettings.width : settings.width;
        var caption = (typeof settings.caption == 'undefined') ? defaultSettings.caption : settings.caption;
        var captionClass = (typeof settings.captionClass == 'undefined') ? defaultSettings.captionClass : settings.captionClass;
        var buttonClass = (typeof settings.buttonClass == 'undefined') ? defaultSettings.buttonClass : settings.buttonClass;
        var footerIconsPosition = (typeof settings.footerIconsPosition == 'undefined') ? defaultSettings.footerIconsPosition : settings.footerIconsPosition;

        var calendarContent = '<table cellpadding="0" id="' + calendarName + '_Outer" cellspacing="0" border="0" width="' + width + '">';
            calendarContent += '<caption class="' + captionClass + '">' + caption + '</caption>';
            calendarContent += '<tbody><tr>';
            calendarContent += '<td><div id="' + calendarName + '"></div></td>';
            calendarContent += '</tr></tbody>';
            calendarContent += '<tfoot>';
            calendarContent += '<tr>';
            calendarContent += '<th align="' + footerIconsPosition + '">';
            calendarContent += '<img id="' + containerName + '_TableViewIcon" src="' + buttonIconsPath + 'table.png" class="' + buttonClass + '" alt="Table View" />';
            calendarContent += '</th>';
            calendarContent += '</tr>';
            calendarContent += '</tfoot>';
            calendarContent += '</table>';
        return calendarContent;
      }
      return '';
    }

    function getActionButtons() {
      var buttonClass = (typeof settings.buttonClass == 'undefined') ? defaultSettings.buttonClass : settings.buttonClass;

      var buttonsContent = '';
      if (buttons.length > 0) {
        for (var buttonIndex=0; buttonIndex<buttons.length; buttonIndex++) {
          var button = buttons[buttonIndex];
          buttonsContent += getButtonContent(button, buttonClass);
        }
      }
      return buttonsContent;
    }

    function activateIcons() {
      if (buttons.length > 0) {
        for (var buttonIndex=0; buttonIndex<buttons.length; buttonIndex++) {
          var button = buttons[buttonIndex];
          activateIcon(button);
        }
      }
    }

    function activateIcon(button) {
      if (button == 'add') {
        activateAddButton();
      } else if (button == 'update') {
        activateUpdateButton();
      } else if (button == 'delete') {
        activateDeleteButton();
      } else if (button == 'export') {
        activateExportButton();
      } else if (button == 'report') {
        activateReportButton();
      } else if (button == 'print') {
        activatePrintButton();
      } else if (button == 'table') {
        activateTableButton();
      } else if (button == 'calendar') {
        activateCalendarButton();
      } else if (button == 'map') {
        activateMapButton();
      } else if (button == 'linked') {
        activateLinkedButton();
      } else if (button == 'notify') {
        activateNotifyButton();
      }
    }

    function activateAddButton() {
      var containerName = (typeof settings.containerName == 'undefined') ? defaultSettings.containerName : settings.containerName;
      var model = getButtonAttribute('add', 'model');
      var addTitle = getButtonAttribute('add', 'title');

      var addButton = $('#' + containerName + '_AddIcon');
      addButton.prop('title', addTitle);
      addButton.tooltipster();
      addButton.click( function() {
        if (model) {
          currentDataTable = oTable;
          //var title = getButtonAttribute('add', 'title');
          var url = getButtonAttribute('add', 'url');
          var width = getButtonAttribute('add', 'width');
          var height = getButtonAttribute('add', 'height');
          var sendParams = getButtonAttribute('add', 'sendParams');
          modelDialog = openDialog(getUrl(url, sendParams), addTitle, width, height);
        } else {
          var url = getButtonAttribute('add', 'url');
          submitPage(url, '');
        }
      });
    }

    function activateUpdateButton() {
      var containerName = (typeof settings.containerName == 'undefined') ? defaultSettings.containerName : settings.containerName;
      var updateButton = $('#' + containerName + '_UpdateIcon');
      var updateTitle = getButtonAttribute('update', 'title');
      updateButton.prop('title', updateTitle);
      updateButton.tooltipster();
      updateButton.click( function() {
      currentDataTable = oTable;
      var updatable = getButtonAttribute('update', 'updatable');
        if (updatable) {
          //var title = getButtonAttribute('update', 'title');
          var url = getButtonAttribute('update', 'url');
          var width = getButtonAttribute('update', 'width');
          var height = getButtonAttribute('update', 'height');
          var selectRowMessage = getButtonAttribute('update', 'selectRowMessage');
          var sendParams = getButtonAttribute('update', 'sendParams');
          var anSelected = fnGetSelected(oTable);
          if (anSelected == null) {
            info_ac(selectRowMessage);
          } else {
            modelDialog = openDialog(getUrl(url, sendParams), updateTitle, width, height);
          }
        } else {
          var noUpdateMessage = getButtonAttribute('update', 'noUpdateMessage');
          info_ac(noUpdateMessage);
        }
      });
    }

    function activateDeleteButton() {
      var containerName = (typeof settings.containerName == 'undefined') ? defaultSettings.containerName : settings.containerName;

      var deleteButton = $('#' + containerName + '_DeleteIcon');
      var deleteTitle = getButtonAttribute('delete', 'title');
      deleteButton.prop('title', deleteTitle);
      deleteButton.tooltipster();
      deleteButton.click( function() {
        currentDataTable = oTable;
        var deletable = getButtonAttribute('delete', 'deletable');
        if (deletable) {
          var url = getButtonAttribute('delete', 'url');
          var selectRowMessage = getButtonAttribute('delete', 'selectRowMessage');
          var confirmMessage = getButtonAttribute('delete', 'confirmMessage');
          var confirmCallback = getButtonAttribute('delete', 'confirmCallback');
          var anSelected = fnGetSelected( oTable );
          if (anSelected == null) {
            info_ac(selectRowMessage);
          } else {
            confirm(confirmMessage, confirmCallback);
          }
        } else {
          var noDeleteMessage = getButtonAttribute('delete', 'noDeleteMessage');
          info_ac(noDeleteMessage);
        }
      });
    }

    function deleteRow(result) {
      if (result == "Yes") {
        var url = getButtonAttribute('delete', 'url');
        var sendParams = getButtonAttribute('delete', 'sendParams');
        var anSelected = fnGetSelected( oTable );
        $.ajax({
          url: getUrl(url, sendParams),
          context: document.body,
          success: function(result){
            $(this).addClass("done");
            oTable.fnDeleteRow(anSelected);
            parseWholepageResponse(result, false);
          }
        });
      }
    }

    function activateExportButton() {
      var containerName = (typeof settings.containerName == 'undefined') ? defaultSettings.containerName : settings.containerName;
      var exportButton = $('#' + containerName + '_ExportIcon');
      exportButton.prop('title', 'Export Data');
      exportButton.tooltipster();
    }

    function activateMapButton() {
      currentDataTable = oTable;
      var containerName = (typeof settings.containerName == 'undefined') ? defaultSettings.containerName : settings.containerName;
      var mapButton = $('#' + containerName + '_MapViewIcon');
      mapButton.prop('title', 'View In Map');
      mapButton.tooltipster();
      mapButton.click( function() {
        var title = getButtonAttribute('map', 'title');
        var selectRowMessage = getButtonAttribute('map', 'selectRowMessage');
        var mapUrlIndex = getButtonAttribute('map', 'mapUrlIndex');
        var width = getButtonAttribute('map', 'width');
        var height = getButtonAttribute('map', 'height');
        var anSelected = fnGetSelected(oTable);
        if (anSelected == null) {
          info_ac(selectRowMessage);
        } else {
          var mapUrl = oTable.fnGetData(anSelected)[parseInt(mapUrlIndex)];
          if (mapUrl == null || mapUrl == '') {
            info_ac('Map is currently not available for the selected ' + title);
          } else {
            modelDialog = openDialog(mapUrl, title, width, height);
          }
        }
      });
    }

    function activateLinkedButton() {
      var containerName = (typeof settings.containerName == 'undefined') ? defaultSettings.containerName : settings.containerName;
      var linkButton = $('#' + containerName + '_LinkedViewIcon');
      linkButton.prop('title', 'Drill down');
      linkButton.tooltipster();
      linkButton.click( function() {
        var selectRowMessage = getButtonAttribute('linked', 'selectRowMessage');
        var linkedContainerName = getButtonAttribute('linked', 'containerName');
        var anSelected = fnGetSelected(oTable);
        if (anSelected == null) {
          info_ac(selectRowMessage);
        } else {
          // Create the nested datatable.
          var dataTableSettings = getButtonAttribute('linked', 'dataTableSettings');
          var sendParams = getButtonAttribute('linked', 'sendParams');
          var originalAjaxSource = dataTableSettings.sAjaxSource;
          var modifiedAjaxSource = null;
          if (originalAjaxSource != '') {
            modifiedAjaxSource = getUrl(originalAjaxSource, sendParams);
          }
          dataTableSettings.sAjaxSource = modifiedAjaxSource;
          $('<div id=' + linkedContainerName + '>').remove();
          var linkedContainer = $('<div id=' + linkedContainerName + '>');
          $('#' + containerName).append(linkedContainer);
          linkedContainer.activeTable(settings.linked);
          dataTableSettings.sAjaxSource = originalAjaxSource;
          // scroll to the content
          $('html, body').scrollTo('#' + linkedContainerName, {duration: 2000, offsetTop : '50'});
        }
      });
    }

    function activateTableButton() {
      var containerName = (typeof settings.containerName == 'undefined') ? defaultSettings.containerName : settings.containerName;
      var calendarName = (typeof calendar.calendarName == 'undefined') ? defaultSettings.calendar.calendarName : calendar.calendarName;
      var tableButton = $('#' + containerName + '_TableViewIcon');
      tableButton.prop('title', 'Grid View');
      tableButton.tooltipster();
      tableButton.click( function() {
        currentDataTable = oTable;
        $('#' + calendarName + '_Outer').hide(0);
        $('#' + containerName + '_Outer').show(1000);
        enableActionButtons(containerName, true);
      });
    }

    function activateCalendarButton() {
      var containerName = (typeof settings.containerName == 'undefined') ? defaultSettings.containerName : settings.containerName;
      var calendarName = (typeof calendar.calendarName == 'undefined') ? defaultSettings.calendar.calendarName : calendar.calendarName;
      var calendarButton = $('#' + containerName + '_CalendarViewIcon');
      calendarButton.prop('title', 'Calendar View');
      calendarButton.tooltipster();
      calendarButton.click( function() {
        currentDataTable = oTable;
        $('#' + containerName + '_Outer').hide(0);
        $('#' + calendarName + '_Outer').show(1000);
        enableActionButtons(containerName, false);
      });
    }

    function activateReportButton() {
      var containerName = (typeof settings.containerName == 'undefined') ? defaultSettings.containerName : settings.containerName;
      var reportButton = $('#' + containerName + '_ReportIcon');
      reportButton.prop('title', 'View Report');
      reportButton.tooltipster();
      reportButton.click( function() {
        currentDataTable = oTable;
        var callback = getButtonAttribute('report', 'callback');
        if (callback == null || typeof callback == 'undefined') {
            var title = getButtonAttribute('report', 'title');
            var url = getButtonAttribute('report', 'url');
            var width = getButtonAttribute('report', 'width');
            var height = getButtonAttribute('report', 'height');
            var selectRowMessage = getButtonAttribute('report', 'selectRowMessage');
            var sendParams = getButtonAttribute('report', 'sendParams');
            var anSelected = fnGetSelected(oTable);
            if (anSelected == null) {
              info_ac(selectRowMessage);
            } else {
              //modelDialog = openReportDialog(getUrl(url, sendParams), title, width, height);
              modelDialog = openDialog(getUrl(url, sendParams), title, width, height);
            }
        } else {
            callback.call();
        }
      });
    }

    function activatePrintButton() {
      var containerName = (typeof settings.containerName == 'undefined') ? defaultSettings.containerName : settings.containerName;
      var printButton = $('#' + containerName + '_PrintIcon');
      printButton.prop('title', 'Print Report');
      printButton.tooltipster();
      printButton.click( function() {
        currentDataTable = oTable;
        var title = getButtonAttribute('report', 'title');
        var url = getButtonAttribute('report', 'url');
        var width = getButtonAttribute('report', 'width');
        var height = getButtonAttribute('report', 'height');
        var selectRowMessage = getButtonAttribute('report', 'selectRowMessage');
        var sendParams = getButtonAttribute('report', 'sendParams');
        var anSelected = fnGetSelected(oTable);
        if (anSelected == null) {
          info_ac(selectRowMessage);
        } else {
          modelDialog = openDialog(getUrl(url, sendParams), title, width, height);
        }
      });
    }

    function activateNotifyButton() {
      var containerName = (typeof settings.containerName == 'undefined') ? defaultSettings.containerName : settings.containerName;
      var notifyButton = $('#' + containerName + '_NotifyIcon');
      notifyButton.prop('title', 'Notify');
      notifyButton.tooltipster();
      notifyButton.click( function() {
        currentDataTable = oTable;
        var url = getButtonAttribute('notify', 'url');
        var selectRowMessage = getButtonAttribute('notify', 'selectRowMessage');
        var sendParams = getButtonAttribute('notify', 'sendParams');
        //var callback = getButtonAttribute('notify', 'callback');
        // callback();
        var anSelected = fnGetSelected(oTable);
        if (anSelected == null) {
          info_ac(selectRowMessage);
        } else {
          $.ajax({
            url: getUrl(url, sendParams),
            context: document.body,
            success: function(result) {
              parseWholepageResponse(result, true);
            }
          });
        }
      });
    }

    function getButtonAttribute(button, attribute) {
      var attributeValue = null;
      if (typeof settings[''+button] == 'undefined') {
        attributeValue = defaultSettings[''+button][''+attribute];
      } else {
        if (typeof settings[''+button][''+attribute] == 'undefined') {
          attributeValue = defaultSettings[''+button][''+attribute];
        } else {
          attributeValue = settings[''+button][''+attribute];
        }
      }
      return attributeValue;
    }

    function enableActionButtons(containerName, enable) {
      if (enable) {
        $('#' + containerName + '_AddIcon').show(1000);
        $('#' + containerName + '_UpdateIcon').show(1000);
        $('#' + containerName + '_DeleteIcon').show(1000);
        $('#' + containerName + '_ExportIcon').show(1000);
        //$('#' + containerName + '_TableViewIcon').show(1000);
        $('#' + containerName + '_CalendarViewIcon').show(1000);
        $('#' + containerName + '_MapViewIcon').show(1000);
        //$('#' + containerName + '_LinkedViewIcon').show(1000);
      } else {
        //$('#' + containerName + '_AddIcon').hide(1000);
        $('#' + containerName + '_UpdateIcon').hide(1000);
        $('#' + containerName + '_DeleteIcon').hide(1000);
        $('#' + containerName + '_ExportIcon').hide(1000);
        //$('#' + containerName + '_TableViewIcon').hide(1000);
        $('#' + containerName + '_CalendarViewIcon').hide(1000);
        $('#' + containerName + '_MapViewIcon').hide(1000);
        $('#' + containerName + '_LinkedViewIcon').hide(1000);
      }
    }

    /* Get the rows which are currently selected */
    function fnGetSelected( oTableLocal ) {
      var aReturn = new Array();
      var selected = null;
      var aTrs = oTableLocal.fnGetNodes();
      for ( var i=0 ; i<aTrs.length ; i++ ) {
        if ( $(aTrs[i]).hasClass('row_selected') ) {
          aReturn.push( aTrs[i] );
          selected = aTrs[i];
        }
      }
      return selected;
    }

    function getUrl(url, sendParams) {
      var modifiedUrl = null;
      var paramString = '';
      if (typeof sendParams == 'undefined' || sendParams.length == 0) {
        modifiedUrl = url;
      } else {
        var noOfParams = sendParams.length;
        for (var paramIndex=0; paramIndex<noOfParams; paramIndex++) {
          var sendParam = sendParams[paramIndex];
          var refTable = sendParam.refTable;
          var paramName = sendParam.paramName;
          var paramValue = '';
          if (refTable == 'none') {
            if (typeof sendParam.paramValue != 'undefined') {
              paramValue = sendParam.paramValue;
            } else if (typeof sendParam.paramValueFunction != 'undefined') {
              var paramValueFunction = sendParam.paramValueFunction;
              paramValue = paramValueFunction();
            }
          } else if (refTable == 'self') {
            var anSelected = fnGetSelected(oTable);
            var selectedRow = oTable.fnGetData(anSelected);
            if (parseInt(sendParam.columnIndex) == -1) {
              var columnNames = sendParam.columnNames;
              if (typeof(columnNames) == 'undefined' || columnNames.length != selectedRow.length) {
                throw('Length of "columnNames" does not match with the cells in selected row.');
              }
              var object = new Object();
              for (var index=0; index<selectedRow.length; index++) {
                object[columnNames[index]] = selectedRow[index];
              }
              paramValue = JSON.stringify(object);
            } else {
              paramValue = selectedRow[parseInt(sendParam.columnIndex)];
            }
          } else {
            var refDataTable = $('#'+refTable + '_Inner').dataTable();
            var anSelected = fnGetSelected(refDataTable);
            var selectedRow = refDataTable.fnGetData(anSelected);
            paramValue = selectedRow[parseInt(sendParam.columnIndex)];
          }
          if (paramIndex == 0) {
            paramString = paramName + '=' + paramValue;
          } else {
            paramString = paramString + '&' + paramName + '=' + paramValue;
          }
        }
      }
      if (url.indexOf('?') == -1) {
        // No parameters appended so far
        if (paramString == '') {
          modifiedUrl = url + '?sid=' + new Date().getTime();
        } else {
          modifiedUrl = url + '?' + paramString + '&sid=' + new Date().getTime();
        }
      } else {
        // Some parameters appended before
        if (paramString == '') {
          modifiedUrl = url + '&sid=' + new Date().getTime();
        } else {
          modifiedUrl = url + '&' + paramString + '&sid=' + new Date().getTime();
        }
      }
      return modifiedUrl;
    }
  }
})(jQuery, window, document);


function reloadData() {
  var oSettings = currentDataTable.fnSettings();
  oSettings.sAjaxSource = oSettings.sAjaxSource + '?sid=' + new Date().getTime();

  currentDataTable.fnClearTable(currentDataTable);
  var jsonFunction = $.getJSON(oSettings.sAjaxSource, function(respnose) {
    //enableActionButtons(false);
    if (respnose.aaData != '') {
      for (var i=0; i<respnose.aaData.length; i++) {
        currentDataTable.oApi._fnAddData(oSettings, respnose.aaData[i]);
      }
      currentDataTable.fnDraw(currentDataTable);
      currentDataTable.oApi._fnProcessingDisplay(oSettings, true);
      //enableActionButtons(respnose.aaData.length > 0);
    }
  });
}
