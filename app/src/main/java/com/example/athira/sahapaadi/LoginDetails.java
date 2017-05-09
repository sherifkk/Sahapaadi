package com.example.athira.sahapaadi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.yalantis.jellytoolbar.listener.JellyListener;
import com.yalantis.jellytoolbar.widget.JellyToolbar;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginDetails extends AppCompatActivity {

  private ProgressDialog pDialog;

  EditText editName;
  EditText editEmailID;
  EditText editPassword;
  EditText editConfirmPassword;

  DBHelper dbhelper;

  private static final String TEXT_KEY = "text";

  private JellyToolbar toolbar;
  private AppCompatTextView editText;

  private JellyListener jellyListener = new JellyListener() {
    @Override public void onCancelIconClicked() {
      toolbar.collapse();
    }
  };

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_details);

    toolbar = (JellyToolbar) findViewById(R.id.toolbar);
    toolbar.getToolbar().setNavigationIcon(R.drawable.ic_menu);
    toolbar.setJellyListener(jellyListener);
    toolbar.getToolbar().setPadding(0, getStatusBarHeight(), 0, 0);



    editText = (AppCompatTextView) LayoutInflater.from(this).inflate(R.layout.edit_text, null);
    editText.setBackgroundResource(R.color.colorTransparent);
    editText.setText("Personal Info");
    toolbar.setContentView(editText);

    getWindow().getDecorView()
        .setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

    pDialog = new ProgressDialog(this);
    pDialog.setCancelable(false);

    dbhelper = new DBHelper(this);

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
        if (name.isEmpty()) {
          YoYo.with(Techniques.Shake)
              .duration(700)
              .repeat(2)
              .playOn(editName);
          editName.setError("Please fill the required fields");
          editName.requestFocus();
          return;
        }

        if (email.isEmpty() || !email.matches(emailPattern)) {
          YoYo.with(Techniques.Shake)
              .duration(700)
              .repeat(2)
              .playOn(editEmailID);
          editEmailID.setError("Invalid Email address");
          editEmailID.requestFocus();
          editEmailID.selectAll();
          return;
        }

        if (password.length() < 6) {
          YoYo.with(Techniques.Shake)
              .duration(700)
              .repeat(2)
              .playOn(editPassword);
          editPassword.setError("Password must be of minimum 6 characters length");
          editPassword.requestFocus();
          editPassword.selectAll();
          return;
        }

        if (!confirmPassword.equals(password)) {
          YoYo.with(Techniques.Shake)
              .duration(700)
              .repeat(2)
              .playOn(editPassword);
          editPassword.setError("Password does not match the confirm password.");
          editPassword.requestFocus();
          editPassword.selectAll();
          editConfirmPassword.setText("");
          return;
        }
        checkEmail(email,name,password);
      }
    });
  }

  private void checkEmail(final String email,final String name,final String password) {
    String URL_LOGIN = "http://sahapaadi.pe.hu/checkmail.php";
    pDialog.setMessage("Loading ...");
    showDialog();

    StringRequest strReq =
        new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
          @Override public void onResponse(String response) {
            hideDialog();
            try {
              JSONObject jObj = new JSONObject(response);
              Log.d("Rwsponse", "onResponse: "+jObj.toString());
              boolean error = jObj.getBoolean("error");
              if (!error) {
                dbhelper.revRegistration();
                dbhelper.initRegistration();
                JSONObject jsonObject = jObj.getJSONObject("values");
                JSONArray jsonArray = jsonObject.getJSONArray("university");
                for(int i=0;i<jsonArray.length();i++){
                  JSONObject object = jsonArray.getJSONObject(i);
                  dbhelper.putUniversity(object.getString("uid"),object.getString("uname"));
                }

                jsonArray = jsonObject.getJSONArray("program");
                for(int i=0;i<jsonArray.length();i++){
                  JSONObject object = jsonArray.getJSONObject(i);
                  dbhelper.putProgram(object.getString("pid"),object.getString("pname"),object.getString("year"));
                }

                jsonArray = jsonObject.getJSONArray("scheme");
                for(int i=0;i<jsonArray.length();i++){
                  JSONObject object = jsonArray.getJSONObject(i);
                  dbhelper.putScheme(object.getString("sid"),object.getString("sname"));
                }

                jsonArray = jsonObject.getJSONArray("up");
                for(int i=0;i<jsonArray.length();i++){
                  JSONObject object = jsonArray.getJSONObject(i);
                  dbhelper.putUniversityProgram(object.getString("upid"),object.getString("uid"),object.getString("pid"));
                }

                jsonArray = jsonObject.getJSONArray("syllabus");
                for(int i=0;i<jsonArray.length();i++){
                  JSONObject object = jsonArray.getJSONObject(i);
                  dbhelper.putSyllabus(object.getString("syid"),object.getString("upid"),object.getString("sid"));
                }
                Intent intent = new Intent(LoginDetails.this, ProfileUpdate.class);
                intent.putExtra("name",name);
                intent.putExtra("email",email);
                intent.putExtra("password",password);
                startActivity(intent);
              } else {
                YoYo.with(Techniques.Shake)
                    .duration(700)
                    .repeat(2)
                    .playOn(editEmailID);
                editEmailID.setError("Email ID Already Exit!");
                editEmailID.requestFocus();
                editEmailID.selectAll();
                editPassword.setText("");
                editConfirmPassword.setText("");
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
            return params;
          }
        };
    AppController.getInstance().addToRequestQueue(strReq, "req_checkemail");
  }

  private void showDialog() {
    if (!pDialog.isShowing()) pDialog.show();
  }

  private void hideDialog() {
    if (pDialog.isShowing()) pDialog.dismiss();
  }

  private int getStatusBarHeight() {
    int result = 0;
    int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
      result = getResources().getDimensionPixelSize(resourceId);
    }
    return result;
  }
}
