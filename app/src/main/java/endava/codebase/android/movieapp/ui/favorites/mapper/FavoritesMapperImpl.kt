package endava.codebase.android.movieapp.ui.favorites.mapper

import endava.codebase.android.movieapp.model.Movie
import endava.codebase.android.movieapp.ui.component.MovieCardViewState
import endava.codebase.android.movieapp.ui.favorites.FavoritesMovieViewState
import endava.codebase.android.movieapp.ui.favorites.FavoritesViewState

class FavoritesMapperImpl : FavoritesMapper {
    override fun toFavoritesViewState(favoriteMovies: List<Movie>): FavoritesViewState {

        val favoritesMovieViewState: List<FavoritesMovieViewState> = favoriteMovies.map { movie ->
            FavoritesMovieViewState(
                movie.id,
                MovieCardViewState(
                    movie.imageUrl,
                    movie.isFavorite
                )
            )
        }
        return FavoritesViewState(favoritesMovieViewState)
    }
}
