package com.lasalle.second.part.propertycross;

import android.app.Application;
import android.content.Context;

import com.lasalle.second.part.propertycross.services.ApplicationServiceFactory;
import com.lasalle.second.part.propertycross.util.VolleyRequestHandler;

public class PropertyCrossApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
