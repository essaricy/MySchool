/*
jQuery.validator plugin 
modified by Srikanth Kumar, 2013.
*/

function validateField(validationSettings) {
    if (validationSettings != null) {
        var failedValidationType = null;

        var field = validationSettings.field;
        var dataType = validationSettings.dataType;

        var errorClass = (typeof validationSettings.errorClass == 'undefined') ? '' : validationSettings.errorClass;
        var ignoreOnEmpty = (typeof validationSettings.ignoreOnEmpty == 'undefined') ? false : validationSettings.ignoreOnEmpty;
        var checkInvalid = !(typeof validationSettings.invalid == 'undefined');

        var checkEmpty = !(typeof validationSettings.empty == 'undefined');
        var checkMinimum = !(typeof validationSettings.min == 'undefined');
        var checkMaximum = !(typeof validationSettings.max == 'undefined');

        if (field != null) {
            var fieldValue = field.val();
            if (dataType == 'numeric') {
                if (checkEmpty) {
                    if(fieldValue == '') {
                        if (ignoreOnEmpty) {
                            return true;
                        } else {
                            failedValidationType = 'empty';
                        }
                    }
                }
                if (failedValidationType == null) {
                    if (checkInvalid) {
                        if(!$.isNumeric(fieldValue)) {
                            failedValidationType = 'invalid';
                        }
                    }
                }
                if (failedValidationType == null) {
                    if (checkMinimum) {
                        var minBoundary = (typeof validationSettings.min.minBoundary == 'undefined') ? 0 : validationSettings.min.minBoundary;
                        if(parseInt(fieldValue) < minBoundary) {
                            failedValidationType = 'min';
                        }
                    }
                }
                if (failedValidationType == null) {
                    if (checkMaximum) {
                        var maxBoundary = (typeof validationSettings.max.maxBoundary == 'undefined') ? 0 : validationSettings.max.maxBoundary;
                        if(parseInt(fieldValue) > maxBoundary) {
                            failedValidationType = 'max';
                        }
                    }
                }
            }

            var message = null;
            var messageType = null;

            if (failedValidationType == null) {
                field.removeClass(errorClass);
            } else {
                if (failedValidationType == 'empty') {
                    validationField = validationSettings.empty;
                    message = validationField.message;
                } else if (failedValidationType == 'invalid') {
                    validationField = validationSettings.invalid;
                    message = validationField.message;
                } else if (failedValidationType == 'min') {
                    validationField = validationSettings.min;
                    message = validationField.message;
                    var minBoundary = (typeof validationSettings.min.minBoundary == 'undefined') ? 0 : validationSettings.min.minBoundary;
                    message = message.replace("#minBoundary", minBoundary); 
                } else if (failedValidationType == 'max') {
                    validationField = validationSettings.max;
                    message = validationField.message;
                    var maxBoundary = (typeof validationSettings.max.maxBoundary == 'undefined') ? 0 : validationSettings.max.maxBoundary;
                    message = message.replace("#maxBoundary", maxBoundary); 
                }

                messageType = validationField.messageType;
                field.addClass(errorClass);

                if (messageType == 'error') {
                    showError(message);
                }
                return false;
            }
        }
    }
  return true;
}
