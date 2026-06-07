package es.ulpgc.eite.da.advmasterdetail.movies;

import java.lang.ref.WeakReference;
import es.ulpgc.eite.da.advmasterdetail.data.MovieEntity;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;

/**
 * Interface defining the contract between the View, Presenter and Model for the Movie List screen.
 */
public interface MovieListContract {

    /**
     * Interface for the View layer of the Movie List screen.
     */
    interface View {
        /**
         * Injects the presenter into the view.
         * @param presenter The presenter to be used.
         */
        void injectPresenter(Presenter presenter);

        /**
         * Updates the UI with the data from the view model.
         * @param viewModel The view model containing the movie list and filter state.
         */
        void displayMovieListData(MovieListViewModel viewModel);

        /**
         * Navigates to the Movie Detail screen.
         */
        void navigateToMovieDetailScreen();

        /**
         * Navigates to the Serie List screen.
         */
        void navigateToSerieListScreen();

        /**
         * Navigates back to the Login screen.
         */
        void navigateToLoginScreen();

        /**
         * Closes the current view.
         */
        void finishView();

        /**
         * Updates the title of the toolbar based on the filter state.
         * @param showingFavorites True if only favorites are shown, false otherwise.
         */
        void updateToolbarTitle(boolean showingFavorites);

        /**
         * Shows a message indicating that a user account is required.
         */
        void showUserRequiredMessage();
    }

    /**
     * Interface for the Presenter layer of the Movie List screen.
     */
    interface Presenter {
        void injectView(WeakReference<View> view);
        void injectModel(Model model);

        /**
         * Fetches the movie data from the model.
         */
        void fetchMovieListData();

        /**
         * Handles the selection of a movie from the list.
         * @param item The selected movie entity.
         */
        void selectMovieData(MovieEntity item);

        /**
         * Toggles the filter to show either all movies or only favorites.
         */
        void toggleFavoritesFilter();

        /**
         * Handles the click on the "Go to Series" menu item.
         */
        void onSerieListMenuClicked();

        /**
         * Handles the click on the "Logout" menu item.
         */
        void onLogoutMenuClicked();

        /**
         * Handles the back button press.
         */
        void onBackPressed();

        void onCreateCalled();
        void onRecreateCalled();
        void onPauseCalled();
    }

    /**
     * Interface for the Model layer of the Movie List screen.
     */
    interface Model {
        /**
         * Fetches all movies from the repository.
         * @param callback The callback to receive the results.
         */
        void fetchMovieListData(RepositoryContract.GetMovieListCallback callback);

        /**
         * Fetches only the favorite movies for a specific user.
         * @param userId The ID of the logged-in user.
         * @param callback The callback to receive the results.
         */
        void fetchFavoriteMovies(int userId, RepositoryContract.GetMovieListCallback callback);
    }
}
