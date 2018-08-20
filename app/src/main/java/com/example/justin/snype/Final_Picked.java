package com.example.justin.snype;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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
    int total_counter = 0;
    String[] food_types;
    String[] results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final__picked);
        final TextView food_name = (TextView) findViewById(R.id.food_name);
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
        int total = food_names_splitter.length*max;
        results = new String[total];
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
    }

    class getRestaurants extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            for(int i = 0; i<counter; i++) {
                Log.v("NEW FOOD: ", food_types[i] );
                params.put("term", food_types[i]);
                Call<SearchResponse> call = myelpFusionApi.getBusinessSearch(params);
                SearchResponse searchResponse = null;
                try {
                    searchResponse = call.execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ArrayList<Business> businesses = searchResponse.getBusinesses();
                //Double rating = businesses.get(0).getRating();
                if (searchResponse != null) {
                    for(int j = 0; j<businesses.size(); j++){
                        results[total_counter] = businesses.get(j).getName();
                        Log.v("THE LIST: ",results[total_counter]);
                        total_counter++;
                    }
                }
            }
            for(int k = 0; k<results.length; k++){
                Log.v("WE HAVE THE LIST",results[k]);
            }
            return null;
        }
    }
}
