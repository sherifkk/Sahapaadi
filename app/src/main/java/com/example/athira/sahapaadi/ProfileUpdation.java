

package com.example.athira.sahapaadi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.yalantis.jellytoolbar.listener.JellyListener;
import com.yalantis.jellytoolbar.widget.JellyToolbar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileUpdation extends AppCompatActivity {

  private ProgressDialog pDialog;

  DBHelper dbhelper;
  Cursor cursor;

  ArrayList<String> universityId;
  ArrayList<String> universityName;
  ArrayList<String> programId;
  ArrayList<String> programName;
  ArrayList<Integer> programYear;
  ArrayList<String> schemeId;
  ArrayList<String> schemeName;

  String name;
  String email;
  String password;

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
    setContentView(R.layout.activity_profile_update);

    toolbar = (JellyToolbar) findViewById(R.id.toolbar);
    toolbar.getToolbar().setNavigationIcon(R.drawable.ic_menu);
    toolbar.setJellyListener(jellyListener);
    toolbar.getToolbar().setPadding(0, getStatusBarHeight(), 0, 0);



    editText = (AppCompatTextView) LayoutInflater.from(this).inflate(R.layout.edit_text, null);
    editText.setBackgroundResource(R.color.colorTransparent);
    editText.setText("Update Info");
    toolbar.setContentView(editText);

    getWindow().getDecorView()
        .setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

    pDialog = new ProgressDialog(this);
    pDialog.setCancelable(false);

    final Spinner university = (Spinner) findViewById(R.id.university);
    final Spinner program = (Spinner) findViewById(R.id.program);
    final Spinner scheme = (Spinner) findViewById(R.id.schema);
    final EditText editSemester = (EditText) findViewById(R.id.semester);

    editSemester.setText("6");

    universityId = new ArrayList<>();
    universityName = new ArrayList<>();
    programId = new ArrayList<>();
    programName = new ArrayList<>();
    programYear = new ArrayList<>();
    schemeId = new ArrayList<>();
    schemeName = new ArrayList<>();

    dbhelper = new DBHelper(this);
    cursor = dbhelper.getUniversity();
    while (cursor.moveToNext()) {
      universityId.add(cursor.getString(0));
      universityName.add(cursor.getString(1));
    }
    university.setAdapter(new SpinnerAdapter(this, universityName));

    university.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        programId.clear();
        programName.clear();
        programYear.clear();

        cursor = dbhelper.getProgram(universityId.get(i));
        while (cursor.moveToNext()) {
          programId.add(cursor.getString(0));
          programName.add(cursor.getString(1));
          programYear.add(cursor.getInt(2) * 2);
        }
        program.setAdapter(new SpinnerAdapter(ProfileUpdation.this, programName));
      }

      @Override public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });

    program.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        schemeId.clear();
        schemeName.clear();

        cursor = dbhelper.getScheme(programId.get(i));
        while (cursor.moveToNext()) {
          schemeId.add(cursor.getString(0));
          schemeName.add(cursor.getString(1));
        }
        scheme.setAdapter(new SpinnerAdapter(ProfileUpdation.this, schemeName));
      }

      @Override public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });



    FrameLayout buttonSignup = (FrameLayout) findViewById(R.id.buttonSignup);
    buttonSignup.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View view) {
        String semester = editSemester.getText().toString().trim();
        if (semester.isEmpty()
            || Integer.parseInt(semester) <= 0
            || Integer.parseInt(semester) > programYear.get(program.getSelectedItemPosition())) {
          YoYo.with(Techniques.Shake).duration(700).repeat(2).playOn(editSemester);
          editSemester.setError("Invalid Semester");
          editSemester.setText("");
          editSemester.requestFocus();
          return;
        }
        dbhelper.updateUser(semester);
        startActivity(new Intent(ProfileUpdation.this,SplashActivity.class));
        //signUp(name,email,password,schemeId.get(scheme.getSelectedItemPosition()),semester);
      }
    });
  }

  private void signUp(final String name, final String email, final String password, final String scheme, final String semester) {
    String URL_LOGIN = "http://sahapaadi.pe.hu/signup.php";
    pDialog.setMessage("Loading ...");
    showDialog();

    StringRequest strReq =
        new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
          @Override public void onResponse(String response) {
            hideDialog();
            try {
              JSONObject jObj = new JSONObject(response);
              boolean error = jObj.getBoolean("error");
              if (!error) {

                startActivity(new Intent(ProfileUpdation.this, SplashActivity.class));
                finish();
              } else {
                Toast.makeText(getApplicationContext(), "Network error, try again!",
                    Toast.LENGTH_LONG).show();
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
          }
        }) {
          @Override protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("email", email);
            params.put("password", password);
            params.put("name", name);
            params.put("scheme", scheme);
            params.put("semester", semester);
            return params;
          }
        };
    AppController.getInstance().addToRequestQueue(strReq, "req_signup");

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
