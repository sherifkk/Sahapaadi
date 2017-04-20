package com.example.athira.sahapaadi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;

public class LoginDetails extends AppCompatActivity {

  EditText editName;
  EditText editEmailID;
  EditText editPassword;
  EditText editConfirmPassword;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_details);

    editName = (EditText) findViewById(R.id.signupName);
    editEmailID = (EditText) findViewById(R.id.signupEmailID);
    editPassword = (EditText) findViewById(R.id.signupPassword);
    editConfirmPassword = (EditText) findViewById(R.id.signupConfirmPassword);

    FrameLayout buttonSignupNext = (FrameLayout) findViewById(R.id.buttonSignupNext);
    buttonSignupNext.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View view) {
        String name = editName.getText().toString().trim();
        String email = editEmailID.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String confirmPassword = editConfirmPassword.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (name.isEmpty()){
          editName.setError("Please fill the required fields");
          editName.requestFocus();
          return;
        }

        if(email.isEmpty() || !email.matches(emailPattern)){
          editEmailID.setError("Invalid Email address");
          editEmailID.requestFocus();
          editEmailID.selectAll();
          return;
        }


        if (password.length()<6){
          editPassword.setError("Password must be of minimum 6 characters length");
          editPassword.requestFocus();
          editPassword.selectAll();
          return;
        }

        if (!confirmPassword.equals(password)){
          editPassword.setError("Password does not match the confirm password.");
          editPassword.requestFocus();
          editPassword.selectAll();
          editConfirmPassword.setText("");
          return;
          }




      }
    });
  }
}
