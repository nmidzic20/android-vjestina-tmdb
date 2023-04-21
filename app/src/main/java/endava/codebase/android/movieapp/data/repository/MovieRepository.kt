package endava.codebase.android.movieapp.data.repository

import endava.codebase.android.movieapp.model.Movie
import endava.codebase.android.movieapp.model.MovieCategory
import endava.codebase.android.movieapp.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun trendingMovies(movieCategory: MovieCategory): Flow<List<Movie>>
    fun newReleases(movieCategory: MovieCategory): Flow<List<Movie>>
    fun movieDetails(movieId: Int): Flow<MovieDetails>
    fun favoriteMovies(): Flow<List<Movie>>
    suspend fun addMovieToFavorites(movieId: Int)
    suspend fun removeMovieFromFavorites(movieId: Int)
    suspend fun toggleFavorite(movieId: Int)
}
