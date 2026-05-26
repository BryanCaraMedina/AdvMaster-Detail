package es.ulpgc.eite.da.advmasterdetail.seriedetail;

import java.lang.ref.WeakReference;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;

/**
 * Interface defining the contract between the View, Presenter and Model for the Serie Detail screen.
 */
public interface SerieDetailContract {

    /**
     * Interface for the View layer of the Serie Detail screen.
     */
    interface View {
        /**
         * Injects the presenter into the view.
         * @param presenter The presenter instance.
         */
        void injectPresenter(Presenter presenter);

        /**
         * Updates the UI with the serie details and favorite status.
         * @param viewModel The view model containing data and states.
         */
        void displaySerieDetailData(SerieDetailViewModel viewModel);
    }

    /**
     * Interface for the Presenter layer of the Serie Detail screen.
     */
    interface Presenter {
        void injectView(WeakReference<View> view);
        void injectModel(Model model);
        
        void onCreateCalled();
        void onRecreateCalled();
        void onPauseCalled();

        /**
         * Fetches detailed information for the currently selected serie.
         */
        void fetchSerieDetailData();

        /**
         * Toggles the favorite status for the current serie for the logged-in user.
         */
        void toggleFavorite();
    }

    /**
     * Interface for the Model layer of the Serie Detail screen.
     */
    interface Model {
        /**
         * Toggles favorite status in the repository.
         */
        void toggleSerieFavorite(int userId, int serieId, RepositoryContract.ActionCallback callback);

        /**
         * Checks if a serie is marked as favorite by a specific user.
         */
        void isSerieFavorite(int userId, int serieId, RepositoryContract.FavoriteCheckCallback callback);
    }
}
