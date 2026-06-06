package es.ulpgc.eite.da.advmasterdetail.series;

import java.lang.ref.WeakReference;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;
import es.ulpgc.eite.da.advmasterdetail.data.SerieEntity;

/**
 * Interface defining the contract between the View, Presenter and Model for the Serie List screen.
 */
public interface SerieListContract {

    /**
     * Interface for the View layer of the Serie List screen.
     */
    interface View {
        /**
         * Injects the presenter into the view.
         * @param presenter The presenter instance.
         */
        void injectPresenter(Presenter presenter);

        /**
         * Updates the UI with the data from the view model.
         * @param viewModel The view model containing series and state.
         */
        void displaySerieListData(SerieListViewModel viewModel);

        /**
         * Navigates to the Serie Detail screen.
         */
        void navigateToSerieDetailScreen();

        /**
         * Navigates to the Movie List screen.
         */
        void navigateToMovieListScreen();

        /**
         * Navigates back to the Login screen.
         */
        void navigateToLoginScreen();

        /**
         * Updates the title of the toolbar based on the filter state.
         * @param showingFavorites True if only favorites are shown.
         */
        void updateToolbarTitle(boolean showingFavorites);

        /**
         * Shows a message indicating that a user account is required.
         */
        void showUserRequiredMessage();
    }

    /**
     * Interface for the Presenter layer of the Serie List screen.
     */
    interface Presenter {
        void injectView(WeakReference<View> view);
        void injectModel(Model model);

        /**
         * Fetches the serie data from the model.
         */
        void fetchSerieListData();

        /**
         * Handles the selection of a serie from the list.
         * @param item The selected entity.
         */
        void selectSerieData(SerieEntity item);

        /**
         * Toggles the filter to show either all series or only favorites.
         */
        void toggleFavoritesFilter();

        /**
         * Handles the click on the "Go to Movies" menu item.
         */
        void onMovieListMenuClicked();

        /**
         * Handles the click on the "Logout" menu item.
         */
        void onLogoutMenuClicked();

        void onCreateCalled();
        void onRecreateCalled();
        void onPauseCalled();
    }

    /**
     * Interface for the Model layer of the Serie List screen.
     */
    interface Model {
        /**
         * Fetches all series from the repository.
         * @param callback Result callback.
         */
        void fetchSerieListData(RepositoryContract.GetSerieListCallback callback);

        /**
         * Fetches only the favorite series for a specific user.
         * @param userId The ID of the user.
         * @param callback Result callback.
         */
        void fetchFavoriteSeries(int userId, RepositoryContract.GetSerieListCallback callback);
    }
}
