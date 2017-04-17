package com.example.athira.sahapaadi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        TextView buttonLogin = (TextView) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {

                loginClick();
            }
        });

        LinearLayout buttonSignup = (LinearLayout) findViewById(R.id.buttonSignup);
        buttonSignup.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {

                sendMessage();



            }
        });

    }
    public void loginClick()
    {
        //check if password or email field is empty
        EditText loginEmailID = (EditText) findViewById(R.id.university);
        EditText loginPassword = (EditText) findViewById(R.id.loginPassword);

        if(loginEmailID.getText().length()==0)
            loginEmailID.setError("Email ID is mandatory");
        if(loginPassword.getText().length()==0)
            loginPassword.setError("Password is mandatory");

    }
    public void sendMessage()
    {
        Intent intent = new Intent(LoginPage.this,LoginDetails.class);
        startActivity(intent);
    }
}
