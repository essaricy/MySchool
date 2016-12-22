<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/styles/myschool-sliders.css" />
<style>
.GallerySmallImage {
  margin-left: 15px;
  margin-right: 15px;
  margin-bottom: 10px;
  border:1px solid #888;
  box-shadow: 5px 5px 5px #888;
  cursor: pointer;
}
</style>

<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/widgets/jquery.jssor.slider/js/jssor.slider-21.1.5.mini.js"></script>
<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/scripts/myschool-sliders.js"></script>

<script>
$(document).ready(function() {
  $.ajax({
    url: "<%=request.getContextPath()%>/gallery/getLatestGallery.htm",
    data: {
      sid: new Date().getTime()
    },
    dataType: 'json',
    context: document.body,
    success: function(result) {
      var noGalleryHtml = '<p style="font-weight: bold; color: red; ">Watch this space for latest gallery</p>';
      if (result == null || result.Gallery == null || result.Gallery == 'undefined') {
        $('#LatestGallery').html(noGalleryHtml);
      } else {
        var gallery = result.Gallery;
        var galleryName = gallery.GalleryName;

        var galleryItems = result.Gallery.GalleryItems;
        if (galleryItems == null || galleryItems == 'undefined' || galleryItems.length == 0) {
          $('#LatestGallery').html(noGalleryHtml);
        } else {
          var sliderId = 'jssor_gallery';
          var gallerySlider = $('<div id="' + sliderId + '" class="jssor-container">');
          var loading = $('<div data-u="loading" style="position: absolute; top: 0px; left: 0px;"><div style="filter: alpha(opacity=70); opacity: 0.7; position: absolute; display: block; top: 0px; left: 0px; width: 100%; height: 100%;"></div><div style="position:absolute;display:block;background-color: gray;top:0px;left:0px;width:100%;height:100%;"></div></div>');
          var slides = $('<div data-u="slides" class="jssor-slides">');

          gallerySlider.append(loading);
          gallerySlider.append(slides);

          showGalleryItems(galleryName, gallery.GalleryItems, slides);

          $('#LatestGallery').append(gallerySlider);
          $('#LatestGallery').append('<a href="<%=request.getContextPath()%>/public/gallery.htm" style="color: #55ABDA; text-align: right;">Click here to view all galleries »</a>');
          createInlineSlider(sliderId);
        }
      }
    }
  });

  function showGalleryItems(galleryName, galleryItems, gallerySlider) {
    if (galleryItems != null && galleryItems.length != 0) {
      $.each(galleryItems, function(index, galleryItem) {
        var imageContainer = $('<div style="display: none;">');
        //imageContainer.attr('class', 'ImageContainer');

        //var galleryItemName = galleryItem.GalleryName;
        var image = $('<img>');
        image.attr('class', 'GallerySmallImage');
        //alert('URL=' + galleryItem.Url + '\nPassport=' + galleryItem.Passport + '\nThumbnail=' + galleryItem.Thumbnail);
        image.attr('src', galleryItem.Passport);
        image.click(function() {
          openWindow('<%=request.getContextPath()%>/image/slideshow.htm?GalleryName=' + galleryName + '&Selection=' + (index++) + '&sid=' + new Date().getTime(), 'Slide Show of [ ' + galleryName + ' ]', $(window).width()-20, $(window).height()-20);
        });
        imageContainer.append(image);
        gallerySlider.append(imageContainer);
      });
    }
  }
});
</script>

<!-- ####################################### GALLERY ############################################### -->
<div id="LatestGallery">
  <h2 class="formHeading">Latest Gallery</h2>
</div>
