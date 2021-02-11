window.onload = function(){
  var buttonAvailable = document.getElementById("buttonAvailable");
  var available = document.getElementById("teacherAvailable");
  var availableLink = document.getElementById("availableLink");
  var confirmToast = document.getElementById("btn-confirm");
  
  buttonAvailable.onclick = function() {
    
    if (available.style.background == "green") {
      available.style.background = "red";
      disableLink();
      
    } else {
      available.style.background = "green";
      showLink();    
    }
    
  }
}



$("#btn-confirm").click(function(){
    $('.toast').toast('show');
});

document.querySelector('#btn-confirm').addEventListener('click', e => {
	alert('Un travail a été ajouté!');
});

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


