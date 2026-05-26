package es.ulpgc.eite.da.advmasterdetail.moviedetail;

import java.lang.ref.WeakReference;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;

/**
 * Interface defining the contract between the View, Presenter and Model for the Movie Detail screen.
 */
public interface MovieDetailContract {

    /**
     * Interface for the View layer of the Movie Detail screen.
     */
    interface View {
        /**
         * Injects the presenter into the view.
         * @param presenter The presenter to be used.
         */
        void injectPresenter(Presenter presenter);

        /**
         * Updates the UI with the movie details and favorite status.
         * @param viewModel The view model containing movie data and favorite state.
         */
        void displayMovieDetailData(MovieDetailViewModel viewModel);
    }

    /**
     * Interface for the Presenter layer of the Movie Detail screen.
     */
    interface Presenter {
        void injectView(WeakReference<View> view);
        void injectModel(Model model);
        
        void onCreateCalled();
        void onRecreateCalled();
        void onPauseCalled();

        /**
         * Fetches detailed information for the currently selected movie.
         */
        void fetchMovieDetailData();

        /**
         * Toggles the favorite status for the current movie for the logged-in user.
         */
        void toggleFavorite();
    }

    /**
     * Interface for the Model layer of the Movie Detail screen.
     */
    interface Model {
        /**
         * Toggles favorite status in the repository.
         */
        void toggleMovieFavorite(int userId, int movieId, RepositoryContract.ActionCallback callback);

        /**
         * Checks if a movie is marked as favorite by a specific user.
         */
        void isMovieFavorite(int userId, int movieId, RepositoryContract.FavoriteCheckCallback callback);
    }
}
