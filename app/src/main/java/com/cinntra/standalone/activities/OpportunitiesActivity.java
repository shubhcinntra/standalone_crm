package com.cinntra.standalone.activities;


import android.app.Dialog;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.cinntra.standalone.R;
import com.cinntra.standalone.adapters.FilterAdapter;
import com.cinntra.standalone.adapters.InvoicePagerAdapter;
import com.cinntra.standalone.fragments.Open_Opprtunity_Fragment;
import com.cinntra.standalone.globals.MainBaseActivity;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;


public class OpportunitiesActivity extends MainBaseActivity implements View.OnClickListener {
//    @BindView(R.id.new_quatos)
//     RelativeLayout new_quatos;
//    @BindView(R.id.head_title)
//    TextView head_title;
//    @BindView(R.id.back_press)
//    RelativeLayout back_press;
//    @BindView(R.id.viewpager)
//    ViewPager viewpager;
//    @BindView(R.id.tab_layout)
//    TabLayout tabLayout;
//    @BindView(R.id.searchLay)
//    RelativeLayout searchLay;
//    @BindView(R.id.mainHeaderLay)
//    RelativeLayout mainHeaderLay;
//    @BindView(R.id.search)
//    RelativeLayout search;
//    @BindView(R.id.searchView)
//    SearchView searchView;
//    @BindView(R.id.filter)
//    ImageView filter;
//    @BindView(R.id.headerLay)
//    LinearLayout headerLay;
    InvoicePagerAdapter pagerAdapter;
    ImageView close;
    TextView clear_all;
    RecyclerView recycler_view;
    FilterAdapter adapter;
    LinearLayoutManager layoutManager;
    Dialog dialog;
    @Override
    protected void onResume()
     {
        super.onResume();
//
//        if(new_quatos!=null)
//           new_quatos.setClickable(true);
//        if(pagerAdapter!=null)
//            pagerAdapter.notifyDataSetChanged();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState)
     {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.opportunity_pipeline);
    // ButterKnife.bind(this);



         ActionBar actionBar = getSupportActionBar();
         actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24);
         actionBar.setDisplayHomeAsUpEnabled(true);






         /*getActionBar().setDisplayHomeAsUpEnabled(true);
         getActionBar().setHomeButtonEnabled(true);*/

//     new_quatos.setOnClickListener(this);
//     back_press.setOnClickListener(this);
//     search.setOnClickListener(this);
//     filter.setOnClickListener(this);

   //  setDefaults();

        }
    //private String []tabs = {"Open","Won","Lost","Pipeline"};
    private String []tabs = {"Open","Won","Lost"};
    private ArrayList<Fragment> fragments =new ArrayList<Fragment>();

//    private void setDefaults()
//          {
//      /*   getActionBar().setTitle((getString(R.string.opportunities)));
//         getActionBar().setDisplayHomeAsUpEnabled(true);*/
//
//        head_title.setText(getString(R.string.opportunities));
//
//              Open_Opprtunity_Fragment fragment2 = new Open_Opprtunity_Fragment();
//              FragmentManager fragmentManager = getSupportFragmentManager();
//              FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//              fragmentTransaction.add(R.id.open_opp_container, fragment2);
//              fragmentTransaction.commit();
//
//
//        /*fragments.add(new Open_Opprtunity_Fragment());
//        fragments.add(new Won_Opp_Fragment());
//        fragments.add(new Lost_Opp_Fragment());*/
//
//
//      // fragments.add(new Opportunity_pipeline());
//
//
//        pagerAdapter = new InvoicePagerAdapter(getSupportFragmentManager(),fragments,tabs);
//        viewpager.setAdapter(pagerAdapter);
//        tabLayout.setupWithViewPager(viewpager);
//    }


    @Override
    public void onClick(View v) {
     switch (v.getId()){

     }
//      {
//     case R.id.back_press :
//        finish();
//          break;
//     case R.id.new_quatos:
//         startActivity(new Intent(this, AddOpportunityActivity.class));
//         break;
//     case R.id.search:
//        mainHeaderLay.setVisibility(View.GONE);
//        headerLay.setVisibility(View.GONE);
//        /*searchLay.setVisibility(View.VISIBLE);
//
//         searchView.setIconifiedByDefault(true);
//         searchView.setFocusable(true);
//         searchView.setIconified(false);
//         searchView.requestFocusFromTouch();*/
//         break;
//          case R.id.filter:
//
//              dialog = new Dialog(this);
//              LayoutInflater layoutInflater = LayoutInflater.from(this);
//              View custom_dialog =layoutInflater.inflate(R.layout.opportunity_filter,null);
//              dialog.setContentView(custom_dialog);
//              dialog.getWindow().setLayout(900,
//                      WindowManager.LayoutParams.WRAP_CONTENT);
//              dialog.getWindow().setBackgroundDrawable(new
//                      ColorDrawable(Color.TRANSPARENT));
//              close = custom_dialog.findViewById(R.id.close);
//              recycler_view = custom_dialog.findViewById(R.id.recycler_view);
//
//              layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//              adapter = new FilterAdapter(this,dialog);
//              recycler_view.setLayoutManager(layoutManager);
//              recycler_view.setAdapter(adapter);
//
//              dialog.show();
//
//          close.setOnClickListener(new View.OnClickListener()
//              {
//                  @Override
//                  public void onClick(View v) {
//                      dialog.cancel();
//                  }
//              });
//
//          tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
//               {
//                  @Override
//                  public void onTabSelected(TabLayout.Tab tab) {
//                  }
//
//                  @Override
//                  public void onTabUnselected(TabLayout.Tab tab) {
//
//                  }
//
//                  @Override
//                  public void onTabReselected(TabLayout.Tab tab) {
//
//                  }
//              });
//         }
    }





//    @Override
//    public void onBackPressed() {
//      if(new_quatos!=null) {
//      new_quatos.setClickable(true);
//
//      getSupportActionBar().show();
//      if(mainHeaderLay.getVisibility()==View.GONE)
//          {
//      searchLay.setVisibility(View.GONE);
//      mainHeaderLay.setVisibility(View.VISIBLE);
//          }
//       else {
//        super.onBackPressed();
//         }
//        }
//
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
           {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}