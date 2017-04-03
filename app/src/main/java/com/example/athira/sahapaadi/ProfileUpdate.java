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
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Lobster-Regular.ttf");
        TextView tv = (TextView) findViewById(R.id.logo_text2);
        tv.setTypeface(type);
    }
      public void sendMessage(View view)
      {  // checking if mandatory field are not empty
         /* EditText nameField = (EditText) findViewById(R.id.nameField);
          EditText emailidField = (EditText) findViewById(R.id.emailidField);
          EditText programField = (EditText) findViewById(R.id.programField);
          EditText universityField = (EditText) findViewById(R.id.universityField);
          EditText schemaField = (EditText) findViewById(R.id.schemaField);
          EditText semesterField =(EditText) findViewById(R.id.semesterField);
          EditText collegeField =(EditText) findViewById(R.id.collegeField);
          EditText passwordField =(EditText) findViewById(R.id.passwordField);

          if(nameField.getText().length()==0)
          { nameField.setError("This field is mandatory");
              return;
          }
          else if(emailidField.getText().length()==0) {
              emailidField.setError("This field is mandatory");
              return;
          }

          else if(programField.getText().length()==0) {
              programField.setError("This field is mandatory");
              return;
          }
          else if(universityField.getText().length()==0) {
              universityField.setError("This field is mandatory");
              return;
          }
          else if(schemaField.getText().length()==0) {
              schemaField.setError("This field is mandatory");
              return;
          }
         else if(semesterField.getText().length()==0) {
              semesterField.setError("This field is mandatory");
              return;
          }
          else if(collegeField.getText().length()==0) {
              collegeField.setError("This field is mandatory");
              return;
          }
          else if(passwordField.getText().length()==0) {
              passwordField.setError("This field is mandatory");
              return;
          }*/
         Intent intent = new Intent(ProfileUpdate.this,HomePage.class);
          startActivity(intent);
      }

}
