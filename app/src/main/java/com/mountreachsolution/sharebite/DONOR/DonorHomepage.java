package com.mountreachsolution.sharebite.DONOR;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowInsetsController;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mountreachsolution.sharebite.R;

public class DonorHomepage extends AppCompatActivity  implements BottomNavigationView.OnNavigationItemSelectedListener {
BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_donor_homepage);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.brown)));
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.brown));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false);
            window.getInsetsController().setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
        }

        getWindow().setNavigationBarColor(ContextCompat.getColor(DonorHomepage.this,R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        bottomNavigationView = findViewById(R.id.bottomnevigatiomuserhome);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        bottomNavigationView.setSelectedItemId(R.id.DDonate);

    }
    Donatefood donatefood = new Donatefood();
    Request request = new Request();
    HistoryDonate  historyDonate = new HistoryDonate();
    ProfilDonate profilDonate = new ProfilDonate();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.DDonate){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,donatefood).commit();
        }else if(item.getItemId()==R.id.DRequest){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,request).commit();
        } else if(item.getItemId()==R.id.DHiistory){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,historyDonate).commit();
        }else if(item.getItemId()==R.id.DProfil){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,profilDonate).commit();
        }
        return true;
    }
}