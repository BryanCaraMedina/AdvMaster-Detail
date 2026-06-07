package es.ulpgc.eite.da.advmasterdetail.series;

import java.lang.ref.WeakReference;
import es.ulpgc.eite.da.advmasterdetail.app.CatalogMediator;
import es.ulpgc.eite.da.advmasterdetail.app.SessionManager;
import es.ulpgc.eite.da.advmasterdetail.data.SerieEntity;
import es.ulpgc.eite.da.advmasterdetail.data.UserEntity;

public class SerieListPresenter implements SerieListContract.Presenter {

    private WeakReference<SerieListContract.View> view;
    private SerieListState state;
    private SerieListContract.Model model;
    private CatalogMediator mediator;
    private SessionManager sessionManager;

    public SerieListPresenter(CatalogMediator mediator, SessionManager sessionManager) {
        this.mediator = mediator;
        this.sessionManager = sessionManager;
    }

    @Override
    public void onCreateCalled() {
        state = new SerieListState();
    }

    @Override
    public void onRecreateCalled() {
        state = mediator.getSerieListState();
        if (state == null) {
            state = new SerieListState();
        }
    }

    @Override
    public void onPauseCalled() {
        mediator.setSerieListState(state);
    }

    @Override
    public void fetchSerieListData() {
        UserEntity user = mediator.getUser();

        if (state.showingFavorites && user != null) {
            model.fetchFavoriteSeries(user.id, series -> {
                state.series = series;
                view.get().displaySerieListData(state);
            });
        } else {
            model.fetchSerieListData(series -> {
                state.series = series;
                view.get().displaySerieListData(state);
            });
        }
        view.get().updateToolbarTitle(state.showingFavorites);
    }

    @Override
    public void toggleFavoritesFilter() {
        if (mediator.getUser() == null) {
            view.get().showUserRequiredMessage();
            return;
        }
        state.showingFavorites = !state.showingFavorites;
        fetchSerieListData();
    }

    @Override
    public void onMovieListMenuClicked() {
        view.get().navigateToMovieListScreen();
    }

    @Override
    public void onLogoutMenuClicked() {
        mediator.setUser(null);
        sessionManager.logout();
        view.get().navigateToLoginScreen();
    }

    @Override
    public void onBackPressed() {
        if (state.showingFavorites) {
            state.showingFavorites = false;
            fetchSerieListData();
        } else {
            view.get().finishView();
        }
    }

    @Override
    public void selectSerieData(SerieEntity item) {
        mediator.setSerie(item);
        view.get().navigateToSerieDetailScreen();
    }

    @Override
    public void injectView(WeakReference<SerieListContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(SerieListContract.Model model) {
        this.model = model;
    }
}
