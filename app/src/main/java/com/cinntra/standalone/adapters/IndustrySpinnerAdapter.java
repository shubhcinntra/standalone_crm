package com.cinntra.standalone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cinntra.standalone.R;
import com.cinntra.standalone.model.IndustryItem;

import java.util.List;


public class IndustrySpinnerAdapter extends BaseAdapter {
    Context context;
    List<IndustryItem> stagesList;
    LayoutInflater inflter;
   public IndustrySpinnerAdapter(Context context, List<IndustryItem> stagesList)
      {
   this.context    = context;
   this.stagesList = stagesList;
   inflter = (LayoutInflater.from(context));
      }
    @Override
    public int getCount() {
        return stagesList.size();
    }

    @Override
    public IndustryItem getItem(int position) {
        return stagesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        v = inflter.inflate(R.layout.stages_spinner_item, null);
        TextView title = (TextView)v.findViewById(R.id.title);
        title.setText(stagesList.get(position).getIndustryName());
        return v;
    }
}
