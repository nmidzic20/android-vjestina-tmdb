package endava.codebase.android.movieapp.ui.home.mapper

import endava.codebase.android.movieapp.R
import endava.codebase.android.movieapp.model.Movie
import endava.codebase.android.movieapp.model.MovieCategory
import endava.codebase.android.movieapp.ui.component.MovieCardViewState
import endava.codebase.android.movieapp.ui.component.MovieCategoryLabelTextViewState
import endava.codebase.android.movieapp.ui.component.MovieCategoryLabelViewState
import endava.codebase.android.movieapp.ui.home.HomeMovieCategoryViewState
import endava.codebase.android.movieapp.ui.home.HomeMovieViewState

class HomeScreenMapperImpl : HomeScreenMapper {
    override fun toHomeMovieCategoryViewState(
        movieCategories: List<MovieCategory>,
        selectedMovieCategory: MovieCategory,
        movies: List<Movie>
    ): HomeMovieCategoryViewState {

        val _movieCategories: List<MovieCategoryLabelViewState> = movieCategories.map { movieCategory ->
            MovieCategoryLabelViewState(
                itemId = movieCategory.ordinal,
                isSelected = selectedMovieCategory.ordinal == movieCategory.ordinal,
                categoryText = when (movieCategory.ordinal) {
                    MovieCategory.POPULAR.ordinal ->
                        MovieCategoryLabelTextViewState.TextStringResource(R.string.popular)
                    MovieCategory.TOP_RATED.ordinal ->
                        MovieCategoryLabelTextViewState.TextStringResource(R.string.top_rated)
                    MovieCategory.NOW_PLAYING.ordinal ->
                        MovieCategoryLabelTextViewState.TextStringResource(R.string.now_playing)
                    else -> {
                        MovieCategoryLabelTextViewState.TextStringResource(R.string.upcoming)
                    }
                }
            )
        }
        val _movies: List<HomeMovieViewState> = movies.map { movie ->
            HomeMovieViewState(
                id = movie.id,
                movieCardViewState = MovieCardViewState(
                    imageUrl = movie.imageUrl,
                    isFavorite = movie.isFavorite
                )
            )
        }
        return HomeMovieCategoryViewState(_movieCategories, _movies)
    }
}
