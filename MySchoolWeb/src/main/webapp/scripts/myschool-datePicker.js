/*
jQuery.datePicker plugin 
modified by Srikanth Kumar, 2013.
*/

(function($, window, document) {
  $.fn.datePicker = function(settings) {
    var id;
    var rangeId1;
    var rangeId2;
    var rangeDatePicker = false;

    var defaultSettings = {
      showOn: "button",
      buttonImage: "../images/icons/calendar.gif",
      buttonImageOnly: true,
      dateFormat: 'dd/mm/yy',
      changeMonth: true,
      changeYear: true
    };

    initDatePicker();

    function initDatePicker() {
      if (settings == null) {
        throw('datePicker - no settings specified.');
      }

      id = (settings.id == 'undefined') ? null : settings.id;
      rangeId1 = (settings.rangeId1 == 'undefined') ? null : settings.rangeId1;
      rangeId2 = (settings.rangeId2 == 'undefined') ? null : settings.rangeId2;

      if (id == null && rangeId1 == null && rangeId2 == null) {
        throw('datePicker - "id" for single datePicker or "rangeId1" and "rangeId2" for range datePickers must be specified.');
      } else if (id != null && (rangeId1 != null || rangeId2 != null)) {
        throw('datePicker - cannot specify "rangeId1" and "rangeId2" when "id" is specified.');
      } else if (id == null && (rangeId1 == null || rangeId2 == null)) {
        throw('datePicker - must specify "rangeId1" and "rangeId2" for range datePickers.');
      }
      rangeDatePicker = (rangeId1 != null && rangeId2 != null);

      if (rangeDatePicker) {
        initiateRangeDatePicker($('#' + rangeId1), $('#' + rangeId2));
      } else {
        initiateSingleDatePicker($('#' + id));
      }
    }

    function initiateSingleDatePicker(datePickerObj) {
	  // Show past and future by default
	  var minDate = (settings.past != 'undefined' && settings.past == false) ? new Date(): null;
	  var maxDate = (settings.future != 'undefined' && settings.future == false) ? new Date(): null;

      jQuery(datePickerObj).datepicker({
        showOn: getAttributeValue('showOn'),
        buttonImage: getAttributeValue('buttonImage'),
        buttonImageOnly: getAttributeValue('buttonImageOnly'),
        dateFormat: getAttributeValue('dateFormat'),
        changeMonth: getAttributeValue('changeMonth'),
        changeYear: getAttributeValue('changeYear'),
		minDate: minDate,
		maxDate: maxDate
      });
    }

    function initiateRangeDatePicker(datePickerObj1, datePickerObj2) {
      initiateSingleDatePicker(datePickerObj1);
      initiateSingleDatePicker(datePickerObj2);

	  $(datePickerObj1).datepicker("option", "onClose", function(selectedDate) {
        $(datePickerObj2).datepicker("option", "minDate", selectedDate);
        $(datePickerObj2).val('');
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
