package com.systers.conference.ui.online.schedule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.systers.conference.R;
import com.systers.conference.data.db.RealmDataRepository;
import com.systers.conference.data.model.Event;
import com.systers.conference.ui.online.schedule.day_wise.DayWiseScheduleFragment;
import com.systers.conference.utils.DateTimeUtil;
import com.systers.conference.utils.LogUtils;
import com.systers.conference.ui.views.FontTabLayout;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.RealmResults;

public class ScheduleFragment extends Fragment {

    @BindView(R.id.day_tabs)
    FontTabLayout mTabLayout;
    @BindView(R.id.schedule_view_pager)
    ViewPager mViewPager;
    private Unbinder mUnbinder;
    private ScheduleViewPagerAdapter mAdapter;
    private RealmDataRepository mRealmRepo = RealmDataRepository.getDefaultInstance();
    private RealmResults<Event> mEventResults;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        setupViewPager();
        mTabLayout.setupWithViewPager(mViewPager);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mEventResults.removeAllChangeListeners();
    }

    private void setupViewPager() {
        mAdapter = new ScheduleViewPagerAdapter(getChildFragmentManager());
        mEventResults = mRealmRepo.getEvent();
        mEventResults.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<Event>>() {
            @Override
            public void onChange(RealmResults<Event> events, OrderedCollectionChangeSet changeSet) {
                int i = events.size();
                mAdapter.removeAllFragments();
                if (changeSet != null) {
                    LogUtils.LOGE("Event", Arrays.toString(changeSet.getInsertions()));
                    LogUtils.LOGE("Event", Arrays.toString(changeSet.getChanges()));
                }
                for (int j = 0; j < i; j++) {
                    Date startDate = DateTimeUtil.getDate(events.get(j).getStartDate());
                    Date endDate = DateTimeUtil.getDate(events.get(j).getEndDate());
                    Calendar start = Calendar.getInstance();
                    start.setTime(startDate);
                    Calendar end = Calendar.getInstance();
                    end.setTime(endDate);
                    end.add(Calendar.DATE, 1);
                    int k = 1;
                    for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
                        mAdapter.addFragment(DayWiseScheduleFragment.newInstance(1, DateTimeUtil.formatDate(date)), ("DAY " + k));
                        k++;
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
        mViewPager.setAdapter(mAdapter);
    }
}
