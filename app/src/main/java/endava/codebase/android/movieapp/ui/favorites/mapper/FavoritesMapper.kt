package endava.codebase.android.movieapp.ui.favorites.mapper

import endava.codebase.android.movieapp.model.Movie
import endava.codebase.android.movieapp.ui.favorites.FavoritesViewState

interface FavoritesMapper {
    fun toFavoritesViewState(favoriteMovies: List<Movie>): FavoritesViewState
}
