function initializeView(viewName, viewRootElement){
    //console.log(viewName);

    if(viewName === "TeacherQueueViewWeb"){

        initializeQueue(viewRootElement);
        
    }else if(viewName === "TeacherDashboardViewWeb"){

        initializeDashboard(viewRootElement);
    }
}
