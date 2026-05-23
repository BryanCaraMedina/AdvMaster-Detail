package es.ulpgc.eite.da.advmasterdetail.data;

import java.util.List;

public interface RepositoryContract {

    interface GetMovieListCallback {
        void setMovieList(List<MovieEntity> movies);
    }

    interface GetMovieCallback {
        void setMovie(MovieEntity movie);
    }

    interface GetSerieListCallback {
        void setSerieList(List<SerieEntity> series);
    }

    interface GetSerieCallback {
        void setSerie(SerieEntity serie);
    }

    interface LoginCallback {
        void onLoginResult(boolean success, UserEntity user);
    }

    interface RegisterCallback {
        void onRegisterResult(boolean success);
    }

    interface ActionCallback {
        void onSuccess();
        void onError(String message);
    }

    interface FavoriteCheckCallback {
        void onFavoriteChecked(boolean isFavorite);
    }

    void getMovieList(GetMovieListCallback callback);
    void getMovie(int id, GetMovieCallback callback);
    void getSerieList(GetSerieListCallback callback);
    void getSerie(int id, GetSerieCallback callback);
    
    void login(String username, String password, LoginCallback callback);
    void register(UserEntity user, RegisterCallback callback);
    
    // Favoritos
    void getFavoriteMovies(int userId, GetMovieListCallback callback);
    void toggleMovieFavorite(int userId, int movieId, ActionCallback callback);
    void isMovieFavorite(int userId, int movieId, FavoriteCheckCallback callback);

    void getFavoriteSeries(int userId, GetSerieListCallback callback);
    void toggleSerieFavorite(int userId, int serieId, ActionCallback callback);
    void isSerieFavorite(int userId, int serieId, FavoriteCheckCallback callback);
}
