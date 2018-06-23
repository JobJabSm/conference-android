package com.systers.conference.ui.online.schedule;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.systers.conference.data.db.repositories.EventRepository;
import com.systers.conference.data.model.Event;

import java.util.List;

/**
 * Created by haroon on 6/21/18.
 */

public class ScheduleViewModel extends AndroidViewModel {

    private LiveData<List<Event>> events;

    public ScheduleViewModel(@NonNull Application application) {
        super(application);

        EventRepository eventRepository = new EventRepository(application);
        events = eventRepository.getAllEvents();
    }

    public LiveData<List<Event>> getEvents() {
        return events;
    }
}
