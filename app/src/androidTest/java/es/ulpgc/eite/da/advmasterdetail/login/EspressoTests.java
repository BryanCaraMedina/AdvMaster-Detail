package es.ulpgc.eite.da.advmasterdetail.login;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.pm.ActivityInfo;

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

    // --- UTILIDADES PARA ROTACIÓN ---

    private void girarAHorizontal() {
        mActivityScenarioRule.getScenario().onActivity(activity ->
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE));
    }

    private void girarAVertical() {
        mActivityScenarioRule.getScenario().onActivity(activity ->
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT));
    }

    // --- PRUEBAS DE LA PANTALLA DE LOGIN ---

    @Test
    public void login_pruebaRotacionPantalla() {
        girarAHorizontal();
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
        girarAVertical();
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
    }

    @Test
    public void login_pruebaAparicionDialogoSalida() {
        // Al pulsar atrás en el Login debe aparecer el diálogo
        pressBack();
        onView(withText("Salir")).check(matches(isDisplayed()));
        onView(withText("¿Seguro que quieres salir de la aplicación?")).check(matches(isDisplayed()));
        
        // Cerramos el diálogo para que no interfiera con otros tests
        onView(withText("No")).perform(click());
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
    }

    // --- PRUEBAS DE PELÍCULAS ---

    @Test
    public void peliculas_pruebaAccesoInvitadoYGiro() {
        onView(withId(R.id.skip_login_button)).perform(click());
        onView(withId(R.id.movie_recycler)).check(matches(isDisplayed()));
        
        girarAHorizontal();
        onView(withId(R.id.movie_recycler)).check(matches(isDisplayed()));
    }

    @Test
    public void peliculas_pruebaVerDetalleYGiro() {
        onView(withId(R.id.skip_login_button)).perform(click());
        onView(withId(R.id.movie_recycler)).perform(actionOnItemAtPosition(0, click()));
        
        onView(withId(R.id.movie_title)).check(matches(isDisplayed()));
        
        girarAHorizontal();
        onView(withId(R.id.movie_title)).check(matches(isDisplayed()));
        
        pressBack(); // Volver a la lista
        onView(withId(R.id.movie_recycler)).check(matches(isDisplayed()));
    }

    @Test
    public void peliculas_pruebaVolverAlLoginYGiro() {
        onView(withId(R.id.skip_login_button)).perform(click());
        girarAHorizontal();
        pressBack(); // Volver al Login
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
    }

    // --- PRUEBAS DE SERIES ---

    @Test
    public void series_pruebaAccesoInvitadoYGiro() {
        onView(withId(R.id.radio_series)).perform(click());
        onView(withId(R.id.skip_login_button)).perform(click());
        
        onView(withId(R.id.serie_recycler)).check(matches(isDisplayed()));
        girarAHorizontal();
        onView(withId(R.id.serie_recycler)).check(matches(isDisplayed()));
    }

    @Test
    public void series_pruebaVerDetalleYGiro() {
        onView(withId(R.id.radio_series)).perform(click());
        onView(withId(R.id.skip_login_button)).perform(click());
        onView(withId(R.id.serie_recycler)).perform(actionOnItemAtPosition(0, click()));
        
        onView(withId(R.id.serie_title)).check(matches(isDisplayed()));
        girarAHorizontal();
        onView(withId(R.id.serie_title)).check(matches(isDisplayed()));
    }

    @Test
    public void series_pruebaVolverAlLoginYGiro() {
        onView(withId(R.id.radio_series)).perform(click());
        onView(withId(R.id.skip_login_button)).perform(click());
        girarAHorizontal();
        pressBack();
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
    }

    @Test
    public void navegacion_pruebaCambioDirectoSeriesAPeliculasYGiro() {
        onView(withId(R.id.radio_series)).perform(click());
        onView(withId(R.id.skip_login_button)).perform(click());
        
        // Botón de cambio directo
        onView(withId(R.id.btn_switch_to_movies)).perform(click());
        girarAHorizontal();
        onView(withId(R.id.movie_recycler)).check(matches(isDisplayed()));
    }

    // --- PRUEBAS DE REGISTRO ---

    @Test
    public void registro_pruebaNavegacionYGiro() {
        onView(withId(R.id.go_to_register_button)).perform(click());
        onView(withId(R.id.register_button)).check(matches(isDisplayed()));
        
        girarAHorizontal();
        onView(withId(R.id.register_button)).check(matches(isDisplayed()));
        
        pressBack(); // Volver al login
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
    }
}
