package com.example.moviedb.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviedb.R;
import com.example.moviedb.helper.InternetConnection;

public class SplashScreenActivity extends AppCompatActivity {

    InternetConnection connection;
    private ImageView logo;
    private TextView appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        connection = new InternetConnection(this);
//        if(connection.isConnected()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent start = new Intent(SplashScreenActivity.this, MainMenuActivity.class);
                    start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(start);
                    finish();
                }
            },1500);
//        }else{
//            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
//        }

        logo = findViewById(R.id.imageView4);
        appName = findViewById(R.id.textView);

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}