package com.systers.conference.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.systers.conference.data.db.daos.EventDao;
import com.systers.conference.data.db.daos.SessionDao;
import com.systers.conference.data.db.daos.SpeakerDao;
import com.systers.conference.data.db.daos.TrackDao;
import com.systers.conference.data.model.Event;
import com.systers.conference.data.model.Session;
import com.systers.conference.data.model.Speaker;
import com.systers.conference.data.model.Track;

/**
 * Created by haroon on 6/21/18.
 */

@Database(entities = {Event.class, Session.class, Speaker.class, Track.class},
        version = 1, exportSchema = false)
public abstract class ConferenceDatabase extends RoomDatabase {

    private static ConferenceDatabase conferenceDatabase;
    public abstract EventDao eventDao();
    public abstract SessionDao sessionDao();
    public abstract SpeakerDao speakerDao();
    public abstract TrackDao trackDao();

    public static ConferenceDatabase getDb(final Context context) {
        if (conferenceDatabase == null) {
            synchronized (ConferenceDatabase.class) {
                if (conferenceDatabase == null) {
                    conferenceDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            ConferenceDatabase.class, "conference_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return conferenceDatabase;
    }
}
