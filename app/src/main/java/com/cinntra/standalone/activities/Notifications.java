package com.cinntra.standalone.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cinntra.standalone.R;
import com.cinntra.standalone.adapters.NotificationAdapter;
import com.cinntra.standalone.databinding.ActivityNotificationsBinding;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.globals.MainBaseActivity;
import com.cinntra.standalone.interfaces.FragmentRefresher;
import com.cinntra.standalone.model.NotificationResponse;
import com.cinntra.standalone.model.NotificatonValue;
import com.cinntra.standalone.model.SalesEmployeeItem;
import com.cinntra.standalone.webservices.NewApiClient;
import com.github.ybq.android.spinkit.SpinKitView;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notifications extends MainBaseActivity implements View.OnClickListener, FragmentRefresher {

    private AppCompatActivity activity;
//    @BindView(R.id.notificationList)
//    RecyclerView notificationList;
//    @BindView(R.id.swipeContainer)
//    SwipeRefreshLayout swipeContainer;
//    @BindView(R.id.back_press)
//    RelativeLayout back_press;
//    @BindView(R.id.No_notification_view)
//    LinearLayout No_Notification_view;
//    @BindView(R.id.notificationView)
//    LinearLayout notificationView;
//    @BindView(R.id.loader)
//    SpinKitView loader;

    ArrayList<NotificatonValue> notificationData = new ArrayList<>();
    ActivityNotificationsBinding binding;

    @Override
    protected void onResume() {
    super.onResume();
     }

    @Override
    protected void onCreate(Bundle savedInstanceState)
          {
    super.onCreate(savedInstanceState);
     activity = Notifications.this;
     binding=ActivityNotificationsBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());
   //  ButterKnife.bind(this);


     binding.headerWhiteBack.backPress.setOnClickListener(this);
     if (Globals.checkInternet(Notifications.this))
       callApi();
      swipeManager();



        }

    private void callApi() {
        binding.loader.setVisibility(View.VISIBLE);
        SalesEmployeeItem salesEmployeeItem = new SalesEmployeeItem();
        salesEmployeeItem.setEmp(Prefs.getString(Globals.EmployeeID,""));
        Call<NotificationResponse> call = NewApiClient.getInstance().getApiService().allnotification(salesEmployeeItem);
        call.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                if (response != null)
                {
                    if(response.body().getData().size()>0){

                        notificationData.clear();
                        notificationData.addAll(response.body().getData());
                        binding.notificationList.setLayoutManager(new LinearLayoutManager(Notifications.this,RecyclerView.VERTICAL,false));
                        binding.notificationList.setAdapter(new NotificationAdapter(activity,notificationData));
                    }else{
                        binding.NoNotificationView.setVisibility(View.VISIBLE);
                        binding. notificationView.setVisibility(View.GONE);

                    }
                    binding. loader.setVisibility(View.GONE);
                    binding. swipeContainer.setRefreshing(false);

                }
            }
            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                binding.swipeContainer.setRefreshing(false);
                binding. loader.setVisibility(View.GONE);
            }
        });
    }

    private void swipeManager() {
        binding. swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
     @Override
     public void onRefresh() {
      /*new Handler(Looper.getMainLooper()).postDelayed(new Runnable()
        {
      @Override public void run() {

         }}, 2000);*/
         if(Globals.checkInternet(Notifications.this))
             callApi();
         else
             binding.swipeContainer.setRefreshing(false);
            }
        });
    }


    @Override
    public void onClick(View v) {
    finish();
      }

    @Override
    public void onRefresh() {
        if(Globals.checkInternet(Notifications.this)) {
            binding.loader.setVisibility(View.VISIBLE);
            callApi();
        }
    }
}