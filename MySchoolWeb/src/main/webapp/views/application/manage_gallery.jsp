<style>
#LayoutContainer {
  position: relative;
  width: 100%;
  height: 100%;
}
#TopContainer {
  position: absolute;
  height: 40px;
  width: 80%;
  left: 20%;
  border: 1px solid #DDD;
  background-color: #EEE;
}
#ControlsContainer {
  position: relative;
  top: 50%;
  text-align: right;
  margin-right: 20px;
  transform: translateY(-50%);
  background-color: #EEE;
}
#LeftContainer {
  position: absolute;
  width: 20%;
  height: 480px;
  top: 0px;
  left: 0px;
  bottom: 0px;
  border: 1px solid #DDD;
  background-color: #EEE;
  overflow: scroll;
}
#CenterContainer {
  position: absolute;
  width: 80%;
  top: 40px;
  left: 20%;
  right: 0px;
  bottom: 0px;
  border: 1px solid #DDD;
  overflow: scroll;
}
#GalleriesList {
  list-style-type: none;
  margin: 0;
  padding: 0;
  width: 99%;
}
#GalleriesList li {
  margin: 1px;
  padding: 0.3em;
  font-size: 1em;
}
#GalleriesList li:hover {
  cursor: pointer;
}
.image-selected {}
.upload-error {
  font-size: 10px;
  font-weight:bold;
  color:red;
  text-align: left;
  font-style:italic;
}
.upload-success {
  font-size: 10px;
  font-weight:bold;
  color:green;
  text-align: left;
  font-style:italic;
}
</style>

<script src="<%=request.getContextPath()%>/widgets/jquery.jrumble.1.3/jquery.jrumble.1.3.min.js"></script>
<script>
var SHOW_UPLOAD_CONTAINER=1;
var SHOW_GALLERY_ITEMS_CONTAINER=2;
var uploader=null;
var currentGalleryName=null;
var galleries=new Array();
var addImageStatusElements=null;
var addImageStatus=null;
var newGallertNameInput=null;
var pin='<img src="<%=request.getContextPath()%>/images/icons/red_pin.png" width="16" height="16" style="float: right;"/>';

$(document).ready(function() {
  $('#LeftContainer').height($(window).height()-140);
  $('#CenterContainer').height($(window).height()-140-40);
  $("#AddImagesContainer").hide();
  $("#NoGalleriesNoteContainer").hide();

  // Make the gallery options selectable.
  $("#GalleriesList").selectable({
    /*stop:function(event, ui) {
      $(event.target).children('.ui-selected').not(':first').removeClass('ui-selected');
      $(".ui-selected", this).each(function () {
        var index = $("#GalleriesList li").index(this);
        currentGalleryName=$(this).text();
      });
    },
    selecting: function(event, ui) {
      $(newGallertNameInput).hide();
      if( $(".ui-selected, .ui-selecting").length > 1){
        $(ui.selecting).removeClass("ui-selecting");
      }
    },*/
    selected: function( event, ui ) {
      $(ui.selected).addClass("ui-selected").siblings().removeClass("ui-selected");
      currentGalleryName=$(ui.selected).text();
      updateUI(currentGalleryName);
      //alert($(ui.selected).text());
      /*$('#GalleriesList li').each(function(index, value) {
        // If selected then display the gallery items init.
        if ($(this).hasClass('ui-selected')) {
          $(this).siblings().removeClass("ui-selected");
          updateUI($(this).attr('value'));
        }
      });*/
    }
  });
  //$('#GalleriesList li:first').addClass('ui-selected');

  // Display the list of galleries on the left panel.
  $.ajax({
    url: "<%=request.getContextPath()%>/gallery/jsonListGalleries.htm",
    data: {
      sid: new Date().getTime()
    },
    dataType: 'json',
    context: document.body,
    success: function(result) {
      if (result == null || result.Galleries == null || result.Galleries == 'undefined' || result.Galleries.length == 0) {
        $("#NoGalleriesNoteContainer").show();
      } else {
        galleries=result.Galleries;
        if (galleries != null && galleries.length != 0) {
          for (var index=0; index<galleries.length; index++) {
            var gallery=galleries[index];
            var galleryName=gallery['GalleryName'];
            var pinned=gallery['Pinned'];
            var li=null;

            if (pinned=='Y') {
                li='<li class="ui-widget-content" value="' + galleryName + '">' + pin + galleryName + '</li>';
            } else {
                li='<li class="ui-widget-content" value="' + galleryName + '">' + galleryName + '</li>';
            }
            $('#GalleriesList').append(li);
          }
        }
        updateUI(null);
      }
    }
  });

  // Get the gallery image item.
  function getImage(galleryName, galleryItem) {
    var image = $('<img>');
    image.attr('class', 'GallerySmallImage');
    image.attr('name', galleryItem['GalleryName']);
    image.attr('src', galleryItem['Passport']);
    $(image).jrumble({speed: 100});
    image.click(function() {
      if ($(this).hasClass('image-selected')) {
        $(this).removeClass('image-selected')
        $(this).trigger('stopRumble');
      } else {
        $(this).addClass('image-selected');
        $(this).trigger('startRumble');
      }
    });
    image.fadeIn(1000);
    return image;
  }

  function showView(mode) {
    if (mode == SHOW_UPLOAD_CONTAINER) {
      if (uploader != null) {
        uploader = $('#UploadContainer').pluploadQueue();
        uploader.destroy();
      }
      initUploadContainer();
      $("#GalleryItemsContainer").slideUp(1000);
      $("#AddImagesContainer").slideDown(1000);
    } else if (mode == SHOW_GALLERY_ITEMS_CONTAINER) {
      $("#AddImagesContainer").slideUp(1000);
      $("#GalleryItemsContainer").slideDown(1000);
    }
  }

  // Initiate Upload Container
  function initUploadContainer() {
    addImageStatusElements = new Array();
    addImageStatus = new Array();

    $("#UploadContainer").pluploadQueue({
      // General settings
      runtimes : 'html5',
      multipart: true,
      file_data_name: 'uploadFile',
      multipart_params: {multiUploadId:'', uploadName: ''},
      url : '<%=request.getContextPath()%>/gallery/doAddGalleryItem.htm',
      // TODO get file size limit from the server
      max_file_size : '5242880b',
      chunk_size : '5242880b',
      unique_names : true,
      filters : [
        {title : "Images", extensions : "jpg,jpeg,png"}
      ],
      extra_columns: [
      {
        column_name: 'Result',
        column_html: '<span class="add-image-status">...</span>',
        column_css: "float: left; overflow: hidden; width: 300px;",
      }
      ]
    });
    var pluploader = $('#UploadContainer').pluploadQueue();
    pluploader.bind('BeforeUpload', function (up, file) {
      $.extend(up.settings.multipart_params, {
        multiUploadId: currentGalleryName,
        uploadName: currentGalleryName
      });
    });
    pluploader.bind('FileUploaded', function(up, file, response) {
      var result = $.parseJSON(response.response);
      if (result != null && result.Successful) {
        notifySuccess(result.StatusMessage);
      } else {
        attendError(result.StatusMessage);
      }
      // Capture the status into the array to update
      addImageStatus[addImageStatus.length]=result;
    });
    pluploader.bind('UploadComplete', function(up, files) {
      // Update the status of each file
      var gallery=getGalleryByName(currentGalleryName);
      if (gallery != null) {
          var galleryItems=gallery['GalleryItems'];
          if (galleryItems == null) {
              galleryItems=new Array();
              gallery['GalleryItems']=galleryItems;
          }
          for (var index=0; index<files.length; index++) {
              var file=files[index];
              var addImageStatusElement=$('#' + file.id).find('.add-image-status');
              var result=addImageStatus[index];
              $(addImageStatusElement).text(result.StatusMessage);
              if (result.Successful) {
                $(addImageStatusElement).addClass('upload-success');
                // add gallery item to the local map
                var galleryItem=new Object();
                galleryItem['GalleryName']=file.name;
                var reference=result['Reference'];
                if (reference != null && reference != 'undefined') {
                    galleryItem['Passport']=reference['Passport'];
                }
                galleryItems[galleryItems.length]=galleryItem;
              } else {
                $(addImageStatusElement).addClass('upload-error');
              }
          }
      }
    });
  }

  // New Gallery
  $('#NewGallery').click(function() {
    promptText('Gallery Name', '', function(galleryName) {
        currentGalleryName=galleryName;
        performOperations('CREATE_GALLERY');
    });
  });

  $('#AddImages').click(function() {
    if(currentGalleryName == null) {
      error('There are no galleries present to add images');
      return;
    }
    showView(SHOW_UPLOAD_CONTAINER);
  });

  $('#DeleteImages').click(function() {
    var selectedImages = $('.image-selected');
    if (selectedImages.length == 0) {
      error('There are no images selected to delete');
      return;
    }
    performOperations('DELETE_IMAGES');
  });

  $('#DeleteGallery').click(function() {
    performOperations('DELETE_GALLERY');
  });

  $('#PinGallery').click(function() {
    performOperations('PIN_GALLERY');
  });

  function deselectImages() {
    var selectedImages = $('.image-selected');
    for (var index=0; index<selectedImages.length; index++) {
      var selectedImage=selectedImages[index];
      $(selectedImage).removeClass('image-selected')
      $(selectedImage).trigger('stopRumble');
    }
  }
  var operationsData=new Object();
  operationsData.CREATE_GALLERY=new Object();
  operationsData.CREATE_GALLERY.emptyMessage=null;
  operationsData.CREATE_GALLERY.confirmMessage=null;
  operationsData.CREATE_GALLERY.url='<%=request.getContextPath()%>/gallery/doCreateGallery.htm';
  operationsData.CREATE_GALLERY.successCallback=function() {
    $("#NoGalleriesNoteContainer").hide();
    var gallery = new Object();
    gallery['GalleryName']=currentGalleryName;
    gallery['GalleryItems']=null;
    galleries[galleries.length]=gallery;
    $('#GalleriesList').append('<li class="ui-widget-content" value="' + currentGalleryName + '">' + currentGalleryName + '</li>');
  };
  operationsData.DELETE_GALLERY=new Object();
  operationsData.DELETE_GALLERY.emptyMessage='There are no galleries present to delete';
  operationsData.DELETE_GALLERY.confirmMessage="Deleting the gallery will delete all the images in it. <br/>Are you sure to delete the gallery 'gallery_name'?";
  operationsData.DELETE_GALLERY.url='<%=request.getContextPath()%>/gallery/doDeleteGallery.htm';
  operationsData.DELETE_GALLERY.successCallback=function() {
    // Get the gallery items by the name
    for (var index=0; index<galleries.length; index++) {
      var gallery = galleries[index];
      if (gallery!= null && currentGalleryName == gallery['GalleryName']) {
        delete galleries[index];
      }
    }
    $('#GalleriesList li:contains(' + currentGalleryName + ')').remove();
    currentGalleryName=null;
  };
  operationsData.PIN_GALLERY=new Object();
  operationsData.PIN_GALLERY.emptyMessage='There are no galleries present to pin';
  operationsData.PIN_GALLERY.confirmMessage="Do you want to pin the gallery 'gallery_name'?";
  operationsData.PIN_GALLERY.url='<%=request.getContextPath()%>/gallery/pinGallery.htm';
  operationsData.PIN_GALLERY.successCallback=function() {
      $('#GalleriesList li img').each(function(index, img) {
          $(img).remove();
      });
      //$('#GalleriesList li:contains(' + currentGalleryName + ')').append(pin);
      $('#GalleriesList li:contains(' + currentGalleryName + ')').html(pin + currentGalleryName);
    for (var index=0; index<galleries.length; index++) {
      var gallery = galleries[index];
      if (gallery!= null) {
        var galleryName=gallery['GalleryName'];
        if (galleryName == currentGalleryName) {
            gallery['Pinned']='Y';
        } else {
            gallery['Pinned']='N';
        }
      }
    }
  };

  operationsData.DELETE_IMAGES=new Object();
  operationsData.DELETE_IMAGES.emptyMessage='There are no galleries present to delete images';
  operationsData.DELETE_IMAGES.confirmMessage="Do you want to delete the selected images from the gallery 'gallery_name'?";
  operationsData.DELETE_IMAGES.url='<%=request.getContextPath()%>/gallery/doDeleteGalleryItems.htm';
  operationsData.DELETE_IMAGES.confirmNoCallback=function() { deselectImages(); }
  operationsData.DELETE_IMAGES.failureCallback=function() { deselectImages(); }
  operationsData.DELETE_IMAGES.successCallback=function() {
    var gallery=getGalleryByName(currentGalleryName);
    if (gallery != null) {
      var galleryItems=gallery['GalleryItems'];
      if (galleryItems != null && galleryItems.length != 0) {
        var selectedImages = $('.image-selected');
        for (var index=0; index<selectedImages.length; index++) {
          var selectedImage=selectedImages[index];
          var selectedImageName=$(selectedImage).attr('name');
          $(selectedImage).remove();
          for (var jindex=0; jindex<galleryItems.length; jindex++) {
            var galleryItem = galleryItems[jindex];
            if (galleryItem != null) {
              var galleryItemName = galleryItem['GalleryName'];
              if (selectedImageName.toLowerCase() === galleryItemName.toLowerCase()) {
                delete galleryItems[jindex];
                break;
              }
            }
          }
        }
      }
    }
  };

  function performOperations(operation) {
    var operationData=operationsData[operation];
    var emptyMessage=operationData['emptyMessage'];
    var confirmMessage=operationData['confirmMessage'];
    var successCallback=operationData['successCallback'];
    var failureCallback=operationData['failureCallback'];
    var confirmNoCallback= operationData['confirmNoCallback'];
    var url=operationData['url'];
    var data={GalleryName: currentGalleryName};

    if(emptyMessage != null && $.isEmptyObject(galleries)) {
      error(emptyMessage);
      return;
    }
    if (currentGalleryName == null || $.trim(currentGalleryName) == '') {
      return;
    }
    if (confirmMessage == null) {
      sendRequest(url, data, function(result) {processResponse(result, successCallback)});
    } else {
      confirmMessage=confirmMessage.replace('gallery_name', currentGalleryName);
      interactConfirm(confirmMessage,
        function () {
          if (operation == 'DELETE_IMAGES') {
            var imagesToDelete=new Array();
            var selectedImages = $('.image-selected');
            for (var index=0; index<selectedImages.length; index++) {
              var selectedImage=selectedImages[index];
              imagesToDelete[imagesToDelete.length]=$(selectedImage).attr('name');
            }
            data['GalleryItems']=JSON.stringify(imagesToDelete);
          }
          sendRequest(url, data, function(result) {processResponse(result, successCallback, failureCallback)});
        }, // Confirm message close
      function() {
        if (confirmNoCallback != null) {
          confirmNoCallback.call();
        }
      });
    }
  }

  function sendRequest(url, data, callBack) {
    if (data != null) {
      data.sid=new Date().getTime();
    }
    $.ajax({
      url: url,
      data: data,
      dataType: 'json',
      context: document.body,
      success: callBack
    });
  }

  function processResponse(result, successCallback, failureCallback) {
    if (result == null || result.Successful == null) {
      attendError('Unable to perform the operation now.');
    } else if (result.Successful) {
      notifySuccess(result.StatusMessage);
      if (successCallback != null) {
        successCallback.call();
        updateUI(currentGalleryName);
      }
    } else {
      attendError(result.StatusMessage);
      if (failureCallback != null) {
        failureCallback.call();
      }
    }
  }

  function updateUI(galleryNameToSelect) {
    if (galleryNameToSelect == null) {
      // If there are no items present then select the first item
      var firstGallery=$('#GalleriesList li').first();
      if (firstGallery != null) {
        galleryNameToSelect=$(firstGallery).text();
      }
    }
    var gallery=getGalleryByName(galleryNameToSelect);
    $('#GalleryItemsContainer').html('<br/>');
    if (gallery == null) {
      $('#AddImagesContainer').hide();
      $("#NoGalleriesNoteContainer").show();
    } else {
      var galleryName=gallery['GalleryName'];
      var galleryItems=gallery['GalleryItems'];
      // Select the gallery and deselect all other galleries
      var galleryToSelect=$('#GalleriesList li:eq(' + galleryNameToSelect + ')');
      $(galleryToSelect).siblings().removeClass("ui-selected");
      $(galleryToSelect).addClass('ui-selected');
      if (galleryItems == null || galleryItems.length == 0) {
        // Display add images screen
        showView(SHOW_UPLOAD_CONTAINER);
      } else {
        for (var index=0; index<galleryItems.length; index++) {
          var galleryItem = galleryItems[index];
          if (galleryItem != null) {
            var image = getImage(galleryName, galleryItem);
            $('#GalleryItemsContainer').append(image);
          }
        }
        showView(SHOW_GALLERY_ITEMS_CONTAINER);
      }
      currentGalleryName=galleryName;
    }
  }

  function getGalleryByName(galleryName) {
    // Get the gallery items by the name
    for (var index=0; index<galleries.length; index++) {
      var gallery = galleries[index];
      if (gallery!= null && galleryName == gallery['GalleryName']) {
        return gallery;
      }
    }
    return null;
  }
});
</script>

<div id="LayoutContainer">
  <div id="TopContainer">
    <div id="ControlsContainer">
      <input id="NewGallery" type="button" value="New Gallery" />
      <input id="AddImages" type="button" value="Add Images" />
      <input id="DeleteImages" type="button" value="Delete Images" />
      <input id="DeleteGallery" type="button" value="Delete Gallery" />
      <input id="PinGallery" type="button" value="Pin Gallery" />
    </div>
  </div>
  <div id="LeftContainer">
    <ol id="GalleriesList">
    </ol>
  </div>
  <div id="CenterContainer">
    <div id="GalleryItemsContainer"></div>
    <div id="AddImagesContainer">
      <div id="UploadContainer">
        <p>You browser doesn't have HTML5 support.</p>
      </div>
      <div id="UploadNote" class="success" style="padding-left: 5x; font-style:italic;">
        <p>* Please be patient and wait after clicking 'Start Upload' till all the files in are uploaded.</p>
        <p>Interrupting will fail to process the files in queue.</p>
        <p>Total time to upload all files depends on the size of the files.</p>
      </div>
    </div>
    <div id="NoGalleriesNoteContainer" class="error" style="padding-left: 5x; font-style:italic;">
      <p>There are no galleries present at this time.</p>
      <p>Click on 'New Gallery' to add galleries and then click on 'Add Images'</p>
    </div> 
  </div>
</div>
