package com.cinntra.standalone.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.standalone.R;
import com.cinntra.standalone.activities.Login;
import com.cinntra.standalone.fragments.CampaignDetailFragment;
import com.cinntra.standalone.fragments.ExpenseDetailFragment;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.model.CampaignModel;
import com.cinntra.standalone.model.ExpenseDataModel;
import com.cinntra.standalone.model.ExpenseResponse;
import com.cinntra.standalone.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {
    Context context;
    List<ExpenseDataModel> leadValueList;
    public ExpenseAdapter(Context c, List<ExpenseDataModel> leadValueList) {
        this.context =c;
        this.leadValueList = leadValueList;
        this.tempList=new ArrayList<>();
        tempList.addAll(leadValueList);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.expense_adapter_screen,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExpenseDataModel lv = leadValueList.get(position);
        holder.customerName.setText(lv.getTripName());
        holder.date.setText(lv.getCreateDate());

        holder.cardNumber.setText("₹ "+lv.getTotalAmount());

        holder.assigned_view.setVisibility(View.VISIBLE);
        holder.assigned.setText(": " +lv.getTypeOfExpense());

        holder.option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openConfirmDialog(lv.getId());
            }
        });
        // holder.amount.setText("Rs:" + lv.getTurnover());

    }



    private void openConfirmDialog(Integer id) {

        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("You Want to Delete!")
                .setConfirmText("Yes,Delete!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        deleteapi(id);

                    }
                })

                .show();

    }

    private void deleteapi(Integer id) {
        List<String> ls = new ArrayList<>();
        ls.add(id.toString());
        HashMap<String,List<String>>  hd = new HashMap<>();
        hd.put("id",ls);
        Call<ExpenseResponse> call = NewApiClient.getInstance().getApiService().deleteexpense(hd);
        call.enqueue(new Callback<ExpenseResponse>(){
            @Override
            public void onResponse(Call<ExpenseResponse> call, Response<ExpenseResponse> response) {

                if(response.code()==200)
                {
                    if(response.body().getMessage().equalsIgnoreCase("successful")){

                        Toasty.success(context, "Deleted Successfully", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toasty.warning(context,response.body().getMessage(),Toast.LENGTH_LONG).show();
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
                        Toast.makeText(context, mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                }}
            @Override
            public void onFailure(Call<ExpenseResponse> call, Throwable t) {

                Toasty.error(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return leadValueList.size();
    }

    List<ExpenseDataModel>  tempList =null ;
    public void filter(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault());
        leadValueList.clear();
        if (charText.length() == 0) {
            leadValueList.addAll(tempList);
        } else {
            for (ExpenseDataModel st : tempList) {
                if(st.getTripName()!=null&&!st.getTripName().isEmpty()) {
                    if (st.getTripName().toLowerCase(Locale.getDefault()).contains(charText)) {
                        leadValueList.add(st);
                    }
                }
            }

        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView customerName,cardNumber,date,assigned;
        LinearLayout assigned_view;
        LinearLayoutCompat option;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.item_title);
            cardNumber   = itemView.findViewById(R.id.price);
            date   = itemView.findViewById(R.id.item_date);
            assigned   = itemView.findViewById(R.id.assigned);
            assigned_view   = itemView.findViewById(R.id.assigned_view);
            option   = itemView.findViewById(R.id.option);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    b.putSerializable(Globals.ExpenseData, leadValueList.get(getAdapterPosition()));
                    ExpenseDetailFragment fragment = new ExpenseDetailFragment();
                    fragment.setArguments(b);
                    FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.customer_lead, fragment);
                    transaction.addToBackStack("Back");
                    transaction.commit();
                }
            });


        }

    }
}