package com.example.justin.snype;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picked);
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
                    data.remove(position);
                    data2.remove(position);
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
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
        Intent intent = new Intent(Picked.this, Search.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
