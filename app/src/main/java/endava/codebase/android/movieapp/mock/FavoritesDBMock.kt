package endava.codebase.android.movieapp.mock

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object FavoritesDBMock {
    private val _favoriteIds = MutableStateFlow(
        MoviesMock.getMoviesList()
            .filter { movie -> movie.isFavorite }
            .map { movie -> movie.id }
            .toSet()
    )
    val favoriteIds: StateFlow<Set<Int>> = _favoriteIds

    init {
        println(_favoriteIds.value + " javni " + favoriteIds.value)
    }

    fun insert(movieId: Int) {
        println("Insert before " + _favoriteIds.value)
        _favoriteIds.value = _favoriteIds.value + setOf(movieId)
        println("Insert after " + _favoriteIds.value)
    }

    fun delete(movieId: Int) {
        println("Delete before " + _favoriteIds.value)
        _favoriteIds.value = _favoriteIds.value.filter { id -> id != movieId }.toSet()
        println("Delete after " + _favoriteIds.value)
    }
}
