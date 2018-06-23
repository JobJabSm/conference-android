package com.systers.conference.data.db.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.systers.conference.data.model.Session;

import java.util.List;

/**
 * Created by haroon on 6/21/18.
 */

@Dao
public interface SessionDao {

    @Query("SELECT * FROM Session")
    LiveData<List<Session>> getSessions();

    @Query("SELECT * FROM Session where id=:id")
    LiveData<Session> getSessionById(String id);

    @Query("SELECT * FROM Session where id=:day")
    LiveData<List<Session>> getSessionsByDay(String day);

    @Query("SELECT * FROM Session where isBookmarked = 1")
    LiveData<List<Session>> getBookmarkedSessions();

    @Insert

    void insertSessions(Session... sessions);
}
