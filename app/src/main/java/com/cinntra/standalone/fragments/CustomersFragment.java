package com.cinntra.standalone.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baoyz.widget.PullRefreshLayout;
import com.cinntra.standalone.R;
import com.cinntra.standalone.activities.AddBPCustomer;
import com.cinntra.standalone.activities.BussinessPartners;
import com.cinntra.standalone.activities.LeadsActivity;
import com.cinntra.standalone.activities.MainActivity;
import com.cinntra.standalone.adapters.BPStateFilterAdapter;
import com.cinntra.standalone.adapters.BPTypeFilterAdapter;
import com.cinntra.standalone.adapters.BPTypeSpinnerAdapter;
import com.cinntra.standalone.adapters.CustomersAdapter;
import com.cinntra.standalone.adapters.CustomersAdapterDetals;
import com.cinntra.standalone.adapters.SourceAdpater;
import com.cinntra.standalone.adapters.StateAdapter;
import com.cinntra.standalone.adapters.StateNewAdapter;
import com.cinntra.standalone.databinding.FragmentCustomerLeadBinding;
import com.cinntra.standalone.globals.Globals;

import com.cinntra.standalone.interfaces.ChangeTeam;
import com.cinntra.standalone.interfaces.CommentStage;
import com.cinntra.standalone.model.BusinessPartnerData;
import com.cinntra.standalone.model.QuotationResponse;
import com.cinntra.standalone.model.StateData;
import com.cinntra.standalone.model.StateRespose;
import com.cinntra.standalone.model.UTypeData;
import com.cinntra.standalone.viewModel.CustomerViewModel;
import com.cinntra.standalone.viewModel.ItemViewModel;
import com.cinntra.standalone.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CustomersFragment extends Fragment implements CommentStage {
    int currentPage = 0;
//    @BindView(R.id.customer_lead_List)
//    RecyclerView customer_lead_List;
//    @BindView(R.id.loader)
//    ProgressBar loader;
//    @BindView(R.id.swipeRefreshLayout)
//    PullRefreshLayout swipeRefreshLayout;
//    @BindView(R.id.no_datafound)
//    ImageView no_datafound;
    private SearchView searchView;
    boolean recallApi = true;
    LinearLayoutManager layoutManager;
    private Context mContext;
    FragmentCustomerLeadBinding binding;

    public CustomersFragment() {
        // Required empty public constructor
    }

    ArrayList<BusinessPartnerData> AllitemsList;

    // TODO: Rename and change types and number of parameters
    public static CustomersFragment newInstance(String param1, String param2) {
        CustomersFragment fragment = new CustomersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentCustomerLeadBinding.inflate(getLayoutInflater());
        View v = inflater.inflate(R.layout.fragment_customer_lead, container, false);
      //  ButterKnife.bind(this, v);
        mContext = getActivity();
        AllitemsList = new ArrayList<>();
      /* if (Globals.checkInternet(getActivity())) {
           loader.setVisibility(View.VISIBLE);
           callApi(loader);
       }*/

        binding.loader.loader.setVisibility(View.VISIBLE);
        calltypeapi();
        callStateApi();
        binding.swipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (Globals.checkInternet(getActivity())) {

                    callApi(binding.loader.loader);
                     /* FragmentTransaction ft = getFragmentManager().beginTransaction();
                      if (Build.VERSION.SDK_INT >= 26) {
                          ft.setReorderingAllowed(false);
                      }
                      ft.detach(CustomersFragment.this).attach(CustomersFragment.this).commit();*/
                } else
                    binding.swipeRefreshLayout.setRefreshing(false);

            }
        });
        /*  customer_lead_List.addOnScrollListener(new RecyclerView.OnScrollListener()
                 {
              @Override
              public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                  super.onScrollStateChanged(recyclerView, newState);

              }

              @Override
              public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                  super.onScrolled(recyclerView, dx, dy);

                if(layoutManager.findLastCompletelyVisibleItemPosition() == AllitemsList.size()-3 && recallApi)
                   {
           callApi(loader);
                  }
              }
          });*/

        return binding.getRoot();
    }

    @Override
    public void onResume() {

        if (Globals.checkInternet(getActivity())) {
            binding.loader.loader.setVisibility(View.VISIBLE);
            callApi(binding.loader.loader);
        }
        super.onResume();
    }

    private CustomersAdapterDetals dadapter;
    private CustomersAdapter adapter;

    private void callApi(ProgressBar loader) {
        loader.setVisibility(View.VISIBLE);
        CustomerViewModel model = ViewModelProviders.of(this).get(CustomerViewModel.class);
        model.getCustomersList(loader).observe(getActivity(), new Observer<List<BusinessPartnerData>>() {
            @Override
            public void onChanged(@Nullable List<BusinessPartnerData> itemsList) {
                loader.setVisibility(View.GONE);
                if (itemsList.size() > 0) {
                    layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                    binding.customerLeadList.setLayoutManager(layoutManager);
                    AllitemsList.clear();
                    AllitemsList.addAll(itemsList);
                    if (Prefs.getString(Globals.BussinessPageType, "").equalsIgnoreCase("DashBoard")) {


                        dadapter = new CustomersAdapterDetals(getActivity(), AllitemsList);

                        binding.customerLeadList.setAdapter(dadapter);
                        dadapter.notifyDataSetChanged();
                    } else {
                        adapter = new CustomersAdapter(getActivity(), AllitemsList);
                        binding.customerLeadList.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                } else {

                    binding.noDatafound.setVisibility(View.VISIBLE);
                }
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });

//        if (MainActivity.businessPartnerDataFromLocal.size() > 0) {
//            layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
//            binding.customerLeadList.setLayoutManager(layoutManager);
//            AllitemsList.clear();
//            Log.e("LOGGER", "callApi: "+MainActivity.businessPartnerDataFromLocal.get(0).getBPAddresses().size());
//            AllitemsList.addAll(MainActivity.businessPartnerDataFromLocal);
//            if (Prefs.getString(Globals.BussinessPageType, "").equalsIgnoreCase("DashBoard")) {
//                dadapter = new CustomersAdapterDetals(getActivity(), AllitemsList);
//                binding.customerLeadList.setAdapter(dadapter);
//                dadapter.notifyDataSetChanged();
//            } else {
//                adapter = new CustomersAdapter(getActivity(), AllitemsList);
//                binding.customerLeadList.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//            }
//        } else {
//
//            binding.noDatafound.setVisibility(View.VISIBLE);
//        }
//        binding.swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {


        //menu.clear();
        inflater.inflate(R.menu.bussiness_filter, menu);

        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = new SearchView(((BussinessPartners) mContext).getSupportActionBar().getThemedContext());
        searchView.setQueryHint("Search Customers");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setActionView(searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (Prefs.getString(Globals.BussinessPageType, "").equalsIgnoreCase("DashBoard")) {
                    if (dadapter != null)
                        dadapter.filter(newText);

                } else {
                    if (adapter != null)
                        adapter.filter(newText);
                }
                return true;
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.plus:
                Prefs.putString(Globals.AddBp, "");
                startActivity(new Intent(getContext(), AddBPCustomer.class));
                break;
            case R.id.all:
                item.setChecked(!item.isChecked());
                if (dadapter != null)
                    dadapter.AllData();
                if (adapter != null)
                    adapter.AllData();
                break;
            case R.id.my:
                item.setChecked(!item.isChecked());
                opentypedialog();
               /* if(dadapter!=null)
                    dadapter.Customerfilter();

                if(adapter!=null)
                    adapter.Customerfilter();*/
                break;
            case R.id.my_team:
                openstatedialog();
                break;
            /*case R.id.newest:
                item.setChecked(!item.isChecked());
                if(dadapter!=null)
                    dadapter.Typefilter("New Business");
                if(adapter!=null)
                    adapter.Typefilter("New Business");
                break;
            case R.id.oldest:
                item.setChecked(!item.isChecked());
                if(dadapter!=null)
                    dadapter.Typefilter("Existing Business");
                if(adapter!=null)
                    adapter.Typefilter("Existing Business");
                break;
*/
        }
        return true;
    }

    private void openstatedialog() {
        dialog = new Dialog(getContext());
        // LayoutInflater layoutInflater = context.getLayoutInflater();
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View custom_dialog = layoutInflater.inflate(R.layout.dialog_recycler, null);
        dialog.setContentView(custom_dialog);
        //dialog.setTitle("");
        dialog.getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        RecyclerView recyclerview = dialog.findViewById(R.id.dist);
        Button done = dialog.findViewById(R.id.done);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerview.setAdapter(new BPStateFilterAdapter(getContext(), CustomersFragment.this, stateList));
        done.setVisibility(View.GONE);

       /* done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  Log.e("list==>", String.valueOf(Globals.sourcechecklist));
                dialog.cancel();
            }
        });
*/
        dialog.show();
    }

    Dialog dialog;

    private void opentypedialog() {
        dialog = new Dialog(getContext());
        // LayoutInflater layoutInflater = context.getLayoutInflater();
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View custom_dialog = layoutInflater.inflate(R.layout.dialog_recycler, null);
        dialog.setContentView(custom_dialog);
        //dialog.setTitle("");
        dialog.getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        RecyclerView recyclerview = dialog.findViewById(R.id.dist);
        Button done = dialog.findViewById(R.id.done);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerview.setAdapter(new BPTypeFilterAdapter(getContext(), CustomersFragment.this, utypelist));
        done.setVisibility(View.GONE);

       /* done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  Log.e("list==>", String.valueOf(Globals.sourcechecklist));
                dialog.cancel();
            }
        });
*/
        dialog.show();
    }


    ArrayList<StateData> stateList = new ArrayList<>();

    private void callStateApi() {

        StateData stateData = new StateData();
        stateData.setCountry("IN");
        Call<StateRespose> call = NewApiClient.getInstance().getApiService().getStateList(stateData);
        call.enqueue(new Callback<StateRespose>() {
            @Override
            public void onResponse(Call<StateRespose> call, Response<StateRespose> response) {

                if (response.code() == 200) {
                    stateList.addAll(response.body().getData());

                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                        Toast.makeText(getContext(), mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StateRespose> call, Throwable t) {

                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    List<UTypeData> utypelist = new ArrayList<>();

    private void calltypeapi() {
//        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
//        model.getBpTypeList().observe(getActivity(), new Observer<List<UTypeData>>() {
//            @Override
//            public void onChanged(@Nullable List<UTypeData> itemsList) {
//                if(itemsList == null || itemsList.size()== 0){
//
//                }else {
//                    utypelist = itemsList;
//
//                }
//            }
//        });

        utypelist = MainActivity.itemBpTypeDataFromLocal;
    }


    @Override
    public void stagecomment(String name, String id) {
        if (id.equalsIgnoreCase("Type")) {
            if (dadapter != null) {
                dadapter.Typefilter(name);
                if (dadapter.getItemCount() == 0)
                    binding.noDatafound.setVisibility(View.VISIBLE);
                else
                    binding.noDatafound.setVisibility(View.GONE);
            }

            if (adapter != null) {
                adapter.Typefilter(name);
                if (adapter.getItemCount() == 0)
                    binding.noDatafound.setVisibility(View.VISIBLE);
                else
                    binding.noDatafound.setVisibility(View.GONE);
            }
        } else if (id.equalsIgnoreCase("State")) {
            if (dadapter != null) {
                dadapter.StateFilter(name);
                if (dadapter.getItemCount() == 0)
                    binding.noDatafound.setVisibility(View.VISIBLE);
                else
                    binding.noDatafound.setVisibility(View.GONE);
            }

            if (adapter != null) {
                adapter.StateFilter(name);
                if (adapter.getItemCount() == 0)
                    binding.noDatafound.setVisibility(View.VISIBLE);
                else
                    binding.noDatafound.setVisibility(View.GONE);
            }
        }
        dialog.cancel();
    }
}