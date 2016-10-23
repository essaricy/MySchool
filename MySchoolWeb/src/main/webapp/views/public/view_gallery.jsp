<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/widgets/jquery.jssor.slider/js/jssor.slider-21.1.5.mini.js"></script>

<style>
.GallerySmallImage {
  margin-left: 15px;
  margin-right: 15px;
  margin-bottom: 10px;
  border:1px solid #888;
  box-shadow: 5px 5px 5px #888;
  cursor: pointer;
}
.ImageCheckBox {
  position:relative; right:5px; bottom:3px;
}
.GalleryName {
  margin: 5px 5px 5px 5px;
  padding: 0px 0px 0px 0px;
  border-width: 1px;
  border-style: solid;
  padding:3px 10px;
  text-decoration: none;
  text-align: left;
  font-size: 1em;
  font-weight: bold;
  letter-spacing: 1px;
  text-decoration: none;
  background-color: #DDD;
  color: #444;
}

/* jssor slider bullet navigator skin 03 css */
/*
.jssorb03 div           (normal)
.jssorb03 div:hover     (normal mouseover)
.jssorb03 .av           (active)
.jssorb03 .av:hover     (active mouseover)
.jssorb03 .dn           (mousedown)
*/
.jssorb03 {
  position: absolute;
}
.jssorb03 div, .jssorb03 div:hover, .jssorb03 .av {
  position: absolute;
  /* size of bullet elment */
  width: 21px;
  height: 21px;
  text-align: center;
  line-height: 21px;
  color: white;
  font-size: 12px;
  background: url('img/b03.png') no-repeat;
  overflow: hidden;
  cursor: pointer;
}
.jssorb03 div { background-position: -5px -4px; }
.jssorb03 div:hover, .jssorb03 .av:hover { background-position: -35px -4px; }
.jssorb03 .av { background-position: -65px -4px; }
.jssorb03 .dn, .jssorb03 .dn:hover { background-position: -95px -4px; }

/* jssor slider arrow navigator skin 03 css */
/*
.jssora03l                  (normal)
.jssora03r                  (normal)
.jssora03l:hover            (normal mouseover)
.jssora03r:hover            (normal mouseover)
.jssora03l.jssora03ldn      (mousedown)
.jssora03r.jssora03rdn      (mousedown)
*/
.jssora03l, .jssora03r {
  display: block;
  position: absolute;
  /* size of arrow element */
  width: 55px;
  height: 55px;
  cursor: pointer;
  background: url('img/a03.png') no-repeat;
  overflow: hidden;
}
.jssora03l { background-position: -3px -33px; }
.jssora03r { background-position: -63px -33px; }
.jssora03l:hover { background-position: -123px -33px; }
.jssora03r:hover { background-position: -183px -33px; }
.jssora03l.jssora03ldn { background-position: -243px -33px; }
.jssora03r.jssora03rdn { background-position: -303px -33px; }

.jssor-container {
  position: relative;
  margin: 0 auto;
  top: 0px;
  left: 0px;
  width: 809px;
  height: 190px;
  overflow: hidden;
  visibility: hidden;
}
.jssor-slides {
  cursor: default;
  position: relative;
  top: 0px;
  left: 0px;
  width: 809px;
  height: 190px;
  overflow: hidden;
}
</style>

<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
  var pin='<img src="<%=request.getContextPath()%>/images/icons/red_pin.png" class="iconImage" style="float: right;">';
  var jssor_1_options = {
    $AutoPlay: true,
    $AutoPlaySteps: 5,
    $SlideDuration: 100,
    $SlideWidth: 160,
    $SlideSpacing: 3,
    $Cols: 5,
    $ArrowNavigatorOptions: {
      $Class: $JssorArrowNavigator$,
      $Steps: 5
    },
  };

  $('#ManageGallery').tooltipster();
  $('#NoGalleriesNote').hide();

  $('#ManageGallery').click(function() {
      var url='<%=request.getContextPath()%>/gallery/launchManageGallery.htm';
      var title='Manage Galary';
      var width=$(window).width()-100;
      var height=$(window).height()-100;
      openWindow(url, title, width, height);
  });

  $.ajax({
    url: "<%=request.getContextPath()%>/gallery/jsonListGalleries.htm",
    data: {
      sid: new Date().getTime()
    },
    dataType: 'json',
    context: document.body,
    success: function(result) {
      if (result == null || result.Galleries == null || result.Galleries == 'undefined' || result.Galleries.length == 0) {
        $('#NoGalleriesNote').show();
      } else {
        $.each(result.Galleries, function(index, gallery) {
          var galleryName = gallery.GalleryName;
          var pinned = gallery.Pinned;
          var galleryHeading = $('<p class="GalleryName">');
          if (pinned == 'Y') {
              galleryHeading.append(pin + galleryName);
          } else {
              galleryHeading.text(galleryName);
          }

          var sliderId = 'jssor_' + index;
          var gallerySlider = $('<div id="' + sliderId + '" class="jssor-container">');
          var loading = $('<div data-u="loading" style="position: absolute; top: 0px; left: 0px;"><div style="filter: alpha(opacity=70); opacity: 0.7; position: absolute; display: block; top: 0px; left: 0px; width: 100%; height: 100%;"></div><div style="position:absolute;display:block;background-color: gray;top:0px;left:0px;width:100%;height:100%;"></div></div>');
          var slides = $('<div data-u="slides" class="jssor-slides">');

          gallerySlider.append(loading);
          gallerySlider.append(slides);

          showGalleryItems(galleryName, gallery.GalleryItems, slides, galleryHeading);

          $('#GalleryOutlook').append(galleryHeading);
          $('#GalleryOutlook').append(gallerySlider);
          $(galleryHeading).click();

          var jssor_1_slider = new $JssorSlider$(sliderId, jssor_1_options);
        });
      }
    }
  });

  function showGalleryItems(galleryName, galleryItems, gallerySlider, galleryHeading) {
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
  $(document).social({
    title: '${ORGANIZATION.name} - Director'
  });
});
</script>

<div id="Socialize"></div>
<p/>
<table class="formTable_Container" width="100%">
  <caption>Gallery
  <c:if test="${PAGE_ACCESS != null && PAGE_ACCESS.update}">
    <img id="ManageGallery" src="<%=request.getContextPath()%>/images/icons/slides.png" style="float: right;" class="iconImage" title="Manage Gallery" />
    </c:if>
  </caption>
  <tr>
    <td align="center">
      <div id="NoGalleriesNote" class="error" style="padding-left: 5x; font-style:italic;">
        <p style="color: red;font-weight: bold;">There are no galleries present at this time.</p>
        <c:if test="${PAGE_ACCESS == null || !PAGE_ACCESS.update}">
        <p style="color: red;">Keep checking this space for galleries.</p>
        </c:if>
      </div>
      <div id="GalleryOutlook" class="expand-collapse">
      </div>
    </td>
  </tr>
</table>
