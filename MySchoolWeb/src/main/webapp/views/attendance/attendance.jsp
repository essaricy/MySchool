<%@ taglib tagdir="/WEB-INF/tags" prefix="myschool" %>

<style type='text/css'>
#calendar {
	width: 900px;
	margin: 0 auto;
}
</style>
<script type='text/javascript'>

	$(document).ready(function() {

		$('#calendar').fullCalendar({
			theme: true,
			header: {
				left: 'month,basicWeek',
				center: 'title',
				right: 'prev,next today'
			},
			editable: false,
			eventSources: [
				// All general Holidays go here.
				{
					events: [
						<c:forEach var="holiday" items="${holidays}" varStatus="holidaysStatus">
						{
							title: '${holiday.holidayName}',
							start: '${fn:substring(holiday.startDate, 6, 10)}/${fn:substring(holiday.startDate, 3, 5)}/${fn:substring(holiday.startDate, 0, 2)}',
							end: '${fn:substring(holiday.startDate, 6, 10)}/${fn:substring(holiday.startDate, 3, 5)}/${fn:substring(holiday.startDate, 0, 2)}',
							allDay: true
						}
						<c:if test="${not holidaysStatus.last}">,</c:if>
						</c:forEach>
					],
		      backgroundColor: '#FFFF00',
		      textColor: '#0000FF'
				}
			]
		});
	
	});

</script>

<table class="formTable_Container" id="attendance">
	<tr>
		<td><div id='calendar'></div></td>
	</tr>
</table>

