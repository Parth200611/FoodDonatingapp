package com.mountreachsolution.sharebite.ACCEPTER;

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
import com.mountreachsolution.sharebite.DONOR.DonorHomepage;
import com.mountreachsolution.sharebite.R;

public class AccepterHomepage extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_accepter_homepage);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.brown)));
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.brown));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false);
            window.getInsetsController().setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
        }

        getWindow().setNavigationBarColor(ContextCompat.getColor(AccepterHomepage.this,R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        bottomNavigationView = findViewById(R.id.bottomnevigatiomuserhome);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        bottomNavigationView.setSelectedItemId(R.id.AAllDonate);
    }

    AllDonate allDonate = new AllDonate();
    HistroryAcc histroryAcc = new HistroryAcc();
    RequestFood requestFood = new RequestFood();
    AccepetFrofil accepetFrofil = new AccepetFrofil();
    Responce responce = new Responce();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.AAllDonate){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,allDonate).commit();
        }else if(item.getItemId()==R.id.AHiistory){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,histroryAcc).commit();
        } else if(item.getItemId()==R.id.AProfil){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,accepetFrofil).commit();
        }else if(item.getItemId()==R.id.AResponce){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,responce).commit();
        }else if(item.getItemId()==R.id.ARequestfood){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,requestFood).commit();
        }
        return true;
    }
}