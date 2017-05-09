package com.example.athira.sahapaadi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginPage extends AppCompatActivity {

  private ProgressDialog pDialog;
  private SessionManager session;
  private DBHelper dbHelper;
  private EditText editEmailID;
  private EditText editPassword;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_page);
    FrameLayout buttonLogin = (FrameLayout) findViewById(R.id.buttonLogin);

    pDialog = new ProgressDialog(this);
    pDialog.setCancelable(false);

    dbHelper = new DBHelper(this);
    session = new SessionManager(this);

    buttonLogin.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View view) {
        editEmailID = (EditText) findViewById(R.id.loginEmailID);
        editPassword = (EditText) findViewById(R.id.loginPassword);
        String email = editEmailID.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(email.isEmpty() || !email.matches(emailPattern)){
          YoYo.with(Techniques.Shake)
              .duration(700)
              .repeat(2)
              .playOn(editEmailID);
          editEmailID.setError("Invalid Email address");
          editEmailID.requestFocus();
          editEmailID.selectAll();
          return;
        }

        if (password.length()<6){
          YoYo.with(Techniques.Shake)
              .duration(700)
              .repeat(2)
              .playOn(editPassword);
          editPassword.setError("Password must be of minimum 6 characters length");
          editPassword.requestFocus();
          editPassword.selectAll();
          return;
        }
        checkLogin(email, password);
      }
    });

    LinearLayout buttonSignup = (LinearLayout) findViewById(R.id.buttonSignup);
    buttonSignup.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View view) {

        startActivity(new Intent(LoginPage.this, LoginDetails.class));
      }
    });
  }

  private void checkLogin(final String email, final String password) {
    String URL_LOGIN = "http://sahapaadi.pe.hu/login.php";
    pDialog.setMessage("Logging in ...");
    showDialog();

    StringRequest strReq =
        new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {

          @Override public void onResponse(String response) {
            hideDialog();
            try {
              JSONObject jObj = new JSONObject(response);
              boolean error = jObj.getBoolean("error");
              if (!error) {
                session.setLogin(true);
                dbHelper.revUser();
                dbHelper.initUser();
                dbHelper.putUser(jObj.getString("uid"), jObj.getString("name"), jObj.getString("syid"), jObj.getString("sem"));
                Intent intent = new Intent(LoginPage.this, SplashActivity.class);
                startActivity(intent);
                finish();
              } else {
                YoYo.with(Techniques.Shake)
                    .duration(700)
                    .repeat(2)
                    .playOn(editEmailID);
                editEmailID.setError("Invalid email or Password!");
                editEmailID.requestFocus();
                editEmailID.selectAll();
                editPassword.setText("");
              }
            } catch (JSONException e) {
              Toast.makeText(getApplicationContext(), "Network error, try again!",
                  Toast.LENGTH_LONG).show();
            }
          }
        }, new Response.ErrorListener() {
          @Override public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(), "Network error, try again!", Toast.LENGTH_LONG)
                .show();
            hideDialog();
          }
        }) {
          @Override protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("email", email);
            params.put("password", password);
            return params;
          }
        };
    AppController.getInstance().addToRequestQueue(strReq, "req_login");
  }

  private void showDialog() {
    if (!pDialog.isShowing()) pDialog.show();
  }

  private void hideDialog() {
    if (pDialog.isShowing()) pDialog.dismiss();
  }
}
