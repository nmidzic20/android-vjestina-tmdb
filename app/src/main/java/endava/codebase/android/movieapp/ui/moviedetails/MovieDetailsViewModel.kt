package endava.codebase.android.movieapp.ui.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import endava.codebase.android.movieapp.data.repository.MovieRepository
import endava.codebase.android.movieapp.mock.MoviesMock
import endava.codebase.android.movieapp.ui.component.ActorCardViewState
import endava.codebase.android.movieapp.ui.component.CrewItemViewState
import endava.codebase.android.movieapp.ui.moviedetails.mapper.MovieDetailsMapper
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieId: Int,
    private val movieRepository: MovieRepository,
    movieDetailsMapper: MovieDetailsMapper,
) : ViewModel() {

    private val movieDetails = MoviesMock.getMovieDetails()
    private val initialMovieDetailsViewState = MovieDetailsViewState(
        id = movieDetails.movie.id,
        imageUrl = movieDetails.movie.imageUrl.orEmpty(),
        voteAverage = movieDetails.voteAverage,
        title = movieDetails.movie.title,
        overview = movieDetails.movie.overview,
        isFavorite = movieDetails.movie.isFavorite,
        crew = movieDetails.crew.map { crewman ->
            CrewmanViewState(
                crewman.id,
                CrewItemViewState(
                    crewman.name,
                    crewman.job
                )
            )
        },
        cast = movieDetails.cast.map { actor ->
            ActorViewState(
                actor.id,
                ActorCardViewState(
                    actor.imageUrl.orEmpty(),
                    actor.name,
                    actor.character,
                )
            )
        }
    )

    val movieDetailsViewState: StateFlow<MovieDetailsViewState> =
        movieRepository
            .movieDetails(movieId)
            .map { movieDetails ->
                movieDetailsMapper.toMovieDetailsViewState(movieDetails)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(1000),
                initialValue = initialMovieDetailsViewState,
            )

    fun onFavoriteClick(movieId: Int) {
        viewModelScope.launch { movieRepository.toggleFavorite(movieId) }
    }
}
