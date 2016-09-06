<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<style>
.userFormTable {
  font-size: 1.2em;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
  $(this).lazySelect({id: "PreviousDesignation", url: '<%=request.getContextPath()%>/designation/jsonList.htm', prefixCode: true,
    selectOnCode: $('#PreviousDesignationId').val()
  });
  $(this).lazySelect({id: "NextDesignation", url: '<%=request.getContextPath()%>/designation/jsonList.htm', prefixCode: true,
    selectOnCode: $('#NextDesignationId').val()
  });
  $(this).datePicker({id: 'EffectiveFrom'});

  $('#create').click(function() {
    validateEmployeeAttribute('CREATE',
        '<%=request.getContextPath()%>/employee-attribute/doValidate.htm', 'EmployeePromotion',
        getEmployeePromotion(), employeePromotionAttributeSequence);
  });

  $('#update').click(function() {
    validateEmployeeAttribute('UPDATE',
        '<%=request.getContextPath()%>/employee-attribute/doValidate.htm', 'EmployeePromotion',
        getEmployeePromotion(), employeePromotionAttributeSequence);
  });
});

function getEmployeePromotion() {
    var EmployeePromotion = new Object();
    EmployeePromotion.EmployeePromotionId=$('#EmployeePromotionId').val();
    EmployeePromotion.PriorDesignationId=$('#PreviousDesignation').val();
    EmployeePromotion.PriorDesignation=$('#PreviousDesignation option:selected').text();
    EmployeePromotion.CurrentDesignationId=$('#NextDesignation').val();
    EmployeePromotion.CurrentDesignation=$('#NextDesignation option:selected').text();
    EmployeePromotion.EffectiveFrom=$('#EffectiveFrom').val();
    return EmployeePromotion;
}
</script>

<c:if test="${EmployeePromotion == null}">
<table class="formTable_Data">
  <tr>
    <td width="40%" class="label">Previous Designation<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <select id="PreviousDesignation" class="chosen-select">
        <option value="0">Select</option>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Next Designation<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <select id="NextDesignation" class="chosen-select">
        <option value="0">Select</option>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Effective From<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input id="EffectiveFrom" type="text" class="datepicker" />
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <input type="hidden" id="EmployeePromotionId" value="0" />
      <input type="button" id="create" value='<spring:message code="common.create"/>' />
    </td>
  </tr>
</table>
</c:if>

<c:if test="${EmployeePromotion != null}">
<table class="formTable_Data">
  <tr>
    <td width="40%" class="label">Previous Designation<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="hidden" id="PreviousDesignationId" value="${EmployeePromotion.priorDesignation.designationId}" />
      <select id="PreviousDesignation" class="chosen-select">
        <option value="0">Select</option>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Next Designation<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="hidden" id="NextDesignationId" value="${EmployeePromotion.currentDesignation.designationId}" />
      <select id="NextDesignation" class="chosen-select">
        <option value="0">Select</option>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Effective From<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input id="EffectiveFrom" type="text" class="datepicker" value="${EmployeePromotion.effectiveFrom}" />
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <input type="hidden" id="EmployeePromotionId" value="${EmployeePromotion.promotionId}" />
      <input type="button" id="update" value='<spring:message code="common.update"/>' />
    </td>
  </tr>
</table>
</c:if>