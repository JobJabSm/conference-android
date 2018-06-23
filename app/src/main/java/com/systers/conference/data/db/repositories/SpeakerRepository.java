package com.systers.conference.data.db.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.systers.conference.data.db.ConferenceDatabase;
import com.systers.conference.data.db.daos.SpeakerDao;
import com.systers.conference.data.model.Speaker;

import java.util.List;

/**
 * Created by haroon on 6/21/18.
 */

public class SpeakerRepository {

    private SpeakerDao speakerDao;
    private LiveData<List<Speaker>> speakers;

    public SpeakerRepository(Application application) {
        ConferenceDatabase conferenceDatabase = ConferenceDatabase.getDb(application);
        speakerDao = conferenceDatabase.speakerDao();
        speakers = speakerDao.getSpeakers();
    }

    public LiveData<List<Speaker>> getAllSpeakers() {
        return speakers;
    }

    public void insertSpeakers (Speaker... speakers) {
        new insertAsyncSpeakers(speakerDao).execute(speakers);
    }

    private static class insertAsyncSpeakers extends AsyncTask<Speaker, Void, Void> {

        private SpeakerDao asyncSpeakerDao;

        insertAsyncSpeakers(SpeakerDao speakerDao) {
            asyncSpeakerDao = speakerDao;
        }

        @Override
        protected Void doInBackground(Speaker... speakers) {
            asyncSpeakerDao.insertSpeakers(speakers);
            return null;
        }
    }
}
