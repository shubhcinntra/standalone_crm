package com.cinntra.standalone.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.standalone.R;
import com.cinntra.standalone.activities.AddBPCustomer;
import com.cinntra.standalone.activities.LeadsActivity;
import com.cinntra.standalone.fragments.CampaignDetailFragment;
import com.cinntra.standalone.fragments.CreateBPSelectionSheet;
import com.cinntra.standalone.fragments.LeadBottomsheetFragment;
import com.cinntra.standalone.fragments.LeadDetail;
import com.cinntra.standalone.fragments.LeadInformation;
import com.cinntra.standalone.fragments.Opportunity_Detail_NewFragment;
import com.cinntra.standalone.fragments.ReminderSelectionSheet;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.model.CampaignModel;
import com.cinntra.standalone.model.ChatModel;
import com.cinntra.standalone.model.ChatResponse;
import com.cinntra.standalone.model.FollowUpData;
import com.cinntra.standalone.model.FollowUpResponse;
import com.cinntra.standalone.model.QuotationResponse;
import com.cinntra.standalone.newapimodel.LeadValue;
import com.cinntra.standalone.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class CampaignAdapter extends RecyclerView.Adapter<CampaignAdapter.ViewHolder> {
    Context context;
    List<CampaignModel> leadValueList;
    public CampaignAdapter(Context c, List<CampaignModel> leadValueList) {
        this.context =c;
        this.leadValueList = leadValueList;
        this.tempList=new ArrayList<>();
        tempList.addAll(leadValueList);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.campaign_adapter_screen,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CampaignModel lv = leadValueList.get(position);
        holder.customerName.setText(lv.getCampaignSetName());
        holder.date.setText(lv.getCreateDate());

        if(lv.getStatus() == 0){
            holder.cardNumber.setText(": InActive");
            holder.cardNumber.setTextColor(ContextCompat.getColor(context, R.color.red));
        }else{
            holder.cardNumber.setText(": Active");
            holder.cardNumber.setTextColor(ContextCompat.getColor(context, R.color.green));
        }

        holder.assigned_view.setVisibility(View.VISIBLE);
        holder.assigned.setText(": " +lv.getCampaignAccess());

        // holder.amount.setText("Rs:" + lv.getTurnover());

    }








    @Override
    public int getItemCount() {
        return leadValueList.size();
    }

    List<CampaignModel>  tempList =null ;
    public void filter(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault());
        leadValueList.clear();
        if (charText.length() == 0) {
            leadValueList.addAll(tempList);
        } else {
            for (CampaignModel st : tempList) {
                if(st.getCampaignSetName()!=null&&!st.getCampaignSetName().isEmpty()) {
                    if (st.getCampaignSetName().toLowerCase(Locale.getDefault()).contains(charText)) {
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.item_title);
            cardNumber   = itemView.findViewById(R.id.price);
            date   = itemView.findViewById(R.id.item_date);
            assigned   = itemView.findViewById(R.id.assigned);
            assigned_view   = itemView.findViewById(R.id.assigned_view);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    b.putSerializable(Globals.CampaignData, leadValueList.get(getAdapterPosition()));
                    CampaignDetailFragment fragment = new CampaignDetailFragment();
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