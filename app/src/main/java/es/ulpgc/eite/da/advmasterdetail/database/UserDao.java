package es.ulpgc.eite.da.advmasterdetail.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
import es.ulpgc.eite.da.advmasterdetail.data.UserEntity;
import es.ulpgc.eite.da.advmasterdetail.data.MovieEntity;
import es.ulpgc.eite.da.advmasterdetail.data.SerieEntity;
import es.ulpgc.eite.da.advmasterdetail.data.UserMovieCrossRef;
import es.ulpgc.eite.da.advmasterdetail.data.UserSerieCrossRef;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUser(UserEntity user);

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    UserEntity login(String username, String password);

    // Favoritos Películas
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavoriteMovie(UserMovieCrossRef ref);

    @Delete
    void deleteFavoriteMovie(UserMovieCrossRef ref);

    @Query("SELECT COUNT(*) FROM user_movie_favorites WHERE userId = :userId AND movieId = :movieId")
    int isMovieFavorite(int userId, int movieId);

    @Query("SELECT m.* FROM movies m INNER JOIN user_movie_favorites f ON m.id = f.movieId WHERE f.userId = :userId")
    List<MovieEntity> getFavoriteMovies(int userId);

    // Favoritos Series
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavoriteSerie(UserSerieCrossRef ref);

    @Delete
    void deleteFavoriteSerie(UserSerieCrossRef ref);

    @Query("SELECT COUNT(*) FROM user_serie_favorites WHERE userId = :userId AND serieId = :serieId")
    int isSerieFavorite(int userId, int serieId);

    @Query("SELECT s.* FROM series s INNER JOIN user_serie_favorites f ON s.id = f.serieId WHERE f.userId = :userId")
    List<SerieEntity> getFavoriteSeries(int userId);
}
