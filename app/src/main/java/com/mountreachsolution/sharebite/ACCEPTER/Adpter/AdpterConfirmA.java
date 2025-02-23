package com.mountreachsolution.sharebite.ACCEPTER.Adpter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mountreachsolution.sharebite.ACCEPTER.RequsetDetailsA;
import com.mountreachsolution.sharebite.DONOR.Adpter.AdpterConfirm;
import com.mountreachsolution.sharebite.DONOR.POJO.POJOAccepetRequest;
import com.mountreachsolution.sharebite.DONOR.RequsetDetailsD;
import com.mountreachsolution.sharebite.R;

import java.util.List;

public class AdpterConfirmA extends RecyclerView.Adapter<AdpterConfirmA.ViewHolder> {
    List<POJOAccepetRequest> pojoAccepetRequests;
    Activity activity;

    public AdpterConfirmA(List<POJOAccepetRequest> pojoAccepetRequests, Activity activity) {
        this.pojoAccepetRequests = pojoAccepetRequests;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterConfirmA.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.confirm,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterConfirmA.ViewHolder holder, int position) {
        POJOAccepetRequest obj = pojoAccepetRequests.get(position);
        holder.tvUsername.setText(obj.getDusername());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, RequsetDetailsA.class);
                i.putExtra("Dusername",obj.getDusername());
                i.putExtra("AUsername",obj.getAUsername());
                i.putExtra("id",obj.getId());
                activity.startActivity(i);
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
