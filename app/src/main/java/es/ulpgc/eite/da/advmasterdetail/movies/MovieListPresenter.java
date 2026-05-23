package es.ulpgc.eite.da.advmasterdetail.movies;

import java.lang.ref.WeakReference;
import es.ulpgc.eite.da.advmasterdetail.app.CatalogMediator;
import es.ulpgc.eite.da.advmasterdetail.data.MovieEntity;
import es.ulpgc.eite.da.advmasterdetail.data.UserEntity;

public class MovieListPresenter implements MovieListContract.Presenter {

    private WeakReference<MovieListContract.View> view;
    private MovieListState state;
    private MovieListContract.Model model;
    private CatalogMediator mediator;

    public MovieListPresenter(CatalogMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void onCreateCalled() {
        state = new MovieListState();
    }

    @Override
    public void onRecreateCalled() {
        state = mediator.getMovieListState();
    }

    @Override
    public void onPauseCalled() {
        mediator.setMovieListState(state);
    }

    @Override
    public void fetchMovieListData() {
        UserEntity user = mediator.getUser();
        
        if (state.showingFavorites && user != null) {
            model.fetchFavoriteMovies(user.id, movies -> {
                state.movies = movies;
                view.get().displayMovieListData(state);
            });
        } else {
            model.fetchMovieListData(movies -> {
                state.movies = movies;
                view.get().displayMovieListData(state);
            });
        }
        view.get().updateToolbarTitle(state.showingFavorites);
    }

    @Override
    public void toggleFavoritesFilter() {
        if (mediator.getUser() == null) return;
        state.showingFavorites = !state.showingFavorites;
        fetchMovieListData();
    }

    @Override
    public void onSerieListMenuClicked() {
        view.get().navigateToSerieListScreen();
    }

    @Override
    public void onLogoutMenuClicked() {
        mediator.setUser(null);
        view.get().navigateToLoginScreen();
    }

    @Override
    public void selectMovieData(MovieEntity item) {
        mediator.setMovie(item);
        view.get().navigateToMovieDetailScreen();
    }

    @Override
    public void injectView(WeakReference<MovieListContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(MovieListContract.Model model) {
        this.model = model;
    }
}
