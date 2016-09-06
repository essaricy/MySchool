function handleServerResponseOnPage(result, reload) {
  var returnCode = false;
  if (result.Successful) {
    var message = result.StatusMessage;
    if (message != null && typeof(message) != 'undefined' && message != '' && message != 'null') {
      alertSuccess(message);
      returnCode = true;
    } else {
      alertSuccess('Data has been saved successfully.')
      returnCode = true;
    }
    if (reload) {
	  location.reload();
    }
  } else {
    var message = result.StatusMessage;
    if (message != null && typeof(message) != 'undefined' && message != '' && message != 'null') {
      alertError(message);
    } else {
      alertError("Server has sent an unexpected response. Please contact support for assistance.");
    }
  }
  return returnCode;
}

function handleServerResponseOnModal(result) {
  if (result.Successful) {
    var message = result.StatusMessage;
    if (message != null && typeof(message) != 'undefined' && message != '' && message != 'null') {
      parent.window.alertSuccess(message);
    } else {
      parent.window.alertSuccess('Data has been updated successfully.');
    }
    closeCurrentWindow();
    parent.window.reloadData();
  } else {
    var message = result.StatusMessage;
    if (message != null && typeof(message) != 'undefined' && message != '' && message != 'null') {
      alertError(message);
    } else {
      alertError("Server has sent an unexpected response. Please contact support for assistance.");
    }
  }
}

function showFieldError(field, message) {
	var actionResult = $('#action-result');
	actionResult.hide(0);
	actionResult.attr('class', 'action-result-error');
	actionResult.html(message);
	actionResult.show(0);
}

function submitPage(actionToSubmit, targetToSubmit) {
  document.forms[0].action = actionToSubmit;
  document.forms[0].target = targetToSubmit;
  document.forms[0].submit();
  //dhtmlwindow.progress();
}

/*

function attendError(message) {
  alertify.error(message);
  return false;
}

function notifySuccess(message) {
  alertify.success(message);
  return false;
}

function openWindow(url, title, width, height) {
  var jqxhr = jQuery.get(url)
  .done(function(data) {
    currentWindow = jQuery.modal(data, {
      focus: 'true',
      title: title,
      opacity: 60,
      minWidth: width,
      minHeight: height,
      overlayClose: false,
      overlayCss: {
        opacity: "0.6",
        filter: "alpha(opacity=60)",
        "background-color": "#4682B4"
      },
      containerCss: {
        backgroundColor:"#FFFFFF",
        borderColor:"#FFFFFF",
        padding:0,
        border: "2px solid #CC9900",
        "-moz-box-shadow": "0 0 3px 3px #CC9900",
        "-webkit-box-shadow": "0 0 3px 3px #CC9900",
        "box-shadow": "0 0 3px 3px #CC9900"
      },
    });
  })
  .fail(function() {
    currentWindow = jQuery.modal('<span style="font-size: 1.5em;" class="error">Oops! We are unable to load the form now. Please try again later.</h1>', {
      focus: 'true',
      opacity: 80,
      maxWidth: width,
      maxHeight: 40,
      overlayClose: false,
      overlayCss: {
        backgroundColor:"#000000"
      },
      containerCss: {
        backgroundColor:"#FFFFFF",
        borderColor:"#FFFFFF",
        padding:0,
        "-moz-box-shadow": "0 0 3px 3px #888",
        "-webkit-box-shadow": "0 0 3px 3px #888",
        "box-shadow": "0 0 3px 3px #888"
      },
    });
  });
return currentWindow;
}

function openReportDialog(url, title, width, height) {
  return $.modal('<iframe src="' + url + '" width="' + (width-10) + '" height="' + (height-30) + '">', {
    containerCss:{
      backgroundColor:"#fff", 
      borderColor:"#fff", 
      height:450, 
      padding:0, 
      width:830
    },
    overlayClose:true,
    title: title,
    minWidth: width,
    minHeight: height,
  });
}
*/