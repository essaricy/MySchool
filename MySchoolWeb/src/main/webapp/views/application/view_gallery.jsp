<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/widgets/jquery.expand.collapse/css/example.css" />
<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/widgets/jquery.expand.collapse/scripts/expand.js"></script>

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
</style>

<script type="text/javascript" charset="utf-8">
var pin='<img src="<%=request.getContextPath()%>/images/icons/red_pin.png" class="iconImage" />';
$(document).ready(function() {
  $('#ExpandAll').tooltipster();
  $('#CollapseAll').tooltipster();
  $('#ManageGallery').tooltipster();
  $('#NoGalleriesNote').hide();

  $('#ExpandAll').click(function() {
    $("#GalleryOutlook h3.expand").find('a').each(function() {
      if ($(this).attr('class') != 'open') {
        $(this).click();
      }
    });
  });

  $('#CollapseAll').click(function() {
    $("#GalleryOutlook h3.expand").find('a').each(function() {
      if ($(this).attr('class') == 'open') {
        $(this).click();
      }
    });
  });

  $('#ManageGallery').click(function() {
      var url='<%=request.getContextPath()%>/gallery/launchManageGallery.htm';
      var title='Manage Galary';
      var width=$(window).width()-100;
      var height=$(window).height()-100;
      openDialog(url, title, width, height);
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
          var galleryHeading = $('<h3 class="expand">');
          galleryHeading.text(galleryName);

          var galleryThumbsContainer = $('<div class="collapse">');
          showGalleryItems(galleryName, gallery.GalleryItems, galleryThumbsContainer, galleryHeading);

          $('#GalleryOutlook').append(galleryHeading);
          $('#GalleryOutlook').append(galleryThumbsContainer);
          $(galleryHeading).toggler();
          $(galleryHeading).click();
          if (pinned=='Y') {
              galleryHeading.find('a').append(" (" + gallery.GalleryItems.length + ")").append(pin);
          } else {
              galleryHeading.find('a').append(" (" + gallery.GalleryItems.length + ")");
          }
        });
      }
    }
  });

  function showGalleryItems(galleryName, galleryItems, galleryThumbsContainer, galleryHeading) {
    if (galleryItems != null && galleryItems.length != 0) {
      $.each(galleryItems, function(index, galleryItem) {
        var imageContainer = $('<span>');
        imageContainer.attr('class', 'ImageContainer');

        var galleryItemName = galleryItem.GalleryName;
        var image = $('<img>');
        image.attr('class', 'GallerySmallImage');
        image.attr('src', galleryItem.Passport);
        image.click(function() {
          openDialog('<%=request.getContextPath()%>/image/slideshow.htm?GalleryName=' + galleryName + '&Selection=' + (index++) + '&sid=' + new Date().getTime(), 'Slide Show of [ ' + galleryName + ' ]', $(window).width()-20, $(window).height()-20);
        });
        imageContainer.append(image);
        imageContainer.fadeIn(3000);
        galleryThumbsContainer.append(imageContainer);
      });
    }
 }

  $(document).social({
    title: '${ORGANIZATION_PROFILE.organizationName} - Director'
  });
});
</script>

<div id="Socialize"></div>
<p/>
<div id="GalleryWrapper" class="expand-collapse-wrapper">
  <div class="dataTableCaption" style="text-align: center;">
    Gallery
    <img id="ExpandAll" src="<%=request.getContextPath()%>/images/icons/triangle_down.png" style="float: right;" class="iconImage" title="Expand All" />
    <img id="CollapseAll" src="<%=request.getContextPath()%>/images/icons/triangle_up.png" style="float: right;" class="iconImage" title="Collapse All" />
    <c:if test="${PAGE_ACCESS != null && PAGE_ACCESS.update}">
    <img id="ManageGallery" src="<%=request.getContextPath()%>/images/icons/slides.png" style="float: right;" class="iconImage" title="Manage Gallery" />
    </c:if>
  </div>

  <div id="GalleryOutlookContainer" class="expand-collapse-content">  
    <div id="GalleryOutlook" class="expand-collapse">
    </div>
  </div>
  <div id="NoGalleriesNote" class="error" style="padding-left: 5x; font-style:italic;">
    <p>There are no galleries present at this time.</p>
    <p>Keep checking this space for galleries.</p>
  </div>
</div>
