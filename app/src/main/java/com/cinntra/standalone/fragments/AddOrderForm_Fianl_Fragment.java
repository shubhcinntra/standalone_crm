package com.cinntra.standalone.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.cinntra.standalone.R;
import com.cinntra.standalone.activities.AddOrderAct;

import com.cinntra.standalone.activities.MainActivity;
import com.cinntra.standalone.adapters.CountryAdapter;
import com.cinntra.standalone.adapters.StateAdapter;
import com.cinntra.standalone.databinding.FragmentAddQtFinalBinding;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.interfaces.SubmitQuotation;
import com.cinntra.standalone.model.AddressExtension;
import com.cinntra.standalone.model.CountryData;
import com.cinntra.standalone.model.QuotationResponse;
import com.cinntra.standalone.model.StateData;
import com.cinntra.standalone.model.StateRespose;
import com.cinntra.standalone.room.CountriesDatabase;
import com.cinntra.standalone.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddOrderForm_Fianl_Fragment extends Fragment {

    SubmitQuotation submitQuotation;
    String [] shippinngType;
    String billshipType;
    String ship_shiptype;
    String billtoState,billtoStateCode,billtoCountrycode,billtoCountryName,shiptoState,shiptoCountrycode,shiptoCountryName,shiptoStateCode;
    CountryAdapter countryAdapter;
    StateAdapter stateAdapter,shipStateAdapter;
    ArrayList<StateData> stateList = new ArrayList<>();
    ArrayList<StateData> shipstateList = new ArrayList<>();
    public AddOrderForm_Fianl_Fragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AddOrderForm_Fianl_Fragment newInstance(String param1, String param2) {
        AddOrderForm_Fianl_Fragment fragment = new AddOrderForm_Fianl_Fragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    FragmentAddQtFinalBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAddQtFinalBinding.inflate(inflater,container,false);
        View v=inflater.inflate(R.layout.fragment_add_qt_final, container, false);
      //  ButterKnife.bind(this,v);
        binding.quotationPreparedForContent.addressSection.checkboxManager.setVisibility(View.VISIBLE);
        setDefaults();
        eventManageer();
        shippinngType  = getResources().getStringArray(R.array.shippingType);
        billshipType = shippinngType[0];
        ship_shiptype = shippinngType[0];
         submitQuotation = (SubmitQuotation)getActivity();

        binding.quotationPreparedForContent.addressSection.doneButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (validation(binding.quotationPreparedForContent.addressSection.billingNameValue.getText().toString().trim(),
                    binding.quotationPreparedForContent.addressSection.billingAddressValue.getText().toString().trim())) {
                AddressExtension addressExtension = new AddressExtension();
                if(binding.quotationPreparedForContent.addressSection.checkbox1.isChecked()){
                    addressExtension.setShipToZipCode(binding.quotationPreparedForContent.addressSection.zipcodeValue2.getText().toString().trim());
                    addressExtension.setShipToBuilding(binding.quotationPreparedForContent.addressSection.shippingNameValue.getText().toString().trim());
                    addressExtension.setShipToStreet(binding.quotationPreparedForContent.addressSection.shippingAddressValue.getText().toString().trim());
                    addressExtension.setU_SSTATE(shiptoState);
                    addressExtension.setU_SCOUNTRY(shiptoCountryName);
                    addressExtension.setU_SHPTYPS(ship_shiptype);
                    addressExtension.setShipToState(shiptoStateCode);
                    addressExtension.setShipToCountry(shiptoCountrycode);
                }else{
                    addressExtension.setShipToZipCode(binding.quotationPreparedForContent.addressSection.zipCodeValue.getText().toString().trim());
                    addressExtension.setShipToBuilding(binding.quotationPreparedForContent.addressSection.billingNameValue.getText().toString().trim());
                    addressExtension.setShipToStreet(binding.quotationPreparedForContent.addressSection.billingAddressValue.getText().toString().trim());
                    addressExtension.setU_SSTATE(billtoState);
                    addressExtension.setU_SCOUNTRY(billtoCountryName);
                    addressExtension.setU_SHPTYPS(billshipType);
                    addressExtension.setShipToState(billtoStateCode);
                    addressExtension.setShipToCountry(billtoCountrycode);
                }


                addressExtension.setShipToCity("");
               // addressExtension.setShipToZipCode(zipcode_value2.getText().toString());
                 //countryCode2
                addressExtension.setU_OPPRNM("");
                addressExtension.setU_BSTATE(billtoState);
                addressExtension.setU_BCOUNTRY(billtoCountryName);
                addressExtension.setBillToBuilding(binding.quotationPreparedForContent.addressSection.billingNameValue.getText().toString());
                addressExtension.setBillToStreet(binding.quotationPreparedForContent.addressSection.billingAddressValue.getText().toString());
                addressExtension.setBillToCity("");
                addressExtension.setBillToZipCode(binding.quotationPreparedForContent.addressSection.zipCodeValue.getText().toString());
                addressExtension.setBillToState(billtoStateCode);
                addressExtension.setBillToCountry(billtoCountrycode);   //countryCode
                addressExtension.setU_SHPTYPB(billshipType);
                AddOrderAct.addQuotationObj.setAddressExtension(addressExtension);
                if (Globals.checkInternet(getActivity()))
                    submitQuotation.submitQuotaion(binding.loader.loader);
            }
        }
        });
        return binding.getRoot();
    }

    private void eventManageer() {
        CountriesDatabase db = CountriesDatabase.getDatabase(requireActivity().getApplicationContext());
        List<CountryData> localData = db.myDataDao().getAll();
        ArrayList<CountryData> arrayList = new ArrayList<>(localData);

        countryAdapter = new CountryAdapter(getContext(), MainActivity.countrylistFromLocal);
        binding.quotationPreparedForContent.addressSection.countryValue.setAdapter(countryAdapter);
        binding.quotationPreparedForContent.addressSection.countryValue.setSelection(Globals.getCountrypos(arrayList,"India"));

        billtoCountrycode ="IN";
        billtoCountryName = "India";
        shiptoCountrycode = "IN";
        shiptoCountryName ="India";
        binding.quotationPreparedForContent.addressSection.countryValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                billtoCountrycode = arrayList.get(position).getCode();
                billtoCountryName = arrayList.get(position).getName();
                billtoState = "";
                billtoStateCode = "";
                callStateApi(billtoCountrycode,"billto");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                billtoCountrycode = arrayList.get(0).getCode();
                billtoCountryName = arrayList.get(0).getName();
            }
        });

        binding.quotationPreparedForContent.addressSection.stateValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                billtoState= stateList.get(position).getName();
                billtoStateCode= stateList.get(position).getCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                billtoState= stateList.get(0).getName();
                billtoStateCode= stateList.get(0).getCode();
            }
        });

        binding.quotationPreparedForContent.addressSection.shipCountryValue.setAdapter(countryAdapter);
        binding.quotationPreparedForContent.addressSection.shipCountryValue.setSelection(Globals.getCountrypos(MainActivity.countrylistFromLocal,"India"));

        binding.quotationPreparedForContent.addressSection.shipCountryValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                shiptoCountrycode = MainActivity.countrylistFromLocal.get(position).getCode();
                shiptoCountryName = MainActivity.countrylistFromLocal.get(position).getName();
                shiptoState = "";
                shiptoStateCode = "";
                callStateApi(shiptoCountrycode,"shipto");
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
                shiptoState= stateList.get(position).getName();
                shiptoStateCode= stateList.get(position).getCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                shiptoState= stateList.get(0).getName();
                shiptoStateCode= stateList.get(0).getCode();
            }
        });



        binding.quotationPreparedForContent.addressSection.checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    binding.quotationPreparedForContent.addressSection.shipBlock.setVisibility(View.VISIBLE);
                }else{
                    binding.quotationPreparedForContent.addressSection.shipBlock.setVisibility(View.GONE);
                }
            }
        });



        binding.quotationPreparedForContent.addressSection.shippingSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                billshipType = shippinngType[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                billshipType = shippinngType[0];

            }
        });
        binding.quotationPreparedForContent.addressSection.shippingSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ship_shiptype = shippinngType[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ship_shiptype = shippinngType[0];
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

                if(response.code()==200)
                {
                    if(code.equalsIgnoreCase("billto")){
                        stateList.clear();
                        if(response.body().getData().size()>0) {

                            stateList.addAll(response.body().getData());
                        }else{
                            StateData sta = new StateData();
                            sta.setName("Select State");
                            stateList.add(sta);
                        }
                        stateAdapter = new StateAdapter(getContext(),stateList);
                        binding.quotationPreparedForContent.addressSection.stateValue.setAdapter(stateAdapter);
                        stateAdapter.notifyDataSetChanged();
                        billtoState= stateList.get(0).getName();
                        billtoStateCode= stateList.get(0).getCode();
                    }else{
                        shipstateList.clear();
                        if(response.body().getData().size()>0) {

                            shipstateList.addAll(response.body().getData());
                        }else{
                            StateData sta = new StateData();
                            sta.setName("Select State");
                            shipstateList.add(sta);
                        }
                        shipStateAdapter = new StateAdapter(getContext(),shipstateList);
                        binding.quotationPreparedForContent.addressSection.shipStateValue.setAdapter(shipStateAdapter);
                        shipStateAdapter.notifyDataSetChanged();
                        shiptoState= shipstateList.get(0).getName();
                        shiptoStateCode= shipstateList.get(0).getCode();
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
    private void setDefaults() {

        binding.headerBottomRounded.headTitle.setText(getResources().getString(R.string.add_order));
        binding.headerBottomRounded.backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }
    private boolean validation(String billingName,String bAddress){
        if(billingName.isEmpty()){
       Globals.showMessage(getActivity(),getResources().getString(R.string.can_not_empty));
       return  false;
        }
        else if(bAddress.isEmpty())
        {
       Globals.showMessage(getActivity(),getResources().getString(R.string.can_not_empty));
       return false;
        }
        return true;

    }

}