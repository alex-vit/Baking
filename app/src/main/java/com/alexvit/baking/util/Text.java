package com.alexvit.baking.util;

import com.alexvit.baking.entity.Ingredient;

import java.util.List;

/**
 * Created by Aleksandrs Vitjukovs on 8/2/2017.
 */

public class Text {

    public static String ingredientsToString(List<Ingredient> ingredients) {

        StringBuilder builder = new StringBuilder();

        for (int i = 1; i < ingredients.size() - 1; i++) {
            builder.append(i);
            builder.append(". ");
            builder.append(ingredients.get(i).ingredient);
            builder.append(", ");
            builder.append(ingredients.get(i).quantity);
            builder.append(ingredients.get(i).measure);
            if (i < ingredients.size()) builder.append("\n");
        }

        return builder.toString();
    }

}
