package com.cinntra.standalone.fragments;

import android.app.Dialog;
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
import com.cinntra.standalone.activities.AddOrderAct;
import com.cinntra.standalone.activities.MainActivity;
import com.cinntra.standalone.activities.OrderActivity;
import com.cinntra.standalone.adapters.CustomerFilterAdapter;
import com.cinntra.standalone.adapters.DemoAdapter;
import com.cinntra.standalone.adapters.Open_Order_Adapter;
import com.cinntra.standalone.databinding.FragmentOpenOrderBinding;
import com.cinntra.standalone.databinding.FragmentOrdersBinding;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.interfaces.CommentStage;
import com.cinntra.standalone.model.BusinessPartnerData;
import com.cinntra.standalone.model.QuotationItem;
import com.cinntra.standalone.viewModel.CustomerViewModel;
import com.cinntra.standalone.viewModel.QuotationList_ViewModel;
import com.pixplicity.easyprefs.library.Prefs;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Open_Order extends Fragment implements CommentStage {

    //    @BindView(R.id.recyclerview)
//    RecyclerView recyclerview;
//    @BindView(R.id.loader)
//    ProgressBar loader;
//    @BindView(R.id.no_datafound)
//    ImageView no_datafound;
//    @BindView(R.id.swipeRefreshLayout)
//    PullRefreshLayout swipeRefreshLayout;
    private SearchView searchView;
    int currentpage = 0;
    boolean recallApi = true;
    ArrayList<QuotationItem> AllItemList  = new ArrayList<>();

    ArrayList<QuotationItem> AllItemNewList = new ArrayList<>();
    LinearLayoutManager layoutManager;
    FragmentOpenOrderBinding binding;

    public Open_Order() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Globals.checkInternet(getActivity())) {
            {
                binding.loader.loader.setVisibility(View.VISIBLE);
                callApi(binding.loader.loader);
            }

        }
    }

    // TODO: Rename and change types and number of parameters
    public static Open_Order newInstance(String param1, String param2) {
        Open_Order fragment = new Open_Order();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    Open_Order_Adapter adapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOpenOrderBinding.inflate(getLayoutInflater());
//        View v = inflater.inflate(R.layout.fragment_open_order, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        /*recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(layoutManager.findLastCompletelyVisibleItemPosition() == AllItemList.size()-3 && recallApi)
                {
                    callApi(loader);
                }
            }
        });*/
    }

    ArrayList<BusinessPartnerData> AllitemsList;

    private void callCustomerApi() {
//        CustomerViewModel model = ViewModelProviders.of(this).get(CustomerViewModel.class);
//        model.getCustomersList(loader).observe(getActivity(), new Observer<List<BusinessPartnerData>>() {
//            @Override
//            public void onChanged(@Nullable List<BusinessPartnerData> itemsList) {
//
//                if(itemsList.size()>0) {
//                    AllitemsList = new ArrayList<>();
//                    AllitemsList.addAll(itemsList);
//
//                }
//            }
//        });

//        if(MainActivity.businessPartnerDataFromLocal.size()>0) {
//            AllitemsList = new ArrayList<>();
//            AllitemsList.addAll(MainActivity.businessPartnerDataFromLocal);
//
//        }

    }

    private void callApi(ProgressBar loader) {
        loader.setVisibility(View.VISIBLE);
        QuotationList_ViewModel model = ViewModelProviders.of(this).get(QuotationList_ViewModel.class);
        model.getOrders(loader).observe(getActivity(), new Observer<List<QuotationItem>>() {
            @Override
            public void onChanged(@Nullable List<QuotationItem> itemsList) {
                loader.setVisibility(View.GONE);
                AllItemNewList.clear();
                // assert itemsList != null;
                AllItemNewList.addAll(itemsList);

              //  Toast.makeText(requireContext(), "Adapter List==>" + AllItemNewList.size(), Toast.LENGTH_SHORT).show();

                setAdapter();

                if (itemsList.size() < 0)
                    binding.noDatafound.setVisibility(View.VISIBLE);
                else
                    binding.noDatafound.setVisibility(View.GONE);
                binding.swipeRefreshLayout.setRefreshing(false);
            }


        });
    }

    private void setAdapter() {
        layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        binding.recyclerview.setLayoutManager(layoutManager);
        Open_Order_Adapter adapter = new Open_Order_Adapter(getActivity(), AllItemNewList);
        binding.recyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {


        //menu.clear();
        inflater.inflate(R.menu.order_filter, menu);

        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = new SearchView(((OrderActivity) getContext()).getSupportActionBar().getThemedContext());
        searchView.setQueryHint("Search Orders");
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
                Prefs.putString(Globals.FromQuotation, "Order");
                startActivity(new Intent(getContext(), AddOrderAct.class));
                break;
            case R.id.all:
                if (adapter != null)
                    adapter.allData();
                nodatafound();
                break;
            case R.id.customer:
              /*  if(adapter!=null)
                    adapter.Customerfilter();*/
                showcustomerdialog();
                break;
            case R.id.posting:

                break;
            case R.id.valid:
                if (adapter != null)
                    adapter.ValidDate();
                break;
            case R.id.order:
                LocalDate dateObj2 = LocalDate.parse(Globals.curntDate);
                LocalDate afterdate2 = dateObj2.minusDays(8);
                adapter.PostingDate(afterdate2, dateObj2);
                break;
        }
        return true;
    }

    private void nodatafound() {
        if (adapter.getItemCount() == 0)
            binding.noDatafound.setVisibility(View.VISIBLE);
        else
            binding.noDatafound.setVisibility(View.GONE);
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
        recyclerview.setAdapter(new CustomerFilterAdapter(getContext(), Open_Order.this, AllitemsList));
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