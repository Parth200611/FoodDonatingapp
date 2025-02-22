package com.mountreachsolution.sharebite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class AccepterActivity extends AppCompatActivity {

    EditText trustNameInput, registrationNumberInput, addressInput, mobileInput, detailsInput,usernameInput,PasswordInput;
    ImageView certificateImage;
    Button uploadCertificateButton, registerButton;
    String userType="Acceptor";
    String trustName, registrationNumber, address, mobile, details,username,password,email="null",dob="null";
    Bitmap bitmap;
    Uri filepath;
    private  int pick_image_request=789;

    private static final int PICK_IMAGE_REQUEST_PASS = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_accepter);

        trustNameInput = findViewById(R.id.trust_name_input);
        registrationNumberInput = findViewById(R.id.registration_number_input);
        certificateImage = findViewById(R.id.certificate_image);
        uploadCertificateButton = findViewById(R.id.upload_certificate_button);
        addressInput = findViewById(R.id.address_input);
        mobileInput = findViewById(R.id.mobile_input);
        detailsInput = findViewById(R.id.details_input);
        registerButton = findViewById(R.id.register_button);
        usernameInput=findViewById(R.id.username_input);
        PasswordInput=findViewById(R.id.password_input);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trustName = trustNameInput.getText().toString().trim();
                registrationNumber = registrationNumberInput.getText().toString().trim();
                address = addressInput.getText().toString().trim();
                mobile = mobileInput.getText().toString().trim();
                details = detailsInput.getText().toString().trim();
                username = usernameInput.getText().toString().trim();
                password = PasswordInput.getText().toString().trim();

                // For now, just check if inputs are valid
                if (trustName.isEmpty() || registrationNumber.isEmpty() || address.isEmpty() || mobile.isEmpty()) {
                    Toast.makeText(AccepterActivity.this, "Please fill all the required fields!", Toast.LENGTH_SHORT).show();
                } else {
                    registerdata();;
                }
            }
        });

        uploadCertificateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectUserProfileimage();

            }
        });



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
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                certificateImage.setImageBitmap(bitmap);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void UserImageSaveTodatabase(Bitmap bitmap, String strTitle) {
        VolleyMultipartRequest volleyMultipartRequest =  new VolleyMultipartRequest(Request.Method.POST, urls.certificateimage, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Toast.makeText(AccepterActivity.this, "Image Save as Profil "+strTitle, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AccepterActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                String errorMsg = error.getMessage();
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    errorMsg = new String(error.networkResponse.data);
                }
                Log.e("UploadError", errorMsg);
                Toast.makeText(AccepterActivity.this, "Upload Error: " + errorMsg, Toast.LENGTH_LONG).show();

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
        Volley.newRequestQueue(AccepterActivity.this).add(volleyMultipartRequest);
    }

    private byte[] getfiledatafromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void registerdata() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        // Adding all necessary fields
        params.put("name", trustName);
        params.put("mobileno", mobile);
        params.put("emailid", email); // Added emailid
        params.put("dob", dob);       // Added dob
        params.put("password", password);
        params.put("username", username);
        params.put("address", address);
        params.put("userType", userType);
        params.put("registerno", registrationNumber);
        params.put("details", details);

        // Sending POST request
        client.post(urls.userregister, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    String success = response.getString("success");
                    if (success.equals("1")) {
                        Toast.makeText(AccepterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                        // Save user info to SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", username);
                        editor.putString("userType", userType);
                        editor.apply();

                        // Save user image to database
                        UserImageSaveTodatabase(bitmap, username);

                        // Navigate to LoginActivity
                        Intent intent = new Intent(AccepterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(AccepterActivity.this, "Registration Failed. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.e("JSON Error", "Error parsing JSON response", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("Registration Failed", "Status Code: " + statusCode + ", Response: " + responseString, throwable);
                Toast.makeText(AccepterActivity.this, "Registration failed. Check your connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}