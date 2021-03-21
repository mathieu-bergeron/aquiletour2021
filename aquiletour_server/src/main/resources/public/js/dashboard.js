window.onload = function(){
    // in JSWeet, we must call initializeDashboard when it gets loaded
    initializeDashboardJs({'jQueryElement':$(document)});

    var buttonAvailable = $("#buttonAvailable");
    var available = document.getElementById("teacherAvailable");
    var availableLink = document.getElementById("availableLink");

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

function initializeDashboardJs(rootHtmlElement){
    let jQueryRoot = rootHtmlElement.jQueryElement;

    let addQueueButton = jQueryRoot.find("#add-queue-button");
    let modalDashboard = jQueryRoot.find("#modalDashboard");

    addQueueButton.on('click', function(){
        modalDashboard.modal('show');
    });
}


