function validateEmployeeAttribute(operation, url, attributeName, attributeData, attributeSequence) {

  var datatable = getEmployeeDataTable(attributeName);
  // read data from the corresponding data table.
  var attributesDataArray = getEmployeeAttributesData(operation, datatable, attributeName, attributeSequence);
  // append EmployeeDocument that is passed in.
  attributesDataArray[attributesDataArray.length] = attributeData;

  // send request to server to validate and check for duplicates.
  $.ajax({
    type: "POST",
    url: url,
    data: {
      AttributeName: attributeName,
      AttributeData: JSON.stringify(attributesDataArray),
      sid: new Date().getTime()
    },
    async: false,
    context: this
  }).done(function(result) {
    if (result.Successful) {
      var message = result.StatusMessage;
      if (message != null && typeof(message) != 'undefined' && message != '' && message != 'null') {
        notifySuccess(message);
      } else {
        notifySuccess('Data has been updated successfully.');
      }
      closeCurrentWindow();

      if (operation == 'CREATE') {
        addAttributeData(datatable, attributeData, attributeSequence);
      } else if (operation == 'UPDATE') {
        updateAttributeData(datatable, attributeData, attributeSequence);
      }
    } else {
      var message = result.StatusMessage;
      if (message != null && typeof(message) != 'undefined' && message != '' && message != 'null') {
        attendError(message);
      } else {
        attendError("Server has sent an unexpected response. Please contact support for assistance.");
      }
    }
  });
}

function getEmployeeDataTable(attributeName) {
  if (attributeName == 'EmployeeDocument') {
    return $('#EmployeeDocumentsTable_Inner').dataTable();
  } else if (attributeName == 'EmployeeEducation') {
    return $('#EmployeeEducationsTable_Inner').dataTable();
  } else if (attributeName == 'EmployeeExperience') {
    return $('#EmployeeExperiencesTable_Inner').dataTable();
  } else if (attributeName == 'EmployeePromotion') {
    return $('#EmployeePromotionsTable_Inner').dataTable();
  } else if (attributeName == 'EmployeeTeachingSubject') {
    return $('#EmployeeTeachingSubjectsTable_Inner').dataTable();
  }
}

function getEmployeeAttributesData(operation, datatable, attributeName, attributeSequence) {
  if (datatable == null) {
	  datatable = getEmployeeDataTable(attributeName);
  }
  var attributesDataArray = new Array();
  var rows = datatable.fnGetNodes();
  var selectedIndex = fnGetSelectedIndex(datatable);
  if (rows != null && typeof(rows) != 'undefined') {
    for(var index=0; index<rows.length; index++) {
	  // if its an update then dont put old data.
	  if (operation == 'UPDATE' && index == selectedIndex) {
		  continue;
	  }
      var row = datatable.fnGetData(rows[index]);
      // create employee attribute object by reading row
      var EmployeeAttributeData = new Object();
      // For each attribute, set the column value to the employee attribute object
      for(var jindex=0; jindex<attributeSequence.length; jindex++) {
        EmployeeAttributeData[attributeSequence[jindex]] = row[jindex];
      }
      attributesDataArray[attributesDataArray.length] = EmployeeAttributeData;
    }
  }
  return attributesDataArray;
}

function addAttributeData(datatable, attributeData, attributeSequence) {
  var datatableData = new Array();
  for (var index=0; index<attributeSequence.length; index++) {
    datatableData[index] = attributeData[attributeSequence[index]];
  }
  datatable.fnAddData( datatableData );
}

function updateAttributeData(datatable, attributeData, attributeSequence) {
  var selectedIndex = fnGetSelectedIndex( datatable );
  if (selectedIndex != null) {
    for (var index=0; index<attributeSequence.length; index++) {
      datatable.fnUpdate(attributeData[attributeSequence[index]], selectedIndex, index);
    }
  }
}
function fnGetSelectedIndex( oTableLocal ){
  var aReturn = new Array();
  var selectedIndex = null;
  var aTrs = oTableLocal.fnGetNodes();
  for ( var i=0 ; i<aTrs.length ; i++ ) {
    if ( $(aTrs[i]).hasClass('row_selected') ) {
      selectedIndex = i;
      break;
    }
  }
  return selectedIndex;
}

function fnGetSelected(oTableLocal) {
  var aReturn = new Array();
  var selected = null;
  var aTrs = oTableLocal.fnGetNodes();
  for ( var i=0 ; i<aTrs.length ; i++ ) {
    if ( $(aTrs[i]).hasClass('row_selected') ) {
      aReturn.push( aTrs[i] );
      selected = aTrs[i];
    }
  }
  return selected;
}
