function initializeDashboard(viewRootElement, jSweet){

    var buttonAvailable = document.getElementById("buttonAvailable");
    var available = document.getElementById("teacherAvailable");
    var availableLink = document.getElementById("availableLink");
    var buttonAddCourse = document.getElementById("add-course-submit-button");

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
    
    
    const buttonAvailable = viewRootElement.find("#buttonAvailable");
    const available = viewRootElement.find("#teacherAvailable");
    const availableLink = viewRootElement.find("#availableLink");
    const coursesContainer = viewRootElement.find("#courses-container");

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
    coursesContainer.sortable({
      handle:'.handle'
    });
}
