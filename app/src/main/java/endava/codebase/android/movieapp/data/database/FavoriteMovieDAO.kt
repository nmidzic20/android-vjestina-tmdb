package endava.codebase.android.movieapp.data.database

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDAO {
    @Query("SELECT * FROM favorite_movie")
    fun favorites(): Flow<List<DbFavoriteMovie>>
}