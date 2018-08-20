package com.intkhabahmed.bakenshake;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.intkhabahmed.bakenshake.activities.MainActivity;
import com.intkhabahmed.bakenshake.fragments.RecipesFragment;
import com.intkhabahmed.bakenshake.utils.MockData;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipeListTest {
    private static final String RECIPE_NAME = "Nutella Pie";
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);
    private IdlingResource mIdlingResource;

    @Before
    public void setupFragmentAndRegisterIdlingResource() {
        RecipesFragment recipesFragment = new RecipesFragment();
        activityTestRule.getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment_container, recipesFragment).commit();
        mIdlingResource = recipesFragment.getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void testClickOnRecipeList() throws InterruptedException {
        onView(withId(R.id.recipes_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recipe_name_tv)).check(matches(withText(MockData.getRecipeById(1).getRecipeName())));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}
