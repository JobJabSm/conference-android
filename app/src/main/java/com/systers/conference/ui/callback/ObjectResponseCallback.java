package com.systers.conference.ui.callback;

public interface ObjectResponseCallback<T> {
    void OnSuccess(T response);

    void OnFailure(Throwable error);
}
