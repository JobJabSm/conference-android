package com.systers.conference.data.db.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.systers.conference.data.db.ConferenceDatabase;
import com.systers.conference.data.db.daos.EventDao;
import com.systers.conference.data.model.Event;

import java.util.List;

/**
 * Created by haroon on 6/21/18.
 */

public class EventRepository {

    private EventDao eventDao;
    private LiveData<List<Event>> events;

    public EventRepository(Application application) {
        ConferenceDatabase conferenceDatabase = ConferenceDatabase.getDb(application);
        eventDao = conferenceDatabase.eventDao();
        events = eventDao.getEvents();
    }

    public LiveData<List<Event>> getAllEvents() {
        return events;
    }

    public void insertEvents (Event... events) {
        new insertAsyncEvents(eventDao).execute(events);
    }

    private static class insertAsyncEvents extends AsyncTask<Event, Void, Void> {

        private EventDao asyncEventDao;

        insertAsyncEvents(EventDao eventDao) {
            asyncEventDao = eventDao;
        }

        @Override
        protected Void doInBackground(Event... events) {
            asyncEventDao.insertEvents(events);
            return null;
        }
    }
}
