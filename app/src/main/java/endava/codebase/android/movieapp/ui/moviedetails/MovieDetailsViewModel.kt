package endava.codebase.android.movieapp.ui.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import endava.codebase.android.movieapp.data.repository.MovieRepository
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

    private val initialMovieDetailsViewState = MovieDetailsViewState.EMPTY

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
