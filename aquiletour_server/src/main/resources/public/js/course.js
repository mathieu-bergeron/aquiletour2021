var radius = 8;
var diameter = radius * 2;


function initializeCourse(viewRootElement, jSweet){

  var canvas = new fabric.Canvas('breadcrumb-canvas');
  var breadcrumb = $("#breadcrumbs-container");
  var margin = parseInt(breadcrumb.css("marginLeft"));

  canvas.setWidth((breadcrumb.outerWidth() + margin));
  canvas.setHeight(100);


  updatePosition(canvas);
}

  function makeCircle(index, top, left, radius){
    var circle = new fabric.Circle({
    top: top,
    left: left,
    strokeWidth: 3,
    radius: radius,
    fill: 'white',
    stroke: 'orange',
    selectable: false,
    evented: false,
    index: index
    });

    return circle;
  }

  // function makeCircleSibling(index, top, left, radius, centerX, offsetY){
  //   var circle = new fabric.Circle({
  //   top: top,
  //   left: left,
  //   strokeWidth: 1,
  //   radius: radius,
  //   fill: '#9C6663',
  //   stroke: 'black',
  //   selectable: false,
  //   index: index,
  //   centerX : centerX,
  //   offsetY : offsetY
  //   });

  //   return circle;
  // }

  function makeLine(coords) {
    return new fabric.Line(coords,{
      fill: 'orange',
      stroke: 'orange',
      strokeWidth: 5,
      selectable: false,
      evented: false
    });
  }

  
  function updatePosition(canvas) {
    var bcItems = $(".breadcrumb-item");
    var myElement = document.querySelector("#bc-position"); 
    
    

    bcItems.each(( index, element ) => {
      
      var offsetY = element.offsetTop - (element.offsetTop - 1);
      var centerY = element.offsetTop + (element.offsetHeight / 2) - radius;
      var centerX = element.offsetLeft + (element.offsetWidth / 2) - radius;

      

      var offsetRight = window.innerWidth - element.offsetLeft - element.offsetWidth;

      let jsonValue = $(bcItems[index]).attr("siblings");
      let mySiblings;

      if (jsonValue === undefined) {
        mySiblings = [];
        console.log("no siblings");
      } else {
        mySiblings = JSON.parse(jsonValue);
      }
      
      var circle = makeCircle(index, offsetY, centerX, radius);

      canvas.add(circle);

      canvas.bringToFront(circle);
      
      //take position from previous element;
      if (index != 0) {
      
        var prevItem = bcItems[index - 1];
        var nextItem = bcItems[index + 1];
        var line = makeLine([prevItem.offsetLeft, radius - 1 , element.offsetLeft, radius - 1]);

        canvas.add(line);

        canvas.sendToBack(line);
       
      } else {
        var lastLine = makeLine([offsetRight, radius - 1 , bcItems[bcItems.length - 1].offsetLeft, radius - 1]);

        canvas.add(lastLine);

        canvas.sendToBack(lastLine);
      }
      
      
      // if (mySiblings.length != 0 && mySiblings.length > 0) {

      //   var circleSibling = makeCircleSibling(index, offsetY + (diameter + radius), centerX, radius, centerX, offsetY);

      //   circleSibling.mySiblings = mySiblings;

      //   canvas.add(circleSibling);

      //   canvas.bringToFront(circleSibling);

        

      // }
     
      


    });
    
    // canvas.on('mouse:over', function(e) {
      
    //   if (e.target != null) {
    //     var prevElement = e.target.index - 1;
        

    //     if (e.target.mySiblings !== undefined && e.target.mySiblings.length > 0) {
    //       console.log("has siblings");
          
          
    //       if (e.target.index == 0) {
    //         var lineSibling = makeLine([-5, radius, e.target.centerX, e.target.offsetY + (diameter + radius)]);

    //         console.log("target id : " + e.target.index);
    //         console.log("offset Y " + e.target.offsetY + " center X " + e.target.centerX);
    //         canvas.add(lineSibling);

    //         canvas.sendToBack(lineSibling);

    //         canvas.remove(circle);

    //       } else {
    //         var nextLineSibling = makeLine([prevElement.offsetWidth, radius, e.target.centerX, e.target.offsetY + (diameter + radius)]);

    //         console.log("offset Y " + e.target.offsetY + " center X " + e.target.centerX);
    //         console.log(e.target.index);
    //         console.log(prevElement.centerX);
    //         canvas.add(nextLineSibling);

    //         canvas.sendToBack(nextLineSibling);
    //       }
          
          
          
          
          
    //     } else {
    //       console.log("no siblings");
    //     }

    //   }

      
    // });
    
  }
/*$(document).ready(function() {
  
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

  


  console.log("initializeCourse");*/

    


