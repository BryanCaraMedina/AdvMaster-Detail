package es.ulpgc.eite.da.advmasterdetail.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "user_serie_favorites", primaryKeys = {"userId", "serieId"})
public class UserSerieCrossRef {
    @NonNull
    public int userId;
    @NonNull
    public int serieId;

    public UserSerieCrossRef(int userId, int serieId) {
        this.userId = userId;
        this.serieId = serieId;
    }
}
