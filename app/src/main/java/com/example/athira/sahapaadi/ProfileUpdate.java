  package com.example.athira.sahapaadi;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.athira.sahapaadi.R.id.emailLoginField;
import static com.example.athira.sahapaadi.R.id.info;
import static com.example.athira.sahapaadi.R.id.passwordLoginField;

  public class ProfileUpdate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
    }
      public void sendMessage(View view)
      {
         Intent intent = new Intent(ProfileUpdate.this,HomePage.class);
          startActivity(intent);
      }

}
