package com.samblancat.pong;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class listefill extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.liste);

        final ListView lv = findViewById(R.id.listname);
        lv.setAdapter(new MyCustomAdapter());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /*** Custom Adapter ***/
    private class MyCustomAdapter extends ArrayAdapter<String> {
        MyCustomAdapter() {
            super(listefill.this, R.layout.item_liste, glob.wordsok);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null) convertView = getLayoutInflater().inflate(R.layout.item_liste, parent,false);
            TextView textViewAtCustomLayout = convertView.findViewById(R.id.text_custom_layout);
            textViewAtCustomLayout.setText(glob.wordsok.get(position));
            return convertView;
        }
    }
}