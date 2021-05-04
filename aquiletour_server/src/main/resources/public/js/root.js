window.onload = function(){
    $('.navbar-nav>li>a').on('click', function(){
        $('.navbar-collapse').collapse('hide');
    });

    $('#login-dropdown').on('click', function(e){
        e.stopPropagation();
    })

    $('#roleStudent').on('click', function(e){
        $('#student-login-collapse').collapse('show');
        $('#teacher-login-collapse').collapse('hide');
    })

    $('#roleTeacher').on('click', function(e){
        $('#student-login-collapse').collapse('hide');
        $('#teacher-login-collapse').collapse('show');
    })
}
