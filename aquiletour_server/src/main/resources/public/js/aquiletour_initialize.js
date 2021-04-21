function initializeView(viewName, viewRootElement, jSweet){

    if(viewName === "TeacherQueueViewWeb"){

        initializeQueue(viewRootElement, jSweet);
        
    }else if(viewName === "TeacherDashboardViewWeb"){

        initializeDashboard(viewRootElement, jSweet);

    }else if(viewName === "CommitListViewWeb"){

        //initializeCommitList(viewRootElement, jSweet);

    }else if(viewName === "CourseViewWeb"){

        initializeCourse(viewRootElement, jSweet);

    }else if(viewName === "SemesterViewWeb"){

        initializeSemester(viewRootElement, jSweet);
    }
}
