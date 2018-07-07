package com.systers.conference.ui.online.event;

import android.arch.lifecycle.Observer;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.squareup.picasso.Picasso;
import com.systers.conference.R;
import com.systers.conference.data.db.repositories.SessionRepository;
import com.systers.conference.data.model.Session;
import com.systers.conference.data.model.Speaker;
import com.systers.conference.ui.base.BaseActivity;
import com.systers.conference.ui.views.DayWiseScheduleViewHolder;
import com.systers.conference.utils.DateTimeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventDetailActivity extends BaseActivity implements EventDetailMvpView{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.time)
    TextView mTime;
    @BindView(R.id.event_description)
    TextView mEventDescription;
    @BindView(R.id.fab_menu)
    FloatingActionsMenu mFloatingActionsMenu;
    @BindView(R.id.room)
    TextView mRoom;
    @BindView(R.id.audience_level)
    TextView mAudience;
    FloatingActionButton mBookmark;
    @BindView(R.id.speakers_container)
    ViewGroup mSpeakers;
    @BindView(R.id.speakers_header)
    TextView mSpeakerListHeader;
    private Session mSession;
    private List<Speaker> mSpeakerList = new ArrayList<>();
    private SessionRepository sessionRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        ButterKnife.bind(this);
        sessionRepository = new SessionRepository(getApplication());
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        showEventDetails();
    }


    private void setDrawables() {
        Drawable iconDrawable = AppCompatResources.getDrawable(this, R.drawable.ic_place_grey600_24dp);
        mRoom.setCompoundDrawablesWithIntrinsicBounds(iconDrawable, null, null, null);
        iconDrawable = AppCompatResources.getDrawable(this, R.drawable.ic_access_time_grey600_24dp);
        mTime.setCompoundDrawablesWithIntrinsicBounds(iconDrawable, null, null, null);
        iconDrawable = AppCompatResources.getDrawable(this, R.drawable.ic_people_grey600_24dp);
        mAudience.setCompoundDrawablesWithIntrinsicBounds(iconDrawable, null, null, null);
        iconDrawable = AppCompatResources.getDrawable(this, R.drawable.ic_calendar_plus);
        iconDrawable = AppCompatResources.getDrawable(this, R.drawable.ic_share_grey600_24dp);
    }

    private void updateSession() {
        mEventDescription.setText(mSession.getDescription());
        mAudience.setText(mSession.getSessiontype());
        mRoom.setText(mSession.getLocation());
        String startTime = DateTimeUtil.getTimeFromTimeStamp(DateTimeUtil.FORMAT_24H, Long.valueOf(mSession.getStarttime()));
        String endTime = DateTimeUtil.getTimeFromTimeStamp(DateTimeUtil.FORMAT_24H, Long.valueOf(mSession.getEndtime()));
        Date date = DateTimeUtil.getDate(mSession.getSessiondate());
        String descriptiveDate = "";
        if (date != null) {
            descriptiveDate = DateTimeUtil.getDateDescriptive(date);
        }
        mTime.setText(descriptiveDate + ", " + startTime + " - " + endTime);
        if (mSession.isBookmarked()) {
            Drawable iconDrawable = AppCompatResources.getDrawable(this, R.drawable.ic_bookmark_grey600_24dp);
            mBookmark.setIconDrawable(iconDrawable);
            mBookmark.setTitle(getString(R.string.remove_from_bookmarks));
        } else {
            Drawable iconDrawable = AppCompatResources.getDrawable(this, R.drawable.ic_bookmark_border_grey600_24dp);
            mBookmark.setIconDrawable(iconDrawable);
            mBookmark.setTitle(getString(R.string.add_to_bookmarks));
        }
    }

    private void updateSpeakers() {
        mSpeakers.removeAllViews();
        mSpeakerList.clear();
        mSpeakerList.addAll(mSession.getSpeakers());
        if (mSpeakerList != null && mSpeakerList.size() > 0) {
            mSpeakerListHeader.setVisibility(View.VISIBLE);
            mSpeakers.setVisibility(View.VISIBLE);
            for (Speaker speaker : mSpeakerList) {
                View view = LayoutInflater.from(this).inflate(R.layout.speaker_list_item, mSpeakers, false);
                ImageView avatar = view.findViewById(R.id.speaker_avatar_icon);
                if (!TextUtils.isEmpty(speaker.getAvatarUrl())) {
                    Picasso.with(this).load(speaker.getAvatarUrl())
                            .resize(80, 80)
                            .centerCrop()
                            .placeholder(R.drawable.anita_borg_logo)
                            .error(R.drawable.anita_borg_logo)
                            .into(avatar);
                }
                TextView speakerName = (TextView) view.findViewById(R.id.speaker_name);
                speakerName.setText(speaker.getName());
                TextView speakerRole = (TextView) view.findViewById(R.id.speaker_role);
                speakerRole.setText(speaker.getRole() + ", " + speaker.getCompany());
                mSpeakers.addView(view);
            }
        } else {
            mSpeakerListHeader.setVisibility(View.GONE);
            mSpeakers.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if (mFloatingActionsMenu.isExpanded()) {
            mFloatingActionsMenu.collapse();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showEventDetails() {
        setDrawables();
        sessionRepository.getSessionById(getIntent()
                .getStringExtra(DayWiseScheduleViewHolder.SESSION_ID)).observe(this,
                new Observer<Session>() {
                    @Override
                    public void onChanged(@Nullable Session session) {
                        updateSession();
                        updateSpeakers();
                    }
                });
    }

    @OnClick(R.id.calendar_fab)
    public void addToCalendar() {
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.Events.TITLE, mSession.getName());
        intent.putExtra(CalendarContract.Events.DESCRIPTION, mSession.getDescription());
        try {
                 startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, R.string.calendar_not_found, Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.share_fab)
    public void share() {
        //TODO: Share your event here
    }

    @OnClick(R.id.bookmark_fab)
    public void toggleBookmark() {
        if (mSession != null) {
            if (mSession.isBookmarked()) {
                mSession.setBookmarked(false);
                sessionRepository.insertSessions(mSession);
            } else {
                mSession.setBookmarked(true);
                sessionRepository.insertSessions(mSession);
            }
        }
    }
}
