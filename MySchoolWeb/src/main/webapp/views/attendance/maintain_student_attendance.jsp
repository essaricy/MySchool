<style>
#studentAttendanceEntryTable {
    box-shadow: 5px 5px 5px #888;
  border: 2px solid #888888;
}

#studentAttendanceEntryTable tbody .refAttendanceHeaderRowClass {
    background-color: gray;
    color: white;
    font-weight:regular;
    font-size: 12px;
}

#studentAttendanceEntryTable tbody .dateHeaderRowClass{
    background-color: #1582C4;
    color: white;
    font-weight: bold;
    font-size: 10px;
}

#studentAttendanceEntryTable thead .dayHeaderRowClass {
    background-color: #1582C4;
    color: white;
    font-weight: normal;
    font-size: 10px;
}
#studentAttendanceEntryTable thead,tbody tr .GeneralHoliday {
    background-color: #F62817;
    color: white;
    font-weight: bold;
    font-size: 10px;
}
#studentAttendanceEntryTable thead,tbody tr .DeclaredHoliday {
    background-color: #488AC7;
    color: white;
    font-weight: bold;
    font-size: 10px;
}

#studentAttendanceEntryTable thead,tbody tr .Today {
    background-color: #4CC552;
    font-weight: bold;
    font-size: 12px;
}
#studentAttendanceEntryTable thead,tbody tr .FirstColumn {
    color: black;
    font-weight: bold;
    font-size: 10px;
}

</style>

<script>
$(document).ready(function() {
    var currentDate = getCurrentDate(true);
    if (currentDate == null) {
        $('#studentAttendanceEntryTable').hide();
        attendError('Unable to load the data. Please try again later.');
    } else {
        var classId = $('#classId').val();
        if (classId != '' && typeof(classId) != 'undefined') {
            displayAttendanceData(currentDate.getMonth() + 1, currentDate.getFullYear());
        }
    }

    $('#PreviousYear').click(function () {
        var currentMonth = parseInt($('#currentMonth').val());
        var currentYear = parseInt($('#currentYear').val());
        currentYear--;
        displayAttendanceData(currentMonth, currentYear);
    });

    $('#PreviousMonth').click(function () {
        var currentMonth = parseInt($('#currentMonth').val());
        var currentYear = parseInt($('#currentYear').val());
        if (currentMonth == 1) {
            currentMonth = 12;
            currentYear--;
        } else {
            currentMonth--;
        }
        displayAttendanceData(currentMonth, currentYear);
    });

    $('#NextMonth').click(function () {
        var currentMonth = parseInt($('#currentMonth').val());
        var currentYear = parseInt($('#currentYear').val());
        if (currentMonth == 12) {
            currentMonth = 1;
            currentYear++;
        } else {
            currentMonth++;
        }
        displayAttendanceData(currentMonth, currentYear);
    });

    $('#NextYear').click(function () {
        var currentMonth = parseInt($('#currentMonth').val());
        var currentYear = parseInt($('#currentYear').val());
        currentYear++;
        displayAttendanceData(currentMonth, currentYear);
    });

    $('#SaveAttendance').click(function () {
        var numberOfDays = parseInt($('#numberOfDays').val());
        var numberOfStudents = parseInt($('#numberOfStudents').val());

        var attendanceData = new Object();
        // Prepare attendance metadata.
        var attendanceMetaData = new Object();
        attendanceMetaData.year = $('#currentYear').val();
        attendanceMetaData.month = $('#currentMonth').val();
        attendanceMetaData.classId = $('#classId').val();
        attendanceMetaData.numberOfDays = $('#numberOfDays').val();
        attendanceMetaData.numberOfStudents = $('#numberOfStudents').val();
        // Prepare reference attendace data
        var referenceAttendanceData = new Array();
        for (var index=1; index<=numberOfDays; index++) {
            var reference_attendance_chkbox = $('input[type="checkbox"][name="reference_attendance_chkbox_' + index + '"]')[0];
            referenceAttendanceData[referenceAttendanceData.length] = reference_attendance_chkbox.checked;
        }
        // Prepare students attendance data.
        var studentsAttendanceData = new Object();
        for (var index=0; index<numberOfStudents; index++) {
            var studentAttendanceData = new Array();
            var studentId = $('#student_' + index).val();

            for (var dateIndex=1; dateIndex<=numberOfDays; dateIndex++) {
                var student_attendance_chkbox = $('input[type="checkbox"][name="student_attendance_chkbox_' + dateIndex + '"]')[index];
                studentAttendanceData[studentAttendanceData.length] = student_attendance_chkbox.checked;
            }
            studentsAttendanceData[''+studentId] = studentAttendanceData;
        }

        attendanceData.attendanceMetaData = attendanceMetaData;
        attendanceData.referenceAttendance = referenceAttendanceData;
        attendanceData.studentsAttendanceData = studentsAttendanceData;

        $.ajax({
            url: "<%=request.getContextPath()%>/attendance/updateStudentAttendance.htm",
            data: {
                attendanceData: JSON.stringify(attendanceData),
                sid: new Date().getTime()
            },
            context: document.body,
            success: function(result) {
                handleServerResponseOnModal(result);
            }
        });
    });

    $('#FreezeAttendance').click(function () {
        info_ac('Freeze Attendance');
    });

    function getCurrentDate(setValues) {
        var currentDate = null;
        $.ajax({
            url: "<%=request.getContextPath()%>/attendance/getCurrent.htm",
            async: false,
            dataType: "json",
            data: {
                sid: new Date().getTime()
            },
            success: function(data) {
                if (data != null && data.currentDate != null) {
                    var dateNumber = parseInt(data.currentDate.substring(0, 2));
                    var monthNumber = parseInt(data.currentDate.substring(3, 5)) - 1;
                    var yearNumber = parseInt(data.currentDate.substring(6, 10));
                    if (setValues) {
                        $('#currentMonth').val(monthNumber + 1);
                        $('#currentYear').val(yearNumber);
                    }
                    currentDate = new Date(yearNumber, monthNumber, dateNumber);
                }
            }
        });
        return currentDate;
    }

    function displayAttendanceData(month, year) {
        var classId = $('#classId').val();
        displayActionButtons(false);

        $.ajax({
            url: "<%=request.getContextPath()%>/attendance/getReferenceAttendance.htm",
            dataType: "json",
            async: false,
            data: {
                classId: classId,
                month: month,
                year: year,
                output: "JSON",
                sid: new Date().getTime()
            },
            success: function(data) {
                if (data != null && data.attendanceData != null) {
                    $('#currentMonth').val(month);
                    $('#currentYear').val(year);

                    fillAttendanceSummaryTable(data.attendanceData);
                    if (fillStudentAttendanceEntryTable(data.attendanceData.days)) {
                        displayActionButtons(true);
                    } else {
                        displayActionButtons(false);
                        attendError('Unable to load student attendances. Please try again later.');
                        $('#studentAttendanceEntryTable').hide();
                    }
                }
            }
        });
    }

    function fillAttendanceSummaryTable(attendanceData) {
        var tbody = $('#studentAttendanceSummaryTable tbody');
        tbody.empty();

        createHeaderRow(tbody, "Year", $('#currentYear').val());
        createHeaderRow(tbody, "Month", attendanceData.FullName);
        createHeaderRow(tbody, "Number Of General Holidays", attendanceData.NumberOfGeneralHolidays);
        createHeaderRow(tbody, "Number Of Declared Holidays", attendanceData.NumberOfDeclaredHolidays);
        createHeaderRow(tbody, "Number Of Holidays", attendanceData.NumberOfHolidays);
    }

    function createHeaderRow(parentNode, label, value) {
        var dataRow = $("<tr>");
        $("<td>").text(label).appendTo(dataRow);
        $("<td>").text('').appendTo(dataRow);
        $("<td>").text(value).appendTo(dataRow);
        dataRow.appendTo(parentNode);
    }

    function fillStudentAttendanceEntryTable(daysData) {
        var thead = $('#studentAttendanceEntryTable thead');
        var tbody = $('#studentAttendanceEntryTable tbody');
        thead.empty();
        tbody.empty();

        var alteredDaysData = new Array();
        var firstRow = new Object();

        firstRow.Date='Day';
        firstRow.DayShortName='Date';
        firstRow.Reference_Attendance = 'Reference Attendance';

        alteredDaysData[0] = firstRow;
        $(daysData).each(function(index, dayData) {
            alteredDaysData[alteredDaysData.length] = dayData;
        });

        $('#numberOfDays').val(daysData.length);
        fillRow(thead, alteredDaysData, 'dayHeaderRowClass', 'Date', true, 'label');
        fillRow(tbody, alteredDaysData, 'dateHeaderRowClass', 'DayShortName', false, 'label');
        fillRow(tbody, alteredDaysData, 'refAttendanceHeaderRowClass', 'Reference_Attendance', false, 'checkbox');

        return displayClassAttendance();
    }

    function fillRow(thead, daysData, rowClass, fieldName, isHeaderRow, fieldElement) {
        var dataRow = $("<tr>");
        var dataCell = null;

        dataRow.addClass(rowClass);
        dataRow.appendTo(thead);

        $(daysData).each(function(index, dayData) {
            if (isHeaderRow) {
                dataCell = $("<th>");
            } else {
                dataCell = $("<td>");
            }
            if (fieldElement == 'label') {
                dataCell = getAttendanceEntryCell(dataCell, dayData, true, false);
                dataCell.text(dayData[''+fieldName]);
            } else if (fieldElement == 'checkbox') {
                if (index == 0) {
                    dataCell.text(dayData[''+fieldName]);
                } else {
                    dataCell = getAttendanceEntryCell(dataCell, dayData, true, true);
                }
            }
            dataCell.appendTo(dataRow);
        });
    }

    function displayClassAttendance() {
        var success = false;
        var classId = $('#classId').val();
        var month = $('#currentMonth').val();
        var year = $('#currentYear').val();

        $.ajax({
            url: "<%=request.getContextPath()%>/attendance/getClassAttendance.htm",
            dataType: "json",
            async: false,
            data: {
                classId: classId,
                month: month,
                year: year,
                output: "JSON",
                sid: new Date().getTime()
            },
            success: function(data) {
                if (data != null && data.studentAttendances != null) {
                    fillStudentAttendances(data.studentAttendances);
                    success = true;
                }
            }
        });
        return success;
    }

    function fillStudentAttendances(studentAttendances) {
        var tbody = $('#studentAttendanceEntryTable tbody');
        for (var index=0; index<studentAttendances.length; index++) {
            fillStudentAttendance(tbody, studentAttendances, index);
        }
        $('#numberOfStudents').val(studentAttendances.length);
    }

    function fillStudentAttendance(parentNode, studentAttendances, index) {
        var dataRow = $("<tr>");
        var studentCell = $("<td>");
        var studentAttendance = studentAttendances[index];

        var studentIdHIddenField = $('<input type="hidden" id="student_' + index + '" value="' + studentAttendance.StudentId + '" >');
        var studentName = studentAttendance.FirstName + ', ' + studentAttendance.LastName;

        studentCell.append(studentIdHIddenField);
        studentCell.append(studentName);
        studentCell.addClass('FirstColumn');

        studentCell.appendTo(dataRow);
        dataRow.appendTo(parentNode);
        createStudentAttendanceEntryRow(dataRow, studentAttendance.Attendance.days);
    }

    function createStudentAttendanceEntryRow(parentNode, daysData) {
        $(daysData).each(function(index, dayData) {
            getAttendanceEntryCell($("<td>"), dayData, false, true).appendTo(parentNode);
        });
    }

    function getAttendanceEntryCell(attendanceEntryCell, dayData, isHeaderCell, showCheckBox) {
        if (dayData.GeneralHoliday) {
            attendanceEntryCell.addClass('GeneralHoliday');
        } else if (dayData.DeclaredHoliday) {
            attendanceEntryCell.addClass('DeclaredHoliday');
        }
        if (dayData.Date == currentDate.getDate()) {
            attendanceEntryCell.addClass('Today');
        }
        if (showCheckBox) {
            // Past data
            var checkBoxElement = $('<input type="checkbox" />');

            if (dayData.DateLine == "0") {
                checkBoxElement.attr('disabled', false);
                checkBoxElement.attr('checked', dayData.Present);
            } else if (dayData.DateLine == "1") {
                checkBoxElement.attr('disabled', false);
                checkBoxElement.attr('checked', dayData.Present);
            } else if (dayData.DateLine == "2") {
                checkBoxElement.attr('disabled', false);
                checkBoxElement.attr('checked', dayData.Present);
            }
            if (isHeaderCell) {
                checkBoxElement.attr('name', 'reference_attendance_chkbox_' + dayData.Date);
                checkBoxElement.attr('id', 'reference_attendance_chkbox_' + dayData.Date);
                checkBoxElement.click(function () {
                    var student_attendance_chkboxes = $('input[type="checkbox"][name="student_attendance_chkbox_' + dayData.Date + '"]');
                    var markAll = false;
                    if (this.checked) {
                        if (dayData.GeneralHoliday || dayData.DeclaredHoliday || dayData.Holiday) {
                            markAll = true;
                        } else {
                            markAll = true;
                        }
                        if (markAll) {
                            for (var index=0; index<student_attendance_chkboxes.length; index++) {
                                student_attendance_chkboxes[index].checked = true;
                                student_attendance_chkboxes[index].disabled = false;
                            }
                        } else {
                            this.checked = !this.checked;
                        }
                    } else {
                        for (var index=0; index<student_attendance_chkboxes.length; index++) {
                            student_attendance_chkboxes[index].checked = false;
                            student_attendance_chkboxes[index].disabled = true;
                        }
                    }
                });
            } else {
                checkBoxElement.attr('name', 'student_attendance_chkbox_' + dayData.Date);
                checkBoxElement.attr('disabled', !($('input[type="checkbox"][name="reference_attendance_chkbox_' + dayData.Date + '"]')[0].checked));
            }
            
            attendanceEntryCell.append(checkBoxElement);
        }
        return attendanceEntryCell;
    }

    function displayActionButtons(show) {
        if (show) {
            $('#PreviousYear').show(1000);
            $('#PreviousMonth').show(1000);
            $('#NextMonth').show(1000);
            $('#NextYear').show(1000);
            $('#SaveAttendance').show(1000);
            $('#FreezeAttendance').show(1000);
        } else {
            $('#PreviousYear').hide(1000);
            $('#PreviousMonth').hide(1000);
            $('#NextMonth').hide(1000);
            $('#NextYear').hide(1000);
            $('#SaveAttendance').hide(1000);
            $('#FreezeAttendance').hide(1000);
        }
    }
});
</script>

<table cellpadding="2" cellspacing="0" align="center" border="0" width="80%">
    <tr>
        <td>
            <table class="formTable_Data" id="studentAttendanceSummaryTable">
                <tbody></tbody>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table class="formTable_Container" id="studentAttendanceEntryTable">
                <thead></thead>
                <tbody></tbody>
                <tfoot>
                    <tr>
                        <th colspan="32" align="right">
                            <img id="PreviousYear" src="<%=request.getContextPath()%>/images/icons/move_all_left.png" class="iconImage" alt="Previous Year" />
                            <img id="PreviousMonth" src="<%=request.getContextPath()%>/images/icons/move_left.png" class="iconImage" alt="Previous Month" />
                            <img id="NextMonth" src="<%=request.getContextPath()%>/images/icons/move_right.png" class="iconImage" alt="Next Month" />
                            <img id="NextYear" src="<%=request.getContextPath()%>/images/icons/move_all_right.png" class="iconImage" alt="Next Year" />
                            <img id="SaveAttendance" src="<%=request.getContextPath()%>/images/icons/save.png" class="iconImage" alt="Save Attendance" />
                            <img id="FreezeAttendance" src="<%=request.getContextPath()%>/images/icons/lock.png" class="iconImage" alt="Freeze Attendance" />
                        </th>
                    </tr>
                </tfoot>
            </table>
        </td>
    </tr>
</table>

<input type="hidden" id="classId" value="${classId}" />
<input type="hidden" id="currentMonth" value="" />
<input type="hidden" id="currentYear" value="" />
<input type="hidden" id="numberOfDays" value="" />
<input type="hidden" id="numberOfStudents" value="" />