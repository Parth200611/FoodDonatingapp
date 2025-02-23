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

import com.mountreachsolution.sharebite.ACCEPTER.POJO.POJOGetRequest;
import com.mountreachsolution.sharebite.R;
import com.mountreachsolution.sharebite.ACCEPTER.RequestDeatils;

import java.util.List;

public class AdpterFRequest extends RecyclerView.Adapter<AdpterFRequest.ViewHolder> {
    List<POJOGetRequest>pojoGetRequests;
    Activity activity;

    public AdpterFRequest(List<POJOGetRequest> pojoGetRequests, Activity activity) {
        this.pojoGetRequests = pojoGetRequests;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterFRequest.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.donaterequest,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AdpterFRequest.ViewHolder holder, int position) {
        POJOGetRequest data = pojoGetRequests.get(position);

        // Set values for the TextViews (Username, etc.)
        holder.tvUsername.setText(data.getDusername());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, RequestDeatils.class);
                i.putExtra("DUsername",data.getDusername());
                i.putExtra("Ausername",data.getAusername());
                i.putExtra("id",data.getId());
                activity.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pojoGetRequests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername, tvDetails;
        CardView card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            card = itemView.findViewById(R.id.Card);
        }
    }
}
