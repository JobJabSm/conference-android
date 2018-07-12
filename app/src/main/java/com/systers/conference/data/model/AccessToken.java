package com.systers.conference.data.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class AccessToken {
    @SerializedName("accesstoken")
    private String token;
}
