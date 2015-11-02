<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<style>
.userFormTable {
  font-size: 0.8em;
}
</style>
<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
  $(this).lazySelect({
    id: "Class_Lookup_Branch",
    url: '<%=request.getContextPath()%>/branch/jsonList.htm',
    valueIndices: [2],
    changeCallback: branchChange,
    //selectOnCode: $('#Class_Lookup_BranchId').val()
  });
  $('#Class_Lookup_School').chosen({width: "100%"});
  $('#Class_Lookup_RegisteredClass').chosen({width: "100%"});

  function branchChange() {
    var branchId = $('#Class_Lookup_Branch').val();
    $('#Class_Lookup_RegisteredClass').children('option:not(:first)').remove();
    $('#Class_Lookup_School').children('option:not(:first)').remove();
    if (branchId != 0) {
      $(this).lazySelect({
        id: "Class_Lookup_School",
        url: '<%=request.getContextPath()%>/school/jsonList.htm?branchId=' + branchId + '&sid=' + new Date().getTime(),
        valueIndices: [2],
        changeCallback: schoolChange,
        //selectOnCode: $('#Class_Lookup_SchoolId').val()
      });
    }
    $("#Class_Lookup_School").trigger("chosen:updated");
    $("#Class_Lookup_RegisteredClass").trigger("chosen:updated");
  }

  function schoolChange(clearOthers) {
    var schoolId = $('#Class_Lookup_School').val();
    $('#Class_Lookup_RegisteredClass').children('option:not(:first)').remove();
    if (schoolId != 0) {
      $(this).lazySelect({
        id: "Class_Lookup_RegisteredClass",
        url: '<%=request.getContextPath()%>/class/jsonListRegistered.htm?SchoolId=' + schoolId + '&sid=' + new Date().getTime(),
        valueIndices: [2, 4, 6],
        valueDelimiter: ' ~ ',
        changeCallback: registeredClassChange,
        //selectOnCode: $('#Class_Lookup_RegisteredClassId').val()
      });
    }
    $("#Class_Lookup_RegisteredClass").trigger("chosen:updated");
  }

  function registeredClassChange() {
    var classId = $('#Class_Lookup_RegisteredClass').val();
    if (classId != 0) {
      var registeredClassDesc = $('#Class_Lookup_RegisteredClass option:selected').text();
      var registeredClassDescSplit = registeredClassDesc.split(' ~ ');
      $('#Class_Lookup_RegisteredClassId').val($('#Class_Lookup_RegisteredClass').val());
      $('#Class_Lookup_ClassName').val(registeredClassDescSplit[0]);
      $('#Class_Lookup_MediumName').val(registeredClassDescSplit[1]);
      $('#Class_Lookup_SectionName').val(registeredClassDescSplit[2]);
    }
  }

  $('#Class_Lookup').click(function() {
    var classId = $('#Class_Lookup_RegisteredClass').val();
    if (classId == '0') {
      showError('Please select a class');
    } else {
      setRegisteredClass(getRegisteredClass());
      modal.close();
    }
  });

  // Initially select the options if updating class.
  var branchId = $('#Class_Lookup_BranchId').val();
  if (branchId != 0) {
    $('#Class_Lookup_Branch option').each(function(index, value) {
        if (branchId == value.value) {
            $(this).attr('selected', true);
            $("#Class_Lookup_Branch").trigger("chosen:updated"); 
            branchChange();
        }
    });
  }
  var schoolId = $('#Class_Lookup_SchoolId').val();
  if (schoolId != 0) {
    $('#Class_Lookup_School option').each(function(index, value) {
        if (schoolId == value.value) {
            $(this).attr('selected', true);
            $("#Class_Lookup_School").trigger("chosen:updated"); 
            schoolChange();
        }
    });
  }
  var registeredClassId = $('#Class_Lookup_RegisteredClassId').val();
  if (registeredClassId != 0) {
    $('#Class_Lookup_RegisteredClass option').each(function(index, value) {
        if (registeredClassId == value.value) {
            $(this).attr('selected', true);
            $("#Class_Lookup_RegisteredClass").trigger("chosen:updated"); 
            registeredClassChange();
        }
    });
  }
});

function getRegisteredClass() {
    var RegisteredClass = new Object();
    RegisteredClass.RegisteredClassId=$('#Class_Lookup_RegisteredClassId').val();
    RegisteredClass.ClassName=$('#Class_Lookup_ClassName').val();
    RegisteredClass.MediumName=$('#Class_Lookup_MediumName').val();
    RegisteredClass.SectionName=$('#Class_Lookup_SectionName').val();
    return RegisteredClass;
}
</script>

<input type="hidden" id="Class_Lookup_ClassName" value="" />
<input type="hidden" id="Class_Lookup_MediumName" value="" />
<input type="hidden" id="Class_Lookup_SectionName" value="" />

<c:if test="${RegisteredClass == null}">
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td width="40%" class="label">Branch<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="hidden" id="Class_Lookup_BranchId" value="0" />
      <select id="Class_Lookup_Branch" class="chosen-select">
        <option value="0">Select</option>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">School<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="hidden" id="Class_Lookup_SchoolId" value="0" />
      <select id="Class_Lookup_School" class="chosen-select">
        <option value="0">Select</option>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Class<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <select id="Class_Lookup_RegisteredClass" class="chosen-select">
        <option value="0">Select</option>
      </select>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <input type="hidden" id="Class_Lookup_RegisteredClassId" value="0" />
      <input type="button" id="Class_Lookup" class="active" value='<spring:message code="common.create"/>' />
    </td>
  </tr>
</table>
</c:if>

<c:if test="${RegisteredClass != null}">
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td width="40%" class="label">Branch<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="hidden" id="Class_Lookup_BranchId" value="${RegisteredClass.school.branch.branchId}" />
      <select id="Class_Lookup_Branch" class="chosen-select">
        <option value="0">Select</option>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">School<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="hidden" id="Class_Lookup_SchoolId" value="${RegisteredClass.school.schoolId}" />
      <select id="Class_Lookup_School" class="chosen-select">
        <option value="0">Select</option>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Class<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <select id="Class_Lookup_RegisteredClass" class="chosen-select">
        <option value="0">Select</option>
      </select>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <input type="hidden" id="Class_Lookup_RegisteredClassId" value="${RegisteredClass.classId}" />
      <input type="button" id="Class_Lookup" class="active" value='<spring:message code="common.update"/>' />
    </td>
  </tr>
</table>
</c:if>
