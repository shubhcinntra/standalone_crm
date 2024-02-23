package com.cinntra.standalone.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.cinntra.standalone.activities.AddPaymentDetails;
import com.cinntra.standalone.activities.MainActivity;
import com.cinntra.standalone.adapters.PreviousImageViewAdapter;
import com.cinntra.standalone.adapters.SalesEmployeeAdapter;
import com.cinntra.standalone.databinding.AddPaymentDetailsBinding;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.model.BusinessPartnerData;
import com.cinntra.standalone.model.ExpenseDataModel;
import com.cinntra.standalone.model.ExpenseResponse;
import com.cinntra.standalone.model.PaymentDetailsModel;
import com.cinntra.standalone.model.SalesEmployeeItem;
import com.cinntra.standalone.viewModel.CustomerViewModel;
import com.cinntra.standalone.viewModel.ItemViewModel;
import com.cinntra.standalone.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

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

public class PaymentDetailFragment extends Fragment implements View.OnClickListener {


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
//    EditText payment_date;
//    @BindView(R.id.expense_todate)
//    EditText transactionid;
//    @BindView(R.id.amount)
//    EditText totalamount;
//    @BindView(R.id.receivedamount)
//    EditText receivedamount;
//    @BindView(R.id.dueamount)
//    EditText dueamount;
//    @BindView(R.id.remarks)
//    EditText remarks;
//    @BindView(R.id.tripname)
//    EditText invoice_id;
//    @BindView(R.id.sales_employee_spinner)
//    Spinner sales_employee_spinner;
//    @BindView(R.id.expense_type_spinner)
//    Spinner expense_type_spinner;

    PaymentDetailsModel expenseDataModel;

    String customername, TransactMod, customercode;
    ArrayList<BusinessPartnerData> AllitemsList = new ArrayList<>();

    AddPaymentDetailsBinding binding;

    JSONObject jsonObject = new JSONObject();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b = getArguments();
            expenseDataModel = (PaymentDetailsModel) b.getSerializable(Globals.paymentDetailsData);

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = AddPaymentDetailsBinding.inflate(inflater, container, false);
        View v = inflater.inflate(R.layout.add_payment_details, container, false);
        // ButterKnife.bind(this,v);
        if (Globals.checkInternet(getContext())) {
            callcustomerApi();

        }
        binding.loader.loader.setVisibility(View.VISIBLE);
        binding.headerBottomRounded.back.setOnClickListener(this);
        binding.expenseFromDate.setOnClickListener(this);
        binding.create.setOnClickListener(this);
        binding.headerBottomRounded.headTitle.setText("Edit Payment Details");
        binding.attachment.setVisibility(View.GONE);
        binding.create.setText("Update");
        eventmanger();
        setData();

        return binding.getRoot();
    }

    private void eventmanger() {
        binding.expenseTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TransactMod = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                TransactMod = adapterView.getSelectedItem().toString();

            }
        });

        binding.salesEmployeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                customername = AllitemsList.get(i).getCardName();
                customercode = AllitemsList.get(i).getCardCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                customername = AllitemsList.get(0).getCardName();
                customercode = AllitemsList.get(0).getCardCode();
            }
        });
    }

    private void setData() {

        binding.tripname.setText(expenseDataModel.getInvoiceNo());
        binding.expenseTodate.setText(expenseDataModel.getTransactId());
        binding.amount.setText(expenseDataModel.getTotalAmt());
        binding.receivedamount.setText(expenseDataModel.getReceivedAmount());
        binding.dueamount.setText(expenseDataModel.getDueAmount());
        binding.remarks.setText(expenseDataModel.getRemarks());
        binding.expenseFromDate.setText(expenseDataModel.getPaymentDate());
        ArrayAdapter<String> transactionmodadpter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.transaction_mode));
        binding.expenseTypeSpinner.setAdapter(transactionmodadpter);
        binding.expenseTypeSpinner.setSelection(transactionmodadpter.getPosition(expenseDataModel.getTransactMod()));


        PreviousImageViewAdapter adapter = new PreviousImageViewAdapter(getContext(), expenseDataModel.getAttach());
        binding.prevattachment.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false));
        binding.prevattachment.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        binding.loader.loader.setVisibility(View.GONE);
    }

    private int getcustomerposition(String customercode) {
        for (BusinessPartnerData bd : AllitemsList) {
            if (bd.getCardCode().equalsIgnoreCase(customercode)) {
                return AllitemsList.indexOf(bd);
            }
        }
        return 0;
    }


    private void callcustomerApi() {
//        CustomerViewModel model = ViewModelProviders.of(this).get(CustomerViewModel.class);
//        model.getCustomersList(loader).observe(getActivity(), new Observer<List<BusinessPartnerData>>() {
//            @Override
//            public void onChanged(@Nullable List<BusinessPartnerData> itemsList) {
//
//                if(itemsList.size()>0) {
//                    AllitemsList.clear();
//                    AllitemsList.addAll(itemsList);
//                    sales_employee_spinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, addDatatoCategoryList(AllitemsList)));
//                    customercode = expenseDataModel.getCardCode();
//                    sales_employee_spinner.setSelection(getcustomerposition(customercode));
//
//
//                }
//
//            }
//        });

        if (MainActivity.businessPartnerDataFromLocal.size() > 0) {
            AllitemsList.clear();
            AllitemsList.addAll(MainActivity.businessPartnerDataFromLocal);
            binding.salesEmployeeSpinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, addDatatoCategoryList(AllitemsList)));
            customercode = expenseDataModel.getCardCode();
            binding.salesEmployeeSpinner.setSelection(getcustomerposition(customercode));


        }

    }

    private ArrayList<String> addDatatoCategoryList(ArrayList<BusinessPartnerData> allitemsList) {
        ArrayList<String> bplist = new ArrayList<>();
        for (BusinessPartnerData bpdata : allitemsList) {
                   /* if(LeadID.isEmpty()){

                    }else{
                        if(bpdata.getU_LEADID().equalsIgnoreCase(LeadID)){
                            bplist.add(bpdata.getCardName());
                        }
                    }
*/
            bplist.add(bpdata.getCardName());
        }
        return bplist;
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

            case R.id.back:
                getActivity().onBackPressed();
                break;
            case R.id.create:
                if (validation(binding.tripname, binding.expenseFromDate, binding.expenseTodate, binding.amount, binding.receivedamount, binding.dueamount)) {
                    if (Globals.checkInternet(getContext())) {
                        binding.loader.loader.setVisibility(View.VISIBLE);
                        updatepaymentdetailsapi();
                    }
                }
                break;

        }
    }


    private boolean validation(EditText invoice_id, EditText payment_date, EditText transactionid, EditText totalamount, EditText receivedamount, EditText dueamount) {

        if (invoice_id.length() == 0) {
            invoice_id.setError("Enter Invoice Id");
            return false;
        } else if (payment_date.length() == 0) {
            payment_date.setError("Enter Payment Date");
            return false;
        } else if (transactionid.length() == 0) {
            transactionid.setError("Enter Transaction ID");
            return false;
        } else if (totalamount.length() == 0) {
            totalamount.setError("Enter Total Amount");
            return false;
        } else if (receivedamount.length() == 0) {
            receivedamount.setError("Enter Received Amount");
            return false;
        } else if (Integer.parseInt(totalamount.getText().toString()) < Integer.parseInt(receivedamount.getText().toString())) {
            Toasty.warning(getContext(), "Received amount will not be maximum then Total amount").show();
            return false;
        }
        return true;
    }

    private void updatepaymentdetailsapi() {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);


        builder.addFormDataPart("id", expenseDataModel.getId().toString());
        builder.addFormDataPart("Remarks", binding.remarks.getText().toString());
        builder.addFormDataPart("InvoiceNo", binding.tripname.getText().toString());
        builder.addFormDataPart("TransactId", binding.expenseTodate.getText().toString());
        builder.addFormDataPart("TotalAmt", binding.amount.getText().toString());
        builder.addFormDataPart("TransactMod", TransactMod);
        builder.addFormDataPart("DueAmount", binding.dueamount.getText().toString());
        builder.addFormDataPart("PaymentDate", binding.expenseFromDate.getText().toString());
        builder.addFormDataPart("ReceivedAmount", binding.receivedamount.getText().toString());
        builder.addFormDataPart("CardCode", customercode);
        builder.addFormDataPart("updateDate", Globals.getTodaysDatervrsfrmt());
        builder.addFormDataPart("updateTime", Globals.getTCurrentTime());
        builder.addFormDataPart("updatedBy", Globals.TeamSalesEmployeCode);
        builder.addFormDataPart("Attach", "", RequestBody.create(
                MediaType.parse("multipart/form-data"),
                expenseDataModel.getAttach().toString()));

        MultipartBody requestBody = builder.build();
        Call<ExpenseResponse> call = NewApiClient.getInstance().getApiService().updatepayment(requestBody);
        call.enqueue(new Callback<ExpenseResponse>() {
            @Override
            public void onResponse(Call<ExpenseResponse> call, Response<ExpenseResponse> response) {

                if (response.code() == 200) {

                    if (response.body().getMessage().equalsIgnoreCase("successful")) {
                        Toasty.success(getContext(), "Updated Successfully", Toast.LENGTH_LONG).show();

                        getActivity().onBackPressed();
                    } else {
                        Toasty.warning(requireContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }


                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    ExpenseResponse mError = new ExpenseResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, ExpenseResponse.class);
                        Toast.makeText(requireContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                }
               binding.loader. loader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ExpenseResponse> call, Throwable t) {
               binding.loader. loader.setVisibility(View.GONE);
                Toasty.error(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
