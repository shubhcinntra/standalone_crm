package com.cinntra.standalone.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cinntra.standalone.R;
import com.cinntra.standalone.model.CountryData;

import java.util.ArrayList;

public class CountryAdapterAuto extends ArrayAdapter<CountryData> {
    Context context;
    int resourceId;
    ArrayList<CountryData> items;

    public CountryAdapterAuto(@NonNull Context context, int resourceId, ArrayList<CountryData> items) {
        super(context, resourceId, items);
        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(resourceId, parent, false);
        }
        CountryData model = getItem(position);
        TextView name = view.findViewById(R.id.text_view);
        name.setText(model.getName());
        return view;
    }

    @Nullable
    @Override
    public CountryData getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
