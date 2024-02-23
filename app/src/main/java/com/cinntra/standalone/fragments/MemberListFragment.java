package com.cinntra.standalone.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.standalone.R;
import com.cinntra.standalone.activities.AddOpportunityActivity;
import com.cinntra.standalone.activities.CampaignActivity;
import com.cinntra.standalone.activities.OpportunitiesActivity;
import com.cinntra.standalone.adapters.CampaignListAdapter;
import com.cinntra.standalone.databinding.MemberListingBinding;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.model.CampaignListModel;
import com.cinntra.standalone.model.CampaignListResponse;
import com.cinntra.standalone.model.CampaignModel;
import com.cinntra.standalone.model.CampaignResponse;
import com.cinntra.standalone.newapimodel.LeadResponse;
import com.cinntra.standalone.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Objects;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberListFragment extends Fragment implements View.OnClickListener {


//    @BindView(R.id.listing)
//    RecyclerView listing;
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
//    @BindView(R.id.head_title)
//    TextView head_title;
//    @BindView(R.id.back_press)
//    RelativeLayout back_press;



    CampaignModel campaignData;
    MemberListingBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
//        setHasOptionsMenu(true);

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b      = getArguments();
            campaignData =(CampaignModel) b.getSerializable(Globals.CampaignData);

        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=MemberListingBinding.inflate(inflater,container,false);

        View v=inflater.inflate(R.layout.member_listing, container, false);
       // ButterKnife.bind(this,v);
        eventSearchManager();


        if(Globals.checkInternet(getContext()))
        {

            callApi(campaignData.getId());
        }


        return binding.getRoot();
    }


    private void eventSearchManager()
    {
        binding.quotesHeader.headTitle.setText("Campaign");
        binding.quotesHeader.backPress.setOnClickListener(this);
        binding.quotesHeader.search.setOnClickListener(this);
        binding.quotesHeader.filterView.setVisibility(View.GONE);
        binding.quotesHeader.newQuatos.setVisibility(View.GONE);
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

                return true;
            }
        });

    }


    private void callApi(Integer id) {

        CampaignListResponse cm = new CampaignListResponse();
        cm.setCampaignSetId(id);

        Call<CampaignListModel> call = NewApiClient.getInstance().getApiService().getmemberlist(cm);
        call.enqueue(new Callback<CampaignListModel>() {
            @Override
            public void onResponse(Call<CampaignListModel> call, Response<CampaignListModel> response) {

                if(response.code()==200)
                {
                    binding.listing.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                    binding.listing.setAdapter(new CampaignListAdapter(getContext(),response.body().getData()));
                }
                else
                {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    LeadResponse mError = new LeadResponse();
                    try {
                        String s =response.errorBody().string();
                        mError= gson.fromJson(s, LeadResponse.class);
                        Toast.makeText(getContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                }

            }
            @Override
            public void onFailure(Call<CampaignListModel> call, Throwable t) {

                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.search:
                binding.headerLay.setVisibility(View.GONE);
                binding.quotesHeader.searchLay.setVisibility(View.VISIBLE);

                binding.quotesHeader.searchView.setIconifiedByDefault(true);
                binding.quotesHeader. searchView.setFocusable(true);
                binding.quotesHeader.searchView.setIconified(false);
                binding.quotesHeader.searchView.requestFocusFromTouch();
                break;
            case R.id.back_press:
                requireActivity().onBackPressed();
                break;


        }

    }


}
