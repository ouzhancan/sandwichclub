package com.udacity.sandwichclub.utils;

import android.support.annotation.NonNull;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject nameObject = (JSONObject) jsonObject.get(Sandwich.NAME_TAG);
            String mainNameString = nameObject.getString(Sandwich.MAIN_NAME_TAG);
            JSONArray alsoKnownAsJsonArr = nameObject.getJSONArray(Sandwich.ALSO_KNOWN_AS_TAG);
            String placeOfOriginString = jsonObject.getString(Sandwich.PLACE_OF_ORIGIN_TAG);
            String descriptionString = jsonObject.getString(Sandwich.DESCRIPTION_TAG);
            String imageString = jsonObject.getString(Sandwich.IMAGE_TAG);
            JSONArray ingredientJsonArr = jsonObject.getJSONArray(Sandwich.INGREDIENTS_TAG);

            sandwich = new Sandwich();

            sandwich.setMainName(mainNameString);
            sandwich.setAlsoKnownAs(jsonArrayToStringList(alsoKnownAsJsonArr));
            sandwich.setPlaceOfOrigin(placeOfOriginString);
            sandwich.setDescription(descriptionString);
            sandwich.setImage(imageString);
            sandwich.setIngredients(jsonArrayToStringList(ingredientJsonArr));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }

    private static List<String> jsonArrayToStringList(JSONArray jsonArray) throws JSONException {

        int sizeOfArray = jsonArray.length();

        List<String> list = null;

        if( sizeOfArray > 0){
            list = new ArrayList<String>();

            for(int i = 0; i < sizeOfArray; i++){
                list.add(jsonArray.getString(i));
            }
        }

        return list;
    }
}
