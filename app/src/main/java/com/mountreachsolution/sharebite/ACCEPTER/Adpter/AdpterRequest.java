package com.mountreachsolution.sharebite.ACCEPTER.Adpter;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mountreachsolution.sharebite.ACCEPTER.POJO.POJORequest;
import com.mountreachsolution.sharebite.DONOR.Request;
import com.mountreachsolution.sharebite.R;
import com.mountreachsolution.sharebite.urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdpterRequest extends RecyclerView.Adapter<AdpterRequest.ViewHolder> {
    List<POJORequest>pojoRequests;
    Activity activity;

    public AdpterRequest(List<POJORequest> pojoRequests, Activity activity) {
        this.pojoRequests = pojoRequests;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterRequest.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.requestuser,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterRequest.ViewHolder holder, int position) {
        POJORequest data = pojoRequests.get(position);  // Replace MyDataModel with your model class

        // Set values for TextViews
        holder.tvName.setText(data.getName());
        holder.tvUsername.setText(data.getUsername());
        holder.tvAddress.setText(data.getAddress());
        holder.tvRegister.setText(data.getRegisterNo());
        holder.tgvDetail.setText(data.getDetail());
        Glide.with(activity)
                .load(urls.address + "images/"+data.getImage())
                .skipMemoryCache(true)
                .error(R.drawable.baseline_person_24)// Resize the image to 800x800 pixels
                .into(holder.cvImage);
        holder.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String DUsername=data.getId();
                String acceptorusername=data.getUsername();
                PushData(DUsername ,acceptorusername);
            }
        });


    }

    private void PushData(String dUsername, String acceptorusername) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("Dousername", dUsername);
        params.put("accusername", acceptorusername);

        client.post(urls.AddRequest, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("success");
                    if (status.equals("1")) {
                        // Handle success
                        Toast.makeText(activity, "Request Sent", Toast.LENGTH_SHORT).show();
                    } else {
                        // Handle failure case where success is not "1"
                        Toast.makeText(activity, "Request Failed to send", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, "Error parsing response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                // Handle failure response
                if (errorResponse != null) {
                    try {
                        String errorMessage = errorResponse.getString("message");
                        Toast.makeText(activity, "Server Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "Error parsing error response", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle cases where the error response is null (like network failure)
                    Toast.makeText(activity, "Network Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return pojoRequests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView cvImage;
        TextView tvName, tvUsername, tvAddress, tvRegister, tgvDetail;
        Button btnSend;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvImage = itemView.findViewById(R.id.cvImage);
            tvName = itemView.findViewById(R.id.tvName);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvRegister = itemView.findViewById(R.id.tvregister);
            tgvDetail = itemView.findViewById(R.id.tgvDetail);
            btnSend = itemView.findViewById(R.id.btnSend);

        }
    }
}
