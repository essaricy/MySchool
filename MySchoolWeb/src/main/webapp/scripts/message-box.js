$(function () {
  $(window).scroll(function () {
    var top = $(window).scrollTop();
    $("div.social").css({ position: (top > 260 ? "fixed" : "absolute"), top: top > 260 ? 10 : 260 });
  });
});

function setCodeView(mainCode) {
  var code = mainCode.split('');
  var temp = "";
  var spanRed = "<span style=\"color:red;\">";
  var spanGreen = "<span style=\"color:green;\">";
  var spanClose = "</span>";
  var isOpen = false;
  var lastIndex = 0;
  for (var i = 0; i < code.length; i++) {
    if (code[i] == '"' || code[i] == '\'') {
      temp += (isOpen ? code[i] + spanClose : spanRed + code[i]);
      lastIndex = i;
      isOpen = !isOpen;
    } else if (code[i] == ',' || code[i] == ':' || code[i] == '{' || code[i] == '}') {
      temp += spanGreen + code[i] + spanClose;
    } else if (code[i] == '<') {
      temp += "&#060";
    } else if (code[i] == '>') {
      temp += "&#062";
    } else if (code[i] == '\n') {
      temp += i == 0 ? "" : (i == code.length - 1 ? "" : "<br/>");
    } else if (code[i] == ' ') {
      temp += ' ';
    } else {
      temp += code[i];
    }
  }
  return temp;
}

function SetCodeBlocks() {
  $("div.codeBlock>code").each(function (index, domEle) {
    var code = setCodeView($(this).html());
    $(this).html(code);
  });
}

$(function () {
  SetCodeBlocks();
});


function warn(message) {
  $.msgBox({
    title: "Warning",
    content: message,
    opacity: 0.8,
    showButtons: true,
    autoClose: false,
    buttons: [{ value: "OK" }],
  });
}
function warn_ac(message) {
  $.msgBox({
    title: "Warning",
    content: message,
    showButtons: false,
    opacity: 0.8,
    autoClose: true,
    buttons: [{ value: "OK" }],
  });
}

function info(message) {
  $.msgBox({
    title: "Information",
    content: message,
    type:"info",
    opacity: 0.8,
    showButtons: true,
    autoClose: false,
    buttons: [{ value: "OK" }],
  });
}

function info_ac(message) {
  $.msgBox({
    title: "Information",
    content: message,
    type:"info",
    opacity: 0.8,
    showButtons: true,
    autoClose: false,
    buttons: [{ value: "OK" }],
  });
}

function info_cb(message, callbackFunction) {
  $.msgBox({
    title: "Information",
    content: message,
    type:"info",
    showButtons: true,
    opacity: 0.8,
    autoClose: false,
    buttons: [{ value: "OK" }],
    success: callbackFunction
  });
}

function confirm(message, callbackFunction) {
  $.msgBox({
    title: "Are You Sure?",
    content: message,
    type: "confirm",
    opacity: 0.6,
    showButtons: true,
    autoClose: false,
    buttons: [{ value: "Yes" }, { value: "No" }],
    success: callbackFunction
  });
}

function error(message) {
  $.msgBox({
    title: "Error",
    content: message,
    type: "error",
    opacity: 0.8,
    showButtons: true,
    autoClose: false,
    buttons: [{ value: "OK"}]
  });
}

function error_ac(message) {
  $.msgBox({
    title: "Error",
    content: message,
    type: "error",
    opacity: 0.8,
    showButtons: false,
    autoClose:true,
    buttons: [{ value: "OK"}]
  });
}
