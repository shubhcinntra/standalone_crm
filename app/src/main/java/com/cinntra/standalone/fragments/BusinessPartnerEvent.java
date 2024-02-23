package com.cinntra.standalone.fragments;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.cinntra.standalone.R;
import com.cinntra.standalone.adapters.BPinnerPagerAdapter;
import com.cinntra.standalone.calender.DatePickerListener;
import com.cinntra.standalone.calender.HorizontalPicker;
import com.cinntra.standalone.databinding.ActivityfragmentNotificationsBinding;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.model.EventValue;
import com.google.android.material.tabs.TabLayout;

import org.joda.time.DateTime;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class BusinessPartnerEvent extends Fragment implements DatePickerListener, View.OnClickListener {
    public static int currentItem = 0;

//    @BindView(R.id.viewpager)
//    ViewPager viewpager;
//    @BindView(R.id.tab_layout)
//    TabLayout tableLayout;
//    @BindView(R.id.add_new)
//    LinearLayout add_new;
//
//    @BindView(R.id.head_title)
//    TextView head_title;
//    @BindView(R.id.back_press)
//    RelativeLayout back_press;
//    @BindView(R.id.datePicker)
//    HorizontalPicker picker;
//    @BindView(R.id.frame)
//    FrameLayout frame;


    ArrayList<EventValue> alleventlist = new ArrayList<>();

    @Override
    public void onResume() {
        super.onResume();


    }

    public BusinessPartnerEvent() {
        // Required empty public constructor
    }

    Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;

        super.onAttach(context);

    }


    ActivityfragmentNotificationsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        binding = ActivityfragmentNotificationsBinding.inflate(getLayoutInflater());
        View v = inflater.inflate(R.layout.activityfragment_notifications, container, false);
        //ButterKnife.bind(this,v);

        binding.headerFrame.mainContainer.setVisibility(View.GONE);
        binding.headerFrame.headTitle.setText(getString(R.string.activity));
        setDefaults();
        setcalender();

        return binding.getRoot();

    }


    ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    public static String tabs[] = {"Events", "Tasks"};

    private void setDefaults() {
        fragmentList.clear();
        fragmentList.add(new BPEvent_Fragment());
        fragmentList.add(new BPTasks_Fragment());
        //  fragmentList.add(new Log_Fragment());
        BPinnerPagerAdapter pagerAdapter;
        pagerAdapter = new BPinnerPagerAdapter(getParentFragment().getParentFragmentManager(), fragmentList, tabs);
        binding.viewpager.setAdapter(pagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewpager);
        binding.addNew.setOnClickListener(this);
        binding.headerFrame.backPress.setOnClickListener(this);
        binding.addNew.setVisibility(View.VISIBLE);
        // pagerAdapter.notifyDataSetChanged();
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentItem = tab.getPosition();
               /* if(currentItem==0)
                    add_new.setVisibility(View.GONE);
                else
                    add_new.setVisibility(View.VISIBLE);*/


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void setcalender() {

        // initialize it and attach a listener
        binding.datePicker.setListener(this).setDays(2000).setOffset(500)
                .init();
        binding.datePicker.setDate(new DateTime());
    }


    @Override
    public void onDateSelected(DateTime dateSelected) {

        int mn = dateSelected.getMonthOfYear();
        DecimalFormat formatter = new DecimalFormat("00");
        String month = formatter.format(mn);

        int day = dateSelected.getDayOfMonth();
        DecimalFormat formatter1 = new DecimalFormat("00");
        String dd = formatter1.format(day);

        Globals.CurrentSelectedDate = dateSelected.getYear() + "-" + month + "-" + dd;
        Log.e("Date=>C", Globals.CurrentSelectedDate);
        binding.viewpager.getAdapter().notifyDataSetChanged();
    }


    private void showTaskDialog() {

        FragmentManager fm = getFragmentManager();
        BPAddTaskDialogue editNameDialogFragment = BPAddTaskDialogue.newInstance("Some Title");

        editNameDialogFragment.show(fm, "");
    }

    private void showEventDialog() {

        FragmentManager fm = getFragmentManager();
        BPAddEventDialogue editNameDialogFragment = BPAddEventDialogue.newInstance("Some Title");

        editNameDialogFragment.show(fm, "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_new:
                if (currentItem == 0)
                    showEventDialog();
                else if (currentItem == 1)
                    showTaskDialog();
                else if (currentItem == 2)
                    showLogDialog();
                break;
            case R.id.back_press:
                getActivity().onBackPressed();
                break;
        }
    }

    private void showLogDialog() {
        FragmentManager fm = getChildFragmentManager();
        AddLogDialogue editNameDialogFragment = AddLogDialogue.newInstance("Some Title");
        editNameDialogFragment.show(fm, "");
    }
}