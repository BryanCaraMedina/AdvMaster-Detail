package es.ulpgc.eite.da.advmasterdetail.moviedetail;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import es.ulpgc.eite.da.advmasterdetail.R;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailContract.View {

    private MovieDetailContract.Presenter presenter;
    private TextView titleView, descriptionView;
    private ImageView imageView;
    private Button favoriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        setTitle(R.string.title_movie_detail);

        titleView = findViewById(R.id.movie_title);
        descriptionView = findViewById(R.id.movie_description);
        imageView = findViewById(R.id.movie_image);
        favoriteButton = findViewById(R.id.favorite_button);

        favoriteButton.setOnClickListener(v -> presenter.toggleFavorite());

        MovieDetailScreen.configure(this);

        if (savedInstanceState == null) {
            presenter.onCreateCalled();
        } else {
            presenter.onRecreateCalled();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.fetchMovieDetailData();
    }

    @Override
    public void displayMovieDetailData(MovieDetailViewModel viewModel) {
        runOnUiThread(() -> {
            if (viewModel.movie != null) {
                titleView.setText(viewModel.movie.title);
                descriptionView.setText(viewModel.movie.description);
                
                if (viewModel.movie.imageUrl != null && !viewModel.movie.imageUrl.isEmpty()) {
                    Glide.with(this).load(viewModel.movie.imageUrl).into(imageView);
                }

                if (viewModel.showFavoriteButton) {
                    favoriteButton.setVisibility(View.VISIBLE);
                    if (viewModel.isFavorite) {
                        favoriteButton.setText(R.string.btn_remove_favorite);
                    } else {
                        favoriteButton.setText(R.string.btn_add_favorite);
                    }
                } else {
                    favoriteButton.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void injectPresenter(MovieDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPauseCalled();
    }
}
