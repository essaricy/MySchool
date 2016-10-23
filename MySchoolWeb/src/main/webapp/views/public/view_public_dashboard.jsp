<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/styles/myschool-sliders.css" />
<style>
.feature_slide_1 {
  position: absolute;
  top: 590px;
  left: -120px;
  width: 980px;
  height: 60px;
  background-color: rgba(235,81,0,0.5);
  font-size: 20px;
  color: #ffffff;
  line-height: 30px;
  text-align: center;
}
.feature_slide {
  position: absolute;
  top: 320px;
  left: 0px;
  width: 980px;
  height: 60px;
  background-color: rgba(235,81,0,0.5);
  font-size: 20px;
  color: #ffffff;
  line-height: 30px;
  text-align: center;
}
</style>
<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/widgets/jquery.jssor.slider/js/jssor.slider-21.1.5.mini.js"></script>
<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/scripts/myschool-sliders.js"></script>

<script>
$(document).ready(function() {
  createFullSlider("features_slider");
});
</script>

<!-- ####################################### FEATURES ############################################### -->
<div id="features_slider" style="position: relative; margin: 0 auto; top: 0px; left: 0px; width: 980px; height: 380px; overflow: hidden; visibility: hidden;">
    <!-- Loading Screen -->
    <div data-u="loading" style="position: absolute; top: 0px; left: 0px;">
        <div style="filter: alpha(opacity=70); opacity: 0.7; position: absolute; display: block; top: 0px; left: 0px; width: 100%; height: 100%;"></div>
        <div style="position:absolute;display:block;background:url('<%=request.getContextPath()%>/widgets/jquery.jssor.slider/img/loading.gif') no-repeat center center;top:0px;left:0px;width:100%;height:100%;"></div>
    </div>
    <div data-u="slides" style="cursor: default; position: relative; top: 0px; left: 0px; width: 980px; height: 380px; overflow: hidden;">
        <div data-b="0" data-p="170.00" data-po="80% 55%" style="display: none;">
            <img data-u="image" src="<%=request.getContextPath()%>/images/features/classroom.jpg" />
            <div data-u="caption" data-t="1" class="feature_slide_1">Description about class room goes here. Description about class room goes here. Description about class room goes here. Description about class room goes here. Description about class room goes here. </div>
        </div>
        <div data-b="1" data-p="170.00" style="display: none;">
            <img data-u="image" src="<%=request.getContextPath()%>/images/features/library.jpg" />
            <div data-u="caption" data-t="10" class="feature_slide">Description about library goes here. Description about library goes here. Description about library goes here. Description about library goes here. Description about library goes here.</div>
        </div>
        <div data-b="2" data-p="170.00" style="display: none;">
            <img data-u="image" src="<%=request.getContextPath()%>/images/features/playground.jpg" />
            <div data-u="caption" data-t="17" class="feature_slide">Description about playground goes here. Description about playground goes here. Description about playground goes here. Description about playground goes here. Description about playground goes here.</div>
        </div>
        <div data-b="2" data-p="170.00" style="display: none;">
            <img data-u="image" src="<%=request.getContextPath()%>/images/features/auditorium.jpg" />
            <div data-u="caption" data-t="17" class="feature_slide">Description about auditorium goes here. Description about auditorium goes here. Description about auditorium goes here. Description about auditorium goes here. Description about auditorium goes here.</div>
        </div>
        <div data-b="2" data-p="170.00" style="display: none;">
            <img data-u="image" src="<%=request.getContextPath()%>/images/features/computerlab.jpg" />
            <div data-u="caption" data-t="17" class="feature_slide">Description about computerlab goes here. Description about computerlab goes here. Description about computerlab goes here. Description about computerlab goes here. Description about computerlab goes here.</div>
        </div>
        <div data-b="2" data-p="170.00" style="display: none;">
            <img data-u="image" src="<%=request.getContextPath()%>/images/features/transport.jpg" />
            <div data-u="caption" data-t="17" class="feature_slide">Description about transport goes here. Description about transport goes here. Description about transport goes here. Description about transport goes here. Description about transport goes here.</div>
        </div>
        <div data-b="2" data-p="170.00" style="display: none;">
            <img data-u="image" src="<%=request.getContextPath()%>/images/features/lab.jpg" />
            <div data-u="caption" data-t="17" class="feature_slide">Description about lab goes here. Description about lab goes here. Description about lab goes here. Description about lab goes here. Description about lab goes here.</div>
        </div>
    </div>
    <!-- Bullet Navigator -->
    <div data-u="navigator" class="jssorb05" style="bottom:16px;right:16px;" data-autocenter="1">
        <!-- bullet navigator item prototype -->
        <div data-u="prototype" style="width:16px;height:16px;"></div>
    </div>
    <!-- Arrow Navigator -->
    <span data-u="arrowleft" class="jssora22l" style="top:0px;left:10px;width:40px;height:58px;" data-autocenter="2"></span>
    <span data-u="arrowright" class="jssora22r" style="top:0px;right:10px;width:40px;height:58px;" data-autocenter="2"></span>
</div>
<br/>
<div style="width: 20%; float: left;">
  <h2 class="formHeading">Connect With</h2>
    <div>
left content<br/>
left content<br/>
left content<br/>
    </div>

left content<br/>
left content<br/>
left content<br/>
left content<br/>
left content<br/>
left content<br/>
left content<br/>
left content<br/>
left content<br/>
left content<br/>
</div>
<div style="width: 20%; float: right;">
  <h2 class="formHeading">Connect With</h2>
    <div>
right content<br/>
right content<br/>
right content<br/>
    </div>

  <h2 class="formHeading">Quick Links</h2>
    <div>
right content<br/>
right content<br/>
right content<br/>
    </div>

right content<br/>
right content<br/>
right content<br/>
right content<br/>
right content<br/>
right content<br/>
right content<br/>
right content<br/>
right content<br/>
right content<br/>
right content<br/>
right content<br/>
right content<br/>
right content<br/>

</div>
<div style="width: 50%; clear: none;">
<h2 class="formHeading">Latest News & Events</h2>
center content<br/>
center content<br/>
center content<br/>
center content<br/>
center content<br/>
center content<br/>
center content<br/>
center content<br/>
center content<br/>
center content<br/>
center content<br/>
center content<br/>
center content<br/>
center content<br/>
center content<br/>
center content<br/>
center content<br/>
</div>
<p style="clear: both;">&nbsp;</p>

<!-- ####################################### GALLERY ############################################### -->
<%@ include file="/views/common/latest_gallery_strip.jsp" %>
