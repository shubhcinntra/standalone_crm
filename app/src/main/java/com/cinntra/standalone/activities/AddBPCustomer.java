package com.cinntra.standalone.activities;


import static com.cinntra.standalone.activities.AddOpportunityActivity.LeadCode;
import static com.cinntra.standalone.activities.MainActivity.businessPartnerDataFromLocal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.standalone.R;
import com.cinntra.standalone.adapters.BPTypeSpinnerAdapter;
import com.cinntra.standalone.adapters.CountryAdapter;
import com.cinntra.standalone.adapters.IndustrySpinnerAdapter;
import com.cinntra.standalone.adapters.LeadListAdapter;
import com.cinntra.standalone.adapters.LeadsAdapter;
import com.cinntra.standalone.adapters.PaymentAdapter;
import com.cinntra.standalone.adapters.SalesEmployeeAdapter;
import com.cinntra.standalone.adapters.StateAdapter;
import com.cinntra.standalone.databinding.FragmentAddPartner2Binding;
import com.cinntra.standalone.fragments.Dashboard;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.globals.MainBaseActivity;
import com.cinntra.standalone.model.AddBusinessPartnerData;
import com.cinntra.standalone.model.BPAddress;
import com.cinntra.standalone.model.BusinessPartnerData;
import com.cinntra.standalone.model.ContactEmployees;
import com.cinntra.standalone.model.CountryData;
import com.cinntra.standalone.model.CustomerBusinessRes;
import com.cinntra.standalone.model.IndustryItem;
import com.cinntra.standalone.model.LeadFilter;
import com.cinntra.standalone.model.PayMentTerm;
import com.cinntra.standalone.model.QuotationResponse;
import com.cinntra.standalone.model.SalesEmployeeItem;
import com.cinntra.standalone.model.StateData;
import com.cinntra.standalone.model.StateRespose;
import com.cinntra.standalone.model.UTypeData;
import com.cinntra.standalone.newapimodel.LeadResponse;
import com.cinntra.standalone.newapimodel.LeadValue;
import com.cinntra.standalone.room.BusinessPartnerDatabase;
import com.cinntra.standalone.viewModel.CustomerViewModel;
import com.cinntra.standalone.viewModel.ItemViewModel;
import com.cinntra.standalone.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddBPCustomer extends MainBaseActivity implements View.OnClickListener {

    private static final String TAG = "AddBPCustomer";
    String TYPE = "";
    String U_LeadNM = "";
    String industryCode;
    String shippingType;
//    @BindView(R.id.head_title)
//    TextView head_title;
//    @BindView(R.id.back_press)
//    RelativeLayout back_press;
//    @BindView(R.id.name_value)
//    EditText name_value;
//    @BindView(R.id.contact_owner_value)
//    EditText contact_owner_value;
//    @BindView(R.id.tab_2)
//    LinearLayout tab_2;
//    @BindView(R.id.general)
//    TextView general;
//    @BindView(R.id.contact)
//    TextView contact;
//    @BindView(R.id.tab_1)
//    LinearLayout tab_1;
//    @BindView(R.id.industry_view)
//    RelativeLayout industry_view;
//    @BindView(R.id.industry_spinner)
//    Spinner industry_spinner;
//    @BindView(R.id.type_view)
//    RelativeLayout type_view;
//    @BindView(R.id.type_spinner)
//    Spinner type_spinner;
//    @BindView(R.id.annual_revenue_value)
//    EditText annual_revenue_value;
//    @BindView(R.id.account_balance_value)
//    EditText account_balance_value;
//    @BindView(R.id.company_id_value)
//    EditText company_id_value;
//    @BindView(R.id.invoice_no_value)
//    EditText invoice_no_value;
//    @BindView(R.id.credit_limit_value)
//    EditText credit_limit_value;
//    @BindView(R.id.payment_term_value)
//    Spinner payment_term_value;
//    @BindView(R.id.parent_account_value)
//    Spinner parent_account_value;
//    @BindView(R.id.ratingBar)
//    RatingBar ratingBar;
//    @BindView(R.id.mobile_value)
//    EditText mobile_value;
//    @BindView(R.id.email_value)
//    EditText email_value;
//    @BindView(R.id.website_value)
//    EditText website_value;
//    @BindView(R.id.bill_to_value)
//    EditText bill_to_value;
//    @BindView(R.id.ship_to_value)
//    EditText ship_to_value;
//    @BindView(R.id.remarks_value)
//    EditText remarks_value;
//    @BindView(R.id.upload_img)
//    ImageView upload_img;
//    @BindView(R.id.uploadview)
//    RelativeLayout uploadview;
//    @BindView(R.id.ship_block)
//    LinearLayout ship_block;
//    @BindView(R.id.general_frame)
//    FrameLayout general_frame;
//    @BindView(R.id.contact_frame)
//    FrameLayout contact_frame;
//    @BindView(R.id.create_button)
//    Button create_button;
//    @BindView(R.id.done_button)
//    Button done_button;
//    @BindView(R.id.sales_employee_spinner)
//    Spinner sales_employee_spinner;
//    @BindView(R.id.cardCode_value)
//    EditText cardCode_value;
//    @BindView(R.id.shipcity_value)
//    EditText shipcity_value;
//    @BindView(R.id.city_value)
//    EditText city_value;
//    @BindView(R.id.checkbox1)
//    CheckBox checkbox1;
//
//    @BindView(R.id.shipping_spinner)
//    Spinner shipping_spinner;
//
//    @BindView(R.id.shipping_spinner2)
//    Spinner shipping_spinner2;
//    @BindView(R.id.billing_name_value)
//    EditText billing_name_value;
//    @BindView(R.id.zip_code_value)
//    EditText zip_code_value;
//    @BindView(R.id.billing_address_value)
//    EditText billing_address_value;
//
//    @BindView(R.id.shipping_name_value)
//    EditText shipping_name_value;
//
//    @BindView(R.id.lead_value)
//    EditText lead_value;
//    @BindView(R.id.company_email_value)
//    EditText company_email_value;
//    @BindView(R.id.company_no_value)
//    EditText company_no_value;
//    @BindView(R.id.shipping_address_value)
//    EditText shipping_address_value;
//    @BindView(R.id.zipcode_value2)
//    EditText zipcode_value2;
//    @BindView(R.id.loader)
//    ProgressBar loader;
//    @BindView(R.id.country_value)
//    Spinner country_value;
//    @BindView(R.id.state_value)
//    Spinner state_value;
//    @BindView(R.id.ship_country_value)
//    Spinner ship_country_value;
//    @BindView(R.id.ship_state_value)
//    Spinner ship_state_value;
//    @BindView(R.id.lead_spinner)
//    Spinner lead_spinner;

    int salesEmployeeCode = 0;
    List<LeadValue> leadValueList = new ArrayList<>();
    String LeadID = "0";
    String payment_term = "";
    String parenT_account = "";
    String[] shippinngType;
    String billshipType;
    String ship_shiptype;
    AppCompatActivity act;
    String billtoState, billtoStateCode, billtoCountrycode, billtoCountryName, shiptoState, shiptoCountrycode, shiptoCountryName, shiptoStateCode;
    CountryAdapter countryAdapter;
    StateAdapter stateAdapter, shipStateAdapter;
    ArrayList<StateData> stateList = new ArrayList<>();
    ArrayList<StateData> shipstateList = new ArrayList<>();
    LeadValue leadValue;
    FragmentAddPartner2Binding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = AddBPCustomer.this;
        binding=FragmentAddPartner2Binding.inflate(getLayoutInflater());
        binding=FragmentAddPartner2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       // ButterKnife.bind(this);
        Intent intent = getIntent();

        if (intent != null && Prefs.getString(Globals.AddBp, "").equalsIgnoreCase("Lead")) {
            leadValue = intent.getParcelableExtra(Globals.AddBp);
            binding.fragmentAddpartnergeneral.leadValue.setEnabled(false);
            setData(leadValue);
        }
        act = this;
        shippinngType = getResources().getStringArray(R.array.shippingType);
        billshipType = shippinngType[0];

        setDefaults();
        //  callLeadApi();
        callBPlistApi();
        if (Globals.checkInternet(this)) {
            callSalessApi();

        }
        eventManager();
    }

    private void callLeadApi() {
        LeadFilter value = new LeadFilter();
        value.setAssignedTo(Globals.TeamEmployeeID);
        value.setLeadType("All");
        Call<LeadResponse> call = NewApiClient.getInstance().getApiService().getAllLead(value);
        call.enqueue(new Callback<LeadResponse>() {
            @Override
            public void onResponse(Call<LeadResponse> call, Response<LeadResponse> response) {
                if (response.code() == 200) {
                    LeadValue lvt = new LeadValue();
                    lvt.setCompanyName("Select lead");
                    lvt.setContactPerson("");
                    lvt.setEmail("");
                    lvt.setPhoneNumber("");

                    leadValueList.clear();
                    leadValueList.add(lvt);
                    leadValueList.addAll(filter(response.body().getData()));

                    binding.fragmentAddpartnergeneral.leadSpinner.setAdapter(new LeadListAdapter(AddBPCustomer.this, leadValueList));
                    if (Prefs.getString(Globals.AddBp, "").equalsIgnoreCase("Lead")) {
                        binding.fragmentAddpartnergeneral.leadSpinner.setSelection(getleadpos(leadValueList, leadValue.getCompanyName()));
                    }
                } else {

                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                        Toast.makeText(AddBPCustomer.this, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<LeadResponse> call, Throwable t) {

                Toast.makeText(AddBPCustomer.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ArrayList<LeadValue> filter(List<LeadValue> data) {
        ArrayList<LeadValue> templ = new ArrayList<>();
        for (LeadValue lv : data) {
            if (lv.getStatus().equalsIgnoreCase("Qualified")) {
                templ.add(lv);
            }
        }
        return templ;
    }


    private void setData(LeadValue leadValue) {
        binding.fragmentAddpartnergeneral.nameValue.setText(leadValue.getCompanyName());
        binding.fragmentAddpartnercontact.contactOwnerValue.setText(leadValue.getContactPerson());
        binding.fragmentAddpartnercontact.emailValue.setText(leadValue.getEmail());
        binding.fragmentAddpartnercontact.mobileValue.setText(leadValue.getPhoneNumber());
        binding.fragmentAddpartnercontact.addressSection.billingNameValue.setText(leadValue.getCompanyName());
        binding.fragmentAddpartnergeneral.leadValue.setText(leadValue.getCompanyName());
        LeadID = leadValue.getId().toString();

    }

    private int getleadpos(List<LeadValue> leadValueList, String companyName) {
        for (LeadValue ld : leadValueList) {
            if (ld.getCompanyName().equalsIgnoreCase(companyName)) {
                return leadValueList.indexOf(ld);
            }
        }
        return 0;
    }

    private void setDefaults() {
        frameManager(binding.generalFrame, binding.contactFrame, binding.general, binding.contact);
        binding.fragmentAddpartnercontact.addressSection.doneButton.setVisibility(View.GONE);
        binding.headerBottomRounded.headTitle.setText(getResources().getString(R.string.add_bussiness));
        binding.fragmentAddpartnercontact.createButton.setOnClickListener(this);
        binding.headerBottomRounded.backPress.setOnClickListener(this);
        // tab_2.setOnClickListener(this);
        binding.general.setOnClickListener(this);
        binding.tab2.setOnClickListener(this);
        binding.contact.setOnClickListener(this);
        binding.fragmentAddpartnergeneral.leadValue.setOnClickListener(this);

    }


    ArrayList<BusinessPartnerData> AllitemsList = new ArrayList<>();

    private void callBPlistApi() {


//        CustomerViewModel model = ViewModelProviders.of(this).get(CustomerViewModel.class);
//        model.getCustomersList(loader).observe(act, new Observer<List<BusinessPartnerData>>() {
//            @Override
//            public void onChanged(@Nullable List<BusinessPartnerData> itemsList) {
//
//                if (itemsList.size() >= 0) {
//                    AllitemsList.clear();
//                    BusinessPartnerData bpd = new BusinessPartnerData();
//                    bpd.setCardName("Select Parent Account");
//                    AllitemsList.add(bpd);
//                    AllitemsList.addAll(itemsList);
//
//
////                    parent_account_value.setAdapter(new ParentAccAdapter(act,filter(AllitemsList)));
//                    parent_account_value.setAdapter(new ArrayAdapter(act, android.R.layout.simple_list_item_1, addDatatoCategoryList(AllitemsList)));
//                    parenT_account = addDatatoCategoryList(AllitemsList).get(0);
//
//
//                }
//            }
//
//        });

        if (businessPartnerDataFromLocal.size() >= 0) {
            AllitemsList.clear();
            BusinessPartnerData bpd = new BusinessPartnerData();
            bpd.setCardName("Select Parent Account");
            AllitemsList.add(bpd);
            AllitemsList.addAll(businessPartnerDataFromLocal);


//                    parent_account_value.setAdapter(new ParentAccAdapter(act,filter(AllitemsList)));
            binding.fragmentAddpartnergeneral.parentAccountValue.setAdapter(new ArrayAdapter(act, android.R.layout.simple_list_item_1, addDatatoCategoryList(AllitemsList)));
            parenT_account = addDatatoCategoryList(AllitemsList).get(0);


        }


    }

    private ArrayList<String> addDatatoCategoryList(ArrayList<BusinessPartnerData> allitemsList) {
        ArrayList<String> bplist = new ArrayList<>();
        for (BusinessPartnerData bpdata : allitemsList) {
                   /* if(LeadID.isEmpty()){

                    }else{
                        if(bpdata.getLeadID().equalsIgnoreCase(LeadID)){
                            bplist.add(bpdata.getCardName());
                        }
                    }
*/
            bplist.add(bpdata.getCardName());
        }
        return bplist;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_press:
                onBackPressed();
                break;
            case R.id.tab_1:
            case R.id.general:
                frameManager(binding.generalFrame, binding.contactFrame, binding.general, binding.contact);
                break;
            case R.id.tab_2:
            case R.id.contact:
                frameManager(binding.contactFrame, binding.generalFrame, binding.contact, binding.general);
                break;
            case R.id.lead_value:
                selectLead();
                break;

            case R.id.create_button:
                String name = binding.fragmentAddpartnergeneral.nameValue.getText().toString().trim();
                String balance = binding.fragmentAddpartnergeneral.accountBalanceValue.getText().toString().trim();
                String anlrvnue = binding.fragmentAddpartnergeneral.annualRevenueValue.getText().toString().trim();
                String credit_limit = binding.fragmentAddpartnergeneral.creditLimitValue.getText().toString().trim();
                String invoice = binding.fragmentAddpartnergeneral.invoiceNoValue.getText().toString().trim();
                String rating = String.valueOf(binding.fragmentAddpartnergeneral.ratingBar.getRating());
                String cardCode = binding.fragmentAddpartnergeneral.cardCodeValue.getText().toString().trim();
                String mobile = binding.fragmentAddpartnercontact.mobileValue.getText().toString().trim();
                String email = binding.fragmentAddpartnercontact.emailValue.getText().toString().trim();
                String website = binding.fragmentAddpartnercontact.websiteValue.getText().toString().trim();
                String comp_email = binding.fragmentAddpartnergeneral.companyEmailValue.getText().toString().trim();
                String comp_no = binding.fragmentAddpartnergeneral.companyNoValue.getText().toString().trim();

                if (validation(name, comp_email, comp_no, mobile, email, industryCode, billtoStateCode)) {


                    /************************ BP Address *****************************/
                    ArrayList<BPAddress> postbpAddresses = new ArrayList<>();

                    BPAddress bp = new BPAddress();
                    bp.setBPCode(cardCode);
                    bp.setAddressName(binding.fragmentAddpartnercontact.billToValue.getText().toString());
                    bp.setStreet(binding.fragmentAddpartnercontact.addressSection.billingAddressValue.getText().toString());
                    bp.setBlock("");
                    bp.setZipCode(binding.fragmentAddpartnercontact.addressSection.zipCodeValue.getText().toString());
                    bp.setCity(binding.fragmentAddpartnercontact.addressSection.cityValue.getText().toString());
                    bp.setCountry(billtoCountrycode);  //countryCode
                    bp.setState(billtoStateCode);
                    bp.setUCountry(billtoCountryName);
                    bp.setUState(billtoState);
                    bp.setUShptyp(billshipType);
                    bp.setRowNum("0");
                    bp.setAddressType("bo_BillTo");
                    postbpAddresses.add(bp);


                    BPAddress bp1 = new BPAddress();
                    if (binding.fragmentAddpartnercontact.addressSection.checkbox1.isChecked()) {
                        bp1.setZipCode(binding.fragmentAddpartnercontact.addressSection.zipcodeValue2.getText().toString());
                        bp1.setAddressName(binding.fragmentAddpartnercontact.addressSection.shippingNameValue.getText().toString());
                        bp1.setStreet(binding.fragmentAddpartnercontact.addressSection.shippingAddressValue.getText().toString());
                        bp1.setUState(shiptoState);
                        bp1.setUCountry(shiptoCountryName);
                        bp1.setUShptyp(ship_shiptype);
                        bp1.setState(shiptoStateCode);
                        bp1.setCountry(shiptoCountrycode);
                        bp1.setCity(binding.fragmentAddpartnercontact.addressSection.shipcityValue.getText().toString());

                    } else {
                        bp1.setAddressName(binding.fragmentAddpartnercontact.addressSection.billingNameValue.getText().toString());
                        bp1.setStreet(binding.fragmentAddpartnercontact.addressSection.billingAddressValue.getText().toString());
                        bp1.setZipCode(binding.fragmentAddpartnercontact.addressSection.zipCodeValue.getText().toString());
                        bp1.setUCountry(billtoCountryName);
                        bp1.setUState(billtoState);
                        bp1.setUShptyp(billshipType);
                        bp1.setState(billtoStateCode);
                        bp1.setCountry(billtoCountrycode);
                        bp1.setCity(binding.fragmentAddpartnercontact.addressSection.cityValue.getText().toString());
                    }
                    bp1.setRowNum("1");

                    bp1.setBPCode(cardCode);
                    bp1.setBlock("B");

                    bp1.setAddressType("bo_ShipTo");
                    postbpAddresses.add(bp1);

                    /********************* Con Employee ************************/
                    ArrayList<ContactEmployees> postcontactEmployees = new ArrayList<>();
                    ContactEmployees postemp = new ContactEmployees();
                    postemp.setName(binding.fragmentAddpartnercontact.contactOwnerValue.getText().toString());
                    postemp.setE_Mail(email);
                    postemp.setMobilePhone(mobile);
                   /* postemp.setCardCode("Code");
                    postemp.setAddress("Cinntra");
                    postemp.setPosition("1");
                    postemp.setPhone1("96868768");
                    postemp.setPhone2("7863823132");
                    postemp.setMobilePhone("896787632");
                    postemp.setFax("jkdf");
                    postemp.setPager("4");
                    postemp.setRemarks1("Remarks1");
                    postemp.setRemarks2("Remarks1");
                    postemp.setGender("Male");
                    postemp.setTitle("Mr");
                    postemp.setPassword("Pass");
                    postemp.setFirstName("First");
                    postemp.setMiddleName("Middle");
                    postemp.setLastName("LastName");*/
                    postcontactEmployees.add(postemp);


                    AddBusinessPartnerData contactExtension = new AddBusinessPartnerData();
                    contactExtension.setUCurbal(balance);
                    contactExtension.setU_LEADID(LeadID);
                    contactExtension.setU_LEADNM(U_LeadNM);


                    contactExtension.setPayTermsGrpCode(payment_term);
                    contactExtension.setCreditLimit(credit_limit);
                    contactExtension.setURating(rating);
                    contactExtension.setUInvno(invoice);
                    contactExtension.setUParentacc(parenT_account);
                    contactExtension.setCardCode(cardCode);
                    contactExtension.setCardName(name);
                    contactExtension.setCardType("cCustomer"); //select value from spinner
                    contactExtension.setSalesPersonCode(String.valueOf(salesEmployeeCode));
                    contactExtension.setEmailAddress(comp_email);
                    contactExtension.setPhone1(comp_no);
                    contactExtension.setUType(TYPE);
                    contactExtension.setNotes(binding.fragmentAddpartnercontact.remarksValue.getText().toString());
                    contactExtension.setUAnlrvn(anlrvnue);
                    contactExtension.setIndustry(industryCode);
                    contactExtension.setUAccnt("");
                    contactExtension.setWebsite(website);
                    contactExtension.setDiscountPercent("");
                    contactExtension.setCurrency("INR");
                    contactExtension.setIntrestRatePercent("");
                    contactExtension.setCommissionPercent("");
                    contactExtension.setAttachmentEntry("");
                    contactExtension.setUBpgrp("");
                    contactExtension.setUContownr(binding.fragmentAddpartnercontact.contactOwnerValue.getText().toString());
                    // contactExtension.setContactPerson(contact_owner_value.getText().toString());
                    contactExtension.setCreateDate(Globals.getTodaysDatervrsfrmt());
                    contactExtension.setCreateTime(Globals.getTCurrentTime());
                    contactExtension.setUpdateDate(Globals.getTodaysDatervrsfrmt());
                    contactExtension.setuLat(String.valueOf(Globals.currentlattitude));
                    contactExtension.setuLong(String.valueOf(Globals.currentlongitude));
                    contactExtension.setUpdateTime(Globals.getTCurrentTime());
                    contactExtension.setBPAddresses(postbpAddresses);
                    contactExtension.setContactEmployees(postcontactEmployees);
                    if (Globals.checkInternet(this)) {
                        binding.loader.loader.setVisibility(View.VISIBLE);
                        createBP(contactExtension);
                    }
                }


                break;

        }
    }

    private void frameManager(FrameLayout visiblle_frame, FrameLayout f1, TextView selected, TextView t1) {
        selected.setTextColor(getResources().getColor(R.color.colorPrimary));
        t1.setTextColor(getResources().getColor(R.color.black));
        visiblle_frame.setVisibility(View.VISIBLE);
        f1.setVisibility(View.GONE);

    }

    private void selectLead() {
        Prefs.putString(Globals.BussinessPageType, "AddBPLead");
        Intent i = new Intent(AddBPCustomer.this, LeadsActivity.class);
        startActivityForResult(i, LeadCode);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LeadCode && resultCode == RESULT_OK) {
            LeadValue leadValue = data.getParcelableExtra(Globals.Lead_Data);
            binding.fragmentAddpartnergeneral.leadValue.setText(leadValue.getCompanyName());
            LeadID = leadValue.getId().toString();
            setData(leadValue);
        }
    }

    private void eventManager() {


        binding.fragmentAddpartnergeneral.parentAccountValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (getPaymenterm.size() > 0)
                    parenT_account = addDatatoCategoryList(AllitemsList).get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parenT_account = addDatatoCategoryList(AllitemsList).get(0);
            }
        });


        countryAdapter = new CountryAdapter(AddBPCustomer.this, MainActivity.countrylistFromLocal);
        binding.fragmentAddpartnercontact.addressSection.countryValue.setAdapter(countryAdapter);
        binding.fragmentAddpartnercontact.addressSection.countryValue.setSelection(Globals.getCountrypos(MainActivity.countrylistFromLocal, "India"));

        binding.fragmentAddpartnercontact.addressSection.countryValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                billtoCountrycode = MainActivity.countrylistFromLocal.get(position).getCode();
                billtoCountryName = MainActivity.countrylistFromLocal.get(position).getName();
                billtoState = "";
                billtoStateCode = "";
                callStateApi(billtoCountrycode, "billto");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                billtoCountrycode = MainActivity.countrylistFromLocal.get(0).getCode();
                billtoCountryName = MainActivity.countrylistFromLocal.get(0).getName();
            }
        });

        binding.fragmentAddpartnercontact.addressSection.stateValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                billtoState = stateList.get(position).getName();
                billtoStateCode = stateList.get(position).getCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                billtoState = stateList.get(0).getName();
                billtoStateCode = stateList.get(0).getCode();
            }
        });

        binding.fragmentAddpartnercontact.addressSection.shipCountryValue.setAdapter(countryAdapter);
        binding.fragmentAddpartnercontact.addressSection.shipCountryValue.setSelection(Globals.getCountrypos(MainActivity.countrylistFromLocal, "India"));

        binding.fragmentAddpartnercontact.addressSection.shipCountryValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                shiptoCountrycode = MainActivity.countrylistFromLocal.get(position).getCode();
                shiptoCountryName = MainActivity.countrylistFromLocal.get(position).getName();
                shiptoState = "";
                shiptoStateCode = "";
                callStateApi(shiptoCountrycode, "shipto");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                shiptoCountrycode = MainActivity.countrylistFromLocal.get(0).getCode();
                shiptoCountryName = MainActivity.countrylistFromLocal.get(0).getName();
            }
        });

        binding.fragmentAddpartnercontact.addressSection.shipStateValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                shiptoState = stateList.get(position).getName();
                shiptoStateCode = stateList.get(position).getCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                shiptoState = stateList.get(0).getName();
                shiptoStateCode = stateList.get(0).getCode();
            }
        });

        if (stateList.isEmpty()) {
            StateData sta = new StateData();
            sta.setName("Select State");
            stateList.add(sta);
        }

        binding.fragmentAddpartnergeneral.salesEmployeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (salesEmployeeItemList.size() > 0 && position > 0)
                    salesEmployeeCode = Integer.parseInt(salesEmployeeItemList.get(position).getSalesEmployeeCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                salesEmployeeCode = Integer.parseInt(salesEmployeeItemList.get(0).getSalesEmployeeCode());
            }

        });

        binding.fragmentAddpartnergeneral.typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (utypelist.size() > 0) {
                    TYPE = utypelist.get(position).getId().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                TYPE = utypelist.get(0).getId().toString();
            }
        });

      /*  lead_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(leadValueList.size()>1&&position>0){
                    LeadID = leadValueList.get(position).getId().toString();
                    U_LeadNM = leadValueList.get(position).getCompanyName();
                    setData(leadValueList.get(position));
                }else{
                    LeadID = "";
                    U_LeadNM = "";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        binding.fragmentAddpartnercontact.addressSection.shippingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                billshipType = shippinngType[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                billshipType = shippinngType[0];

            }
        });

        binding.fragmentAddpartnercontact.addressSection.shippingSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ship_shiptype = shippinngType[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ship_shiptype = shippinngType[0];
            }
        });

        binding.fragmentAddpartnergeneral.industrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (IndustryItemItemList.size() > 0)
                    industryCode = IndustryItemItemList.get(position).getIndustryCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if (IndustryItemItemList.size() > 0)
                    industryCode = IndustryItemItemList.get(0).getIndustryCode();
            }
        });
        binding.fragmentAddpartnercontact.addressSection.checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.fragmentAddpartnercontact.addressSection.shipBlock.setVisibility(View.VISIBLE);
                } else {
                    binding.fragmentAddpartnercontact.addressSection.shipBlock.setVisibility(View.GONE);
                }
            }
        });
        binding.fragmentAddpartnergeneral.paymentTermValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (getPaymenterm.size() > 0)
                    payment_term = getPaymenterm.get(position).getGroupNumber();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                payment_term = getPaymenterm.get(0).getGroupNumber();

            }
        });

    }

    private void callStateApi(String countryCode, String code) {

        StateData stateData = new StateData();
        stateData.setCountry(countryCode);
        Call<StateRespose> call = NewApiClient.getInstance().getApiService().getStateList(stateData);
        call.enqueue(new Callback<StateRespose>() {
            @Override
            public void onResponse(Call<StateRespose> call, Response<StateRespose> response) {

                if (response.code() == 200) {
                    if (code.equalsIgnoreCase("billto")) {
                        stateList.clear();
                        if (response.body().getData().size() > 0) {

                            stateList.addAll(response.body().getData());
                        } else {
                            StateData sta = new StateData();
                            sta.setName("Select State");
                            stateList.add(sta);
                        }
                        stateAdapter = new StateAdapter(AddBPCustomer.this, stateList);
                        binding.fragmentAddpartnercontact.addressSection.stateValue.setAdapter(stateAdapter);
                        stateAdapter.notifyDataSetChanged();
                        billtoState = stateList.get(0).getName();
                        billtoStateCode = stateList.get(0).getCode();

                    } else {
                        shipstateList.clear();
                        if (response.body().getData().size() > 0) {

                            shipstateList.addAll(response.body().getData());
                        } else {
                            StateData sta = new StateData();
                            sta.setName("Select State");
                            shipstateList.add(sta);
                        }
                        shipStateAdapter = new StateAdapter(AddBPCustomer.this, shipstateList);
                        binding.fragmentAddpartnercontact.addressSection.shipStateValue.setAdapter(shipStateAdapter);
                        shipStateAdapter.notifyDataSetChanged();
                        shiptoState = stateList.get(0).getName();
                        shiptoStateCode = stateList.get(0).getCode();
                    }

                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                        Toast.makeText(AddBPCustomer.this, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StateRespose> call, Throwable t) {

                Toast.makeText(AddBPCustomer.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean validation(String cowner, String comp_name,
                               String comp_no, String mobile, String email, String industryCode, String billtoStateCode) {


        if (cowner.isEmpty()) {
            Globals.showMessage(act, "Enter Company name");
            return false;
        } else if (comp_name.isEmpty() || Globals.isvalidateemail(binding.fragmentAddpartnergeneral.companyEmailValue)) {
            Globals.showMessage(act, "Enter Company Email");
            return false;
        } else if (comp_no.isEmpty()) {
            Globals.showMessage(act, "Enter Company Contact No.");
            return false;
        } else if (mobile.isEmpty()) {
            Globals.showMessage(act, "Enter Contact person mobile number");
            return false;
        } else if (industryCode.trim().equalsIgnoreCase("-1")) {
            Globals.showMessage(act, "Select Industry.");
            return false;
        } else if (email.length() != 0 && Globals.isvalidateemail(binding.fragmentAddpartnercontact.emailValue)) {
            Globals.showMessage(act, "Enter email address");
            return false;
        } else if (billtoStateCode.isEmpty()) {
            Globals.showMessage(act, "Select Billing state");
            return false;
        } else if (binding.fragmentAddpartnercontact.addressSection.billingNameValue.getText().toString().trim().isEmpty()) {
            Globals.showMessage(act, "Enter Billing Name");
            return false;
        } else if (binding.fragmentAddpartnercontact.addressSection.billingAddressValue.getText().toString().trim().isEmpty()) {
            Globals.showMessage(act, "Enter Billing Address");
            return false;
        } else if (binding.fragmentAddpartnercontact.addressSection.zipCodeValue.getText().toString().trim().isEmpty()) {
            Globals.showMessage(act, "Enter Billing Zipcode");
            return false;
        }
        return true;
    }

    /********************* APIs ***************************/
    List<PayMentTerm> getPaymenterm = new ArrayList<>();

    private void callPaymentApi() {
//        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
//        model.getPaymentList().observe(act, new Observer<List<PayMentTerm>>() {
//            @Override
//            public void onChanged(List<PayMentTerm> payMentTermList) {
//                if (payMentTermList == null || payMentTermList.size() == 0) {
//                    Globals.setmessage(act);
//                } else {
//                    getPaymenterm.clear();
//                    getPaymenterm = payMentTermList;
//                    payment_term_value.setAdapter(new PaymentAdapter(act, getPaymenterm));
//                    payment_term = getPaymenterm.get(0).getGroupNumber();
//
//                }
//
//            }
//        });

        if (MainActivity.paymentTermListFromLocal == null || MainActivity.paymentTermListFromLocal.size() == 0) {
            Globals.setmessage(act);
        } else {
            getPaymenterm.clear();
            getPaymenterm = MainActivity.paymentTermListFromLocal;
            binding.fragmentAddpartnergeneral.paymentTermValue.setAdapter(new PaymentAdapter(act, getPaymenterm));
            payment_term = getPaymenterm.get(0).getGroupNumber();

        }


        callUTypeApi();
    }

    List<UTypeData> utypelist = new ArrayList<>();

    private void callUTypeApi() {
//        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
//        model.getBpTypeList().observe(this, new Observer<List<UTypeData>>() {
//            @Override
//            public void onChanged(@Nullable List<UTypeData> itemsList) {
//                if (itemsList == null || itemsList.size() == 0) {
//                    Globals.setmessage(act);
//                } else {
//                    utypelist = itemsList;
//                    type_spinner.setAdapter(new BPTypeSpinnerAdapter(act, itemsList));
//                    TYPE = utypelist.get(0).getId().toString();
//
//                }
//            }
//        });

        utypelist = MainActivity.itemBpTypeDataFromLocal;
        binding.fragmentAddpartnergeneral.typeSpinner.setAdapter(new BPTypeSpinnerAdapter(act, MainActivity.itemBpTypeDataFromLocal));
        TYPE = utypelist.get(0).getId().toString();
    }


    List<IndustryItem> IndustryItemItemList = new ArrayList<>();

    private void callStagesApi() {
//        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
//        model.getIndustryList().observe(this, new Observer<List<IndustryItem>>() {
//            @Override
//            public void onChanged(@Nullable List<IndustryItem> itemsList) {
//                if(itemsList == null || itemsList.size()== 0){
//                    Globals.setmessage( act);
//                }else {
//                    IndustryItemItemList = itemsList;
//                    industry_spinner.setAdapter(new IndustrySpinnerAdapter(act,itemsList));
//                    industryCode = IndustryItemItemList.get(0).getIndustryCode();
//
//                }
//            }
//        });

        if (MainActivity.industryListFromLocal == null || MainActivity.industryListFromLocal.size() == 0) {
            Globals.setmessage(act);
        } else {
            IndustryItemItemList = MainActivity.industryListFromLocal;
            binding.fragmentAddpartnergeneral.industrySpinner.setAdapter(new IndustrySpinnerAdapter(act, MainActivity.industryListFromLocal));
            industryCode = IndustryItemItemList.get(0).getIndustryCode();

        }


        callPaymentApi();
    }


    public List<SalesEmployeeItem> salesEmployeeItemList = new ArrayList<>();

    private void callSalessApi() {
        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getSalesEmployeeList().observe(act, new Observer<List<SalesEmployeeItem>>() {
            @Override
            public void onChanged(@Nullable List<SalesEmployeeItem> itemsList) {
                if (itemsList == null || itemsList.size() == 0) {
                    Globals.setmessage(act);
                } else {
                    salesEmployeeItemList = itemsList;
                    binding.fragmentAddpartnergeneral.salesEmployeeSpinner.setAdapter(new SalesEmployeeAdapter(act, itemsList));
                    salesEmployeeCode = Integer.parseInt(salesEmployeeItemList.get(0).getSalesEmployeeCode());

                }
            }
        });
        callStagesApi();
    }

    /********************* Add Contact API *************************/

    private void createBP(AddBusinessPartnerData in) {

        Call<CustomerBusinessRes> call = NewApiClient.getInstance().getApiService().addnewCustomer(in);
        call.enqueue(new Callback<CustomerBusinessRes>() {
            @Override
            public void onResponse(Call<CustomerBusinessRes> call, Response<CustomerBusinessRes> response) {
                binding.loader.loader.setVisibility(View.GONE);
                if (response.body().getStatus().equalsIgnoreCase("200") ) {

                    if (response.body().getStatus().equalsIgnoreCase("200")) {
                        fetchBusinessPartnertDataFromApi(AddBPCustomer.this);


//                        Toasty.success(AddBPCustomer.this, "Add Successfully", Toast.LENGTH_LONG).show();
//                        onBackPressed();
                    } else {
                        Toasty.warning(AddBPCustomer.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toasty.warning(AddBPCustomer.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
//                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
//                    Gson gson = new GsonBuilder().create();
//                    QuotationResponse mError = new QuotationResponse();
//                    try {
//                        String s = response.errorBody().string();
//                        mError = gson.fromJson(s, QuotationResponse.class);
//                        Toast.makeText(act, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
//                    } catch (IOException e) {
//                        //handle failure to read error
//                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CustomerBusinessRes> call, Throwable t) {
                binding.loader.loader.setVisibility(View.GONE);
                Toasty.error(AddBPCustomer.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<BusinessPartnerData> fetchBusinessPartnertDataFromApi(Context context) {
       // alertDialog.show();
        Call<CustomerBusinessRes> call = NewApiClient.getInstance().getApiService().getAllBusinessPartner();
        ArrayList<BusinessPartnerData> countrylist = new ArrayList<>();
        call.enqueue(new Callback<CustomerBusinessRes>() {
            @Override
            public void onResponse(Call<CustomerBusinessRes> call, Response<CustomerBusinessRes> response) {
              //  alertDialog.dismiss();
                Log.d(TAG, "onResponse: " + response.code());
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse: enter in response");
                    if (response.body().getData().size() > 0) {
                        countrylist.clear();
                        countrylist.addAll(response.body().getData());
                        Log.d(TAG, "onResponse: " + countrylist.size());
//                        CountriesDatabase db = Room.databaseBuilder(
//                                context.getApplicationContext(),
//                                CountriesDatabase.class, "my-db-country"
//                        ).allowMainThreadQueries().build();
                        BusinessPartnerDatabase db = BusinessPartnerDatabase.getDatabase(getApplicationContext());
                        List<BusinessPartnerData> localData = db.myDataDao().getAll();
                        if (!localData.equals(countrylist)) {
                            db.myDataDao().insertAll(countrylist);
                            businessPartnerDataFromLocal.addAll(countrylist);
                            Log.e(TAG, "onResponse: " + db.myDataDao().getAll().toString());
                            Toasty.success(AddBPCustomer.this, "Add Successfully", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        } else {
                            //  Toast.makeText(MainActivity.this, "updated value", Toast.LENGTH_SHORT).show();

                        }

                        //  Log.d(TAG, "firstValue: " + localData.get(0).getName());

                        Log.d(TAG, "fetchDataAndUpdateDb: " + localData.size());
                    }


                }
            }

            @Override
            public void onFailure(Call<CustomerBusinessRes> call, Throwable t) {
                Toast.makeText(AddBPCustomer.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return countrylist;


    }

}