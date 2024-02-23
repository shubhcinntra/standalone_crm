package com.cinntra.standalone.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cinntra.standalone.R;

import java.util.ArrayList;
import java.util.List;

public class ImageViewAdapter extends RecyclerView.Adapter <ImageViewAdapter.ContactViewHolder>{

    Context context;
    ArrayList<Uri> UriList ;
    public ImageViewAdapter(Context context, List<Uri> UriList) {
        this.context = context;
        this.UriList= (ArrayList<Uri>) UriList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.imageadapterscree,parent,false);
        return new ContactViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);



        Glide.with(context).load(UriList.get(position).toString()).apply(options).into(holder.loadimage);
        holder.cross.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                UriList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return UriList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {


        ImageView loadimage,cross;
        public ContactViewHolder(@NonNull View itemView) {

            super(itemView);
            loadimage = itemView.findViewById(R.id.loadimage);
            cross= itemView.findViewById(R.id.cross);


        }
    }
}
