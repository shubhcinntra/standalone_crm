package com.cinntra.standalone.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Explode;

import com.cinntra.standalone.R;
import com.cinntra.standalone.activities.MapsActivity;
import com.cinntra.standalone.fragments.PaymentCollection;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.model.BusinessPartnerData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


public class CustomersAdapterDetals extends RecyclerView.Adapter<CustomersAdapterDetals.ViewHolder> {
    Context context;
    List<BusinessPartnerData> customerList;

    public CustomersAdapterDetals(Context context, List<BusinessPartnerData> customerList) {

        this.context = context;
        this.customerList = customerList;
        this.tempList = new ArrayList<BusinessPartnerData>();
        this.tempList.addAll(customerList);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.customers_item, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final BusinessPartnerData obj = getItem(position);
        holder.customerName.setText(obj.getCardName());
        holder.cardNumber.setText("ID : Cust-" + obj.getId());
        holder.date.setText(obj.getCreateDate());
         /* try {
              if (obj.getUCurbal() != null && !obj.getUCurbal().isEmpty())
                  holder.amount.setText(Globals.getAmmount(obj.getCurrency(), Globals.changeDecemal(obj.getUCurbal())));
              if (obj.getCreditLimit() != null && !obj.getCreditLimit().isEmpty())
                  holder.credit_limit_value.setText(Globals.getAmmount(obj.getCurrency(), Globals.changeDecemal(obj.getCreditLimit())));
          }catch (Exception e){

          }*/

        if (obj.getUType().size() > 0)
            holder.amount.setText(obj.getUType().get(0).getType());
        else
            holder.amount.setText("N/A");
        if (obj.getURating() != null && obj.getURating().matches("\\d*\\.\\d\\d"))
            holder.ratingBar.setRating(Float.valueOf(obj.getURating()));
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public BusinessPartnerData getItem(int position) {
        return customerList.get(position);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView customerName, cardNumber, date, amount, credit_limit_value, payment_collection;
        ImageView gps_icon, map_icon;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.customerName);
            cardNumber = itemView.findViewById(R.id.cardNumber);
            date = itemView.findViewById(R.id.date);
            amount = itemView.findViewById(R.id.amount);
            credit_limit_value = itemView.findViewById(R.id.credit_limit_value);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            payment_collection = itemView.findViewById(R.id.payment_collection);
            gps_icon = itemView.findViewById(R.id.gps_icon);
            map_icon = itemView.findViewById(R.id.map_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                  /*Bundle b = new Bundle();
                  b.putSerializable(Globals.BussinessItemData,customerList.get(getAdapterPosition()));
                  // Opportunity_Detail_Fragment fragment = new Opportunity_Detail_Fragment();
                  Update_BussinessPartner_Fragment fragment = new Update_BussinessPartner_Fragment();
                  fragment.setArguments(b);
                  FragmentTransaction transaction =  ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                  transaction.replace(R.id.main_edit_qt_frame, fragment);
                  transaction.addToBackStack(null);
                  transaction.commit();*/
                    Bundle b = new Bundle();
                    b.putSerializable(Globals.BussinessItemData, customerList.get(getAdapterPosition()));
                    // Opportunity_Detail_Fragment fragment = new Opportunity_Detail_Fragment();
                    BusinessPartnerDetail fragment = new BusinessPartnerDetail();
                    fragment.setArguments(b);
                    FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_edit_qt_frame, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                }
            });

            payment_collection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PaymentCollection fragment = new PaymentCollection();
                    Explode fade = new Explode();
                    fragment.setEnterTransition(fade);
                    fragment.setExitTransition(fade);
                    FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_edit_qt_frame, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });


            gps_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    b.putSerializable(Globals.BussinessItemData, customerList.get(getAdapterPosition()));
                    Intent i = new Intent(context, MapsActivity.class);
                    i.putExtras(b);
                    context.startActivity(i);
                }
            });

            map_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }


    List<BusinessPartnerData> tempList = null;

    public void filter(String charText) {
        customerList.clear();
        tempList.addAll(customerList);
        charText = charText.toLowerCase(Locale.getDefault());
        if (charText.length() == 0) {
            customerList.addAll(tempList);
        } else {
            for (BusinessPartnerData st : tempList) {
                if (st.getCardName() != null && !st.getCardName().isEmpty() && st.getCardCode() != null && !st.getCardCode().isEmpty()) {
                    if (st.getCardName().toLowerCase(Locale.getDefault()).contains(charText) || st.getCardCode().toLowerCase(Locale.getDefault()).contains(charText)) {
                        customerList.add(st);
                    }
                }
            }

        }
        notifyDataSetChanged();
    }

    public void StateFilter(String state) {
        customerList.clear();
        if (state.length() == 0) {
            customerList.addAll(tempList);
        } else {
            for (BusinessPartnerData bde : tempList) {
                if (bde.getBPAddresses().size() > 0) {
                    if (state.trim().equalsIgnoreCase(bde.getBPAddresses().get(0).getUState())) {
                        customerList.add(bde);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public void Typefilter(String name) {

        customerList.clear();
        if (name.length() == 0) {
            customerList.addAll(tempList);
        } else {
            for (BusinessPartnerData bde : tempList) {
                if (bde.getUType().size() > 0) {
                    if (name.trim().equalsIgnoreCase(bde.getUType().get(0).getType())) {
                        customerList.add(bde);
                    }
                }
            }
        }
        notifyDataSetChanged();

    }

    public void AllData() {
        customerList.clear();
        customerList.addAll(tempList);
        notifyDataSetChanged();
    }

    public void Customerfilter() {
        customerList.clear();
        for (BusinessPartnerData st : tempList) {
            if (st.getCardName() != null && !st.getCardName().isEmpty())
                customerList.add(st);
        }

        Collections.sort(customerList, new Comparator<BusinessPartnerData>() {
            @Override
            public int compare(BusinessPartnerData o1, BusinessPartnerData o2) {

                return o1.getCardName().compareTo(o2.getCardName());
            }
        });
        notifyDataSetChanged();
    }
}
