package com.cinntra.standalone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.standalone.R;

public class BarItemVerticalAdapter extends RecyclerView.Adapter<BarItemVerticalAdapter.ViewHolder> {
    Context context;
    String  itemArray[];

    public BarItemVerticalAdapter(Context context, String  itemArray[])
    {
      this.context = context;
      this.itemArray = itemArray;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View rootView = LayoutInflater.from(context).inflate(R.layout.ver_bar_item,parent,false);
    return new ViewHolder(rootView);
      }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
       {
    holder.item_name.setText(itemArray[position]);
       }

    @Override
    public int getItemCount()
      {
    return itemArray.length;
      }



    class ViewHolder extends RecyclerView.ViewHolder {
          private TextView item_name;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.title);




        }
    }
}
