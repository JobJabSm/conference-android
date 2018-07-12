package com.systers.conference.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import lombok.Data;

@Entity
@Data
public class Event {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String startdate;
    private String enddate;
}
