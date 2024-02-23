package com.cinntra.standalone.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.cinntra.standalone.R;
import com.cinntra.standalone.activities.InvoiceActivity;
import com.cinntra.standalone.activities.Opportunities_Pipeline_Activity;
import com.cinntra.standalone.activities.OrderActivity;
import com.cinntra.standalone.activities.QuotationActivity;

import com.cinntra.standalone.databinding.CustomersdetailBinding;
import com.cinntra.standalone.fragments.BusinessPartnerBranch;
import com.cinntra.standalone.fragments.BusinessPartnerContact;

import com.cinntra.standalone.fragments.Update_BussinessPartner_Fragment;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.interfaces.DatabaseClick;
import com.cinntra.standalone.model.BusinessPartnerData;
import com.google.android.material.tabs.TabLayout;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;



public class BusinessPartnerDetail extends Fragment implements View.OnClickListener, DatabaseClick {

//    @BindView(R.id.head_title)
//    TextView head_title;
//    @BindView(R.id.back_press)
//    RelativeLayout back_press;
//    @BindView(R.id.viewpager)
//    ViewPager viewpager;
//    @BindView(R.id.tab_layout)
//    TabLayout tabLayout;
//    @BindView(R.id.option)
//    LinearLayout option;
    BusinessPartnerData customerItem;

    CustomersdetailBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            Bundle     b = getArguments();
            customerItem = (BusinessPartnerData) b.getSerializable(Globals.BussinessItemData);

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=CustomersdetailBinding.inflate(inflater,container,false);
        View v=inflater.inflate(R.layout.customersdetail, container, false);
       // ButterKnife.bind(this,v);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        setDefaults();
        return binding.getRoot();


    }



    private String []tabs = {"General","Contact","Branch"};
    private ArrayList<Fragment> fragments =new ArrayList<Fragment>();
    private void setDefaults()
    {
        Prefs.putString(Globals.SelectAddress,"NoUpdate");
        binding.headerBottomRounded.headTitle.setText(getResources().getString(R.string.customer));
        fragments.add(new Update_BussinessPartner_Fragment(BusinessPartnerDetail.this,customerItem));
//        fragments.add(new BusinessPartnerEvent());
        fragments.add(new BusinessPartnerContact(customerItem));
        fragments.add(new BusinessPartnerBranch(BusinessPartnerDetail.this,customerItem));
        BusinessPagerAdapter pagerAdapter = new BusinessPagerAdapter(getChildFragmentManager(),fragments,tabs);
        binding.viewpager.setAdapter(pagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewpager);
        binding.headerBottomRounded.backPress.setOnClickListener(this);
        binding.option.setOnClickListener(this);

    }


    @Override
    public void onDetach() {
        super.onDetach();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_press:
                Globals.branchData.clear();
                requireActivity().onBackPressed();
                break;
            case R.id.option:
                openPopup();
                break;
        }
    }

    private void openPopup() {
        PopupMenu popup = new PopupMenu(getContext(),binding.option);
        popup.getMenuInflater().inflate(R.menu.businesspartnermenu,popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.opportunity:
                    startActivity(new Intent(getActivity(), Opportunities_Pipeline_Activity.class));
                    break;
                case R.id.quotation:
                    startActivity(new Intent(getActivity(), QuotationActivity.class));
                    break;
                case R.id.order:
                    startActivity(new Intent(getActivity(), OrderActivity.class));
                    break;
                case R.id.invoice:
                    startActivity(new Intent(getActivity(), InvoiceActivity.class));
                    break;

            }
            return true ;
        });
        popup.show();


    }

    @Override
    public void onClick(int po)
    {
        binding.viewpager.setCurrentItem(po);
    }
}
