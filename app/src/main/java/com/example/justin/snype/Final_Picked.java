package com.example.justin.snype;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yelp.fusion.client.connection.YelpFusionApi;
import com.yelp.fusion.client.connection.YelpFusionApiFactory;
import com.yelp.fusion.client.models.Business;
import com.yelp.fusion.client.models.SearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

public class Final_Picked extends AppCompatActivity {
    String food_list = "";
    String temp = "";
    YelpFusionApiFactory mapiFactory;
    YelpFusionApi myelpFusionApi;
    Map<String, String> params = new HashMap<>();
    int counter = 0;
    int counter2 = 0;
    int total_counter = 0;
    String[] food_types;
    String[] results;
    ArrayList<String> results2 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final__picked);
        final TextView food_name = (TextView) findViewById(R.id.food_name);
        final ConstraintLayout main_layout = (ConstraintLayout) findViewById(R.id.main_layout);
        SharedPreferences read = getSharedPreferences("finalpicker", MODE_PRIVATE);
        String latitude = read.getString("latitude", "No name defined");
        String longitude = read.getString("longitude", "No name defined");
        String food_names = read.getString("food_names", "No name defined");
        String[] food_names_splitter = food_names.split("#");
        counter = food_names_splitter.length;
        Log.v("counter", Integer.toString(counter) );
        food_types = new String[food_names_splitter.length];
        for(int i = 0; i<food_names_splitter.length; i++){
            String[] word_splitter = food_names_splitter[i].split("@");
            temp = word_splitter[1].substring(1,word_splitter[1].length()-1);
            food_types[i] = temp;
        }
        int max = 50/counter;
        Log.v("FINAL: ", food_list);
        food_list = food_list + "food";

        mapiFactory = new YelpFusionApiFactory();
        try{
            myelpFusionApi = mapiFactory.createAPI(getString(R.string.yelp_api_key));
        }catch(IOException e){
            e.printStackTrace();
        }
        params.put("term", "japanese food");
        params.put("limit", Integer.toString(max));
        params.put("latitude", latitude);
        params.put("longitude",longitude);

        new getRestaurants().execute();

            main_layout.setOnTouchListener(new OnSwipeTouchListener(Final_Picked.this) {
                public void onSwipeTop() {
                    Toast.makeText(Final_Picked.this, "UP", Toast.LENGTH_SHORT).show();
                }
                public void onSwipeRight() {
                    counter2--;
                    if(counter2<0){
                        counter2 = results.length-1;
                    }
                    food_name.setText(results[counter2]);
                    Toast.makeText(Final_Picked.this, "RIGHT", Toast.LENGTH_SHORT).show();
                }
                public void onSwipeLeft() {
                    counter2++;
                    if(counter2>results.length-1){
                        counter2=0;
                    }
                    food_name.setText(results[counter2]);
                    Toast.makeText(Final_Picked.this, "LEFT", Toast.LENGTH_SHORT).show();
                }
                public void onSwipeBottom() {
                    Toast.makeText(Final_Picked.this, "DOWN", Toast.LENGTH_SHORT).show();
                }
            });


    }

    class getRestaurants extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            final TextView food_name = (TextView) findViewById(R.id.food_name);
            for(int i = 0; i<counter; i++) {
                Log.v("NEW FOOD: ", food_types[i] );
                params.put("term", food_types[i]);
                Call<SearchResponse> call = myelpFusionApi.getBusinessSearch(params);
                SearchResponse searchResponse = null;
                try {
                    Log.v("SUCCESS: ", "SUCCESS" );
                    searchResponse = call.execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ArrayList<Business> businesses = searchResponse.getBusinesses();
                Log.v("SIZE: ", Integer.toString(businesses.size()));
                //Double rating = businesses.get(0).getRating();
                if (searchResponse != null) {
                    try{
                        if(results2.size() > 0){
                            food_name.setText(results2.get(0));
                        }
                    }
                    catch (Exception e){

                    }
                    for(int j = 0; j<businesses.size(); j++){
                        results2.add(businesses.get(j).getName());
                        Log.v("THE LIST: ",businesses.get(j).getName());
                    }
                }
            }
            results = results2.toArray(new String[results2.size()]);
            for(int k = 0; k<results.length; k++){
                Log.v("INDEX",Integer.toString(k));
                Log.v("WE HAVE THE LIST",results[k]);
            }
            return null;
        }
    }
}
