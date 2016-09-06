<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">

$(document).ready(function() {
    var validateMarks = {
        field: null,
        dataType: 'numeric',
        focusSetBack: true,
        errorClass: 'fieldErrorClass',
        ignoreOnEmpty: false,
        empty: {
            message: 'Please enter maximum marks for the subject.',
            messageType: 'error'
        },
        invalid: {
            message: 'Please enter valid marks for the subject',
            messageType: 'error'
        },
        min: {
            message: 'Entered marks cannot be lesser than #minBoundary',
            minBoundary: 0,
            messageType: 'error'
        },
        max: {
            message: 'Entered marks cannot be greater than #maxBoundary',
            maxBoundary: 200,
            messageType: 'error'
        }
    };
    $(this).datePicker({id: 'examDate'});

    $('#subjectsTable').dataTable({
        "bPaginate": true,
        "bAutoWidth": false,
        "aoColumnDefs": [ {
            "bSearchable": false,
            "bVisible": false,
            "aTargets": [ 0 ]
        }],
        "sPaginationType": "full_numbers"
    });

    $("#subjectsTable tbody").click(function(event) {
        var oTable = $('#subjectsTable').dataTable();
        $(oTable.fnSettings().aoData).each(function (){
            $(this.nTr).removeClass('row_selected');
        });
        $(event.target.parentNode).addClass('row_selected');
    });

    $('#Add_Subject').click(function() {
        var oTable = $('#subjectsTable').dataTable();

        if ('${fn:length(registeredSubjects)}' == '0') {
            attendError('There are no subjects setup in this class. You must add subjects to the class before creating an exam.');
            return;
        }

        if (oTable.fnGetData().length == '${fn:length(registeredSubjects)}') {
            warn('All available subjects are added to this exam and you cannot add a new subject.');
            return;
        }

        var newRowElements = createNewRow();
        var newRow = $('<tr>');

        for (elemIndex=0; elemIndex<newRowElements.length; elemIndex++) {
            var newCell = $('<td>');
            newCell.append(newRowElements[elemIndex]);
            newRow.append(newCell);
        }
        oTable.fnAddData([newRowElements[0], newRowElements[1], newRowElements[2]]);
        $('.chosen-select').chosen({width: "80%"});
    });

    $('#Delete_Subject').click(function() {
        var oTable = $('#subjectsTable').dataTable();
        var anSelected = fnGetSelected( oTable );
        if (anSelected == null) {
            info_ac('<spring:message code="common.selectRow.delete"/>');
        } else {
            var subjectExamId = oTable.fnGetData(anSelected)[0];
            oTable.fnDeleteRow(anSelected);
        }
    });

    $('#create').click(function() {
        var formData = getFormData();
        if (formData != null) {
            $.ajax({
                type: "POST",
                url: "<%=request.getContextPath()%>/exam/doUpdate.htm",
                data: {
                    updatedExamsData: JSON.stringify(formData),
                    sid: new Date().getTime()
                },
                context: this
            }).done(function(result) {
                handleServerResponseOnModal(result);
            });
        }
    });

    function fnGetSelected( oTableLocal ){
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

    function createNewRow() {
        var newRowElements = new Array();
        // list subjects in this class only.
        var selectElementConent = null;
        var optionsContent = '';
        jQuery.ajax({
            type: "POST",
            dataType: 'json',
            async: false,
            url: "<%=request.getContextPath()%>/registeredSubject/jsonList.htm",
            data: {
                classId: $('#classId').val()
            },
            success: function(response){
                $.each(response.aaData, function(i, subject) {
                    optionsContent += '<option value="' + subject[0] + '">' + subject[2] + '</option>'
                });
            }
        });
        selectElementConent = '<input type="hidden" value="0">' + '<select class="chosen-select">' + optionsContent + '</select>'
        newRowElements[0] = '0';
        newRowElements[1] = selectElementConent;
        newRowElements[2] = '<input type="text" size="3" />';
        return newRowElements;
    }

    function getFormData() {
        var examName =  $('#examName').val();
        if (examName == null || examName == '') {
            notifyError('<spring:message code="exam.enter.examName"/>');
            return null;
        }
        var examDate =  $('#examDate').val();
        if (examDate == null || examDate == '') {
            notifyError('<spring:message code="exam.enter.examDate"/>');
            return null;
        }

        var oTable = $('#subjectsTable').dataTable();
        if (oTable.fnGetData().length == 0) {
            notifyError('<spring:message code="exam.no.subject"/>');
            return null;
        }

        var continueValidation = true;
        var subjectExamsData = new Array();
        var subjectsTracker = new Array();

        $('#subjectsTable > tbody > tr').each(function (index, row) {
            if (continueValidation) {
                var subjectExamData = new Object();
                $.each(this.cells, function(columnIndex) {
                    if (continueValidation) {
                        if (columnIndex == 0) {
                            subjectExamData.subjectExamId = $(this).find('input').val();
                            subjectExamData.registeredSubjectId = $(this).find('select option:selected').val();
                            subjectExamData.subjectName = $(this).find('select option:selected').text();
                            var subjectPresent = false;
                            for (var subjectIndex=0; subjectIndex<subjectsTracker.length; subjectIndex++) {
                                if (subjectsTracker[subjectIndex] == subjectExamData.registeredSubjectId) {
                                    subjectPresent = true;
                                    break;
                                } 
                            }
                            if (subjectPresent) {
                                notifyError('Subject (' + subjectExamData.subjectName + ') is selected more than once.');
                                continueValidation = false;
                            } else {
                                subjectsTracker[subjectsTracker.length] = subjectExamData.registeredSubjectId;
                            }
                        } else if (columnIndex == 1) {
                            validateMarks.field = $(this).find('input');
                            if (!validateField(validateMarks)) {
                                continueValidation = false;
                            }
                            subjectExamData.maximumMarks = validateMarks.field.val();
                        }
                    }
                });
                subjectExamsData[subjectExamsData.length] = subjectExamData;
            }
        });

        if (!continueValidation) {
            return null;
        }
        var formData = new Object();
        formData.classId = $('#classId').val();
        formData.examId = $('#examId').val();
        formData.examName = $('#examName').val();
        formData.examDate =  $('#examDate').val();
        formData.subjectExamsData =  subjectExamsData;
        return formData;
    }

    $('.chosen-select').chosen({width: "100%"});
});
</script>

<c:if test="${examDetails == null}">
<table class="formTable_Data">
  <input type="hidden" name="updatedSubjectExamsData" />
  <input type="hidden" id="examId" value="0"/>
  <tr>
    <td class="label" width="40%"><spring:message code="class.name"/></td>
    <td class="value" width="60%">
      <input type="hidden" id="classId" value="${classId}"/>
      <b><c:out value="${classDetails.classDto.className}"/></b>
    </td>
  </tr>
  <tr>
    <td class="label" width="40%"><spring:message code="exam.examName"/><label class="mandatory">*</label></td>
    <td class="value">
      <input type="text" id="examName" maxlength="64"/>
    </td>
  </tr>
  <tr>
    <td class="label" width="40%"><spring:message code="exam.examDate"/><label class="mandatory">*</label></td>
    <td class="value">
      <input type="text" id="examDate" class="datepicker" maxlength="10" />
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <table cellpadding="0" cellspacing="0" align="center" id="subjectsTable" class="display">
        <thead>
          <tr>
            <th>Subject Exam Id</th>
            <th><spring:message code="subject"/></th>
            <th><spring:message code="exam.maximumMarks"/></th>
          </tr>
        </thead>
        <tbody></tbody>
        <tfoot>
          <tr>
            <th colspan="3" align="right">
              <img src="<%=request.getContextPath()%>/images/icons/add.png" id="Add_Subject" class="iconImage" title="Add Subject" />
              <img src="<%=request.getContextPath()%>/images/icons/delete.png" id="Delete_Subject" class="iconImage" title="Delete Subject" />
            </th>
          </tr>
        </tfoot>
      </table>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <input type="button" id="create" value='<spring:message code="common.create"/>' />
    </td>
  </tr>
</table>
</c:if>

<c:if test="${examDetails != null}">
<table class="formTable_Data">
  <input type="hidden" id="examId" value="${examDetails.examId}"/>
  <c:if test="${examDetails.examCompleted}">
  <tr>
    <td class="label" colspan="2" align="center">
      <font style="font-size: 14px; color: red;">This exam has been marked as 'Completed' and cannot be edited now.</font>
    </td>
  </tr>
  </c:if>
  <tr>
    <td class="label" width="40%"><spring:message code="class.name"/></td>
    <td class="value" width="60%">
      <input type="hidden" id="classId" value="${classId}"/>
      <c:out value="${classDetails.classDto.className}"/>
    </td>
  </tr>
  <tr>
    <td class="label" width="40%"><spring:message code="exam.examName"/></td>
    <td class="value" width="60%">
      <input type="text" id="examName" maxlength="64" value="${examDetails.examName}" />
    </td>
  </tr>
  <tr>
    <td class="label" width="40%"><spring:message code="exam.examDate"/><label class="mandatory">*</label></td>
    <td class="value" width="60%">
      <input type="text" id="examDate" class="datepicker" maxlength="10" value="${examDetails.examDate}" />
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <table cellpadding="0" cellspacing="0" align="center" border="0" id="subjectsTable" class="display">
        <thead>
          <tr>
            <th>Subject Exam Id</th>
            <th><spring:message code="subject"/></th>
            <th><spring:message code="exam.maximumMarks"/></th>
          </tr>
        </thead>
        <tbody>
          <c:if test="${examDetails.subjectExams != null}">
          <c:forEach var="subjectExam" items="${examDetails.subjectExams}">
          <tr>
            <td>${subjectExam.subjectExamId}</td>
            <td>
              <input type="hidden" value="${subjectExam.subjectExamId}" />
              <select class="chosen-select">
                <c:forEach var="registeredSubject" items="${registeredSubjects}">
                <c:if test="${registeredSubject.subjectId == subjectExam.registeredSubject.subjectId}">
                <option value="${registeredSubject.subjectId}" selected>${registeredSubject.subject.subjectName}</option>
                </c:if>
                <c:if test="${registeredSubject.subjectId != subjectExam.registeredSubject.subjectId}">
                <option value="${registeredSubject.subjectId}">${registeredSubject.subject.subjectName}</option>
                </c:if>
                </c:forEach>
              </select>
            </td>
            <td>
              <input type="text" value="${subjectExam.maximumMarks}" size="3" />
            </td>
          </tr>
          </c:forEach>
          </c:if>
        </tbody>
        <c:if test="${examDetails.examCompleted == 'false'}">
        <tfoot>
          <tr>
            <th colspan="3" align="right">
              <img src="<%=request.getContextPath()%>/images/icons/add.png" id="Add_Subject" class="iconImage" title="Add Subject" />
              <img src="<%=request.getContextPath()%>/images/icons/delete.png" id="Delete_Subject" class="iconImage" title="Delete Subject" />
            </th>
          </tr>
        </tfoot>
        </c:if>
      </table>
    </td>
  </tr>
  <c:if test="${examDetails.examCompleted == 'false'}">
  <tr>
    <td colspan="2" align="center">
      <input type="button" id="create" value='<spring:message code="common.update"/>' />
    </td>
  </tr>
  </c:if>
</table>
</c:if>
