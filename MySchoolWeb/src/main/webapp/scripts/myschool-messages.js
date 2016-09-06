/*
 * myschool-notifications plugin 
 * This script contains utitlity methods for launching message boxees and notifications.
 * modified by Srikanth Kumar, 2016.
*/

/*********************** NOTIFICATION MESSAGE FUNCTIONS ***********************/
Lobibox.base.DEFAULTS = $.extend({}, Lobibox.base.DEFAULTS, {
  iconSource: 'fontAwesome'
});
Lobibox.notify.DEFAULTS = $.extend({}, Lobibox.notify.DEFAULTS, {
  iconSource: 'fontAwesome'
});
Lobibox.confirm.DEFAULTS = $.extend({}, Lobibox.confirm.DEFAULTS, {
  iconSource: 'fontAwesome'
});
Lobibox.prompt.DEFAULTS = $.extend({}, Lobibox.prompt.DEFAULTS, {
  iconSource: 'fontAwesome'
});

var messageOptions = {
  showClass: 'slideIn',
  hideClass: 'slideOut',
  size: null, 
  sound: false,
  icon: true,
  delay: 0,
  title: null,
  msg: null
};
var ok_button = {
	ok: { 'class': 'formButton', closeOnClick: true }
}
var yes_no_buttons = {
	yes: { 'class': 'formButton', closeOnClick: true },
	no: { 'class': 'formButton', closeOnClick: true }
}

// These are auto close messages
function notifyMessage(text) {
  message('notify', 'default', text);
}
function notifyInfo(text) {
  message('notify', 'info', text);
}
function notifyWarning(text) {
  message('notify', 'warning', text);
}
function notifyError(text) {
  message('notify', 'error', text);
}
function notifySuccess(text) {
  message('notify', 'success', text);
}

/*********************** ATTENTION MESSAGE FUNCTIONS ***********************/
// These are sticky messages. To show messages that are output of some operation
function attendMessage(text) {
  message('attend', 'default', text);
}
function attendInfo(text) {
  message('attend', 'info', text);
}
function attendWarning(text) {
  message('attend', 'warning', text);
}
function attendError(text) {
  message('attend', 'error', text);
}
function attendSuccess(text) {
  message('attend', 'success', text);
}

/*********************** ALERT MESSAGE FUNCTIONS ***********************/
function alertMessage(text) {
  message('alert', 'default', text);
}
function alertInfo(text) {
  message('alert', 'info', text);
}
function alertWarning(text) {
  message('alert', 'warning', text);
}
function alertError(text) {
  message('alert', 'error', text);
}
function alertSuccess(text) {
  message('alert', 'success', text);
}
/*********************** INTERACTIVE MESSAGE FUNCTIONS ***********************/
/** Confirmation messages */
function interactConfirm(text, successCallback, failCallback) {
  Lobibox.confirm({
	title: 'Are You Sure?',
    msg: text,
    buttons:yes_no_buttons,
    //callback: callback
    callback: function ($this, type) {
      if (type === 'yes') {
		  successCallback.call();
      } else if (type === 'no') {
		  if (failCallback != null) {
			  failCallback.call();
		  }
      }
    }
  });
}
/** Prompt messages */
function promptText(label, value, successCallback) {
  interactPrompt('text', label, value, successCallback, null);
}
function promptNumber(label, value, successCallback) {
  interactPrompt('number', label, value, successCallback, null);
}
function promptColor(label, value, successCallback) {
  interactPrompt('color', label, value, successCallback, null);
}
function interactPrompt(type, label, value, successCallback, failCallback) {
  var title = null;
  if (type == 'text') {
    title='Enter Value For';
  } else if (type == 'number') {
    title='Enter Value For';
  } else if (type == 'color') {
    title='Select Color For';
  }
  Lobibox.prompt(type, {
    title: title,
	label: label,
	value: ((value==null)?'':value),
    required: true,
	errorMessage: 'The field is required',
	callback: function (box, type) {
      if (type === 'ok') {
		  if ($(box.$input[0]).val() != '') {
			  //successCallback.call();
			  successCallback($(box.$input[0]).val());
		  }
      } else if (type === 'cancel') {
		  if (failCallback != null && typeof(failCallback) != 'undefined') {
			  failCallback.call();
		  }
      }
    }
  });
}


var currentWindow=null;
// Window
function openWindow(url, title, width, height) {
	// TODO May be report is not working since i m loading the data than using URL?
  //notifyInfo('Loading ' + url);
  /*var jqxhr = jQuery.get(url)
    .done(function(data) {
      currentWindow = Lobibox.window({
        title: title,
        url: url,
        //content: '<script type="text/javascript"> $(document).ready(function() {   $(this).datePicker({	  id: "qualifyingPercentage" }); }); </script>   <table width="80%" align="center" class="formTable_Data" style="font-size: 1.3em;">     <tr>       <td class="label" width="40%">Name<label class="mandatory">*</label></td>       <td class="value" width="60%">         <input type="text" id="gradeName" maxlength="2" />       </td>     </tr>     <tr>       <td class="label" width="40%">Qualifying Percentage<label class="mandatory">*</label></td>       <td class="value" width="60%">         <input type="text" id="qualifyingPercentage" maxlength="2" />       </td>     </tr>     <tr>       <td colspan="2" align="center">         <input type="button" id="create" value="Create" onclick="notifyInfo(\'Added exam grade\'); closeCurrentWindow();"/>       </td>     </tr>   </table> </table>',
        width: width,
        height: height,
        draggable: false,
        modal: true,
        showClass: 'zoomIn',                // Show animation class
        hideClass: 'zoomOut',               // Hide animation class
        // iconSource: 'fontAwesome',
      });
    })
    .fail(function() {
        
    });*/
  currentWindow = Lobibox.window({
    title: title,
    url: url,
    width: width,
    height: height,
    draggable: false,
    modal: true,
    showClass: 'zoomIn',                // Show animation class
    hideClass: 'zoomOut',               // Hide animation class
    // iconSource: 'fontAwesome',
  });
  return currentWindow;
}

function closeCurrentWindow() {
  if (currentWindow != null)  {
	  currentWindow.destroy();
	  currentWindow = null;
  }
}
/*********************** ONE BIG FINAL FUNCTIONS ***********************/
function message(mode, type, text) {
  //alert('mode=' + mode + ', type=' + type + ', text=' + text);
  messageOptions.msg=text;

  if (mode == 'notify') {
	  messageOptions.size='mini';
	  messageOptions.delay=3000;

      if (type == 'default') {
        messageOptions.title='Message';
      } else if (type == 'info') {
        messageOptions.title='Information';
      } else if (type == 'warning') {
        messageOptions.title='Warning';
      } else if (type == 'error') {
        messageOptions.title='Error';
      } else if (type == 'success') {
        messageOptions.title='Success';
      }
	  Lobibox.notify(type, messageOptions);
  } else if (mode == 'attend') {
	  messageOptions.size='large';
	  messageOptions.delay=0;

      if (type == 'default') {
        messageOptions.title='Message';
      } else if (type == 'info') {
        messageOptions.title='Information';
      } else if (type == 'warning') {
        messageOptions.title='Warning';
      } else if (type == 'error') {
        messageOptions.title='Error';
      } else if (type == 'success') {
        messageOptions.title='Success';
      }
	  Lobibox.notify(type, messageOptions);
  } else if (mode == 'alert') {
	  delete messageOptions['size'];
	  delete messageOptions['delay'];

      if (type == 'info') {
        messageOptions.title='Info';
      } else if (type == 'warning') {
        messageOptions.title='Warning';
      } else if (type == 'error') {
        messageOptions.title='Error';
      } else if (type == 'success') {
        messageOptions.title='Success';
      }
	  messageOptions.buttons=ok_button; 
	  Lobibox.alert(type, messageOptions);
  }
}
