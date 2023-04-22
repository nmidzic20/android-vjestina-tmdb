package endava.codebase.android.movieapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import endava.codebase.android.movieapp.data.repository.MovieRepository
import endava.codebase.android.movieapp.mock.MoviesMock
import endava.codebase.android.movieapp.ui.component.ActorCardViewState
import endava.codebase.android.movieapp.ui.component.CrewItemViewState
import endava.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import endava.codebase.android.movieapp.ui.moviedetails.ActorViewState
import endava.codebase.android.movieapp.ui.moviedetails.CrewmanViewState
import endava.codebase.android.movieapp.ui.moviedetails.MovieDetailsViewState
import endava.codebase.android.movieapp.ui.moviedetails.mapper.MovieDetailsMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeCategoryViewModel(
    private val movieRepository: MovieRepository,
    homeScreenMapper: HomeScreenMapper,
// other parameters if needed
) : ViewModel() {
    /*private val _movieDetailsViewState = MutableStateFlow<MovieDetailsViewState>(
    )
    val movieDetailsViewState: StateFlow<MovieDetailsViewState> = _movieDetailsViewState

    init {
        viewModelScope.launch {
            movieRepository. .collect {  ->
                _movieDetailsViewState.value = homeScreenMapper.toHomeMovieCategoryViewState()
            }
        }
    }

    fun onFavoriteClick(movieId: Int) {
        viewModelScope.launch { movieRepository.toggleFavorite(movieId) }
    }*/

}