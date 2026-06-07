package es.ulpgc.eite.da.advmasterdetail.login;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.ulpgc.eite.da.advmasterdetail.R;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class EspressoTests {

    @Rule
    public ActivityScenarioRule<LoginActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void espressoTests() {
        // --- 1. PROBAR PELÍCULAS (GUEST) ---
        onView(withId(R.id.skip_login_button)).perform(click());

        // Ver detalle de la primera película y volver
        onView(withId(R.id.movie_recycler)).perform(actionOnItemAtPosition(0, click()));
        pressBack(); 
        
        // Volver al Login desde la lista (1 solo back porque en modo invitado no hay favoritos)
        pressBack(); 

        // --- 2. PROBAR SERIES (GUEST) ---
        onView(withId(R.id.radio_series)).perform(click());
        onView(withId(R.id.skip_login_button)).perform(click());

        // Ver detalle de una serie y volver
        onView(withId(R.id.serie_recycler)).perform(actionOnItemAtPosition(0, click()));
        pressBack();

        // Probar cambio directo de listas (Series -> Películas)
        onView(withId(R.id.btn_switch_to_movies)).perform(click());
        
        // Volver al Login
        pressBack();

        // --- 3. PROBAR REGISTRO ---
        onView(withId(R.id.go_to_register_button)).perform(click());
        pressBack(); // Volver al Login

        // --- 4. PROBAR DIÁLOGO DE SALIDA EN LOGIN ---
        // Pulsamos atrás en el Login para ver el mensaje que pediste
        pressBack(); 
        onView(withText("Salir")).check(matches(isDisplayed()));
        onView(withText("No")).perform(click()); // Cancelamos para que el test termine limpiamente
    }
}
