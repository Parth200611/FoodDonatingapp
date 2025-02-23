package com.mountreachsolution.sharebite.DONOR.Adpter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mountreachsolution.sharebite.DONOR.POJO.POJOAccepetRequest;
import com.mountreachsolution.sharebite.DONOR.RequsetDetailsD;
import com.mountreachsolution.sharebite.R;

import java.util.List;

public class AdpterConfirm extends RecyclerView.Adapter<AdpterConfirm.ViewHolder> {
    List<POJOAccepetRequest>pojoAccepetRequests;
    Activity activity;

    public AdpterConfirm(List<POJOAccepetRequest> pojoAccepetRequests, Activity activity) {
        this.pojoAccepetRequests = pojoAccepetRequests;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterConfirm.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.confirm,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterConfirm.ViewHolder holder, int position) {
        POJOAccepetRequest obj = pojoAccepetRequests.get(position);
        holder.tvUsername.setText(obj.getAUsername());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, RequsetDetailsD.class);
                i.putExtra("Dusername",obj.getDusername());
                i.putExtra("AUsername",obj.getAUsername());
                i.putExtra("id",obj.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return pojoAccepetRequests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername;
        CardView card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername=itemView.findViewById(R.id.tvUsername);
            card=itemView.findViewById(R.id.Card);
        }
    }
}
