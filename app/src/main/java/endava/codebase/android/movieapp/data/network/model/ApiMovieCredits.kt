package endava.codebase.android.movieapp.data.network.model

import endava.codebase.android.movieapp.data.network.BASE_IMAGE_URL
import endava.codebase.android.movieapp.model.Actor
import endava.codebase.android.movieapp.model.Crewman
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieCreditsResponse(
    @SerialName("cast")
    val cast: List<ApiCast>,
    @SerialName("crew")
    val crew: List<ApiCrew>,
)

@Serializable
data class ApiCast(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("character")
    val character: String,
    @SerialName("profile_path")
    val profilePath: String?,
) {
    fun toActor() = Actor(
        id = id,
        name = name,
        character = character,
        imageUrl = "$BASE_IMAGE_URL/$profilePath",
    )
}

@Serializable
data class ApiCrew(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("job")
    val job: String,
) {
    fun toCrewman() = Crewman(
        id = id,
        name = name,
        job = job,
    )
}