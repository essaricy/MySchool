<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
  /* This rule is read by Galleria to define the gallery height: #galleria{height: 540px; width:100%;}*/
  
</style>
<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/widgets/galleria/galleria-1.2.8.js"></script>
<script>
var refSize='<%=request.getParameter("REFERENCE_SIZE")%>';
$(document).ready(function() {
  $('#galleria').addClass(refSize);

  $.ajax({
    url: "<%=request.getContextPath()%>/gallery/getLatestGallery.htm",
    data: {
      sid: new Date().getTime()
    },
    dataType: 'json',
    context: document.body,
    success: function(result) {
      if (result == null || result.Gallery == null || result.Gallery == 'undefined') {
        $('#GalleryName').text('Latest Gallery');
        $('#Latest_Gallery_More').hide();
        var errorMessage = $('<span class="mandatory">');
        errorMessage.append('Watch out this space for galleries.');
        $('#galleria').append(errorMessage);
      } else {
        var galleryName = result.Gallery.GalleryName;
        var galleryItems = result.Gallery.GalleryItems;
        if (galleryItems == null || galleryItems == 'undefined' || galleryItems.length == 0) {
          $('#GalleryName').text('Latest Gallery');
          $('#Latest_Gallery_More').hide();
          var errorMessage = $('<span class="mandatory">');
          errorMessage.append('Watch out this space for galleries.');
          $('#galleria').append(errorMessage);
        } else {
		  $('#GalleryName').text('Latest Gallery [' + galleryName + ']');
          $.each(galleryItems, function(index, galleryItem) {
			var galleryItemName = galleryItem.GalleryName;
           	var image = $('<img>');
           	image.attr('src', '<%=request.getContextPath()%>/image/getImage.htm?type=gallery&imageSize=THUMBNAIL&contentId=' + galleryName + '/' + galleryItemName);
           	var link = $('<a>');
           	link.attr('href', '<%=request.getContextPath()%>/image/getImage.htm?type=gallery&imageSize=ORIGINAL&&contentId=' + galleryName + '/' + galleryItemName);
           	link.append(image);
           	$('#galleria').append(link);
           	$('#galleria').width($(''+refSize).width()-10);
           	$('#galleria').height($(''+refSize).height()-34);
           	Galleria.loadTheme('<%=request.getContextPath()%>/widgets/galleria/themes/classic/galleria.classic.min.js');
           	// Initialize Galleria
           	Galleria.run('#galleria', {autoplay: true});
       	  });
        }
      }
    }
  });
});
</script>
<div class="TileHeader" align="left">
  <strong id="GalleryName"></strong>
  <a href="<%=request.getContextPath()%>/gallery/launchGallery.htm"><img src="<%=request.getContextPath()%>/images/icons/more.png" title="Findout more..." style="cursor: pointer;"/></a>
</div>
<div class="TileContent" align="left">
  <div id="galleria"></div>
</div>
