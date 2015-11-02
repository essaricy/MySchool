<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="com.myschool.attendance.dto.AttendanceCode"%>

<style>
.ui-tabs .ui-tabs-nav li a {
    font-size: 0.6em !important;
}
.full-calendar-header {
    border: 1px solid #aaaaaa;
    background: #D4D4D4 50% 50% repeat-x;
    color: #222222;
    font-weight: bold;
    padding-left: 5px;
    padding-right: 5px;
    padding-top: 3px;
    padding-bottom: 3px;
    font-family: Verdana, Arial, sans-serif;
    font-size: 0.5em;
    min-width: 20px;
    border-collapse: collapse;
}
.Attendance-XX {
    background-color: #D4D4D4;
    background-image: url('<%=request.getContextPath()%>/images/icons/mesh.png');
    opacity: 0.4;
    font-weight: regular;
    font-size: 10px;
}
.Attendance-WF {
    background-color: #26A65B;
    cursor: pointer;
    font-weight: regular;
    font-size: 10px;
}
.Attendance-WH {
    background-color: #87D37C;
    cursor: pointer;
    font-weight: regular;
    font-size: 10px;
}
.Attendance-NF {
    background-color: #BDC3C7;
    cursor: pointer;
    font-weight: regular;
    font-size: 10px;
}
.Attendance-SF {
    background-color: #F39C12;
    cursor: pointer;
    font-weight: regular;
    font-size: 10px;
}
.Attendance-IF {
    background-color: #E04006;
    cursor: pointer;
    font-weight: regular;
    font-size: 10px;
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
</style>

<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/widgets/jquery-ui-1.10.2/ui/jquery.ui.tabs.js"></script>
<script type="text/javascript">

$(document).ready(function() {
  var AttendanceCodes=new Array();
  AttendanceCodes[AttendanceCodes.length]='WF';
  AttendanceCodes[AttendanceCodes.length]='WH';
  AttendanceCodes[AttendanceCodes.length]='NF';
  AttendanceCodes[AttendanceCodes.length]='SF';
  AttendanceCodes[AttendanceCodes.length]='IF';

  // Populate Academic Years
  $(this).lazySelect({
    id: "EffectiveAcademicYear",
    url: '<%=request.getContextPath()%>/academic/jsonList.htm',
    valueIndices: [0],
    selectOnCode: $('#EffectiveAcademicYearName').val()
  });
  $('#EffectiveAcademicYear').change(function() {
    populateAttendanceProfile();
  });

  // Populate Assigned Schools
  $(this).lazySelect({
    id: "AssignedSchools",
    url: '<%=request.getContextPath()%>/school/jsonList.htm',
    codeIndex: 0,
    valueIndices: [10, 9, 2],
    valueDelimiter: '/'
  });
  // Populate assigned classes
  $(this).lazySelect({
    id: "AssignedClasses",
    url: '<%=request.getContextPath()%>/class/jsonListRegistered.htm',
    codeIndex: 0,
    valueIndices: [16, 10, 8, 2, 4, 6],
    valueDelimiter: '/'
  });

  // Add All and Clear All functionality
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
      }
    });
    $('#' + dataDest).trigger("chosen:updated");
  });
  $('.ClearAllAssignments').click(function() {
    var dataDest = $(this).attr('data-dest');
    $('#' + dataDest + ' > option').each(function(index, value) {
      var selected = $(value).is(':selected');
      var selectedValue = $(value).val();
      if (selected) {
        $('#' + dataDest + ' option[value="' + selectedValue + '"]').prop('selected', false);
        var optionVal = $(value).val();
        var params = new Object();
        params.deselected=optionVal;
      }
    });
    $('#' + dataDest).trigger("chosen:updated");
  });

  // Change attendance tool implementation
  $('#ChangeAttendace').click(function() {
    var AttendanceDayTerm = $('#AttendanceDayTerm').val();
    var AttendanceDayInWeekNumber = $('#AttendanceDayInWeekNumber').val();
    var AttendanceDayValue = $('#AttendanceDayValue').val();
    $("#YearViewCalendar > tbody > tr").each(function(index, monthRow) {
      var termNumber = 0;
      $(monthRow).find("td").each(function(index, dayCell) {
        var DayNumberInWeek=$(dayCell).find('input[type="hidden"][name="DayNumberInWeek"]').val();

        if (DayNumberInWeek != null && typeof (DayNumberInWeek) != 'undefined') {
          if (AttendanceDayInWeekNumber == DayNumberInWeek) {
            termNumber++;
            if (AttendanceDayTerm == 0 || AttendanceDayTerm == termNumber) {
              $(dayCell).removeClass();
              $(dayCell).addClass('Attendance-' + AttendanceDayValue);
              $(dayCell).find('input[type="hidden"][name="Reference"]').val(AttendanceDayValue);
            }
          }
        }
      });
    });
  });

  function populateAttendanceProfile() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/attendance/jsonGetAttendanceProfile.htm",
      data: {
        AttendanceProfileId: $('#AttendanceProfileId').val(),
        AcademicYearName: $('#EffectiveAcademicYear').val()
      },
      context: this
    }).done(function(result) {
      if (result != null && result.AttendanceProfile != null) {
        var AttendanceProfile = result.AttendanceProfile;
        var AttendanceMonths = AttendanceProfile.AttendanceMonths;
        var EffectiveAcademic=AttendanceProfile.EffectiveAcademic;
        var AssignedSchools=AttendanceProfile.AssignedSchools;
        var AssignedClasses=AttendanceProfile.AssignedClasses;
        if (AttendanceMonths != null && EffectiveAcademic != null)  {
          populateAttendanceMonths(AttendanceMonths);
          populateAssignements(AssignedSchools, AssignedClasses);
        }
      }
    });
  }

  function populateAttendanceMonths(AttendanceMonths) {
    var headerRow = $('<tr>');
    $("#YearViewCalendar > thead").html("");
    $("#YearViewCalendar thead").append(headerRow);
    headerRow.append('<th class="full-calendar-header">Month/Dates</th>');
    for (var currentDate=1; currentDate<=31; currentDate++) {
      headerRow.append('<th class="full-calendar-header">' + currentDate + '</th>');
    }
    $("#YearViewCalendar").hide();
    $("#YearViewCalendar > tbody").empty();
    for (var monthIndex=0; monthIndex<AttendanceMonths.length; monthIndex++) {
      var AttendanceMonth = AttendanceMonths[monthIndex];
      var monthRow = $('<tr>');
      var monthCell = $('<td class="full-calendar-header">') ;
      monthCell.append('<input type="hidden" name="AttendanceMonthId" value="' + AttendanceMonth.AttendanceMonthId + '" />');
      monthCell.append('<input type="hidden" name="MonthNumber" value="' + AttendanceMonth.MonthNumber + '" />');
      monthCell.append('<input type="hidden" name="MonthName" value="' + AttendanceMonth.MonthName + '" />');
      monthCell.append('<input type="hidden" name="Year" value="' + AttendanceMonth.Year + '" />');
      monthCell.append(AttendanceMonth.MonthName + ' - ' + AttendanceMonth.Year);
      monthRow.append(monthCell);
      var AttendanceDays=AttendanceMonth['AttendanceDays'];
      for (var dateIndex=0; dateIndex<AttendanceDays.length; dateIndex++) {
        var AttendanceDay=AttendanceDays[dateIndex];
        var dateCell = $('<td align="center">');
        dateCell.append('<input type="hidden" name="Date" value="' + dateIndex + '" />');
        if (AttendanceDay == null) {
          dateCell.addClass('Attendance-XX');
          dateCell.append('<input type="hidden" name="Reference" value="XX" />');
        } else if (AttendanceDay.Reference == null) {
          dateCell.append('<input type="hidden" name="DayNumberInWeek" value="' + AttendanceDay.DayNumberInWeek + '" />');
          <c:if test="${AttendanceProfile == null}">
            dateCell.addClass('Attendance-WF');
            dateCell.append('<input type="hidden" name="Reference" value="WF" />');
            $(dateCell).click(function() {
              changeAttendanceCode (this);
            });
          </c:if>
          <c:if test="${AttendanceProfile != null}">
            dateCell.addClass('Attendance-XX');
            dateCell.append('<input type="hidden" name="Reference" value="XX" />');
          </c:if>
        } else {
          dateCell.append('<input type="hidden" name="DayNumberInWeek" value="' + AttendanceDay.DayNumberInWeek + '" />');
          dateCell.append('<input type="hidden" name="Reference" value="' + AttendanceDay.Reference + '" />');
          dateCell.addClass('Attendance-' + AttendanceDay.Reference);
          $(dateCell).click(function() {
            changeAttendanceCode (dateCell);
          });
        }
        monthRow.append(dateCell);
      }
      $("#YearViewCalendar tbody").append(monthRow);
    }
    $("#YearViewCalendar").show(1000);
  }

  function changeAttendanceCode(dateCell) {
    for (var index=0; index<AttendanceCodes.length; index++) {
      var AttendanceCode=AttendanceCodes[index];
      if ($(dateCell).hasClass('Attendance-' + AttendanceCode)) {
        $(dateCell).removeClass('Attendance-' + AttendanceCode);
        var nextIndex=(index==AttendanceCodes.length-1)?0:index+1;
        $(dateCell).addClass('Attendance-' + AttendanceCodes[nextIndex]);
        $(dateCell).find('input[type="hidden"][name="Reference"]').val(AttendanceCodes[nextIndex]);
        break;
      }
    }
  }

  function populateAssignements(AssignedSchools, AssignedClasses) {
    if (AssignedSchools != null && AssignedSchools.length != 0) {
      var SelectedSchoolIDs=new Array();
      for (var index=0; index<AssignedSchools.length; index++) {
        var AssignedSchool=AssignedSchools[index];
        SelectedSchoolIDs[SelectedSchoolIDs.length]=AssignedSchool.SchoolId;
      }
      $('#AssignedSchools').val(SelectedSchoolIDs).trigger('chosen:updated');
    }
    if (AssignedClasses != null && AssignedClasses.length != 0) {
      var SelectedClassIDs=new Array();
      for (var index=0; index<AssignedClasses.length; index++) {
        var AssignedClass=AssignedClasses[index];
        SelectedClassIDs[SelectedClassIDs.length]=AssignedClass.ClassId;
      }
      $('#AssignedClasses').val(SelectedClassIDs).trigger('chosen:updated');
    }
  }

  $('#CreateAttendanceProfile').click(function() {
    saveAttendanceProfile('<%=request.getContextPath()%>/attendance/doCreate.htm');
  });
  $('#UpdateAttendanceProfile').click(function() {
    saveAttendanceProfile('<%=request.getContextPath()%>/attendance/doUpdate.htm');
  });

  // Create Tabs
  $("#AttendanceProfilesDetails").tabs();
  $("#AttendanceProfilesDetails").tabs( "option", "active", 0);
  $('#AttendanceDayTerm').chosen({width: '200px'});
  $('#AttendanceDayInWeekNumber').chosen({width: '200px'});
  $('#AttendanceDayValue').chosen({width: '200px'});
  $('#AssignedSchools').chosen({width: '100%'});
  $('#AssignedClasses').chosen({width: '100%'});
  $('#EffectiveAcademicYear').change();

  function getAttendanceProfileData() {
    if ($.trim($('#ProfileName').val()) == '') {
      error('Please enter a name for the profile.');
      return null;
    }
    if ($('#AssignedSchools').val() == null && $('#AssignedClasses').val() == null) {
      error('Please assign the profile at least to one School or Class.');
      return null;
    }

    var AttendanceProfileData = new Object();
    AttendanceProfileData.AttendanceProfileId=$('#AttendanceProfileId').val();
    AttendanceProfileData.AttendanceProfileName=$('#ProfileName').val();

    var EffectiveAcademic = new Object();
    EffectiveAcademic.AcademicYearName=$('#EffectiveAcademicYear').val();
    AttendanceProfileData.EffectiveAcademic=EffectiveAcademic;

    var AttendanceMonths = new Array();
    var AttendanceMonth = null;
    var AttendanceDays = null;
    var AttendanceDay = null;

    // serialize profile definition
    $("#YearViewCalendar > tbody > tr").each(function(index, row) {
      AttendanceMonth = new Object();
      AttendanceDays = new Array();

      $(this).find('td').each(function(index, cell) {
        if (index == 0) {
          AttendanceMonth.AttendanceMonthId=$(this).find('input[type="hidden"][name="AttendanceMonthId"]').val();
          AttendanceMonth.MonthNumber=$(this).find('input[type="hidden"][name="MonthNumber"]').val();
          AttendanceMonth.Year=$(this).find('input[type="hidden"][name="Year"]').val();
          AttendanceMonth.AttendanceDays=AttendanceDays;
        } else {
          AttendanceDay = new Object();
          AttendanceDay.Date=index;
          var attendanceClass=$(this).attr('class');
          var attendanceCode=$(this).find('input[type="hidden"][name="Reference"]').val();
          if (attendanceCode != 'XX') {
            AttendanceDay.Reference=attendanceCode;
          }
          AttendanceDays[AttendanceDays.length]=AttendanceDay;
        }
      });
      AttendanceMonths[AttendanceMonths.length]=AttendanceMonth;
    });
    AttendanceProfileData.AttendanceMonths=AttendanceMonths;

    // Serialize Profile assignments
    AttendanceProfileData.AssignedSchools=$('#AssignedSchools').val();
    AttendanceProfileData.AssignedClasses=$('#AssignedClasses').val();
    return AttendanceProfileData;
  }

  function saveAttendanceProfile(url) {
    var AttendanceProfile=getAttendanceProfileData();
    if (AttendanceProfile == null) {
        return;
    }

    jQuery.ajax({
      type: "POST",
      url: url,
      data: {
        AttendanceProfile: JSON.stringify(AttendanceProfile),
        sid: new Date().getTime()
      },
      context: this
    }).done(function(result) {
      parseModelResponse(result);
    });
  }
});

</script>
<c:if test="${AttendanceProfile == null}">
  <input type="hidden" id="AttendanceProfileId" value="" />
  <input type="hidden" id="EffectiveAcademicYearName" value="" />
  <table width="100%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
    <tr>
      <td class="label">Profile Name<label class="mandatory">*</label></td>
      <td class="value"><input type="text" id="ProfileName" maxlength="128" /></td>
      <td class="label">Effective Academic Year<label class="mandatory">*</label></td>
      <td class="value">
        <select id="EffectiveAcademicYear">
        </select>
      </td>
      <td align="right">
        <input type="button" id="CreateAttendanceProfile" class="active" value='<spring:message code="common.create"/>' />
      </td>
    </tr>
  </table>
</c:if>
<c:if test="${AttendanceProfile != null}">
  <input type="hidden" id="AttendanceProfileId" value="${AttendanceProfile.profileId}" />
  <input type="hidden" id="EffectiveAcademicYearName" value="${AttendanceProfile.effectiveAcademic.academicYearName}" />
  <table width="100%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
    <tr>
      <td class="label">Profile Name<label class="mandatory">*</label></td>
      <td class="value"><input type="text" id="ProfileName" value="${AttendanceProfile.profileName}" maxlength="128" /></td>
      <td class="label">Effective Academic Year<label class="mandatory">*</label></td>
      <td class="value">
        <select id="EffectiveAcademicYear">
        </select>
      </td>
      <td align="right">
        <input type="button" id="UpdateAttendanceProfile" class="active" value='<spring:message code="common.update"/>' />
      </td>
    </tr>
  </table>
</c:if>

<div id="AttendanceProfilesDetails">
  <ul>
    <li><a id="AssignAttendanceCodesTab" href="#AssignAttendanceCodes">Assign Attendance Codes</a></li>
    <li><a id="AssignToSchoolsTab" href="#AssignToSchools">Assign To Schools [Employees]</a></li>
    <li><a id="AssignToClassesTab" href="#AssignToClasses">Assign To Classes [Students]</a></li>
  </ul>
  <div id="AssignAttendanceCodes">
    <span class="CircleText-GREEN">1</span>
    <b style="font-size: 0.7em;">Assign Attendance Code to each day of the Academic Year</b>
    <br />
    <br />

    <table id="AttendanceToolsTable" class="userFormTable" cellpadding="0" cellspacing="5" width="100%" style="font-size: 0.7em;" align="center">
      <tr>
        <td>
          Turn 
          <select id="AttendanceDayTerm">
            <option value="0">All</option>
            <option value="1">1st</option>
            <option value="2">2nd</option>
            <option value="3">3rd</option>
            <option value="4">4th</option>
            <option value="5">5th</option>
          </select>
          <select id="AttendanceDayInWeekNumber">
            <option value="1">Sundays</option>
            <option value="2">Mondays</option>
            <option value="3">Tuesdays</option>
            <option value="4">Wednessdays</option>
            <option value="5">Thursdays</option>
            <option value="6">Fridays</option>
            <option value="7">Saturdays</option>
          </select>
          to
          <select id="AttendanceDayValue">
            <option value="NF">Non-working Day</option>
            <option value="WH">Working Halfday</option>
          </select>
          <input type="button" id="ChangeAttendace" class="active" value="Change" />
        </td>
      </tr>
    </table>
    <br />

    <table id="YearViewCalendar" cellpadding="0" cellspacing="2" width="100%" border="0">
      <thead>
      </thead>
      <tbody>
      </tbody>
    </table>

    <table id="LegendsTable" cellpadding="0" cellspacing="2" width="100%" style="font-size: 0.6em;">
      <thead>
      </thead>
      <tbody>
        <tr>
          <td width="30px" class="Attendance-WF">&nbsp;</td>
          <td>WF - Working Day (Full)</td>
          <td width="30px" class="Attendance-WH">&nbsp;</td>
          <td>WH - Working Halfday</td>
          <td width="30px" class="Attendance-NF">&nbsp;</td>
          <td>NF - Non-working Day (Full)</td>
          <td width="30px" class="Attendance-SF">&nbsp;</td>
          <td>SF - Statutory Holiday (Full)</td>
          <td width="30px" class="Attendance-IF">&nbsp;</td>
          <td>IF - An Impromptu Holiday (Full)</td>
        </tr>
      </tbody>
    </table>
    <hr />
  </div>

  <div id="AssignToSchools">
    <table class="userFormTable" cellpadding="10" cellspacing="0" width="100%" style="font-size: 0.7em;" align="center">
      <tr>
        <td width="80%" valign="top">
          <span class="CircleText-GREEN">2</span>
          <b style="font-size: 1em;">Assign this profile to the all the employees who belong to the following School(s)</b>
        </td>
        <td width="20%" valign="top" align="right">
          <input type="button" value="Add All" data-dest="AssignedSchools" class="active AddAllAssignments" />
          <input type="button" value="Clear All" data-dest="AssignedSchools" class="active ClearAllAssignments" />
        </td>
      </tr>
      <tr>
        <td colspan="2">
          <select id="AssignedSchools" multiple class="chosen-select">
          </select>
        </td>
      </tr>
    </table>
  </div>

  <div id="AssignToClasses">
    <table class="userFormTable" cellpadding="10" cellspacing="0" width="100%" style="font-size: 0.7em;" align="center">
      <tr>
        <td width="80%" valign="top">
          <span class="CircleText-GREEN">3</span>
          <b style="font-size: 1em;">Assign this profile to the all the students who belong to the following Class(es)</b>
        </td>
        <td width="20%" valign="top" align="right">
          <input type="button" value="Add All" data-dest="AssignedClasses" class="active AddAllAssignments" />
          <input type="button" value="Clear All" data-dest="AssignedClasses" class="active ClearAllAssignments" />
        </td>
      </tr>
      <tr>
        <td colspan="2">
          <select id="AssignedClasses" multiple class="chosen-select">
          </select>
        </td>
      </tr>
    </table>
  </div>
</div>
