window.onload = function(){

    var buttonAvailable = document.getElementById("buttonAvailable");
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
}
