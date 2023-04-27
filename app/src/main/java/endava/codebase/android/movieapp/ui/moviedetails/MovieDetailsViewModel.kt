package endava.codebase.android.movieapp.ui.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import endava.codebase.android.movieapp.data.repository.MovieRepository
import endava.codebase.android.movieapp.mock.MoviesMock
import endava.codebase.android.movieapp.ui.component.ActorCardViewState
import endava.codebase.android.movieapp.ui.component.CrewItemViewState
import endava.codebase.android.movieapp.ui.favorites.FavoritesViewState
import endava.codebase.android.movieapp.ui.moviedetails.mapper.MovieDetailsMapper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieId: Int,
    private val movieRepository: MovieRepository,
    movieDetailsMapper: MovieDetailsMapper,
// other parameters if needed
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
    /*private val _movieDetailsViewState = MutableStateFlow<MovieDetailsViewState>(
        initialMovieDetailsViewState
    )*/
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

    /*init {
        viewModelScope.launch {
            movieRepository.movieDetails(movieId).collect { movieDetails ->
                _movieDetailsViewState.value = movieDetailsMapper.toMovieDetailsViewState(movieDetails)
            }
        }
    }*/

    fun onFavoriteClick(movieId: Int) {
        viewModelScope.launch { movieRepository.toggleFavorite(movieId) }
    }
}
