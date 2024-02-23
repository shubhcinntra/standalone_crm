package com.cinntra.standalone.activities;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.standalone.R;
import com.cinntra.standalone.adapters.BPTypeSpinnerAdapter;
import com.cinntra.standalone.adapters.CategoryAdapter;
import com.cinntra.standalone.adapters.ContactPersonAdapter;
import com.cinntra.standalone.adapters.LeadTypeAdapter;
import com.cinntra.standalone.adapters.SalesEmployeeAdapter;
import com.cinntra.standalone.databinding.AddOpportunityBinding;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.globals.MainBaseActivity;
import com.cinntra.standalone.interfaces.DatabaseClick;
import com.cinntra.standalone.model.BusinessPartnerData;
import com.cinntra.standalone.model.ContactPerson;
import com.cinntra.standalone.model.ContactPersonData;
import com.cinntra.standalone.model.EmployeeValue;
import com.cinntra.standalone.model.ItemCategoryResponse;
import com.cinntra.standalone.model.LeadTypeData;
import com.cinntra.standalone.model.LeadTypeResponse;
import com.cinntra.standalone.model.NewOppResponse;
import com.cinntra.standalone.model.OwnerItem;
import com.cinntra.standalone.model.QuotationResponse;
import com.cinntra.standalone.model.SalesEmployeeItem;
import com.cinntra.standalone.model.SalesOpportunitiesLines;
import com.cinntra.standalone.model.UTypeData;
import com.cinntra.standalone.newapimodel.AddOpportunityModel;
import com.cinntra.standalone.newapimodel.LeadResponse;
import com.cinntra.standalone.newapimodel.LeadValue;
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


public class AddOpportunityActivity extends MainBaseActivity implements View.OnClickListener, DatabaseClick {

    public static int PARTNERCODE = 100;
    public static int OWNERCODE   = 1001;
    public static int LeadCode = 101;
    public  int ITEMSVIEWCODE   = 10000;

    Activity act;
    int salesEmployeeCode = 0;
    String salesEmployeename = "";
    String ContactPersonName     = "";
    String ContactPersonCode     = "";

    String stagesCode     = "No";
    String TYPE           = "";
    String LEAD_SOURCE    = "";

//     @BindView(R.id.head_title)
//     TextView head_title;
//     @BindView(R.id.back_press)
//     RelativeLayout back_press;
//     @BindView(R.id.opportunity_name_value)
//     EditText opportunity_name_value;
//     @BindView(R.id.business_partner_value)
//     EditText business_partner_value;
//     @BindView(R.id.contact_person_spinner)
//     Spinner contact_person_spinner;
//     @BindView(R.id.close_date_value)
//     EditText close_date_value;
//     @BindView(R.id.opportunity_owner_value)
//     EditText opportunity_owner_value;
//     @BindView(R.id.sales_employee_spinner)
//     Spinner sales_employee_spinner;
//     @BindView(R.id.type_spinner)
//     Spinner type_spinner;
//     @BindView(R.id.probability_value)
//     EditText probability_value;
//     @BindView(R.id.potential_amount_value)
//     EditText potential_amount_value;
//     @BindView(R.id.lead_source_spinner)
//     Spinner lead_source_spinner;
//     @BindView(R.id.stage_spinner)
//     Spinner stage_spinner;
//     @BindView(R.id.description_value)
//     EditText description_value;
//     @BindView(R.id.bussinessPartner)
//     RelativeLayout bussinessPartner;
//     @BindView(R.id.owener)
//     RelativeLayout owener;
//     @BindView(R.id.submit_button)
//     Button submit_button;
//     @BindView(R.id.startDate)
//     RelativeLayout startDate;
//     @BindView(R.id.loader)
//     ProgressBar loader;
//     @BindView(R.id.start_date_value)
//     EditText start_date_value;
//     @BindView(R.id.startcalender)
//     ImageView startcalender;
//    @BindView(R.id.closeDate)
//    RelativeLayout closeDate;
//    @BindView(R.id.closeCalender)
//    ImageView closeCalender;
//    @BindView(R.id.lead_view)
//    RelativeLayout lead_view;
//    @BindView(R.id.lead_value)
//    EditText lead_value;
//    @BindView(R.id.itemCount)
//    TextView itemCount;
//    @BindView(R.id.item_frame)
//    RelativeLayout item_frame;

    String DataOwnershipfield = "";
    String LeadID = "0";
    String CardName = "";
    AddOpportunityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding=AddOpportunityBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
  //  ButterKnife.bind(this);
    act = AddOpportunityActivity.this;
        Globals.SelectedItems.clear();
    setDefaults();
    eventManager();
    if(Globals.checkInternet(this)){
        callSalessApi();
    }

        binding.itemFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Globals.SelectedItems.size()==0) {
                    /*Globals.ItemType = "Paid";
                    openCategorydailog();*/
                    Intent intent = new Intent(AddOpportunityActivity.this, ItemsList.class);
                    intent.putExtra("CategoryID",0);
                    startActivityForResult(intent, ITEMSVIEWCODE);
                }
                else {
                    Intent intent = new Intent(AddOpportunityActivity.this, SelectedItems.class);
                    intent.putExtra("FromWhere","addQt");
                    startActivityForResult(intent, ITEMSVIEWCODE);
                }
            }
        });


     }

    Dialog TaxListdialog;
    private void openCategorydailog() {
        RelativeLayout backPress;
        TextView head_title;
        RecyclerView recyclerview;
        ProgressBar loader;

        TaxListdialog = new Dialog(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View custom_dialog =layoutInflater.inflate(R.layout.taxes_alert,null);
        recyclerview = custom_dialog.findViewById(R.id.recyclerview);
        backPress    = custom_dialog.findViewById(R.id.back_press);
        head_title   = custom_dialog.findViewById(R.id.head_title);
        loader       = custom_dialog.findViewById(R.id.loader);
        head_title.setText(getResources().getString(R.string.select_tax));
        TaxListdialog.setContentView(custom_dialog);
        TaxListdialog.getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));
        TaxListdialog.show();

        backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaxListdialog.dismiss();
            }
        });


        EmployeeValue opportunityValue = new EmployeeValue();
        opportunityValue.setSalesEmployeeCode(Prefs.getString(Globals.SalesEmployeeCode,""));
        Call<ItemCategoryResponse> call = NewApiClient.getInstance().getApiService().getAllCategory(opportunityValue);
        call.enqueue(new Callback<ItemCategoryResponse>() {
            @Override
            public void onResponse(Call<ItemCategoryResponse> call, Response<ItemCategoryResponse> response) {
                binding.loader.loader.setVisibility(View.GONE);
                if(response.code()==200)
                {

                    CategoryAdapter adapter = new CategoryAdapter(AddOpportunityActivity.this, response.body().getData(),TaxListdialog);
                    recyclerview.setLayoutManager(new LinearLayoutManager(AddOpportunityActivity.this, RecyclerView.VERTICAL,false));
                    recyclerview.setAdapter(adapter);
                }
                else
                {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s =response.errorBody().string();
                        mError= gson.fromJson(s,QuotationResponse.class);
                        Toast.makeText(AddOpportunityActivity.this, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ItemCategoryResponse> call, Throwable t) {
                binding.loader.loader.setVisibility(View.GONE);
                Toast.makeText(AddOpportunityActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    List<UTypeData> utypelist = new ArrayList<>();
    private void callUTypeApi() {
        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getOPpTypeList().observe(this, new Observer<List<UTypeData>>() {
            @Override
            public void onChanged(@Nullable List<UTypeData> itemsList) {
                if(itemsList == null || itemsList.size()== 0){
                    Globals.setmessage( act);
                }else {
                    utypelist = itemsList;
                    binding.typeSpinner.setAdapter(new BPTypeSpinnerAdapter(act,itemsList));
                    TYPE = utypelist.get(0).getId().toString();

                }
            }
        });
        callSourceApi();
    }
    ArrayList<LeadTypeData> sourceData = new ArrayList<>();
    private void callSourceApi() {

//
//        Call<LeadTypeResponse> call = NewApiClient.getInstance().getApiService().getsourceType();
//        call.enqueue(new Callback<LeadTypeResponse>() {
//            @Override
//            public void onResponse(Call<LeadTypeResponse> call, Response<LeadTypeResponse> response) {
//
//                if(response.code()==200)
//                {
//                    sourceData.clear();
//                   /* LeadTypeData ld = new LeadTypeData();
//                    ld.setName("Choose Select");
//                    sourceData.add(ld);*/
//                    sourceData.addAll(response.body().getData());
//                    lead_source_spinner.setAdapter(new LeadTypeAdapter(AddOpportunityActivity.this,sourceData));
//                    LEAD_SOURCE = sourceData.get(0).getName();
//                }
//                else
//                {
//                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
//                    Gson gson = new GsonBuilder().create();
//                    LeadResponse mError = new LeadResponse();
//                    try {
//                        String s =response.errorBody().string();
//                        mError= gson.fromJson(s, LeadResponse.class);
//                        Toast.makeText(AddOpportunityActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
//                    } catch (IOException e) {
//                        //handle failure to read error
//                    }
//                }
//
//            }
//            @Override
//            public void onFailure(Call<LeadTypeResponse> call, Throwable t) {
//                loader.setVisibility(View.GONE);
//                Toast.makeText(AddOpportunityActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

        sourceData.clear();
        sourceData.addAll(MainActivity.leadSourceListFromLocal);
       // Toast.makeText(act, sourceData.get(0).getName(), Toast.LENGTH_SHORT).show();
        binding.leadSourceSpinner.setAdapter(new LeadTypeAdapter(AddOpportunityActivity.this, sourceData));
        LEAD_SOURCE = sourceData.get(0).getName();


    }
    private void eventManager() {
        binding.salesEmployeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
          if (salesEmployeeItemList.size() > 0) {
              salesEmployeename = salesEmployeeItemList.get(position).getSalesEmployeeName();
              salesEmployeeCode = Integer.valueOf(salesEmployeeItemList.get(position).getSalesEmployeeCode());
          }
      }
      @Override
      public void onNothingSelected(AdapterView<?> parent)

          {
              salesEmployeename = salesEmployeeItemList.get(0).getSalesEmployeeName();
              salesEmployeeCode = Integer.valueOf(salesEmployeeItemList.get(0).getSalesEmployeeCode());

          }
        });

        binding.typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
          {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             if(utypelist.size()>0)
                TYPE = utypelist.get(position).getId().toString();
            }
         @Override
         public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.leadSourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
          {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(sourceData.size()>0){
                  //  LEAD_SOURCE = sourceData.get(position).toString();
                    LEAD_SOURCE=sourceData.get(position).getName().toString();
                  //  Toast.makeText(act, "Spinner"+LEAD_SOURCE, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                LEAD_SOURCE = sourceData.get(0).getName().toString();
            }
        });

        binding.contactPersonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(ContactEmployeesList.size()>0){
                    ContactPersonName = ContactEmployeesList.get(position).getFirstName();
                    ContactPersonCode = ContactEmployeesList.get(position).getInternalCode();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ContactPersonName = ContactEmployeesList.get(0).getFirstName();
                ContactPersonCode = ContactEmployeesList.get(0).getInternalCode();
            }
        });

    }


    private void setDefaults()
            {
     binding.headerBottomRounded.headTitle.setText(getResources().getString(R.string.add_opportunity));
                binding.headerBottomRounded.backPress.setOnClickListener(this);
     binding.bussinessPartner.setOnClickListener(this);
     binding.owener.setOnClickListener(this);
     binding.submitButton.setOnClickListener(this);
     binding.businessPartnerValue.setOnClickListener(this);
     binding.startDateValue.setOnClickListener(this);
     binding.opportunityOwnerValue.setOnClickListener(this);
     binding.startDate.setOnClickListener(this);
     binding.startcalender.setOnClickListener(this);
     binding.closeDate.setOnClickListener(this);
     binding.closeDateValue.setOnClickListener(this);
     binding.startcalender.setOnClickListener(this);
    binding.leadView.setOnClickListener(this);
    binding.leadValue.setOnClickListener(this);
     binding.startDateValue.setText(Globals.getTodaysDate());

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
     if (requestCode == PARTNERCODE&&resultCode == RESULT_OK) {

       BusinessPartnerData customerItem = (BusinessPartnerData) data.getSerializableExtra(Globals.CustomerItemData);
       setData(customerItem);

        }
     else  if(requestCode == OWNERCODE&&resultCode == RESULT_OK)
     {
    OwnerItem ownerItem = (OwnerItem) data.getSerializableExtra(Globals.OwnerItemData);
    binding.opportunityOwnerValue.setText(ownerItem.getFirstName()+" "+ownerItem.getMiddleName()+" "+ownerItem.getLastName());
         DataOwnershipfield = ownerItem.getEmployeeID();
       }else if(requestCode==LeadCode &&resultCode==RESULT_OK)
     {
         LeadValue leadValue =data.getParcelableExtra(Globals.Lead_Data);
         binding.leadValue.setText(leadValue.getCompanyName());
         LeadID = leadValue.getId().toString();
     }
    }

       @Override
       public void onClick(View v)
         {
    switch (v.getId())
           {
    case R.id.back_press:
        finish();
        break;
        case R.id.startcalender:
               case R.id.start_date_value:
               case R.id.startDate:
                   startDate();
          break;
               case R.id.closeCalender:
               case R.id.close_date_value:
               case R.id.closeDate:
                   closetDate();
            break;

               case R.id.bussinessPartner:
        case R.id.business_partner_value:
           /* if(LeadID.isEmpty()){
                Toasty.warning(this,"Select Lead First",Toasty.LENGTH_SHORT).show();
            }else {

            }*/
            selectBPartner();
        break;
        case R.id.lead_value:
               case R.id.lead_view:
                   Prefs.putString(Globals.BussinessPageType,"AddOpportunityLead");
                   Intent i = new Intent(AddOpportunityActivity.this, LeadsActivity.class);
                   startActivityForResult(i,LeadCode);
                   break;
        case R.id.owener:
        case R.id.opportunity_owner_value:

            Intent ii = new Intent(AddOpportunityActivity.this,OwnerList.class);
            startActivityForResult(ii,OWNERCODE);
            break;
        case R.id.submit_button:

             String cardValue    = binding.businessPartnerValue.getText().toString().trim();
             String       remark = binding.descriptionValue.getText().toString().trim();
             if(validation(cardValue,salesEmployeeCode,binding.potentialAmountValue.getText().toString().trim(),remark))
                {
             jsonlist.clear();
             SalesOpportunitiesLines dc = new SalesOpportunitiesLines();
             dc.setSalesPerson(salesEmployeeCode);
             dc.setDocumentType("bodt_MinusOne");
             dc.setMaxLocalTotal(Float.valueOf(binding.potentialAmountValue.getText().toString().trim()));
             dc.setStageKey("2");
             jsonlist.add(dc);

             AddOpportunityModel obj = new AddOpportunityModel();
             obj.setOpportunityName(binding.opportunityNameValue.getText().toString().trim());
             obj.setClosingDate(binding.closeDateValue.getText().toString().trim());
             obj.setPredictedClosingDate(binding.closeDateValue.getText().toString().trim());
             obj.setUType(TYPE);
             obj.setCustomerName(CardName);
             obj.setUFav("N");
             obj.setULsource(LEAD_SOURCE);
             obj.setUProblty(binding.probabilityValue.getText().toString().trim());
            // obj.setDataOwnershipfield(DataOwnershipfield);
             obj.setCardCode(cardValue); //cardcode
             obj.setSalesPerson(String.valueOf(salesEmployeeCode));
             obj.setContactPerson(ContactPersonCode);
             obj.setMaxLocalTotal(binding.potentialAmountValue.getText().toString().trim());//Potential Ammount
             obj.setRemarks(remark);
             obj.setMaxSystemTotal("0.7576");
             obj.setStatus("sos_Open");
             obj.setReasonForClosing("None");
             obj.setTotalAmountLocal("5.0");
             obj.setTotalAmounSystem("0.075");
             obj.setCurrentStageNo("2");
             obj.setIndustry("None");
             obj.setLinkedDocumentType("None");
             obj.setStatusRemarks("None");
             obj.setProjectCode("None");
             obj.setClosingType("sos_Days");
             obj.setOpportunityType("boOpSales");
             obj.setUpdateDate(Globals.getTodaysDatervrsfrmt());
             obj.setUpdateTime(Globals.getTCurrentTime());
             obj.setSalesOpportunitiesLines(jsonlist);
             obj.setSource("None");
             obj.setDataOwnershipfield(String.valueOf(salesEmployeeCode));
            obj.setSalesPersonName(salesEmployeename);
            obj.setContactPersonName(ContactPersonName);
            obj.setDataOwnershipName(salesEmployeename);
             obj.setStartDate(Globals.getTodaysDatervrsfrmt());
             obj.setU_LEADID(LeadID);
             obj.setU_LEADNM(binding.leadValue.getText().toString());
             obj.setOppItem(Globals.SelectedItems);
             if(Globals.checkInternet(getApplicationContext()))
                 addQuotation(obj);
                 }


            break;

          }
       }

    private void startDate()
        {
       Globals.selectDate(AddOpportunityActivity.this,binding.startDateValue);
        }
    private void closetDate() {
        Globals.selectDate(AddOpportunityActivity.this,binding.closeDateValue);
    }


    @Override
    protected void onResume() {
        super.onResume();
        binding.itemCount.setText("Item ("+Globals.SelectedItems.size()+")");

    }

    private void selectBPartner()
         {
        Prefs.putString(Globals.BussinessPageType,"AddOpportunity");
        Intent i = new Intent(AddOpportunityActivity.this, BussinessPartners.class);
        startActivityForResult(i,PARTNERCODE);
           }

     ArrayList<SalesOpportunitiesLines> jsonlist = new ArrayList<>();
      private ArrayList<ContactPersonData>   ContactEmployeesList;
      private ContactPersonAdapter  contactPersonAdapter;
     private void setData(BusinessPartnerData customerItem)
         {

          ContactEmployeesList = new ArrayList<>();
             CardName= customerItem.getCardName();
             callContactEmployeeApi(customerItem.getCardCode());




          binding.businessPartnerValue.setText(customerItem.getCardCode());

          if(ContactEmployeesList.size()>0)
             ContactPersonCode = ContactEmployeesList.get(0).getInternalCode();

           }

    private void callContactEmployeeApi(String id) {
         ContactPersonData contactPersonData = new ContactPersonData();
         contactPersonData.setCardCode(id);
        binding.loader.loader.setVisibility(View.VISIBLE);
        Call<ContactPerson> call = NewApiClient.getInstance().getApiService().contactemplist(contactPersonData);
        call.enqueue(new Callback<ContactPerson>() {
            @Override
            public void onResponse(Call<ContactPerson> call, Response<ContactPerson> response) {
                binding.loader.loader.setVisibility(View.GONE);
                if(response.code()==200)
                {
                    if(response.body().getData().size()>0) {
                        ContactEmployeesList.clear();
                        ContactEmployeesList.addAll(response.body().getData());
                    }else{

                            ContactPersonData contactEmployees = new ContactPersonData();
                            contactEmployees.setFirstName("No Contact Person");
                            contactEmployees.setInternalCode("-1");

                            ContactEmployeesList.add(contactEmployees);

                    }
                    contactPersonAdapter =new ContactPersonAdapter(AddOpportunityActivity.this,ContactEmployeesList);
                    binding.contactPersonSpinner.setAdapter(contactPersonAdapter);

                }
                else
                {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s =response.errorBody().string();
                        mError= gson.fromJson(s,QuotationResponse.class);
                        Toast.makeText(AddOpportunityActivity.this, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ContactPerson> call, Throwable t) {
                binding.loader.loader.setVisibility(View.GONE);
                Toast.makeText(AddOpportunityActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }




    public List<SalesEmployeeItem> salesEmployeeItemList = new ArrayList<>();
    private void callSalessApi()
      {
    ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
    model.getSalesEmployeeList().observe(this, new Observer<List<SalesEmployeeItem>>() {
     @Override
     public void onChanged(@Nullable List<SalesEmployeeItem> itemsList) {
         if(itemsList == null || itemsList.size() == 0){
             Globals.setmessage(getApplicationContext());
         }else{
             salesEmployeeItemList = itemsList;
             binding.salesEmployeeSpinner.setAdapter(new SalesEmployeeAdapter(AddOpportunityActivity.this,itemsList));
             salesEmployeename = salesEmployeeItemList.get(0).getSalesEmployeeName();
             salesEmployeeCode = Integer.valueOf(salesEmployeeItemList.get(0).getSalesEmployeeCode());

         }
            }
        });
          callUTypeApi();
     }


    private void addQuotation(AddOpportunityModel in)
       {
         binding.loader.loader.setVisibility(View.VISIBLE);
        Call<NewOppResponse> call = NewApiClient.getInstance().getApiService().createopportunity(in);
        call.enqueue(new Callback<NewOppResponse>() {
            @Override
            public void onResponse(Call<NewOppResponse> call, Response<NewOppResponse> response) {
                binding.loader.loader.setVisibility(View.GONE);
                if(response.code()==200)
                {

                    if(response.body().getStatus().equalsIgnoreCase("200")){
                        Toasty.success(AddOpportunityActivity.this, "Add Successfully", Toast.LENGTH_LONG).show();
                        Globals.SelectedItems.clear();
                        onBackPressed();
                    }else{
                        Toasty.warning(AddOpportunityActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();

                    }
                }
                else
                {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s =response.errorBody().string();
                        mError= gson.fromJson(s,QuotationResponse.class);
                        Toast.makeText(AddOpportunityActivity.this, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<NewOppResponse> call, Throwable t) {
                binding.loader.loader.setVisibility(View.GONE);
                Toasty.error(AddOpportunityActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validation(
    String cardCode,int
    salesEmployeeCode,
    String potentialAmount,String remark)
        {
   if(cardCode.isEmpty())
      {
    Globals.showMessage(act,getString(R.string.select_bp));
    return false;
      }

   else if(ContactPersonCode.equalsIgnoreCase("-1")){
       Globals.showMessage(act,getString(R.string.enter_cp));
       return false;
   }

   else if(potentialAmount.isEmpty()){
       Globals.showMessage(act,getString(R.string.potential_amnt_error));
       return false;
   }

   else if(binding.opportunityNameValue.getText().toString().trim().length()==0){
       binding.opportunityNameValue.requestFocus();
       binding.opportunityNameValue.setError(getString(R.string.enter_opp));
       Globals.showMessage(act,getString(R.string.enter_opp));
       return false;
   }
   else if(binding.closeDateValue.getText().toString().trim().length()==0){
       Globals.showMessage(act,"Enter closing date");
       return false;
   }
   else if(TYPE.equalsIgnoreCase("-None-")){
       Globals.showMessage(act,getString(R.string.enter_tye));
       return false;
   }
   else if(LEAD_SOURCE.equalsIgnoreCase("-None-")){
       Globals.showMessage(act,getString(R.string.enter_lead_source));
       return false;
   }

   else if(salesEmployeeCode==0){
       Globals.showMessage(act,getString(R.string.enter_sp));
       return false;
   }

   else if(remark.isEmpty()){
       binding.descriptionValue.requestFocus();
       binding.descriptionValue.setError(getString(R.string.remark_error));
       Globals.showMessage(act,getString(R.string.remark_error));
       return false;
   }

    return true;
     }


    @Override
    public void onClick(int po) {
        Intent intent = new Intent(this, ItemsList.class);
        intent.putExtra("CategoryID",po);
        startActivityForResult(intent, ITEMSVIEWCODE);
    }
}