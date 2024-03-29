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
                categoryText = MovieCategoryLabelTextViewState.TextStringResource(getStringResource(movieCategory))
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

    private fun getStringResource(movieCategory: MovieCategory): Int = when (movieCategory) {
        MovieCategory.POPULAR ->
            R.string.popular
        MovieCategory.TOP_RATED ->
            R.string.top_rated
        MovieCategory.NOW_PLAYING ->
            R.string.now_playing
        MovieCategory.UPCOMING -> {
            R.string.upcoming
        }
    }
}
