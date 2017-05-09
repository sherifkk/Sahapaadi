package com.example.athira.sahapaadi;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.yalantis.jellytoolbar.listener.JellyListener;
import com.yalantis.jellytoolbar.widget.JellyToolbar;

public class ReaderActivity extends AppCompatActivity {

  private static final String TEXT_KEY = "text";

  private JellyToolbar toolbar;
  private AppCompatEditText editText;

  private JellyListener jellyListener = new JellyListener() {
    @Override public void onCancelIconClicked() {
      if (TextUtils.isEmpty(editText.getText())) {
        toolbar.collapse();
      } else {
        editText.getText().clear();
      }
    }
  };

  TextView mTextView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_reader);

    mTextView = (TextView) findViewById(R.id.textView);

    toolbar = (JellyToolbar) findViewById(R.id.toolbar);
    toolbar.getToolbar().setNavigationIcon(R.drawable.ic_menu);
    toolbar.setJellyListener(jellyListener);
    toolbar.getToolbar().setPadding(0, getStatusBarHeight(), 0, 0);

    editText = (AppCompatEditText) LayoutInflater.from(this).inflate(R.layout.edit_text1, null);
    editText.setBackgroundResource(R.color.colorTransparent);
    editText.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        highlightString(charSequence.toString().length());
      }

      @Override public void afterTextChanged(Editable editable) {

      }
    });
    toolbar.setContentView(editText);

    getWindow().getDecorView()
        .setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

  }

  private void highlightString(int s) {
    SpannableString spannableString = new SpannableString(mTextView.getText());
    BackgroundColorSpan[] backgroundColorSpen = spannableString.getSpans(0,spannableString.length(),BackgroundColorSpan.class);
    for(BackgroundColorSpan span: backgroundColorSpen){
      spannableString.removeSpan(span);
    }

    //while (co> 0){


      spannableString.setSpan(new BackgroundColorSpan(Color.YELLOW), 3, s+3,
          Spanned.SPAN_COMPOSING);
    //}

    mTextView.setText(spannableString);
  }

  private int getStatusBarHeight() {
    int result = 0;
    int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
      result = getResources().getDimensionPixelSize(resourceId);
    }
    return result;
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    outState.putString(TEXT_KEY, editText.getText().toString());
    super.onSaveInstanceState(outState);
  }

  @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    editText.setText(savedInstanceState.getString(TEXT_KEY));
    editText.setSelection(editText.getText().length());
  }

}
