package com.systers.conference.ui.online.schedule.day_wise;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.systers.conference.data.db.repositories.SessionRepository;
import com.systers.conference.data.model.Session;

import java.util.List;

/**
 * Created by haroon on 6/21/18.
 */

public class DayScheduleViewModel extends AndroidViewModel {

    private LiveData<List<Session>> sessions;
    private SessionRepository sessionRepository;

    public DayScheduleViewModel(@NonNull Application application) {
        super(application);

        sessionRepository = new SessionRepository(application);
        sessions = sessionRepository.getAllSessions();
    }

    public LiveData<List<Session>> getSessions() {
        return sessions;
    }

    public LiveData<Session> getSessionById(String id) {
        return sessionRepository.getSessionById(id);
    }

    public LiveData<List<Session>> getSessionsByDay(String day) {
        return sessionRepository.getSessionsByDay(day);
    }

    public LiveData<List<Session>> getBookmerkedSessions() {
        return sessionRepository.getBookmarkedSessions();
    }
}
