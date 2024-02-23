package com.cinntra.standalone.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cinntra.standalone.R;
import com.cinntra.standalone.activities.MainActivity;
import com.cinntra.standalone.activities.SelectedItems;
import com.cinntra.standalone.adapters.ContactPersonAdapter;
import com.cinntra.standalone.adapters.CountryAdapter;
import com.cinntra.standalone.adapters.SalesEmployeeAdapter;
import com.cinntra.standalone.adapters.StateAdapter;
import com.cinntra.standalone.databinding.EditQuotationBinding;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.model.AddressExtension;
import com.cinntra.standalone.model.ContactPersonData;
import com.cinntra.standalone.model.CountryData;
import com.cinntra.standalone.model.CountryResponse;
import com.cinntra.standalone.model.DocumentLines;
import com.cinntra.standalone.model.QuotationDocumentLines;
import com.cinntra.standalone.model.QuotationItem;
import com.cinntra.standalone.model.QuotationResponse;
import com.cinntra.standalone.model.SalesEmployeeItem;
import com.cinntra.standalone.model.StateData;
import com.cinntra.standalone.model.StateRespose;
import com.cinntra.standalone.model.UpdateQTDocumentLines;
import com.cinntra.standalone.model.UpdateQuotationModel;
import com.cinntra.standalone.room.CountriesDatabase;
import com.cinntra.standalone.viewModel.ItemViewModel;
import com.cinntra.standalone.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class Order_Update_Fragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "Order";
    public static int SelectItemCode = 102;
    private String QT_ID = "";
    public static String CardValue;
    public static String salePCode;
    UpdateQuotationModel addQuotationObj;

    CountriesDatabase db = CountriesDatabase.getDatabase(getActivity());

    FragmentActivity act;
//    @BindView(R.id.add)
//    ImageView add;
//
//    @BindView(R.id.oppView)
//    LinearLayout oppView;
//    @BindView(R.id.head_title)
//    TextView head_title;
//    @BindView(R.id.back_press)
//    RelativeLayout back_press;
//    @BindView(R.id.loader)
//    ProgressBar loader;
//
//    /******General Tab********/
//
//    @BindView(R.id.general)
//    TextView general;
//    @BindView(R.id.tab_1)
//    LinearLayout tab_1;
//    @BindView(R.id.general_frame)
//    FrameLayout general_frame;
//    @BindView(R.id.opportunity_name_value)
//    EditText opportunity_name_value;
//
//    @BindView(R.id.remark_value)
//    EditText remark_value;
//    @BindView(R.id.submit)
//    Button submit;
//    @BindView(R.id.companyNameCard)
//    CardView companyNameCard;
//    @BindView(R.id.company_name)
//    TextView company_name;
//    @BindView(R.id.qt_status)
//    TextView qt_status;
//    @BindView(R.id.valid_untill_date)
//    TextView valid_untill_date;
//    @BindView(R.id.contact_person_spinner)
//    Spinner contact_person_spinner;
//
//    /******Total Tab********/
//    @BindView(R.id.tab_2)
//    LinearLayout tab_2;
//    @BindView(R.id.total_frame)
//    FrameLayout total_frame;
//    @BindView(R.id.total)
//    TextView total;
//    @BindView(R.id.total_before_discont_value)
//    EditText total_before_discont_value;
//    @BindView(R.id.discont_value)
//    EditText _discont_value;
//    @BindView(R.id.tax_value)
//    EditText tax_value;
//    @BindView(R.id.total_value)
//    EditText total_value;
//    @BindView(R.id.shipping_value)
//    EditText shipping_value;
//    @BindView(R.id.next_button)
//    Button next;
//    @BindView(R.id.items_count)
//    TextView items_count;
//    @BindView(R.id.item_frame)
//    RelativeLayout item_frame;
//
//
//    /******Address Tab********/
//    @BindView(R.id.tab_3)
//    LinearLayout tab_3;
//    @BindView(R.id.prepared_frame)
//    FrameLayout prepared_frame;
//    @BindView(R.id.address)
//    TextView address;
//    @BindView(R.id.billing_name_value)
//    EditText billing_name_value;
//    @BindView(R.id.zip_code_value)
//    EditText zip_code_value;
//    @BindView(R.id.shipping_spinner)
//    Spinner shipping_spinner;
//    @BindView(R.id.billing_address_value)
//    EditText billing_address_value;
//    @BindView(R.id.done_button)
//    Button done;
//
//
//    @BindView(R.id.postingDate)
//    LinearLayout postingDate;
//    @BindView(R.id.posting_value)
//    EditText posting_value;
//    @BindView(R.id.postCal)
//    ImageView postCal;
//
//
//    @BindView(R.id.validDate)
//    LinearLayout validDate;
//    @BindView(R.id.valid_till_value)
//    EditText valid_till_value;
//    @BindView(R.id.validCal)
//    ImageView validCal;
//
//    @BindView(R.id.documentDate)
//    LinearLayout documentDate;
//    @BindView(R.id.document_date_value)
//    EditText document_date_value;
//    @BindView(R.id.docCal)
//    ImageView docCal;
//
//
//    @BindView(R.id.bpView)
//    LinearLayout bpView;
//    @BindView(R.id.quote_information)
//    TextView quote_information;
//    @BindView(R.id.checkbox1)
//    CheckBox checkbox1;
//    @BindView(R.id.ship_block)
//    LinearLayout ship_block;
//    @BindView(R.id.salesemployee_spinner)
//    Spinner salesemployee_spinner;
//    @BindView(R.id.shipping_name_value)
//    EditText shipping_name_value;
//    @BindView(R.id.shipping_address_value)
//    EditText shipping_address_value;
//    @BindView(R.id.zipcode_value2)
//    EditText zipcode_value2;
//    @BindView(R.id.city_value)
//    EditText city_value;
//    @BindView(R.id.shipcity_value)
//    EditText shipcity_value;
//    @BindView(R.id.shipping_spinner2)
//    Spinner shipping_spinner2;
//    @BindView(R.id.ok)
//    ImageView ok;
//
//    @BindView(R.id.country_value)
//    Spinner country_value;
//    @BindView(R.id.state_value)
//    Spinner state_value;
//    @BindView(R.id.ship_country_value)
//    Spinner ship_country_value;
//    @BindView(R.id.ship_state_value)
//    Spinner ship_state_value;


    EditQuotationBinding binding;

    public static boolean ESCAPED = true;
    public static boolean DISABLED = true;
    String[] shippinngType;
    String billtoState, billtoCountrycode, billtoCountryName, shiptoState, shiptoCountrycode, shiptoCountryName, shiptoStateCode, billtoStateCode;
    CountryAdapter countryAdapter;
    StateAdapter stateAdapter, shipstateAdapter;
    ArrayList<StateData> stateList = new ArrayList<>();
    ArrayList<StateData> shipstateList = new ArrayList<>();
    ArrayList<CountryData> localData = new ArrayList<>();

    public Order_Update_Fragment() {
        //Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Order_Update_Fragment newInstance(String param1, String param2) {
        Order_Update_Fragment fragment = new Order_Update_Fragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    QuotationItem quotationItem;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b = getArguments();
            quotationItem = (QuotationItem) b.getSerializable(Globals.QuotationItem);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        act = getActivity();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        binding=EditQuotationBinding.inflate(getLayoutInflater());
        View v = inflater.inflate(R.layout.edit_quotation, container, false);
      //  ButterKnife.bind(this, v);
        shippinngType = getActivity().getResources().getStringArray(R.array.shippingType);
        binding.quotationGeneralContent.companyNameCard.setVisibility(View.VISIBLE);
        binding.quotationGeneralContent.oppView.setVisibility(View.GONE);
        binding.headerBottomroundEdit.headTitle.setText(getString(R.string.details_order));
        binding.quotationGeneralContent.quoteInformation.setText(getResources().getString(R.string.order_information));
        binding.quotationGeneralContent.bpView.setVisibility(View.GONE);
        binding.tab1.setOnClickListener(this);
        binding.tab2.setOnClickListener(this);
        binding.tab3.setOnClickListener(this);
        binding.general.setOnClickListener(this);
        binding.quotationTotalContent.itemFrame.setOnClickListener(this);
        binding.headerBottomroundEdit.backPress.setOnClickListener(this);
        setDisable();
        setData();
        binding.quotationPreparedForContent.addressSection.checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.quotationPreparedForContent.addressSection.shipBlock.setVisibility(View.VISIBLE);
                } else {
                    binding.quotationPreparedForContent.addressSection.shipBlock.setVisibility(View.GONE);
                }
            }
        });


        return binding.getRoot();
    }

    private void setDisable() {

        binding.headerBottomroundEdit.add.setOnClickListener(this);
        binding.headerBottomroundEdit.add.setVisibility(View.INVISIBLE);
        binding.quotationGeneralContent.bpView.setVisibility(View.GONE);

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.quotationGeneralContent.opportunityNameValue.getApplicationWindowToken(), 0);


        binding.quotationGeneralContent.submit.setClickable(false);
        binding.quotationGeneralContent.submit.setFocusable(false);


        binding.quotationGeneralContent.opportunityNameValue.setFocusableInTouchMode(false);
        binding.quotationGeneralContent.opportunityNameValue.setFocusable(false);
        binding.quotationGeneralContent.opportunityNameValue.setClickable(false);

        binding.quotationGeneralContent.contactPersonSpinner.setEnabled(false);
        binding.quotationGeneralContent.salesemployeeSpinner.setEnabled(false);

        binding.quotationGeneralContent.postingValue.setClickable(false);
        binding.quotationGeneralContent.postingValue.setFocusable(false);
        binding.quotationGeneralContent.postingValue.setFocusableInTouchMode(false);

        binding.quotationGeneralContent.postingDate.setFocusable(false);
        binding.quotationGeneralContent.postingDate.setClickable(false);
        binding.quotationGeneralContent.postingDate.setFocusableInTouchMode(false);
        binding.quotationGeneralContent.postingDate.setEnabled(false);

        binding.quotationGeneralContent.validTillValue.setFocusableInTouchMode(false);
        binding.quotationGeneralContent.validTillValue.setClickable(false);
        binding.quotationGeneralContent.validTillValue.setFocusable(false);


        binding.quotationGeneralContent.validDate.setClickable(false);
        binding.quotationGeneralContent.validDate.setFocusable(false);
        binding.quotationGeneralContent.validDate.setEnabled(false);
        binding.quotationGeneralContent.validDate.setFocusableInTouchMode(false);

        binding.quotationGeneralContent.documentDateValue.setFocusable(false);
        binding.quotationGeneralContent.documentDateValue.setClickable(false);
        binding.quotationGeneralContent.documentDateValue.setFocusableInTouchMode(false);

        binding.quotationGeneralContent.documentDate.setFocusable(false);
        binding.quotationGeneralContent.documentDate.setClickable(false);
        binding.quotationGeneralContent.documentDate.setEnabled(false);
        binding.quotationGeneralContent.documentDate.setFocusableInTouchMode(false);

        binding.quotationGeneralContent.remarkValue.setFocusableInTouchMode(false);
        binding.quotationGeneralContent.remarkValue.setFocusable(false);
        binding.quotationGeneralContent.remarkValue.setClickable(false);

        binding.quotationTotalContent.totalBeforeDiscontValue.setClickable(false);
        binding.quotationTotalContent.totalBeforeDiscontValue.setFocusable(false);
        binding.quotationTotalContent.totalBeforeDiscontValue.setFocusableInTouchMode(false);

        binding.quotationTotalContent.discontValue.setFocusableInTouchMode(false);
        binding.quotationTotalContent.discontValue.setFocusable(false);
        binding.quotationTotalContent.discontValue.setClickable(false);

        binding.quotationTotalContent.taxValue.setFocusableInTouchMode(false);
        binding.quotationTotalContent.taxValue.setFocusable(false);
        binding.quotationTotalContent.taxValue.setClickable(false);

        binding.quotationTotalContent.shippingValue.setFocusableInTouchMode(false);
        binding.quotationTotalContent.shippingValue.setFocusable(false);
        binding.quotationTotalContent.shippingValue.setClickable(false);

        binding.quotationTotalContent.totalValue.setFocusableInTouchMode(false);
        binding.quotationTotalContent.totalValue.setFocusable(false);
        binding.quotationTotalContent.totalValue.setClickable(false);

        binding.quotationTotalContent.nextButton.setFocusable(false);
        binding.quotationTotalContent.nextButton.setClickable(false);


        binding.quotationPreparedForContent.addressSection.shippingNameValue.setFocusableInTouchMode(false);
        binding.quotationPreparedForContent.addressSection.shippingNameValue.setFocusable(false);
        binding.quotationPreparedForContent.addressSection.shippingNameValue.setClickable(false);

        binding.quotationPreparedForContent.addressSection.zipcodeValue2.setClickable(false);
        binding.quotationPreparedForContent.addressSection.zipcodeValue2.setFocusable(false);
        binding.quotationPreparedForContent.addressSection.zipcodeValue2.setFocusableInTouchMode(false);

        binding.quotationPreparedForContent.addressSection.shippingSpinner2.setEnabled(false);

        binding.quotationPreparedForContent.addressSection.doneButton.setClickable(false);
        binding.quotationPreparedForContent.addressSection.doneButton.setFocusable(false);


        binding.quotationPreparedForContent.addressSection.shippingAddressValue.setFocusableInTouchMode(false);
        binding.quotationPreparedForContent.addressSection.shippingAddressValue.setFocusable(false);
        binding.quotationPreparedForContent.addressSection.shippingAddressValue.setClickable(false);

        binding.quotationPreparedForContent.addressSection.billingAddressValue.setClickable(false);
        binding.quotationPreparedForContent.addressSection.billingAddressValue.setFocusable(false);
        binding.quotationPreparedForContent.addressSection.billingAddressValue.setFocusableInTouchMode(false);

        binding.quotationPreparedForContent.addressSection.billingNameValue.setFocusableInTouchMode(false);
        binding.quotationPreparedForContent.addressSection.billingNameValue.setFocusable(false);
        binding.quotationPreparedForContent.addressSection.billingNameValue.setClickable(false);

        binding.quotationPreparedForContent.addressSection.zipCodeValue.setClickable(false);
        binding.quotationPreparedForContent.addressSection.zipCodeValue.setFocusable(false);
        binding.quotationPreparedForContent.addressSection.zipCodeValue.setFocusableInTouchMode(false);
        binding.quotationPreparedForContent.addressSection.countryValue.setClickable(false);
        binding.quotationPreparedForContent.addressSection.shipStateValue.setClickable(false);
        binding.quotationPreparedForContent.addressSection.shipCountryValue.setClickable(false);
        binding.quotationPreparedForContent.addressSection.stateValue.setClickable(false);
        binding.quotationPreparedForContent.addressSection.shippingSpinner.setEnabled(false);

    //    binding.headerBottomroundEdit.add.setVisibility(View.VISIBLE);
        binding.headerBottomroundEdit.ok.setVisibility(View.GONE);

        ESCAPED = true;
        DISABLED = false;

    }

    String ContactPersonCode = "";
    String salesPersonCode = "";
    String billshiptype = "";
    String ship_shiptype = "";


    private void setData() {
        billtoCountrycode = quotationItem.getAddressExtension().getBillToCountry();
        billtoStateCode = quotationItem.getAddressExtension().getBillToState();
        billtoCountryName = quotationItem.getAddressExtension().getU_BCOUNTRY();
        billtoState = quotationItem.getAddressExtension().getU_BSTATE();
        shiptoCountrycode = quotationItem.getAddressExtension().getShipToCountry();
        shiptoStateCode = quotationItem.getAddressExtension().getShipToState();
        shiptoCountryName = quotationItem.getAddressExtension().getU_SCOUNTRY();
        shiptoState = quotationItem.getAddressExtension().getU_SSTATE();
        binding.quotationPreparedForContent.addressSection.cityValue.setText(quotationItem.getAddressExtension().getBillToCity());
        binding.quotationPreparedForContent.addressSection.shipcityValue.setText(quotationItem.getAddressExtension().getShipToCity());
        callStateApi(billtoCountrycode, "billto");
        callStateApi(shiptoCountrycode, "shipto");


        localData = (ArrayList)db.myDataDao().getAll();
        Log.d(TAG, "setData: ");

        if (localData.isEmpty()) {
            Log.d(TAG, "emptyLocal Data: ");
            callCountryApi();
        }
        Log.d(TAG, "gettingdata: ");
        countryAdapter = new CountryAdapter(getContext(), localData);


        binding.quotationPreparedForContent.addressSection.countryValue.setAdapter(countryAdapter);
        binding.quotationPreparedForContent.addressSection.shipCountryValue.setAdapter(countryAdapter);

        binding.quotationPreparedForContent.addressSection.countryValue.setSelection(Globals.getCountrypos(localData, billtoCountryName));
        binding.quotationPreparedForContent.addressSection.shipCountryValue.setSelection(Globals.getCountrypos(localData, shiptoCountryName));
        QT_ID = quotationItem.getDocEntry();
        ContactPersonCode = quotationItem.getContactPersonCode().get(0).getId().toString();
        salesPersonCode = quotationItem.getSalesPersonCode().get(0).getSalesEmployeeCode();

        if (Globals.checkInternet(getActivity()))
            callContactApi(quotationItem.getCardCode());

        Globals.SelectedItems.clear();
       /* Globals.contentList.clear();
        Globals.contentList.addAll(quotationItem.getDocumentLines());*/


        /*********** Set Data In Header Section**************/
//          qt_status.setText(Globals.getStatus(quotationItem.getDocumentStatus()));

       /* if (Globals.viewStatus(quotationItem.getDocumentStatus()) == "Close"){
            qt_status.setText("Closed");
            qt_status.setBackground(getResources().getDrawable(R.drawable.closeroundsaffron));

        }else{
            qt_status.setText("Open");
            qt_status.setBackground(getResources().getDrawable(R.drawable.openroundedgreen));
        }*/

        Total_Before_Disscount = Total_Before_Disscount(quotationItem.getDocumentLines());
        Globals.SelectedItems.addAll(quotationItem.getDocumentLines());
        binding.quotationGeneralContent.companyName.setText(quotationItem.getCardName());
        binding.quotationGeneralContent.validUntillDate.setText(getResources().getString(R.string.valid_untill) + ": " + quotationItem.getDocDueDate());

        binding.quotationTotalContent.discontValue.setText(quotationItem.getDiscountPercent());//
        binding.quotationTotalContent.totalBeforeDiscontValue.setText(Globals.getAmmount(quotationItem.getDocCurrency(), String.valueOf(Total_Before_Disscount)));
        binding.quotationTotalContent.taxValue.setText("10");
        //total_value.setText(Globals.getAmmount(quotationItem.getDocCurrency(),quotationItem.getDocTotal())+" ( "+Globals.getAmmount(quotationItem.getDocCurrency(),quotationItem.getDocTotalSys())+" )");
        binding.quotationTotalContent.totalBeforeDiscontValue.setText(quotationItem.getDocCurrency() + " " + Globals.calculatetotalofitem(Globals.SelectedItems));
        binding.quotationTotalContent.totalValue.setText(quotationItem.getDocCurrency() + " " + Globals.calculatetotal(10, Double.parseDouble(binding.quotationTotalContent.totalBeforeDiscontValue.getText().toString())));
        binding.quotationGeneralContent.documentDateValue.setText(quotationItem.getTaxDate());
        binding.quotationGeneralContent.validTillValue.setText(quotationItem.getDocDueDate());
        binding.quotationGeneralContent.postingValue.setText(quotationItem.getDocDate());
        binding.quotationGeneralContent.remarkValue.setText(quotationItem.getComments());

        if (quotationItem.getAddressExtension().getBillToBuilding() != null)
            binding.quotationPreparedForContent.addressSection.billingNameValue.setText(quotationItem.getAddressExtension().getBillToBuilding());
        if (quotationItem.getAddressExtension().getBillToStreet() != null)
            binding.quotationPreparedForContent.addressSection.billingAddressValue.setText(quotationItem.getAddressExtension().getBillToStreet());
        if (quotationItem.getAddressExtension().getBillToZipCode() != null)
            binding.quotationPreparedForContent.addressSection.zipCodeValue.setText(quotationItem.getAddressExtension().getBillToZipCode());


        if (quotationItem.getAddressExtension().getShipToBuilding() != null)
            binding.quotationPreparedForContent.addressSection.shippingNameValue.setText(quotationItem.getAddressExtension().getShipToBuilding());
        if (quotationItem.getAddressExtension().getShipToStreet() != null)
            binding.quotationPreparedForContent.addressSection.shippingAddressValue.setText(quotationItem.getAddressExtension().getShipToStreet());
        if (quotationItem.getAddressExtension().getShipToZipCode() != null)
            binding.quotationPreparedForContent.addressSection.zipcodeValue2.setText(quotationItem.getAddressExtension().getShipToZipCode());

        billshiptype = quotationItem.getAddressExtension().getU_SHPTYPB();
        if (billshiptype != null)
            binding.quotationPreparedForContent.addressSection.shippingSpinner.setSelection(Globals.getShipTypePo(shippinngType, billshiptype));

        ship_shiptype = quotationItem.getAddressExtension().getU_SHPTYPS();
        if (ship_shiptype != null)
            binding.quotationPreparedForContent.addressSection.shippingSpinner2.setSelection(Globals.getShipTypePo(shippinngType, ship_shiptype));

        binding.quotationTotalContent.itemsCount.setText("Items (" + quotationItem.getDocumentLines().size() + ")");


        /*********** Set Data In Content Section**************/
        frameManager(binding.generalFrame, binding.totalFrame, binding.preparedFrame, binding.general, binding.total, binding.address);


        /******************* Data for Api use ************************/
        addQuotationObj = new UpdateQuotationModel();
        CardValue = quotationItem.getCardCode();
        salePCode = quotationItem.getContactPersonCode().get(0).getId().toString();
        addQuotationObj.setCardCode(CardValue);
        addQuotationObj.setSalesPerson(salePCode);


        binding.quotationGeneralContent.contactPersonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ContactPersonCode = ContactEmployeesList.get(position).getInternalCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.quotationPreparedForContent.addressSection.shippingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                billshiptype = shippinngType[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if (billshiptype != null)
                    binding.quotationPreparedForContent.addressSection.shippingSpinner.setSelection(Globals.getShipTypePo(shippinngType, billshiptype));
            }
        });

        binding.quotationPreparedForContent.addressSection.shippingSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ship_shiptype = shippinngType[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if (ship_shiptype != null)
                    binding.quotationPreparedForContent.addressSection.shippingSpinner2.setSelection(Globals.getShipTypePo(shippinngType, ship_shiptype));
            }
        });


        binding.quotationGeneralContent.salesemployeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (salesEmployeeItemList.size() > 0 && position > 0) {
                    salesPersonCode = salesEmployeeItemList.get(position).getSalesEmployeeCode();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        salesEmployeeItemList = Globals.getSaleEmployeeArrayList(Globals.SalesEmployeeList);
        if (salesEmployeeItemList == null)
            callSalessApi();
        else {
            salesadapter = new SalesEmployeeAdapter(getActivity(), salesEmployeeItemList);
            binding.quotationGeneralContent.salesemployeeSpinner.setAdapter(salesadapter);
            if (!salesEmployeeItemList.isEmpty() && !salesPersonCode.isEmpty())
                binding.quotationGeneralContent.salesemployeeSpinner.setSelection(Globals.getSelectedSalesP(salesEmployeeItemList, salesPersonCode));
        }


        binding.quotationPreparedForContent.addressSection.countryValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                billtoCountrycode = MainActivity.countrylistFromLocal.get(position).getCode();
                billtoCountryName = MainActivity.countrylistFromLocal.get(position).getName();

                callStateApi(billtoCountrycode, "billto");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                billtoCountrycode = MainActivity.countrylistFromLocal.get(0).getCode();
                billtoCountryName = MainActivity.countrylistFromLocal.get(0).getName();
            }
        });

        binding.quotationPreparedForContent.addressSection.stateValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


        binding.quotationPreparedForContent.addressSection.shipCountryValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                shiptoCountrycode = MainActivity.countrylistFromLocal.get(position).getCode();
                shiptoCountryName = MainActivity.countrylistFromLocal.get(position).getName();

                callStateApi(shiptoCountrycode, "shipto");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                shiptoCountrycode = MainActivity.countrylistFromLocal.get(0).getCode();
                shiptoCountryName = MainActivity.countrylistFromLocal.get(0).getName();

            }
        });

        binding.quotationPreparedForContent.addressSection.shipStateValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                shiptoState = shipstateList.get(position).getName();
                shiptoStateCode = shipstateList.get(position).getCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                shiptoState = shipstateList.get(0).getName();
                shiptoStateCode = shipstateList.get(0).getCode();
            }
        });


    }

    private void callCountryApi() {

        Call<CountryResponse> call = NewApiClient.getInstance().getApiService().getCountryList();
        call.enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getData().size() > 0) {
                        localData.clear();
                        localData.addAll(response.body().getData());
                    }


                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setDefaults() {

        binding.general.setOnClickListener(this);
        binding.total.setOnClickListener(this);
        binding.quotationGeneralContent.submit.setOnClickListener(this);
        binding.address.setOnClickListener(this);
        binding.headerBottomroundEdit.add.setOnClickListener(this);
        binding.quotationTotalContent.nextButton.setOnClickListener(this);
        binding.quotationPreparedForContent.addressSection.doneButton.setOnClickListener(this);
        binding.quotationGeneralContent.postingValue.setOnClickListener(this);
        binding.quotationGeneralContent.postingDate.setOnClickListener(this);
        binding.quotationGeneralContent.postCal.setOnClickListener(this);
        binding.quotationGeneralContent.validTillValue.setOnClickListener(this);
        binding.quotationGeneralContent.validDate.setOnClickListener(this);
        binding.headerBottomroundEdit.ok.setOnClickListener(this);
        binding.quotationGeneralContent.validCal.setOnClickListener(this);
        binding.quotationGeneralContent.documentDateValue.setOnClickListener(this);
        binding.quotationGeneralContent.docCal.setOnClickListener(this);
        binding.quotationGeneralContent.documentDate.setOnClickListener(this);


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
                        stateAdapter = new StateAdapter(getContext(), stateList);
                        binding.quotationPreparedForContent.addressSection.stateValue.setAdapter(stateAdapter);

                        stateAdapter.notifyDataSetChanged();
                        if (quotationItem.getAddressExtension().getU_BSTATE() != null)
                            binding.quotationPreparedForContent.addressSection.stateValue.setSelection(Globals.getStatePo(stateList, quotationItem.getAddressExtension().getU_BSTATE()));

                    } else {
                        shipstateList.clear();
                        if (response.body().getData().size() > 0) {

                            shipstateList.addAll(response.body().getData());

                        } else {
                            StateData sta = new StateData();
                            sta.setName("Select State");
                            shipstateList.add(sta);
                        }
                        shipstateAdapter = new StateAdapter(getContext(), shipstateList);
                        binding.quotationPreparedForContent.addressSection.shipStateValue.setAdapter(shipstateAdapter);
                        shipstateAdapter.notifyDataSetChanged();
                        if (quotationItem.getAddressExtension().getU_SSTATE() != null)
                            binding.quotationPreparedForContent.addressSection.shipStateValue.setSelection(Globals.getStatePo(shipstateList, quotationItem.getAddressExtension().getU_SSTATE()));
                    }
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

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.add:
                if (ESCAPED) {
                    Globals.openKeyboard(getContext());
                    setEnable();
                    setDefaults();
                } else {
                    setDisable();
                }
                break;
            case R.id.ok:
                setDisable();
                String oppname = binding.quotationGeneralContent.opportunityNameValue.getText().toString().trim();
                String poDate = binding.quotationGeneralContent.postingValue.getText().toString().trim();
                String vDate = binding.quotationGeneralContent.validTillValue.getText().toString().trim();
                String docDate = binding.quotationGeneralContent.documentDateValue.getText().toString().trim();
                String discount = binding.quotationTotalContent.discontValue.getText().toString().trim();
                String remark = binding.quotationGeneralContent.remarkValue.getText().toString().trim();

                if (valiadtion(ContactPersonCode, poDate, vDate, docDate, remark)) {

                    addQuotationObj.setOpportunityName(oppname);
                    addQuotationObj.setSalesPerson(ContactPersonCode);
                    addQuotationObj.setSalesPersonCode(salesPersonCode);
                    addQuotationObj.setPostingDate(poDate);
                    addQuotationObj.setValidDate(vDate);
                    addQuotationObj.setDiscountPercent(Float.valueOf(discount));
                    addQuotationObj.setDocumentDate(docDate);
                    addQuotationObj.setRemarks(remark);
                    addQuotationObj.setUpdateDate(Globals.getTodaysDate());
                    addQuotationObj.setUpdateTime(Globals.getTCurrentTime());
                    addQuotationObj.setCreateTime(Globals.getTCurrentTime());
                    addQuotationObj.setCreateDate(Globals.getTodaysDate());
                    addQuotationObj.setId(quotationItem.getId());


                    AddressExtension addressExtension = new AddressExtension();
                    addressExtension.setShipToBuilding(binding.quotationPreparedForContent.addressSection.shippingNameValue.getText().toString());
                    addressExtension.setShipToStreet(binding.quotationPreparedForContent.addressSection.shippingAddressValue.getText().toString());
                    addressExtension.setShipToCity(binding.quotationPreparedForContent.addressSection.shipcityValue.getText().toString());
                    addressExtension.setShipToZipCode(binding.quotationPreparedForContent.addressSection.zipcodeValue2.getText().toString());
                    addressExtension.setShipToState(shiptoStateCode);
                    addressExtension.setShipToCountry(shiptoCountrycode);   //countryCode2
                    addressExtension.setU_SSTATE(shiptoState);
                    addressExtension.setU_SCOUNTRY(shiptoCountryName);
                    addressExtension.setU_SHPTYPS(ship_shiptype);
                    addressExtension.setU_OPPRNM("");
                    addressExtension.setBillToBuilding(binding.quotationPreparedForContent.addressSection.billingNameValue.getText().toString());
                    addressExtension.setBillToStreet(binding.quotationPreparedForContent.addressSection.billingAddressValue.getText().toString());
                    addressExtension.setBillToCity(binding.quotationPreparedForContent.addressSection.cityValue.getText().toString());
                    addressExtension.setBillToZipCode(binding.quotationPreparedForContent.addressSection.zipCodeValue.getText().toString());
                    addressExtension.setBillToState(billtoStateCode);
                    addressExtension.setBillToCountry(billtoCountrycode);       //countryCode2
                    addressExtension.setU_BSTATE(billtoState);
                    addressExtension.setU_BCOUNTRY(billtoCountryName);
                    addressExtension.setU_SHPTYPB(billshiptype);
                    addressExtension.setId(quotationItem.getAddressExtension().getId());
                    addQuotationObj.setAddressExtension(addressExtension);

                    //  addQuotationObj.setDocumentLines(postJson(quotationItem.getDocumentLines()));
//                    addQuotationObj.setDocumentLines(postJsonCopy(Globals.SelectedItems,quotationItem.getDocumentLines()));
                    addQuotationObj.setDocumentLines(Globals.SelectedItems);


                    if (Globals.checkInternet(getActivity())) {

                        updateQuotation(QT_ID, addQuotationObj);
                    }
                }

                break;
            case R.id.customer_block:
                Bussiness_Partner_Fragment frg = new Bussiness_Partner_Fragment();
                FragmentTransaction tr = getFragmentManager().beginTransaction();
                tr.replace(R.id.quatoes_main_container, frg);
                tr.addToBackStack(null);
                tr.commit();
                break;
            case R.id.back_press:
                ((AppCompatActivity) getActivity()).getSupportActionBar().show();
                Globals.hideKeybaord(v, getContext());
                getActivity().onBackPressed();
                break;
            case R.id.tab_1:
            case R.id.general:
                frameManager(binding.generalFrame, binding.totalFrame, binding.preparedFrame, binding.general, binding.total, binding.address);
                break;
            case R.id.tab_2:
            case R.id.total:
                frameManager(binding.totalFrame, binding.generalFrame, binding.preparedFrame, binding.total, binding.general, binding.address);
                break;
            case R.id.tab_3:

            case R.id.address:
                frameManager(binding.preparedFrame, binding.generalFrame, binding.totalFrame, binding.address, binding.general, binding.total);
                break;
            case R.id.posting_value:
            case R.id.postCal:
            case R.id.postingDate:
                if (DISABLED)
                    Globals.selectDate(getContext(), binding.quotationGeneralContent.postingValue);
                break;
            case R.id.valid_till_value:
            case R.id.validDate:
            case R.id.validCal:
                if (DISABLED)
                    Globals.selectDate(getContext(), binding.quotationGeneralContent.validTillValue);
                break;
            case R.id.document_date_value:
            case R.id.docCal:
            case R.id.documentDate:
                if (DISABLED)
                    Globals.selectDate(getContext(), binding.quotationGeneralContent.documentDateValue);
                break;
            case R.id.submit:
                if (validation(binding.quotationGeneralContent.remarkValue.getText().toString().trim(), ContactPersonCode)) {
                    frameManager(binding.totalFrame, binding.generalFrame, binding.preparedFrame, binding.total, binding.general, binding.address);
                }
                break;
            case R.id.next_button:
                frameManager(binding.preparedFrame, binding.generalFrame, binding.totalFrame, binding.address, binding.general, binding.total);
                break;
            case R.id.done_button:
                // frameManager(prepared_frame,general_frame,total_frame,address,general,total);
                binding.headerBottomroundEdit.add.performClick();
                break;

            case R.id.item_frame:
                Globals.hideKeybaord(v, getContext());
                if (DISABLED) {
                    Globals.inventory_item_close = false;
                    Intent intent = new Intent(act, SelectedItems.class);
                    intent.putExtra("FromWhere", "QT_UP");
                    startActivityForResult(intent, SelectItemCode);
                } else {
                    Globals.inventory_item_close = true;
                    Intent intent = new Intent(act, SelectedItems.class);
                    intent.putExtra("FromWhere", "invoices");
                    startActivityForResult(intent, SelectItemCode);
                }
                break;


        }


    }

    private void setEnable() {

        binding.quotationGeneralContent.postingValue.setClickable(true);

        binding.quotationGeneralContent.validTillValue.setClickable(true);

        binding.quotationGeneralContent.documentDateValue.setClickable(true);

        binding.quotationGeneralContent.submit.setClickable(true);
        binding.quotationGeneralContent.submit.setFocusable(true);
        binding.quotationGeneralContent.submit.setEnabled(true);

        binding.quotationGeneralContent.opportunityNameValue.setFocusableInTouchMode(true);
        binding.quotationGeneralContent.opportunityNameValue.setFocusable(true);
        binding.quotationGeneralContent.opportunityNameValue.setClickable(true);

        binding.quotationGeneralContent.contactPersonSpinner.setEnabled(true);
        binding.quotationGeneralContent.salesemployeeSpinner.setEnabled(true);

        binding.quotationGeneralContent.postingValue.setClickable(true);


        binding.quotationGeneralContent.postCal.setEnabled(true);
        binding.quotationGeneralContent.postCal.setClickable(true);
        binding.quotationGeneralContent.postCal.setFocusable(true);
        binding.quotationGeneralContent.postCal.setFocusableInTouchMode(true);


        binding.quotationGeneralContent.postingDate.setClickable(true);

        binding.quotationGeneralContent.validTillValue.setClickable(true);


        binding.quotationGeneralContent.validCal.setEnabled(true);
        binding.quotationGeneralContent.validCal.setClickable(true);
        binding.quotationGeneralContent.validDate.setClickable(true);

        binding.quotationGeneralContent.documentDateValue.setClickable(true);

        binding.quotationGeneralContent.docCal.setEnabled(true);
        binding.quotationGeneralContent.docCal.setClickable(true);

        binding.quotationGeneralContent.documentDate.setClickable(true);

        binding.quotationGeneralContent.remarkValue.setFocusableInTouchMode(true);
        binding.quotationGeneralContent.remarkValue.setFocusable(true);
        binding.quotationGeneralContent.remarkValue.setClickable(true);

        binding.quotationTotalContent.totalBeforeDiscontValue.setClickable(true);
        binding.quotationTotalContent.totalBeforeDiscontValue.setFocusable(true);
        binding.quotationTotalContent.totalBeforeDiscontValue.setFocusableInTouchMode(true);

        binding.quotationTotalContent.discontValue.setFocusableInTouchMode(true);
        binding.quotationTotalContent.discontValue.setFocusable(true);
        binding.quotationTotalContent.discontValue.setClickable(true);

        binding.quotationTotalContent.taxValue.setFocusableInTouchMode(true);
        binding.quotationTotalContent.taxValue.setFocusable(true);
        binding.quotationTotalContent.taxValue.setClickable(true);

        binding.quotationTotalContent.shippingValue.setFocusableInTouchMode(true);
        binding.quotationTotalContent.shippingValue.setFocusable(true);
        binding.quotationTotalContent.shippingValue.setClickable(true);

        binding.quotationTotalContent.totalValue.setFocusableInTouchMode(true);
        binding.quotationTotalContent.totalValue.setFocusable(true);
        binding.quotationTotalContent.totalValue.setClickable(true);

        binding.quotationTotalContent.nextButton.setFocusable(true);
        binding.quotationTotalContent.nextButton.setEnabled(true);
        binding.quotationTotalContent.nextButton.setClickable(true);

        binding.quotationPreparedForContent.addressSection.shippingNameValue.setFocusableInTouchMode(true);
        binding.quotationPreparedForContent.addressSection.shippingNameValue.setFocusable(true);
        binding.quotationPreparedForContent.addressSection.shippingNameValue.setClickable(true);

        binding.quotationPreparedForContent.addressSection.zipcodeValue2.setClickable(true);
        binding.quotationPreparedForContent.addressSection.zipcodeValue2.setFocusable(true);
        binding.quotationPreparedForContent.addressSection.zipcodeValue2.setFocusableInTouchMode(true);
        binding.quotationPreparedForContent.addressSection.shippingSpinner2.setEnabled(true);

        binding.quotationPreparedForContent.addressSection.doneButton.setClickable(true);
        binding.quotationPreparedForContent.addressSection.doneButton.setFocusable(true);
        binding.quotationPreparedForContent.addressSection.doneButton.setEnabled(true);

        binding.quotationPreparedForContent.addressSection.shippingAddressValue.setFocusableInTouchMode(true);
        binding.quotationPreparedForContent.addressSection.shippingAddressValue.setFocusable(true);
        binding.quotationPreparedForContent.addressSection.shippingAddressValue.setClickable(true);

        binding.quotationPreparedForContent.addressSection.billingAddressValue.setClickable(true);
        binding.quotationPreparedForContent.addressSection.billingAddressValue.setFocusable(true);
        binding.quotationPreparedForContent.addressSection.billingAddressValue.setFocusableInTouchMode(true);

        binding.quotationPreparedForContent.addressSection.billingNameValue.setFocusableInTouchMode(true);
        binding.quotationPreparedForContent.addressSection.billingNameValue.setFocusable(true);
        binding.quotationPreparedForContent.addressSection.billingNameValue.setClickable(true);

        binding.quotationPreparedForContent.addressSection.zipCodeValue.setClickable(true);
        binding.quotationPreparedForContent.addressSection.zipCodeValue.setFocusable(true);
        binding.quotationPreparedForContent.addressSection.zipCodeValue.setFocusableInTouchMode(true);
        binding.quotationPreparedForContent.addressSection.shippingSpinner.setEnabled(true);
        binding.quotationPreparedForContent.addressSection.countryValue.setClickable(true);
        binding.quotationPreparedForContent.addressSection.stateValue.setClickable(true);
        binding.quotationPreparedForContent.addressSection.shipStateValue.setClickable(true);
        binding.quotationPreparedForContent.addressSection.shipCountryValue.setClickable(true);
        binding.headerBottomroundEdit.add.setVisibility(View.GONE);
        binding.headerBottomroundEdit.ok.setVisibility(View.VISIBLE);

        ESCAPED = false;
        DISABLED = true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SelectItemCode) {
            binding.quotationTotalContent.itemsCount.setText("Item (" + Globals.SelectedItems.size() + ")");
            binding.quotationTotalContent.totalBeforeDiscontValue.setText(String.valueOf(Globals.calculatetotalofitem(Globals.SelectedItems)));
            binding.quotationTotalContent.taxValue.setText("10");
            binding.quotationTotalContent.totalValue.setText(String.valueOf(Globals.calculatetotal(10, Double.parseDouble(binding.quotationTotalContent.totalBeforeDiscontValue.getText().toString()))));


        }

    }

    private void frameManager(FrameLayout visiblle_frame, FrameLayout f1, FrameLayout f2,
                              TextView selected, TextView t1, TextView t2) {
        selected.setTextColor(getResources().getColor(R.color.colorPrimary));
        t1.setTextColor(getResources().getColor(R.color.black));
        t2.setTextColor(getResources().getColor(R.color.black));

        visiblle_frame.setVisibility(View.VISIBLE);
        f1.setVisibility(View.GONE);
        f2.setVisibility(View.GONE);

    }



    private boolean validation(
            String stagesCode, String remark) {
        if (stagesCode.isEmpty()) {
            Globals.showMessage(getContext(), getString(R.string.can_not_empty));
            return false;
        } else if (remark.isEmpty()) {
            Globals.showMessage(getContext(), getString(R.string.can_not_empty));
            return false;
        }

        return true;
    }


    /******************* Update API *********************/
    ArrayList<UpdateQTDocumentLines> postlist;

    private ArrayList<UpdateQTDocumentLines> postJson(ArrayList<QuotationDocumentLines> list) {
        postlist = new ArrayList<>();
        for (int i = 0; i < list.size(); i++
        ) {
            UpdateQTDocumentLines dc = new UpdateQTDocumentLines();
            dc.setLineNum(list.get(i).getLineNum());
            dc.setItemCode(list.get(i).getItemCode());
            dc.setQuantity(list.get(i).getQuantity());
            dc.setTaxCode(list.get(i).getTaxCode());//BED+VAT
            dc.setUnitPrice(list.get(i).getUnitPrice());
            dc.setDiscountPercent(Float.valueOf(list.get(i).getDiscountPercent()));
            postlist.add(dc);
        }

        return postlist;
    }

    private ArrayList<UpdateQTDocumentLines> postJsonCopy(ArrayList<DocumentLines> list, ArrayList<QuotationDocumentLines> existingList) {
        int docNum = existingList.size();
        postlist = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            UpdateQTDocumentLines dc = new UpdateQTDocumentLines();
            if (i < docNum) {
                dc.setLineNum(existingList.get(i).getLineNum());
            } else {
                docNum++;
                dc.setLineNum("" + docNum);
            }

            dc.setItemCode(list.get(i).getItemCode());
            dc.setQuantity(list.get(i).getQuantity());
            dc.setTaxCode(list.get(i).getTaxCode());//BED+VAT
            dc.setUnitPrice(list.get(i).getUnitPrice());
            dc.setItemDescription(list.get(i).getItemName());
            dc.setDiscountPercent(Float.valueOf("0.0"));
            postlist.add(dc);
        }

        return postlist;
    }


    float Total_Before_Disscount = 0;

    private float Total_Before_Disscount(ArrayList<DocumentLines> list) {
        float result = 0;
        for (int i = 0; i < list.size(); i++) {
            result = result + Float.parseFloat(list.get(i).getQuantity()) * Float.parseFloat(list.get(i).getUnitPrice());
        }
        return result;
    }

    private boolean valiadtion(String contactPerson, String postDate, String validDate,
                               String DocDate, String remarks) {
        if (contactPerson.isEmpty()) {
            Globals.showMessage(act, getResources().getString(R.string.can_not_empty));
            return false;
        } else if (validDate.isEmpty()) {
            Globals.showMessage(act, getResources().getString(R.string.can_not_empty));
            return false;
        } else if (DocDate.isEmpty()) {
            Globals.showMessage(act, getResources().getString(R.string.can_not_empty));
            return false;
        } else if (postDate.isEmpty()) {
            Globals.showMessage(act, getResources().getString(R.string.can_not_empty));
            return false;
        } else if (remarks.isEmpty()) {
            Globals.showMessage(act, getResources().getString(R.string.can_not_empty));
            return false;
        }
        return true;
    }

    private void updateQuotation(String QT_ID, UpdateQuotationModel in) {
        binding.loader.loader.setVisibility(View.VISIBLE);
        Call<QuotationResponse> call = NewApiClient.getInstance().getApiService().updateOrder(in);
        call.enqueue(new Callback<QuotationResponse>() {
            @Override
            public void onResponse(Call<QuotationResponse> call, Response<QuotationResponse> response) {
                binding.loader.loader.setVisibility(View.GONE);
                if (response.code() == 200) {
                    Globals.SelectedItems.clear();
                    Toast.makeText(act, "Posted Successfully.", Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                        Toast.makeText(act, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<QuotationResponse> call, Throwable t) {
                binding.loader.loader.setVisibility(View.GONE);
                Toast.makeText(act, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<ContactPersonData> ContactEmployeesList;
    ContactPersonAdapter contactPersonAdapter;

    private void callContactApi(String cardCode) {
        ContactEmployeesList = new ArrayList<>();
        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getContactEmployeeList(cardCode).observe(getActivity(), new Observer<List<ContactPersonData>>() {
            @Override
            public void onChanged(@Nullable List<ContactPersonData> itemsList) {
                if (itemsList == null || itemsList.size() == 0) {
                    Globals.setmessage(getActivity());
                } else {
                    ContactEmployeesList = itemsList;
                    contactPersonAdapter = new ContactPersonAdapter(getActivity(), ContactEmployeesList);
                    binding.quotationGeneralContent.contactPersonSpinner.setAdapter(contactPersonAdapter);
                    //int index = ContactEmployeesList.get


                    if (itemsList != null && ContactPersonCode != null)
                        binding.quotationGeneralContent.contactPersonSpinner.setSelection(Globals.getSelectedContact(itemsList, ContactPersonCode));

                }
            }
        });
    }

    SalesEmployeeAdapter salesadapter;
    public List<SalesEmployeeItem> salesEmployeeItemList = new ArrayList<>();

    private void callSalessApi() {
        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getSalesEmployeeList().observe(getActivity(), new Observer<List<SalesEmployeeItem>>() {
            @Override
            public void onChanged(@Nullable List<SalesEmployeeItem> itemsList) {
                if (itemsList == null || itemsList.size() == 0) {
                    Globals.setmessage(getActivity());
                } else {
                    salesEmployeeItemList = itemsList;
                    salesadapter = new SalesEmployeeAdapter(getActivity(), itemsList);
                    binding.quotationGeneralContent.salesemployeeSpinner.setAdapter(salesadapter);
                    if (!itemsList.isEmpty() && !salesPersonCode.isEmpty())
                        binding.quotationGeneralContent.salesemployeeSpinner.setSelection(Globals.getSelectedSalesP(itemsList, salesPersonCode));
                }
            }
        });
    }

}