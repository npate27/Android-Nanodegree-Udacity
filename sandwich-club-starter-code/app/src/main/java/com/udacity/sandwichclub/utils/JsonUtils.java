package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    //Name-related keys in the given JSON
    private static final String SW_NAME = "name";
    private static final String SW_MAINANME = "mainName";
    private static final String SW_AKA = "alsoKnownAs";         //array

    private static final String SW_PLACEOFORIGIN = "placeOfOrigin";
    private static final String SW_DESCRIPTION = "description";
    private static final String SW_IMAGE = "image";
    private static final String SW_INGREDIENTS = "ingredients"; //array

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        JSONObject sandwichJson = new JSONObject(json);

        JSONObject nameJsonObj = sandwichJson.getJSONObject(SW_NAME);
        JSONArray akaNameJsonArr = nameJsonObj.getJSONArray(SW_AKA);
        JSONArray ingredientsJsonObj = sandwichJson.getJSONArray(SW_INGREDIENTS);

        String mainName = nameJsonObj.getString(SW_MAINANME);
        List<String> alsoKnownAs = jsonArrToList(akaNameJsonArr);
        String placeOfOrigin = sandwichJson.getString(SW_PLACEOFORIGIN);
        String description =  sandwichJson.getString(SW_DESCRIPTION);
        String image = sandwichJson.getString(SW_IMAGE);
        List<String> ingredients = jsonArrToList(ingredientsJsonObj);

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }

    /**
     * Converts a given JSONArray into a List<String> object
     *
     * @param  jsonArray the JSONArray to be converted
     * @return           the List<String> representation of jsonArray
     */
    private static List<String> jsonArrToList(JSONArray jsonArray) throws JSONException {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            stringList.add(jsonArray.getString(i));
        }
        return stringList;
    }
}
