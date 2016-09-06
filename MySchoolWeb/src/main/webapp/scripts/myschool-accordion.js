/*
jQuery.myAccordion plugin 
modified by Srikanth Kumar, 2013.
*/

(function($, window, document) {
  $.fn.myAccordion = function(settings) {
    var defaultSettings = {
      collapsible: true,
      heightStyle: "content",
      icons: {
        header: "ui-icon-circle-arrow-e",
        activeHeader: "ui-icon-circle-arrow-s"
      }
    };

    initMyAccordion();

    function initMyAccordion() {
      if (settings == null) {
        throw('myAccordion - no settings specified.');
      }

      id = (settings.id == 'undefined') ? null : settings.id;
      if (id == null) {
        throw('myAccordion - "id" is not specified.');
      }

      var accordianElement = $('#' + id);
      jQuery(accordianElement).accordion({
        collapsible: getAttributeValue('collapsible'),
        icons: getAttributeValue('icons'),
        heightStyle: getAttributeValue('heightStyle'),
      });
    }

    function getAttributeValue(attributeName) {
      if (typeof(settings[attributeName]) == 'undefined') {
        return defaultSettings[attributeName];
      }
      return settings[attributeName];
    }
  }
})(jQuery, window, document);
