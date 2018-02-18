package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.Arrays;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView descriptionView,originView,alsoKnownView,ingredientsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        descriptionView = findViewById(R.id.description_tv);
        originView = findViewById(R.id.origin_tv);
        alsoKnownView = findViewById(R.id.also_known_tv);
        ingredientsView = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        alsoKnownView.setText(polishListtoString(sandwich.getAlsoKnownAs()));
        originView.setText(sandwich.getPlaceOfOrigin());
        descriptionView.setText(sandwich.getDescription());
        ingredientsView.setText(polishListtoString(sandwich.getIngredients()));

    }

    private String polishListtoString(List<String> strArr){

        StringBuilder builder = new StringBuilder();

        if( strArr != null && strArr.size() > 0 ){
            for(int i=0;i<strArr.size();i++){
                builder.append(strArr.get(i));
                builder.append("\t");
            }
        }

        return builder.length()>0? builder.toString():"There are no informations.";
    }
}
