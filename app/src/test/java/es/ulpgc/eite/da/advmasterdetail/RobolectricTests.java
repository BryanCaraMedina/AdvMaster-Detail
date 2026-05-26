package es.ulpgc.eite.da.advmasterdetail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.Lifecycle;
import androidx.room.Room;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLooper;

import java.util.concurrent.Executor;

import es.ulpgc.eite.da.advmasterdetail.app.CatalogMediator;
import es.ulpgc.eite.da.advmasterdetail.app.SessionManager;
import es.ulpgc.eite.da.advmasterdetail.data.CatalogRepository;
import es.ulpgc.eite.da.advmasterdetail.data.MovieEntity;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;
import es.ulpgc.eite.da.advmasterdetail.data.SerieEntity;
import es.ulpgc.eite.da.advmasterdetail.database.CatalogDatabase;
import es.ulpgc.eite.da.advmasterdetail.login.LoginActivity;
import es.ulpgc.eite.da.advmasterdetail.moviedetail.MovieDetailActivity;
import es.ulpgc.eite.da.advmasterdetail.movies.MovieListActivity;
import es.ulpgc.eite.da.advmasterdetail.register.RegisterActivity;
import es.ulpgc.eite.da.advmasterdetail.seriedetail.SerieDetailActivity;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class RobolectricTests {

    private CatalogDatabase database;
    private SessionManager sessionManager;

    @Before
    public void setup() {
        Context context = ApplicationProvider.getApplicationContext();
        
        // Use in-memory database for testing
        database = Room.inMemoryDatabaseBuilder(context, CatalogDatabase.class)
                .allowMainThreadQueries()
                .build();
        CatalogDatabase.setTestInstance(database);

        // Synchronous executor for testing
        Executor synchronousExecutor = Runnable::run;
        RepositoryContract repository = CatalogRepository.getInstance(context);
        if (repository instanceof CatalogRepository) {
            ((CatalogRepository) repository).setExecutor(synchronousExecutor);
        }

        sessionManager = new SessionManager(context);
        sessionManager.logout();
        CatalogMediator.resetInstance();
    }

    @After
    public void tearDown() {
        database.close();
    }

    @Test
    public void testRegistrationFlow() {
        try (ActivityScenario<RegisterActivity> scenario = ActivityScenario.launch(RegisterActivity.class)) {
            scenario.onActivity(activity -> {
                ((EditText) activity.findViewById(R.id.register_email)).setText("test_user");
                ((EditText) activity.findViewById(R.id.register_password)).setText("1234");
                activity.findViewById(R.id.register_button).performClick();
                
                // Idle looper to process background tasks and callbacks
                ShadowLooper.idleMainLooper();
                
                assertTrue("RegisterActivity should be finishing", activity.isFinishing());
            });
        }
    }

    @Test
    public void testLoginFlow() {
        // Prepare database with a user
        database.userDao().insertUser(new es.ulpgc.eite.da.advmasterdetail.data.UserEntity("test_user", "1234"));

        try (ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class)) {
            scenario.onActivity(activity -> {
                ((EditText) activity.findViewById(R.id.login_email)).setText("test_user");
                ((EditText) activity.findViewById(R.id.login_password)).setText("1234");
                activity.findViewById(R.id.login_button).performClick();

                ShadowLooper.idleMainLooper();

                Intent nextIntent = shadowOf(activity).getNextStartedActivity();
                assertNotNull("Next activity should be started", nextIntent);
                assertEquals(MovieListActivity.class.getName(), nextIntent.getComponent().getClassName());
            });
        }
    }

    @Test
    public void testGuestModeHidesFavorites() {
        CatalogMediator.getInstance().setUser(null);
        MovieEntity movie = new MovieEntity();
        movie.id = 1;
        movie.title = "Test Movie";
        CatalogMediator.getInstance().setMovie(movie);

        try (ActivityScenario<MovieDetailActivity> scenario = ActivityScenario.launch(MovieDetailActivity.class)) {
            scenario.onActivity(activity -> {
                ShadowLooper.idleMainLooper();
                View favButton = activity.findViewById(R.id.favorite_button);
                assertEquals("Favorite button should be GONE for guest", View.GONE, favButton.getVisibility());
            });
        }
    }

    @Test
    public void testFavoritesButtonVisibleForLoggedUser() {
        es.ulpgc.eite.da.advmasterdetail.data.UserEntity user = new es.ulpgc.eite.da.advmasterdetail.data.UserEntity("user", "pass");
        user.id = 1;
        CatalogMediator.getInstance().setUser(user);
        
        MovieEntity movie = new MovieEntity();
        movie.id = 1;
        movie.title = "Test Movie";
        CatalogMediator.getInstance().setMovie(movie);

        try (ActivityScenario<MovieDetailActivity> scenario = ActivityScenario.launch(MovieDetailActivity.class)) {
            scenario.onActivity(activity -> {
                ShadowLooper.idleMainLooper();
                View favButton = activity.findViewById(R.id.favorite_button);
                assertEquals("Favorite button should be VISIBLE for logged user", View.VISIBLE, favButton.getVisibility());
            });
        }
    }

    @Test
    public void testActivityRecreation() {
        try (ActivityScenario<MovieListActivity> scenario = ActivityScenario.launch(MovieListActivity.class)) {
            scenario.moveToState(Lifecycle.State.RESUMED);
            scenario.recreate();
            scenario.onActivity(activity -> {
                assertNotNull("RecyclerView should exist after recreation", activity.findViewById(R.id.movie_recycler));
            });
        }
    }
}
