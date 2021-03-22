function initializeDashboard(viewRootElement){

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

    coursesContainer.sortable({
      handle:'.handle'
    });
}
