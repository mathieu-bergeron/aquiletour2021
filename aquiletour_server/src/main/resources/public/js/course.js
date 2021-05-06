function initializeCourse(viewRootElement, jSweet){
  console.log('initializeCourse');


  var canvas = new fabric.Canvas('breadcrumb-canvas');
  var radius = 8;
  var diameter = radius * 2;
  
  var breadcrumb = $("#breadcrumb");
  var margin = parseInt(breadcrumb.css("marginLeft"));

  canvas.setWidth((breadcrumb.outerWidth() + margin));
  canvas.setHeight(100);

  function testCircle(index ,top, left, radius){
    var circle = new fabric.Circle({
    top: top,
    left: left,
    strokeWidth: 3,
    radius: radius,
    fill: 'black',
    stroke: 'white',
    selectable: false,
    id: index
    });

    return circle;
  }

  function getPosition(element) {
    var xPos = 0;
    var yPos = 0;

    while (element) {
      if (element.tagName == "BODY") {
        var xScroll = element.scrollLeft || document.documentElement.scrollLeft;
        var yScroll = element.scrollTop || document.documentElement.scrollTop;

        xPos += (element.offsetLeft - xScroll + element.clientLeft);
        yPos += (element.offsetTop - yScroll + element.clientTop);
      } else {
        xPos += (element.offsetLeft - element.scrollLeft + element.clientLeft);
        yPos += (element.offsetTop - element.scrollTop + element.clientTop);
      }
      
      element = element.offsetParent;
    }
    return {
      x: xPos,
      y: yPos
    };
  }

 document.addEventListener('load', updatePosition());
 
  function makeLine(coords) {
    return new fabric.Line(coords,{
      fill: 'orange',
      stroke: 'orange',
      strokeWidth: 5,
      selectable: false,
      evented: false
    });
  }

  
  function updatePosition() {
    var bcItems = $(".breadcrumb-item");
    var myElement = document.querySelector("#bc-position"); 
    var position = getPosition(myElement);

    
    
    canvas.clear();

    //console.log(bcItems);

    bcItems.each(( index, element ) => {
      console.log(element, getPosition(element), index);
      

      
      var offsetY = element.offsetTop - (element.offsetTop - 1);
      var centerY = element.offsetTop + (element.offsetHeight / 2) - radius;
      var centerX = element.offsetLeft + (element.offsetWidth / 2) - radius;
      var offsetRight = window.innerWidth - element.offsetLeft - element.offsetWidth;

      console.log("offset top: " + centerY + " offset left: " + offsetRight);
      console.log(bcItems);


      let jsonValue = $(bcItems[index]).attr("siblings");
      console.log("jsonvalue " + jsonValue);
      
      //let parsedJson = JSON.parse(jsonValue);

      canvas.add(makeLine([element.offsetLeft, radius, offsetRight, radius]), testCircle(index, offsetY, centerX, radius));
      

        /*
      if (parsedJson.length != 0 && parsedJson.length > 0) {

        canvas.add(testCircle(index, offsetY + (diameter + radius), centerX, radius));

      }
  
      console.log( "json object : " + JSON.stringify(parsedJson));
      */
      
      
    });
    
    

    

    canvas.on('mouse:over', function(e) {
      
      if (e.target != null) {
        
        if (parsedJson.length != 0 && parsedJson.length > 0) {
          console.log("has siblings");
        } else {
          console.log("no siblings");
        }

      }

      
    });
    
  }





$(document).ready(function() {
  
    // get box count
    var count = 0;
    var checked = 0;
    function countBoxes() { 
      count = $("input[type='checkbox']").length;
      console.log(count);
    }
    
    countBoxes();
    $(":checkbox").click(countBoxes);
    
    // count checks
    
    function countChecked() {
      checked = $("input:checked").length;
      
      var percentage = parseInt(((checked / count) * 100),10);
      $(".progressbar-bar").progressbar({
              value: percentage
          });
      $(".progressbar-label").text(percentage + "%");
      $(".progressbar-label").css("background-color", blue);
    }
    
    countChecked();
    $(":checkbox").click(countChecked);
  });

}
    


