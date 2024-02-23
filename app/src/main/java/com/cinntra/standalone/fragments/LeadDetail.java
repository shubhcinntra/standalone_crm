package com.cinntra.standalone.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.cinntra.standalone.R;
import com.cinntra.standalone.activities.AddCampaign;
import com.cinntra.standalone.activities.MainActivity;
import com.cinntra.standalone.adapters.LeadTypeAdapter;
import com.cinntra.standalone.databinding.LeadDetailBinding;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.model.CreateLead;
import com.cinntra.standalone.model.LeadTypeData;
import com.cinntra.standalone.model.LeadTypeResponse;
import com.cinntra.standalone.newapimodel.LeadResponse;
import com.cinntra.standalone.newapimodel.LeadValue;
import com.cinntra.standalone.webservices.NewApiClient;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeadDetail extends Fragment implements View.OnClickListener {
//    @BindView(R.id.head_title)
//    TextView head_title;
//    @BindView(R.id.back_press)
//    RelativeLayout back_press;
//    @BindView(R.id.companyname)
//    EditText companyname;
//    @BindView(R.id.full_name)
//    EditText full_name;
//    @BindView(R.id.contact_no)
//    EditText contact_no;
//    @BindView(R.id.email)
//    EditText email;
//    @BindView(R.id.source)
//    EditText source;
//    @BindView(R.id.location)
//    EditText location;
//    @BindView(R.id.product_interest)
//    EditText product_interest;
//    @BindView(R.id.comment)
//    EditText comment;
//    @BindView(R.id.update)
//    Button update;
//     @BindView(R.id.loader)
//     SpinKitView loader;
//     @BindView(R.id.phone)
//     ImageView phone;
//    @BindView(R.id.turnover)
//    EditText turnover;
//    @BindView(R.id.designation)
//    EditText designation;
//    @BindView(R.id.status_spinner)
//    Spinner status_spinner;
//    @BindView(R.id.leadType_spinner)
//    Spinner leadType_spinner;



    LeadValue leadValue;
    Context leadsActivity;
    List<LeadValue> leadValues = new ArrayList<>();
    String[] leadstatus = new String[4];
    String status = "";
    String leadtype = "";
    Integer id;
    ArrayList<LeadTypeData> leadTypeData = new ArrayList<>();
    Context context;

    LeadDetailBinding binding;
    public LeadDetail(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b      = getArguments();
            if(b.getString("From").equalsIgnoreCase("Lead")) {
                leadValue = (LeadValue) b.getParcelable(Globals.LeadDetails);
                ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
                id = leadValue.getId();
            }
            else {
                id = Integer.parseInt(b.getString("From","2"));
                }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=LeadDetailBinding.inflate(inflater,container,false);
       // View v=inflater.inflate(R.layout.lead_detail, container, false);
      //  ButterKnife.bind(this,v);
        binding.headerBottomRounded.headTitle.setText("Lead Detail");
        binding.headerBottomRounded.backPress.setOnClickListener(this);
        leadstatus = getResources().getStringArray(R.array.lead_status);
        binding.loader.setVisibility(View.VISIBLE);
        eventManager();
        if(Globals.checkInternet(getContext())) {
            callApi(id);
        }
        else{
            Toast.makeText(getContext(),getResources().getString(R.string.check_internet), Toast.LENGTH_LONG).show();
        }



        return binding.getRoot();
    }


    private void callleadTypeApi(String type) {

//        Call<LeadTypeResponse> call = NewApiClient.getInstance().getApiService().getLeadType();
//        call.enqueue(new Callback<LeadTypeResponse>() {
//            @Override
//            public void onResponse(Call<LeadTypeResponse> call, Response<LeadTypeResponse> response) {
//
//                if(response.code()==200)
//                {
//                    leadTypeData.clear();
//                    leadTypeData.addAll(response.body().getData());
//                    leadType_spinner.setAdapter(new LeadTypeAdapter(getContext(),leadTypeData));
//                    leadType_spinner.setSelection(Globals.getleadType(leadTypeData,type));
//                }
//                else
//                {
//                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
//                    Gson gson = new GsonBuilder().create();
//                    LeadResponse mError = new LeadResponse();
//                    try {
//                        String s =response.errorBody().string();
//                        mError= gson.fromJson(s, LeadResponse.class);
//                        Toast.makeText(getContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
//                    } catch (IOException e) {
//                        //handle failure to read error
//                    }
//                }
//                loader.setVisibility(View.GONE);
//            }
//            @Override
//            public void onFailure(Call<LeadTypeResponse> call, Throwable t) {
//                loader.setVisibility(View.GONE);
//                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

        leadTypeData.clear();
        leadTypeData.addAll(MainActivity.leadTypeListFromLocal);
        binding.leadTypeSpinner.setAdapter(new LeadTypeAdapter(requireContext(), leadTypeData));
        binding.leadTypeSpinner.setSelection(Globals.getleadType(leadTypeData,type));


    }


    private void eventManager() {


        binding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation(binding.companyname, binding.fullName, binding.contactNo, binding.location, binding.comment, binding.email)) {

                    CreateLead lv = new CreateLead();
                    lv.setId(id);
                    lv.setCompanyName(binding.companyname.getText().toString());
                    lv.setContactPerson(binding.fullName.getText().toString());
                    lv.setPhoneNumber(binding.contactNo.getText().toString());
                    lv.setEmail(binding.email.getText().toString());
                    lv.setLocation(binding.location.getText().toString());
                    lv.setSource(binding.source.getText().toString());
                    lv.setProductInterest(binding.productInterest.getText().toString());
                    lv.setAssignedTo(leadValues.get(0).getAssignedTo().getId().toString());
                    lv.setNumOfEmployee(10);
                    lv.setTurnover(binding.turnover.getText().toString());
                    lv.setDesignation(binding.designation.getText().toString());
                    lv.setEmployeeId(leadValues.get(0).getEmployeeId().getId());
                    lv.setMessage(binding.comment.getText().toString());
                    lv.setDate(leadValues.get(0).getDate());
                    lv.setTimestamp(Globals.getTimestamp());
                    lv.setStatus(status);
                    lv.setLeadType(leadtype);
                    lv.setCaption("");
                    lv.setAttach("");
                    if (Globals.checkInternet(getContext())) {
                        binding.loader.setVisibility(View.VISIBLE);
                        callUpdateApi(lv);
                    }
                }
            }
        });

        binding.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.contactNo.getText().toString().trim().isEmpty()) {

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + binding.contactNo.getText().toString()));


                    Dexter.withActivity(getActivity())
                            .withPermission(Manifest.permission.CALL_PHONE)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse response) {
                                    startActivity(intent);
                                }

                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse response) {
                                    // check for permanent denial of permission
                                    if (response.isPermanentlyDenied()) {
                                        // navigate user to app settings

                                        Toast.makeText(getContext(),"Please Enable phone from setting",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                    token.continuePermissionRequest();
                                }
                            }).check();
                    // startActivity(intent);
                }
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

        binding.leadTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                leadtype = leadTypeData.get(position).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                leadtype = leadTypeData.get(0).getName();
            }
        });


    }

    private boolean validation(EditText companyname, EditText full_name, EditText contact_no, EditText location, EditText product_interest, EditText email) {

        if(companyname.length()==0){
            companyname.requestFocus();
            companyname.setError("Enter Company Name");
            Toasty.warning(requireContext(),"Enter Company Name",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(full_name.length()==0){
            full_name.requestFocus();
            full_name.setError("Enter Full Name");
            Toasty.warning(requireContext(),"Enter Full Name",Toast.LENGTH_SHORT).show();

            return false;
        }
        else if(contact_no.length()!=10){
            contact_no.setError("Enter Contact No");
            contact_no.requestFocus();
            Toasty.warning(requireContext(),"Enter Contact No",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!email.getText().toString().isEmpty()){
            if(Globals.isvalidateemail(email)){
                email.requestFocus();
                email.setError("Email address is not valid");
                return false;
            }
        }
        return true;

    }


    private void callUpdateApi(CreateLead lv) {
        Call<LeadResponse> call = NewApiClient.getInstance().getApiService().updateLead(lv);
        call.enqueue(new Callback<LeadResponse>() {
            @Override
            public void onResponse(Call<LeadResponse> call, Response<LeadResponse> response) {

                if(response.code()==200)
                {
                    if(response.body().getMessage().equalsIgnoreCase("successful")){
                        Toasty.success(getContext(), "Updated Successfully", Toast.LENGTH_LONG).show();
                        getActivity().onBackPressed();
                    }
                    else{
                        Toasty.warning(getContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                    }

                }
                else
                {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    LeadResponse mError = new LeadResponse();
                    try {
                        String s =response.errorBody().string();
                        mError= gson.fromJson(s, LeadResponse.class);
                        Toast.makeText(getActivity(), mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                }
                binding.loader.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<LeadResponse> call, Throwable t) {
                binding.loader.setVisibility(View.GONE);
                Toasty.error(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callApi(int id) {
        LeadValue lv = new LeadValue();
        lv.setId(id);
        Call<LeadResponse> call = NewApiClient.getInstance().getApiService().particularlead(lv);
        call.enqueue(new Callback<LeadResponse>() {
            @Override
            public void onResponse(Call<LeadResponse> call, Response<LeadResponse> response) {
                if (response != null)
                {
                    if(response.body()!=null){

                        leadValues = response.body().getData();
                        setData(leadValues.get(0));

                    }


                }
            }
            @Override
            public void onFailure(Call<LeadResponse> call, Throwable t) {

            }
        });
    }

    private void setData(LeadValue lv) {

        leadtype = lv.getLeadType();
        status = lv.getStatus();
        binding.statusSpinner.setSelection(getStatuspos(lv.getStatus()));
        binding.companyname.setText(lv.getCompanyName());
        binding.fullName.setText(lv.getContactPerson());
        binding.contactNo.setText(lv.getPhoneNumber());
        binding.email.setText(lv.getEmail());
        binding.location.setText(lv.getLocation());
        binding.source.setText(lv.getSource());
        binding.productInterest.setText(lv.getProductInterest());
        binding.turnover.setText(lv.getTurnover());
        binding.designation.setText(lv.getDesignation());
        binding.comment.setText(lv.getMessage());
        binding.loader.setVisibility(View.GONE);
        callleadTypeApi(lv.getLeadType());

    }

    private int getStatuspos(String status) {
        int pos = -1;
        for(int i =0;i<leadstatus.length;i++)
        {
            String data = leadstatus[i];
            if(data.equalsIgnoreCase(status)){
                pos = i;
            }
        }
        return pos;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_press:

                getActivity().onBackPressed();
                break;

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

    }
}
