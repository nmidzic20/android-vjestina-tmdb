package endava.codebase.android.movieapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movie")
data class DbFavoriteMovie(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "poster_url") val posterUrl: String,
)
