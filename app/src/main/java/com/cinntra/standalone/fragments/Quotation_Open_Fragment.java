package com.cinntra.standalone.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
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
import com.cinntra.standalone.activities.AddQuotationAct;
import com.cinntra.standalone.activities.MainActivity;
import com.cinntra.standalone.activities.QuotationActivity;
import com.cinntra.standalone.adapters.BPTypeFilterAdapter;
import com.cinntra.standalone.adapters.CustomerFilterAdapter;
import com.cinntra.standalone.adapters.CustomersAdapter;
import com.cinntra.standalone.adapters.CustomersAdapterDetals;
import com.cinntra.standalone.adapters.Quotation_Adapter;
import com.cinntra.standalone.databinding.FragmentQuotesListBinding;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.interfaces.CommentStage;
import com.cinntra.standalone.interfaces.FragmentRefresher;
import com.cinntra.standalone.model.BusinessPartnerData;
import com.cinntra.standalone.model.QuotationItem;
import com.cinntra.standalone.viewModel.CustomerViewModel;
import com.cinntra.standalone.viewModel.QuotationList_ViewModel;
import com.pixplicity.easyprefs.library.Prefs;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;




public class Quotation_Open_Fragment extends Fragment implements View.OnClickListener, FragmentRefresher, CommentStage {


//    @BindView(R.id.recyclerview)
//    RecyclerView recyclerview;
//    @BindView(R.id.loader)
//    ProgressBar loader;
//    @BindView(R.id.no_datafound)
//    ImageView no_datafound;
//    @BindView(R.id.swipeRefreshLayout)
//    PullRefreshLayout swipeRefreshLayout;

    FragmentQuotesListBinding binding;


    private SearchView searchView;
    private Quotation_Adapter adapter;
    LinearLayoutManager layoutManager;
    int currentPage = 0;
    //    private boolean isLastPage = false;
    ArrayList<QuotationItem> AllItemList;
    private boolean isLoading = false;
    boolean recallApi = true;
    private Context mContext;

    public Quotation_Open_Fragment() {
        //Required empty public constructor

    }


  /*  // TODO: Rename and change types and number of parameters
    public static Quotation_Open_Fragment newInstance(String param1, String param2) {
      Quotation_Open_Fragment fragment = new Quotation_Open_Fragment(oppdata);
      Bundle args = new Bundle();

      fragment.setArguments(args);
      return fragment;
        }*/


    @Override
    public void onResume() {
        super.onResume();
        if (Globals.checkInternet(getActivity())) {
           binding.loader.loader.setVisibility(View.VISIBLE);
            callApi(binding.loader.loader);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        binding=FragmentQuotesListBinding.inflate(inflater,container,false);
        View v = inflater.inflate(R.layout.fragment_quotes_list, container, false);
     //   ButterKnife.bind(this, v);
        mContext = getActivity();
        AllItemList = new ArrayList<>();


        if (Globals.checkInternet(getContext())) {
            callCustomerApi();
        }

        binding.swipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (Globals.checkInternet(getActivity())) {

                    callApi(binding.loader.loader);
                } else
                   binding.swipeRefreshLayout.setRefreshing(false);
            }
        });


        return binding.getRoot();
    }

    ArrayList<BusinessPartnerData> AllitemsList;

    private void callCustomerApi() {
//        CustomerViewModel model = ViewModelProviders.of(this).get(CustomerViewModel.class);
//        model.getCustomersList(loader).observe(getActivity(), new Observer<List<BusinessPartnerData>>() {
//            @Override
//            public void onChanged(@Nullable List<BusinessPartnerData> itemsList) {
//
//                if (itemsList.size() > 0) {
//                    AllitemsList = new ArrayList<>();
//                    AllitemsList.addAll(itemsList);
//
//                }
//            }
//        });


        if (MainActivity.businessPartnerDataFromLocal.size() > 0) {
            AllitemsList = new ArrayList<>();
            AllitemsList.addAll(MainActivity.businessPartnerDataFromLocal);

        }
    }


    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()) {
        /*  case R.id.new_quatos:
          fragment = new New_Quotation();
        FragmentManager fm       = getFragmentManager();
        FragmentTransaction transaction  = fm.beginTransaction();
       //FragmentTransaction transaction =  ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.quatoes_main_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
           break;*/


        }


    }


    /***********  API Calling ************/

    private void callApi(ProgressBar loader) {
        QuotationList_ViewModel model = ViewModelProviders.of(this).get(QuotationList_ViewModel.class);
        model.getQutotation(loader).observe(getActivity(), new Observer<List<QuotationItem>>() {
            @Override
            public void onChanged(@Nullable List<QuotationItem> itemsList) {


                AllItemList.clear();
                assert itemsList != null;
                AllItemList.addAll(itemsList);
                layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                adapter = new Quotation_Adapter(Quotation_Open_Fragment.this, getContext(), AllItemList);
              binding.recyclerview.setLayoutManager(layoutManager);
                binding.recyclerview.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                if (AllItemList.size() == 0)
                    binding.noDatafound.setVisibility(View.VISIBLE);
                else
                    binding.noDatafound.setVisibility(View.GONE);
                binding.swipeRefreshLayout.setRefreshing(false);
            }


        });

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {


        //menu.clear();
        inflater.inflate(R.menu.quotation_filter, menu);

        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = new SearchView(((QuotationActivity) mContext).getSupportActionBar().getThemedContext());
        searchView.setQueryHint("Search Quotation");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setActionView(searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (adapter != null)
                    adapter.filter(newText);
                return true;
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.plus:

                Intent i = new Intent(getContext(), AddQuotationAct.class);
                startActivity(i);
                break;
            case R.id.all:
                if (adapter != null)
                    adapter.AllData();
                break;
            case R.id.newest:
                LocalDate date = LocalDate.parse(Globals.curntDate);
                LocalDate dateafter = date.minusDays(8);
                adapter.PostingDate(dateafter, date);
                break;
            case R.id.oldest:
//                        Date date1 = sdf.parse(date);
//                String curntDate = Globals.getTodaysDate();
                break;
            case R.id.my:
                if (adapter != null)
                    adapter.Favfilter("Y");
                break;
            case R.id.valid:

                showcustomerdialog();

               /* if(adapter!=null)
                    adapter.Customerfilter();*/
                break;
            case R.id.posting:

                break;
        }
        return true;
    }

    Dialog dialog;

    private void showcustomerdialog() {
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
        recyclerview.setAdapter(new CustomerFilterAdapter(getContext(), Quotation_Open_Fragment.this, AllitemsList));
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

    @Override
    public void onRefresh() {
        binding.loader.loader.setVisibility(View.VISIBLE);
        callApi(binding.loader.loader);
    }

    @Override
    public void stagecomment(String id, String name) {
        dialog.cancel();
        if (adapter != null) {
            adapter.Customernamefilter(name);
            if (adapter.getItemCount() == 0)
                binding.noDatafound.setVisibility(View.VISIBLE);
            else
                binding.noDatafound.setVisibility(View.GONE);
        }
    }
}