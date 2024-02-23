package com.cinntra.standalone.fragments;


import static com.sap.db.util.Dbg.print;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baoyz.widget.PullRefreshLayout;
import com.cinntra.standalone.R;
import com.cinntra.standalone.activities.AddOpportunityActivity;
import com.cinntra.standalone.activities.LeadsActivity;
import com.cinntra.standalone.activities.MainActivity;
import com.cinntra.standalone.activities.Opportunities_Pipeline_Activity;
import com.cinntra.standalone.adapters.BPTypeFilterAdapter;
import com.cinntra.standalone.adapters.BPTypeSpinnerAdapter;
import com.cinntra.standalone.adapters.OpenOpportunities_Adapter;
import com.cinntra.standalone.adapters.SourceAdpater;
import com.cinntra.standalone.databinding.FragmentOpenOppBinding;
import android.view.View;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.interfaces.ChangeTeam;
import com.cinntra.standalone.interfaces.CommentStage;
import com.cinntra.standalone.interfaces.FragmentRefresher;
import com.cinntra.standalone.model.LeadTypeData;
import com.cinntra.standalone.model.LeadTypeResponse;
import com.cinntra.standalone.model.UTypeData;
import com.cinntra.standalone.newapimodel.LeadResponse;
import com.cinntra.standalone.newapimodel.NewOpportunityRespose;
import com.cinntra.standalone.viewModel.ItemViewModel;
import com.cinntra.standalone.viewModel.Opportunities_ViewModel;
import com.cinntra.standalone.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Open_Opprtunity_Fragment extends Fragment implements FragmentRefresher, CommentStage {

//    @BindView(R.id.recyclerview)
//    RecyclerView recyclerview;
//    @BindView(R.id.loader)
//    ProgressBar loader;
//    @BindView(R.id.swipeRefreshLayout)
//    PullRefreshLayout swipeRefreshLayout;
//    @BindView(R.id.no_datafound)
//    ImageView no_datafound;

    Context mContext;

    LinearLayoutManager layoutManager;
    ArrayList<NewOpportunityRespose> AllitemLIst;

    FragmentOpenOppBinding binding;
    public  Open_Opprtunity_Fragment()
      { }


    //TODO: Rename and change types and number of parameters
    public static Open_Opprtunity_Fragment newInstance(String param1, String param2) {
     Open_Opprtunity_Fragment fragment = new Open_Opprtunity_Fragment();
     Bundle args = new Bundle();

     fragment.setArguments(args);
     return fragment;
       }





    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
      {
    // Inflate the layout for this fragment
          binding=FragmentOpenOppBinding.inflate(inflater,container,false);
    View v=inflater.inflate(R.layout.fragment_open_opp, container, false);
 //   ButterKnife.bind(this,v);
    mContext = getActivity();
//          getActivity().getActionBar().setTitle((getString(R.string.opportunities)));
    AllitemLIst = new ArrayList<>();
          Globals.sourcechecklist.clear();
    //eventSearchManager();
          callSourceApi();






    return binding.getRoot();
      }


    @Override
    public void onResume() {
//        String rahu = "google";
//        print(rahu[0]);
        super.onResume();
        if(Globals.checkInternet(getActivity())){
            //  assert binding.loader != null;
          //  assert binding.loader != null;
          //  binding.loader.setVisibility(View.VISIBLE);

            callApi(binding.loader);
        }
    }






    OpenOpportunities_Adapter adapter;

    private void callApi(ProgressBar loader)
      {
   Opportunities_ViewModel model = ViewModelProviders.of(this).get(Opportunities_ViewModel.class);
   model.newOpprtunities(loader).observe(getActivity(), new Observer<List<NewOpportunityRespose>>() {
     @Override
     public void onChanged(@Nullable List<NewOpportunityRespose> itemsList)
      {
            AllitemLIst.clear();
             AllitemLIst.addAll(itemsList);
            binding.swipeRefreshLayout.setRefreshing(false);
             layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
             adapter = new OpenOpportunities_Adapter(Open_Opprtunity_Fragment.this,getContext(),AllitemLIst);
             binding.recyclerview.setLayoutManager(layoutManager);
             binding.recyclerview.setAdapter(adapter);

             adapter.notifyDataSetChanged();
            if(itemsList.size()<=0)
                binding.noDatafound.setVisibility(View.VISIBLE);
            else
                binding.noDatafound.setVisibility(View.GONE);
     }

        });
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
     {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
      {

      //  menu.clear();
        inflater.inflate(R.menu.filteroption_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = new SearchView(((Opportunities_Pipeline_Activity) mContext).getSupportActionBar().getThemedContext());
          searchView.setQueryHint("Search Opportunity");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setActionView(searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if(adapter!=null && newText.trim().length() >  0)
                    adapter.filter(newText.trim());
                return true;
            }
        });


    }






    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {

            case R.id.plus:
                startActivity(new Intent(getContext(), AddOpportunityActivity.class));
                break;
            case R.id.all:
                Globals.sourcechecklist.clear();
                if(adapter!=null)
                    adapter.AllData();

                break;
            case R.id.my:

                break;
            case R.id.my_team:
                if(adapter!=null)
                    adapter.Favfilter("Y");
                break;
            case R.id.opptype:
                showtypedialog();
                break;
            case R.id.customername:

            break;
            case R.id.source:
                showsourcedialog();
            break;


        }
        return true;
    }
    Dialog dialog;
    private void showtypedialog() {
        {
            dialog = new Dialog(getContext());
            // LayoutInflater layoutInflater = context.getLayoutInflater();
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            View custom_dialog =layoutInflater.inflate(R.layout.dialog_recycler,null);
            dialog.setContentView(custom_dialog);
            //dialog.setTitle("");
            dialog.getWindow().setBackgroundDrawable(new
                    ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);

            RecyclerView recyclerview = dialog.findViewById(R.id.dist);
            Button done = dialog.findViewById(R.id.done);
            recyclerview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            recyclerview.setAdapter(new BPTypeFilterAdapter(getContext(),Open_Opprtunity_Fragment.this,utypelist));


           done.setVisibility(View.GONE);

            dialog.show();
        }
    }

    ArrayList<LeadTypeData> sourceData = new ArrayList<>();

    private void callSourceApi() {
//        Call<LeadTypeResponse> call = NewApiClient.getInstance().getApiService().getsourceType();
//        call.enqueue(new Callback<LeadTypeResponse>() {
//            @Override
//            public void onResponse(Call<LeadTypeResponse> call, Response<LeadTypeResponse> response) {
//
//                if(response.code()==200)
//                {
//                    sourceData.clear();
//                    sourceData.addAll(response.body().getData());
//                }
//                else
//                {
//                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
//                    Gson gson = new GsonBuilder().create();
//                    LeadResponse mError = new LeadResponse();
//                    try {
//                        String s =response.errorBody().string();
//                        mError= gson.fromJson(s, LeadResponse.class);
//                        Toast.makeText(getContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
//                    } catch (IOException e) {
//                        //handle failure to read error
//                    }
//                }
//
//            }
//            @Override
//            public void onFailure(Call<LeadTypeResponse> call, Throwable t) {
//
//                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });


        sourceData.clear();
        sourceData.addAll(MainActivity.leadSourceListFromLocal);



        callutypeApi();
    }


        List<UTypeData> utypelist = new ArrayList<>();
        private void callutypeApi() {
            ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
            model.getOPpTypeList().observe(getActivity(), new Observer<List<UTypeData>>() {
                @Override
                public void onChanged(@Nullable List<UTypeData> itemsList) {
                    if(itemsList == null || itemsList.size()== 0){
                        Globals.setmessage( getActivity());
                    }else {
                        utypelist = itemsList;

                    }
                }
            });
        }


    private void showsourcedialog() {
        Dialog dialog = new Dialog(getContext());
        // LayoutInflater layoutInflater = context.getLayoutInflater();
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View custom_dialog =layoutInflater.inflate(R.layout.dialog_recycler,null);
        dialog.setContentView(custom_dialog);
        //dialog.setTitle("");
        dialog.getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);

        RecyclerView recyclerview = dialog.findViewById(R.id.dist);
        Button done = dialog.findViewById(R.id.done);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerview.setAdapter(new SourceAdpater(getContext(),sourceData));


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.sourcefilter(Globals.sourcechecklist);
                if(adapter.getItemCount()==0){
                    binding.noDatafound.setVisibility(View.VISIBLE);
                }else {
                    binding.noDatafound.setVisibility(View.GONE);
                }
                Log.e("list==>", String.valueOf(Globals.sourcechecklist));
                dialog.cancel();
            }
        });

        dialog.show();
    }


    @Override
    public void onRefresh() {
        if(Globals.checkInternet(getActivity())){
         //   assert binding.loader != null;
           // binding.loader.setVisibility(View.VISIBLE);
            callApi(binding.loader);}
    }



    @Override
    public void stagecomment(String id, String name) {
        dialog.cancel();
        if (adapter != null) {
            adapter.Typefilter(id);
            if (adapter.getItemCount() == 0)
                binding.noDatafound.setVisibility(View.VISIBLE);
            else
                binding.noDatafound.setVisibility(View.GONE);
        }
    }
}