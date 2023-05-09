package endava.codebase.android.movieapp.data.repository

import endava.codebase.android.movieapp.data.database.DbFavoriteMovie
import endava.codebase.android.movieapp.data.database.FavoriteMovieDAO
import endava.codebase.android.movieapp.data.network.MovieService
import endava.codebase.android.movieapp.model.Movie
import endava.codebase.android.movieapp.model.MovieCategory
import endava.codebase.android.movieapp.model.MovieDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.withContext

class MovieRepositoryImpl(
    private val movieService: MovieService,
    private val movieDao: FavoriteMovieDAO,
    private val bgDispatcher: CoroutineDispatcher
) : MovieRepository {

    private val moviesByCategory: Map<MovieCategory, Flow<List<Movie>>> = MovieCategory.values()
        .associateWith { movieCategory ->
            flow {
                val movieResponse = when (movieCategory) {

                    MovieCategory.POPULAR -> movieService.fetchPopularMovies()
                    MovieCategory.UPCOMING -> movieService.fetchUpcomingMovies()
                    MovieCategory.NOW_PLAYING -> movieService.fetchNowPlayingMovies()
                    MovieCategory.TOP_RATED -> movieService.fetchTopRatedMovies()
                }
                emit(movieResponse.movies)
            }.flatMapLatest { apiMovies ->
                movieDao.favorites()
                    .map { favoriteMovies ->
                        apiMovies.map { apiMovie ->
                            apiMovie.toMovie(isFavorite = favoriteMovies.any { it.id == apiMovie.id })
                        }
                    }
            }.shareIn(
                scope = CoroutineScope(bgDispatcher),
                started = SharingStarted.WhileSubscribed(1000L),
                replay = 1,
            )
        }

    private val favorites = movieDao.favorites().map {
        it.map { dbFavoriteMovie ->
            Movie(
                id = dbFavoriteMovie.id,
                imageUrl = dbFavoriteMovie.posterUrl,
                title = "",
                overview = "",
                isFavorite = true,
            )
        }
    }.shareIn(
        scope = CoroutineScope(bgDispatcher),
        started = SharingStarted.WhileSubscribed(1000L),
        replay = 1,
    )

    override fun movies(movieCategory: MovieCategory): Flow<List<Movie>> = moviesByCategory[movieCategory]!!

    override fun movieDetails(movieId: Int): Flow<MovieDetails> = flow {
        emit(movieService.fetchMovieDetails(movieId) to movieService.fetchMovieCredits(movieId))
    }.flatMapLatest { (apiMoviesDetails, apiMovieCredits) ->
        movieDao.favorites()
            .map { favoriteMovies ->
                apiMoviesDetails.toMovieDetails(
                    isFavorite = favoriteMovies.any { it.id == apiMoviesDetails.id },
                    crew = apiMovieCredits.crew.map { crewman -> crewman.toCrewman() },
                    cast = apiMovieCredits.cast.map { actor -> actor.toActor() },
                )
            }
    }.flowOn(bgDispatcher)

    override fun favoriteMovies(): Flow<List<Movie>> = favorites

    override suspend fun addMovieToFavorites(movieId: Int) {
        val movieDetails = movieDetails(movieId).first()
        val posterUrl = movieDetails.movie.imageUrl.orEmpty()
        movieDao.insertFavoriteMovies(DbFavoriteMovie(movieId, posterUrl))
    }

    override suspend fun removeMovieFromFavorites(movieId: Int) =
        movieDao.deleteFavoriteMovies(DbFavoriteMovie(movieId, ""))

    override suspend fun toggleFavorite(movieId: Int) {
        val favoriteMovies = favorites.first()
        withContext(bgDispatcher) {
            if (favoriteMovies.none { it.id == movieId })
                addMovieToFavorites(movieId)
            else
                removeMovieFromFavorites(movieId)
        }
    }
}
