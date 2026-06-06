package es.ulpgc.eite.da.advmasterdetail.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import es.ulpgc.eite.da.advmasterdetail.data.MovieEntity;
import es.ulpgc.eite.da.advmasterdetail.data.SerieEntity;
import es.ulpgc.eite.da.advmasterdetail.data.UserEntity;
import es.ulpgc.eite.da.advmasterdetail.data.UserMovieCrossRef;
import es.ulpgc.eite.da.advmasterdetail.data.UserSerieCrossRef;

@Database(entities = {
    MovieEntity.class, 
    SerieEntity.class, 
    UserEntity.class,
    UserMovieCrossRef.class,
    UserSerieCrossRef.class
}, version = 7000, exportSchema = false)
public abstract class CatalogDatabase extends RoomDatabase {

    private static CatalogDatabase INSTANCE;

    public abstract MovieDao movieDao();
    public abstract SerieDao serieDao();
    public abstract UserDao userDao();

    public static CatalogDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    CatalogDatabase.class,
                    "catalog_perfect_v7000.db"
            )
            .fallbackToDestructiveMigration()
            .build();
        }
        return INSTANCE;
    }

    public static void setTestInstance(CatalogDatabase database) {
        INSTANCE = database;
    }
}
