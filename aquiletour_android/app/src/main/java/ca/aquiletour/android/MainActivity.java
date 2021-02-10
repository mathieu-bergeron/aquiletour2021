package ca.aquiletour.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ca.ntro.android.NtroAndroid;

public class MainActivity extends AppCompatActivity {

    static {
        String[] options = new String[]{"--traceLevel","APP"};

        NtroAndroid.defaultInitializationTask()
                .setOptions(options)
                .addNextTask(new AquiletourMainAndroid())
                .execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: add rootView to root viewLoader
        //       notify that root viewLoader has finishedTask
    }
}