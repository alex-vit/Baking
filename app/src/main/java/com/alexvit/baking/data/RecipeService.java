package com.alexvit.baking.data;

import com.alexvit.baking.entity.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by alexander.vitjukov on 08.08.2017.
 */

public interface RecipeService {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> listRecipes();
}
