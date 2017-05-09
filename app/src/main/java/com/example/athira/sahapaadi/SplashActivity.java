package com.example.athira.sahapaadi;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity {

  DBHelper dbHelper;


  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_splash);

    dbHelper = new DBHelper(this);

    SessionManager session = new SessionManager(getApplicationContext());
    if (!session.isLoggedIn()) {
      Intent intent = new Intent(this, LoginPage.class);
      startActivity(intent);
      finish();
    } else {
      Cursor cursor = dbHelper.getUser();
      cursor.moveToPosition(2);
      String syid = cursor.getString(1);
      cursor.moveToNext();
      String sem = cursor.getString(1);
      update(syid,sem);
    }
  }

  private void update(final String syid, final String sem) {
    String URL_LOGIN = "http://sahapaadi.pe.hu/subject.php";

    StringRequest strReq =
        new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {

          @Override public void onResponse(String response) {
            try {
              JSONArray jsonArray = new JSONArray(response);
              dbHelper.revSub();
              dbHelper.initsub();
              dbHelper.revFav();
              dbHelper.initfav();
              dbHelper.revDown();
              dbHelper.initDown();
              for(int i=0;i<jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                dbHelper.putSub(object.getString("sid"),object.getString("sname"),object.getString("pdf"),object.getString("ppt"),object.getString("notes"),object.getString("qps"),object.getString("books"));
              }
              startActivity(new Intent(SplashActivity.this,MainActivity.class));
              finish();
            } catch (JSONException e) {
              e.printStackTrace();
            }
          }
        }, new Response.ErrorListener() {
          @Override public void onErrorResponse(VolleyError error) {
            startActivity(new Intent(SplashActivity.this,MainActivity.class));
            finish();
          }
        }) {
          @Override protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("syid", syid);
            params.put("sem", sem);
            return params;
          }
        };
    AppController.getInstance().addToRequestQueue(strReq, "req_login");
  }
}
