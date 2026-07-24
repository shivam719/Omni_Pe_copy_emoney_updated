package com.fintech.omnipe.Notification;

import android.os.Bundle;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.fintech.omnipe.Api.Response.AppUserListResponse;
import com.fintech.omnipe.Notification.Adapter.NotificationListAdapter;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.UtilMethods;


public class NotificationFragment extends BottomSheetDialogFragment {

    AppUserListResponse mNotificationResponse;
    RecyclerView recycler_view;
    TextView noData;
    NotificationListAdapter mNotificationListAdapter;
    private int readCount;
    private View rlCancel;
    SetReadNotification mSetReadNotification;

    public void setCallBack(SetReadNotification mSetReadNotification) {
        this.mSetReadNotification = mSetReadNotification;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notifications, container, false);
        rlCancel = v.findViewById(R.id.rl_close);
        recycler_view = v.findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        noData = v.findViewById(R.id.noData);
        mNotificationResponse = new Gson().fromJson(UtilMethods.INSTANCE.getNotificationList(getActivity()), AppUserListResponse.class);

        if (mNotificationResponse != null && mNotificationResponse.getNotifications() != null && mNotificationResponse.getNotifications().size() > 0) {
            mNotificationListAdapter = new NotificationListAdapter(getActivity(), mNotificationResponse.getNotifications(), new NotificationListAdapter.CountCallBack() {
                @Override
                public void onClickNotification(int position) {
                    mNotificationResponse.getNotifications().get(position).setSeen(true);
                    mNotificationListAdapter.notifyDataSetChanged();
                    UtilMethods.INSTANCE.setNotificationList(getActivity(), new Gson().toJson(mNotificationResponse));
                    readCount++;
                    if (mSetReadNotification != null) {
                        mSetReadNotification.setReadCount(readCount);
                    }
                }
            });
            recycler_view.setAdapter(mNotificationListAdapter);
        } else {
            // noData.setVisibility(View.VISIBLE);
        }
        rlCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return v;
    }


    public interface SetReadNotification {

        void setReadCount(int readCount);

    }

}
