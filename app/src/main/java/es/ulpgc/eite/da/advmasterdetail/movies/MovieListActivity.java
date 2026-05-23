package es.ulpgc.eite.da.advmasterdetail.movies;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.ulpgc.eite.da.advmasterdetail.R;
import es.ulpgc.eite.da.advmasterdetail.data.MovieEntity;
import es.ulpgc.eite.da.advmasterdetail.login.LoginActivity;
import es.ulpgc.eite.da.advmasterdetail.moviedetail.MovieDetailActivity;
import es.ulpgc.eite.da.advmasterdetail.series.SerieListActivity;

public class MovieListActivity extends AppCompatActivity implements MovieListContract.View {

    private MovieListContract.Presenter presenter;
    private MovieListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        setTitle("Movies");

        MovieListScreen.configure(this);

        initMovieListContainer();

        if (savedInstanceState == null) {
            presenter.onCreateCalled();
        } else {
            presenter.onRecreateCalled();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.fetchMovieListData();
    }

    private void initMovieListContainer() {
        listAdapter = new MovieListAdapter(view -> {
            MovieEntity item = (MovieEntity) view.getTag();
            presenter.selectMovieData(item);
        });

        RecyclerView recyclerView = findViewById(R.id.movie_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_toggle_favorites) {
            presenter.toggleFavoritesFilter();
            return true;
        } else if (id == R.id.action_go_to_series) {
            presenter.onSerieListMenuClicked();
            return true;
        } else if (id == R.id.action_logout) {
            presenter.onLogoutMenuClicked();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPauseCalled();
    }

    @Override
    public void displayMovieListData(MovieListViewModel viewModel) {
        runOnUiThread(() -> listAdapter.setItems(viewModel.movies));
    }

    @Override
    public void navigateToMovieDetailScreen() {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToSerieListScreen() {
        Intent intent = new Intent(this, SerieListActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void updateToolbarTitle(boolean showingFavorites) {
        if (showingFavorites) {
            setTitle("Favorite Movies");
        } else {
            setTitle("Movies");
        }
    }

    @Override
    public void injectPresenter(MovieListContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
