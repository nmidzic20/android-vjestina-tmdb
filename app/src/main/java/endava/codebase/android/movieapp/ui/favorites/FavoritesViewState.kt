package endava.codebase.android.movieapp.ui.favorites

import endava.codebase.android.movieapp.ui.component.MovieCardViewState

data class FavoritesMovieViewState(
    val id: Int,
    val movieCardViewState: MovieCardViewState
)

data class FavoritesViewState(val favoritesMovieViewStateList: List<FavoritesMovieViewState>)
