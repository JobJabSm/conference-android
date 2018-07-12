package com.systers.conference.data.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class SessionList {
    @SerializedName("sessionid")
    private String sessionId;

    @SerializedName("sessionkey")
    private String sessionKey;
}
