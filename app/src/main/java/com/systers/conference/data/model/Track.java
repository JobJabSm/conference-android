package com.systers.conference.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

import lombok.Data;

@Entity
@Data
public class Track {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String color;
    @Ignore
    private final List<Session> sessions = null;
}
