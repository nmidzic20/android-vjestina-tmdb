package endava.codebase.android.movieapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import endava.codebase.android.movieapp.data.repository.MovieRepository
import endava.codebase.android.movieapp.mock.MoviesMock
import endava.codebase.android.movieapp.model.MovieCategory
import endava.codebase.android.movieapp.ui.component.ActorCardViewState
import endava.codebase.android.movieapp.ui.component.CrewItemViewState
import endava.codebase.android.movieapp.ui.component.MovieCategoryLabelViewState
import endava.codebase.android.movieapp.ui.favorites.newReleasesCategoryViewState
import endava.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import endava.codebase.android.movieapp.ui.moviedetails.ActorViewState
import endava.codebase.android.movieapp.ui.moviedetails.CrewmanViewState
import endava.codebase.android.movieapp.ui.moviedetails.MovieDetailsViewState
import endava.codebase.android.movieapp.ui.moviedetails.mapper.MovieDetailsMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeCategoryViewModel(
    private val movieRepository: MovieRepository,
    homeScreenMapper: HomeScreenMapper,
    selectedTrendingMovieCategory: MovieCategory,
    selectedNewMovieCategory: MovieCategory,
// other parameters if needed
) : ViewModel() {
    private val initialTrendingCategoryViewState = homeScreenMapper.toHomeMovieCategoryViewState(
        movieCategories = listOf(
            MovieCategory.POPULAR,
            MovieCategory.TOP_RATED,
        ),
        selectedMovieCategory = MovieCategory.POPULAR,
        movies = MoviesMock.getMoviesList()
    )
    private val initialNewReleasesCategoryViewState = homeScreenMapper.toHomeMovieCategoryViewState(
        movieCategories = listOf(
            MovieCategory.NOW_PLAYING,
            MovieCategory.UPCOMING,
        ),
        selectedMovieCategory = MovieCategory.NOW_PLAYING,
        movies = MoviesMock.getMoviesList()
    )

    private val _trendingCategoryViewState = MutableStateFlow<HomeMovieCategoryViewState>(
        initialTrendingCategoryViewState
    )
    val trendingCategoryViewState: StateFlow<HomeMovieCategoryViewState> = _trendingCategoryViewState

    private val _newReleasesCategory = MutableStateFlow<HomeMovieCategoryViewState>(
        initialNewReleasesCategoryViewState
    )
    val newReleasesCategory: StateFlow<HomeMovieCategoryViewState> = _newReleasesCategory

    init {
        viewModelScope.launch {
            movieRepository.trendingMovies(selectedTrendingMovieCategory).collect { movies ->
                _trendingCategoryViewState.value = homeScreenMapper.toHomeMovieCategoryViewState(
                    movieCategories = listOf(
                        MovieCategory.POPULAR,
                        MovieCategory.TOP_RATED,
                    ),
                    selectedTrendingMovieCategory,
                    movies
                )
            }
        }
        viewModelScope.launch {
            movieRepository.newReleases(selectedNewMovieCategory).collect { movies ->
                _newReleasesCategory.value = homeScreenMapper.toHomeMovieCategoryViewState(
                    movieCategories = listOf(
                        MovieCategory.NOW_PLAYING,
                        MovieCategory.UPCOMING,
                    ),
                    selectedNewMovieCategory,
                    movies
                )
            }
        }
    }

    fun onFavoriteClick(movieId: Int) {
        viewModelScope.launch { movieRepository.toggleFavorite(movieId) }
    }

    fun onCategoryClick(selectedMovieCategory: MovieCategoryLabelViewState) {
        when (selectedMovieCategory.itemId) {
            MovieCategory.POPULAR.ordinal, MovieCategory.TOP_RATED.ordinal -> {
                _trendingCategoryViewState.value = HomeMovieCategoryViewState(
                    movieCategories = _trendingCategoryViewState.value.movieCategories.map { movieCategory ->
                        MovieCategoryLabelViewState(
                            movieCategory.itemId,
                            selectedMovieCategory.itemId == movieCategory.itemId,
                            movieCategory.categoryText
                        )
                    },
                    movies = _trendingCategoryViewState.value.movies
                )
            }
            MovieCategory.NOW_PLAYING.ordinal, MovieCategory.UPCOMING.ordinal -> {
                _newReleasesCategory.value = HomeMovieCategoryViewState(
                    movieCategories = _newReleasesCategory.value.movieCategories.map { movieCategory ->
                        MovieCategoryLabelViewState(
                            movieCategory.itemId,
                            selectedMovieCategory.itemId == movieCategory.itemId,
                            movieCategory.categoryText
                        )
                    },
                    movies = _newReleasesCategory.value.movies
                )
            }
        }

    }

}