package com.example.justin.snype;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final Button mybutton=(Button)findViewById(R.id.next_button);
        mybutton.setVisibility(View.GONE);
        final TextView search = (TextView) findViewById(R.id.Swipe);
        // THERE ARE 124 DIFFERENT CATEGORIES
        final String[] categories = new String[]{"Afghan@(afghani)", "African@(african)", "American (New)@(newamerican)", "American (Traditional)@(tradamerican)", "Arabian@(arabian)", "Argentine@(argentine)", "Armenian@(armenian)", "Asian Fusion@(asianfusion)", "Australian@(australian)", "Austrian@(austrian)", "Bangladeshi@(bangladeshi)", "Barbeque@(bbq)", "Basque@(basque)", "Belgian@(belgian)", "Brasseries@(brasseries)", "Brazilian@(brazilian)", "Breakfast & Brunch@(breakfast_brunch)", "British@(british)", "Buffets@(buffets)", "Bulgarian@(bulgarian)", "Burgers@(burgers)", "Burmese@(burmese)", "Cafes@(cafes)", "Cafeteria@(cafeteria)", "Cajun/Creole@(cajun)", "Cambodian@(cambodian)", "Caribbean@(caribbean)", "Catalan@(catalan)", "Cheesesteaks@(cheesesteaks)", "Chicken Shop@(chickenshop)", "Chicken Wings@(chicken_wings)", "Chinese@(chinese)", "Comfort Food@(comfortfood)", "Creperies@(creperies)", "Cuban@(cuban)", "Czech@(czech)", "Delis@(delis)", "Diners@(diners)", "Dinner Theater@(dinnertheater)", "Ethiopian@(ethiopian)", "Fast Food@(hotdogs)", "Filipino@(filipino)", "Fish & Chips@(fishnchips)", "Fondue@(fondue)", "Food Court@(food_court)", "Food Stands@(foodstands)", "French@(french)", "Game Meat@(gamemeat)", "Gastropubs@(gastropubs)", "Georgian@(georgian)", "German@(german)", "Gluten-Free@(gluten_free)", "Greek@(greek)", "Guamanian@(guamanian)", "Halal@(halal)", "Hawaiian@(hawaiian)", "Himalayan/Nepalese@(himalayan)", "Honduran@(honduran)", "Hong Kong Style Cafe@(hkcafe)", "Hot Dogs@(hotdog)", "Hot Pot@(hotpot)", "Hungarian@(hungarian)", "Iberian@(iberian)","Indian@(indpak)", "Indonesian@(indonesian)", "Irish@(irish)", "Italian@(italian)", "Japanese@(japanese)", "Kebab@(kebab)", "Korean@(korean)", "Kosher@(kosher)","Laotian@(laotian)", "Latin American@(latin)", "Live/Raw Food@(raw_food)", "Malaysian@(malaysian)", "Mediterranean@(mediterranean)", "Mexican@(mexican)", "Middle Eastern @(mideastern)", "Modern European@(modern_european)", "Mongolian@(mongolian)", "Moroccan@(moroccan)", "New Mexican Cuisine@(newmexican)", "Nicaraguan@(nicaraguan)", "Noodles@(noodles)", "Pakistani@(pakistani)", "Pan Asian@(panasian)", "Persian/Iranian@(persian)", "Peruvian@(peruvian)", "Pizza@(pizza)", "Polish@(polish)", "Polynesian@(polynesian)", "Pop-Up Restaurants@(popuprestaurants)", "Portuguese@(portuguese)", "Poutineries@(poutineries)", "Russian@(russian)", "Salad@(salad)", "Sandwiches@(sandwiches)", "Scandinavian@(scandinavian)", "Scottish@(scottish)", "Seafood@(seafood)", "Singaporean@(singaporean)", "Slovakian@(slovakian)", "Soul Food@(soulfood)", "Soup@(soup)", "Southern@(southern)", "Spanish@(spanish)", "Sri Lankan@(srilankan)", "Steakhouses@(steak)", "Supper Clubs@(supperclubs)", "Sushi Bars@(sushi)", "Syrian@(syrian)", "Taiwanese@(taiwanese)", "Tapas Bars@(tapas)", "Tapas/Small Plates@(tapasmallplates)", "Tex-Mex@(tex-mex)", "Thai@(thai)", "Turkish@(turkish)", "Ukrainian@(ukrainian)", "Uzbek@(uzbek)", "Vegan@(vegan)", "Vegetarian@(vegetarian)", "Vietnamese@(vietnamese)", "Waffles@(waffles)", "Wraps@(wraps)"};
        shuffleArray(categories);
        String[] cat_split = categories[0].split("@");
        search.setText(cat_split[0]);
        search.setOnTouchListener(new OnSwipeTouchListener(Search.this) {
            SharedPreferences read = getSharedPreferences("discarded", MODE_PRIVATE);
            SharedPreferences.Editor edit = getSharedPreferences("discarded", MODE_PRIVATE).edit();
            SharedPreferences read1 = getSharedPreferences("saved", MODE_PRIVATE);
            SharedPreferences.Editor edit1 = getSharedPreferences("saved", MODE_PRIVATE).edit();
            String temp = "";
            int counter = 0;
            int deleted = 0;
            public void onSwipeTop() {
                mybutton.setVisibility(View.VISIBLE);
                String[] cat_split = categories[counter].split("@");
                String saver = "";
                edit1.putString(cat_split[0], "true");
                if(!read1.contains("saved")){
                    edit1.putString("saved", cat_split[0]+"@");
                }
                else{
                    temp = read1.getString("saved", "No name defined");
                    temp = temp+(cat_split[0]+"@");
                    edit1.putString("saved", temp);
                }
                Toast.makeText(Search.this, temp, Toast.LENGTH_SHORT).show();
                saver = cat_split[0];
                edit1.commit();
                counter++;
                boolean found = true;
                while(found) {
                    if (counter >= categories.length) {
                        counter = 0;
                    }
                    cat_split = categories[counter].split("@");
                    if(read.contains(cat_split[0])||read1.contains(cat_split[0])){
                        counter++;
                    }
                    else{
                        found = false;
                        search.setText(cat_split[0]);
                    }
                }
                Toast.makeText(Search.this, "saved " + saver, Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                counter--;
                boolean found = true;
                while(found) {
                    if (counter < 0) {
                        counter = categories.length;
                    }
                    String[] cat_split = categories[counter].split("@");
                    if(read.contains(cat_split[0])||read1.contains(cat_split[0])){
                        counter--;
                    }
                    else{
                        found = false;
                        search.setText(cat_split[0]);
                    }
                }
            }
            public void onSwipeLeft() {
                counter++;
                boolean found = true;
                while(found) {
                    if (counter >= categories.length) {
                        counter = 0;
                    }
                    String[] cat_split = categories[counter].split("@");
                    if(read.contains(cat_split[0])||read1.contains(cat_split[0])){
                        counter++;
                    }
                    else{
                        found = false;
                        search.setText(cat_split[0]);
                    }
                }
            }
            public void onSwipeBottom() {
                String[] cat_split = categories[counter].split("@");
                String deleter = cat_split[0];
                edit.putString(cat_split[0], "true");
                edit.commit();
                counter++;
                boolean found = true;
                while(found) {
                    if (counter >= categories.length) {
                        counter = 0;
                    }
                    cat_split = categories[counter].split("@");
                    if(read.contains(cat_split[0])||read1.contains(cat_split[0])){
                        counter++;
                    }
                    else{
                        found = false;
                        search.setText(cat_split[0]);
                    }
                }
                Toast.makeText(Search.this, "deleted " + deleter, Toast.LENGTH_SHORT).show();
            }

        });
    }
    static void shuffleArray(String[] ar)
    {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
    public void reset(View view){
        SharedPreferences.Editor edit = getSharedPreferences("discarded", MODE_PRIVATE).edit();
        edit.clear().commit();
        Toast.makeText(Search.this, "Reset Clicked", Toast.LENGTH_SHORT).show();
    }
    public void next(View view){
        SharedPreferences.Editor edit = getSharedPreferences("saved", MODE_PRIVATE).edit();
        edit.clear().commit();
        Toast.makeText(Search.this, "Next Clicked", Toast.LENGTH_SHORT).show();
    }
}
