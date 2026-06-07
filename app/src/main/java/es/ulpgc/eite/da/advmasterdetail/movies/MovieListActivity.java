package es.ulpgc.eite.da.advmasterdetail.movies;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
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
    private TextView emptyView;
    private Button btnSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        
        emptyView = findViewById(R.id.movie_empty_view);
        btnSwitch = findViewById(R.id.btn_switch_to_series);

        btnSwitch.setOnClickListener(v -> presenter.onSerieListMenuClicked());

        MovieListScreen.configure(this);

        initMovieListContainer();

        if (savedInstanceState == null) {
            presenter.onCreateCalled();
        } else {
            presenter.onRecreateCalled();
        }
    }

    @Override
    public void onBackPressed() {
        navigateToLoginScreen();
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
        runOnUiThread(() -> {
            listAdapter.setItems(viewModel.movies);
            
            if (viewModel.movies == null || viewModel.movies.isEmpty()) {
                emptyView.setVisibility(View.VISIBLE);
                if (viewModel.showingFavorites) {
                    emptyView.setText(R.string.msg_no_favorites);
                } else {
                    emptyView.setText(R.string.msg_no_data);
                }
            } else {
                emptyView.setVisibility(View.GONE);
            }
        });
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
        finish();
    }

    @Override
    public void navigateToLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void updateToolbarTitle(boolean showingFavorites) {
        if (showingFavorites) {
            setTitle(R.string.title_favorite_movies);
        } else {
            setTitle(R.string.title_movies);
        }
    }

    @Override
    public void showUserRequiredMessage() {
        Toast.makeText(this, R.string.msg_user_required, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void injectPresenter(MovieListContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
