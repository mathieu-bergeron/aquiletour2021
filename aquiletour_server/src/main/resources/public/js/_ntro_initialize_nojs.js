$(document).ready(function(){

    const viewRootElement = $(document);
    const jSweet = false;

    initializeView("RootViewWeb", viewRootElement, jSweet);
    initializeView("TeacherDashboardViewWeb", viewRootElement, jSweet);
    initializeView("TeacherCourseSummaryViewWeb", viewRootElement, jSweet);
    initializeView("QueuesViewWeb", viewRootElement, jSweet);
    initializeView("QueueSummaryViewWeb", viewRootElement, jSweet);
    initializeView("TeacherQueueViewWeb", viewRootElement, jSweet);
    initializeView("StudentQueueViewWeb", viewRootElement, jSweet);
    initializeView("LoginViewWeb", viewRootElement, jSweet);
    initializeView("HomeViewWeb", viewRootElement, jSweet);
    initializeView("CourseViewWeb", viewRootElement, jSweet);

});