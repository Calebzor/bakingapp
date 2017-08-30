package hu.tvarga.bakingapp;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import hu.tvarga.bakingapp.ui.main.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class DetailStepTest {

	@Rule
	public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(
			MainActivity.class);

	@Test
	public void goingPreviousTillBeginningLeadsBackToStepList() {
		ViewInteraction mainCardFragmentsRecyclerView = onView(withId(R.id.mainCardFragment));
		mainCardFragmentsRecyclerView.perform(actionOnItemAtPosition(0, click()));

		ViewInteraction detailCardFragmentsRecyclerView = onView(withId(R.id.detailFragment));
		// click not the 0 item because that's ingredients, not 1, because that's the first step,
		// so we click 2nd step
		detailCardFragmentsRecyclerView.perform(actionOnItemAtPosition(2, click()));

		ViewInteraction previousButton = onView(withId(R.id.previousStep));
		previousButton.perform(click());

		previousButton = onView(withId(R.id.previousStep));
		previousButton.perform(click());

		detailCardFragmentsRecyclerView = onView(withId(R.id.detailFragment));
		detailCardFragmentsRecyclerView.check(matches(isDisplayed()));
	}

}
