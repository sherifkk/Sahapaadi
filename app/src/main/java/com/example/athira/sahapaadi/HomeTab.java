package com.example.athira.sahapaadi;

/**
 * Created by athira on 3/4/17.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class HomeTab extends Fragment {
    private String arry[] = { "Tofeeq", "Ahmad", "Fragment", "Example",
            "Tofeeq", "Ahmad", "Fragment", "Example" };

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        ListView lv = (ListView)view.findViewById(R.id.listviewid);
        ArrayAdapter<String> array = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1);
        for (String str : arry)
            array.add(str);
        lv.setAdapter(array);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


            }
        });

       
        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.hometab, container, false);


    }
}
