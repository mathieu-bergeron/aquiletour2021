package ca.ntro.android;

import ca.ntro.android.services.InitializationTaskAndroid;
import ca.ntro.core.initialization.NtroInitializationTask;
import ca.ntro.core.system.trace.__T;

public class NtroAndroid {

    public static NtroInitializationTask defaultInitializationTask(){
        __T.call(NtroAndroid.class, "defaultInitializationTask");

        NtroInitializationTask initializationTask = new NtroInitializationTask();
        initializationTask.addSubTask(new InitializationTaskAndroid());

        return initializationTask;
    }


}
