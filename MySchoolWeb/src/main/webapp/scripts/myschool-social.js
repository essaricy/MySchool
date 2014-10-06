/*
jQuery.social plugin 
modified by Srikanth Kumar, 2013.
*/

(function($, window, document) {
  $.fn.social = function(settings) {
    var defaultSettings = {
      containerName: 'Socialize',
      title: null
    };

    initSocialize();

    function initSocialize() {
	  if (settings == null) {
		  throw('myschool.social - no settings specified.');
	  }
	  var containerName = (typeof(settings.containerName) == 'undefined') ? defaultSettings.containerName: settings.containerName;
	  var title = settings.title;

      if (title == null) {
        throw('myschool.social - "title" not specified.');
      }

	  var container = $('#' + containerName);
	  container.append('<div class="facebook" title="Share link on Facebook">Facebook</div>');
      container.append('<div class="twitter" title="Share link on Twitter">Twitter</div>');
      container.append('<div class="plusone" title="Share link on Google+">Google+</div>');

	  $(container).socialLikes({
        url: window.location.href,
        title: title,
        counters: true,
	    zeroes: "yes"
      });
    }
  }
})(jQuery, window, document);
