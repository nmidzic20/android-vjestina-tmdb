package endava.codebase.android.movieapp.data.network

import endava.codebase.android.movieapp.data.network.model.ApiMovieDetails
import endava.codebase.android.movieapp.data.network.model.MovieCreditsResponse
import endava.codebase.android.movieapp.data.network.model.MovieResponse

interface MovieService {
    suspend fun fetchPopularMovies(): MovieResponse
    suspend fun fetchTopRatedMovies(): MovieResponse
    suspend fun fetchNowPlayingMovies(): MovieResponse
    suspend fun fetchUpcomingMovies(): MovieResponse
    suspend fun fetchMovieDetails(movieId: Int): ApiMovieDetails
    suspend fun fetchMovieCredits(movieId: Int): MovieCreditsResponse
}
