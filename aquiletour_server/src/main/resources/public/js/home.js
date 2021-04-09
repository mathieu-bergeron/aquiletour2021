window.onload = function(){

    $("#roleStudent").css('background-color', '#63999C');

    $("a").click(function(){
      if (this.id == 'roleStudent') {

        $(this).css('background-color', '#63999C');
        $("#roleTeacher").css('background-color', '');
        $("#userRole").text('No de DA');

        history.pushState({
          id: 'homepage'
        }, 'Home', window.location.pathname + "?connectRole=Student");

      } else if (this.id == 'roleTeacher') {

        $(this).css('background-color', '#63999C');
        $("#roleStudent").css('background-color', '');
        $("#userRole").text('No d\'employe');

        history.pushState({
          id: 'homepage'
        }, 'Home', window.location.pathname + "?connectRole=Teacher");
      }
    });

    $("#btn-close").click(function(){
      history.pushState({
          id: 'homepage'
        }, 'Home', window.location.pathname);
    })

    
}