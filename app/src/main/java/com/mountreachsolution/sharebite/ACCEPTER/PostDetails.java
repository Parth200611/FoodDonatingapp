package com.mountreachsolution.sharebite.ACCEPTER;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mountreachsolution.sharebite.R;
import com.mountreachsolution.sharebite.urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class PostDetails extends AppCompatActivity {
    ImageView ivImage;
     TextView tvName, tvMobileno, tvUsername, tvLocation, tvQuantity, tvDetails;
     Button btnAccept, btnReject;
     String accepter;
    String username, address, detail, quantity, image,name,mobileno,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post_details);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.brown));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.white));
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        accepter = sharedPreferences.getString("username", "Guest");
        ivImage = findViewById(R.id.ivImage);
        tvName = findViewById(R.id.tvName);
        tvMobileno = findViewById(R.id.tvMobileno);
        tvUsername = findViewById(R.id.tvUsername);
        tvLocation = findViewById(R.id.tvLocation);
        tvQuantity = findViewById(R.id.tvQuantity);
        tvDetails = findViewById(R.id.tvDetails);
        btnAccept = findViewById(R.id.btnAccept);
        btnReject = findViewById(R.id.btnReject);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        address = intent.getStringExtra("address");
        detail = intent.getStringExtra("detail");
        quantity = intent.getStringExtra("quantity");
        image = intent.getStringExtra("image");
        id = intent.getStringExtra("id");


       
        tvUsername.setText(username);
        tvLocation.setText(address);
        tvQuantity.setText(quantity);
        tvDetails.setText(detail);

        Glide.with(PostDetails.this)
                .load(urls.address + "images/"+image)
                .skipMemoryCache(true)
                .error(R.drawable.baseline_person_24)// Resize the image to 800x800 pixels
                .into(ivImage);
btnAccept.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String status1="Requested Accepted";
        PushData(status1);
    }
});btnReject.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String status2="Requested Rejected";
        PushData2(status2);
    }
});

        
        getData(username);
    }

    private void PushData(String status1) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("name", name);
        params.put("address", address);
        params.put("quantity", quantity);
        params.put("detail", detail);
        params.put("image", image);
        params.put("mobilenno", mobileno);
        params.put("acceptero", accepter);
        params.put("status", status1);

        client.post(urls.acceptdata, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("success");
                    if (status.equals("1")) {
                        Toast.makeText(PostDetails.this, "Post Accepted!", Toast.LENGTH_SHORT).show();
                      removePost(id);
                    } else {
                        Toast.makeText(PostDetails.this, "Post Acceptance Failed!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.e("onSuccess", "Error parsing response", e);
                    Toast.makeText(PostDetails.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("onFailure", "Request failed. Status code: " + statusCode);
                Log.e("onFailure", "Response: " + responseString);
                Log.e("onFailure", "Throwable: " + throwable.getMessage());

                // Handle error response
                Toast.makeText(PostDetails.this, "Post Acceptance Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removePost(String id) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("id", id);

        client.post(urls.removepost, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("status");
                    if (status.equals("success")) {
                        Intent i = new Intent(PostDetails.this,AccepterHomepage.class);
                        startActivity(i);
                        finish();

                        // Remove item from list and update RecyclerVie
                    } else {
                        Toast.makeText(PostDetails.this, "Failed to Remove", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(PostDetails.this, "Error: Unable to Remove Diet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void PushData2(String status2) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("name", name);
        params.put("address", address);
        params.put("quantity", quantity);
        params.put("detail", detail);
        params.put("image", image);
        params.put("mobilenno", mobileno);
        params.put("acceptero", accepter);
        params.put("status", status2);

        client.post(urls.acceptdata, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("success");
                    if (status.equals("1")) {
                        Toast.makeText(PostDetails.this, "Post Rejected!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PostDetails.this, "Post Acceptance Failed!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.e("onSuccess", "Error parsing response", e);
                    Toast.makeText(PostDetails.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("onFailure", "Request failed. Status code: " + statusCode);
                Log.e("onFailure", "Response: " + responseString);
                Log.e("onFailure", "Throwable: " + throwable.getMessage());

                // Handle error response
                Toast.makeText(PostDetails.this, "Post Acceptance Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getData(String username) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",username);
        client.post(urls.Profildat,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("getProfildata");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                         name=jsonObject.getString("name");
                         mobileno=jsonObject.getString("mobile");
                        String gender=jsonObject.getString("dob");
                        String email=jsonObject.getString("email");
                        String image=jsonObject.getString("image");
                        String usertype=jsonObject.getString("usertype");
                        String registerno=jsonObject.getString("registerno");
                        String details=jsonObject.getString("details");
                        tvName.setText(name);
                        tvMobileno.setText(mobileno);




                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

}