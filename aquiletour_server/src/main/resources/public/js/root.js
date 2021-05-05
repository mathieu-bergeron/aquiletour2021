function initializeRoot(viewRootElement, jSweet){

    //const testToast = viewRootElement.find('#test-toast');

    const navBarA = viewRootElement.find('.navbar-nav>li>a');

    const itsNotMeLink = viewRootElement.find('#its-not-me-link');

    const loginDropdown = viewRootElement.find('#login-dropdown');
    const loginButton = viewRootElement.find('#login-button');
    const loginMenuMessage = viewRootElement.find('#login-menu-message');

    const loginTabStudent = viewRootElement.find('#login-tab-student');
    const loginTabTeacher = viewRootElement.find('#login-tab-teacher');

    const studentLoginCollapse = viewRootElement.find('#student-login-collapse');
    const teacherLoginCollapse = viewRootElement.find('#teacher-login-collapse');

    //testToast.toast('show');

    navBarA.on('click', function(){
        $('.navbar-collapse').collapse('hide');
    });

    if(loginDropdown.hasClass('show')){
        loginDropdown.removeClass('show');
        loginButton.click();
    }

    loginDropdown.on('click', function(e){
        e.stopPropagation();
    });

    loginDropdown.on('hidden.bs.dropdown', function(){
        loginMenuMessage.hide();
    });

    loginTabStudent.on('click', function(e){
        studentLoginCollapse.collapse('show');
        teacherLoginCollapse.collapse('hide');

        loginTabStudent.addClass('login-tab-selected');
        loginTabTeacher.removeClass('login-tab-selected');
    });

    loginTabTeacher.on('click', function(e){
        studentLoginCollapse.collapse('hide');
        teacherLoginCollapse.collapse('show');

        loginTabStudent.removeClass('login-tab-selected');
        loginTabTeacher.addClass('login-tab-selected');
    });

    itsNotMeLink.on('click', function(e){
        e.preventDefault();
        const formId = itsNotMeLink.attr("form");
        const form = viewRootElement.find("#" + formId);
        form.submit();
    });

}
