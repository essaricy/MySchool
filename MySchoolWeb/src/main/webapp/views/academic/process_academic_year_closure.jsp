<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript" charset="utf-8">
$(document).ready(function () {
  $('.chosen-select').chosen({width: "40%"});
});
</script>

<c:if test="${AcademicYearClosure != null}">
<table width="70%" class="userFormTable" align="center" cellspacing="10" cellpadding="5">
  <caption class="dataTableCaption">Academic Year Closure</caption>
  <tr>
    <td width="100%" align="center">
      <c:set var="MySchoolProfile" value="${AcademicYearClosure.mySchoolProfile}" />
      <c:set var="CurrentAcademic" value="${AcademicYearClosure.currentAcademic}" />
      <c:set var="NextAcademic" value="${AcademicYearClosure.nextAcademic}" />
      <c:set var="ExamGrades" value="${AcademicYearClosure.examGrades}" />

      <c:if test="${MySchoolProfile.ayeInProgress}">
        <font class="error">Academic Year Closure process is already in progress.</font>
      </c:if>
      <!-- MySchool Configuration -->
      <table class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
          <tr>
            <td colspan="2" align="left">
              <b>Review the below configurations before initiating Academic Year Closure.</b><br/>
              [<a href="<%=request.getContextPath()%>/profile/list.htm" class="formLink">Change Configuration</a>]
            </td>
          </tr>
          <!-- Email service configuration -->
          <tr>
            <td width="50%" class="label">Use Email Service</td>
            <td width="50%" class="value">
            <c:if test="${MySchoolProfile.emailActive}">Yes</c:if>
            <c:if test="${!MySchoolProfile.emailActive}">No</c:if>
            </td>
          </tr>

          <c:if test="${MySchoolProfile.emailActive}">
          <tr>
            <td width="50%" class="label">Use Email Service for Employees</td>
            <td width="50%" class="value">
            <c:if test="${MySchoolProfile.emailEmployees}">Yes</c:if>
            <c:if test="${!MySchoolProfile.emailEmployees}">No</c:if>
            </td>
          </tr>
          </c:if>

          <c:if test="${MySchoolProfile.emailActive}">
          <tr>
            <td width="50%" class="label">Use Email Service for Students</td>
            <td width="50%" class="value">
            <c:if test="${MySchoolProfile.emailStudents}">Yes</c:if>
            <c:if test="${!MySchoolProfile.emailStudents}">No</c:if>
            </td>
          </tr>
          </c:if>

          <!-- SMS service configuration -->
          <tr>
            <td width="50%" class="label">Use SMS Service</td>
            <td width="50%" class="value">
            <c:if test="${MySchoolProfile.smsActive}">Yes</c:if>
            <c:if test="${!MySchoolProfile.smsActive}">No</c:if>
            </td>
          </tr>

          <c:if test="${MySchoolProfile.emailActive}">
          <tr>
            <td width="50%" class="label">Use SMS Service for Employees</td>
            <td width="50%" class="value">
            <c:if test="${MySchoolProfile.smsEmployees}">Yes</c:if>
            <c:if test="${!MySchoolProfile.smsEmployees}">No</c:if>
            </td>
          </tr>
          </c:if>

          <c:if test="${MySchoolProfile.emailActive}">
          <tr>
            <td width="50%" class="label">Use SMS Service for Students</td>
            <td width="50%" class="value">
            <c:if test="${MySchoolProfile.smsStudents}">Yes</c:if>
            <c:if test="${!MySchoolProfile.smsStudents}">No</c:if>
            </td>
          </tr>
          </c:if>
      </table>

      <!-- Academic Year information -->
      <table class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5" width="100%">
          <tr>
            <td colspan="2" align="left">
              <b>Review Academic Year information.</b><br/>
              <c:if test="${CurrentAcademic == null or NextAcademic == null}">
                [<a href="<%=request.getContextPath()%>/academic/list.htm" class="formLink">Setup Academic Year</a>]
              </c:if>
            </td>
          </tr>
          <!-- Current Academic Year -->
          <c:if test="${CurrentAcademic == null}">
          <tr>
            <td colspan="2" align="left">
              Current Academic Year information is not available. This is mandatory to initiate Academic Year Closure.<br />
            </td>
          </tr>
          </c:if>

          <c:if test="${CurrentAcademic != null}">
          <tr>
            <td width="50%" class="label">Current Academic Year Name</td>
            <td width="50%" class="value">${CurrentAcademic.academicYearName}</td>
          </tr>
          <tr>
            <td width="50%" class="label">Current Academic Year Start Date</td>
            <td width="50%" class="value">${CurrentAcademic.academicYearStartDate}</td>
          </tr>
          <tr>
            <td width="50%" class="label">Current Academic Year End Date</td>
            <td width="50%" class="value">${CurrentAcademic.academicYearEndDate}</td>
          </tr>
          </c:if>

          <!-- Next Academic Year -->
          <c:if test="${NextAcademic == null}">
          <tr>
            <td colspan="2" align="left">
              Next Academic Year information is not available. This is mandatory to initiate Academic Year Closure.
            </td>
          </tr>
          </c:if>

          <c:if test="${NextAcademic != null}">
          <tr>
            <td width="50%" class="label">Next Academic Year Name</td>
            <td width="50%" class="value">${NextAcademic.academicYearName}</td>
          </tr>
          <tr>
            <td width="50%" class="label">Current Academic Year Start Date</td>
            <td width="50%" class="value">${NextAcademic.academicYearStartDate}</td>
          </tr>
          <tr>
            <td width="50%" class="label">Current Academic Year End Date</td>
            <td width="50%" class="value">${NextAcademic.academicYearEndDate}</td>
          </tr>
          </c:if>
      </table>

      <table class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5" width="100%">
          <tr>
            <td colspan="2" align="left">
              <b>Exam Grades to promote students automatically to the next class.</b><br/>
              <c:if test="${ExamGrades == null}">
                [<a href="<%=request.getContextPath()%>/academic/list.htm" class="formLink">Setup Exam Grades</a>]
              </c:if>
            </td>
          </tr>
          <c:if test="${ExamGrades == null}">
          <tr>
            <td colspan="2" align="left">
              Exam Grades information is not available. This is mandatory to initiate Academic Year Closure.
            </td>
          </tr>
          </c:if>
          <c:if test="${ExamGrades != null}">
          <tr>
            <td width="50%" class="label">Exam Grade</td>
            <td width="50%" class="value">
              <select id="examGrades" class="chosen-select">
                <c:forEach var="examGrade" items="${ExamGrades}">
                  <option value="${examGrade.examGradeId}">${examGrade.gradeName}</option>
                </c:forEach>
              </select>
            </td>
          </tr>
          </c:if>
      </table>

      <c:set var="canProcess" value="${MySchoolProfile.ayeInProgress != true and CurrentAcademic != null and NextAcademic != null}" />
      <table class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5" width="100%">
        <tr>
          <td align="center">
            <c:if test="${canProcess}">
              <input type="button" id="InitiateAYC" class="active" value='Initiate Academic Year Closure' />
            </c:if>
            <c:if test="${not canProcess}">
              <input type="button" id="InitiateAYC" class="inactive" value='Initiate Academic Year Closure' disabled />
            </c:if>
          </td>
        </tr>
      </table>

    </td>
  </tr>
</table>
</c:if>
