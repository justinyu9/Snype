package com.example.justin.snype;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Picked extends AppCompatActivity {

    private ArrayList<String> data = new ArrayList<String>();
    private ArrayList<String> data2 = new ArrayList<String>();
    private LocationManager locationManager;
    private LocationListener locationListener;
    double latitude;
    double longitude;
    String final_picked = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picked);
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


        ListView lv = (ListView) findViewById(R.id.chosen_list);
        generateListContent();
        lv.setAdapter(new MyListAdaper(this, R.layout.list_item, data));
    }
    private void generateListContent(){
        SharedPreferences read1 = getSharedPreferences("saved", MODE_PRIVATE);
        String[] categories = new String[]{"Afghan@(afghani)", "African@(african)", "American (New)@(newamerican)", "American (Traditional)@(tradamerican)", "Arabian@(arabian)", "Argentine@(argentine)", "Armenian@(armenian)", "Asian Fusion@(asianfusion)", "Australian@(australian)", "Austrian@(austrian)", "Bangladeshi@(bangladeshi)", "Barbeque@(bbq)", "Basque@(basque)", "Belgian@(belgian)", "Brasseries@(brasseries)", "Brazilian@(brazilian)", "Breakfast & Brunch@(breakfast_brunch)", "British@(british)", "Buffets@(buffets)", "Bulgarian@(bulgarian)", "Burgers@(burgers)", "Burmese@(burmese)", "Cafes@(cafes)", "Cafeteria@(cafeteria)", "Cajun/Creole@(cajun)", "Cambodian@(cambodian)", "Caribbean@(caribbean)", "Catalan@(catalan)", "Cheesesteaks@(cheesesteaks)", "Chicken Shop@(chickenshop)", "Chicken Wings@(chicken_wings)", "Chinese@(chinese)", "Comfort Food@(comfortfood)", "Creperies@(creperies)", "Cuban@(cuban)", "Czech@(czech)", "Delis@(delis)", "Diners@(diners)", "Dinner Theater@(dinnertheater)", "Ethiopian@(ethiopian)", "Fast Food@(hotdogs)", "Filipino@(filipino)", "Fish & Chips@(fishnchips)", "Fondue@(fondue)", "Food Court@(food_court)", "Food Stands@(foodstands)", "French@(french)", "Game Meat@(gamemeat)", "Gastropubs@(gastropubs)", "Georgian@(georgian)", "German@(german)", "Gluten-Free@(gluten_free)", "Greek@(greek)", "Guamanian@(guamanian)", "Halal@(halal)", "Hawaiian@(hawaiian)", "Himalayan/Nepalese@(himalayan)", "Honduran@(honduran)", "Hong Kong Style Cafe@(hkcafe)", "Hot Dogs@(hotdog)", "Hot Pot@(hotpot)", "Hungarian@(hungarian)", "Iberian@(iberian)","Indian@(indpak)", "Indonesian@(indonesian)", "Irish@(irish)", "Italian@(italian)", "Japanese@(japanese)", "Kebab@(kebab)", "Korean@(korean)", "Kosher@(kosher)","Laotian@(laotian)", "Latin American@(latin)", "Live/Raw Food@(raw_food)", "Malaysian@(malaysian)", "Mediterranean@(mediterranean)", "Mexican@(mexican)", "Middle Eastern @(mideastern)", "Modern European@(modern_european)", "Mongolian@(mongolian)", "Moroccan@(moroccan)", "New Mexican Cuisine@(newmexican)", "Nicaraguan@(nicaraguan)", "Noodles@(noodles)", "Pakistani@(pakistani)", "Pan Asian@(panasian)", "Persian/Iranian@(persian)", "Peruvian@(peruvian)", "Pizza@(pizza)", "Polish@(polish)", "Polynesian@(polynesian)", "Pop-Up Restaurants@(popuprestaurants)", "Portuguese@(portuguese)", "Poutineries@(poutineries)", "Russian@(russian)", "Salad@(salad)", "Sandwiches@(sandwiches)", "Scandinavian@(scandinavian)", "Scottish@(scottish)", "Seafood@(seafood)", "Singaporean@(singaporean)", "Slovakian@(slovakian)", "Soul Food@(soulfood)", "Soup@(soup)", "Southern@(southern)", "Spanish@(spanish)", "Sri Lankan@(srilankan)", "Steakhouses@(steak)", "Supper Clubs@(supperclubs)", "Sushi Bars@(sushi)", "Syrian@(syrian)", "Taiwanese@(taiwanese)", "Tapas Bars@(tapas)", "Tapas/Small Plates@(tapasmallplates)", "Tex-Mex@(tex-mex)", "Thai@(thai)", "Turkish@(turkish)", "Ukrainian@(ukrainian)", "Uzbek@(uzbek)", "Vegan@(vegan)", "Vegetarian@(vegetarian)", "Vietnamese@(vietnamese)", "Waffles@(waffles)", "Wraps@(wraps)"};
        for(int i = 0; i<categories.length; i++){
            if(read1.contains(categories[i])){
                String[] splitter = categories[i].split("@");
                data.add(splitter[0]);
                data2.add(categories[i]);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyListAdaper extends ArrayAdapter<String> {
        private int layout;
        private List<String> mObjects;
        private MyListAdaper(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            mObjects = objects;
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewholder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_text);
                viewHolder.button = (Button) convertView.findViewById(R.id.list_item_button);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewHolder) convertView.getTag();
            mainViewholder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor edit1 = getSharedPreferences("saved", MODE_PRIVATE).edit();
                    String temp = data2.get(position);
                    Toast.makeText(Picked.this, "Deleted " + data.get(position), Toast.LENGTH_SHORT).show();
                    edit1.remove(temp);
                    edit1.apply();
                    data.clear();
                    data2.clear();
                    ListView lv = (ListView) findViewById(R.id.chosen_list);
                    generateListContent();
                    lv.setAdapter(new MyListAdaper(Picked.this, R.layout.list_item, data));
                }
            });
            mainViewholder.title.setText(getItem(position));

            return convertView;
        }
    }
    public class ViewHolder {

        TextView title;
        Button button;
    }
    public void back(View view){
        SharedPreferences read = getSharedPreferences("back_button", MODE_PRIVATE);
        String changer = read.getString("back", "No name defined");
        if(changer.equals("list")){
            Intent intent = new Intent(Picked.this, list_search.class);
            Bundle bundle = new Bundle();
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(Picked.this, Search.class);
            Bundle bundle = new Bundle();
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    public void next(View view){
        SharedPreferences.Editor edit = getSharedPreferences("finalpicker", MODE_PRIVATE).edit();
        edit.putString("longitude", Double.toString(longitude));
        edit.putString("latitude", Double.toString(latitude));
        for(int i = 0 ; i<data2.size(); i++){
            Log.v("FORLOOP: ", data2.get(i));
            final_picked = final_picked+data2.get(i)+"#";
        }
        edit.putString("food_names", final_picked);
        edit.commit();
        Log.v("FINAL PICK: ",final_picked);
        Intent intent = new Intent(Picked.this, Final_Picked.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
