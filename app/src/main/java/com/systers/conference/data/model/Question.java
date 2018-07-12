package com.systers.conference.data.model;


import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Question {
    @SerializedName("questionid")
    private String questionId;

    @SerializedName("fieldname")
    private String fieldName;

    @SerializedName("inputtype")
    private String inputType;

    @SerializedName("name")
    private String displayName;

    @SerializedName("pageid")
    private String pageId;

    @SerializedName("page")
    private String pageName;
}
