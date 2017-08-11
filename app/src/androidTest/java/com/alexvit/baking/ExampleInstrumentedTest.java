package com.alexvit.baking;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.alexvit.baking.entity.Recipe;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private static final String PACKAGE_NAME = "com.alexvit.baking";

    @Rule
    public IntentsTestRule<RecipeListActivity> mActivityRule = new IntentsTestRule<>(
            RecipeListActivity.class);

    @Test
    public void clickingRecipe_LaunchesDetailActivity() {

        onView(withId(R.id.rv_recipe_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(allOf(
                toPackage(PACKAGE_NAME),
                hasExtra(
                        equalTo(RecipeDetailActivity.TAG_PARCEL_RECIPE),
                        instanceOf(Recipe.class))
                )
        );
    }

    @Test
    public void clickingStep_LaunchesStepActivity() {

        int position = 0;

        onView(withId(R.id.rv_recipe_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(position, click()));
        onView(withId(R.id.rv_steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(position, click()));
        intended(allOf(
                toPackage(PACKAGE_NAME),
                hasExtra(StepActivity.TAG_PARCEL_POSITION, position),
                hasExtra(equalTo(StepActivity.TAG_PARCEL_STEPS), instanceOf(List.class))
        ));
    }
}
