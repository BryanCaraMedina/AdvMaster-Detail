package es.ulpgc.eite.da.advmasterdetail.series;

import java.lang.ref.WeakReference;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;
import es.ulpgc.eite.da.advmasterdetail.data.SerieEntity;

public interface SerieListContract {

    interface View {
        void injectPresenter(Presenter presenter);
        void displaySerieListData(SerieListViewModel viewModel);
        void navigateToSerieDetailScreen();
        void navigateToMovieListScreen();
        void navigateToLoginScreen();
        void updateToolbarTitle(boolean showingFavorites);
    }

    interface Presenter {
        void injectView(WeakReference<View> view);
        void injectModel(Model model);
        void fetchSerieListData();
        void selectSerieData(SerieEntity item);
        void toggleFavoritesFilter();
        void onMovieListMenuClicked();
        void onLogoutMenuClicked();
        void onCreateCalled();
        void onRecreateCalled();
        void onPauseCalled();
    }

    interface Model {
        void fetchSerieListData(RepositoryContract.GetSerieListCallback callback);
        void fetchFavoriteSeries(int userId, RepositoryContract.GetSerieListCallback callback);
    }
}
