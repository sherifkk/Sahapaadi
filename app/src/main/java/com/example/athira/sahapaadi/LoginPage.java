package com.example.athira.sahapaadi;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Lobster-Regular.ttf");
        TextView tv = (TextView) findViewById(R.id.logo_text1);
        tv.setTypeface(type);
        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
        EditText emailLoginField = (EditText) findViewById(R.id.emailLoginField);
        EditText passwordLoginField = (EditText) findViewById(R.id.passwordLoginField);


    }
    public void loginClick(View v)
    {
        //check if password or email field is empty
        EditText emailLoginField = (EditText) findViewById(R.id.emailLoginField);
        EditText passwordLoginField = (EditText) findViewById(R.id.passwordLoginField);

        if(emailLoginField.getText().length()==0)
            emailLoginField.setError("Email ID is mandatory");
        if(passwordLoginField.getText().length()==0)
            passwordLoginField.setError("Password is mandatory");




    }
    public void sendMessage(View view)
    {
        Intent intent = new Intent(LoginPage.this,LoginDetails.class);
        startActivity(intent);
    }
}
