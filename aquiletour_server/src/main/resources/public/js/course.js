function initializeCourse(viewRootElement, jSweet){

<<<<<<< HEAD
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
=======
    console.log("initializeCourse");
}

>>>>>>> main
