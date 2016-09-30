/*
jQuery.ajax plugin 
modified by Srikanth Kumar, 2016.
*/

function sendAjax(url, data, successCallback, failCallback) {
  wait();
  jQuery.ajax({
    type: "POST",
    url: url,
    data: data,
    context: this
  }).done(function(result) {
    if (result.Successful) {
      var message = result.StatusMessage;
      if (message == null || typeof(message) == 'undefined' || message == '' || message == 'null') {
        notifyInfo("Data has been saved successully");
      } else {
        alertInfo(message);
      }
	  unwait();
      if (successCallback != null) {
        successCallback.call();
      }
    } else {
      var message = result.StatusMessage;
      if (message == null || typeof(message) == 'undefined' || message == '' || message == 'null') {
        alertError("Something really went wrong. Try submitting the form again.");
      } else {
        alertError(message);
      }
	  unwait();
	  if (failCallback != null) {
        failCallback.call();
      }
    }
  });
}
