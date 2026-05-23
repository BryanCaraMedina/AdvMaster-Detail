package es.ulpgc.eite.da.advmasterdetail.moviedetail;

import java.lang.ref.WeakReference;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;

public interface MovieDetailContract {

    interface View {
        void injectPresenter(Presenter presenter);
        void displayMovieDetailData(MovieDetailViewModel viewModel);
    }

    interface Presenter {
        void injectView(WeakReference<View> view);
        void injectModel(Model model);
        
        void onCreateCalled();
        void onRecreateCalled();
        void onPauseCalled();
        
        void fetchMovieDetailData();
        void toggleFavorite();
    }

    interface Model {
        void toggleMovieFavorite(int userId, int movieId, RepositoryContract.ActionCallback callback);
        void isMovieFavorite(int userId, int movieId, RepositoryContract.FavoriteCheckCallback callback);
    }
}
