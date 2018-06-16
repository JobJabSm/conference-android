package com.systers.conference.data.model;

import com.google.gson.annotations.SerializedName;

public class AttendeeId {
    @SerializedName("attendeeid")
    private String attendeeId;

    public String getAttendeeId() {
        return attendeeId;
    }
}
