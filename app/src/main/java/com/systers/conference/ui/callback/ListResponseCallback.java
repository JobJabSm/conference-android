package com.systers.conference.ui.callback;

import java.util.List;

public interface ListResponseCallback<T> {
    void onSuccess(List<T> response);
}
