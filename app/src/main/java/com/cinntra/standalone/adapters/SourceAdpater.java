package com.cinntra.standalone.adapters;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.standalone.R;
import com.cinntra.standalone.activities.AddBPCustomer;
import com.cinntra.standalone.activities.LeadsActivity;
import com.cinntra.standalone.fragments.CreateBPSelectionSheet;
import com.cinntra.standalone.fragments.LeadBottomsheetFragment;
import com.cinntra.standalone.fragments.LeadDetail;
import com.cinntra.standalone.fragments.LeadInformation;
import com.cinntra.standalone.fragments.ReminderSelectionSheet;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.model.ChatModel;
import com.cinntra.standalone.model.ChatResponse;
import com.cinntra.standalone.model.FollowUpData;
import com.cinntra.standalone.model.FollowUpResponse;
import com.cinntra.standalone.model.LeadTypeData;
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


public class SourceAdpater extends RecyclerView.Adapter<SourceAdpater.ViewHolder> {
    Context context;
    List<LeadTypeData> leadValueList;
    public SourceAdpater(Context c, List<LeadTypeData> leadValueList) {
        this.context =c;
        this.leadValueList = leadValueList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.multiselect_source,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LeadTypeData lv = leadValueList.get(position);
        holder.sourcecheck.setText(lv.getName());
        holder.sourcecheck.setChecked(leadValueList.get(position).isIschecked());

        holder.sourcecheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    leadValueList.get(position).setIschecked(true);
                    Globals.sourcechecklist.add(leadValueList.get(position).getName());
                }else{
                    leadValueList.get(position).setIschecked(false);
                    Globals.sourcechecklist.remove(leadValueList.get(position).getName());

                }
            }
        });
           }




    private void showBottomSheet(int id,String name)
    {

        ReminderSelectionSheet bottomSheetFragment = new ReminderSelectionSheet(id,name);
        bottomSheetFragment.show(((AppCompatActivity)context).getSupportFragmentManager(), bottomSheetFragment.getTag());

    }



    @Override
    public int getItemCount() {
        return leadValueList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       CheckBox sourcecheck;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sourcecheck = itemView.findViewById(R.id.sourcecheck);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  /*  if(!leadValueList.get(getAdapterPosition()).isIschecked()){
                        leadValueList.get(getAdapterPosition()).setIschecked(true);
                        Globals.sourcechecklist.add(leadValueList.get(getAdapterPosition()).getName());
                    }else{
                        leadValueList.get(getAdapterPosition()).setIschecked(false);
                        Globals.sourcechecklist.remove(leadValueList.get(getAdapterPosition()).getName());

                    }*/
                }
            });



        }
    }
}