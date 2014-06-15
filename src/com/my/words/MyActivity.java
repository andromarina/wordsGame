package com.my.words;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import com.words.core.Scene;

public class MyActivity extends Activity {
    private Scene scene;
    private Configurator configurator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.scene = WordApplication.getScene();
        setContentView(this.scene);
        this.configurator = WordApplication.getConfigurator();
        this.configurator.restoreProgress();
        this.configurator.initialize();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewGroup parent = (ViewGroup) this.scene.getParent();
        parent.removeAllViews();
        this.configurator.deinitialize();
    }

}
