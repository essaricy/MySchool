<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
  var validateMarks = {
    field: null,
    dataType: 'numeric',
    focusSetBack: true,
    errorClass: 'fieldErrorClass',
    ignoreOnEmpty: true,
    empty: {
      message: 'Please enter marks for the students',
      messageType: 'error'
    },
    invalid: {
      message: 'Please enter valid marks for the students',
      messageType: 'error'
    },
    min: {
      message: 'Entered marks cannot be lesser than #minBoundary',
      minBoundary: 0,
      messageType: 'error'
    },
    max: {
      message: 'Entered marks cannot be greater than #maxBoundary',
      //maxBoundary: 0,
      messageType: 'error'
    }
  };

  var numberOfSubjects = parseInt($('#numberOfSubjects').val());
  var MARKS_START_COLUMN = 3;
  var MARKS_END_COLUMN = MARKS_START_COLUMN +  numberOfSubjects;

  var marksEntryTable = $('#marksEntryTable').dataTable({
    "bPaginate": true,
    "aoColumnDefs": [ {
      "bSearchable": false,
      "bVisible": false,
      "aTargets": [ ]
    }],
    "sPaginationType": "full_numbers"
  });

  $("#save").click(function() {
    var proceedSubmit = true;
    var numberOfSubjects = parseInt($('#numberOfSubjects').val());
    var MARKS_START_COLUMN = 1;
    var MARKS_END_COLUMN = MARKS_START_COLUMN +  numberOfSubjects - 1;
    var StudentsMarksData = new Array();

    //var jsonObjectData = new Object();

    $('#marksEntryTable > tbody > tr').each(function () {
      var StudentMarksData = new Object();
      var StudentData = new Object();
      var SubjectsMarksData = new Array();

      $.each(this.cells, function(columnIndex) {
        if (proceedSubmit) {
          if (columnIndex == 0) {
            StudentData.StudentId = $(this).find('input[name="studentId"]').val();
            StudentData.AdmissionNumber = $(this).find('input[name="admissionNumber"]').val();
          } else if (columnIndex >= MARKS_START_COLUMN && columnIndex <= MARKS_END_COLUMN) {
            var SubjectMarksData = new Object();
            var studentMarksTextBox = $(this).find('input[type="text"]');
            SubjectMarksData.StudentExamId = $(this).find('input[name="studentExamId"]').val();
            SubjectMarksData.SubjectExamId = $(this).find('input[name="subjectExamId"]').val();
            // set the field and maximum marks
            validateMarks.field = studentMarksTextBox;
            var maximumMarks = $('#marksEntryTable > thead > tr').find('th').eq(columnIndex).find('input[name="maximumMarks"]').val();
            validateMarks.max.maxBoundary = parseInt(maximumMarks);
            if (!validateField(validateMarks)) {
              proceedSubmit = false;
            }
            SubjectMarksData.ObtainedMarks = studentMarksTextBox.val();
            SubjectsMarksData[SubjectsMarksData.length] = SubjectMarksData;
          }
        }
      });
      StudentMarksData.StudentData = StudentData;
      StudentMarksData.SubjectsMarksData = SubjectsMarksData;
      StudentsMarksData[StudentsMarksData.length] = StudentMarksData;
    });

    if (proceedSubmit) {
      $.ajax({
        type: "POST",
        url: "<%=request.getContextPath()%>/student-exam/updateStudentsMarks.htm",
        data: { StudentsMarksData: JSON.stringify(StudentsMarksData) },
        context: this
      }).done(function(result) {
        parseModelResponse(result);
      });
    }
  });

  $("#complete").click(function() {
    confirm('You will not be able to enter or modify marks for any student in this exam if you mark it complete. SMS and/or e-mail will be sent to the configured students. Would you like to proceed?', completeExam);
  });

  function completeExam(result) {
    if (result == "Yes") {
      $.ajax({
        type: "POST",
        url: '<%=request.getContextPath()%>/exam/freezeExam.htm',
        data: {
          examId: $('#examId').val(),
          //classId: $('#classId').val(),
          sid: new Date().getTime()
        },
        context: this
      }).done(function(result) {
        parseModelResponse(result);
      });
    }
  }
});

</script>

<c:if test="${ExamDetails != null}">
<c:set var="RegisteredClass" value="${ExamDetails.registeredClass}" />
<input type="hidden" id="ExamId" value="${ExamDetails.examId}" />
<input type="hidden" id="RegisteredClassId" value="${RegisteredClass.classId}" />
<table width="80%" id="ExamDetailsTable" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td class="label">
      Exam Name
    </td>
    <td class="value">
      <b>${ExamDetails.examName}</b>
    </td>
    <td class="label">
      Exam Date
    </td>
    <td class="value">
      <b>${ExamDetails.examDate}</b>
    </td>
  </tr>
  <tr>
    <td class="label">
      Class
    </td>
    <td class="value">
      <b>${RegisteredClass.classDto.className}, ${RegisteredClass.medium.description}, ${RegisteredClass.section.sectionName}</b>
    </td>
    <td class="label">
      Status
    </td>
    <td class="value">
      <c:if test="${ExamDetails.examCompleted == 'true'}"><b style="color: green;">Completed</b></c:if>
      <c:if test="${ExamDetails.examCompleted == 'false'}"><b style="color: blue;">In Progress</b></c:if>
    </td>
  </tr>
</table>
<table cellpadding="0" cellspacing="0" align="center">
  <tr>
    <td valign="top" align="center">
      <input type="hidden" id="examId" value="${examId}" />
      <input type="hidden" id="classId" value="${classId}" />

      <c:if test="${SubjectExams != null}">
      <input type="hidden" id="numberOfSubjects" value="${fn:length(SubjectExams)}" />
      <table width="100%" cellspacing="0" align="center" border="0" id="marksEntryTable" class="display">
        <thead>
          <tr>
            <th><spring:message code="student.studentName"/></th>
            <c:forEach var="subjectExam" items="${SubjectExams}">
            <th>
              <input type="hidden" name="subjectExamId" value="${subjectExam.subjectExamId}" />
             <input type="hidden" name="maximumMarks" value="${subjectExam.maximumMarks}" />${subjectExam.registeredSubject.subject.subjectName}<font color="yellow">[${subjectExam.maximumMarks}]</font>
            </th>
            </c:forEach>
            <th><spring:message code="marksEntry.totalMarks"/></th>
            <th><spring:message code="marksEntry.percentage"/> <font color="yellow">[%]</font></th>
            <th><spring:message code="marksEntry.grade"/></th>
          </tr>
        </thead>
        <tbody>
          <c:if test="${StudentsInExam != null}">
          <c:forEach var="studentInExam" items="${StudentsInExam}">
          <tr>
            <td>
              <input type="hidden" name="studentId" value="${studentInExam.student.studentId}" />
              <input type="hidden" name="admissionNumber" value="${studentInExam.student.admissionNumber}" />
                ${studentInExam.student.personalDetails.firstName}&nbsp;${studentInExam.student.personalDetails.lastName}&nbsp;(${studentInExam.student.admissionNumber})
            </td>
            <!-- Put student exam ids as 0 since marks entry is not done yet. -->
            <c:if test="${studentInExam.studentExams == null}">
            <c:forEach var="subjectExam" items="${SubjectExams}">
            <td align="center">
              <input type="hidden" name="studentExamId" value="0" />
              <input type="hidden" name="subjectExamId" value="${subjectExam.subjectExamId}" />
              <c:if test="${ExamDetails.examCompleted == 'true'}">0</c:if>
              <c:if test="${ExamDetails.examCompleted == 'false'}">
              <input type="text" class="textComponent" size="3" />
              </c:if>
            </td>
            </c:forEach>
            </c:if>

            <c:if test="${studentInExam.studentExams != null}">
            <c:forEach var="studentExam" items="${studentInExam.studentExams}">
            <td align="center">
              <input type="hidden" name="studentExamId" value="${studentExam.studentExamId}" />
              <input type="hidden" name="subjectExamId" value="${studentExam.subjectExamId}" />
              <c:if test="${ExamDetails.examCompleted == 'true'}">${studentExam.obtainedMarks}</c:if>
              <c:if test="${ExamDetails.examCompleted == 'false'}">
              <input type="text" class="textComponent" size="3" value="${studentExam.obtainedMarks}" />
              </c:if>
            </td>
            </c:forEach>
            </c:if>
            <td class="boldCenterCell">${studentInExam.totalMarks}</td>
            <td class="boldCenterCell">${studentInExam.percentage}</td>
            <td class="boldLeftCell">
              <c:if test="${studentInExam.grade == null}">N/A</c:if>
              <c:if test="${studentInExam.grade != null}">${studentInExam.grade}</c:if>
            </td>
          </tr>
          </c:forEach>
          </c:if>
        </tbody>
      </table>
      </c:if>
    </td>
  </tr>
  <tr>
    <c:if test="${SubjectExams != null && ExamDetails.examCompleted == 'false'}">
    <td align="center">
      <input id="save" type="button" value="Save" class="formButton" />
      <input id="complete" type="button" value="Mark as Complete" class="formButton" />
    </td>
    </c:if>
  </tr>
</table>
</c:if>
