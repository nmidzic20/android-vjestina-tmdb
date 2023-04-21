package endava.codebase.android.movieapp.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import endava.codebase.android.movieapp.data.repository.MovieRepository
import endava.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val movieRepository: MovieRepository,
    favoritesScreenMapper: FavoritesMapper,
// other parameters if needed
) : ViewModel() {
    private val _favoritesViewState = MutableStateFlow<FavoritesViewState>(
        FavoritesViewState(
            emptyList()
        )
    )
    val favoritesViewState: StateFlow<FavoritesViewState> = _favoritesViewState

    init {
        viewModelScope.launch {
            movieRepository.favoriteMovies().collect { movies ->
                val favoritesViewState = favoritesScreenMapper.toFavoritesViewState(movies)
                _favoritesViewState.value = favoritesViewState
            }
        }
    }

    fun onFavoriteClick(movieId: Int) {
    }
}
