package com.example.justin.snype;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class Picked extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picked);
        SharedPreferences read1 = getSharedPreferences("saved", MODE_PRIVATE);
        String temp = read1.getString("saved", "No name defined");
        String[] choose_split = temp.split("#");
        String[] finalized = new String[choose_split.length];
        for(int i = 0; i<choose_split.length; i++){
            String[] splitter = choose_split[i].split("@");
            finalized[i] = splitter[0];
        }
        Arrays.sort(finalized);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Picked.this, android.R.layout.simple_dropdown_item_1line, finalized);
        ListView show = (ListView) findViewById(R.id.chosen_list);
        show.setAdapter(adapter);
    }
}
