package com.my.words;

import android.app.Application;
import android.content.Context;
import com.words.core.Scene;

/**
 * Created by mara on 3/28/14.
 */
public class WordApplication extends Application {
    private static Scene scene;
    private static Configurator configurator;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        this.scene = new Scene(this);
        this.configurator = new Configurator();
        this.context = getApplicationContext();
    }

    public static Scene getScene() {
        return scene;
    }

    public static Configurator getConfigurator() {
        return configurator;
    }

    public static Context getContext() {
        return context;
    }
}
