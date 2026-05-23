package es.ulpgc.eite.da.advmasterdetail.seriedetail;

import java.lang.ref.WeakReference;
import es.ulpgc.eite.da.advmasterdetail.app.CatalogMediator;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;
import es.ulpgc.eite.da.advmasterdetail.data.SerieEntity;
import es.ulpgc.eite.da.advmasterdetail.data.UserEntity;

public class SerieDetailPresenter implements SerieDetailContract.Presenter {

    private WeakReference<SerieDetailContract.View> view;
    private SerieDetailState state;
    private SerieDetailContract.Model model;
    private CatalogMediator mediator;

    public SerieDetailPresenter(CatalogMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void onCreateCalled() {
        state = new SerieDetailState();
    }

    @Override
    public void onRecreateCalled() {
        state = mediator.getSerieDetailState();
        if (state == null) {
            state = new SerieDetailState();
        }
    }

    @Override
    public void onPauseCalled() {
        mediator.setSerieDetailState(state);
    }

    @Override
    public void fetchSerieDetailData() {
        SerieEntity serie = mediator.getSerie();
        if (serie != null) {
            state.serie = serie;
        }

        UserEntity user = mediator.getUser();
        state.showFavoriteButton = (user != null);

        if (user != null && state.serie != null) {
            model.isSerieFavorite(user.id, state.serie.id, isFavorite -> {
                state.isFavorite = isFavorite;
                view.get().displaySerieDetailData(state);
            });
        } else {
            view.get().displaySerieDetailData(state);
        }
    }

    @Override
    public void toggleFavorite() {
        UserEntity user = mediator.getUser();
        if (user == null || state.serie == null) return;

        model.toggleSerieFavorite(user.id, state.serie.id, new RepositoryContract.ActionCallback() {
            @Override
            public void onSuccess() {
                fetchSerieDetailData();
            }

            @Override
            public void onError(String message) {
                // Handle error
            }
        });
    }

    @Override
    public void injectView(WeakReference<SerieDetailContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(SerieDetailContract.Model model) {
        this.model = model;
    }
}
