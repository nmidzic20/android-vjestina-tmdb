package endava.codebase.android.movieapp.ui.moviedetails.mapper

import endava.codebase.android.movieapp.model.MovieDetails
import endava.codebase.android.movieapp.ui.component.ActorCardViewState
import endava.codebase.android.movieapp.ui.component.CrewItemViewState
import endava.codebase.android.movieapp.ui.moviedetails.ActorViewState
import endava.codebase.android.movieapp.ui.moviedetails.CrewmanViewState
import endava.codebase.android.movieapp.ui.moviedetails.MovieDetailsViewState

class MovieDetailsMapperImpl : MovieDetailsMapper {
    override fun toMovieDetailsViewState(movieDetails: MovieDetails): MovieDetailsViewState {

        val crewmanViewStateList: List<CrewmanViewState> = movieDetails.crew.map { crewman ->
            CrewmanViewState(
                crewman.id,
                CrewItemViewState(
                    crewman.name,
                    crewman.job
                )
            )
        }
        val castViewStateList: List<ActorViewState> = movieDetails.cast.map { actor ->
            ActorViewState(
                actor.id,
                ActorCardViewState(
                    actor.imageUrl ?: "",
                    actor.name,
                    actor.character,
                )
            )
        }

        return MovieDetailsViewState(
            id = movieDetails.movie.id,
            imageUrl = movieDetails.movie.imageUrl ?: "",
            voteAverage = movieDetails.voteAverage,
            title = movieDetails.movie.title,
            overview = movieDetails.movie.overview,
            isFavorite = movieDetails.movie.isFavorite,
            crew = crewmanViewStateList,
            cast = castViewStateList,
        )
    }
}
