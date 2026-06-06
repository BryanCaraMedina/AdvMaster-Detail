package es.ulpgc.eite.da.advmasterdetail.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "series")
public class SerieEntity {
    @PrimaryKey
    public int id;

    @SerializedName("title")
    @ColumnInfo(name = "title")
    public String title;

    @SerializedName("description")
    @ColumnInfo(name = "description")
    public String description;

    @SerializedName("imageUrl")
    @ColumnInfo(name = "imageUrl")  // ← ESTO es lo que falta
    public String imageUrl;

    @Override
    public String toString() {
        return title;
    }
}
