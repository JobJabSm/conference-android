package com.systers.conference.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

import lombok.Data;

@Entity
@Data
public class Session {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String sessiondate;
    private String starttime;
    private String endtime;
    private String sessiontype;
    private String location;
    private String description;
    private boolean isBookmarked;
    @Ignore
    private List<Track> tracks;
    @Ignore
    private List<Speaker> speakers;
}