function parseModelReponse(result) {
  $(result).find("successful").each(function() {
    if ($(this).text() == 'true') {
      $(result).find("statusMessage").each(function() {
        var message = $(this).text();
        if (message == null || message == '' || message == 'null') {
          parent.window.showSuccess('Data has been saved successfully.');
        } else {
          parent.window.showSuccess(message);
        }
      });
      //parent.window.modelDialog.close();
	  if (modal != null) {
		modal.close();
	  }
      parent.window.reloadData();
    } else {
      $(result).find("statusMessage").each(function() {
        var message = $(this).text();
        if (message != null && message != '' && message != 'null') {
          showError(message);
        } else {
          showError("Server has sent an unexpected response. Please contact support for assistance.");
        }
      });
    }
  });
}

function parseWholepageResponse(result, reload) {
  var returnCode = false;
  $(result).find("successful").each(function() {
    if ($(this).text() == 'true') {
      $(result).find("statusMessage").each(function() {
        var message = $(this).text();
        if (message == null || message == '' || message == 'null') {
            showSuccess('Data has been saved successfully.');
            returnCode = true;
        } else {
            showSuccess(message);
            returnCode = true;
        }
      });
      if (reload) {
        reloadData();
      }
    } else {
      $(result).find("statusMessage").each(function() {
        var message = $(this).text();
        if (message != null && message != '' && message != 'null') {
          showError(message);
        } else {
          showError("Server has sent an unexpected response. Please contact support for assistance.");
        }
      });
    }
  });
  return returnCode;
}

/******************************************************************************/
function showFieldError(field, message) {
    var actionResult = $('#action-result');
    actionResult.hide(0);
    actionResult.attr('class', 'action-result-error');
    actionResult.html(message);
    actionResult.show(0);
}

function showError(message) {
  alertify.error(message);
  return false;
}

function showSuccess(message) {
  alertify.success(message);
  return false;
}

function changeCheckboxValue(chkObj) {
  if (chkObj.checked) {
    chkObj.value = 'true';
  } else {
    chkObj.value = 'false';
  }
}

function submitPage(actionToSubmit, targetToSubmit) {
  document.forms[0].action = actionToSubmit;
  document.forms[0].target = targetToSubmit;
  document.forms[0].submit();
  //dhtmlwindow.progress();
}

var modal = null;
function openDialog(url, title, width, height) {
	var jqxhr = jQuery.get(url)
	.done(function(data) {
		modal = jQuery.modal(data, {
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
	    modal = jQuery.modal('<span style="font-size: 1.5em;" class="error">Oops! We are unable to load the form now. Please try again later.</h1>', {
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
	return modal;
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

function parseJsonReponse(result) {
  if (result.Successful) {
	  var message = result.StatusMessage;
	  if (message != null && typeof(message) != 'undefined' && message != '' && message != 'null') {
        parent.window.showSuccess(message);
      } else {
        parent.window.showSuccess('Data has been updated successfully.');
      }
	  if (modal != null) {
		modal.close();
	  }
      parent.window.reloadData();
  } else {
	  var message = result.StatusMessage;
	  if (message != null && typeof(message) != 'undefined' && message != '' && message != 'null') {
        showError(message);
      } else {
        showError("Server has sent an unexpected response. Please contact support for assistance.");
      }
  }
}
