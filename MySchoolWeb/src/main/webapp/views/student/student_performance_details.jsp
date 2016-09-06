<c:if test="${student != null && studentPerformance == null}">
<table border="0" cellpadding="6" cellspacing="0" align="center" id="performanceDetails" width="100%">
  <tr>
    <td class="error">Exams data is for this student is not available.</td>
  </tr>
</table>
</c:if>

<c:if test="${student != null && studentPerformance != null}">
<script>
function showProgress(mode) {
  if (mode == 'Table') {
    document.getElementById('gridTable').style.display = 'block';
    document.getElementById('graphTable').style.display = 'none';
  } else if (mode == 'Graph') {
    document.getElementById('gridTable').style.display = 'none';
    document.getElementById('graphTable').style.display = 'block';
  }
}
</script>

<table border="0" cellpadding="6" cellspacing="0" align="center" id="performanceDetails" width="100%">
  <tr>
    <td colspan="3">
    <!-- GRID TABLE -->
      <table class="formTable_Data" id="gridTable">
        <thead>
          <tr>
            <th align="left">Exam Name</th>
            <c:forEach var="aStudentPerformance" items="${studentPerformance}" varStatus="count">
              <c:if test="${count.count == 1}">
                <c:if test="${aStudentPerformance.studentInExam != null}">
                  <c:if test="${aStudentPerformance.studentInExam.studentExams != null}">
                    <c:forEach var="studentExam" items="${aStudentPerformance.studentInExam.studentExams}">
                      <c:if test="${studentExam.subjectExam != null}">
                        <c:if test="${studentExam.subjectExam.registeredSubject != null}">
                          <c:if test="${studentExam.subjectExam.registeredSubject.subject != null}">
                            <c:if test="${studentExam.subjectExam.registeredSubject.subject.subjectName != null}">
                              <th align="left">${studentExam.subjectExam.registeredSubject.subject.subjectName}</th>
                            </c:if>
                          </c:if>
                        </c:if>
                      </c:if>
                    </c:forEach>
                  </c:if>
                </c:if>
              </c:if>
            </c:forEach>
            <th>Total Marks</th>
            <th>Percentage</th>
            <th>Grade</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="aStudentPerformance" items="${studentPerformance}">
          <tr>
            <c:if test="${aStudentPerformance.exam != null}">
              <td>${aStudentPerformance.exam.examName}<!--&nbsp;(${aStudentPerformance.exam.examDate})--></td>
            </c:if>
            <c:if test="${aStudentPerformance.studentInExam != null}">
              <c:if test="${aStudentPerformance.studentInExam.studentExams != null}">
                <c:forEach var="studentExam" items="${aStudentPerformance.studentInExam.studentExams}">
                  <td align="left"><b>${studentExam.obtainedMarks}</b>&nbsp;[${studentExam.subjectExam.maximumMarks}]</td>
                </c:forEach>
              </c:if>
              <td align="center">${aStudentPerformance.studentInExam.totalMarks}</td>
              <td align="center">${aStudentPerformance.studentInExam.percentage}</td>
              <td align="center">${aStudentPerformance.studentInExam.grade}</td>
            </c:if>
          </tr>
          </c:forEach>
        </tbody>
      </table>

      <!-- GRAPH TABLE -->
      <table class="formTable_Data" id="graphTable" style="display:none;">
        <tbody>
          <tr>
            <td align="center">
              <jsp:useBean id="studentProgressGraph" class="com.myschool.web.controllers.StudentProgressGraph" scope="session"/>
              <cewolf:chart id="line" title="Progress Graph" type="verticalbar3d" xaxislabel="Exams" yaxislabel="Percentage">
                <cewolf:data>
                  <cewolf:producer id="studentProgressGraph" />
                </cewolf:data>
                <cewolf:colorpaint color="#ECF1F7" />
              </cewolf:chart>
              <p>
              <cewolf:img chartid="line" renderer="/cewolf" width="800" height="500" />
            </td>
          </tr>
        </tbody>
      </table>

    </td>
  </tr>
  <tr>
    <td align="right" width="49%"><input type="button" value="Table" onclick="showProgress(this.value)"/></td>
    <td width="2%">&nbsp;</td>
    <td align="left" width="49%"><input type="button" value="Graph" onclick="showProgress(this.value)"/></td>
  </tr>
</table>
</c:if>
