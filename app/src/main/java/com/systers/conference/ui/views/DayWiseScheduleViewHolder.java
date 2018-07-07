package com.systers.conference.ui.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.systers.conference.R;
import com.systers.conference.data.model.Session;
import com.systers.conference.ui.online.event.EventDetailActivity;
import com.systers.conference.utils.DateTimeUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DayWiseScheduleViewHolder extends ViewHolder {

    public static final String SESSION_ID = "mSession-id";
    @BindView(R.id.schedule_list_item)
    CardView listItem;
    @BindView(R.id.title)
    TextView sessionTitle;
    @BindView(R.id.subtitle)
    TextView sessionSubTitle;
    private Session mSession;
    private Context mContext;

    public DayWiseScheduleViewHolder(View itemView, Context context) {
        super(itemView);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    public void bindSession() {
        sessionTitle.setText(mSession.getName());
        sessionSubTitle.setText(DateTimeUtil.getTimeFromTimeStamp(DateTimeUtil.FORMAT_24H, Long.valueOf(mSession.getStarttime())) + " - " + DateTimeUtil.getTimeFromTimeStamp(DateTimeUtil.FORMAT_24H, Long.valueOf(mSession.getEndtime())) + ", " + mSession.getLocation());
        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EventDetailActivity.class);
                intent.putExtra(SESSION_ID, mSession.getId());
                mContext.startActivity(intent);
            }
        });
    }

    public Session getSession() {
        return mSession;
    }

    public void setSession(Session session) {
        mSession = session;
    }
}
