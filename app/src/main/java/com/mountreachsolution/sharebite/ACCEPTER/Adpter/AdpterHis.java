package com.mountreachsolution.sharebite.ACCEPTER.Adpter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mountreachsolution.sharebite.DONOR.POJO.POJOHistory;
import com.mountreachsolution.sharebite.R;
import com.mountreachsolution.sharebite.urls;

import java.util.List;

public class AdpterHis extends RecyclerView.Adapter<AdpterHis.Viewholder> {
    List<POJOHistory>pojoHistories;
    Activity activity;

    public AdpterHis(List<POJOHistory> pojoHistories, Activity activity) {
        this.pojoHistories = pojoHistories;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterHis.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.history,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterHis.Viewholder holder, int position) {
        POJOHistory request=pojoHistories.get(position);

        holder.tvUsername.setText(request.getUsername());
        holder.tvName.setText(request.getName());
        holder.tvMobile.setText(request.getMobile());
        holder.tvLocation.setText(request.getAddress());
        holder.tvDetails.setText(request.getDetail());
        holder.tvStatus.setText(request.getStatus());
        holder.tvQuantity.setText(request.getQuantity());
        Glide.with(activity)
                .load(urls.address + "images/"+request.getImage())
                .skipMemoryCache(true)
                .error(R.drawable.baseline_person_24)// Resize the image to 800x800 pixels
                .into(holder.ivImage);

    }

    @Override
    public int getItemCount() {
        return pojoHistories.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvUsername, tvName, tvMobile, tvLocation, tvDetails, tvStatus,tvQuantity;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvName = itemView.findViewById(R.id.tvname);
            tvMobile = itemView.findViewById(R.id.tvMobile);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvDetails = itemView.findViewById(R.id.tvDetails);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
        }
    }
}
