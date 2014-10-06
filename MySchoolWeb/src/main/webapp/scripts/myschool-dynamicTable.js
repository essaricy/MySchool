/*
jQuery.activeTable plugin 
modified by Srikanth Kumar, 2013.
*/

(function($, window, document) {
    $.fn.dynamicTable = function(settings) {
        var buttonIconsPath = '../images/icons/';
        var dynamicTableData = null;

        var defaultSettings = {
            containerName: 'DynamicTable',
            width: "100%",
            caption: 'Dynamic Table Caption',
            captionClass: 'dataTableCaption',
            width: "100%",
            border: 0,
            url: '',
            sendParams: [],
            headerColumns: []
        };
        
        initDynamicTable();

        function initDynamicTable() {
            var containerName = (typeof settings.containerName == 'undefined') ? defaultSettings.containerName : settings.containerName;

            // TODO get dynamic table data
            dynamicTableData = getDynamicTableData();
            alert(dynamicTableData);

            var dynamicTableContent = getDynamicTableContent();
            alert(dynamicTableContent);
        }

        function getDynamicTableData() {
            var dynamicTableData = null;
            var url = getUrl(settings.url, settings.sendParams);

            $.ajax({
                async: false,
                url: url,
                dataType: "json",
                success: function(data) {
                    dynamicTableData = data;
                }
            });
            return dynamicTableData;
        }

        function getDynamicTableContent() {
            var containerName = (typeof settings.containerName == 'undefined') ? defaultSettings.containerName : settings.containerName;
            var width = (typeof settings.width == 'undefined') ? defaultSettings.width : settings.width;
            var border = (typeof settings.border == 'undefined') ? defaultSettings.border : settings.border;
            var caption = (typeof settings.caption == 'undefined') ? defaultSettings.caption : settings.caption;
            var captionClass = (typeof settings.captionClass == 'undefined') ? defaultSettings.captionClass : settings.captionClass;

            var dynamicTableContent = '<table cellpadding="0" id="' + containerName + '_Outer" cellspacing="0" border="' + border + '" width="' + width + '">';
                dynamicTableContent += '<caption class="' + captionClass + '">' + caption + '</caption>';
                dynamicTableContent += '<tr>';
                dynamicTableContent += '<td>';
                dynamicTableContent += getInnerTableContent();
                dynamicTableContent += '</td>';
                dynamicTableContent += '</tr>';
                dynamicTableContent += '</table>';
            return dynamicTableContent;
        }

        function getInnerTableContent() {
            var containerName = (typeof settings.containerName == 'undefined') ? defaultSettings.containerName : settings.containerName;
            var innerTableContent = '<table width="100%" cellpadding="0" cellspacing="0" border="0" class="display" id="' + containerName + '_Inner">';

            innerTableContent += getInnerTableHeadContent();
            innerTableContent += '<tbody></tbody>';
            //innerTableContent += getInnerTableFootContent();
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

        function getColumnHeadings() {
            var columnData = '';
            var headerDataObjectName = settings.headerColumns.headerDataObjectName;
            var headerDataObjectName = settings.headerColumns.headerDataObjectName;

            var columns = dynamicTableData[headerDataObjectName];
            alert(columns);
            for (var colIndex=0; colIndex<columns.length; colIndex++) {
                columnData += '<th>' + columns[colIndex][columnNameDataObject] + '</th>';
            }
            alert(columnData);
            return columnData;
        }

        function getInnerTableFootContent() {
            var footerIconsPosition = (typeof settings.footerIconsPosition == 'undefined') ? defaultSettings.footerIconsPosition : settings.footerIconsPosition;

            var innerTableFootContent = '<tfoot>';
            innerTableFootContent += '<tr>';
            innerTableFootContent += '<th colspan="' + columns.length + '" align="' + footerIconsPosition + '">';
            innerTableFootContent += '&nbsp;'; //getActionButtons();
            innerTableFootContent += '</th>';
            innerTableFootContent += '</tr>';
            innerTableFootContent += '</tfoot>';
            return innerTableFootContent;
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

                    var refDataTable = $('#'+refTable + '_Inner').dataTable();
                    var anSelected = fnGetSelected(refDataTable);
                    var selectedRow = refDataTable.fnGetData(anSelected);
                    paramValue = selectedRow[parseInt(sendParam.columnIndex)];

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

        /* Get the rows which are currently selected */
        function fnGetSelected( oTableLocal ){
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
    }
})(jQuery, window, document);
