$(document).ready(function(){
    initializeWidgets($(document));
});

function initializeView(viewName, viewRootElement, jSweet){

    if(viewName === "QueueViewWebTeacher"){

        initializeQueue(viewRootElement, jSweet);
        
    }else if(viewName === "TeacherDashboardViewWeb"){

        initializeDashboard(viewRootElement, jSweet);

    }else if(viewName === "CommitListViewWeb"){

        //initializeCommitList(viewRootElement, jSweet);

    }else if(viewName === "CourseViewWeb"){

        initializeCourse(viewRootElement, jSweet);

    }else if(viewName === "SemesterViewWeb"){

        initializeSemester(viewRootElement, jSweet);

    }else if(viewName === "SemesterViewWeb"){

        initializeCourseItem(viewRootElement, jSweet);
    }
}

