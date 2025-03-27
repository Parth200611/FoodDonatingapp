package com.mountreachsolution.sharebite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mountreachsolution.sharebite.ACCEPTER.AccepterHomepage;
import com.mountreachsolution.sharebite.DONOR.DonorHomepage;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {
    TextView title, subtitle, loginLabel, orDivider,tvPassword;
    TextInputEditText username, password;
    CheckBox showPassword;
    Button loginButton, signupButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.brown));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.white));

        title = findViewById(R.id.title);
        subtitle = findViewById(R.id.subtitle);
        loginLabel = findViewById(R.id.login_label);
        tvPassword = findViewById(R.id.tvPassword);


        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        showPassword = findViewById(R.id.show_password);

        loginButton = findViewById(R.id.login_button_needy);
        signupButton = findViewById(R.id.signup_button);

        tvPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,ChangePassword.class);
                startActivity(i);
            }
        });


        showPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {

                password.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {

                password.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            password.setSelection(password.getText().length());
        });


        loginButton.setOnClickListener(v -> {
            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Enter All Data For Log In", Toast.LENGTH_SHORT).show();
            } else {

                Login(user,pass);
            }
        });

        // Signup Button Click Listener
        signupButton.setOnClickListener(v -> {
            // Redirect to signup activity (Implement intent later)
            Intent i = new Intent(this,ChooseRole.class);
            startActivity(i);
        });

    }

    private void Login(String user, String pass) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username", user);
        params.put("password", pass);

        client.post(urls.login, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String success = response.getString("success");
                    String userType = response.getString("usertype"); // Assuming your PHP response sends userType

                    if (success.equals("1")) {
                        // Save login details in SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", user);
                        editor.putString("userType", userType);
                        editor.putBoolean("isLoggedIn", true); // Mark user as logged in
                        editor.apply();

                        Toast.makeText(LoginActivity.this, "Welcome Back!", Toast.LENGTH_SHORT).show();
                        checkUserType(user);

                    } else {
                        Toast.makeText(LoginActivity.this, "Incorrect Username and Password", Toast.LENGTH_SHORT).show();
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

    private void checkUserType(String username) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("username", null);
        String userType = sharedPreferences.getString("userType", null);

        if (savedUsername != null && savedUsername.equals(username)) {
            if (userType != null) {
                if (userType.equalsIgnoreCase("Donor")) {
                    // Navigate to Saviour Homepage
                    Intent intent = new Intent(this, DonorHomepage.class);
                    startActivity(intent);
                    finish();
                } else if (userType.equalsIgnoreCase("Acceptor")) {
                    // Navigate to Needy Homepage
                    Intent intent = new Intent(this, AccepterHomepage.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Invalid User Type", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "User Type not found", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Username not found in SharedPreferences", Toast.LENGTH_SHORT).show();
        }
    }
}