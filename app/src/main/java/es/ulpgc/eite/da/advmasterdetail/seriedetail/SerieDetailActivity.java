package es.ulpgc.eite.da.advmasterdetail.seriedetail;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import es.ulpgc.eite.da.advmasterdetail.R;

public class SerieDetailActivity extends AppCompatActivity implements SerieDetailContract.View {

    private SerieDetailContract.Presenter presenter;
    private TextView titleView, descriptionView;
    private ImageView imageView;
    private Button favoriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serie_detail);
        setTitle(R.string.title_serie_detail);

        titleView = findViewById(R.id.serie_title);
        descriptionView = findViewById(R.id.serie_description);
        imageView = findViewById(R.id.serie_image);
        favoriteButton = findViewById(R.id.favorite_button);

        favoriteButton.setOnClickListener(v -> presenter.toggleFavorite());

        SerieDetailScreen.configure(this);

        if (savedInstanceState == null) {
            presenter.onCreateCalled();
        } else {
            presenter.onRecreateCalled();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.fetchSerieDetailData();
    }

    @Override
    public void displaySerieDetailData(SerieDetailViewModel viewModel) {
        runOnUiThread(() -> {
            if (viewModel.serie != null) {
                titleView.setText(viewModel.serie.title);
                descriptionView.setText(viewModel.serie.description);

                if (viewModel.serie.imageUrl != null && !viewModel.serie.imageUrl.isEmpty()) {
                    Glide.with(this).load(viewModel.serie.imageUrl).into(imageView);
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
    public void injectPresenter(SerieDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPauseCalled();
    }
}
