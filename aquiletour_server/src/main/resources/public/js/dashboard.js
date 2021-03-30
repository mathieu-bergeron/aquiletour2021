function initializeDashboard(viewRootElement, jSweet){

    var buttonAvailable = viewRootElement.find("#buttonAvailable");
    var available = viewRootElement.find("#teacherAvailable");
    var availableLink = viewRootElement.find("#availableLink");
    var buttonAddCourse = viewRootElement.find("#add-course-submit-button");

    $(function() {
      
            
        $( "#courses-container" ).sortable({
            handle:'.handle',
            update: function(event, ui){
                

                const course = $(ui.item);
                const nextCourse = $(course.next());
                const prevCourse = $(course.prev());

                const courseId = course.attr("id");
                const nextCourseId = nextCourse.attr("id");
                const prevCourseId = prevCourse.attr("id");


                if (nextCourse.length > 0) {
                    
                    //window.location = window.location.pathname + "?moveId=" + courseId + "&beforeId=" + nextcourseId;
                    history.pushState({
                        id: 'queue'
                      }, 'Queue', window.location.pathname + "?moveId=" + courseId + "&beforeId=" + nextCourseId);
                    
                } else if (prevCourse.length > 0) {
                
                    //window.location = window.location.pathname + "?moveId=" + courseId + "&afterId=" + prevcourseId;

                    history.pushState({
                        id: 'queue'
                      }, 'Queue', window.location.pathname + "?moveId=" + courseId + "&afterId=" + prevCourseId);
                }

                console.log(nextCourse);
            }
        
        });
        
        
    });
    
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
      }
      
      countChecked();
      $(":checkbox").click(countChecked);
    });

    buttonAvailable.onclick = function() {
      if (available.style.background == "green") {
        available.style.background = "red";

        disableLink();
        
      } else {
        available.style.background = "green";
        
        showLink();    
      }
      
    }

    function disableLink() {

      availableLink.disabled=true;
      availableLink.removeAttribute('href');    
      availableLink.style.textDecoration = 'none';
      availableLink.style.cursor = 'default';
    }

    function showLink() {
      availableLink.disabled=false;
      //assign href dynamically
      availableLink.href = "somepage.html";
      availableLink.style.textDecoration = "underline";
      availableLink.style.cursor = "hand";
    }

    buttonAddCourse.onclick = function() {

      document.getElementById("card-header").className = "ghost-header";
        document.getElementById("card-body").className = "ghost-body";
        document.getElementById("shimmering").className = "shimmering";

        setTimeout(function() {
          document.getElementById("card-header").className = "card-header";
          document.getElementById("card-body").className = "card-body";
          document.getElementById("shimmering").className = "";
        },2500);
    }    

    /*
    coursesContainer.sortable({
      handle:'.handle'
    });*/
}
