function initializeRoot(viewRootElement, JSweet){

    const navBarA = viewRootElement.find('.navbar-nav>li>a');

    const loginDropdown = viewRootElement.find('#login-dropdown');
    const loginButton = viewRootElement.find('#login-button');
    const loginMenuMessage = viewRootElement.find('#login-menu-message');

    const loginTabStudent = viewRootElement.find('#login-tab-student');
    const loginTabTeacher = viewRootElement.find('#login-tab-teacher');

    const studentLoginCollapse = viewRootElement.find('#student-login-collapse');
    const teacherLoginCollapse = viewRootElement.find('#teacher-login-collapse');

    const submitLinks = viewRootElement.find('.submit-link');

    const logoLoading = viewRootElement.find('#logo-loading');
    const logoImage = viewRootElement.find('#logo-image');

    if(!JSweet){
    	logoLoading.hide();
    	logoImage.show();
    }


    //loginButton.dropdown();

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

    submitLinks.on('click', function(e){
        e.preventDefault();
        const formId = $(this).attr("form");
        const form = viewRootElement.find("#" + formId);
        form.submit();
    });

}
