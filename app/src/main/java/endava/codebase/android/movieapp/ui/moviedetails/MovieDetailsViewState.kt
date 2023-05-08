package endava.codebase.android.movieapp.ui.moviedetails

import endava.codebase.android.movieapp.ui.component.ActorCardViewState
import endava.codebase.android.movieapp.ui.component.CrewItemViewState

data class MovieDetailsViewState(
    val id: Int,
    val imageUrl: String,
    val voteAverage: Float,
    val title: String,
    val overview: String,
    val isFavorite: Boolean,
    val crew: List<CrewmanViewState>,
    val cast: List<ActorViewState>,
) {
    companion object {
        val EMPTY = MovieDetailsViewState(
            id = 0,
            imageUrl = "",
            voteAverage = 0f,
            title = "",
            overview = "",
            isFavorite = false,
            crew = emptyList(),
            cast = emptyList()
        )
    }
}

data class CrewmanViewState(
    val id: Int,
    val crewItemViewState: CrewItemViewState,
)

data class ActorViewState(
    val id: Int,
    val actorCardViewState: ActorCardViewState,
)
