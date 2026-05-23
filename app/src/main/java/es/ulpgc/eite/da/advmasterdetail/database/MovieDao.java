package es.ulpgc.eite.da.advmasterdetail.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
import es.ulpgc.eite.da.advmasterdetail.data.MovieEntity;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(List<MovieEntity> movies);

    @Query("SELECT * FROM movies")
    List<MovieEntity> getMovies();

    @Query("SELECT * FROM movies WHERE id = :id LIMIT 1")
    MovieEntity getMovie(int id);
}
