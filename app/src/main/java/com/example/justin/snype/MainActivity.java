package com.example.justin.snype;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.yelp.fusion.client.connection.YelpFusionApi;
import com.yelp.fusion.client.connection.YelpFusionApiFactory;
import com.yelp.fusion.client.models.Business;
import com.yelp.fusion.client.models.SearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    YelpFusionApiFactory mapiFactory;
    YelpFusionApi myelpFusionApi;
    Map<String, String> params = new HashMap<>();
    private LocationManager locationManager;
    private LocationListener locationListener;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }else{
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            longitude = lastKnownLocation.getLongitude();
            latitude = lastKnownLocation.getLatitude();
        }

//        mapiFactory = new YelpFusionApiFactory();
//        try{
//            myelpFusionApi = mapiFactory.createAPI(getString(R.string.yelp_api_key));
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//        params.put("term", "chinese japanese food");
//        params.put("limit", "50");
//        params.put("latitude", Double.toString(latitude));
//        params.put("longitude", Double.toString(longitude));
//
//        new getRestaurants().execute();

        Button myButton = (Button) findViewById(R.id.login_button);
        myButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void guest_clicked(View view){
        Intent intent = new Intent(MainActivity.this, Enter_Name.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);
    }
//    class getRestaurants extends AsyncTask<String, String, String>{
//
//        @Override
//        protected String doInBackground(String... strings) {
//            Call<SearchResponse> call = myelpFusionApi.getBusinessSearch(params);
//            SearchResponse searchResponse = null;
//            try{
//                searchResponse=call.execute().body();
//            }catch(IOException e){
//                e.printStackTrace();
//            }
//            ArrayList<Business> businesses = searchResponse.getBusinesses();
//            Double rating = businesses.get(0).getRating();
//            if(searchResponse != null){
//                for(int i = 0; i<businesses.size(); i++){
//                    Log.v("Business: ", businesses.get(i).getName());
//                }
//                Log.v("Rating: ", rating.toString());
//            }
//            return null;
//        }
//    }
}
