package com.cinntra.standalone.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baoyz.widget.PullRefreshLayout;
import com.cinntra.standalone.R;
import com.cinntra.standalone.adapters.ExpenseAdapter;
import com.cinntra.standalone.adapters.PaymentDetailsAdapter;
import com.cinntra.standalone.databinding.FragmentCampaignBinding;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.globals.MainBaseActivity;
import com.cinntra.standalone.model.ExpenseResponse;
import com.cinntra.standalone.model.PaymentDetailsModel;
import com.cinntra.standalone.model.PaymentRespnse;
import com.cinntra.standalone.model.QuotationResponse;
import com.cinntra.standalone.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentDetails extends MainBaseActivity {


    //    @BindView(R.id.customer_lead_List)
//    RecyclerView recyclerView;
//    @BindView(R.id.loader)
//    ProgressBar loader;
//    @BindView(R.id.swipeRefreshLayout)
//    PullRefreshLayout swipeRefreshLayout;
//    @BindView(R.id.no_datafound)
//    ImageView no_datafound;
    FragmentCampaignBinding binding;


    @Override
    protected void onResume() {
        super.onResume();
        if (Globals.checkInternet(this)) {
            callpaymentapi();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentCampaignBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //   ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24);


        binding.swipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (Globals.checkInternet(PaymentDetails.this)) {
                    callpaymentapi();
                } else
                    binding.swipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    PaymentDetailsAdapter expenseAdapter;
    List<PaymentDetailsModel> edp = new ArrayList<>();

    private void callpaymentapi() {

        Call<PaymentRespnse> call = NewApiClient.getInstance().getApiService().getAllpaymentDetails();
        call.enqueue(new Callback<PaymentRespnse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<PaymentRespnse> call, Response<PaymentRespnse> response) {
                if (response.code() == 200) {
                    edp.clear();
                    edp.addAll(response.body().getData());
                    expenseAdapter = new PaymentDetailsAdapter(PaymentDetails.this, edp);
                    binding.customerLeadList.setLayoutManager(new LinearLayoutManager(PaymentDetails.this, RecyclerView.VERTICAL, false));
                    binding.customerLeadList.setAdapter(expenseAdapter);
                    expenseAdapter.notifyDataSetChanged();
                } else {

                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                        Toast.makeText(PaymentDetails.this, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
                nodatafoundimage();
                binding.swipeRefreshLayout.setRefreshing(false);
                binding.loader.loader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<PaymentRespnse> call, Throwable t) {
               binding.swipeRefreshLayout.setRefreshing(false);
                binding.loader.loader.setVisibility(View.GONE);
                Toast.makeText(PaymentDetails.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void nodatafoundimage() {
        if (expenseAdapter.getItemCount() == 0) {
            binding.noDatafound.setVisibility(View.VISIBLE);
        } else {
            binding.noDatafound.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.campaign_filter, menu);

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = new SearchView((this).getSupportActionBar().getThemedContext());

        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setActionView(searchView);
        searchView.setQueryHint("Search Expense");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                return true;
            }
        });


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:

                break;
            case R.id.plus:
                startActivity(new Intent(this, AddPaymentDetails.class));
                break;


            case android.R.id.home:
                this.finish();
                return true;
        }
        return true;
    }
}
