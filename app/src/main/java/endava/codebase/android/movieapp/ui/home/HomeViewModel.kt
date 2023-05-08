package endava.codebase.android.movieapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import endava.codebase.android.movieapp.data.repository.MovieRepository
import endava.codebase.android.movieapp.model.MovieCategory
import endava.codebase.android.movieapp.ui.component.MovieCategoryLabelViewState
import endava.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val movieRepository: MovieRepository,
    homeScreenMapper: HomeScreenMapper,
) : ViewModel() {

    private val trendingMoviesCategorySelected = MutableStateFlow(MovieCategory.POPULAR)
    private val newMoviesCategorySelected = MutableStateFlow(MovieCategory.NOW_PLAYING)

    private val initialTrendingCategoryViewState = homeScreenMapper.toHomeMovieCategoryViewState(
        movieCategories = listOf(
            MovieCategory.POPULAR,
            MovieCategory.TOP_RATED,
        ),
        selectedMovieCategory = MovieCategory.POPULAR,
        movies = emptyList()
    )
    private val initialNewReleasesCategoryViewState = homeScreenMapper.toHomeMovieCategoryViewState(
        movieCategories = listOf(
            MovieCategory.NOW_PLAYING,
            MovieCategory.UPCOMING,
        ),
        selectedMovieCategory = MovieCategory.NOW_PLAYING,
        movies = emptyList()
    )

    val trendingCategoryViewState: StateFlow<HomeMovieCategoryViewState> =
        trendingMoviesCategorySelected
            .flatMapLatest { selectedMovieCategory ->
                movieRepository.movies(selectedMovieCategory)
                    .map { movies ->
                        homeScreenMapper.toHomeMovieCategoryViewState(
                            movieCategories = listOf(
                                MovieCategory.POPULAR,
                                MovieCategory.TOP_RATED,
                            ),
                            selectedMovieCategory,
                            movies
                        )
                    }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(1000),
                initialValue = initialTrendingCategoryViewState,
            )

    val newReleasesCategory: StateFlow<HomeMovieCategoryViewState> =
        newMoviesCategorySelected
            .flatMapLatest { selectedMovieCategory ->
                movieRepository.movies(selectedMovieCategory)
                    .map { movies ->
                        homeScreenMapper.toHomeMovieCategoryViewState(
                            movieCategories = listOf(
                                MovieCategory.NOW_PLAYING,
                                MovieCategory.UPCOMING,
                            ),
                            selectedMovieCategory,
                            movies
                        )
                    }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(1000),
                initialValue = initialNewReleasesCategoryViewState,
            )

    fun onFavoriteClick(movieId: Int) {
        viewModelScope.launch { movieRepository.toggleFavorite(movieId) }
    }

    fun onCategoryClick(selectedMovieCategory: MovieCategoryLabelViewState) {
        when (selectedMovieCategory.itemId) {
            MovieCategory.POPULAR.ordinal -> {
                trendingMoviesCategorySelected.value = MovieCategory.POPULAR
            }
            MovieCategory.TOP_RATED.ordinal -> {
                trendingMoviesCategorySelected.value = MovieCategory.TOP_RATED
            }
            MovieCategory.NOW_PLAYING.ordinal -> {
                newMoviesCategorySelected.value = MovieCategory.NOW_PLAYING
            }
            MovieCategory.UPCOMING.ordinal -> {
                newMoviesCategorySelected.value = MovieCategory.UPCOMING
            }
        }
    }
}
