package endava.codebase.android.movieapp.mock

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

object FavoritesDBMock {
    private val _favoriteIds = MutableStateFlow(
        MoviesMock.getMoviesList()
            .filter { movie -> movie.isFavorite }
            .map { movie -> movie.id }
            .toSet()
    )
    val favoriteIds: StateFlow<Set<Int>> = _favoriteIds

    fun insert(movieId: Int) {
        _favoriteIds.update { it + movieId }
    }

    fun delete(movieId: Int) {
        _favoriteIds.update { it - movieId }
    }
}
