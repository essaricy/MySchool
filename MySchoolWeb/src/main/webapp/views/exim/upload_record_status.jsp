<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script>
$(document).ready(function() {
    $('#uploadRecordTrackerTable').dataTable({
            "bPaginate": true,
            "bAutoWidth" : false,
            "aoColumnDefs": [ {
                "bSearchable": false,
                "bVisible": false,
                "aTargets": [ ]
            }],
            "sPaginationType": "full_numbers"
    });
});
</script>
<div style="height:400px;overflow:scroll;">
<c:if test="${UploadRecordTrackers == null}">
    <p class="error">No records found.</p>
</c:if>
<c:if test="${UploadRecordTrackers != null}">
    <table id="uploadRecordTrackerTable" width="100%">
        <thead>
            <tr>
                <th width="1px">&nbsp;</td>
                <th width="50px">Record #</th>
                <th>Record Data</th>
                <th>Remarks</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="UploadRecordTracker" items="${UploadRecordTrackers}">
            <tr>
                <c:if test="${UploadRecordTracker.uploadStatus == 'FAILED'}">
                    <td width="1px" style="background-color:red;">&nbsp;</td>
                </c:if>
                <c:if test="${UploadRecordTracker.uploadStatus == 'COMPLETED'}">
                    <td width="1px" style="background-color: #59E817;">&nbsp;</td>
                </c:if>
                <td width="50px">${UploadRecordTracker.recordNumber}</td>
                <td>${UploadRecordTracker.recordData}</td>
                <td>${UploadRecordTracker.remarks}</td>
            </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>
</div>