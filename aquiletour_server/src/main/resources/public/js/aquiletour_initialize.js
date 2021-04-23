function initializeView(viewName, viewRootElement, jSweet){
    //console.log(viewName);

    if(viewName === "TeacherQueueViewWeb"){

        initializeQueue(viewRootElement, jSweet);
        
    }else if(viewName === "TeacherDashboardViewWeb"){

        initializeDashboard(viewRootElement, jSweet);

    }else if(viewName === "CommitListViewWeb"){

        initializeCommitList(viewRootElement, jSweet);

    }else if(viewName === "CourseViewWeb"){

        initializeCourse(viewRootElement, jSweet);
    }else if(viewName === "RootViewWeb"){

        initializeCourse(viewRootElement, jSweet);
    }
}
