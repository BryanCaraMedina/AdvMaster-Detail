package es.ulpgc.eite.da.advmasterdetail.movies;

import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;

public class MovieListModel implements MovieListContract.Model {

    private final RepositoryContract repository;

    public MovieListModel(RepositoryContract repository) {
        this.repository = repository;
    }

    @Override
    public void fetchMovieListData(RepositoryContract.GetMovieListCallback callback) {
        repository.getMovieList(callback);
    }

    @Override
    public void fetchFavoriteMovies(int userId, RepositoryContract.GetMovieListCallback callback) {
        repository.getFavoriteMovies(userId, callback);
    }
}
