package com.cinntra.standalone.fragments;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import com.cinntra.standalone.R;
import com.cinntra.standalone.adapters.OrderPagerAdapter;
import com.cinntra.standalone.calender.DatePickerListener;
import com.cinntra.standalone.calender.HorizontalPicker;
import com.cinntra.standalone.databinding.FragmentNotificationsBinding;
import com.cinntra.standalone.globals.Globals;

import com.cinntra.standalone.model.EventValue;
import com.cinntra.standalone.newapimodel.NewOpportunityRespose;
import com.google.android.material.tabs.TabLayout;

import org.joda.time.DateTime;

import java.text.DecimalFormat;
import java.util.ArrayList;




public class Calender extends Fragment implements DatePickerListener, View.OnClickListener {
    public static  int currentItem =0;

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
    NewOpportunityRespose opportunityItem;

    ArrayList<EventValue> alleventlist = new ArrayList<>();



    @Override
    public void onResume() {
        super.onResume();

        if(pagerAdapter!=null)
            pagerAdapter.notifyDataSetChanged();
    }

    public Calender() {
        // Required empty public constructor
    }
    Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;

        super.onAttach(context);

    }


    FragmentNotificationsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b      = getArguments();
            opportunityItem =(NewOpportunityRespose) b.getParcelable(Globals.OpportunityItem);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        binding=FragmentNotificationsBinding.inflate(inflater,container,false);

        View v=inflater.inflate(R.layout.fragment_notifications, container, false);
        //ButterKnife.bind(this,v);
        Globals.CURRENT_CLASS = getClass().getName();
        setDefaults();
        setcalender();

        return binding.getRoot();

    }



    private  OrderPagerAdapter pagerAdapter;
    ArrayList<Fragment> fragmentList =new  ArrayList<Fragment>();
    private String tabs[] = {"All","Events","Tasks","Follow Up"};
    private void setDefaults() {
        fragmentList.clear();
        fragmentList.add(new All_Event_Task_Fragment());
        fragmentList.add(new Event_Fragment());
        fragmentList.add(new Tasks_Fragment());
        fragmentList.add(new Followup_Fragment());

        pagerAdapter = new OrderPagerAdapter(getChildFragmentManager(),fragmentList,tabs);
        binding.viewpager.setAdapter(pagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewpager);
        binding.addNew.setOnClickListener(this);
       // binding..setOnClickListener(this);
        binding.addNew.setVisibility(View.VISIBLE);

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentItem = tab.getPosition();
                if(currentItem==0 || currentItem==3)
                    binding.addNew.setVisibility(View.GONE);
                else
                    binding.addNew.setVisibility(View.VISIBLE);



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
        binding.datePicker .setListener(this).setDays(2000).setOffset(500)
                .init();
        binding.datePicker.setDate(new DateTime());
    }






    @Override
    public void onDateSelected(DateTime dateSelected) {

        int mn = dateSelected.getMonthOfYear();
        DecimalFormat formatter = new DecimalFormat("00");
        String month = formatter.format(mn);

        int day =dateSelected.getDayOfMonth();
        DecimalFormat formatter1 = new DecimalFormat("00");
        String dd = formatter1.format(day);
      //  picker.setDateSelectedColor(getResources().getColor(R.color.yellow));
        Globals.CurrentSelectedDate = dateSelected.getYear()+"-"+month+"-"+dd;
        Log.e("Date=>C",Globals.CurrentSelectedDate);
      //  callApi();
        binding.viewpager.getAdapter().notifyDataSetChanged();
    }



    private void showTaskDialog() {
        Bundle b = new Bundle();
        b.putParcelable(Globals.OpportunityItem,opportunityItem);
        FragmentManager fm = getChildFragmentManager();
        AddTaskDialogue editNameDialogFragment = AddTaskDialogue.newInstance("Some Title");
        editNameDialogFragment.setArguments(b);
        editNameDialogFragment.show(fm, "");
    }
    private void showEventDialog() {
        Bundle b = new Bundle();
        b.putParcelable(Globals.OpportunityItem,opportunityItem);
        FragmentManager fm = getChildFragmentManager();
        AddEventDialogue editNameDialogFragment = AddEventDialogue.newInstance("Some Title");
        editNameDialogFragment.setArguments(b);
        editNameDialogFragment.show(fm, "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.add_new:
                if(currentItem==1)
                showEventDialog();
                else if (currentItem == 2)
                showTaskDialog();
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