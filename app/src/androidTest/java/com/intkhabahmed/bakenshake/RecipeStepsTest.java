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
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class RecipeStepsTest {
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
        onView(withId(R.id.steps_cv)).perform(click());
        onView(allOf(withId(R.id.next_step_tv), isCompletelyDisplayed())).check(matches(isDisplayed()));
    }

    @Test
    public void testViewPagerSwipeBehaviour() {
        onView(withId(R.id.steps_cv)).perform(click());
        onView(withId(R.id.steps_vp)).perform(swipeLeft());
        onView(allOf(withId(R.id.description_tv), isCompletelyDisplayed())).check(matches(isDisplayed()));
    }

    @Test
    public void testNextButtonClickBehaviour() {
        onView(withId(R.id.steps_cv)).perform(click());
        onView(withId(R.id.next_step_tv)).perform(click());
        onView(allOf(withId(R.id.description_tv), isCompletelyDisplayed())).check(matches(isDisplayed()));
        onView(withId(R.id.no_of_steps_tv)).check(matches(withText(String.format("%s / %s", "1", recipe.getSteps().size()-1))));
    }

    @Test
    public void testPrevButtonClickBehaviour() {
        onView(withId(R.id.steps_cv)).perform(click());
        onView(withId(R.id.next_step_tv)).perform(click());
        onView(withId(R.id.prev_step_tv)).perform(click());
        onView(withId(R.id.no_of_steps_tv)).check(matches(withText(String.format("%s / %s", "0", recipe.getSteps().size()-1))));
    }

    @After
    public void tearDownResources() {
        recipe = null;
        detailActivityActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction().remove(recipeDetailFragment).commit();
    }
}
