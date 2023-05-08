package endava.codebase.android.movieapp.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import endava.codebase.android.movieapp.data.repository.MovieRepository
import endava.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapper
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val movieRepository: MovieRepository,
    favoritesScreenMapper: FavoritesMapper,
) : ViewModel() {

    val favoritesViewState: StateFlow<FavoritesViewState> =
        movieRepository
            .favoriteMovies()
            .map { movies ->
                favoritesScreenMapper.toFavoritesViewState(movies)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(1000),
                initialValue = FavoritesViewState(emptyList()),
            )

    fun onFavoriteClick(movieId: Int) {
        viewModelScope.launch { movieRepository.toggleFavorite(movieId) }
    }
}
