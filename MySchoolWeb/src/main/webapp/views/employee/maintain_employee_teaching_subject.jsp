<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<style>
.userFormTable {
  font-size: 1.2em;
}
</style>
<script type="text/javascript" charset="utf-8">
$(document).ready(function() {

  $(this).lazySelect({
    id: "Branch",
    url: '<%=request.getContextPath()%>/branch/jsonList.htm',
    valueIndices: [2],
    changeCallback: branchChange,
    //selectOnCode: $('#BranchId').val()
  });
  $('#School').chosen({width: "100%"});
  $('#RegisteredClass').chosen({width: "100%"});
  $('#RegisteredSubject').chosen({width: "100%"});

  function branchChange() {
    var branchId = $('#Branch').val();
    $('#RegisteredClass').children('option:not(:first)').remove();
    $('#RegisteredSubject').children('option:not(:first)').remove();
    $('#School').children('option:not(:first)').remove();
    if (branchId != 0) {
      $(this).lazySelect({
        id: "School",
        url: '<%=request.getContextPath()%>/school/jsonList.htm?branchId=' + branchId + '&sid=' + new Date().getTime(),
        valueIndices: [2],
        changeCallback: schoolChange,
        //selectOnCode: $('#SchoolId').val()
      });
    }
    $("#School").trigger("chosen:updated");
    $("#RegisteredClass").trigger("chosen:updated");
    $("#RegisteredSubject").trigger("chosen:updated");
  }

  function schoolChange(clearOthers) {
    var schoolId = $('#School').val();
    $('#RegisteredSubject').children('option:not(:first)').remove();
    $('#RegisteredClass').children('option:not(:first)').remove();
    if (schoolId != 0) {
      $(this).lazySelect({
        id: "RegisteredClass",
        url: '<%=request.getContextPath()%>/class/jsonListBySchool.htm?schoolId=' + schoolId + '&sid=' + new Date().getTime(),
        valueIndices: [2, 4, 6],
        valueDelimiter: ' ~ ',
        changeCallback: registeredClassChange,
        //selectOnCode: $('#RegisteredClassId').val()
      });
    }
    $("#RegisteredClass").trigger("chosen:updated");
    $("#RegisteredSubject").trigger("chosen:updated");
  }

  function registeredClassChange() {
    var classId = $('#RegisteredClass').val();
    $('#RegisteredSubject').children('option:not(:first)').remove();
    if (classId != 0) {
      $(this).lazySelect({
        id: "RegisteredSubject",
        url: '<%=request.getContextPath()%>/registeredSubject/jsonList.htm?classId=' + classId + '&sid=' + new Date().getTime(),
        valueIndices: [2],
        changeCallback: registeredSubjectChange,
        //selectOnCode: $('#RegisteredSubjectId').val()
      });
      var registeredClassDesc = $('#RegisteredClass option:selected').text();
      var registeredClassDescSplit = registeredClassDesc.split(' ~ ');
      $('#ClassName').val(registeredClassDescSplit[0]);
      $('#MediumName').val(registeredClassDescSplit[1]);
      $('#SectionName').val(registeredClassDescSplit[2]);
    }
    $("#RegisteredSubject").trigger("chosen:updated");
  }

  function registeredSubjectChange() {
      $('#RegisteredSubjectId').val($('#RegisteredSubject option:selected').val());
  }

  $('#create').click(function() {
    validateEmployeeAttribute('CREATE',
        '<%=request.getContextPath()%>/employee-attribute/doValidate.htm', 'EmployeeTeachingSubject',
        getEmployeeEmployeeTeachingSubject(), employeeSubjectAttributeSequence);
  });

  $('#update').click(function() {
    validateEmployeeAttribute('UPDATE',
        '<%=request.getContextPath()%>/employee-attribute/doValidate.htm', 'EmployeeTeachingSubject',
        getEmployeeEmployeeTeachingSubject(), employeeSubjectAttributeSequence);
  });

  // Initially select the options if updating subject.
  var branchId = $('#BranchId').val();
  if (branchId != 0) {
    $('#Branch option').each(function(index, value) {
        if (branchId == value.value) {
            $(this).attr('selected', true);
            $("#Branch").trigger("chosen:updated"); 
            branchChange();
        }
    });
  }
  var schoolId = $('#SchoolId').val();
  if (schoolId != 0) {
    $('#School option').each(function(index, value) {
        if (schoolId == value.value) {
            $(this).attr('selected', true);
            $("#School").trigger("chosen:updated"); 
            schoolChange();
        }
    });
  }
  var registeredClassId = $('#RegisteredClassId').val();
  if (registeredClassId != 0) {
    $('#RegisteredClass option').each(function(index, value) {
        if (registeredClassId == value.value) {
            $(this).attr('selected', true);
            $("#RegisteredClass").trigger("chosen:updated"); 
            registeredClassChange();
        }
    });
  }
  var registeredSubjectId = $('#RegisteredSubjectId').val();
  if (registeredSubjectId != 0) {
    $('#RegisteredSubject option').each(function(index, value) {
        if (registeredSubjectId == value.value) {
            $(this).attr('selected', true);
            $("#RegisteredSubject").trigger("chosen:updated"); 
            registeredSubjectChange();
        }
    });
  }
});

function getEmployeeEmployeeTeachingSubject() {
    var EmployeeTeachingSubject = new Object();
    EmployeeTeachingSubject.EmployeeSubjectId=$('#EmployeeSubjectId').val();
    EmployeeTeachingSubject.RegisteredSubjectId=$('#RegisteredSubjectId').val();
    EmployeeTeachingSubject.BranchId=$('#Branch').val();
    EmployeeTeachingSubject.Branch=$('#Branch option:selected').text();
    EmployeeTeachingSubject.SchoolId=$('#School').val();
    EmployeeTeachingSubject.School=$('#School option:selected').text();
    EmployeeTeachingSubject.RegisteredClassId=$('#RegisteredClass').val();
    EmployeeTeachingSubject.ClassId=$('#RegisteredClass').val();
    EmployeeTeachingSubject.Class=$('#ClassName').val();
    EmployeeTeachingSubject.MediumId='0';
    EmployeeTeachingSubject.Medium=$('#MediumName').val();
    EmployeeTeachingSubject.SectionId='0';
    EmployeeTeachingSubject.Section=$('#SectionName').val();
    EmployeeTeachingSubject.SubjectId='0';
    EmployeeTeachingSubject.Subject=$('#RegisteredSubject option:selected').text();
    return EmployeeTeachingSubject;
}
</script>

<input type="hidden" id="ClassName" value="" />
<input type="hidden" id="MediumName" value="" />
<input type="hidden" id="SectionName" value="" />

<c:if test="${EmployeeTeachingSubject == null}">
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td width="40%" class="label">Branch<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="hidden" id="BranchId" value="0" />
      <select id="Branch" class="chosen-select">
        <option value="0">Select</option>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">School<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="hidden" id="SchoolId" value="0" />
      <select id="School" class="chosen-select">
        <option value="0">Select</option>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Class<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="hidden" id="RegisteredClassId" value="0" />
      <select id="RegisteredClass" class="chosen-select">
        <option value="0">Select</option>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Subject<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="hidden" id="RegisteredSubjectId" value="0" />
      <select id="RegisteredSubject" class="chosen-select">
        <option value="0">Select</option>
      </select>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <input type="hidden" id="EmployeeSubjectId" value="0" />
      <input type="button" id="create" class="active" value='<spring:message code="common.create"/>' />
    </td>
  </tr>
</table>
</c:if>

<c:if test="${EmployeeTeachingSubject != null}">
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td width="40%" class="label">Branch<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="hidden" id="BranchId" value="${EmployeeTeachingSubject.registeredSubject.registeredClass.school.branch.branchId}" />
      <select id="Branch" class="chosen-select">
        <option value="0">Select</option>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">School<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="hidden" id="SchoolId" value="${EmployeeTeachingSubject.registeredSubject.registeredClass.school.schoolId}" />
      <select id="School" class="chosen-select">
        <option value="0">Select</option>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Class<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="hidden" id="RegisteredClassId" value="${EmployeeTeachingSubject.registeredSubject.registeredClass.classId}" />
      <select id="RegisteredClass" class="chosen-select">
        <option value="0">Select</option>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Subject<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="hidden" id="RegisteredSubjectId" value="${EmployeeTeachingSubject.registeredSubject.subjectId}" />
      <select id="RegisteredSubject" class="chosen-select">
        <option value="0">Select</option>
      </select>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <input type="hidden" id="EmployeeSubjectId" value="${EmployeeTeachingSubject.employeeSubjectId}" />
      <input type="button" id="update" class="active" value='<spring:message code="common.update"/>' />
    </td>
  </tr>
</table>
</c:if>