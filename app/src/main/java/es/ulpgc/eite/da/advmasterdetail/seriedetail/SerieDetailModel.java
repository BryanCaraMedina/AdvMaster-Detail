package es.ulpgc.eite.da.advmasterdetail.seriedetail;

import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;

public class SerieDetailModel implements SerieDetailContract.Model {

    private final RepositoryContract repository;

    public SerieDetailModel(RepositoryContract repository) {
        this.repository = repository;
    }

    @Override
    public void toggleSerieFavorite(int userId, int serieId, RepositoryContract.ActionCallback callback) {
        repository.toggleSerieFavorite(userId, serieId, callback);
    }

    @Override
    public void isSerieFavorite(int userId, int serieId, RepositoryContract.FavoriteCheckCallback callback) {
        repository.isSerieFavorite(userId, serieId, callback);
    }
}
