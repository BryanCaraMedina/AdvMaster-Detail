package es.ulpgc.eite.da.advmasterdetail.login;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

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

    // --- PRUEBAS DE PELÍCULAS ---

    @Test
    public void pruebaAccesoPeliculasComoInvitado() {
        onView(withId(R.id.skip_login_button)).perform(click());
        onView(withId(R.id.movie_recycler)).check(matches(isDisplayed()));
    }

    @Test
    public void pruebaVerDetalleDePeliculaYRegresarALista() {
        onView(withId(R.id.skip_login_button)).perform(click());
        onView(withId(R.id.movie_recycler)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.movie_title)).check(matches(isDisplayed()));
        pressBack();
        onView(withId(R.id.movie_recycler)).check(matches(isDisplayed()));
    }

    @Test
    public void pruebaVolverAlLoginDesdeListaPeliculas() {
        onView(withId(R.id.skip_login_button)).perform(click());
        pressBack();
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
    }

    // --- PRUEBAS DE SERIES ---

    @Test
    public void pruebaAccesoSeriesComoInvitado() {
        onView(withId(R.id.radio_series)).perform(click());
        onView(withId(R.id.skip_login_button)).perform(click());
        onView(withId(R.id.serie_recycler)).check(matches(isDisplayed()));
    }

    @Test
    public void pruebaVerDetalleDeSerieYRegresarALista() {
        onView(withId(R.id.radio_series)).perform(click());
        onView(withId(R.id.skip_login_button)).perform(click());
        onView(withId(R.id.serie_recycler)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.serie_title)).check(matches(isDisplayed()));
        pressBack();
        onView(withId(R.id.serie_recycler)).check(matches(isDisplayed()));
    }

    @Test
    public void pruebaVolverAlLoginDesdeListaSeries() {
        onView(withId(R.id.radio_series)).perform(click());
        onView(withId(R.id.skip_login_button)).perform(click());
        pressBack();
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
    }

    @Test
    public void pruebaCambiarDeSeriesAPeliculasDirectamente() {
        onView(withId(R.id.radio_series)).perform(click());
        onView(withId(R.id.skip_login_button)).perform(click());
        onView(withId(R.id.btn_switch_to_movies)).perform(click());
        onView(withId(R.id.movie_recycler)).check(matches(isDisplayed()));
    }

    // --- PRUEBAS DE REGISTRO ---

    @Test
    public void pruebaNavegacionAPantallaDeRegistro() {
        onView(withId(R.id.go_to_register_button)).perform(click());
        onView(withId(R.id.register_button)).check(matches(isDisplayed()));
    }

    @Test
    public void pruebaRegresarAlLoginDesdeRegistro() {
        onView(withId(R.id.go_to_register_button)).perform(click());
        pressBack();
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
    }

    // --- PRUEBAS DE SALIDA (DIÁLOGOS) ---

    @Test
    public void pruebaMostrarDialogoDeSalidaEnLogin() {
        pressBack();
        onView(withText("Salir")).check(matches(isDisplayed()));
        onView(withText("¿Seguro que quieres salir de la aplicación?")).check(matches(isDisplayed()));
    }

    @Test
    public void pruebaCancelarDialogoDeSalidaMantieneEnLogin() {
        pressBack();
        onView(withText("No")).perform(click());
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
    }
}
