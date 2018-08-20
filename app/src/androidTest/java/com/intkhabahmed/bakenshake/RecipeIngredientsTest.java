package com.intkhabahmed.bakenshake;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.intkhabahmed.bakenshake.activities.DetailActivity;
import com.intkhabahmed.bakenshake.fragments.RecipeDetailFragment;
import com.intkhabahmed.bakenshake.models.RecipeResult;
import com.intkhabahmed.bakenshake.utils.MockData;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RecipeIngredientsTest {
    @Rule
    public ActivityTestRule<DetailActivity> detailActivityActivityTestRule = new ActivityTestRule<>(DetailActivity.class);
    private RecipeDetailFragment recipeDetailFragment;
    private RecipeResult recipe;

    @Before
    public void setupFragment() {
        recipeDetailFragment = new RecipeDetailFragment();
        try {
            recipe = MockData.getRecipeById(1);
            recipeDetailFragment.setRecipeResult(recipe);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        detailActivityActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container_fl, recipeDetailFragment).commit();
    }

    @Test
    public void testFragmentInstanceCreatedOrNot() {
        onView(withId(R.id.ingredients_cv)).perform(click());
        onView(withId(R.id.ingredients_rv)).check(matches(isDisplayed()));
    }

    @After
    public void tearDownResources() {
        recipe = null;
        detailActivityActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction().remove(recipeDetailFragment).commit();
    }
}
