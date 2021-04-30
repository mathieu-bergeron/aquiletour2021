$(document).ready(function(){

    const viewRootElement = $(document);
    const jSweet = false;


    initializeView("RootViewWeb", viewRootElement, jSweet);
    initializeView("TeacherDashboardViewWeb", viewRootElement, jSweet);
    initializeView("TeacherCourseSummaryViewWeb", viewRootElement, jSweet);
    initializeView("QueuesViewWeb", viewRootElement, jSweet);
    initializeView("QueueSummaryViewWeb", viewRootElement, jSweet);
    initializeView("QueueViewWebTeacher", viewRootElement, jSweet);
    initializeView("QueueViewWebStudent", viewRootElement, jSweet);
    initializeView("LoginViewWeb", viewRootElement, jSweet);
    initializeView("HomeViewWeb", viewRootElement, jSweet);
    initializeView("CourseViewWeb", viewRootElement, jSweet);
    initializeView("CommitListViewWeb", viewRootElement, jSweet);
    initializeView("SemesterViewWeb", viewRootElement, jSweet);
    initializeView("CourseItemViewWeb", viewRootElement, jSweet);

    initializeWidgets(viewRootElement);
});
