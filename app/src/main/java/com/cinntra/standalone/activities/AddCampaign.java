package com.cinntra.standalone.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cinntra.standalone.R;
import com.cinntra.standalone.adapters.CountryAdapter;
import com.cinntra.standalone.adapters.IndustrySpinnerAdapter;
import com.cinntra.standalone.adapters.LeadTypeAdapter;
import com.cinntra.standalone.adapters.SalesEmployeeAdapter;
import com.cinntra.standalone.adapters.StateAdapter;
import com.cinntra.standalone.animation.ViewAnimationUtils;
import com.cinntra.standalone.databinding.AddCampaignBinding;
import com.cinntra.standalone.fragments.Dashboard;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.globals.MainBaseActivity;
import com.cinntra.standalone.model.AddCampaignModel;
import com.cinntra.standalone.model.CampaignModel;
import com.cinntra.standalone.model.CampaignResponse;
import com.cinntra.standalone.model.IndustryItem;
import com.cinntra.standalone.model.LeadTypeData;
import com.cinntra.standalone.model.LeadTypeResponse;
import com.cinntra.standalone.model.QuotationResponse;
import com.cinntra.standalone.model.SalesEmployeeItem;
import com.cinntra.standalone.model.StateData;
import com.cinntra.standalone.model.StateRespose;
import com.cinntra.standalone.newapimodel.LeadResponse;
import com.cinntra.standalone.viewModel.ItemViewModel;
import com.cinntra.standalone.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCampaign extends MainBaseActivity implements View.OnClickListener {


//    @BindView(R.id.companyname)
//    EditText companyname;
//    @BindView(R.id.description)
//    EditText description;
//    @BindView(R.id.from_date)
//    EditText from_date;
//    @BindView(R.id.to_date)
//    EditText to_date;
//    @BindView(R.id.oppfrom_date)
//    EditText oppfrom_date;
//    @BindView(R.id.oppto_date)
//    EditText oppto_date;
//    @BindView(R.id.customerfrom_date)
//    EditText customerfrom_date;
//    @BindView(R.id.customerto_date)
//    EditText customerto_date;
//    @BindView(R.id.campaign_spinner)
//    Spinner campaign_spinner;
//    @BindView(R.id.source_spinner)
//    Spinner source_spinner;
//    @BindView(R.id.priority_spinner)
//    Spinner priority_spinner;
//    @BindView(R.id.status_spinner)
//    Spinner status_spinner;
//    @BindView(R.id.type_spinner)
//    Spinner type_spinner;
//    @BindView(R.id.customertype_spinner)
//    Spinner customertype_spinner;
//    @BindView(R.id.country_spinner)
//    Spinner country_spinner;
//    @BindView(R.id.state_spinner)
//    Spinner state_spinner;
//    @BindView(R.id.industry_spinner)
//    Spinner industry_spinner;
//    @BindView(R.id.customersales_employee_spinner)
//    Spinner customersales_employee_spinner;
//    @BindView(R.id.salesemployee_spinner)
//    Spinner salesemployee_spinner;
//    @BindView(R.id.create)
//    Button create;
//    @BindView(R.id.back_press)
//    RelativeLayout back_press;
//    @BindView(R.id.head_title)
//    TextView head_title;
//    @BindView(R.id.all_bp)
//    CheckBox all_bp;
//    @BindView(R.id.all_opp)
//    CheckBox all_opp;
//    @BindView(R.id.all_lead)
//    CheckBox all_lead;
//    @BindView(R.id.lead_view)
//    LinearLayout lead_view;
//    @BindView(R.id.opp_view)
//    LinearLayout opp_view;
//    @BindView(R.id.bPview)
//    LinearLayout bpView;

    CountryAdapter countryAdapter;
    String Countrycode, CountryName, StateName, StateCode, industryCode, sourcetype, leadtype;
    ArrayList<LeadTypeData> sourceData = new ArrayList<>();
    ArrayList<LeadTypeData> leadTypeData = new ArrayList<>();
    List<SalesEmployeeItem> salesEmployeeItemList = new ArrayList<>();

    String CustomerType, OppType, CampaignAccess = "--None--";
    String OppsalesEmployeeCode, OppsalesEmployeename, CustomersalesEmployeeCode, CustomersalesEmployeename;
    String status = "Follow Up";
    StateAdapter stateAdapter;
    ArrayList<StateData> stateList = new ArrayList<>();
    AddCampaignBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=AddCampaignBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
      //  ButterKnife.bind(this);
        eventmanager();


    }

    private void eventmanager() {

        binding.headerBottomRounded.headTitle.setText("Add Campaign Set");
        binding.headerBottomRounded.backPress.setOnClickListener(this);
        binding.fromDate.setOnClickListener(this);
        binding.toDate.setOnClickListener(this);
        binding.oppfromDate.setOnClickListener(this);
        binding.opptoDate.setOnClickListener(this);
        binding.customerfromDate.setOnClickListener(this);
        binding.customertoDate.setOnClickListener(this);
        binding.create.setOnClickListener(this);


        binding.allOpp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    ViewAnimationUtils.collapse(binding.oppView);
//                  opp_view.setVisibility(View.GONE);
                else
                    ViewAnimationUtils.expand(binding.oppView);
//                  opp_view.setVisibility(View.VISIBLE);
            }
        });

        binding.allLead.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    ViewAnimationUtils.collapse(binding.leadView);
//                    lead_view.setVisibility(View.GONE);
                else
                    ViewAnimationUtils.expand(binding.leadView);
//                    lead_view.setVisibility(View.VISIBLE);
            }
        });
        binding.allBp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    ViewAnimationUtils.collapse(binding.bPview);
//                    bpView.setVisibility(View.GONE);
                else
                    ViewAnimationUtils.expand(binding.bPview);
//                    bpView.setVisibility(View.VISIBLE);
            }
        });


        callleadTypeApi();

        countryAdapter = new CountryAdapter(AddCampaign.this, MainActivity.countrylistFromLocal);
        binding.countrySpinner.setAdapter(countryAdapter);
        binding.countrySpinner.setSelection(Globals.getCountrypos(MainActivity.countrylistFromLocal, "India"));

        binding.countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Countrycode = MainActivity.countrylistFromLocal.get(position).getCode();
                CountryName = MainActivity.countrylistFromLocal.get(position).getName();
                callStateApi(Countrycode);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Countrycode = MainActivity.countrylistFromLocal.get(0).getCode();
                CountryName = MainActivity.countrylistFromLocal.get(0).getName();
            }
        });

        binding.stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StateName = stateList.get(position).getName();
                StateCode = stateList.get(position).getCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                StateName = stateList.get(0).getName();
                StateCode = stateList.get(0).getCode();
            }
        });


        binding.customertypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CustomerType = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                CustomerType = parent.getSelectedItem().toString();
            }
        });


        binding.statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                status = parent.getSelectedItem().toString();
            }
        });

        binding.typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                OppType = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                OppType = parent.getSelectedItem().toString();
            }
        });


        binding.campaignSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CampaignAccess = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                CampaignAccess = parent.getSelectedItem().toString();
            }
        });


        binding.prioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                leadtype = leadTypeData.get(position).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                leadtype = leadTypeData.get(0).getName();
            }
        });


        binding.sourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sourcetype = sourceData.get(position).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sourcetype = sourceData.get(0).getName();
            }
        });

        binding.industrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                industryCode = IndustryItemItemList.get(position).getIndustryCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                industryCode = IndustryItemItemList.get(0).getIndustryCode();
            }
        });


    }


    private void callleadTypeApi() {

        //   Call<LeadTypeResponse> call = NewApiClient.getInstance().getApiService().getLeadType();
//        call.enqueue(new Callback<LeadTypeResponse>() {
//            @Override
//            public void onResponse(Call<LeadTypeResponse> call, Response<LeadTypeResponse> response) {
//
//                if (response.code() == 200) {
//                    leadTypeData.clear();
//                    leadTypeData.addAll(response.body().getData());
//                    priority_spinner.setAdapter(new LeadTypeAdapter(AddCampaign.this, leadTypeData));
//                    leadtype = leadTypeData.get(0).getName();
//                } else {
//                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
//                    Gson gson = new GsonBuilder().create();
//                    LeadResponse mError = new LeadResponse();
//                    try {
//                        String s = response.errorBody().string();
//                        mError = gson.fromJson(s, LeadResponse.class);
//                        Toast.makeText(AddCampaign.this, mError.getMessage(), Toast.LENGTH_LONG).show();
//                    } catch (IOException e) {
//                        //handle failure to read error
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<LeadTypeResponse> call, Throwable t) {
//
//                Toast.makeText(AddCampaign.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

        leadTypeData.clear();
        leadTypeData.addAll(MainActivity.leadTypeListFromLocal);
        binding.prioritySpinner.setAdapter(new LeadTypeAdapter(AddCampaign.this, leadTypeData));
        leadtype = leadTypeData.get(0).getName();

        callSourceApi();
    }


    private void callSourceApi() {


//        Call<LeadTypeResponse> call = NewApiClient.getInstance().getApiService().getsourceType();
//        call.enqueue(new Callback<LeadTypeResponse>() {
//            @Override
//            public void onResponse(Call<LeadTypeResponse> call, Response<LeadTypeResponse> response) {
//
//                if (response.code() == 200) {
//                    sourceData.clear();
//                    sourceData.addAll(response.body().getData());
//                    source_spinner.setAdapter(new LeadTypeAdapter(AddCampaign.this, sourceData));
//                    sourcetype = sourceData.get(0).getName();
//                } else {
//                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
//                    Gson gson = new GsonBuilder().create();
//                    LeadResponse mError = new LeadResponse();
//                    try {
//                        String s = response.errorBody().string();
//                        mError = gson.fromJson(s, LeadResponse.class);
//                        Toast.makeText(AddCampaign.this, mError.getMessage(), Toast.LENGTH_LONG).show();
//                    } catch (IOException e) {
//                        //handle failure to read error
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<LeadTypeResponse> call, Throwable t) {
//
//                Toast.makeText(AddCampaign.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

        sourceData.clear();
        sourceData.addAll(MainActivity.leadSourceListFromLocal);
        binding.sourceSpinner.setAdapter(new LeadTypeAdapter(AddCampaign.this, sourceData));
        sourcetype = sourceData.get(0).getName();



        callIndustryApi();
    }


    List<IndustryItem> IndustryItemItemList = new ArrayList<>();

    private void callIndustryApi() {
//    {
//        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
//        model.getIndustryList().observe(this, new Observer<List<IndustryItem>>() {
//            @Override
//            public void onChanged(@Nullable List<IndustryItem> itemsList) {
//                if(itemsList == null || itemsList.size()== 0){
//                    Globals.setmessage(AddCampaign.this);
//                }else {
//                    IndustryItemItemList = itemsList;
//                    industry_spinner.setAdapter(new IndustrySpinnerAdapter(AddCampaign.this,itemsList));
//                    industryCode = IndustryItemItemList.get(0).getIndustryCode();
//
//                }
//            }
//        });

        if (MainActivity.industryListFromLocal == null || MainActivity.industryListFromLocal.size() == 0) {
            Globals.setmessage(this);
        } else {
            IndustryItemItemList = MainActivity.industryListFromLocal;
            binding.industrySpinner.setAdapter(new IndustrySpinnerAdapter(this, MainActivity.industryListFromLocal));
            industryCode = IndustryItemItemList.get(0).getIndustryCode();

        }


        callSalesEmployeeApi();
    }

    private void callSalesEmployeeApi() {

        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getSalesEmployeeList().observe(AddCampaign.this, new Observer<List<SalesEmployeeItem>>() {
            @Override
            public void onChanged(@Nullable List<SalesEmployeeItem> itemsList) {
                if (itemsList == null || itemsList.size() == 0) {
                    Globals.setmessage(AddCampaign.this);
                } else {
                    salesEmployeeItemList = itemsList;
                    binding.salesemployeeSpinner.setAdapter(new SalesEmployeeAdapter(AddCampaign.this, itemsList));
                    binding.customersalesEmployeeSpinner.setAdapter(new SalesEmployeeAdapter(AddCampaign.this, itemsList));
                    OppsalesEmployeeCode = salesEmployeeItemList.get(0).getSalesEmployeeCode();
                    OppsalesEmployeename = salesEmployeeItemList.get(0).getSalesEmployeeName();
                    CustomersalesEmployeeCode = salesEmployeeItemList.get(0).getSalesEmployeeCode();
                    CustomersalesEmployeename = salesEmployeeItemList.get(0).getSalesEmployeeName();

                }
            }
        });
    }

    private void callStateApi(String countryCode) {

        StateData stateData = new StateData();
        stateData.setCountry(countryCode);
        Call<StateRespose> call = NewApiClient.getInstance().getApiService().getStateList(stateData);
        call.enqueue(new Callback<StateRespose>() {
            @Override
            public void onResponse(Call<StateRespose> call, Response<StateRespose> response) {

                if (response.code() == 200) {

                    stateList.clear();
                    if (response.body().getData().size() > 0) {

                        stateList.addAll(response.body().getData());
                    } else {
                        StateData sta = new StateData();
                        sta.setName("Select State");
                        stateList.add(sta);
                    }
                    stateAdapter = new StateAdapter(AddCampaign.this, stateList);
                    binding.stateSpinner.setAdapter(stateAdapter);
                    stateAdapter.notifyDataSetChanged();
                    StateName = stateList.get(0).getName();
                    StateCode = stateList.get(0).getCode();


                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                        Toast.makeText(AddCampaign.this, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StateRespose> call, Throwable t) {

                Toast.makeText(AddCampaign.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_press:
                onBackPressed();
                break;
            case R.id.from_date:
                Globals.selectDate(this, binding.fromDate);
                break;
            case R.id.to_date:
                Globals.selectDate(this, binding.toDate);
                break;
            case R.id.oppfrom_date:
                Globals.selectDate(this, binding.oppfromDate);
                break;
            case R.id.oppto_date:
                Globals.selectDate(this, binding.opptoDate);
                break;
            case R.id.customerfrom_date:
                Globals.selectDate(this, binding.customerfromDate);
                break;
            case R.id.customerto_date:
                Globals.selectDate(this, binding.customertoDate);
                break;
            case R.id.create:

                if (validation(CampaignAccess, binding.companyname, binding.description)) {
                    AddCampaignModel campaignModel = new AddCampaignModel();
                    campaignModel.setCampaignSetName(binding.companyname.getText().toString());
                    campaignModel.setCampaignAccess(CampaignAccess);
                    campaignModel.setDescription(binding.description.getText().toString());
                    campaignModel.setLeadSource(sourcetype);
                    campaignModel.setLeadPriority(leadtype);
                    campaignModel.setLeadFromDate(binding.fromDate.getText().toString());
                    campaignModel.setLeadToDate(binding.toDate.getText().toString());
                    campaignModel.setBPFromDate(binding.customerfromDate.getText().toString());
                    campaignModel.setBPToDate(binding.customertoDate.getText().toString());
                    campaignModel.setOppFromDate(binding.oppfromDate.getText().toString());
                    campaignModel.setOppToDate(binding.opptoDate.getText().toString());
                    campaignModel.setLeadStatus(status);
                    campaignModel.setOppType(OppType);
                    campaignModel.setBPType(CustomerType);
                    campaignModel.setBPState(StateName);
                    campaignModel.setBPStateCode(StateCode);
                    campaignModel.setBPCountry(CountryName);
                    campaignModel.setBPCountryCode(Countrycode);
                    campaignModel.setCreateDate(Globals.getTodaysDate());
                    campaignModel.setCreateTime(Globals.getTCurrentTime());
                    campaignModel.setBPIndustry(industryCode);
                    campaignModel.setOppSalePerson(OppsalesEmployeeCode);
                    campaignModel.setBPSalePerson(CustomersalesEmployeeCode);
                    campaignModel.setCreateBy(Globals.TeamSalesEmployeCode);
                    campaignModel.setCampaignSetOwner(Globals.TeamSalesEmployeCode);
                    campaignModel.setMemberList("");
                    campaignModel.setOppStage("");
                    campaignModel.setStatus(0);
                    campaignModel.setAllBP(binding.allBp.isChecked() ? 1 : 0);
                    campaignModel.setAllLead(binding.allLead.isChecked() ? 1 : 0);
                    campaignModel.setAllOpp(binding.allOpp.isChecked() ? 1 : 0);

                    if (Globals.checkInternet(AddCampaign.this))
                        createnewCampaign(campaignModel);


                }


                break;
        }
    }

    private void createnewCampaign(AddCampaignModel campaignModel) {
        Call<CampaignResponse> call = NewApiClient.getInstance().getApiService().createCampaign(campaignModel);
        call.enqueue(new Callback<CampaignResponse>() {
            @Override
            public void onResponse(Call<CampaignResponse> call, Response<CampaignResponse> response) {

                if (response.code() == 200) {

                    Toasty.success(AddCampaign.this, "Posted Successfull", Toasty.LENGTH_LONG).show();
                    onBackPressed();


                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                        Toast.makeText(AddCampaign.this, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CampaignResponse> call, Throwable t) {

                Toast.makeText(AddCampaign.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean validation(String campaignAccess, EditText companyname, EditText description) {
        if (campaignAccess.equalsIgnoreCase("--None--")) {
            Toasty.warning(this, "Select Campaign Access", Toasty.LENGTH_SHORT).show();
            return false;
        } else if (companyname.length() == 0) {
            Toasty.warning(this, "Enter Campaign Set Name", Toasty.LENGTH_SHORT).show();
            return false;
        } else if (description.length() == 0) {
            Toasty.warning(this, "Enter Description", Toasty.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
