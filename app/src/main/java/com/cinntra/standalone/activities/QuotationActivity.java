package com.cinntra.standalone.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.cinntra.standalone.R;
import com.cinntra.standalone.adapters.InvoicePagerAdapter;
import com.cinntra.standalone.databinding.FragmentQuotesBinding;
import com.cinntra.standalone.fragments.Quotation_Open_Fragment;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.globals.MainBaseActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;




public class QuotationActivity extends MainBaseActivity implements View.OnClickListener {
//    @BindView(R.id.new_quatos)
//    RelativeLayout new_quatos;
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
//    @BindView(R.id.filterView)
//    RelativeLayout filterview;

    FragmentQuotesBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     binding=FragmentQuotesBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());
   //  ButterKnife.bind(this);



        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setDefaults();
        }

    //private String []tabs ={"Open","All","Approval Status"};
    private String []tabs = {"Open","All"};
    private ArrayList<Fragment> fragments =new ArrayList<Fragment>();


    private void setDefaults() {

        Globals.SelectedItems.clear();
        binding.headerBottomRoundedWithSearchFilter.headTitle.setText(getString(R.string.quotation)+"s");
        fragments.add(new Quotation_Open_Fragment());
       // fragments.add(new Quotation_All_Fragment());
        //fragments.add(new All_Order());

        binding.headerBottomRoundedWithSearchFilter.newQuatos.setOnClickListener(this);
        binding.headerBottomRoundedWithSearchFilter.backPress.setOnClickListener(this);
        binding.headerBottomRoundedWithSearchFilter.search.setOnClickListener(this);
        binding.headerBottomRoundedWithSearchFilter.filterView.setOnClickListener(this);

        InvoicePagerAdapter pagerAdapter = new InvoicePagerAdapter(getSupportFragmentManager(),fragments,tabs);
        binding.viewpager.setAdapter(pagerAdapter);
       binding.tabLayout.setupWithViewPager( binding.viewpager);

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {

        }
        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }
        @Override
        public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }


    private void onOption() {
        PopupMenu popupMenu = new PopupMenu(QuotationActivity.this,binding.headerBottomRoundedWithSearchFilter.filter);
        popupMenu.getMenuInflater().inflate(R.menu.quotation_filter,popupMenu.getMenu());
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
           /* switch (item.getItemId()){
                case R.id.all:
                    item.isChecked();
                    break;
                case R.id.my:
                    break;
                case R.id.my_team:
                    break;
                case R.id.newest:
                    break;
                case R.id.oldest:
                    break;
            }*/
                return true;
            }
        });

    }


    @Override
    public void onClick(View v) {
     switch (v.getId())
          {
     case R.id.back_press :
          finish();
          break;
              case R.id.filterView:
                  onOption();
                  break;

     case R.id.new_quatos:
       /*  Globals.SelectedItems.clear();
         Toast.makeText(this,"Activity",Toast.LENGTH_SHORT).show();
         Intent i = new Intent(this, AddQuotationAct.class);
         if(!Prefs.getString(Globals.QuotationListing,"").equalsIgnoreCase("null")) {
             Bundle b = new Bundle();
             b.putSerializable(Globals.OpportunityQuotation, oppdata);
             i.putExtras(b);

         }
         startActivity(i);*/
         /*New_Quotation fragment = new New_Quotation();
         FragmentManager fm     = getSupportFragmentManager();
         FragmentTransaction transaction  = fm.beginTransaction();
         transaction.replace(R.id.quatoes_main_container, fragment);
         transaction.addToBackStack(null);
         transaction.commit();*/

           break;
     case R.id.search:
         binding.headerBottomRoundedWithSearchFilter.mainHeaderLay.setVisibility(View.GONE);
          binding.headerBottomRoundedWithSearchFilter.searchLay.setVisibility(View.VISIBLE);

         binding.headerBottomRoundedWithSearchFilter.searchView.setIconifiedByDefault(true);
         binding.headerBottomRoundedWithSearchFilter.searchView.setFocusable(true);
         binding.headerBottomRoundedWithSearchFilter.searchView.setIconified(false);
         binding.headerBottomRoundedWithSearchFilter.searchView.requestFocusFromTouch();
            break;
         }
    }


    @Override
    public void onBackPressed() {
      getSupportActionBar().show();
      if(binding.headerBottomRoundedWithSearchFilter.mainHeaderLay.getVisibility()==View.GONE)
            {
                binding.headerBottomRoundedWithSearchFilter.searchLay.setVisibility(View.GONE);
                binding.headerBottomRoundedWithSearchFilter.mainHeaderLay.setVisibility(View.VISIBLE);
            }
            else {
        super.onBackPressed();
            }
        }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}