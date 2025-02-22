package com.mountreachsolution.sharebite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChooseRole extends AppCompatActivity {
     CardView cardViewDonor, cardViewAccepter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_role);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.brown));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.white));

        cardViewDonor = findViewById(R.id.CradViewDoner);
        cardViewAccepter = findViewById(R.id.CradViewAccepter);


        cardViewDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ChooseRole.this, DonorActivity.class);
                startActivity(i);
            }
        });

        cardViewAccepter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ChooseRole.this, AccepterActivity.class);
                startActivity(i);
            }
        });


    }
}