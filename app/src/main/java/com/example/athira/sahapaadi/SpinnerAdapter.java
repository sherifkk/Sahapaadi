package com.example.athira.sahapaadi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class SpinnerAdapter extends BaseAdapter {

  Context context;
  ArrayList<String> universityName;
  LayoutInflater inflter;

  public SpinnerAdapter(Context context, ArrayList<String> universityName){
    this.context = context;
    this.universityName = universityName;
    inflter = (LayoutInflater.from(context));
  }

  @Override public int getCount() {
    return universityName.size();
  }

  @Override public Object getItem(int i) {
    return null;
  }

  @Override public long getItemId(int i) {
    return 0;
  }

  @Override public View getView(int i, View view, ViewGroup viewGroup) {
    view = inflter.inflate(R.layout.item_spinner, null);
    TextView textView = (TextView) view.findViewById(R.id.textView);
    textView.setText(universityName.get(i));
    return view;
  }
}
