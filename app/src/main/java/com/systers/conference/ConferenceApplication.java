package com.systers.conference;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.systers.conference.utils.ReleaseTree;

import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class ConferenceApplication extends Application {
    private static Context appContext;

    public static Context getAppContext() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new ReleaseTree());
        }

        appContext = this;
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .build());
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/calibri.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}
