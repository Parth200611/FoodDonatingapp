package com.mountreachsolution.sharebite.DONOR;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

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
import de.hdodenhof.circleimageview.CircleImageView;

public class RequsetDetailsD extends AppCompatActivity {
    CircleImageView profileImage;
     TextView tvUsername, tvWelcome, tvName, tvRegisterNo, tvAddress;
     Button btnAccept, btnReject;
     String Uuserename,Ausername,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_requset_details_d);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.brown));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.white));
        Uuserename=getIntent().getStringExtra("Dusername");
        Ausername=getIntent().getStringExtra("AUsername");
        id=getIntent().getStringExtra("id");
        profileImage = findViewById(R.id.profileImage);
        tvUsername = findViewById(R.id.tvUsername);
        tvWelcome = findViewById(R.id.tvWelcome);
        tvName = findViewById(R.id.tvname);
        tvRegisterNo = findViewById(R.id.tvRegisterno);
        tvAddress = findViewById(R.id.tvAddress);
        tvUsername.setText(Ausername);
        getdata();



    }

    private void getdata() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",Ausername);
        client.post(urls.Profildat,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("getProfildata");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name=jsonObject.getString("name");
                        String mobileno=jsonObject.getString("mobile");
                        String gender=jsonObject.getString("dob");
                        String email=jsonObject.getString("email");
                        String image=jsonObject.getString("image");
                        String usertype=jsonObject.getString("usertype");
                        String registerno=jsonObject.getString("registerno");
                        String address=jsonObject.getString("address");
                        tvName.setText(name);
                        tvRegisterNo.setText(registerno);
                        tvAddress.setText(address);
                        Glide.with(RequsetDetailsD.this)
                                .load(urls.address + "images/"+image)
                                .skipMemoryCache(true)
                                .error(R.drawable.baseline_person_24)// Resize the image to 800x800 pixels
                                .into(profileImage);


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