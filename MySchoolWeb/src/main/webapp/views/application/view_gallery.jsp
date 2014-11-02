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
</style>

<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
  $('#ExpandAll').tooltipster();
  $('#CollapseAll').tooltipster();

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

  $.ajax({
    url: "<%=request.getContextPath()%>/noticeBoard/jsonGalleryNames.htm",
    data: {
      sid: new Date().getTime()
    },
    dataType: 'json',
    context: document.body,
    success: function(result) {
    if (result == null || result.GalleryNames == null || result.GalleryNames == 'undefined') {
      $('#GalleryContainer').append('<p style="color:red;">Watch out this space for galleries.</p>');
    } else {
      $.each(result.GalleryNames, function(index, value) {
        var galleryHeading = $('<h3 class="expand">');
        galleryHeading.text(value);
        var galleryThumbsContainer = $('<div class="collapse">');
        showGalleryItems(value, galleryThumbsContainer, galleryHeading);
        $('#GalleryOutlook').append(galleryHeading);
        $('#GalleryOutlook').append(galleryThumbsContainer);
      });
      $("#GalleryOutlook h3.expand").toggler();
      $("#GalleryOutlook h3.expand").each(function() {
        $(this).click();
      });
    }
  }
});

function markAsLatest(galleryName) {
  $.ajax({
    url: "<%=request.getContextPath()%>/noticeBoard/markAsLatest.htm",
      data: {
        GalleryName: galleryName,
        sid: new Date().getTime()
      },
      dataType: 'json',
      context: document.body,
      success: function(result) {
        parseWholepageResponse(result, false);
      });
  }

  function showGalleryItems(galleryName, galleryThumbsContainer, galleryHeading) {
    $.ajax({
      url: "<%=request.getContextPath()%>/noticeBoard/jsonGalleryItemNames.htm",
      data: {
        GalleryName: galleryName,
        sid: new Date().getTime()
      },
      dataType: 'json',
      context: document.body,
      success: function(result) {
        var count=0;
        <c:if test="${USER_CONTEXT.userType == 'ADMIN'}">
            var link = $('<a href="#" class="formLink">Mark This As Latest</a>');
            link.click(function () {
              markAsLatest(galleryName);
            });
            galleryThumbsContainer.append(link);
            galleryThumbsContainer.append('<br/>');
        </c:if>

        $.each(result.GalleryItemNames, function(index, value) {
          var image = $('<img>');
          image.attr('class', 'GallerySmallImage');
          image.attr('src', '<%=request.getContextPath()%>/image/getImage.htm?type=gallery&imageSize=PASSPORT&contentId=' + galleryName + '/' + value);
          image.click(function() {
            openDialog('<%=request.getContextPath()%>/image/slideshow.htm?GalleryName=' + galleryName + /* '&GalleryItemNames='+ JSON.stringify(result.GalleryItemNames) + */ '&Selection=' + (index++) + '&sid=' + new Date().getTime(),
            'Slide Show of [ ' + galleryName + ' ]', $(window).width()-20, $(window).height()-20);
          });
          galleryThumbsContainer.append(image);
          count++;
        });
        //alert(count);
        galleryHeading.find('a').append(" (" + count + ")");
      }
    });
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
  </div>
  <div id="GalleryOutlookContainer" class="expand-collapse-content">  
    <div id="GalleryOutlook" class="expand-collapse">
    </div>
  </div>
</div>
