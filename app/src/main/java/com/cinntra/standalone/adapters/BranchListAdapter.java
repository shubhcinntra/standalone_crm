package com.cinntra.standalone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.standalone.R;
import com.cinntra.standalone.interfaces.DatabaseClick;
import com.cinntra.standalone.model.Branch;

import java.util.ArrayList;

public class BranchListAdapter extends RecyclerView.Adapter<BranchListAdapter.ViewHolder> {
    Context context;
    ArrayList<Branch> DBList;
    DatabaseClick databaseClick;

    public BranchListAdapter(Context context, ArrayList<Branch> DBList)
    {
  this.context = context;
  this.DBList  = DBList;
  databaseClick = (DatabaseClick)context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View rootView = LayoutInflater.from(context).inflate(R.layout.database_list_item,parent,false);
    return new ViewHolder(rootView);
      }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
         {
    holder.title.setText(DBList.get(position).getBranchName());
         }

    @Override
    public int getItemCount()
      {
    return DBList.size();
      }



    class ViewHolder extends RecyclerView.ViewHolder {
          private TextView title;
    public ViewHolder(@NonNull View itemView) {
          super(itemView);
         title = itemView.findViewById(R.id.title);
         itemView.setOnClickListener(new View.OnClickListener() {
           @Override
          public void onClick(View v) {
                    databaseClick.onClick(getAdapterPosition());

                }
            });

        }
    }
}
