package com.systers.conference.data.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class AttendeeId {
    @SerializedName("attendeeid")
    private String attendeeId;
}
