package com.systers.conference.data.db.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.systers.conference.data.model.Event;

import java.util.List;

/**
 * Created by haroon on 6/21/18.
 */

@Dao
public interface EventDao {

    @Query("SELECT * FROM Event")
    LiveData<List<Event>> getEvents();

    @Insert
    void insertEvents(Event... events);
}
