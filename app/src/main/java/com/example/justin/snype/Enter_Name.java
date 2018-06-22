package com.example.justin.snype;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.OutputStreamWriter;

public class Enter_Name extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter__name);
        Button myButton = (Button) findViewById(R.id.Next_Button);
        final EditText text1 = (EditText) findViewById(R.id.Name_Next);
        myButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                if(text1.getText().toString().equals("Name") || text1.getText().toString().equals("Name")){
                    Toast.makeText(Enter_Name.this,"What was your name again?",Toast.LENGTH_LONG).show();

                }
                else {
                    SharedPreferences.Editor name = getSharedPreferences("names", MODE_PRIVATE).edit();
                    name.putString("name", text1.getText().toString());
                    name.commit();

                    Intent intent = new Intent(Enter_Name.this, Main_Page.class);
                    Bundle bundle = new Bundle();
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

    }
}
