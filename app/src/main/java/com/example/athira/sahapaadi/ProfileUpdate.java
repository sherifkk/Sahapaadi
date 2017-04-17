  package com.example.athira.sahapaadi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

  public class ProfileUpdate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);

      TextView buttonSignup = (TextView) findViewById(R.id.buttonSignup);
      buttonSignup.setOnClickListener(new OnClickListener() {
        @Override public void onClick(View view) {

          sendMessage();
        }
      });
  
    }
      public void sendMessage()
      {
         Intent intent = new Intent(ProfileUpdate.this,HomePage.class);
          startActivity(intent);
      }

}
