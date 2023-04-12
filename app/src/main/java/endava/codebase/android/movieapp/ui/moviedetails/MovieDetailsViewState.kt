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
)

data class CrewmanViewState(
    val id: Int,
    val crewItemViewState: CrewItemViewState,
)

data class ActorViewState(
    val id: Int,
    val actorCardViewState: ActorCardViewState,
)
