package com.example.athira.sahapaadi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_details);
    }
    public void sendMessage(View view)
    {
        Intent intent = new Intent(LoginDetails.this,ProfileUpdate.class);
        startActivity(intent);
    }
}
