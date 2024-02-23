package com.cinntra.standalone.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.cinntra.standalone.R;
import com.cinntra.standalone.adapters.InvoicePagerAdapter;
import com.cinntra.standalone.databinding.ActivityInvoiceBinding;
import com.cinntra.standalone.fragments.Invoices_Override_Fragment;
import com.cinntra.standalone.globals.MainBaseActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;




public class InvoiceActivity extends MainBaseActivity implements View.OnClickListener {
//    @BindView(R.id.new_quatos)
//    RelativeLayout new_quatos;
//    @BindView(R.id.back_press)
//    RelativeLayout back_press;
//    @BindView(R.id.head_title)
//    TextView head_title;
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

    ActivityInvoiceBinding binding;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     binding=ActivityInvoiceBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());
  //   ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
     setDefaults();

        }
    private String []tabs = {"Overdue","All"};
    private ArrayList<Fragment> fragments =new ArrayList<Fragment>();
    private void setDefaults() {
        binding.headerBottomRoundedWithSearchFilter.headTitle.setText(getString(R.string.invoice));
        fragments.add(new Invoices_Override_Fragment());
     //  fragments.add(new Invoices_All_Fragment());
        binding.headerBottomRoundedWithSearchFilter.newQuatos.setVisibility(View.GONE);
        binding.headerBottomRoundedWithSearchFilter.backPress.setOnClickListener(this);
        binding.headerBottomRoundedWithSearchFilter.search.setOnClickListener(this);

        InvoicePagerAdapter pagerAdapter = new InvoicePagerAdapter(getSupportFragmentManager(),fragments,tabs);
        binding.viewpager.setAdapter(pagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewpager);
    }


    @Override
    public void onClick(View v) {
     switch (v.getId())
          {
     case R.id.back_press :
          finish();
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
    public void onBackPressed()
    {
        if(binding.headerBottomRoundedWithSearchFilter.newQuatos!=null) {
            binding.headerBottomRoundedWithSearchFilter.newQuatos.setClickable(true);
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