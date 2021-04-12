onCreate(NtroContext context){

    MyTask viewModel = new MyTask();

    viewModel.dependsOn(RootView.class, "rootView");
    viewModel.dependsOn(RootModel.class, "model");
    viewModel.dependsOn(DashboardModel.class, "dashboard");

    /*  A
     *  B   ====>  D
     *  C
     *
     */

    viewModel.followedBy(ExpressionEvaluator.class);




}


class MyTask extends Task{

    @Override
    public execute(ResultsMap previousTasksResults, 
                   ResultsMap subTasksResults,
                   NtroContext context,
                   NtroConstants contantsMap,
            ){

        RootView rootView = previousTasksResults.get(RootView.class, "rootView");
        RootModel model = previousTasksResults.get(RootView.class, "model");

        /* En JS 
         *
         * model.myValue;
         *
         *
         */


    }



}

