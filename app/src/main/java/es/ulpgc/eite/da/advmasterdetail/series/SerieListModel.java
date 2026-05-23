package es.ulpgc.eite.da.advmasterdetail.series;

import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;

public class SerieListModel implements SerieListContract.Model {

    private final RepositoryContract repository;

    public SerieListModel(RepositoryContract repository) {
        this.repository = repository;
    }

    @Override
    public void fetchSerieListData(RepositoryContract.GetSerieListCallback callback) {
        repository.getSerieList(callback);
    }

    @Override
    public void fetchFavoriteSeries(int userId, RepositoryContract.GetSerieListCallback callback) {
        repository.getFavoriteSeries(userId, callback);
    }
}
