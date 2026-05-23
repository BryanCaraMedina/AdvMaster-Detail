package es.ulpgc.eite.da.advmasterdetail.seriedetail;

import java.lang.ref.WeakReference;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;

public interface SerieDetailContract {

    interface View {
        void injectPresenter(Presenter presenter);
        void displaySerieDetailData(SerieDetailViewModel viewModel);
    }

    interface Presenter {
        void injectView(WeakReference<View> view);
        void injectModel(Model model);
        void fetchSerieDetailData();
        void toggleFavorite();
        void onCreateCalled();
        void onRecreateCalled();
        void onPauseCalled();
    }

    interface Model {
        void toggleSerieFavorite(int userId, int serieId, RepositoryContract.ActionCallback callback);
        void isSerieFavorite(int userId, int serieId, RepositoryContract.FavoriteCheckCallback callback);
    }
}
