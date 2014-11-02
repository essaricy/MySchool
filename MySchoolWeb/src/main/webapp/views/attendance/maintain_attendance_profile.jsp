<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page import="com.myschool.attendance.dto.AttendanceCode"%>

<style>
.ui-tabs .ui-tabs-nav li a {font-size: 0.6em !important;}
.full-calendar-header {
  border: 1px solid #aaaaaa;
  background: #D4D4D4 50% 50% repeat-x;
  color: #222222;
  font-weight: bold;
  padding-left: 5px;
  padding-right: 5px;
  padding-top: 3px;
  padding-bottom: 3px;
  font-family: Verdana,Arial,sans-serif;
  font-size: 0.6em;
  min-width:20px;
  border-collapse: collapse;
}
/* #42C0FB - light blue, #FFA500 - Orange, #7BBF00 - dark green, #E04006 - red, #4169E1 - Dark blue, #A6D785 - light green */
.Attendance-GENERAL_HOLIDAY {
  background-color: #42C0FB;
  cursor: pointer;
  font-weight: regular;
  font-size: 10px;
}
.Attendance-DECLARED_HOLIDAY {
  background-color: #FFA500;
  cursor: pointer;
  font-weight: regular;
  font-size: 10px;
}
.Attendance-PRESENT {
  background-color: #7BBF00;
  cursor: pointer;
  font-weight: regular;
  font-size: 10px;
}
.Attendance-ON_LEAVE {
  background-color: #A6D785;
  cursor: pointer;
  font-weight: regular;
  font-size: 10px;
}
.Attendance-HALF_DAY_LEAVE {
  background-color: #A6D785;
  cursor: pointer;
  font-weight: regular;
  font-size: 10px;
}
.Attendance-ABSENT {
  background-color: #E04006;
  cursor: pointer;
  font-weight: regular;
  font-size: 10px;
}
.Attendance-UNASSIGNED {
  background-color: #EEE;
  cursor: pointer;
  font-weight: regular;
  font-size: 10px;
}
.Attendance-UNACCOUNTED {
  background-color: #D4D4D4;
  background-image: url('<%=request.getContextPath()%>/images/icons/mesh.png');
  opacity: 0.4;
  font-weight: regular;
  font-size: 10px;
}
.Attendance-CurrentDate {
  border: 3px solid #333;
  box-shadow: 5px 5px 5px #888;
}

.CircleText-RED {
  background: red;
  border-radius: 100%;
  display: inline-block;
  font-size: 14px;
  font-weight: bold;
  line-height: 2em;
  margin-right: 15px;
  text-align: center;
  width: 2em; 
  color: yellow;
}

.CircleText-GRAY {
  background: #cccccc;
  border-radius: 100%;
  display: inline-block;
  font-size: 14px;
  font-weight: bold;
  line-height: 2em;
  margin-right: 15px;
  text-align: center;
  width: 2em; 
  color: yellow;
}

.CircleText-GREEN {
  background: green;
  border-radius: 100%;
  display: inline-block;
  font-size: 14px;
  font-weight: bold;
  line-height: 2em;
  margin-right: 15px;
  text-align: center;
  width: 2em; 
  color: yellow;
}

.CircleText-BLUE {
  background: #5178D0;
  border-radius: 100%;
  display: inline-block;
  font-size: 14px;
  font-weight: bold;
  line-height: 2em;
  margin-right: 15px;
  text-align: center;
  width: 2em; 
  color: yellow;
}

.CircleText-PINK {
  background: #EF0BD8;
  border-radius: 100%;
  display: inline-block;
  font-size: 14px;
  font-weight: bold;
  line-height: 2em;
  margin-right: 15px;
  text-align: center;
  width: 2em; 
  color: yellow;
}

</style>

<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/widgets/jquery-ui-1.10.2/ui/jquery.ui.tabs.js"></script>
<script type="text/javascript">
var AttendanceCodes=new Array();
<c:set var="AttendanceCodes" value="<%=AttendanceCode.values()%>" />
<c:forEach var="AttendanceCode" items="${AttendanceCodes}">
AttendanceCodes[AttendanceCodes.length]='${AttendanceCode}';
</c:forEach>

var Assignments = ["AssignedStates", "AssignedRegions", "AssignedBranches", "AssignedSchools", "AssignedClasses"];
var DataIdNames = ["StateId", "RegionId", "branchId", "schoolId", "ClassId"];
var RequestUrls=["<%=request.getContextPath()%>/state/jsonList.htm",
    "<%=request.getContextPath()%>/region/jsonList.htm",
    "<%=request.getContextPath()%>/branch/jsonList.htm",
    "<%=request.getContextPath()%>/school/jsonList.htm",
    "<%=request.getContextPath()%>/class/jsonListBySchool.htm"];

$(document).ready(function() {
  // Populate Academic Years
  $(this).lazySelect({
    id: "EffectiveAcademicYear",
    url: '<%=request.getContextPath()%>/academic/jsonList.htm',
    valueIndices: [0],
    selectOnCode: $('#EffectiveAcademicYearName').val()
  });
  $('#EffectiveAcademicYear').change(function() {
    $("#AttendanceProfilesDetails").tabs( "option", "active", 0);
    populateAttendanceProfile();
  });

  // Create Tabs
  $("#AttendanceProfilesDetails").tabs();
  $("#AttendanceProfilesDetails").tabs( "option", "active", 1);

  prepareProfileDefinitionTab();
  prepareProfileAssignementsTab();

  $('#EffectiveAcademicYear').change();

  function prepareProfileDefinitionTab() {
    // Profile attendance tools
    for (var index=0; index<AttendanceCodes.length; index++) {
      var AttendanceCode=AttendanceCodes[index];
      if (AttendanceCode != '<%=AttendanceCode.UNACCOUNTED%>') {
        var option = '<option value="' + AttendanceCode + '">' + AttendanceCode + '</option>';
        $('#ChangeAttendanceFrom').append(option);
        $('#ChangeAttendanceTo').append(option);
      }
    }

    $('#ChangeAttendanceFrom').chosen({width: '200px'});
    $('#ChangeAttendanceTo').chosen({width: '200px'});

    // Change attendance tool implementation
    $('#ChangeBulkAttendace').click(function() {
      var ChangeAttendanceFromVal = $('#ChangeAttendanceFrom').val();
      var ChangeAttendanceToVal = $('#ChangeAttendanceTo').val();
      if (ChangeAttendanceFromVal != ChangeAttendanceToVal) {
        $("#YearViewCalendar > tbody > tr > td").each(function(index, value) {
          if ($(value).hasClass('Attendance-' + ChangeAttendanceFromVal)) {
            $(value).removeClass('Attendance-' + ChangeAttendanceFromVal);
            $(value).addClass('Attendance-' + ChangeAttendanceToVal);
            // change attendance code
            $(value).find('input[type="hidden"][name="AttendanceCode"]').val(ChangeAttendanceToVal);
          }
        });
      }
    });

    // Profile Definition. Create Dates header row
    var headerRow = $('<tr>');
    $("#YearViewCalendar > thead").html("");
    $("#YearViewCalendar thead").append(headerRow);
    headerRow.append('<th class="full-calendar-header">Month/Dates</th>');
    for (var currentDate=1; currentDate<=31; currentDate++) {
      headerRow.append('<th class="full-calendar-header">' + currentDate + '</th>');
    }
  }

  function prepareProfileAssignementsTab() {
    $('#AssignedStates').change(function(event, params) { changeSelections('AssignedStates', event, params); });
    $('#AssignedRegions').change(function(event, params) { changeSelections('AssignedRegions', event, params); });
    $('#AssignedBranches').change(function(event, params) { changeSelections('AssignedBranches', event, params); });
    $('#AssignedSchools').change(function(event, params) { changeSelections('AssignedSchools', event, params); });
    $('#AssignedClasses').change(function(event, params) { changeSelections('AssignedClasses', event, params); });

    $('.ClearAllAssignments').click(function() {
      var dataDest = $(this).attr('data-dest');
      //$('#' + dataDest + ' option').prop('selected', false);
      $('#' + dataDest + ' > option').each(function(index, value) {
        var selected = $(value).is(':selected');
        var selectedValue = $(value).val();
        if (selected) {
          $('#' + dataDest + ' option[value="' + selectedValue + '"]').prop('selected', false);
          var optionVal = $(value).val();
          var params = new Object();
          params.deselected=optionVal;
          changeSelections(dataDest, null, params);
        }
      });
      $('#' + dataDest).trigger("chosen:updated");
    });

    $('.AddAllAssignments').click(function() {
      var dataDest = $(this).attr('data-dest');
      $('#' + dataDest + ' > option').each(function(index, value) {
        var selected = $(value).is(':selected');
        var selectedValue = $(value).val();
        if (!selected) {
          $('#' + dataDest + ' option[value="' + selectedValue + '"]').prop('selected', true);
          var optionVal = $(value).val();
          var params = new Object();
          params.selected=optionVal;
          changeSelections(dataDest, null, params);
        }
      });
      $('#' + dataDest).trigger("chosen:updated");
    });

    $(this).lazySelect({
      id: "AssignedStates",
      url: RequestUrls[0],
    });
    $('#AssignedRegions').chosen({width: '95%'});
    $('#AssignedBranches').chosen({width: '95%'});
    $('#AssignedSchools').chosen({width: '95%'});
    $('#AssignedClasses').chosen({width: '95%'});
  }

  function changeSelections(selBoxName, event, params) {
    //alert("changeSelections for " + selBoxName);
    var selected = (params.selected != null && params.selected != 'undefined');
    var changedOptionVal = (selected ? params.selected : params.deselected);
    var selectBoxIndex = getSelectBoxIndex(selBoxName);
    if (selectBoxIndex != Assignments.length-1) {
      var ajaxData={};
      var nextSelectBoxName = Assignments[selectBoxIndex+1];
      var dataIdName = DataIdNames[selectBoxIndex];
      var requestUrl = RequestUrls[selectBoxIndex+1];

      if (selected) {
        ajaxData.url=requestUrl;
        ajaxData.data=new Object();
        ajaxData.data[dataIdName]=changedOptionVal;
        ajaxData.data.sid=new Date().getTime();
        ajaxData.async=false;
        jQuery.ajax(ajaxData).done(function(response) {
          //alert("changeSelections response " + response);
          if (response != null && response.aaData != null) {
            $(response.aaData).each(function(index, data) {
              var alreadyPresent=false;
              var key = data[0];
              var value = '';
              $('#' + nextSelectBoxName + ' > option').each(function(index, value) {
                var optionVal = $(value).val();
                if (!alreadyPresent && optionVal == key) {
                  alreadyPresent=true;
                }
              });
              if (!alreadyPresent) {
                if (nextSelectBoxName == 'AssignedRegions') {
                  value = data[1];
                } else if (nextSelectBoxName == 'AssignedBranches') {
                  value = data[2] + ', ' + data[3];
                } else if (nextSelectBoxName == 'AssignedSchools') {
                  value = data[9] + ', ' + data[2];
                } else if (nextSelectBoxName == 'AssignedClasses') {
                  value = data[2] + ', ' + data[4] + ', ' + data[6];
                }
                var option = $('<option>');
                option.text(key + ', ' + value);
                option.val(key);
                option.attr('data-' + dataIdName, changedOptionVal);
                $('#' + nextSelectBoxName).append(option);
                //$('#' + nextSelectBoxName).val(key); // if you want it to be automatically selected
                $('#' + nextSelectBoxName).trigger("chosen:updated");
                var newParams=new Object();
                newParams.selected=key;
                //changeSelections(nextSelectBoxName, event, newParams);
              }
            });
          }
        });
      } else {
        $('#' + nextSelectBoxName + ' > option').each(function(index, value) {
          var optionVal = $(value).val();
          var optionDataVal = $(value).attr('data-' + dataIdName);
          if (optionDataVal == changedOptionVal) {
            $(value).remove();
            var newParams=new Object();
            newParams.deselected=optionVal;
            changeSelections(nextSelectBoxName, event, newParams);
          }
        });
        $('#' + nextSelectBoxName).trigger("chosen:updated");
      }
    }
  }

  function getSelectBoxIndex(selBoxName) {
    for (var index=0; index<Assignments.length; index++) {
      if (selBoxName == Assignments[index]) {
        return index;
      }
    }
    return -1;
  }

  function populateAttendanceProfile() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/attendance/jsonGetAttendanceProfile.htm",
      data: {
        AttendanceProfileId: $('#AttendanceProfileId').val(),
        AcademicYearName: $("#EffectiveAcademicYear option:selected").val()
      },
      context: this
    }).done(function(result) {
      if (result != null && result.AttendanceProfile != null && result.AttendanceProfile != 'undefined') {
        //enableActionButtons(false);
        var AttendanceProfile = result.AttendanceProfile;
        if (AttendanceProfile.YearAttendance != null && AttendanceProfile.YearAttendance != 'undefined') {
          fillProfileAttendance(AttendanceProfile.YearAttendance);
          if (AttendanceProfile.AttendanceProfileId != 0) {
            $('#EffectiveAcademicYear').prop('disabled', true).trigger("chosen:updated");

            Assignments.forEach(function(Assignment) {
              setAssigned(AttendanceProfile[Assignment], Assignment);
              //alert("Assignment ID " + JSON.stringify(Assignment[Assignment.IDName]));
            });

            //setAssigned(AttendanceProfile.AssignedStates);
            //setAssigned(AttendanceProfile.AssignedRegions);
            //setAssigned(AttendanceProfile.AssignedBranches);
            //setAssigned(AttendanceProfile.AssignedSchools);
            //setAssigned(AttendanceProfile.AssignedClasses);
          }
        }
      }
    });
  }

  function setAssigned(Assignments, AssignmentName) {
    if (Assignments != null && Assignments != 'undefined' && Assignments.length != 0) {
      //alert(JSON.stringify(Assignments));
      Assignments.forEach(function(Assignment) {
        var AssignmentID = Assignment[Assignment.IDName];
        $('#' + AssignmentName + ' > option').each(function(index, value) {
          var optionValue = $(value).val();
          if (optionValue == AssignmentID) {
            //alert(AssignmentName + ", AssignmentID = " + AssignmentID);
            $('#' + AssignmentName + ' option[value="' + optionValue + '"]').prop('selected', true);
            $('#' + AssignmentName).trigger("chosen:updated");
            var optionVal = $(value).val();
            var params = new Object();
            params.selected=optionVal;
            changeSelections(AssignmentName, null, params);
          }
        });
      });
    }
  }

  function enableActionButtons(enable) {
    if (enable) {
      $('#create').show();
      $('#update').show();
    } else {
      $('#create').hide();
      $('#update').hide();
    }
  }

  function fillProfileAttendance(YearAttendance) {
    var MonthNames = YearAttendance.MonthNames;
    if (MonthNames != null && MonthNames.length != 0) {
      var headerRow = $('<tr>');
      $("#YearViewCalendar > tbody").empty();
      for (var index=0; index<=MonthNames.length; index++) {
        var MonthName = MonthNames[index];

        if (MonthName == null || MonthName == 'undefined') {
         continue;
        }
        var MonthAttendance = YearAttendance[MonthName];
        if (MonthAttendance != null && MonthAttendance != 'undefined') {

          var monthRow = $('<tr>');
          var monthCell = $('<td class="full-calendar-header">') ;
          monthCell.append('<input type="hidden" name="MonthAttendanceId" value="' + MonthAttendance.MonthAttendanceId + '" />');
          monthCell.append('<input type="hidden" name="MonthNumber" value="' + MonthAttendance.Month.Number + '" />');
          monthCell.append('<input type="hidden" name="Year" value="' + MonthAttendance.Year + '" />');
          monthCell.append('<input type="hidden" name="MonthShortName" value="' + MonthAttendance.Month.ShortName + '" />');
          monthCell.append('<input type="hidden" name="MonthFullName" value="' + MonthAttendance.Month.FullName + '" />');
          monthCell.append(MonthName);
          monthRow.append(monthCell);

          var Dates = MonthAttendance.Dates;
          if (Dates != null && Dates.length != 0) {
            for (var DateIndex=0; DateIndex<Dates.length; DateIndex++) {
              var CurrentDateValue = Dates[DateIndex];
              var CurrentDate = MonthAttendance[CurrentDateValue];
              var td = null;
              if (CurrentDate.AttendanceCode == null) {
                td = $('<td align="center" class="Attendance-UNASSIGNED">');
              } else {
                td = $('<td align="center" class="Attendance-' + CurrentDate.AttendanceCode + '">');
              }
              if (CurrentDate.DaysInDifference == 0) {
                  td.addClass('Attendance-CurrentDate');
              }
              td.append('<input type="hidden" name="AttendanceCode" value="' + CurrentDate.AttendanceCode + '" />');
              if (CurrentDate.AttendanceCode != '<%=AttendanceCode.UNACCOUNTED%>') {
                td.click({cellObj: td}, changeAttendance);
              }
              monthRow.append(td);
            }
          }
          $("#YearViewCalendar tbody").append(monthRow);
        }
      }
      $("#YearViewCalendar").hide();
      $("#YearViewCalendar").show(1000);
    }
  }

  function changeAttendance(event) {
    var cellObj = event.data.cellObj;
    if (cellObj != null && cellObj != 'undefined') {
      var className = null;
      var classIndex = -1;
      for (var index=0; index<AttendanceCodes.length; index++) {
        if ($(cellObj).hasClass('Attendance-' + AttendanceCodes[index])) {
          classIndex=index;
          className='Attendance-' + AttendanceCodes[index];
          break;
        }
      }
      classIndex++;
      if (classIndex == AttendanceCodes.length) {
        classIndex = 0;
      }
      $(cellObj).removeClass(className);
      $(cellObj).addClass('Attendance-' + AttendanceCodes[classIndex]);
      // change attendance code
      $(cellObj).find('input[type="hidden"][name="AttendanceCode"]').val(AttendanceCodes[classIndex]);

      // Attendance code UNACCOUNTED is system determined type and user cannot set it.
      if (AttendanceCodes[classIndex] == '<%=AttendanceCode.UNACCOUNTED%>') {
        changeAttendance(event);
      }
    }
  }

  $('#create').click(function(){
    saveAttendanceProfile('<%=request.getContextPath()%>/attendance/jsonSaveAttendanceProfile.htm', getAttendanceProfileData());
  });
  $('#update').click(function(){
    saveAttendanceProfile('<%=request.getContextPath()%>/attendance/jsonSaveAttendanceProfile.htm', getAttendanceProfileData());
  });
  $('#activate').click(function() {
    var AttendanceProfileData=getAttendanceProfileData();
    AttendanceProfileData.Active='true';
    saveAttendanceProfile('<%=request.getContextPath()%>/attendance/jsonSaveAttendanceProfile.htm', AttendanceProfileData);
  });
  $('#inactivate').click(function() {
    var AttendanceProfileData=getAttendanceProfileData();
    AttendanceProfileData.Active='false';
    saveAttendanceProfile('<%=request.getContextPath()%>/attendance/jsonSaveAttendanceProfile.htm', AttendanceProfileData);
  });

  function getAttendanceProfileData() {
    var AttendanceProfileData = new Object();
    AttendanceProfileData.AttendanceProfileId=$('#AttendanceProfileId').val();
    AttendanceProfileData.Active=$('#Active').val();
    AttendanceProfileData.ProfileName=$('#ProfileName').val();
    AttendanceProfileData.EffectiveAcademicYear=$('#EffectiveAcademicYear').val();

    var YearAttendance = new Array();
    var MonthAttendance = null;
    var MonthData = null;
    var DaysAttendance = null;
    var DayAttendance = null;

    // serialize profile definition
    $("#YearViewCalendar > tbody > tr").each(function(index, row) {
      MonthAttendance = new Object();
      MonthData = new Object();
      DaysAttendance = new Array();

      $(this).find('td').each(function(index, cell) {
        if (index == 0) {
          MonthAttendance.MonthAttendanceId=$(this).find('input[type="hidden"][name="MonthAttendanceId"]').val();
          MonthData.Number=$(this).find('input[type="hidden"][name="MonthNumber"]').val();
          MonthData.Year=$(this).find('input[type="hidden"][name="Year"]').val();
          MonthAttendance.MonthData=MonthData;
          MonthAttendance.DaysAttendance=DaysAttendance;
        } else {
          DayAttendance = new Object();
          DayAttendance.Date=index;
          DayAttendance.AttendanceCode=$(this).find('input[type="hidden"][name="AttendanceCode"]').val();
          DaysAttendance[DaysAttendance.length]=DayAttendance;
        }
      });
      YearAttendance[YearAttendance.length]=MonthAttendance;
    });

    if (!AttendanceProfileData.UseOrganizationWide) {
      // Serialize REGIONS, BRANCHES, SCHOOLS, REGISTERED CLASSES
      for (var index=0; index<Assignments.length; index++) {
        var AssignedTo=Assignments[index];
        var val = $('#'+AssignedTo).val();
        if (val == null) {
          AttendanceProfileData[AssignedTo]=[];
        } else {
          AttendanceProfileData[AssignedTo]=$('#'+AssignedTo).val();
        }
      }
    }
    AttendanceProfileData.YearAttendance=YearAttendance;
    return AttendanceProfileData;
  }

  function saveAttendanceProfile(url, AttendanceProfileData) {
    //alert(JSON.stringify(AttendanceProfileData));

    // send data to server
    jQuery.ajax({
      type: "POST",
      url: url,
      data: {
        AttendanceProfileData: JSON.stringify(AttendanceProfileData),
        sid: new Date().getTime()
      },
      context: this
    }).done(function(result) {
      parseModelReponse(result);
    });
  }

});

</script>

<c:if test="${AttendanceProfile == null}">
<input type="hidden" id="AttendanceProfileId" value="0" />
<input type="hidden" id="EffectiveAcademicYearName" value="" />
<input type="hidden" id="Active" value="false" />
<img src="<%=request.getContextPath()%>/images/icons/draft.png" style="float: right;" width="80px" height="80px" border="0" />
<table width="40%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td width="40%" class="label">Profile Name<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="ProfileName" maxlength="128" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Effective Academic Year<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <select id="EffectiveAcademicYear">
      </select>
    </td>
  </tr>
</table>
</c:if>
<br />
<c:if test="${AttendanceProfile != null}">
<input type="hidden" id="AttendanceProfileId" value="${AttendanceProfile.attendanceProfileId}" />
<input type="hidden" id="EffectiveAcademicYearName" value="${AttendanceProfile.effectiveAcademic.academicYearName}" />
<input type="hidden" id="Active" value="${AttendanceProfile.active}" />
<c:if test="${!AttendanceProfile.active}">
<img src="<%=request.getContextPath()%>/images/icons/draft.png" style="float: right;" width="80px" height="80px" border="0" />
</c:if>

<table width="40%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td width="40%" class="label">Profile Name<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="ProfileName" maxlength="128" value="${AttendanceProfile.profileName}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Effective Academic Year<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <select id="EffectiveAcademicYear">
      </select>
    </td>
  </tr>
</table>
</c:if>

<div id="AttendanceProfilesDetails">
  <ul>
    <li><a id="ProfileDefinitionTab" href="#ProfileDefinition">Profile Definition</a></li>
    <li><a id="ProfileAssignmentsTab" href="#ProfileAssignments">Profile Assignments</a></li>
  </ul>
  <div id="ProfileDefinition">
    <fieldset>
      <table id="AttendanceToolsTable" class="userFormTable" cellpadding="0" cellspacing="5" width="100%" style="font-size: 0.7em;" align="center">
        <tr>
          <td>
            Change All 
            <select id="ChangeAttendanceFrom"></select>
            To
            <select id="ChangeAttendanceTo"></select>
            <input type="button" id="ChangeBulkAttendace" class="active" value="Change" />
          </td>
        </tr>
      </table>
    </fieldset>

    <table id="YearViewCalendar" cellpadding="0" cellspacing="2" width="100%">
      <thead>
      </thead>
      <tbody>
      </tbody>
    </table>

    <fieldset>
      <table cellpadding="0" cellspacing="2" width="100%" style="font-size: 0.6em;">
        <tr>
          <c:forEach var="AttendanceCode" items="${AttendanceCodes}">
            <td width="30px" class="Attendance-${AttendanceCode}">&nbsp;</td>
            <td>${AttendanceCode}</td>
          </c:forEach>
        </tr>
      </table>
    </fieldset>
    <fieldset>
      <table cellpadding="0" cellspacing="2" width="100%" style="font-size: 0.6em;" align="center" class="userFormTable">
        <tr>
          <td align="center">
            <c:if test="${AttendanceProfile == null}">
              <input type="button" id="create" class="active" value='<spring:message code="common.create"/>' />
            </c:if>
            <c:if test="${AttendanceProfile != null}">
              <input type="button" id="update" class="active" value='<spring:message code="common.update"/>' />
              <c:if test="${AttendanceProfile.active}">
              <input type="button" id="inactivate" class="active" value='Inactivate' />
              </c:if>
              <c:if test="${!AttendanceProfile.active}">
              <input type="button" id="activate" class="active" value='Activate' />
              </c:if>
            </c:if>
          </td>
        </tr>
      </table>
    </fieldset>
  </div>

  <div id="ProfileAssignments">
    <table id="ProfileAssignmentsTable" class="userFormTable" cellpadding="10" cellspacing="0" width="100%" style="font-size: 0.7em;" align="center">
      <tr>
        <td width="50%" valign="top">
          <span class="CircleText-GREEN">1</span>
          <b>
            Assign To States [
              <a href="#" data-dest="AssignedStates" class="formLink AddAllAssignments">Add All</a>,
              <a href="#" data-dest="AssignedStates" class="formLink ClearAllAssignments">ClearAll</a>
            ]
          </b>
          <br /><br />
          <select id="AssignedStates" multiple class="chosen-select">
          </select>
        </td>
        <td width="50%" valign="top">
          <span class="CircleText-GREEN">4</span>
          <b>
            Assign To Schools [
              <a href="#" data-dest="AssignedSchools" class="formLink AddAllAssignments">Add All</a>,
              <a href="#" data-dest="AssignedSchools" class="formLink ClearAllAssignments">ClearAll</a>
            ]
          </b>
          <br /><br />
          <select id="AssignedSchools" multiple class="chosen-select">
          </select>
        </td>
      </tr>
      <tr>
        <td width="50%" valign="top">
          <span class="CircleText-GREEN">2</span>
          <b>
            Assign To Regions [
              <a href="#" data-dest="AssignedRegions" class="formLink AddAllAssignments">Add All</a>,
              <a href="#" data-dest="AssignedRegions" class="formLink ClearAllAssignments">ClearAll</a>
            ]
          </b>
          <br /><br />
          <select id="AssignedRegions" multiple class="chosen-select">
          </select>
        </td>
        <td rowspan="2" valign="top">
          <span class="CircleText-GREEN">5</span>
          <b>
            Assign To Classes [
              <a href="#" data-dest="AssignedClasses" class="formLink AddAllAssignments">Add All</a>,
              <a href="#" data-dest="AssignedClasses" class="formLink ClearAllAssignments">ClearAll</a>
            ]
          </b>
          <br /><br />
          <select id="AssignedClasses" multiple class="chosen-select">
          </select>
        </td>
      </tr>
      <tr>
        <td width="50%" valign="top">
          <span class="CircleText-GREEN">3</span>
          <b>
            Assign To Branches [
              <a href="#" data-dest="AssignedBranches" class="formLink AddAllAssignments">Add All</a>,
              <a href="#" data-dest="AssignedBranches" class="formLink ClearAllAssignments">ClearAll</a>
            ]
          </b>
          <br /><br />
          <select id="AssignedBranches" multiple class="chosen-select">
          </select>
        </td>
      </tr>
    </table>
  </div>
</div>
