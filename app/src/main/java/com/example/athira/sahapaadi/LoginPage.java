package com.example.athira.sahapaadi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);


    }
    public void loginClick(View v)
    {
        //check if password or email field is empty
        EditText loginEmailID = (EditText) findViewById(R.id.university);
        EditText loginPassword = (EditText) findViewById(R.id.loginPassword);

        if(loginEmailID.getText().length()==0)
            loginEmailID.setError("Email ID is mandatory");
        if(loginPassword.getText().length()==0)
            loginPassword.setError("Password is mandatory");




    }
    public void sendMessage(View view)
    {
        Intent intent = new Intent(LoginPage.this,LoginDetails.class);
        startActivity(intent);
    }
}
