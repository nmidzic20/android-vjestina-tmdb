package endava.codebase.android.movieapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import endava.codebase.android.movieapp.data.repository.MovieRepository
import endava.codebase.android.movieapp.mock.MoviesMock
import endava.codebase.android.movieapp.model.Movie
import endava.codebase.android.movieapp.model.MovieCategory
import endava.codebase.android.movieapp.ui.component.MovieCategoryLabelViewState
import endava.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val movieRepository: MovieRepository,
    homeScreenMapper: HomeScreenMapper,
    //selectedTrendingMovieCategory: MovieCategory,
    //selectedNewMovieCategory: MovieCategory,
// other parameters if needed
) : ViewModel() {

    private val trendingMoviesCategorySelected = MutableStateFlow(MovieCategory.POPULAR)
    private val newMoviesCategorySelected = MutableStateFlow(MovieCategory.NOW_PLAYING)

    //mutable state flow -> selectedTrending i selectedNew

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

   /* private val _trendingCategoryViewState = MutableStateFlow<HomeMovieCategoryViewState>(
        initialTrendingCategoryViewState
    )*/
    val trendingCategoryViewState: StateFlow<HomeMovieCategoryViewState> =
        trendingMoviesCategorySelected
            .flatMapLatest { selectedMovieCategory ->
                movieRepository.trendingMovies(selectedMovieCategory)
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

        //_trendingCategoryViewState

   /* private val _newReleasesCategory = MutableStateFlow<HomeMovieCategoryViewState>(
        initialNewReleasesCategoryViewState
    )*/
    val newReleasesCategory: StateFlow<HomeMovieCategoryViewState> =
        newMoviesCategorySelected
        .flatMapLatest { selectedMovieCategory ->
            movieRepository.newReleases(selectedMovieCategory)
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
        //_newReleasesCategory
/*
    init {

        //flatMapLatest
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
*/
    fun onFavoriteClick(movieId: Int) {
        viewModelScope.launch { movieRepository.toggleFavorite(movieId) }
    }

    fun onCategoryClick(selectedMovieCategory: MovieCategoryLabelViewState) {
        when (selectedMovieCategory.itemId) {
            MovieCategory.POPULAR.ordinal -> {
                trendingMoviesCategorySelected.value = MovieCategory.POPULAR
                /*_trendingCategoryViewState.value = HomeMovieCategoryViewState(
                    movieCategories = _trendingCategoryViewState.value.movieCategories.map { movieCategory ->
                        MovieCategoryLabelViewState(
                            movieCategory.itemId,
                            selectedMovieCategory.itemId == movieCategory.itemId,
                            movieCategory.categoryText
                        )
                    },
                    movies = _trendingCategoryViewState.value.movies
                )*/
            }
            MovieCategory.TOP_RATED.ordinal -> {
                trendingMoviesCategorySelected.value = MovieCategory.TOP_RATED
            }
            MovieCategory.NOW_PLAYING.ordinal -> {
                newMoviesCategorySelected.value = MovieCategory.NOW_PLAYING
            }
            MovieCategory.UPCOMING.ordinal -> {
                newMoviesCategorySelected.value = MovieCategory.UPCOMING
                /*_newReleasesCategory.value = HomeMovieCategoryViewState(
                    movieCategories = _newReleasesCategory.value.movieCategories.map { movieCategory ->
                        MovieCategoryLabelViewState(
                            movieCategory.itemId,
                            selectedMovieCategory.itemId == movieCategory.itemId,
                            movieCategory.categoryText
                        )
                    },
                    movies = _newReleasesCategory.value.movies
                )*/
            }
        }
    }
}
