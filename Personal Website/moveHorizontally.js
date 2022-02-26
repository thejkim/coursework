function moveMario(elemId, speed) {
  var element = document.getElementById(elemId);
  var intervalRef = setInterval(update, speed);
  var offsetX = 0;
  var startPointX = 0-element.clientWidth;
  var endPointX = window.innerWidth;

  var marioArray = document.getElementsByClassName("mario");
  var marioHight = element.clientHeight + 'px';
  marioArray[0].style.height = marioHight;
  marioArray[1].style.height = marioHight;
  marioArray[2].style.height = marioHight;

  function update() {
    offsetX++;
    element.style.left = offsetX + "px";
    if (endPointX == offsetX) {
      element.style.left = startPointX;
      offsetX = startPointX;
    }
  }
}

function moveCloud(elemId, speed) {
    var element = document.getElementById(elemId);
    var intervalRef = setInterval(update, speed);
    var offsetX = 0;
    var startPointX = 0-element.clientWidth;
    var endPointX = window.innerWidth;

    var cloudArray = document.getElementsByClassName("cloud");
    var cloudHight = element.clientHeight + 'px';
    cloudArray[0].style.height = cloudHight;
    cloudArray[1].style.height = cloudHight;
    cloudArray[2].style.height = cloudHight;

    function update() {
      offsetX++;
      element.style.left = offsetX + "px";
      if (endPointX == offsetX) {
        element.style.left = startPointX;
        offsetX = startPointX;
      }
    }
  }
