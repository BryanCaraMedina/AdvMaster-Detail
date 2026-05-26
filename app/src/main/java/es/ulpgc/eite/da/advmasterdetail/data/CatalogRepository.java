package es.ulpgc.eite.da.advmasterdetail.data;

import android.content.Context;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import es.ulpgc.eite.da.advmasterdetail.database.CatalogDatabase;

public class CatalogRepository implements RepositoryContract {

    private static CatalogRepository INSTANCE;
    private final CatalogDatabase database;
    private final Context context;
    private Executor executor;

    private CatalogRepository(Context context) {
        this.context = context;
        database = CatalogDatabase.getInstance(context);
        this.executor = Executors.newSingleThreadExecutor();
    }

    public static RepositoryContract getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new CatalogRepository(context);
        }
        return INSTANCE;
    }

    public static void resetInstance() {
        INSTANCE = null;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    @Override
    public void getMovieList(GetMovieListCallback callback) {
        executor.execute(() -> {
            List<MovieEntity> movies = database.movieDao().getMovies();
            if (movies.isEmpty()) {
                loadInitialData();
                movies = database.movieDao().getMovies();
            }
            callback.setMovieList(movies);
        });
    }

    @Override
    public void getMovie(int id, GetMovieCallback callback) {
        executor.execute(() -> {
            MovieEntity movie = database.movieDao().getMovie(id);
            callback.setMovie(movie);
        });
    }

    @Override
    public void getSerieList(GetSerieListCallback callback) {
        executor.execute(() -> {
            List<SerieEntity> series = database.serieDao().getSeries();
            if (series.isEmpty()) {
                loadInitialData();
                series = database.serieDao().getSeries();
            }
            callback.setSerieList(series);
        });
    }

    @Override
    public void getSerie(int id, GetSerieCallback callback) {
        executor.execute(() -> {
            SerieEntity serie = database.serieDao().getSerie(id);
            callback.setSerie(serie);
        });
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
        executor.execute(() -> {
            UserEntity user = database.userDao().login(username, password);
            callback.onLoginResult(user != null, user);
        });
    }

    @Override
    public void register(UserEntity user, RegisterCallback callback) {
        executor.execute(() -> {
            database.userDao().insertUser(user);
            callback.onRegisterResult(true);
        });
    }

    @Override
    public void getFavoriteMovies(int userId, GetMovieListCallback callback) {
        executor.execute(() -> {
            List<MovieEntity> movies = database.userDao().getFavoriteMovies(userId);
            callback.setMovieList(movies);
        });
    }

    @Override
    public void toggleMovieFavorite(int userId, int movieId, ActionCallback callback) {
        executor.execute(() -> {
            UserMovieCrossRef ref = new UserMovieCrossRef(userId, movieId);
            if (database.userDao().isMovieFavorite(userId, movieId) > 0) {
                database.userDao().deleteFavoriteMovie(ref);
            } else {
                database.userDao().insertFavoriteMovie(ref);
            }
            callback.onSuccess();
        });
    }

    @Override
    public void isMovieFavorite(int userId, int movieId, FavoriteCheckCallback callback) {
        executor.execute(() -> {
            boolean isFavorite = database.userDao().isMovieFavorite(userId, movieId) > 0;
            callback.onFavoriteChecked(isFavorite);
        });
    }

    @Override
    public void getFavoriteSeries(int userId, GetSerieListCallback callback) {
        executor.execute(() -> {
            List<SerieEntity> series = database.userDao().getFavoriteSeries(userId);
            callback.setSerieList(series);
        });
    }

    @Override
    public void toggleSerieFavorite(int userId, int serieId, ActionCallback callback) {
        executor.execute(() -> {
            UserSerieCrossRef ref = new UserSerieCrossRef(userId, serieId);
            if (database.userDao().isSerieFavorite(userId, serieId) > 0) {
                database.userDao().deleteFavoriteSerie(ref);
            } else {
                database.userDao().insertFavoriteSerie(ref);
            }
            callback.onSuccess();
        });
    }

    @Override
    public void isSerieFavorite(int userId, int serieId, FavoriteCheckCallback callback) {
        executor.execute(() -> {
            boolean isFavorite = database.userDao().isSerieFavorite(userId, serieId) > 0;
            callback.onFavoriteChecked(isFavorite);
        });
    }

    private static class CatalogData {
        List<MovieEntity> movies;
        List<SerieEntity> series;
    }
}
