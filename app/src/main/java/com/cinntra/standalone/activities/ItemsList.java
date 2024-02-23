package com.cinntra.standalone.activities;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.standalone.R;
import com.cinntra.standalone.adapters.ItemsAdapter;
import com.cinntra.standalone.databinding.ActivityItemBinding;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.globals.MainBaseActivity;
import com.cinntra.standalone.model.DocumentLines;
import com.cinntra.standalone.model.ItemCategoryData;
import com.cinntra.standalone.viewModel.ItemViewModel;

import java.util.ArrayList;
import java.util.List;




public class ItemsList extends MainBaseActivity implements View.OnClickListener {

//     @BindView(R.id.head_title)
//    TextView head_title;
//     @BindView(R.id.back_press)
//     RelativeLayout back_press;
//     @BindView(R.id.itemsRecycler)
//     RecyclerView itemsRecycler;
//     @BindView(R.id.loader)
//     ProgressBar loader;
//     @BindView(R.id.no_datafound)
//     ImageView no_datafound;
//    @BindView(R.id.search)
//    RelativeLayout search;
//    @BindView(R.id.searchLay)
//    RelativeLayout searchLay;
//    @BindView(R.id.mainHeaderLay)
//    RelativeLayout mainHeaderLay;
//    @BindView(R.id.searchView)
//    SearchView searchView;
//    @BindView(R.id.filterView)
//    RelativeLayout filterView;
//    @BindView(R.id.new_quatos)
//    RelativeLayout new_quatos;
//    @BindView(R.id.nestedSV)
//    NestedScrollView nestedSV;
//    @BindView(R.id.idPBLoading)
//    ProgressBar idPBLoading;
     LinearLayoutManager layoutManager;
     int skipSize = 20;

     List<DocumentLines> AllitemsList;

    ItemsAdapter adapter;
    //private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean recallApi = true;
    int currentPage = 0;
    int pageno = 1;

    ActivityItemBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       // ButterKnife.bind(this);
        binding.quotesHeader.filterView.setVisibility(View.GONE);
        binding.quotesHeader.newQuatos.setVisibility(View.GONE);
        AllitemsList = new ArrayList<>();
        setDefaults();

        eventSearchManager();

        layoutManager = new LinearLayoutManager(ItemsList.this, RecyclerView.VERTICAL, false);
        binding.itemsRecycler.setLayoutManager(layoutManager);
        adapter = new ItemsAdapter(ItemsList.this, AllitemsList);
        binding.itemsRecycler.setAdapter(adapter);

        binding.nestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.

                    if (Globals.checkInternet(ItemsList.this)&&recallApi) {

                        binding.idPBLoading.setVisibility(View.VISIBLE);
                        binding.loader.loader.setVisibility(View.VISIBLE);
                        pageno++;
                        AllitemsList.clear();
                        callApi(binding.loader.loader,fromwhere);

                    }


                }

            }
        });



    }

    @Override
    public void onBackPressed()
    {

        if(binding.quotesHeader.mainHeaderLay.getVisibility()==View.GONE)
        {
            binding.quotesHeader.searchLay.setVisibility(View.GONE);
            binding.quotesHeader.mainHeaderLay.setVisibility(View.VISIBLE);
        }
        else {
            super.onBackPressed();
        }
    }

    private void eventSearchManager()
    {
        binding.quotesHeader.searchView.setBackgroundColor(Color.parseColor("#00000000"));
        binding.quotesHeader.searchLay.setVisibility(View.VISIBLE);
        binding.quotesHeader.searchView.setVisibility(View.VISIBLE);

        binding.quotesHeader.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query) {
                binding.quotesHeader.searchView.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText)
            {
                if(adapter!=null){
                    adapter.filter(newText);
                }
                return true;
            }
        });

    }
    String fromwhere;
    private void setDefaults()
       {
    binding.quotesHeader.headTitle.setText(getResources().getString(R.string.items));
    binding.quotesHeader.backPress.setOnClickListener(this);
           binding.quotesHeader.search.setOnClickListener(this);
           fromwhere= String.valueOf(getIntent().getExtras().getInt("CategoryID"));
           // Toast.makeText(this,fromwhere,Toast.LENGTH_SHORT).show();
           if (Globals.checkInternet(getApplicationContext())) {
               pageno = 1;
               recallApi = true;
               AllitemsList.clear();
               callApi(binding.loader.loader, fromwhere);
           }
       }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
    case R.id.back_press:
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
        break;
        case R.id.search:
            binding.quotesHeader.mainHeaderLay.setVisibility(View.GONE);
            binding.quotesHeader.searchLay.setVisibility(View.VISIBLE);

            binding.quotesHeader.searchView.setIconifiedByDefault(true);
            binding.quotesHeader.searchView.setFocusable(true);
            binding.quotesHeader.searchView.setIconified(false);
            binding.quotesHeader.searchView.requestFocusFromTouch();
            break;

          }
       }
    private void callApi(ProgressBar loader, String fromwhere)
    {
        ItemCategoryData id = new ItemCategoryData();
        id.setMaxItem(50);
        id.setPageNo(1);
      //  id.setCatID(fromwhere);
        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getItemsList(loader,id).observe(this, new Observer<List<DocumentLines>>() {
            @Override
            public void onChanged(@Nullable List<DocumentLines> itemsList) {

                if(itemsList == null || itemsList.size() == 0){
                    Globals.setmessage(getApplicationContext());
                    binding.noDatafound.setVisibility(View.VISIBLE);
                }else {
                    binding.noDatafound.setVisibility(View.GONE);
                    recallApi = itemsList.size()>=50;
                    AllitemsList.addAll(itemsList);
                    adapter.notifyDataSetChanged();

                }
                binding.idPBLoading.setVisibility(View.GONE);
            }
        });

    }



}