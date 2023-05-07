package endava.codebase.android.movieapp.data.network.model

import endava.codebase.android.movieapp.data.network.BASE_IMAGE_URL
import endava.codebase.android.movieapp.model.Actor
import endava.codebase.android.movieapp.model.Crewman
import endava.codebase.android.movieapp.model.Movie
import endava.codebase.android.movieapp.model.MovieDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiMovieDetails(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("release_date")
    val releaseDate: String? = null,
    @SerialName("original_language")
    val language: String,
    @SerialName("runtime")
    val runtime: Int,
) {
    fun toMovieDetails(isFavorite: Boolean, crew: List<Crewman>, cast: List<Actor>) = MovieDetails(
        movie = Movie(
            id = id,
            title = title,
            overview = overview,
            imageUrl = "$BASE_IMAGE_URL/$posterPath",
            isFavorite = isFavorite,
        ),
        voteAverage = voteAverage.toFloat(),
        releaseDate = releaseDate.orEmpty(),
        language = language,
        runtime = runtime,
        crew = crew,
        cast = cast,
    )
}