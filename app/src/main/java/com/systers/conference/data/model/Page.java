package com.systers.conference.data.model;

import com.google.gson.annotations.SerializedName;

public class Page {
    @SerializedName("pageid")
    private String pageId;

    @SerializedName("page")
    private String pageName;

    public String getPageId() {
        return pageId;
    }

    public String getPageName() {
        return pageName;
    }
}
