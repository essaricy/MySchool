<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/widgets/jquery.fileDownload/jquery.fileDownload.js"></script>
<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
  $('#importTypeContainer').hide();
  $('.chosen-select').chosen({width: "100%"});

  /* Add a click handler to the rows - this could be used as a callback */
  $("#downloadTemplate").click(function() {
    var importKey = $('#EximPolicies').val();
    $.fileDownload(
      '<%=request.getContextPath()%>/upload/downloadTemplate.htm?importKey=' + importKey,
      {
        successCallback: function (url) {
            alert(url);
          info('Your download has been completed.');
        },
        failCallback: function (html, url) {
          error('We are unable to download '+ importKey + ' template now. Please try later.');
        }
      }
    );
  });

  $("#viewDDD").click(function() {
    document.forms[0].action = '<%=request.getContextPath()%>/images/functional/DDD.jpg';
    document.forms[0].target= '_blank';
    document.forms[0].submit();
  });

<c:if test="${PAGE_ACCESS != null && PAGE_ACCESS.create}">

  var extra_column_html = '';
  <c:if test="${exims != null}">
    <c:forEach var="exim" items="${exims}">
      <c:if test="${exim != null && exim != ''}">
        extra_column_html += '<option value="${exim.eximPolicy}">${exim.description}</option>';
      </c:if>
    </c:forEach>
  </c:if>
  extra_column_html = '<select class="chosen-select plupload_file_type">' + extra_column_html + '</select>';

  // Setup html5 version
  $("#UploadContainer").pluploadQueue({
    // General settings
    runtimes : 'html5',
    multipart: true,
    file_data_name: 'uploadFile',
    multipart_params: {multiUploadId:'', uploadName: ''},
    url : '<%=request.getContextPath()%>/upload/uploadDataFile.htm',
    // TODO get file size limit from the server
    max_file_size : '5242880b',
    chunk_size : '5242880b',
    unique_names : true,
    // Resize images on clientside if we can
    resize : {width : 320, height : 240, quality : 90},
    // Specify what files to browse for
    filters : [
      {title : "Excel files", extensions : "xls"}
    ],
    extra_columns: [
      {
        column_name: 'File Type',
        column_html: extra_column_html,
        //column_css: "float: left; overflow: hidden; width: 300px; border:1px solid red;"
        column_css: "float: left; overflow: hidden; width: 300px;",
      }
    ]
  });

  var uploader = $('#UploadContainer').pluploadQueue();
  var uploadTrackerId = null;
  var validated = false;
  var BeforeUpload = function (up, file) {
    validated = validateUpload(up, file);
    if (validated) {
      if (uploadTrackerId == null) {
        $.ajax({
          url: '<%=request.getContextPath()%>/upload/generateUploadTracker.htm',
          dataType: 'json',
          data: {sid: new Date().getTime()},
          async: false,
          success: function(respnose) {
            uploadTrackerId = respnose.uploadTrackerId;
          }
        });
        if (uploadTrackerId == null) {
          error('Unable to create upload tracker. Please try later.');
          return false;
        }
      }
      //console.log($('#' + file.id).find('.plupload_file_type >option:selected').val());
      var uploadName = $('#' + file.id).find('.plupload_file_type >option:selected').val();
      $.extend(up.settings.multipart_params, {
        multiUploadId: uploadTrackerId,
        uploadName: uploadName
      });
      return true;
    } else {
      uploader.state = plupload.STARTED + 1;
      $('.plupload_upload_status').hide();
      $('a.plupload_stop,div.plupload_progress').hide();
      $('li.plupload_delete a,div.plupload_buttons').show();
      return false;
    }
  };

  uploader.bind('FilesAdded', function(up, files) {
      /*for (var index=0; index<files.length; index++) {
          var file = files[index];
          $('#' + file.id).find('.plupload_file_type').chosen({width: "100%"});
      }*/
  });

  uploader.bind('BeforeUpload', BeforeUpload);
  uploader.bind('FileUploaded', function(up, file, res) {
    if(this.total.queued == 0) {
      $.ajax({
        url: '<%=request.getContextPath()%>/upload/startUploadDataProcess.htm',
        dataType: 'json',
        data: {
          uploadTrackerId: uploadTrackerId,
          sid: new Date().getTime(),
        },
        async: false,
        success: function(respnose) {
          uploadTrackerId = respnose.uploadTrackerId;
        }
      });
    }
  });
</c:if>

  function validateUpload(up, file) {
    var error = null;
    var loopIndex = 0;
    $.each($('.plupload_file_type > select'), function( index, selObject ) {
      if (error == null && $(this).val() == -1) {
        error = 'Please select the import type for the file "' + up.files[loopIndex].name + '"';
        $(this).focus();
      }
      loopIndex++;
    });
    if (error != null) {
      error_ac(error);
    }
    return (error == null);
  }

  $(this).myAccordion({id: 'UploadStatusAccordion'});

  var nowDate = null;
  $('#errorDiv').hide();
  var updateTrackers = function() {
    nowDate = new Date();
    $.get('<%=request.getContextPath()%>/upload/getUploadTrackers.htm',
      { sid: nowDate.getTime() },
      function(data) {
        $('#updateTime').html('[ Loading ]');
        var uploadTrackersContainer = jQuery('#UploadStatusAccordion').accordion();
        $(uploadTrackersContainer).empty();
        $(uploadTrackersContainer).accordion('destroy');
        if (data == null || data.UploadTrackers == null) {
          $('#errorDiv').text('No Uploads are pending or in progress.').show(450);
        } else {
          $('#errorDiv').hide();
          $.each(data.UploadTrackers, function(index, uploadTracker) {
            var trackerTitle = $('<p>');
            trackerTitle.attr('class', 'title');

            var statusText = '';
            var progressText = null;
            if (uploadTracker.UploadStatus == 'NOT_STARTED') {
              statusText = '<font style="color: black;"> Not Started</font>';
            } else if (uploadTracker.UploadStatus == 'STARTED') {
              statusText = '<font style="color: orange;">Started</font>';
              progressText = ', Progress: ' + getProgressText(uploadTracker.TotalFiles, uploadTracker.ProcessedFiles);
            } else if (uploadTracker.UploadStatus == 'COMPLETED') {
              statusText = '<font style="color: green;">Completed</font>';
              progressText = ', Progress: ' + getProgressText(uploadTracker.TotalFiles, uploadTracker.ProcessedFiles);
            } else if (uploadTracker.UploadStatus == 'FAILED') {
              statusText = '<font style="color: red;">Failed</font>';
              progressText = ', Progress: ' + getProgressText(uploadTracker.TotalFiles, uploadTracker.ProcessedFiles);
            }
            trackerTitle.append('Uploaded On: ').append(uploadTracker.UploadTimestamp);
            trackerTitle.append(', Status: ').append(statusText);
            trackerTitle.append(progressText);

            var trackerContainer =  $('<div>');
            trackerContainer.css('padding-top', '0px');
            trackerContainer.css('padding-left', '0px');
            addUploadFileTrackerContent(trackerContainer,uploadTracker.TrackerId);
            uploadTrackersContainer.append(trackerTitle);
            uploadTrackersContainer.append(trackerContainer);
          });

          $(this).myAccordion({id: 'UploadStatusAccordion'});
          /*jQuery(uploadTrackersContainer).accordion({
            collapsible: true,
            icons: icons,
            heightStyle: "content"
          });*/
        }
        $('#updateTime').html('[ '
          + $.datepicker.formatDate('yy-m-dd', nowDate)
          + ' '
          + nowDate.getHours()+ ":" + nowDate.getMinutes() + ":" + nowDate.getSeconds()
          + ' ]');
        },
      'json')
    .fail(function(data) {
      $('#errorDiv').text('Unable to retrieve upload progress information.').show(450);
    });
  };

  function addUploadFileTrackerContent(trackerContainer, trackerId) {
    $.ajax({
      url: '<%=request.getContextPath()%>/upload/getUploadFileTrackers.htm',
      dataType: 'json',
      data: {
        uploadTrackerId: trackerId,
        sid: nowDate.getTime()
      },
      async: false,
      success: function(data) {
        if (data == null || data.UploadFileTrackers == null) {
          var fileTrackerContainer = $('<div>');
          fileTrackerContainer.text('No Upload File Trackers available for this upload tracker.');
          trackerContainer.append(fileTrackerContainer);
        } else {
          $.each(data.UploadFileTrackers, function(index, uploadFileTracker) {
            var fileTrackerContainer = $('<div>');
            fileTrackerContainer.css('padding-top', '5px');
            fileTrackerContainer.css('padding-left', '10px');
            fileTrackerContainer.css('text-align', 'left');

            var fileTrackerTitle = $('<label>');
            fileTrackerTitle.css('font-size', '0.7em');
            fileTrackerTitle.css('color', '#666');
            fileTrackerTitle.html((index + 1) + ') '
                + 'Type: ' + uploadFileTracker.UploadType
                + ', File: <a href="#" onclick="showRecordStatus(' + uploadFileTracker.UploadFileId + ')">'
                + uploadFileTracker.FileName + '</a>');

            var fileTrackerProgress = $('<span>');
            fileTrackerProgress.css('float', 'right');
            fileTrackerProgress.css('width', '200px');
            fileTrackerProgress.css('valign', 'bottom');
            fileTrackerProgress.css('height', '4px');
            fileTrackerProgress.css('font-size', '0.7em');

            if (uploadFileTracker.UploadStatus == 'NOT_STARTED') {
              $(fileTrackerProgress).progressbar({
                value: false
              });
              $(fileTrackerProgress).addClass('waiting');
            } else if (uploadFileTracker.UploadStatus == 'STARTED') {
                if (uploadFileTracker.TotalRecords == 0 || uploadFileTracker.ProcessedRecords == 0) {
                    $(fileTrackerProgress).progressbar({
                value: false
              });
                } else {
                    $(fileTrackerProgress).progressbar({
                max: uploadFileTracker.TotalRecords,
                value: uploadFileTracker.ProcessedRecords
              });
                }
              
              $(fileTrackerProgress).addClass('processing');
            } else if (uploadFileTracker.UploadStatus == 'COMPLETED') {
              $(fileTrackerProgress).progressbar({
                max: uploadFileTracker.TotalRecords,
                value: uploadFileTracker.ProcessedRecords
              });
              $(fileTrackerProgress).addClass('completed');
            } else if (uploadFileTracker.UploadStatus == 'FAILED') {
              $(fileTrackerProgress).progressbar({
                max: uploadFileTracker.TotalRecords,
                value: uploadFileTracker.ProcessedRecords
              });
              $(fileTrackerProgress).addClass('failed');
            }
            fileTrackerContainer.append(fileTrackerTitle);
            fileTrackerContainer.append(fileTrackerProgress);
            trackerContainer.append(fileTrackerContainer);
          });
        }
      }
    });
  }

  $('#refresh').click(function() {
      updateTrackers();
  });
  updateTrackers();
  //window.setInterval(updateTrackers, 10000);

  function getProgressText(totalFiles, processedFiles) {
    var completedText = '';
    if (totalFiles == 0) {
    } else {
      if (processedFiles == 0) {
        completedText = 'File Processing started.';
      } else {
        var percentage = (processedFiles/totalFiles) * 100;
        completedText = processedFiles + ' of ' + totalFiles + ' [' + percentage + '%] completed.';
      }
    }
    return completedText;
  }
});

function showRecordStatus(fileTrackerId) {
    openDialog('<%=request.getContextPath()%>/upload/getUploadRecordTrackers.htm?fileTrackerId=' + fileTrackerId + '&sid=' + new Date().getTime(),
        'Upload File Status - Record Level', $(window).width() - 100, 500);
}
</script>
<style>
.uploadStatus {
    width: 98%;
}

.uploadStatus .heading {
    background-color: #383B3E;
    padding-top: 10px;
    padding-bottom: 10px;
}

.uploadStatus .heading .title {
    color: white;
    text-transform: uppercase;
    font-weight: bold;
    letter-spacing: 2px;
}

.uploadStatus .heading .updateTime {
    font-size: 0.7em;
    color: white;
    letter-spacing: 2px;
}

.uploadStatus #UploadStatusAccordion p {
    font-size: 0.7em;
    font-weight: bold;
    text-align: left;
}

.ui-progressbar.waiting .ui-progressbar-value { background: #AAA; }
.ui-progressbar.processing .ui-progressbar-value { background: #FFA500; }
.ui-progressbar.completed .ui-progressbar-value { background: #59E817; }
.ui-progressbar.failed .ui-progressbar-value { background: red; }
</style>

<div class="uploadStatus">
  <div class="heading">
    <div style="float: left; position:relative; padding-left: 10px;">
      <img src="<%=request.getContextPath()%>/images/icons/download.png" width="35" height="35" border="0"/>
    </div>
    <div class="title">Download Template</div>
    <div class="updateTime">Select any template from below list and click on "Download Template" to download the template.</div>
  </div>
  <p/>
  <table width="60%" cellpadding="10"cellspacing="0" align="center" border="0">
    <tr>
      <td width="50%">
        <select id="EximPolicies" class="chosen-select">
          <c:if test="${exims != null}">
            <c:forEach var="exim" items="${exims}">
              <option value="${exim.eximPolicy}">${exim.description}</option>
            </c:forEach>
          </c:if>
        </select>
      </td>
      <td>
        <input type="button" id="downloadTemplate" class="formButton" value="Download Template" />
      </td>
    </tr>
  </table>
</div>

<c:if test="${PAGE_ACCESS != null && PAGE_ACCESS.create}">
<div id="UploadContainer">
    <p>You browser doesn't have HTML5 support.</p>
</div>
</c:if>

<div class="uploadStatus">
  <div class="heading">
    <div style="float: left; position:relative; padding-left: 10px;">
      <a href="#UploadStatusAccordion" id="refresh">
        <img src="<%=request.getContextPath()%>/images/icons/reload.png" width="35" height="35" border="0"/>
      </a>
    </div>
    <div class="title">Upload status</div>
    <div class="updateTime" id="updateTime">[ Loading ]</div>
  </div>
  <div id="errorDiv" class="error"></div>
  <div id="UploadStatusAccordion"></div>
</div>

<div id="importTypeContainer">
  <select style="width: 100%;">
    <option value="-1">Select</option>
    <c:if test="${exims != null}">
      <c:forEach var="exim" items="${exims}">
        <option value="${exim.eximPolicy}">${exim.description}</option>
      </c:forEach>
    </c:if>
  </select>
</div>
