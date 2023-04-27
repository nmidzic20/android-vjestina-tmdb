package endava.codebase.android.movieapp.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import endava.codebase.android.movieapp.data.repository.MovieRepository
import endava.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val movieRepository: MovieRepository,
    favoritesScreenMapper: FavoritesMapper,
// other parameters if needed
) : ViewModel() {
    /*private val _favoritesViewState = MutableStateFlow<FavoritesViewState>(
        FavoritesViewState(
            emptyList()
        )
    )*/
    //OVAKO - maknuti inite i collectove u viewmodelima
    val favoritesViewState: StateFlow<FavoritesViewState> =
        movieRepository
            .favoriteMovies()
            .map { movies ->
                favoritesScreenMapper.toFavoritesViewState(movies)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(1000), //because of config change
                initialValue = FavoritesViewState(emptyList()),
            )

    /*init {
        viewModelScope.launch {
            movieRepository.favoriteMovies().collect { movies ->
                val favoritesViewState = favoritesScreenMapper.toFavoritesViewState(movies)
                _favoritesViewState.value = favoritesViewState
            }
        }
    }*/

    fun onFavoriteClick(movieId: Int) {
        /*val favoriteMovies = _favoritesViewState.value.favoritesMovieViewStateList.toMutableList()

        favoriteMovies[movieId] = FavoritesMovieViewState(
            id = favoriteMovies[movieId].id,
            movieCardViewState = MovieCardViewState(
                imageUrl = favoriteMovies[movieId].movieCardViewState.imageUrl,
                isFavorite = !favoriteMovies[movieId].movieCardViewState.isFavorite
            )
        )*/
        viewModelScope.launch { movieRepository.toggleFavorite(movieId) }
    }
}
