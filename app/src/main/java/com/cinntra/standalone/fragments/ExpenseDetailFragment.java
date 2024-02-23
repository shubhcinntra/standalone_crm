package com.cinntra.standalone.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.standalone.R;
import com.cinntra.standalone.activities.AddExpense;
import com.cinntra.standalone.adapters.PreviousImageViewAdapter;
import com.cinntra.standalone.adapters.SalesEmployeeAdapter;
import com.cinntra.standalone.databinding.AddExpenseBinding;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.model.CampaignModel;
import com.cinntra.standalone.model.ExpenseDataModel;
import com.cinntra.standalone.model.ExpenseResponse;
import com.cinntra.standalone.model.SalesEmployeeItem;
import com.cinntra.standalone.viewModel.ItemViewModel;
import com.cinntra.standalone.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpenseDetailFragment extends Fragment implements View.OnClickListener {




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
    ExpenseDataModel expenseDataModel;

    AddExpenseBinding binding;

    int salesEmployeeCode = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b      = getArguments();
            expenseDataModel =(ExpenseDataModel) b.getSerializable(Globals.ExpenseData);

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=AddExpenseBinding.inflate(inflater,container,false);
        View v=inflater.inflate(R.layout.add_expense, container, false);
      //  ButterKnife.bind(this,v);
        if(Globals.checkInternet(getContext())){
            callSalessApi();
        }
        binding.loader.loader.setVisibility(View.VISIBLE);
        binding.headerBottomRounded.back.setOnClickListener(this);
        binding.expenseFromDate.setOnClickListener(this);
        binding.expenseTodate.setOnClickListener(this);
        binding.create.setOnClickListener(this);
        binding.headerBottomRounded.headTitle.setText("Edit expense");
        binding.attachment.setVisibility(View.GONE);
        binding.create.setText("Update");
        setdata();

        binding.salesEmployeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(salesEmployeeItemList.size()>0){
                    salesEmployeeCode = Integer.parseInt(salesEmployeeItemList.get(i).getSalesEmployeeCode());
                }            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                if(salesEmployeeItemList.size()>0){
                    salesEmployeeCode = Integer.parseInt(salesEmployeeItemList.get(0).getSalesEmployeeCode());
                }
            }
        });


        return binding.getRoot();
    }

    private void setdata() {

        binding.tripname.setText(expenseDataModel.getTripName());
        binding.expenseFromDate.setText(expenseDataModel.getExpenseFrom());
        binding.expenseTodate.setText(expenseDataModel.getExpenseTo());
        binding.amount.setText(expenseDataModel.getTotalAmount().toString());
        binding.remarks.setText(expenseDataModel.getRemarks());
        PreviousImageViewAdapter adapter = new PreviousImageViewAdapter( getContext(),expenseDataModel.getAttach());
        binding.prevattachment.setLayoutManager(new GridLayoutManager(getContext(),1,GridLayoutManager.HORIZONTAL,false));
        binding.prevattachment.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        binding.loader.loader.setVisibility(View.GONE);

    }

    public List<SalesEmployeeItem> salesEmployeeItemList = new ArrayList<>();
    private void callSalessApi()
    {
        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getSalesEmployeeList().observe(getActivity(), new Observer<List<SalesEmployeeItem>>() {
            @Override
            public void onChanged(@Nullable List<SalesEmployeeItem> itemsList) {
                if(itemsList == null || itemsList.size() == 0){
                    Globals.setmessage(getContext());
                }else {
                    salesEmployeeItemList = itemsList;
                    binding.salesEmployeeSpinner.setAdapter(new SalesEmployeeAdapter(getContext(),itemsList));
                    if(expenseDataModel.getEmployeeId().size()>0) {
                        salesEmployeeCode = expenseDataModel.getEmployeeId().get(0).getId();
                        binding.salesEmployeeSpinner.setSelection(Globals.getSelectedSalesP(salesEmployeeItemList, String.valueOf(salesEmployeeCode)));
                    }
                }
            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.expense_from_date:
                Globals.selectDate(getContext(), binding.expenseFromDate);
                break;
            case R.id.expense_todate:
                Globals.selectDate(getContext(), binding.expenseTodate);
                break;
            case R.id.back:
                getActivity().onBackPressed();
                break;
            case R.id.create:
                if(binding.tripname.length()!=0){
                    if(Globals.checkInternet(getContext())){
                        binding.loader.loader.setVisibility(View.VISIBLE);
                        updateexpenseApi();
                    }
                }else{
                    Toasty.warning(getContext(),"Enter Trip Name").show();
                }
                break;

        }
    }

    private void updateexpenseApi() {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", expenseDataModel.getId().toString());
        builder.addFormDataPart("remarks", binding.remarks.getText().toString());
        builder.addFormDataPart("trip_name", binding.tripname.getText().toString());
        builder.addFormDataPart("expense_from", binding.expenseFromDate.getText().toString());
        builder.addFormDataPart("expense_to", binding.expenseTodate.getText().toString());
        builder.addFormDataPart("totalAmount", binding.amount.getText().toString());
        builder.addFormDataPart("createDate",Globals.getTodaysDatervrsfrmt());
        builder.addFormDataPart("createTime", Globals.getTCurrentTime());
        builder.addFormDataPart("updateDate",Globals.getTodaysDatervrsfrmt());
        builder.addFormDataPart("createTime", Globals.getTCurrentTime());
        builder.addFormDataPart("updateTime", Globals.getTCurrentTime());
        builder.addFormDataPart("employeeId", String.valueOf(salesEmployeeCode));
        builder.addFormDataPart("updatedBy", Globals.TeamSalesEmployeCode);
        builder.addFormDataPart("type_of_expense", "Hotel Stay");
        builder.addFormDataPart("Attach", "", RequestBody.create(
                MediaType.parse("multipart/form-data"),
                expenseDataModel.getAttach().toString()));
        MultipartBody requestBody = builder.build();
        Call<ExpenseResponse> call = NewApiClient.getInstance().getApiService().updateexpense(requestBody);
        call.enqueue(new Callback<ExpenseResponse>(){
            @Override
            public void onResponse(Call<ExpenseResponse> call, Response<ExpenseResponse> response) {

                if(response.code()==200)
                {
                    if(response.body().getMessage().equalsIgnoreCase("successful")){
                        Toasty.success(getContext(), "Updated Successfully", Toast.LENGTH_LONG).show();

                        getActivity().onBackPressed();
                    }
                    else{
                        Toasty.warning(requireContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
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
                        Toast.makeText(requireContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                }
               binding.loader. loader.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<ExpenseResponse> call, Throwable t) {
                binding.loader.loader.setVisibility(View.GONE);
                Toasty.error(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
