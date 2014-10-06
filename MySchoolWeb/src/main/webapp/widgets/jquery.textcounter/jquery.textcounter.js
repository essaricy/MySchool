/*
jQuery.textcounter plugin 
modified by Srikanth Kumar, 2013.
*/

(function($){  
  $.fn.textcounter = function(settings) {
    
    var defaultSettings = {
      id: 'textarea'
    };
    
    init();

    function init() {
      if (typeof settings == 'undefined') {
          settings = defaultSettings;
      }
      var textareaId = (typeof settings.id == 'undefined') ? defaultSettings.id : settings.id;
      var thisObj = $('#' + textareaId);

      var maxlength = thisObj.attr("maxlength");
      thisObj.after('<div class="text-counter">Remaining: <span id="' + textareaId + '_RemainingText" class="text-count">' + maxlength + "</span></div>");
      thisObj.bind("keyup change", function(){
          checkMaxLength(textareaId,  maxlength);
      });
    }

    function checkMaxLength(textareaId, maxlength) {
      var textareaObj = $('#' + textareaId);
      var remainingObj = $('#' + textareaId + '_RemainingText');

      var currentLengthInTextarea = textareaObj.val().length;
	  $(remainingObj).text(parseInt(maxlength) - parseInt(currentLengthInTextarea));
      if (currentLengthInTextarea > (maxlength)) { 
        // Trim the field current length over the maxlength.
        textareaObj.val(textareaObj.val().slice(0, maxlength));
		$(remainingObj).text(0);
	  }
	}
  }
})(jQuery);