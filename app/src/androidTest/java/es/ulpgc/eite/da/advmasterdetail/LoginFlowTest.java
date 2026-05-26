package es.ulpgc.eite.da.advmasterdetail;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.ulpgc.eite.da.advmasterdetail.login.LoginActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginFlowTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void skipLogin_navigatesToMovies() {
        onView(withId(R.id.skip_login_button)).perform(click());
        onView(withId(R.id.movie_recycler)).check(matches(isDisplayed()));
    }

    @Test
    public void loginWithEmptyFields_showsErrorMessage() {
        onView(withId(R.id.login_button)).perform(click());
        onView(withText(R.string.error_fields_empty)).check(matches(isDisplayed()));
    }
}
