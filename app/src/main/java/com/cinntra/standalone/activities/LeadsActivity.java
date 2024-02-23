package com.cinntra.standalone.activities;

import android.annotation.SuppressLint;
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
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baoyz.widget.PullRefreshLayout;
import com.cinntra.standalone.R;
import com.cinntra.standalone.adapters.LeadTypeAdapter;
import com.cinntra.standalone.adapters.LeadsAdapter;
import com.cinntra.standalone.adapters.SourceAdpater;
import com.cinntra.standalone.databinding.FragmentLeadBinding;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.globals.MainBaseActivity;
import com.cinntra.standalone.model.LeadFilter;
import com.cinntra.standalone.model.LeadTypeData;
import com.cinntra.standalone.model.LeadTypeResponse;
import com.cinntra.standalone.model.QuotationResponse;
import com.cinntra.standalone.newapimodel.LeadResponse;
import com.cinntra.standalone.newapimodel.LeadValue;
import com.cinntra.standalone.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LeadsActivity extends MainBaseActivity implements View.OnClickListener, com.borax12.materialdaterangepicker.date.DatePickerDialog.OnDateSetListener {


    public Context mContext;
//    @BindView(R.id.customer_lead_List)
//    RecyclerView recyclerView;
//    @BindView(R.id.loader)
//    ProgressBar loader;
//    @BindView(R.id.swipeRefreshLayout)
//    PullRefreshLayout swipeRefreshLayout;
//    @BindView(R.id.no_datafound)
//    ImageView no_datafound;
//    @BindView(R.id.lead_typeSpinner)
//    Spinner lead_typeSpinner;
//    @BindView(R.id.dotcolor)
//    ImageView dotcolor;
//    @BindView(R.id.dateText)
//    TextView dateText;
//    @BindView(R.id.calendar)
//    ImageView calendar;
//    @BindView(R.id.all_list)
//    TextView all_list;
//    @BindView(R.id.nestedSV)
//    NestedScrollView nestedSV;
//    @BindView(R.id.idPBLoading)
//    ProgressBar idPBLoading;


    List<LeadValue> leadValueList = new ArrayList<>();

    LeadsAdapter adapter;
    LeadFilter value = new LeadFilter();

    boolean recallApi = true;
    int pageno = 1;
    boolean dropdownApi = false;

    public LeadsActivity() {
        // Required empty public constructor
    }

    FragmentLeadBinding binding;


    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentLeadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24);
        Globals.sourcechecklist.clear();
        mContext = LeadsActivity.this;
        value.setAssignedTo(Globals.TeamEmployeeID);
        value.setLeadType("All");
        binding.calendar.setOnClickListener(this);
        binding.allList.setOnClickListener(this);
        callSourceApi();
        binding.customerLeadList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        adapter = new LeadsAdapter(mContext, leadValueList);
        binding.customerLeadList.setAdapter(adapter);
        binding.swipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Globals.sourcechecklist.clear();
                if (Globals.checkInternet(LeadsActivity.this)) {
                    pageno = 1;
                    recallApi = true;
                    leadValueList.clear();
                    callApi(value);
                    binding.dateText.setVisibility(View.GONE);
                    binding.leadTypeSpinner.setSelection(0);

                } else
                    binding.swipeRefreshLayout.setRefreshing(false);

            }
        });


        binding.nestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.

                    if (Globals.checkInternet(LeadsActivity.this) && recallApi) {

                        binding.idPBLoading.setVisibility(View.VISIBLE);
                        binding.loader.loader.setVisibility(View.VISIBLE);
                        pageno++;
                        callApi(value);
                        binding.leadTypeSpinner.setSelection(0);

                    }


                }

            }
        });

    /*    lead_typeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    dotcolor.setBackground(getDrawable(R.drawable.ic_green_dot));
                    callApi(value);
                    nodatafoundimage();
                }
            }
        });*/

        binding.leadTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (adapter != null) {
                    binding.dateText.setVisibility(View.GONE);
                    pageno = 1;
                    recallApi = true;

                    if (position == 0 && dropdownApi) {
                        binding.dotcolor.setBackgroundColor(getResources().getColor(R.color.transparent));
                        value.setLeadType("All");

                        //   adapter.priorityfilter("");
                        leadValueList.clear();
                        callApi(value);
//                        nodatafoundimage();
                    } else if (position == 1) {
                        dropdownApi = true;
                        binding.dotcolor.setBackground(getDrawable(R.drawable.red_dot));
                        //    adapter.priorityfilter("Hot");
                        value.setLeadType("Hot");
                        //   nodatafoundimage();
                        leadValueList.clear();
                        callApi(value);
                    } else if (position == 2) {
                        binding.dotcolor.setBackground(getDrawable(R.drawable.ic_blue_dot));
                        value.setLeadType("Cold");
                        dropdownApi = true;
                        //   adapter.priorityfilter("Cold");
                        leadValueList.clear();
                        callApi(value);
                        //  nodatafoundimage();
                    } else if (position == 3) {
                        dropdownApi = true;
                        binding.dotcolor.setBackground(getDrawable(R.drawable.orange_dot));
                        //  adapter.priorityfilter("Warm");
                        value.setLeadType("Warm");
                        leadValueList.clear();
                        callApi(value);
                        //   nodatafoundimage();
                    }
                    nodatafoundimage();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
//                        Toast.makeText(LeadsActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
//                    } catch (IOException e) {
//                        //handle failure to read error
//                    }
//                }
//
//            }
//            @Override
//            public void onFailure(Call<LeadTypeResponse> call, Throwable t) {
//
//                Toast.makeText(LeadsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

        sourceData.clear();
        sourceData.addAll(MainActivity.leadSourceListFromLocal);

    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.customer_lead);
        fragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mContext = LeadsActivity.this;
        if (Globals.checkInternet(mContext)) {
            binding.loader.loader.setVisibility(View.VISIBLE);
            pageno = 1;
            recallApi = true;
            leadValueList.clear();
            callApi(value);
        } else {
            Toasty.error(mContext, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void callApi(LeadFilter leadValue) {
//        leadValue.setLeadType("2");
        leadValue.setMaxItem(50);
        leadValue.setPageNo(pageno);

        Call<LeadResponse> call = NewApiClient.getInstance().getApiService().getAllLead(leadValue);
        call.enqueue(new Callback<LeadResponse>() {
            @Override
            public void onResponse(Call<LeadResponse> call, Response<LeadResponse> response) {
                if (response.code() == 200) {


                    //  leadValueList.addAll(response.body().getData());
                    if (Prefs.getString(Globals.BussinessPageType, "").equalsIgnoreCase("AddBPLead")) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            if (response.body().getData().get(i).getStatus().equalsIgnoreCase("Qualified")) {
                                leadValueList.add(response.body().getData().get(i));
                            } else {

                            }
                        }
                    } else {
                        leadValueList.addAll(response.body().getData());
                    }


                    recallApi = response.body().getData().size() >= 50;
                    adapter.notifyDataSetChanged();
                } else {

                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                        Toast.makeText(LeadsActivity.this, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
                nodatafoundimage();
                binding.idPBLoading.setVisibility(View.GONE);
                binding.swipeRefreshLayout.setRefreshing(false);
                binding.loader.loader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<LeadResponse> call, Throwable t) {
                binding.swipeRefreshLayout.setRefreshing(false);
                binding.loader.loader.setVisibility(View.GONE);
                binding.idPBLoading.setVisibility(View.GONE);
                Toast.makeText(LeadsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:

                break;
            case R.id.plus:
                startActivity(new Intent(this, AddLead.class));
                break;
            case R.id.follow_up:

                adapter.leadfilter("Follow Up");
                nodatafoundimage();

                break;
            case R.id.all:
                Globals.sourcechecklist.clear();
                adapter.leadfilter("");
                nodatafoundimage();

                break;
            case R.id.meeting:
            case R.id.dead:

                adapter.junkfilter();
                nodatafoundimage();
                break;
            case R.id.demo:

                adapter.leadfilter("New");
                nodatafoundimage();
                break;
            case R.id.hold:

                adapter.leadfilter("Hold");
                nodatafoundimage();
                break;
            case R.id.negotiation:

                adapter.leadfilter("Qualified");
                nodatafoundimage();
                break;
            case R.id.source:
                showsourcedialog();
                break;
            case android.R.id.home:
                this.finish();
                return true;
        }
        return true;
    }

    private void showsourcedialog() {
        Dialog dialog = new Dialog(this);
        // LayoutInflater layoutInflater = context.getLayoutInflater();
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View custom_dialog = layoutInflater.inflate(R.layout.dialog_recycler, null);
        dialog.setContentView(custom_dialog);
        //dialog.setTitle("");
        dialog.getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        RecyclerView recyclerview = dialog.findViewById(R.id.dist);
        Button done = dialog.findViewById(R.id.done);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerview.setAdapter(new SourceAdpater(LeadsActivity.this, sourceData));


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.sourcefilter(Globals.sourcechecklist);
                nodatafoundimage();
                Log.e("list==>", String.valueOf(Globals.sourcechecklist));
                dialog.cancel();
            }
        });

        dialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lead_filter, menu);

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = new SearchView(((LeadsActivity) mContext).getSupportActionBar().getThemedContext());

        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setActionView(searchView);
        searchView.setQueryHint("Search Lead");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (adapter != null)
                    adapter.filter(newText);


                return true;
            }
        });


        return true;
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }

    }

    public void nodatafoundimage() {
        if (adapter.getItemCount() == 0) {
            binding.noDatafound.setVisibility(View.VISIBLE);
        } else {
            binding.noDatafound.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.calendar:
                //openDateDialog();
                opencalendardialog();
                break;
            case R.id.all_list:
                binding.loader.loader.setVisibility(View.VISIBLE);
                pageno = 1;
                recallApi = true;
                leadValueList.clear();
                callApi(value);
                binding.dateText.setVisibility(View.GONE);
                break;
        }
    }

    private void opencalendardialog() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        com.borax12.materialdaterangepicker.date.DatePickerDialog datePickerDialog = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(LeadsActivity.this, mYear, mMonth, mDay);
        datePickerDialog.show(getFragmentManager(), "Datepickerdialog");

    }

    String[] Date = new String[2];

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDateSet(com.borax12.materialdaterangepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        Date[0] = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
        Date[1] = dayOfMonthEnd + "-" + (monthOfYearEnd + 1) + "-" + yearEnd;

        SimpleDateFormat format1 = new SimpleDateFormat("dd-M-yyyy");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date startdate = null;
        java.util.Date enddate = null;
        try {
            startdate = format1.parse(Date[0]);
            enddate = format1.parse(Date[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        binding.dateText.setVisibility(View.VISIBLE);
        binding.dateText.setText(format2.format(startdate) + " to " + format2.format(enddate));
        adapter.datefilter(format2.format(startdate), format2.format(enddate));

        nodatafoundimage();
    }
}