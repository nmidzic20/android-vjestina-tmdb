package endava.codebase.android.movieapp.mock

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object FavoritesDBMock {
    private val _favoriteIds = MutableStateFlow(emptySet<Int>())
    val favoriteIds: StateFlow<Set<Int>> = _favoriteIds

    fun insert(movieId: Int) {
        _favoriteIds.value = _favoriteIds.value + setOf(movieId)
    }

    fun delete(movieId: Int) {
        _favoriteIds.value = _favoriteIds.value.filter { id -> id != movieId }.toSet()
    }
}
