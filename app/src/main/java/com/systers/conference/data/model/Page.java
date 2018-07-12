package com.systers.conference.data.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Page {
    @SerializedName("pageid")
    private String pageId;

    @SerializedName("page")
    private String pageName;
}
