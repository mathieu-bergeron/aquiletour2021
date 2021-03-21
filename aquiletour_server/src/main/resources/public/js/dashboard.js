window.onload = function(){
    // in JSWeet, we must call initializeDashboard when it gets loaded
    initializeDashboard();
}

function initializeDashboard(){
    $('#modalDashboard').modal();

    var addQueueButton = $("#add-queue-button");
    var buttonAvailable = $("#buttonAvailable");
    var available = document.getElementById("teacherAvailable");
    var availableLink = document.getElementById("availableLink");

    addQueueButton.on('click', function(){
        showAddQueueModal();
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

    $(function() {
      $( "#course-cards" ).sortable({
          handle:'.handle'
      });
    
      
    } );
}

function hideAddQueueModal(){
    $('#modalDashboard').modal('hide');
}

function showAddQueueModal(){
    $('#modalDashboard').modal('show');
}

