package com.example.popcornify.ui.splash;

import android.content.Context;

import java.io.IOException;

public class InternetConnection {

    public static boolean checkConnection(Context context) {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException | InterruptedException e)          { e.printStackTrace(); }

        return false;
    }
}