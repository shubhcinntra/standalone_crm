package com.cinntra.standalone.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.newapimodel.AttachDocument;


import java.util.ArrayList;
import java.util.List;

public class PreviousImageViewAdapter extends RecyclerView.Adapter <PreviousImageViewAdapter.ContactViewHolder>{

    Context context;
    ArrayList<AttachDocument> UriList ;
    public PreviousImageViewAdapter(Context context, List<AttachDocument> UriList) {
        this.context = context;
        this.UriList= (ArrayList<AttachDocument>) UriList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.imageadapterscree,parent,false);
        return new ContactViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {

      // String ext = Globals.getFileExtension(UriList.get(position).getFileName());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.icon)
                .error(R.drawable.ic_invoice);
        String ImageUrl = Globals.ImageURl+UriList.get(position).getFile();

        Glide.with(context).load(ImageUrl).apply(options).into(holder.loadimage);
        holder.loadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  if(ext.equalsIgnoreCase("jpg")||ext.equalsIgnoreCase("jpeg")||ext.equalsIgnoreCase("png")||ext.equalsIgnoreCase("svg")){

                }else{

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ImageUrl));
                    context.startActivity(browserIntent);
                }*/
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ImageUrl));
                context.startActivity(browserIntent);
            }
        });





        holder.cross.setVisibility(View.GONE);



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
