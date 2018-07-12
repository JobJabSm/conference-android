package com.systers.conference.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

import lombok.Data;

@Entity
@Data
public class Speaker {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String role;
    private String company;
    private String avatarUrl;
    private String description;
    private String email;
    private String googlePlusUrl;
    private String fbUrl;
    private String twitterUrl;
    private String linkedinUrl;
    @Ignore
    private final List<Session> sessions = null;

    public List<Session> getSessions() {
        return sessions;
    }
}
