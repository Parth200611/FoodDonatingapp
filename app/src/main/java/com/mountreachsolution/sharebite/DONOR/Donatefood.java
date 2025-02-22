package com.mountreachsolution.sharebite.DONOR;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mountreachsolution.sharebite.R;
import com.mountreachsolution.sharebite.VolleyMultipartRequest;
import com.mountreachsolution.sharebite.urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;


public class Donatefood extends Fragment {
    TextView tvTitle, tvSubtitle;
     ImageView ivFoodImage;
     Button btnUploadImage, btnDonateFood;
     EditText etFoodDetails, etAddress, etQuantity;
     String name,userrname;
    Bitmap bitmap;
    Uri filepath;
    private  int pick_image_request=789;

    private static final int PICK_IMAGE_REQUEST_PASS = 1;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_donatefood, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userrname = sharedPreferences.getString("username", "Guest");
        tvTitle = view.findViewById(R.id.tvTitle);
        tvSubtitle = view.findViewById(R.id.tvSubtitle);
        ivFoodImage = view.findViewById(R.id.ivFoodImage);
        btnUploadImage = view.findViewById(R.id.btnUploadImage);
        etFoodDetails = view.findViewById(R.id.etFoodDetails);
        etAddress = view.findViewById(R.id.etAddress);
        etQuantity = view.findViewById(R.id.etQuantity);
        btnDonateFood = view.findViewById(R.id.btnDonateFood);

        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("donorData", Context.MODE_PRIVATE);
         name = sharedPreferences.getString("name", "No Name");
        String mobile = sharedPreferences.getString("mobile", "No Mobile");
        String email = sharedPreferences.getString("email", "No Email");

        btnDonateFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String details = etFoodDetails.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                String quantity = etQuantity.getText().toString().trim();

                if (details.isEmpty() || address.isEmpty() || quantity.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill all fields and upload an image!", Toast.LENGTH_SHORT).show();
                } else {
                    // Perform food donation logic here (API call or local save)

                    Post(userrname,name,address,details,quantity);


                }
            }
        });

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectUserProfileimage();
            }
        });
        return view;
    }

    private void SelectUserProfileimage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image For Profil"),pick_image_request);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==pick_image_request && resultCode==RESULT_OK && data!=null){
            filepath=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),filepath);
                ivFoodImage.setImageBitmap(bitmap);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void UserImageSaveTodatabase(Bitmap bitmap, String strTitle) {
        VolleyMultipartRequest volleyMultipartRequest =  new VolleyMultipartRequest(Request.Method.POST, urls.PostDonateImg, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Toast.makeText(getActivity(), "Image Save as Profil "+strTitle, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                String errorMsg = error.getMessage();
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    errorMsg = new String(error.networkResponse.data);
                }
                Log.e("UploadError", errorMsg);
                Toast.makeText(getActivity(), "Upload Error: " + errorMsg, Toast.LENGTH_LONG).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("tags", strTitle); // Adjusted to match PHP parameter name
                return parms;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String,VolleyMultipartRequest.DataPart> parms = new HashMap<>();
                long imagename = System.currentTimeMillis();
                parms.put("pic",new VolleyMultipartRequest.DataPart(imagename+".jpeg",getfiledatafromBitmap(bitmap)));

                return parms;

            }

        };
        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }

    private byte[] getfiledatafromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void Post(String userrname, String name, String address, String details, String quantity) {
        AsyncHttpClient client  = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.put("username",userrname);
        requestParams.put("name",name);
        requestParams.put("quantity",quantity);
        requestParams.put("address",address);
        requestParams.put("detail",details);
        client.post(urls.PostDonate,requestParams,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String ststus=response.getString("success");
                    if (ststus.equals("1")){
                        Toast.makeText(getContext(), "Food Donate Posted", Toast.LENGTH_SHORT).show();
                        UserImageSaveTodatabase(bitmap,userrname);
                        clearFields();
                    }else{
                        Toast.makeText(getContext(), "Fail to Post", Toast.LENGTH_SHORT).show();
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

    private void clearFields() {
        etFoodDetails.setText("");
        etAddress.setText("");
        etQuantity.setText("");
        ivFoodImage.setImageResource(R.drawable.baseline_camera_alt_24);
    }
}