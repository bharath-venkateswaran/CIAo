package com.ciao.root.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    FirebaseUser user;

    public static int SPLASH_TIME_OUT = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null)
                    startActivity(new Intent(SplashScreen.this,Register.class));
                else
                    startActivity(new Intent(SplashScreen.this,Home.class));
                finish();
            }
        },SPLASH_TIME_OUT);


    }
}