/*
jQuery.layoutMaker plugin 
modified by Srikanth Kumar, 2013.
*/

(function($, window, document) {
    $.fn.panelMaker = function(settings) {
		var panels = null;
        var defaultSettings = {
            containerName: 'LayoutContainer',
			width: '500px',
            panels: [ ]
        };
		var panelDefaultSettings = {
			id: 'PanelId',
			align: 'left',
			width: '250px',
			title: 'Panel Title',
			data: null,
			more: null
		};
        initPanels();

        function initPanels() {
            var containerName = (typeof settings.containerName == 'undefined') ? defaultSettings.containerName : settings.containerName;
			var width = (typeof settings.width == 'undefined') ? defaultSettings.width : settings.width;

			var panels = defaultSettings.panels;
            if ((typeof settings.panels != 'undefined') && (settings.panels.length > 0)) {
                panels = settings.panels;
            }

			$('#' + containerName).attr('width', width);
			if (panels.length > 0) {
				addPanels(panels);
			}
        }

        function addPanels(panels) {
			var containerName = (typeof settings.containerName == 'undefined') ? defaultSettings.containerName : settings.containerName;
			var panelsData = '';
            for (var panelIndex=0; panelIndex<panels.length; panelIndex++) {
				$('#' + containerName).append(getPanel(panels[panelIndex]));
            }
            return panelsData;
        }

		function getPanel(panel) {
			var panelId = (typeof panel.id == 'undefined') ? panelDefaultSettings.id : panel.id;
			var panelAlign = (typeof panel.align == 'undefined') ? panelDefaultSettings.align : panel.align;
			var panelWidth = (typeof panel.width == 'undefined') ? panelDefaultSettings.width : panel.width;
			var panelTitle = (typeof panel.title == 'undefined') ? panelDefaultSettings.title : panel.title;

			var panelData = (typeof panel.data == 'undefined') ? panelDefaultSettings.data : panel.data;
			var more = (typeof panel.more == 'undefined') ? panelDefaultSettings.more : panel.more;

			// Create inline item
			var panelContent = 'No data';
			var panelDiv = $('<div/>');
			panelDiv.attr('class', "item " + panelAlign);
			//panelDiv.attr('width', panelWidth);
			panelDiv.css('width', panelWidth);

			var heading = $('<span>');
			heading.attr('class', 'heading');
			heading.text(panelTitle);
			panelDiv.append(heading);

			if (panelData != null) {
				//var dataUrl= (typeof panelData.url == 'undefined') ? null : panelData.url;
				//var sendParams = (typeof panelData.sendParams == 'undefined') ? null : panel.sendParams;
				panelDiv.append(getContent(panelData));
			}
			if (more != null) {
				var moreText = more.text;
				var moreUrl = (typeof more.url == 'undefined') ? null : more.url;
				var moreSpan = $('<span>');
				moreSpan.attr('class', 'more');

				if (moreUrl == null) {
					moreSpan.text(moreText);
				} else {
					var moreAnchor = $('<a>');
					moreAnchor.attr('href', moreUrl);
					moreAnchor.attr('class', 'moreLink');
					moreAnchor.text(moreText);
					moreSpan.append(moreAnchor);
				}
				panelDiv.append(moreSpan);
			}
			return panelDiv;
        }
    }

	function getContent(panelData) {
		var contentDiv = $('<div>');
		contentDiv.attr('class', 'body');
		var url = (typeof panelData.url == 'undefined') ? null : panelData.url;
		var sendParams = (typeof panelData.sendParams == 'undefined') ? null : panelData.sendParams;
		var display = (typeof panelData.display == 'undefined') ? -1 : panelData.display;

		if (url != null) {
			$.ajax({
				url: getUrl(url, sendParams),
				data: sendParams,
				success: function(result) {
					var listItems = $('<ul>');
					listItems.attr('class', 'content');
					//alert($.parseJSON(result));
					$.each(result, function (index, value) {
						if (display == -1 || index < display) {
							var listItem = $('<li>');
							listItem.text(value);
							listItems.append(listItem);
							contentDiv.append(listItems);
						}
					});
				}
			});
		}
		return contentDiv;
	}

	function getUrl(url, sendParams) {
		var modifiedUrl = null;
		var paramString = '';

		if (sendParams == null || typeof sendParams == 'undefined' || sendParams.length == 0) {
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
					paramValue = selectedRow[parseInt(sendParam.columnIndex)];
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
})(jQuery, window, document);
