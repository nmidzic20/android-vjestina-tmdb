package endava.codebase.android.movieapp.data.network

import endava.codebase.android.movieapp.data.network.model.ApiMovieDetails
import endava.codebase.android.movieapp.data.network.model.MovieCreditsResponse
import endava.codebase.android.movieapp.data.network.model.MovieResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
private const val BASE_URL = "https://api.themoviedb.org/3"
private const val API_KEY = "da8a17106e7ca013dd7b54ed7a3a10f2"

class MovieServiceImpl(private val client: HttpClient) : MovieService {
    override suspend fun fetchPopularMovies(): MovieResponse = client.get("$BASE_URL/movie/popular?api_key=$API_KEY").body()

    override suspend fun fetchTopRatedMovies(): MovieResponse = client.get("$BASE_URL/movie/top_rated?api_key=$API_KEY").body()

    override suspend fun fetchNowPlayingMovies(): MovieResponse = client.get("$BASE_URL/movie/now_playing?api_key=$API_KEY").body()

    override suspend fun fetchUpcomingMovies(): MovieResponse = client.get("$BASE_URL/movie/upcoming?api_key=$API_KEY").body()

    override suspend fun fetchMovieDetails(movieId: Int): ApiMovieDetails = client.get("$BASE_URL/movie/$movieId?api_key=$API_KEY").body()

    override suspend fun fetchMovieCredits(movieId: Int): MovieCreditsResponse = client.get("$BASE_URL/movie/$movieId/credits?api_key=$API_KEY").body()
}
