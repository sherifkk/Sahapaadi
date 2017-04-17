package com.example.athira.sahapaadi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    SessionManager session = new SessionManager(getApplicationContext());
    if (!session.isLoggedIn()) {
      Intent intent = new Intent(this, LoginPage.class);
      startActivity(intent);
      finish();
    }
  }
}
