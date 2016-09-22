var jssor_1_SlideoTransitions = [
  [{b:-1,d:1,o:-1},{b:0,d:1000,x:-257,y:-5,o:1}],
  [{b:480,d:520,x:120,y:-270}],
  [{b:1000,d:500,y:250},{b:8000,d:1000,x:600}],
  [{b:-1,d:1,o:-1,sX:-0.6,sY:-0.6},{b:1500,d:500,o:1,r:360,sX:0.6,sY:0.6},{b:8000,d:1000,y:-150}],
  [{b:-1,d:1,o:-1,tZ:-200},{b:2000,d:1000,o:1,tZ:200},{b:3000,d:1000,o:-1,tZ:200}],
  [{b:-1,d:1,o:-1,tZ:-200},{b:3000,d:1000,o:1,tZ:200},{b:4000,d:1000,o:-1,tZ:200}],
  [{b:-1,d:1,o:-1,tZ:-200},{b:4000,d:1000,o:1,tZ:200},{b:5000,d:1000,o:-1,tZ:200}],
  [{b:-1,d:1,o:-1,tZ:-200},{b:5000,d:1000,o:1,tZ:200},{b:6000,d:1000,o:-1,tZ:200}],
  [{b:-1,d:1,o:-1,tZ:-200},{b:6000,d:1000,o:1,tZ:200},{b:7000,d:1000,o:-1,tZ:200}],
  [{b:-1,d:1,o:-1,tZ:-200},{b:7000,d:1000,o:1,tZ:200},{b:8000,d:1000,o:-1,tZ:200}],
  [{b:-1,d:1,c:{x:250.0,t:-250.0}},{b:0,d:1000,c:{x:-250.0,t:250.0}},{b:5000,d:1000,y:100}],
  [{b:-1,d:1,o:-1},{b:1000,d:1000,o:1},{b:5000,d:1000,y:280}],
  [{b:2000,d:1000,y:190,e:{y:28}},{b:5000,d:1000,x:280}],
  [{b:3000,d:520,y:50},{b:5000,d:1000,y:-50}],
  [{b:3520,d:480,x:-400},{b:5000,d:800,x:400,e:{x:7}}],
  [{b:4000,d:500,x:-400},{b:5000,d:800,x:400,e:{x:7}}],
  [{b:4500,d:500,x:-400},{b:5000,d:800,x:400,e:{x:7}}],
  [{b:-1,d:1,c:{x:250.0,t:-250.0}},{b:0,d:1000,c:{x:-250.0,t:250.0}},{b:10000,d:500,y:90}],
  [{b:-1,d:1,c:{x:150.0,t:-150.0}},{b:1000,d:1000,c:{x:-150.0,t:150.0}},{b:10000,d:500,c:{y:150.0,m:-150.0}}],
  [{b:2000,d:500,x:220}],
  [{b:3500,d:500,rX:-20},{b:4000,d:1000,rX:40},{b:5000,d:500,rX:-10},{b:9500,d:500,o:-1}],
  [{b:3500,d:2000,r:360}],
  [{b:-1,d:1,o:-1},{b:2500,d:500,x:76,y:-25,o:1}],
  [{b:-1,d:1,o:-1},{b:2500,d:500,x:47,y:65,o:1}],
  [{b:-1,d:1,o:-1},{b:2500,d:500,x:-47,y:65,o:1}],
  [{b:-1,d:1,o:-1},{b:2500,d:500,x:-76,y:-25,o:1}],
  [{b:-1,d:1,o:-1},{b:2500,d:500,y:-80,o:1}],
  [{b:-1,d:1,c:{x:120.0,t:-120.0}},{b:6100,d:400,c:{x:-120.0,t:120.0}},{b:10000,d:500,y:-120}],
  [{b:6500,d:500,x:220}],
  [{b:-1,d:1,o:-1,r:180,sX:-0.5,sY:-0.5},{b:7000,d:500,o:1,r:180,sX:0.5,sY:0.5},{b:8000,d:500,o:-1,r:180,sX:9,sY:9}],
  [{b:-1,d:1,o:-1,r:180,sX:-0.5,sY:-0.5},{b:8000,d:500,o:1,r:180,sX:0.5,sY:0.5},{b:9000,d:500,o:-1,r:180,sX:9,sY:9}],
  [{b:-1,d:1,o:-1,r:180,sX:-0.5,sY:-0.5},{b:9000,d:500,o:1,r:180,sX:0.5,sY:0.5},{b:9500,d:500,o:-1,r:180,sX:9,sY:9}]
];

var jssor_inline_slider_options = {
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

var jssor_full_slider_options = {
  $AutoPlay: true,
  $SlideDuration: 500,
  $SlideEasing: $Jease$.$InOutSine,
  $CaptionSliderOptions: {
    $Class: $JssorCaptionSlideo$,
    $Transitions: jssor_1_SlideoTransitions,
    /*$Breaks: [
      [{d:3000,b:8000,t:2}],
      [{d:2000,b:5000}],
      [{d:3000,b:9500,t:2}]
    ]*/
  },
  $ArrowNavigatorOptions: {
    $Class: $JssorArrowNavigator$
  },
  $BulletNavigatorOptions: {
    $Class: $JssorBulletNavigator$
  }
};

function createInlineSlider(sliderId) {
    return new $JssorSlider$(sliderId, jssor_inline_slider_options);
}

function createFullSlider(sliderId) {
  return new $JssorSlider$(sliderId, jssor_full_slider_options);

  /*//responsive code begin
  //you can remove responsive code if you don't want the slider scales while window resizing
  function ScaleSlider() {
    var refSize = jssor_1_slider.$Elmt.parentNode.clientWidth;
    if (refSize) {
      refSize = Math.min(refSize, 980);
      jssor_1_slider.$ScaleWidth(refSize);
    } else {
      window.setTimeout(ScaleSlider, 30);
    }
  }
  ScaleSlider();
  $Jssor$.$AddEvent(window, "load", ScaleSlider);
  $Jssor$.$AddEvent(window, "resize", ScaleSlider);
  $Jssor$.$AddEvent(window, "orientationchange", ScaleSlider);
  //responsive code end*/
}
