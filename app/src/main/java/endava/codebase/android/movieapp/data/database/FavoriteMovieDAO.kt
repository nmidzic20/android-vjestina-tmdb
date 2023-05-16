package endava.codebase.android.movieapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDAO {
    @Query("SELECT * FROM favorite_movie")
    fun favorites(): Flow<List<DbFavoriteMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(movie: DbFavoriteMovie)

    @Query("DELETE FROM favorite_movie WHERE id = :movieId")
    suspend fun deleteFavoriteMovie(movieId: Int)
}
