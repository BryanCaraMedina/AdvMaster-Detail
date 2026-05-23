package es.ulpgc.eite.da.advmasterdetail.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "series")
public class SerieEntity {

    @PrimaryKey
    public int id;

    @SerializedName("title")
    public String title;
    
    @SerializedName("description")
    public String description;
    
    @SerializedName("imageUrl")
    public String imageUrl;

    @Override
    public String toString() {
        return title;
    }
}
