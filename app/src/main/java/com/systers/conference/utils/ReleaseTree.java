package com.systers.conference.utils;

import android.support.annotation.Nullable;
import android.util.Log;

import timber.log.Timber.Tree;

/**
 * Created by haroon on 09/07/18.
 */

public class ReleaseTree extends Tree {

    @Override
    protected void log(int priority, String tag, @Nullable String message, Throwable t) {
        // Only log WARN, ERROR, and WTF.
        if (priority > Log.INFO) {
            Log.println(priority, tag, message);
        }
    }
}
