function initializeRoot(viewRootElement, jSweet){

    const navBarA = viewRootElement.find('.navbar-nav>li>a');
    const loginDropdown = viewRootElement.find('#login-dropdown');

    const loginTabStudent = viewRootElement.find('#login-tab-student');
    const loginTabTeacher = viewRootElement.find('#login-tab-teacher');

    const studentLoginCollapse = viewRootElement.find('#student-login-collapse');
    const teacherLoginCollapse = viewRootElement.find('#teacher-login-collapse');

    navBarA.on('click', function(){
        $('.navbar-collapse').collapse('hide');
    });

    loginDropdown.on('click', function(e){
        e.stopPropagation();
    })

    loginTabStudent.on('click', function(e){
        studentLoginCollapse.collapse('show');
        teacherLoginCollapse.collapse('hide');

        loginTabStudent.addClass('login-tab-selected');
        loginTabTeacher.removeClass('login-tab-selected');
    })

    loginTabTeacher.on('click', function(e){
        studentLoginCollapse.collapse('hide');
        teacherLoginCollapse.collapse('show');

        loginTabStudent.removeClass('login-tab-selected');
        loginTabTeacher.addClass('login-tab-selected');
    })
}
