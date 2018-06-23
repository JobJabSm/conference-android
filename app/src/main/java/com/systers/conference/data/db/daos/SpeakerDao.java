package com.systers.conference.data.db.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.systers.conference.data.model.Speaker;

import java.util.List;

/**
 * Created by haroon on 6/21/18.
 */

@Dao
public interface SpeakerDao {

    @Query("SELECT * FROM Speaker")
    LiveData<List<Speaker>> getSpeakers();

    @Insert
    void insertSpeakers(Speaker... speakers);
}
