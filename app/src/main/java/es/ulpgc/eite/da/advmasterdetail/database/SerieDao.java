package es.ulpgc.eite.da.advmasterdetail.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
import es.ulpgc.eite.da.advmasterdetail.data.SerieEntity;

@Dao
public interface SerieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSeries(List<SerieEntity> series);

    @Query("SELECT * FROM series")
    List<SerieEntity> getSeries();

    @Query("SELECT * FROM series WHERE id = :id LIMIT 1")
    SerieEntity getSerie(int id);
}
