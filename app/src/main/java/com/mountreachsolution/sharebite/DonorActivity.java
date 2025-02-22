package com.mountreachsolution.sharebite;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.jetbrains.annotations.Async;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class DonorActivity extends AppCompatActivity {
    String name, dob, address, mobile, email, username, password;
    Button registerButton;
     String stringDob;
     String userType="Donor";
    EditText nameInput, dobInput, addressInput, mobileInput, emailInput, usernameInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_donor);
        nameInput = findViewById(R.id.name_input);
        dobInput = findViewById(R.id.dob_input);
        addressInput = findViewById(R.id.address_input);
        mobileInput = findViewById(R.id.mobile_input);
        emailInput = findViewById(R.id.email_input);
        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        registerButton = findViewById(R.id.register_button);

        dobInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(DonorActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                stringDob = dayOfMonth + "/" + (month + 1) + "/" + year;
                                dobInput.setText(stringDob);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 name = nameInput.getText().toString().trim();
                 dob = dobInput.getText().toString().trim();
                 address = addressInput.getText().toString().trim();
                 mobile = mobileInput.getText().toString().trim();
                 email = emailInput.getText().toString().trim();
                 username = usernameInput.getText().toString().trim();
                 password = passwordInput.getText().toString().trim();


                if (name.isEmpty() || dob.isEmpty() || address.isEmpty() || mobile.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    dobInput.setError("Please fill all fields");
                } else {
                   registerdata();
                }
            }
        });
    }

    private void registerdata() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("name",name);
        params.put("emailid",email);
        params.put("mobileno",mobile);
        params.put("password",password);
        params.put("username",username);
        params.put("dob",dob);
        params.put("address",address);
        params.put("userType",userType);
        client.post(urls.userregister,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    String success = response.getString("success");
                    if (success.equals("1")){
                        Toast.makeText(DonorActivity.this, "Register As Doner", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", username);
                        editor.putString("userType", userType);
                        editor.apply();
                        Intent i = new Intent(DonorActivity.this,LoginActivity.class);
                        startActivity(i);
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