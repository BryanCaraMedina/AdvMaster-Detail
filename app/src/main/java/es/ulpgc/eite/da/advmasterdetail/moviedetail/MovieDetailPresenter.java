package es.ulpgc.eite.da.advmasterdetail.moviedetail;

import java.lang.ref.WeakReference;
import es.ulpgc.eite.da.advmasterdetail.app.CatalogMediator;
import es.ulpgc.eite.da.advmasterdetail.data.MovieEntity;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;
import es.ulpgc.eite.da.advmasterdetail.data.UserEntity;

public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    private WeakReference<MovieDetailContract.View> view;
    private MovieDetailState state;
    private MovieDetailContract.Model model;
    private CatalogMediator mediator;

    public MovieDetailPresenter(CatalogMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void onCreateCalled() {
        state = new MovieDetailState();
    }

    @Override
    public void onRecreateCalled() {
        state = mediator.getMovieDetailState();
        if (state == null) {
            state = new MovieDetailState();
        }
    }

    @Override
    public void onPauseCalled() {
        mediator.setMovieDetailState(state);
    }

    @Override
    public void fetchMovieDetailData() {
        MovieEntity movie = mediator.getMovie();
        if (movie != null) {
            state.movie = movie;
        }

        UserEntity user = mediator.getUser();
        state.showFavoriteButton = (user != null);

        if (user != null && state.movie != null) {
            model.isMovieFavorite(user.id, state.movie.id, isFavorite -> {
                state.isFavorite = isFavorite;
                view.get().displayMovieDetailData(state);
            });
        } else {
            view.get().displayMovieDetailData(state);
        }
    }

    @Override
    public void toggleFavorite() {
        UserEntity user = mediator.getUser();
        if (user == null || state.movie == null) return;

        model.toggleMovieFavorite(user.id, state.movie.id, new RepositoryContract.ActionCallback() {
            @Override
            public void onSuccess() {
                fetchMovieDetailData();
            }

            @Override
            public void onError(String message) {
                // Handle error
            }
        });
    }

    @Override
    public void injectView(WeakReference<MovieDetailContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(MovieDetailContract.Model model) {
        this.model = model;
    }
}
