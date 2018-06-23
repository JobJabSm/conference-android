package com.systers.conference.data.db.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.systers.conference.data.db.ConferenceDatabase;
import com.systers.conference.data.db.daos.SessionDao;
import com.systers.conference.data.model.Session;

import java.util.List;

/**
 * Created by haroon on 6/21/18.
 */

public class SessionRepository {

    private SessionDao sessionDao;
    private LiveData<List<Session>> sessions;

    public SessionRepository(Application application) {
        ConferenceDatabase conferenceDatabase = ConferenceDatabase.getDb(application);
        sessionDao = conferenceDatabase.sessionDao();
        sessions = sessionDao.getSessions();
    }

    public LiveData<List<Session>> getAllSessions() {
        return sessions;
    }

    public LiveData<Session> getSessionById(String id) {
        return sessionDao.getSessionById(id);
    }

    public LiveData<List<Session>> getSessionsByDay(String day) {
        return sessionDao.getSessionsByDay(day);
    }

    public void insertSessions (Session... sessions) {
        new insertAsyncSessions(sessionDao).execute(sessions);
    }

    public LiveData<List<Session>> getBookmarkedSessions () {
        return sessionDao.getBookmarkedSessions();
    }

    private static class insertAsyncSessions extends AsyncTask<Session, Void, Void> {

        private SessionDao asyncSessionDao;

        insertAsyncSessions(SessionDao sessionDao) {
            asyncSessionDao = sessionDao;
        }

        @Override
        protected Void doInBackground(Session... sessions) {
            asyncSessionDao.insertSessions(sessions);
            return null;
        }
    }
}
