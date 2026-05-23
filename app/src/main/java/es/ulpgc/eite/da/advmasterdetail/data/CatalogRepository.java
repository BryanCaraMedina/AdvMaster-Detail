package es.ulpgc.eite.da.advmasterdetail.data;

import android.content.Context;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import es.ulpgc.eite.da.advmasterdetail.database.CatalogDatabase;

public class CatalogRepository implements RepositoryContract {

    private static CatalogRepository INSTANCE;
    private final CatalogDatabase database;
    private final Context context;

    private CatalogRepository(Context context) {
        this.context = context;
        database = CatalogDatabase.getInstance(context);
    }

    public static RepositoryContract getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new CatalogRepository(context);
        }
        return INSTANCE;
    }

    @Override
    public void getMovieList(GetMovieListCallback callback) {
        new Thread(() -> {
            List<MovieEntity> movies = database.movieDao().getMovies();
            if (movies.isEmpty()) {
                loadInitialData();
                movies = database.movieDao().getMovies();
            }
            callback.setMovieList(movies);
        }).start();
    }

    @Override
    public void getMovie(int id, GetMovieCallback callback) {
        new Thread(() -> {
            MovieEntity movie = database.movieDao().getMovie(id);
            callback.setMovie(movie);
        }).start();
    }

    @Override
    public void getSerieList(GetSerieListCallback callback) {
        new Thread(() -> {
            List<SerieEntity> series = database.serieDao().getSeries();
            if (series.isEmpty()) {
                loadInitialData();
                series = database.serieDao().getSeries();
            }
            callback.setSerieList(series);
        }).start();
    }

    @Override
    public void getSerie(int id, GetSerieCallback callback) {
        new Thread(() -> {
            SerieEntity serie = database.serieDao().getSerie(id);
            callback.setSerie(serie);
        }).start();
    }

    private void loadInitialData() {
        try {
            InputStream is = context.getAssets().open("catalog.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            Gson gson = new Gson();
            CatalogData data = gson.fromJson(json, CatalogData.class);

            database.movieDao().insertMovies(data.movies);
            database.serieDao().insertSeries(data.series);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void login(String username, String password, LoginCallback callback) {
        new Thread(() -> {
            UserEntity user = database.userDao().login(username, password);
            callback.onLoginResult(user != null, user);
        }).start();
    }

    @Override
    public void register(UserEntity user, RegisterCallback callback) {
        new Thread(() -> {
            database.userDao().insertUser(user);
            callback.onRegisterResult(true);
        }).start();
    }

    @Override
    public void getFavoriteMovies(int userId, GetMovieListCallback callback) {
        new Thread(() -> {
            List<MovieEntity> movies = database.userDao().getFavoriteMovies(userId);
            callback.setMovieList(movies);
        }).start();
    }

    @Override
    public void toggleMovieFavorite(int userId, int movieId, ActionCallback callback) {
        new Thread(() -> {
            UserMovieCrossRef ref = new UserMovieCrossRef(userId, movieId);
            if (database.userDao().isMovieFavorite(userId, movieId) > 0) {
                database.userDao().deleteFavoriteMovie(ref);
            } else {
                database.userDao().insertFavoriteMovie(ref);
            }
            callback.onSuccess();
        }).start();
    }

    @Override
    public void isMovieFavorite(int userId, int movieId, FavoriteCheckCallback callback) {
        new Thread(() -> {
            boolean isFavorite = database.userDao().isMovieFavorite(userId, movieId) > 0;
            callback.onFavoriteChecked(isFavorite);
        }).start();
    }

    @Override
    public void getFavoriteSeries(int userId, GetSerieListCallback callback) {
        new Thread(() -> {
            List<SerieEntity> series = database.userDao().getFavoriteSeries(userId);
            callback.setSerieList(series);
        }).start();
    }

    @Override
    public void toggleSerieFavorite(int userId, int serieId, ActionCallback callback) {
        new Thread(() -> {
            UserSerieCrossRef ref = new UserSerieCrossRef(userId, serieId);
            if (database.userDao().isSerieFavorite(userId, serieId) > 0) {
                database.userDao().deleteFavoriteSerie(ref);
            } else {
                database.userDao().insertFavoriteSerie(ref);
            }
            callback.onSuccess();
        }).start();
    }

    @Override
    public void isSerieFavorite(int userId, int serieId, FavoriteCheckCallback callback) {
        new Thread(() -> {
            boolean isFavorite = database.userDao().isSerieFavorite(userId, serieId) > 0;
            callback.onFavoriteChecked(isFavorite);
        }).start();
    }

    private static class CatalogData {
        List<MovieEntity> movies;
        List<SerieEntity> series;
    }
}
