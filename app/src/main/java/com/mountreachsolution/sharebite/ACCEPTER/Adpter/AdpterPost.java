package com.mountreachsolution.sharebite.ACCEPTER.Adpter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mountreachsolution.sharebite.ACCEPTER.POJO.POJOPOSt;
import com.mountreachsolution.sharebite.ACCEPTER.PostDetails;
import com.mountreachsolution.sharebite.R;
import com.mountreachsolution.sharebite.urls;

import java.util.List;

public class AdpterPost extends RecyclerView.Adapter<AdpterPost.ViewHolder> {
    List<POJOPOSt>pojopoSts;
    Activity activity;

    public AdpterPost(List<POJOPOSt> pojopoSts, Activity activity) {
        this.pojopoSts = pojopoSts;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterPost.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.request,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterPost.ViewHolder holder, int position) {
        POJOPOSt obj=pojopoSts.get(position);
        holder.tvUsername.setText(obj.getUsername());
        holder.tvLocation.setText(obj.getAddress());
        holder.tvDetails.setText(obj.getDetail());
        holder.tvquantity.setText(obj.getQuantity());
        Glide.with(activity)
                .load(urls.address + "images/"+obj.getImage())
                .skipMemoryCache(true)
                .error(R.drawable.baseline_person_24)// Resize the image to 800x800 pixels
                .into(holder.ivImage);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, PostDetails.class);
                i.putExtra("username",obj.getUsername());
                i.putExtra("address",obj.getAddress());
                i.putExtra("detail",obj.getDetail());
                i.putExtra("quantity",obj.getQuantity());
                i.putExtra("image",obj.getImage());
                activity.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return pojopoSts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        ImageView ivImage;
        TextView tvUsername, tvLocation, tvDetails,tvquantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvDetails = itemView.findViewById(R.id.tvDetails);
            tvquantity = itemView.findViewById(R.id.tvqquantity);
        }
    }
}
