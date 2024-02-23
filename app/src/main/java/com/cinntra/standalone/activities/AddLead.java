package com.cinntra.standalone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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

import com.cinntra.standalone.R;
import com.cinntra.standalone.adapters.LeadTypeAdapter;
import com.cinntra.standalone.databinding.CreateLeadFromBinding;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.globals.MainBaseActivity;
import com.cinntra.standalone.model.CreateLead;
import com.cinntra.standalone.model.LeadTypeData;
import com.cinntra.standalone.model.LeadTypeResponse;
import com.cinntra.standalone.newapimodel.LeadResponse;
import com.cinntra.standalone.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.util.ArrayList;


import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddLead extends MainBaseActivity {


//    @BindView(R.id.create)
//    Button done;
//    @BindView(R.id.companyname)
//    EditText companyname;
//    @BindView(R.id.comment)
//    EditText comment;
//    @BindView(R.id.product_interest)
//    EditText product_interest;
//    @BindView(R.id.source_spinner)
//    Spinner source;
//    @BindView(R.id.location)
//    EditText location;
//    @BindView(R.id.email)
//    EditText email;
//    @BindView(R.id.turnover)
//    EditText turnover;
//    @BindView(R.id.designation)
//    EditText designation;
//    @BindView(R.id.contact_no)
//    EditText contact_no;
//    @BindView(R.id.full_name)
//    EditText full_name;
//    @BindView(R.id.back)
//    ImageView back;
//    @BindView(R.id.head_title)
//    TextView head_title;
//    @BindView(R.id.loader)
//    ProgressBar loader;
//    @BindView(R.id.status_spinner)
//    Spinner status_spinner;
//    @BindView(R.id.leadType_spinner)
//    Spinner leadType_spinner;
//    @BindView(R.id.itemCount)
//    TextView itemCount;
//    @BindView(R.id.item_frame)
//    RelativeLayout item_frame;
    public int ITEMSVIEWCODE = 10000;


    String status = "Follow up";
    String leadtype = "";
    String sourcetype = "";
    ArrayList<LeadTypeData> leadTypeData = new ArrayList<>();
    ArrayList<LeadTypeData> sourceData = new ArrayList<>();
    CreateLeadFromBinding binding;


    @Override
    protected void onResume() {
        super.onResume();
        binding.itemCount.setText("Item (" + Globals.SelectedItems.size() + ")");

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=CreateLeadFromBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       // ButterKnife.bind(this);
        binding.headerBottomRounded.headTitle.setText("Add Lead");
        Globals.SelectedItems.clear();
        callleadTypeApi();
        eventmanager();

        binding.itemFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Globals.SelectedItems.size() == 0) {
                    /*Globals.ItemType = "Paid";
                    openCategorydailog();*/
                    Intent intent = new Intent(AddLead.this, ItemsList.class);
                    intent.putExtra("CategoryID", 0);
                    startActivityForResult(intent, ITEMSVIEWCODE);
                } else {
                    Intent intent = new Intent(AddLead.this, SelectedItems.class);
                    intent.putExtra("FromWhere", "addQt");
                    startActivityForResult(intent, ITEMSVIEWCODE);
                }
            }
        });

    }

    private void callleadTypeApi() {

//        Call<LeadTypeResponse> call = NewApiClient.getInstance().getApiService().getLeadType();
//        call.enqueue(new Callback<LeadTypeResponse>() {
//            @Override
//            public void onResponse(Call<LeadTypeResponse> call, Response<LeadTypeResponse> response) {
//
//                if(response.code()==200)
//                {
//                   leadTypeData.clear();
//                   leadTypeData.addAll(response.body().getData());
//                    leadType_spinner.setAdapter(new LeadTypeAdapter(AddLead.this,leadTypeData));
//                    leadtype = leadTypeData.get(0).getName();
//                }
//                else
//                {
//                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
//                    Gson gson = new GsonBuilder().create();
//                    LeadResponse mError = new LeadResponse();
//                    try {
//                        String s =response.errorBody().string();
//                        mError= gson.fromJson(s, LeadResponse.class);
//                        Toast.makeText(AddLead.this, mError.getMessage(), Toast.LENGTH_LONG).show();
//                    } catch (IOException e) {
//                        //handle failure to read error
//                    }
//                }
//                loader.setVisibility(View.GONE);
//            }
//            @Override
//            public void onFailure(Call<LeadTypeResponse> call, Throwable t) {
//                loader.setVisibility(View.GONE);
//                Toast.makeText(AddLead.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

        leadTypeData.clear();
        leadTypeData.addAll(MainActivity.leadTypeListFromLocal);
        binding.leadTypeSpinner.setAdapter(new LeadTypeAdapter(AddLead.this, leadTypeData));
        leadtype = leadTypeData.get(0).getName();

        callSourceApi();
    }

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
//                    source.setAdapter(new LeadTypeAdapter(AddLead.this,sourceData));
//                    sourcetype = sourceData.get(0).getName();
//                }
//                else
//                {
//                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
//                    Gson gson = new GsonBuilder().create();
//                    LeadResponse mError = new LeadResponse();
//                    try {
//                        String s =response.errorBody().string();
//                        mError= gson.fromJson(s, LeadResponse.class);
//                        Toast.makeText(AddLead.this, mError.getMessage(), Toast.LENGTH_LONG).show();
//                    } catch (IOException e) {
//                        //handle failure to read error
//                    }
//                }
//                loader.setVisibility(View.GONE);
//            }
//            @Override
//            public void onFailure(Call<LeadTypeResponse> call, Throwable t) {
//                loader.setVisibility(View.GONE);
//                Toast.makeText(AddLead.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

        sourceData.clear();
        sourceData.addAll(MainActivity.leadSourceListFromLocal);
        binding.sourceSpinner.setAdapter(new LeadTypeAdapter(AddLead.this, sourceData));
        sourcetype = sourceData.get(0).getName();


    }

    private void eventmanager() {

        binding.create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addcreatelead();
            }
        });

        binding.headerBottomRounded.backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
    }

    private void addcreatelead() {
        if (validation(binding.companyname, binding.fullName, binding.contactNo, binding.location, binding.comment, binding.email)) {


            CreateLead lv = new CreateLead();
            lv.setCompanyName(binding.companyname.getText().toString());
            lv.setContactPerson(binding.fullName.getText().toString());
            lv.setPhoneNumber(binding.contactNo.getText().toString());
            lv.setEmail(binding.email.getText().toString());
            lv.setLocation(binding.location.getText().toString());
            lv.setSource(sourcetype);
            lv.setProductInterest(binding.productInterest.getText().toString());
            lv.setAssignedTo(Globals.TeamEmployeeID);
            lv.setNumOfEmployee(10);
            lv.setTurnover(binding.turnover.getText().toString());
            lv.setDesignation(binding.designation.getText().toString());
            lv.setEmployeeId(Integer.valueOf(Prefs.getString(Globals.EmployeeID, "")));
            lv.setMessage(binding.comment.getText().toString());
            lv.setDate(Globals.getTodaysDatervrsfrmt());
            lv.setTimestamp(Globals.getTimestamp());
            lv.setStatus(status);
            lv.setLeadType(leadtype);
            lv.setAttach("");
            lv.setCaption("");

            if (Globals.checkInternet(AddLead.this)) {
                binding.loader.setVisibility(View.VISIBLE);
                callcreateLeadApi(lv);
            }
        }


    }

    private void callcreateLeadApi(CreateLead lv) {
        ArrayList<CreateLead> createLeads = new ArrayList<>();
        createLeads.add(lv);
        Call<LeadResponse> call = NewApiClient.getInstance().getApiService().createLead(createLeads);
        call.enqueue(new Callback<LeadResponse>() {
            @Override
            public void onResponse(Call<LeadResponse> call, Response<LeadResponse> response) {

                if (response.code() == 200) {
                    if (response.body().getMessage().equalsIgnoreCase("successful")) {

                        Globals.SelectedItems.clear();
                        Toasty.success(AddLead.this, "Add Successfully", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    } else {
                        Toasty.warning(AddLead.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }

                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    LeadResponse mError = new LeadResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, LeadResponse.class);
                        Toast.makeText(AddLead.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                }
                binding.loader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<LeadResponse> call, Throwable t) {
                binding.loader.setVisibility(View.GONE);
                Toasty.error(AddLead.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validation(EditText companyname, EditText full_name, EditText contact_no, EditText location, EditText product_interest, EditText email) {

        if (companyname.length() == 0) {
            companyname.requestFocus();
            companyname.setError("Enter Company Name");
            Toasty.warning(this, "Enter Company Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (full_name.length() == 0) {
            full_name.requestFocus();
            full_name.setError("Enter Full Name");
            Toasty.warning(this, "Enter Full Name", Toast.LENGTH_SHORT).show();

            return false;
        } else if (contact_no.length() != 10) {
            contact_no.requestFocus();
            contact_no.setError("Enter Contact No");
            Toasty.warning(this, "Enter Contact No", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!email.getText().toString().isEmpty()) {
            if (isvalidateemail()) {
                email.requestFocus();
                email.setError("This email address is not valid");
                return false;
            }
        }
        return true;

    }

    private boolean isvalidateemail() {
        String checkEmail = binding.email.getText().toString();
        boolean hasSpecialEmail = Patterns.EMAIL_ADDRESS.matcher(checkEmail).matches();
        if (!hasSpecialEmail) {
            binding.email.setError("This email address is not valid");
            return true;
        }
        return false;
    }

}
