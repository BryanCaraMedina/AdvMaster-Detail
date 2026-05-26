package es.ulpgc.eite.da.advmasterdetail.series;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.ulpgc.eite.da.advmasterdetail.R;
import es.ulpgc.eite.da.advmasterdetail.data.SerieEntity;
import es.ulpgc.eite.da.advmasterdetail.login.LoginActivity;
import es.ulpgc.eite.da.advmasterdetail.movies.MovieListActivity;
import es.ulpgc.eite.da.advmasterdetail.seriedetail.SerieDetailActivity;

public class SerieListActivity extends AppCompatActivity implements SerieListContract.View {

    private SerieListContract.Presenter presenter;
    private SerieListAdapter listAdapter;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serie_list);
        
        emptyView = findViewById(R.id.serie_empty_view);

        SerieListScreen.configure(this);

        initSerieListContainer();

        if (savedInstanceState == null) {
            presenter.onCreateCalled();
        } else {
            presenter.onRecreateCalled();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.fetchSerieListData();
    }

    private void initSerieListContainer() {
        listAdapter = new SerieListAdapter(view -> {
            SerieEntity item = (SerieEntity) view.getTag();
            presenter.selectSerieData(item);
        });

        RecyclerView recyclerView = findViewById(R.id.serie_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.serie_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_toggle_favorites) {
            presenter.toggleFavoritesFilter();
            return true;
        } else if (id == R.id.action_go_to_movies) {
            presenter.onMovieListMenuClicked();
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
    public void displaySerieListData(SerieListViewModel viewModel) {
        runOnUiThread(() -> {
            listAdapter.setItems(viewModel.series);

            if (viewModel.series == null || viewModel.series.isEmpty()) {
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
    public void navigateToSerieDetailScreen() {
        Intent intent = new Intent(this, SerieDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToMovieListScreen() {
        Intent intent = new Intent(this, MovieListActivity.class);
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
            setTitle(R.string.title_favorite_series);
        } else {
            setTitle(R.string.title_series);
        }
    }

    @Override
    public void injectPresenter(SerieListContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
