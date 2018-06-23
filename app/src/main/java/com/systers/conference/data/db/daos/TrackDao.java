package com.systers.conference.data.db.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.systers.conference.data.model.Track;

import java.util.List;

/**
 * Created by haroon on 6/21/18.
 */

@Dao
public interface TrackDao {

    @Query("SELECT * FROM Track")
    LiveData<List<Track>> getTracks();

    @Insert
    void insertTracks(Track... tracks);
}
