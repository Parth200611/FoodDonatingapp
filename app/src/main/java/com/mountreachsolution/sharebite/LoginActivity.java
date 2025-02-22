package com.mountreachsolution.sharebite;

import android.content.Intent;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity {
    TextView title, subtitle, loginLabel, orDivider;
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


        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        showPassword = findViewById(R.id.show_password);

        loginButton = findViewById(R.id.login_button_needy);
        signupButton = findViewById(R.id.signup_button);


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
    }
}