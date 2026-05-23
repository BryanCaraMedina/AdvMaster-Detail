package es.ulpgc.eite.da.advmasterdetail.moviedetail;

import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;

public class MovieDetailModel implements MovieDetailContract.Model {

    private final RepositoryContract repository;

    public MovieDetailModel(RepositoryContract repository) {
        this.repository = repository;
    }

    @Override
    public void toggleMovieFavorite(int userId, int movieId, RepositoryContract.ActionCallback callback) {
        repository.toggleMovieFavorite(userId, movieId, callback);
    }

    @Override
    public void isMovieFavorite(int userId, int movieId, RepositoryContract.FavoriteCheckCallback callback) {
        repository.isMovieFavorite(userId, movieId, callback);
    }
}
