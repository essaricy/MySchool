/*
jQuery.lazySelect plugin 
modified by Srikanth Kumar, 2013.
*/
(function($, window, document) {
  $.fn.lazySelect = function(settings) {
    var prefixCode = null;
    var codeValueDelimiter = null;
    var valueDelimiter = null;

    var defaultSettings = {
      url: null,
      data: null,
      selectOnCode: null,
      prefixCode: false,
      excludeCodes: null,
      codeValueDelimiter: ' - ',
      valueDelimiter: ' ',
      dataholder: 'aaData',
      codeIndex: 0,
      valueIndices: [1],
      width: "208px",
      changeCallback: null
    };

    initLazySelect();

    function initLazySelect() {
      var id = settings.id;
      var url = (typeof settings.url == 'undefined') ? defaultSettings.url : settings.url;
      var data = (typeof settings.url == 'undefined') ? defaultSettings.data : settings.data;
      var dataholder = (typeof settings.dataholder == 'undefined') ? defaultSettings.dataholder : settings.dataholder;
      var codeIndex = (typeof settings.codeIndex == 'undefined') ? defaultSettings.codeIndex : settings.codeIndex;
      var valueIndices = (typeof settings.valueIndices == 'undefined') ? defaultSettings.valueIndices : settings.valueIndices;

      prefixCode = (typeof settings.prefixCode == 'undefined') ? defaultSettings.prefixCode : settings.prefixCode;
      codeValueDelimiter = (typeof settings.codeValueDelimiter == 'undefined') ? defaultSettings.codeValueDelimiter : settings.codeValueDelimiter;
      valueDelimiter = (typeof settings.valueDelimiter == 'undefined') ? defaultSettings.valueDelimiter : settings.valueDelimiter;

      if (id == null) {
        throw('lazySelect - "id" not specified.');
      }
      if (url == null) {
        throw('lazySelect - "url" not specified.');
      }
      if (dataholder == null) {
        throw('lazySelect - "dataholder" not specified.');
      }
      if (codeIndex == null) {
        throw('lazySelect - "codeIndex" not specified.');
      }
      if (valueIndices == null || valueIndices.length == 0) {
        throw('lazySelect - "valueIndices" not specified.');
      }

      if (data == null) {
        data = {sid: new Date().getTime()};
      } else {
        data.sid = new Date().getTime();
      }
      var selObj = $('#' + id);
      var width = (typeof(settings.width) == 'undefined') ? defaultSettings.width: settings.width;

      settings.codeIndex = codeIndex;
      settings.valueIndices = valueIndices;
      settings.data = data;
      settings.changeCallback = (settings.changeCallback == null)? defaultSettings.changeCallback : settings.changeCallback;
      loadAjaxContent(selObj, settings, width);
    }

    function loadAjaxContent(selObj, settings, width) {
      var codeIndex = settings.codeIndex;
      var valueIndices = settings.valueIndices;
      var selectOnCode = (typeof(settings.selectOnCode) == 'undefined') ? defaultSettings.selectOnCode: settings.selectOnCode;
      var excludeCodes = (typeof settings.excludeCodes == 'undefined') ? defaultSettings.excludeCodes : settings.excludeCodes;

      jQuery.ajax({
        url: settings.url,
        data: settings.data,
        async: false,
        success: function(response) {
          $.each(response.aaData, function(objectIndex, objectValue) {
            var text = '';
            var codeValue = objectValue[codeIndex];
            var option = null;
            var exclude = false;
            if (excludeCodes != null) {
              for (var index=0; index<excludeCodes.length; index++) {
                if (excludeCodes[index] == codeValue) {
                  exclude = true;
                  break;
                }
              }
            }
            if (!exclude) {
              if (selectOnCode != null && selectOnCode == codeValue) {
                var option = $("<option selected>");
              } else {
                var option = $("<option>");
              }
              option.attr("value", objectValue[codeIndex]);
              if (prefixCode) {
                text = objectValue[codeIndex] + codeValueDelimiter;
              }
              text = text + getArrayValue(objectValue, settings.valueIndices, valueDelimiter);
              option.text(text);
              option.appendTo(selObj);
            }
          });
          if (settings.changeCallback == null) {
              $(selObj).chosen({width: width});
          } else {
              $(selObj).chosen({width: width}).change(settings.changeCallback);
          }
        }
      });
    }

    function getArrayValue(arrayObject, valueIndices, dilimiter) {
      var returnValue = '';
      if (valueIndices != null && valueIndices.length != 0) {
        if (valueIndices.length == 1) {
          returnValue = arrayObject[valueIndices];
        } else {
          for (index=0; index<valueIndices.length; index++) {
            returnValue = returnValue + arrayObject[valueIndices[index]]
            if (index != valueIndices.length - 1) {
              returnValue = returnValue + dilimiter;
            }
          }
        }
      }
      return returnValue;
    }
  }
})(jQuery, window, document);
