<c:if test="${student == null}">
<table border="0" cellpadding="6" cellspacing="0" align="center" id="awarenessForm" class="formTable">
	<tr>
		<td colspan="100%">
			<h3><spring:message code="student.register.checklist.title"/></h3>
		</td>
	</tr>
	<tr>
		<td width="2%">&nbsp;</td>
		<td width="2%"><input name="awareCheck_1" type="checkbox" /></td>
		<td width="96%"><label class="mandatory">*</label><spring:message code="student.register.checklist.school.recognition"/></td>
	</tr>
	<tr>
		<td width="2%">&nbsp;</td>
		<td width="2%"><input name="awareCheck_2" type="checkbox" /></td>
		<td width="96%"><label class="mandatory">*</label><spring:message code="student.register.checklist.school.affiliation"/></td>
	</tr>
	<tr>
		<td width="2%">&nbsp;</td>
		<td width="2%"><input name="awareCheck_3" type="checkbox" /></td>
		<td width="96%"><label class="mandatory">*</label><spring:message code="student.register.checklist.syllabus"/></td>
	</tr>
	<tr>
		<td width="2%">&nbsp;</td>
		<td width="2%"><input name="awareCheck_4" type="checkbox" /></td>
		<td width="96%"><label class="mandatory">*</label><spring:message code="student.register.checklist.examination.pattern"/></td>
	</tr>
	<tr>
		<td width="2%">&nbsp;</td>
		<td width="2%"><input name="awareCheck_5" type="checkbox" /></td>
		<td width="96%"><label class="mandatory">*</label><spring:message code="student.register.checklist.school.timing"/></td>
	</tr>
	<tr>
		<td width="2%">&nbsp;</td>
		<td width="2%"><input name="awareCheck_6" type="checkbox" /></td>
		<td width="96%"><label class="mandatory">*</label><spring:message code="student.register.checklist.school.fee.particular"/></td>
	</tr>
	<tr>
		<td width="2%">&nbsp;</td>
		<td width="2%"><input name="awareCheck_7" type="checkbox" /></td>
		<td width="96%"><label class="mandatory">*</label><spring:message code="student.register.checklist.school.fee.payment.days"/></td>
	</tr>
</table>
</c:if>


<c:if test="${student != null}">
<table border="0" cellpadding="6" cellspacing="0" align="center" id="awarenessForm" class="formTable">
	<tr>
		<td colspan="100%">
			<h3><spring:message code="student.register.checklist.title"/></h3>
		</td>
	</tr>
	<tr>
		<td width="2%">&nbsp;</td>
		<td width="2%"><input name="awareCheck_1" type="checkbox" checked /></td>
		<td width="96%">
			<c:if test="${viewOnly != 'true'}">
				<label class="mandatory">*</label>
			</c:if>
		<spring:message code="student.register.checklist.school.recognition"/></td>
		</td>
	</tr>
	<tr>
		<td width="2%">&nbsp;</td>
		<td width="2%"><input name="awareCheck_2" type="checkbox" checked /></td>
		<td width="96%">
			<c:if test="${viewOnly != 'true'}">
				<label class="mandatory">*</label>
			</c:if>
		<spring:message code="student.register.checklist.school.affiliation"/></td>
		</td>
	</tr>
	<tr>
		<td width="2%">&nbsp;</td>
		<td width="2%"><input name="awareCheck_3" type="checkbox" checked /></td>
		<td width="96%">
			<c:if test="${viewOnly != 'true'}">
				<label class="mandatory">*</label>
			</c:if>
		<spring:message code="student.register.checklist.syllabus"/></td>
		</td>
	</tr>
	<tr>
		<td width="2%">&nbsp;</td>
		<td width="2%"><input name="awareCheck_4" type="checkbox" checked /></td>
		<td width="96%">
			<c:if test="${viewOnly != 'true'}">
				<label class="mandatory">*</label>
			</c:if>
			<spring:message code="student.register.checklist.examination.pattern"/></td>
			</td>
	</tr>
	<tr>
		<td width="2%">&nbsp;</td>
		<td width="2%"><input name="awareCheck_5" type="checkbox" checked /></td>
		<td width="96%">
			<c:if test="${viewOnly != 'true'}">
				<label class="mandatory">*</label>
			</c:if>
			<spring:message code="student.register.checklist.school.timing"/></td>
		</td>
	</tr>
	<tr>
		<td width="2%">&nbsp;</td>
		<td width="2%"><input name="awareCheck_6" type="checkbox" checked /></td>
		<td width="96%">
			<c:if test="${viewOnly != 'true'}">
				<label class="mandatory">*</label>
			</c:if>
			<spring:message code="student.register.checklist.school.fee.particular"/></td>
		</td>
	</tr>
	<tr>
		<td width="2%">&nbsp;</td>
		<td width="2%"><input name="awareCheck_7" type="checkbox" checked /></td>
		<td width="96%">
			<c:if test="${viewOnly != 'true'}">
				<label class="mandatory">*</label>
			</c:if>
			<spring:message code="student.register.checklist.school.fee.payment.days"/></td>
		</td>
	</tr>
</table>
</c:if>