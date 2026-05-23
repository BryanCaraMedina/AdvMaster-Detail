package es.ulpgc.eite.da.advmasterdetail.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "user_movie_favorites", primaryKeys = {"userId", "movieId"})
public class UserMovieCrossRef {
    @NonNull
    public int userId;
    @NonNull
    public int movieId;

    public UserMovieCrossRef(int userId, int movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }
}
