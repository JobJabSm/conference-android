package com.systers.conference.data.db.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.systers.conference.data.db.ConferenceDatabase;
import com.systers.conference.data.db.daos.TrackDao;
import com.systers.conference.data.model.Track;

import java.util.List;

/**
 * Created by haroon on 6/21/18.
 */

public class TrackRepository {

    private TrackDao trackDao;
    private LiveData<List<Track>> tracks;

    public TrackRepository(Application application) {
        ConferenceDatabase conferenceDatabase = ConferenceDatabase.getDb(application);
        trackDao = conferenceDatabase.trackDao();
        tracks = trackDao.getTracks();
    }

    public LiveData<List<Track>> getAllTracks() {
        return tracks;
    }

    public void insertTracks (Track... tracks) {
        new insertAsyncTracks(trackDao).execute(tracks);
    }

    private static class insertAsyncTracks extends AsyncTask<Track, Void, Void> {

        private TrackDao asyncTrackDao;

        insertAsyncTracks(TrackDao trackDao) {
            asyncTrackDao = trackDao;
        }

        @Override
        protected Void doInBackground(Track... tracks) {
            asyncTrackDao.insertTracks(tracks);
            return null;
        }
    }
}
