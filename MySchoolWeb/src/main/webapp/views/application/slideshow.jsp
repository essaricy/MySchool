<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
  /* This rule is read by Galleria to define the gallery height: */
  #galleria{height: 540px; width:100%;}
</style>
<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/widgets/galleria/galleria-1.2.8.js"></script>
<script>
  var selectedItem = $('#Selection').val();
  $('#galleria').width($(window).width()-30);
  $('#galleria').height($(window).height()-50);
  Galleria.loadTheme('<%=request.getContextPath()%>/widgets/galleria/themes/classic/galleria.classic.min.js');
  // Initialize Galleria
  Galleria.run('#galleria', {show: parseInt(selectedItem)});
</script>
<input type="hidden" id="GalleryName" value="${GalleryName}">
<input type="hidden" id="Selection" value="${Selection}">
<div id="galleria">
  <c:forEach var="GalleryItemName" items="${GalleryItemNames}">
  <a href="<%=request.getContextPath()%>/image/getImage.htm?type=gallery&imageSize=ORIGINAL&contentId=${GalleryName}/${GalleryItemName}">
    <img src="<%=request.getContextPath()%>/image/getImage.htm?type=gallery&imageSize=THUMBNAIL&contentId=${GalleryName}/${GalleryItemName}" />
  </a>
  </c:forEach>
</div>
