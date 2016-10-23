/*
 * myschool-timer plugin 
 * This script contains utitlity methods for launching timers, coutners and countdowns.
 * modified by Srikanth Kumar, 2016.
*/

/*********************** TIMER FUNCTIONS ***********************/
var currentTimer = null;

function smallCountdownTimerNow(elem, seconds, callback) {
  smallCountdownTimer(elem, seconds, true, callback);
}

function smallCountdownTimer(elem, seconds, autostart, callback) {
  $(elem).countdown360({
    radius: 20,
    seconds: seconds,
    autostart: autostart,
    strokeStyle: "#EA5B0C",
    strokeWidth: 6,
    fillStyle: "#E6E6E6",
    fontSize: 20,
    fontColor: "#555",
    label: ["second", "sec"],
    smooth: true,
    onComplete  : callback
  });
}

function mediumCountdownTimer(elem, seconds, autostart, callback) {
  $(elem).countdown360({
    radius: 30,
    seconds: seconds,
    autostart: autostart,
    strokeStyle: "#EA5B0C",
    strokeWidth: 6,
    fillStyle: "#E6E6E6",
    fontSize: 30,
    fontColor: "#555",
    label: ["second", "sec"],
    smooth: true,
    onComplete  : callback
  });
}

/*********************** INDEFINITE FUNCTIONS ***********************/
function wait() {
  startLoading($('body'));
}
function unwait() {
  stopLoading($('body'));
}

function startLoading(elem) {
  $(elem).waitMe({
    effect : 'win8',
    text : 'Loading...',
    bg : 'rgba(10,10,10, 0.8)',
    color : '#FFF',
    maxSize : '',
    textPos : 'vertical',
    fontSize : 'Please Wait...',
    source : '' 
  });
}

function stopLoading(elem) {
  $(elem).waitMe('hide');
}

// startLoading($('#SSSInstructions'));
// stopLoading($('#SSSInstructions'));
