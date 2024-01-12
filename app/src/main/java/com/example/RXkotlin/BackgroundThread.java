package com.example.RXkotlin;

import android.os.Looper;
import android.util.Log;

public class BackgroundThread extends Thread {

    private static final String TAG = BackgroundThread.class.getSimpleName();

    @Override
    public void run() {
        super.run();
        Looper.prepare();
        for (int i = 0; i < 5; i++) {
            Log.d(TAG, i + "");
        }
        Looper.loop();
        Log.d(TAG, "Thread is killed ?");
    }
}