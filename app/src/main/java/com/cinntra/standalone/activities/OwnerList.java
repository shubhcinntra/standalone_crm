package com.cinntra.standalone.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.standalone.R;
import com.cinntra.standalone.adapters.OwnerAdapter;
import com.cinntra.standalone.databinding.ActivityItemBinding;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.globals.MainBaseActivity;
import com.cinntra.standalone.model.OwnerItem;
import com.cinntra.standalone.viewModel.ItemViewModel;

import java.util.List;




public class OwnerList extends MainBaseActivity implements View.OnClickListener {

//     @BindView(R.id.head_title)
//     TextView head_title;
//     @BindView(R.id.back_press)
//     RelativeLayout back_press;
//     @BindView(R.id.itemsRecycler)
//     RecyclerView itemsRecycler;
//     @BindView(R.id.loader)
//     ProgressBar loader;
     ActivityItemBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       // ButterKnife.bind(this);
        setDefaults();
        if(Globals.checkInternet(getApplicationContext()))
            callApi(binding.loader.loader);
    }

    private void setDefaults()
       {
    binding.quotesHeader.headTitle.setText(getResources().getString(R.string.owner_list));
           binding.quotesHeader.backPress.setOnClickListener(this);
       }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
    case R.id.back_press:
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
        break;

          }
       }
    private void callApi(ProgressBar loader)
          {
//     ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
//     model.getEmployeesList(loader).observe(this, new Observer<List<OwnerItem>>() {
//      @Override
//      public void onChanged(@Nullable List<OwnerItem> itemsList) {
//          if(itemsList == null || itemsList.size() == 0){
//              Globals.setmessage(getApplicationContext());
//          }else{
//              Globals.OwnerList.clear();
//              Globals.OwnerList.addAll(itemsList);
//              OwnerAdapter adapter = new OwnerAdapter(OwnerList.this, itemsList);
//              itemsRecycler.setLayoutManager(new LinearLayoutManager(OwnerList.this, RecyclerView.VERTICAL,false));
//              itemsRecycler.setAdapter(adapter);
//          }
//            }
//        });

              if(MainActivity.ownerListFromLocal == null || MainActivity.ownerListFromLocal.size() == 0){
                  Globals.setmessage(getApplicationContext());
              }else{
                  Globals.OwnerList.clear();
                  Globals.OwnerList.addAll(MainActivity.ownerListFromLocal);
                  OwnerAdapter adapter = new OwnerAdapter(OwnerList.this, MainActivity.ownerListFromLocal);
                  binding.itemsRecycler.setLayoutManager(new LinearLayoutManager(OwnerList.this, RecyclerView.VERTICAL,false));
                 binding. itemsRecycler.setAdapter(adapter);
              }


    }



}