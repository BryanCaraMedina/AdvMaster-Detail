package es.ulpgc.eite.da.advmasterdetail.movies;

import java.lang.ref.WeakReference;
import es.ulpgc.eite.da.advmasterdetail.data.MovieEntity;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;

public interface MovieListContract {

    interface View {
        void injectPresenter(Presenter presenter);
        void displayMovieListData(MovieListViewModel viewModel);
        void navigateToMovieDetailScreen();
        void navigateToSerieListScreen();
        void navigateToLoginScreen();
        void updateToolbarTitle(boolean showingFavorites);
    }

    interface Presenter {
        void injectView(WeakReference<View> view);
        void injectModel(Model model);
        void fetchMovieListData();
        void selectMovieData(MovieEntity item);
        void toggleFavoritesFilter();
        void onSerieListMenuClicked();
        void onLogoutMenuClicked();
        void onCreateCalled();
        void onRecreateCalled();
        void onPauseCalled();
    }

    interface Model {
        void fetchMovieListData(RepositoryContract.GetMovieListCallback callback);
        void fetchFavoriteMovies(int userId, RepositoryContract.GetMovieListCallback callback);
    }
}
