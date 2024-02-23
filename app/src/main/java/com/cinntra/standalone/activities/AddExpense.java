package com.cinntra.standalone.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.standalone.R;
import com.cinntra.standalone.adapters.ImageViewAdapter;
import com.cinntra.standalone.adapters.PreviousImageViewAdapter;
import com.cinntra.standalone.adapters.SalesEmployeeAdapter;
import com.cinntra.standalone.databinding.AddExpenseBinding;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.globals.MainBaseActivity;
import com.cinntra.standalone.model.ExpenseResponse;
import com.cinntra.standalone.model.SalesEmployeeItem;
import com.cinntra.standalone.viewModel.ItemViewModel;
import com.cinntra.standalone.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddExpense extends MainBaseActivity implements View.OnClickListener {


//    @BindView(R.id.attachment)
//    Button attachment;
//    @BindView(R.id.loader)
//    ProgressBar loader;
//    @BindView(R.id.create)
//    Button create;
//    @BindView(R.id.prevattachment)
//    RecyclerView prevattachment;
//    @BindView(R.id.head_title)
//    TextView head_title;
//    @BindView(R.id.back)
//    ImageView back;
//    @BindView(R.id.expense_from_date)
//    EditText expense_from_date;
//    @BindView(R.id.expense_todate)
//    EditText expense_todate;
//    @BindView(R.id.amount)
//    EditText amount;
//    @BindView(R.id.remarks)
//    EditText remarks;
//    @BindView(R.id.tripname)
//    EditText tripname;
//    @BindView(R.id.sales_employee_spinner)
//    Spinner sales_employee_spinner;
//    @BindView(R.id.expense_type_spinner)
//    Spinner expense_type_spinner;

    int salesEmployeeCode = 0;

    private  final int REQUEST_CODE_CHOOSE = 1000;

    AddExpenseBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=AddExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
      //  ButterKnife.bind(this);
        binding.headerBottomRounded.headTitle.setText("Add Expense");
        eventmanager();
        if(Globals.checkInternet(this)) {
            callSalessApi();

        }






    }


    public List<SalesEmployeeItem> salesEmployeeItemList = new ArrayList<>();
    private void callSalessApi()
    {
        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getSalesEmployeeList().observe(this, new Observer<List<SalesEmployeeItem>>() {
            @Override
            public void onChanged(@Nullable List<SalesEmployeeItem> itemsList) {
                if(itemsList == null || itemsList.size() == 0){
                    Globals.setmessage(AddExpense.this);
                }else {
                    salesEmployeeItemList = itemsList;
                    binding.salesEmployeeSpinner.setAdapter(new SalesEmployeeAdapter(AddExpense.this,itemsList));
                    salesEmployeeCode = Integer.parseInt(salesEmployeeItemList.get(0).getSalesEmployeeCode());

                }
            }
        });

    }
    private void eventmanager() {

        binding.expenseFromDate.setOnClickListener(this);
        binding.expenseTodate.setOnClickListener(this);
        binding.attachment.setOnClickListener(this);
        binding.headerBottomRounded.back.setOnClickListener(this);
        binding.create.setOnClickListener(this);

        binding.salesEmployeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                salesEmployeeCode = Integer.parseInt(salesEmployeeItemList.get(i).getSalesEmployeeCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                salesEmployeeCode = Integer.parseInt(salesEmployeeItemList.get(0).getSalesEmployeeCode());

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.expense_from_date:
                Globals.selectDate(this,binding.expenseFromDate);
            break;
            case R.id.expense_todate:
                Globals.selectDate(this,binding.expenseTodate);
                break;
            case R.id.attachment:
                openimageuploader();
                break;
            case R.id.back:
                onBackPressed();
                break;
            case R.id.create:
            if(binding.tripname.length()!=0){
                if (Globals.checkInternet(this)) {
                   binding.loader.loader.setVisibility(View.VISIBLE);
                    addexpenseApi();
                }
            }else{
                Toasty.warning(this,"Enter Trip Name").show();
            }

                break;
        }
    }

    private void addexpenseApi() {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("remarks", binding.remarks.getText().toString());
        builder.addFormDataPart("trip_name", binding.remarks.getText().toString());
        builder.addFormDataPart("expense_from", binding.expenseFromDate.getText().toString());
        builder.addFormDataPart("expense_to", binding.expenseTodate.getText().toString());
        builder.addFormDataPart("totalAmount", binding.amount.getText().toString());
        builder.addFormDataPart("createDate",Globals.getTodaysDatervrsfrmt());
        builder.addFormDataPart("createTime", Globals.getTCurrentTime());
        builder.addFormDataPart("createdBy", Globals.TeamSalesEmployeCode);
        builder.addFormDataPart("employeeId", String.valueOf(salesEmployeeCode));
        builder.addFormDataPart("type_of_expense", "Hotel Stay");
        if(mSelected.size()>0){
            for (int i = 0; i < mSelected.size(); i++) {
                File file = new File(path.get(i));
                builder.addFormDataPart("Attach", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
            }}
        else{
            builder.addFormDataPart("Attach", "", RequestBody.create(
                    MediaType.parse("multipart/form-data"),
                    ""));

        }
        MultipartBody requestBody = builder.build();
        Call<ExpenseResponse> call = NewApiClient.getInstance().getApiService().createexpense(requestBody);
        call.enqueue(new Callback<ExpenseResponse>(){
            @Override
            public void onResponse(Call<ExpenseResponse> call, Response<ExpenseResponse> response) {

                if(response.code()==200)
                {
                    if(response.body().getMessage().equalsIgnoreCase("successful")){

                        Toasty.success(AddExpense.this, "Add Successfully", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }
                    else{
                        Toasty.warning(AddExpense.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                    }

                }
                else
                {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    ExpenseResponse mError = new ExpenseResponse();
                    try {
                        String s =response.errorBody().string();
                        mError= gson.fromJson(s, ExpenseResponse.class);
                        Toast.makeText(AddExpense.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                }
               binding.loader.loader.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<ExpenseResponse> call, Throwable t) {
                binding.loader.loader.setVisibility(View.GONE);
                Toasty.error(AddExpense.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void openimageuploader() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Matisse.from(AddExpense.this)
                                    .choose(MimeType.ofAll())
                                    .countable(true)
                                    .maxSelectable(5)
                                    .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                    .thumbnailScale(0.85f)
                                    .imageEngine(new GlideEngine())
                                    .showPreview(false) // Default is `true`
                                    .forResult(REQUEST_CODE_CHOOSE);
                           /* Intent intent = new Intent();

                            // setting type to select to be image
                            intent.setType("image/*");

                            // allowing multiple image to be selected
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_CHOOSE);*/
                        } else {
                            Toast.makeText(AddExpense.this, "Please enable permission", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    List<Uri> mSelected = new ArrayList<>();
    List<String> path = new ArrayList<>();
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK && null != data) {

            //mSelected.add(data.getData());
            mSelected = Matisse.obtainResult(data);
            for (int i = 0; i < mSelected.size(); i++) {
                path.add(FileUtils.getPath(AddExpense.this, mSelected.get(i)));
            }

            ImageViewAdapter adapter = new ImageViewAdapter( this,mSelected);
            binding.prevattachment.setLayoutManager(new GridLayoutManager(this,1,GridLayoutManager.HORIZONTAL,false));
            binding.prevattachment.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            // Get the Image from data
        } else {
            // show this if no image is selected
            Toast.makeText(AddExpense.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }

    }

}
