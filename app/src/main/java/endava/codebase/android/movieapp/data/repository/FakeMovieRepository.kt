package endava.codebase.android.movieapp.data.repository

import endava.codebase.android.movieapp.mock.FavoritesDBMock
import endava.codebase.android.movieapp.mock.MoviesMock
import endava.codebase.android.movieapp.model.Movie
import endava.codebase.android.movieapp.model.MovieCategory
import endava.codebase.android.movieapp.model.MovieDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

class FakeMovieRepository(
    private val ioDispatcher: CoroutineDispatcher,
) : MovieRepository {
    private val fakeMovies = MoviesMock.getMoviesList().toMutableList()

    private val movies: Flow<List<Movie>> = FavoritesDBMock.favoriteIds
        .mapLatest { favoriteIds ->
            fakeMovies.map {
                val isFavorite = it.id in favoriteIds
                Movie(it.id, it.title, it.overview, it.imageUrl, isFavorite)
            }
        }
        .flowOn(ioDispatcher)

    override fun trendingMovies(movieCategory: MovieCategory) = movies
    override fun newReleases(movieCategory: MovieCategory) = movies

    override fun movieDetails(movieId: Int): Flow<MovieDetails> =
        FavoritesDBMock.favoriteIds
            .mapLatest { favoriteIds ->
                val movieDetails = MoviesMock.getMovieDetails(movieId)
                movieDetails
            }
            .flowOn(ioDispatcher)

    override fun favoriteMovies(): Flow<List<Movie>> = movies.map {
        it.filter { fakeMovie: Movie -> fakeMovie.isFavorite }
    }
    override suspend fun addMovieToFavorites(movieId: Int) {
        FavoritesDBMock.insert(movieId)
    }
    override suspend fun removeMovieFromFavorites(movieId: Int) {
        FavoritesDBMock.delete(movieId)

    }
    override suspend fun toggleFavorite(movieId: Int) {
        println("MOVIEID " + movieId)
        println("TO COMPARE " + FavoritesDBMock.favoriteIds.value)
        if (FavoritesDBMock.favoriteIds.value.contains(movieId)) {
            println("Removing")
            removeMovieFromFavorites(movieId)
        } else {
            println("Adding")
            addMovieToFavorites(movieId)
        }
    }
}
