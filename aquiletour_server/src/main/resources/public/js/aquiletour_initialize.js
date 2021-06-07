$(document).ready(function(){
    initializeWidgets($(document));

    // XXX: avoid form resubmission on refresh
    //      https://www.webtrickshome.com/faq/how-to-stop-form-resubmission-on-page-refresh
    if (window.history.replaceState) {
        window.history.replaceState( null, null, window.location.href );
    }
});

function initializeView(viewName, viewRootElement, jSweet){

    if(viewName === "RootViewWeb"){

        initializeRoot(viewRootElement, jSweet);

    }else if(viewName === "QueueViewWebTeacher"){

        initializeQueue(viewRootElement, jSweet);
        initializeCheckboxes(viewRootElement);

    }else if(viewName === "TeacherDashboardViewWeb"){

        initializeDashboard(viewRootElement, jSweet);

    }else if(viewName === "CommitListViewWeb"){

        initializeCommitList(viewRootElement, jSweet);

    }else if(viewName === "CourseViewWeb"){

        initializeCourse(viewRootElement, jSweet);

    }else if(viewName === "SemesterViewWebTeacher"){

        initializeSemester(viewRootElement, jSweet);

    }else if(viewName === "SemesterViewWebAdmin"){

        initializeSemester(viewRootElement, jSweet);

    }else if(viewName === "SemesterViewWeb"){

        initializeCourseItem(viewRootElement, jSweet);
    }

}

