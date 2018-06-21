package com.example.justin.snype;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Main_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__page);
        SharedPreferences read = getSharedPreferences("names", MODE_PRIVATE);
        String get_name = read.getString("name", "No name defined");
        final TextView greeting = (TextView) findViewById(R.id.greeting);
        String username = get_name;
        greeting.setText("Welcome, " + username + "!");
    }
}
