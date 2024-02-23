package com.cinntra.standalone.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.cinntra.standalone.R;
import com.cinntra.standalone.adapters.ContactPersonAdapter;
import com.cinntra.standalone.adapters.SalesEmployeeAdapter;
import com.cinntra.standalone.databinding.AddQuotationBinding;
import com.cinntra.standalone.fragments.AddQuotationForm_One_Fragment;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.globals.MainBaseActivity;
import com.cinntra.standalone.interfaces.SubmitQuotation;
import com.cinntra.standalone.model.AddQuotation;
import com.cinntra.standalone.model.BusinessPartnerData;
import com.cinntra.standalone.model.ContactPerson;
import com.cinntra.standalone.model.ContactPersonData;
import com.cinntra.standalone.model.DocumentLines;
import com.cinntra.standalone.model.QuotationResponse;
import com.cinntra.standalone.model.SalesEmployeeItem;
import com.cinntra.standalone.newapimodel.NewOpportunityRespose;
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


public class AddQuotationAct extends MainBaseActivity implements View.OnClickListener, SubmitQuotation {
     public static int PARTNERCODE = 10000;
     public static int OPPCODE = 1001;
     public static int ITEMSCODE   = 1000;

//     @BindView(R.id.head_title)
//     TextView head_title;
//     @BindView(R.id.back_press)
//     RelativeLayout back_press;
//
//    @BindView(R.id.postingDate)
//    LinearLayout postingDate;
//    @BindView(R.id.posting_value)
//    EditText posting_value;
//    @BindView(R.id.valid_till_value)
//    EditText valid_till_value;
//    @BindView(R.id.validDate)
//    LinearLayout validDate;
//    @BindView(R.id.document_date_value)
//    EditText document_date_value;
//    @BindView(R.id.documentDate)
//    LinearLayout documentDate;
//    @BindView(R.id.remark_value)
//    EditText remark_value;
//    @BindView(R.id.opportunity_name_value)
//    EditText opportunity_name_value;
//
//
//    @BindView(R.id.loader)
//    ProgressBar loader;
//    @BindView(R.id.submit)
//    Button submit;
//    @BindView(R.id.postCal)
//    ImageView postCal;
//    @BindView(R.id.validCal)
//    ImageView validCal;
//    @BindView(R.id.docCal)
//    ImageView docCal;
//    @BindView(R.id.contact_person_spinner)
//    Spinner contact_person_spinner;
//    @BindView(R.id.bpView)
//    LinearLayout bpView;
//    @BindView(R.id.business_partner_value)
//    EditText business_partner_value;
//    @BindView(R.id.bussinessPartner)
//    RelativeLayout bussinessPartner;
//    @BindView(R.id.opp_view)
//    RelativeLayout opp_view;
//    @BindView(R.id.salesemployee_spinner)
//    Spinner sales_employee_spinner;
//    @BindView(R.id.quo_namevalue)
//    EditText quo_namevalue;

    public static String CardValue;
    public static String CardName;
    public static String salePCode;
    public static AddQuotation addQuotationObj;
    AppCompatActivity act;
    String OPPID = "";
    public static String salesEmployeeCode = "";
    List<SalesEmployeeItem> salesEmployeeItemList = new ArrayList<>();
    NewOpportunityRespose oppdata;

    AddQuotationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    act = AddQuotationAct.this;
    binding=AddQuotationBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
        addQuotationObj = new AddQuotation();
  //  ButterKnife.bind(this);
        callSalessApi();
        setDefaults();
        if(!Prefs.getString(Globals.QuotationListing,"").equalsIgnoreCase("null")){
            oppdata = Globals.opportunityData.get(0);
            setOppData(oppdata);
        }



    
     }

    @SuppressLint("ResourceType")
    private void  setOppData(NewOpportunityRespose oppdata) {
        binding.quotationGeneralContent.businessPartnerValue.setClickable(false);
        binding.quotationGeneralContent.businessPartnerValue.setEnabled(false);
        binding.quotationGeneralContent.businessPartnerValue.setTextColor(Color.parseColor(getString(R.color.black)));
        binding.quotationGeneralContent.bussinessPartner.setClickable(false);
        binding.quotationGeneralContent.bussinessPartner.setEnabled(false);
        binding.quotationGeneralContent.opportunityNameValue.setClickable(false);
        binding.quotationGeneralContent.opportunityNameValue.setEnabled(false);
        binding.quotationGeneralContent.opportunityNameValue.setTextColor(Color.parseColor(getString(R.color.black)));
        binding.quotationGeneralContent.oppView.setClickable(false);
        binding.quotationGeneralContent.oppView.setEnabled(false);
        OPPID = oppdata.getId();
        binding.quotationGeneralContent.opportunityNameValue.setText(oppdata.getOpportunityName());
        binding.quotationGeneralContent.businessPartnerValue.setText(oppdata.getCardCode());

        CardValue = oppdata.getCardCode();
        CardName = oppdata.getCustomerName();
        salePCode = oppdata.getContactPerson();
        salesEmployeeCode = oppdata.getSalesPerson();
        callContactEmployeeApi(oppdata.getCardCode());

        addQuotationObj.setCardCode(CardValue);
        addQuotationObj.setCardName(CardName);
        addQuotationObj.setSalesPerson(salePCode);
        addQuotationObj.setSalesPersonCode(salesEmployeeCode);
    }

    private void callSalessApi()
    {
        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getSalesEmployeeList().observe(this, new Observer<List<SalesEmployeeItem>>()
        {
            @Override
            public void onChanged(@Nullable List<SalesEmployeeItem> itemsList) {
                if(itemsList == null || itemsList.size() == 0){
                    Globals.setmessage(getApplicationContext());
                }else {
                    salesEmployeeItemList = itemsList;
                    binding.quotationGeneralContent.salesemployeeSpinner.setAdapter(new SalesEmployeeAdapter(getApplicationContext(),itemsList));
                   // Globals.getSelectedSalesP(salesEmployeeItemList,salesEmployeeCode);
                    if(!Prefs.getString(Globals.QuotationListing,"").equalsIgnoreCase("null"))
                    binding.quotationGeneralContent.salesemployeeSpinner.setSelection(Globals.getSelectedSalesP(salesEmployeeItemList,salesEmployeeCode));
                    salesEmployeeCode = salesEmployeeItemList.get(0).getSalesEmployeeCode();


                }
            }
        });
    }

    private void setDefaults() {
        binding.quotationGeneralContent.bpView.setVisibility(View.VISIBLE);
        binding.headerBottomRounded.headTitle.setText(getResources().getString(R.string.add_quotation));
        binding.headerBottomRounded.backPress.setOnClickListener(this);
        binding.quotationGeneralContent.postingDate.setOnClickListener(this);
        binding.quotationGeneralContent.postingValue.setOnClickListener(this);
        binding.quotationGeneralContent.postCal.setOnClickListener(this);
        binding.quotationGeneralContent.validDate.setOnClickListener(this);
        binding.quotationGeneralContent.validTillValue.setOnClickListener(this);
        binding.quotationGeneralContent.validCal.setOnClickListener(this);
        binding.quotationGeneralContent.documentDate.setOnClickListener(this);
        binding.quotationGeneralContent.documentDateValue.setOnClickListener(this);
        binding.quotationGeneralContent.docCal.setOnClickListener(this);
        binding.quotationGeneralContent.bussinessPartner.setOnClickListener(this);
        binding.quotationGeneralContent.businessPartnerValue.setOnClickListener(this);
        binding.quotationGeneralContent.opportunityNameValue.setOnClickListener(this);
        binding.quotationGeneralContent.oppView.setOnClickListener(this);


        binding.quotationGeneralContent.submit.setOnClickListener(this);
    }
    private void selectBPartner() {
        Prefs.putString(Globals.BussinessPageType,"Quotation");
        Intent i = new Intent(act, BussinessPartners.class);
        startActivityForResult(i,PARTNERCODE);
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
    case R.id.back_press:
        if(getFragmentManager().getBackStackEntryCount()>0){
            getFragmentManager().popBackStack();
        }else {
            Globals.opp = null;
            onBackPressed();
        }
        break;
        case R.id.postingDate:
        case R.id.postCal:
        case R.id.posting_value:
            Globals.selectDate(act,binding.quotationGeneralContent.postingValue);
            break;
        case R.id.validDate:
        case R.id.validCal:
        case R.id.valid_till_value:
            Globals.selectDate(act,binding.quotationGeneralContent.validTillValue);
            break;
        case R.id.documentDate:
        case R.id.document_date_value:
        case R.id.docCal:
            Globals.selectDate(act,binding.quotationGeneralContent.documentDateValue);
            break;
        case R.id.bussinessPartner:
        case R.id.business_partner_value:
            selectBPartner();
            break;
        case R.id. opportunity_name_value:
        case R.id.opp_view:
            selectOpportunity();
            break;

        case R.id.itemsView:
            if(Globals.SelectedItems.size()==0) {
         Intent intent = new Intent(AddQuotationAct.this, ItemsList.class);
         startActivityForResult(intent, ITEMSCODE);
            }
            else {
         Intent intent = new Intent(AddQuotationAct.this, SelectedItems.class);
         intent.putExtra("FromWhere","AddQt");
         startActivityForResult(intent, ITEMSCODE);
            }
            break;
        case R.id.submit:
            String oppname = binding.quotationGeneralContent.opportunityNameValue.getText().toString().trim();
            String poDate  = binding.quotationGeneralContent.postingValue.getText().toString().trim();
            String vDate   = binding.quotationGeneralContent.validTillValue.getText().toString().trim();
            String docDate = binding.quotationGeneralContent.documentDateValue.getText().toString().trim();
            String remark  = binding.quotationGeneralContent.remarkValue.getText().toString().trim();
            if(valiadtion(oppname,contactPersonCode,poDate,vDate,docDate,remark)) {
                if(!Prefs.getString(Globals.SelectedBranch,"").isEmpty())
               addQuotationObj.setBPLName(Prefs.getString(Globals.SelectedBranch,""));
                if(!Prefs.getString(Globals.SelectedBranchID,"").isEmpty())
               addQuotationObj.setBPL_IDAssignedToInvoice(Prefs.getString(Globals.SelectedBranchID,""));
                addQuotationObj.setOpportunityName(oppname);
                addQuotationObj.setSalesPerson(contactPersonCode);
                addQuotationObj.setSalesPersonCode(salesEmployeeCode);
                addQuotationObj.setPostingDate(poDate);
                addQuotationObj.setValidDate(vDate);
                addQuotationObj.setDocumentDate(docDate);
                addQuotationObj.setRemarks(remark);
                addQuotationObj.setU_OPPID(OPPID);
                addQuotationObj.setUpdateDate(Globals.getTodaysDate());
                addQuotationObj.setUpdateTime(Globals.getTCurrentTime());
                addQuotationObj.setCreateTime(Globals.getTCurrentTime());
                addQuotationObj.setCreateDate(Globals.getTodaysDate());
                addQuotationObj.setU_QUOTNM(binding.quotationGeneralContent.quoNamevalue.getText().toString().trim());

                addQuotationObj.setU_QUOTID("");
                addQuotationObj.setU_OPPID(OPPID);


                AddQuotationForm_One_Fragment addQuotationForm_one_fragment = new AddQuotationForm_One_Fragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.main_edit_qt_frame, addQuotationForm_one_fragment).addToBackStack("");
                fragmentTransaction.commit();
            }
//
            break;
          }
       }

    private void selectOpportunity() {
        Prefs.putString(Globals.SelectOpportnity,"Quotation");
        Intent in = new Intent(act, Opportunities_Pipeline_Activity.class);
        startActivityForResult(in,OPPCODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if(resultCode == RESULT_OK&&requestCode == PARTNERCODE) {
    BusinessPartnerData customerItem = (BusinessPartnerData) data.getSerializableExtra(Globals.CustomerItemData);
    setData(customerItem);
        }
    else if (resultCode == RESULT_OK && requestCode == OPPCODE){
        NewOpportunityRespose oppItem =  Globals.opp;
        if(oppItem!=null) {
//            opportunity_name_value.setText(oppItem.getOpportunityName());
            setOppData(oppItem);
//            OPPID = oppItem.getId();
        }

    }


    }


    String contactPersonCode = "";


    private ArrayList<ContactPersonData>   ContactEmployeesList= new ArrayList<>();
    private ContactPersonAdapter  contactPersonAdapter;
    private void setData(BusinessPartnerData customerItem)
         {



             callContactEmployeeApi(customerItem.getCardCode());
    // PriceListNum = customerItem.getPriceListNum();
     CardValue = customerItem.getCardCode();
     CardName = customerItem.getCardName();
     salePCode = customerItem.getContactPerson();
     salesEmployeeCode = customerItem.getSalesPersonCode().get(0).getSalesEmployeeCode();
     addQuotationObj.setCardCode(CardValue);
     addQuotationObj.setCardName(CardName);
     addQuotationObj.setSalesPerson(salePCode);
     addQuotationObj.setSalesPersonCode(salesEmployeeCode);


        //  ContactEmployeesList = customerItem.getContactPerson();
          binding.quotationGeneralContent.businessPartnerValue.setText(customerItem.getCardCode());


       binding.quotationGeneralContent.contactPersonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               contactPersonCode = ContactEmployeesList.get(position).getInternalCode();
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {
               contactPersonCode = ContactEmployeesList.get(0).getInternalCode();
           }
       });
             binding.quotationGeneralContent.salesemployeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
             {
                 @Override
                 public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                     if(salesEmployeeItemList.size()>0&&position>0)
                         salesEmployeeCode = salesEmployeeItemList.get(position).getSalesEmployeeCode();

                 }
                 @Override
                 public void onNothingSelected(AdapterView<?> parent) {
                     salesEmployeeCode = salesEmployeeItemList.get(0).getSalesEmployeeCode();

                 }
             });


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
                    contactPersonAdapter =new ContactPersonAdapter(act,ContactEmployeesList);
                    binding.quotationGeneralContent.contactPersonSpinner.setAdapter(contactPersonAdapter);
                    if(!Prefs.getString(Globals.QuotationListing,"").equalsIgnoreCase("null"))
                        binding.quotationGeneralContent.contactPersonSpinner.setSelection(Globals.getContactPos(ContactEmployeesList,salePCode));
                    contactPersonCode = ContactEmployeesList.get(0).getInternalCode();

                }
                else
                {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s =response.errorBody().string();
                        mError= gson.fromJson(s,QuotationResponse.class);
                        Toast.makeText(AddQuotationAct.this, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ContactPerson> call, Throwable t) {
                binding.loader.loader.setVisibility(View.GONE);
                Toast.makeText(AddQuotationAct.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    ArrayList<DocumentLines> postlist;
    private ArrayList<DocumentLines> postJson(ArrayList<DocumentLines> list)
          {
        postlist = new ArrayList<>();
          for (int i=0;i<list.size();i++) {
              DocumentLines dc = new DocumentLines();
              if(!Prefs.getString(Globals.SelectedWareHose,"").isEmpty())
                  dc.setWarehouseCode(Prefs.getString(Globals.SelectedWareHose,""));
              dc.setItemCode(Globals.SelectedItems.get(i).getItemCode());
              dc.setQuantity(Globals.SelectedItems.get(i).getQuantity());
              dc.setTaxCode(Globals.SelectedItems.get(i).getTaxCode());//BED+VAT
              dc.setUnitPrice(Globals.SelectedItems.get(i).getUnitPrice());
              dc.setItemDescription(Globals.SelectedItems.get(i).getItemName());
              dc.setDiscountPercent(2.0f);
              postlist.add(dc);
          }

          return postlist;
      }


    private void addQuotation(AddQuotation in,ProgressBar loader)
        {
                        loader.setVisibility(View.VISIBLE);
   Call<QuotationResponse> call = NewApiClient.getInstance().getApiService().addQuotation(in);
   call.enqueue(new Callback<QuotationResponse>() {
      @Override
   public void onResponse(Call<QuotationResponse> call, Response<QuotationResponse> response) {
          loader.setVisibility(View.GONE);
    if(response.code()==200)
          {
             if(response.body().getStatus()==200) {
                 Globals.SelectedItems.clear();
                 Globals.opp = null;

                 Toasty.success(AddQuotationAct.this, "Add Successfully", Toast.LENGTH_LONG).show();
                  finish();
              }else{
                 Toasty.warning(AddQuotationAct.this,response.body().getMessage(),Toast.LENGTH_LONG).show();

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
        Toast.makeText(AddQuotationAct.this, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
         } catch (IOException e) {
       //handle failure to read error
         }
       //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
            }
       }
       @Override
       public void onFailure(Call<QuotationResponse> call, Throwable t) {
           loader.setVisibility(View.GONE);
           Toasty.error(AddQuotationAct.this, t.getMessage(), Toast.LENGTH_SHORT).show();
           }
        });
       }

    @Override
    public void submitQuotaion(ProgressBar loader) {

          addQuotationObj.setDocumentLines(Globals.SelectedItems);
          addQuotation(addQuotationObj,loader);

    }

    private boolean valiadtion(String OppName,String contactPerson,String postDate,String validDate,
                               String DocDate,String remarks){
      if(OppName.isEmpty())
      {
          Globals.showMessage(act,"Enter Opportunity name");
          return false;
      }
      else  if(contactPerson.isEmpty())
      {
          Globals.showMessage(act,"No Contact Person Found");
          return false;
      }
      else  if(validDate.isEmpty())
      {
          Globals.showMessage(act,"Enter Valid date");
          return false;
      }
      else  if(DocDate.isEmpty())
      {
          Globals.showMessage(act,"Enter Document date");
          return false;
      }
      else  if(postDate.isEmpty())
      {
          Globals.showMessage(act,"Enter Posting date");
          return false;
      }
      else  if(remarks.isEmpty())
      {
          Globals.showMessage(act,"Enter Remarks");
          return false;
      }
    return true;
    }
}