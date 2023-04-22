package endava.codebase.android.movieapp.ui.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import endava.codebase.android.movieapp.R
import endava.codebase.android.movieapp.mock.MoviesMock
import endava.codebase.android.movieapp.model.MovieCategory
import endava.codebase.android.movieapp.ui.component.MovieCard
import endava.codebase.android.movieapp.ui.component.MovieCardViewState
import endava.codebase.android.movieapp.ui.component.MovieCategoryLabel
import endava.codebase.android.movieapp.ui.component.MovieCategoryLabelViewState
import endava.codebase.android.movieapp.ui.home.HomeCategoryViewModel
import endava.codebase.android.movieapp.ui.home.HomeMovieCategoryViewState
import endava.codebase.android.movieapp.ui.home.HomeMovieViewState
import endava.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import endava.codebase.android.movieapp.ui.home.mapper.HomeScreenMapperImpl
import endava.codebase.android.movieapp.ui.theme.spacing

private val homeScreenMapper: HomeScreenMapper = HomeScreenMapperImpl()

val trendingCategoryViewState = homeScreenMapper.toHomeMovieCategoryViewState(
    movieCategories = listOf(
        MovieCategory.POPULAR,
        MovieCategory.TOP_RATED,
    ),
    selectedMovieCategory = MovieCategory.POPULAR,
    movies = MoviesMock.getMoviesList()
)
val newReleasesCategoryViewState = homeScreenMapper.toHomeMovieCategoryViewState(
    movieCategories = listOf(
        MovieCategory.NOW_PLAYING,
        MovieCategory.UPCOMING,
    ),
    selectedMovieCategory = MovieCategory.NOW_PLAYING,
    movies = MoviesMock.getMoviesList()
)

@Composable
fun HomeRoute(
    onNavigateToMovieDetails: (Int) -> Unit,
    viewModel: HomeCategoryViewModel
) {
    /*var trendingCategory by remember { mutableStateOf(trendingCategoryViewState) }
    var newReleasesCategory by remember { mutableStateOf(newReleasesCategoryViewState) }

    val onCategoryClick = { selectedMovieCategory: MovieCategoryLabelViewState ->
        when (selectedMovieCategory.itemId) {
            MovieCategory.POPULAR.ordinal, MovieCategory.TOP_RATED.ordinal -> {
                trendingCategory = HomeMovieCategoryViewState(
                    movieCategories = trendingCategoryViewState.movieCategories.map { movieCategory ->
                        MovieCategoryLabelViewState(
                            movieCategory.itemId,
                            selectedMovieCategory.itemId == movieCategory.itemId,
                            movieCategory.categoryText
                        )
                    },
                    movies = trendingCategoryViewState.movies
                )
            }
            MovieCategory.NOW_PLAYING.ordinal, MovieCategory.UPCOMING.ordinal -> {
                newReleasesCategory = HomeMovieCategoryViewState(
                    movieCategories = newReleasesCategoryViewState.movieCategories.map { movieCategory ->
                        MovieCategoryLabelViewState(
                            movieCategory.itemId,
                            selectedMovieCategory.itemId == movieCategory.itemId,
                            movieCategory.categoryText
                        )
                    },
                    movies = newReleasesCategoryViewState.movies
                )
            }
        }
    }

    val onFavoriteClick = { selectedMovie: MovieCardViewState ->

        val trendingMovies = trendingCategoryViewState.movies.map {
            HomeMovieViewState(
                it.id,
                MovieCardViewState(
                    it.movieCardViewState.imageUrl,
                    if (selectedMovie.imageUrl == it.movieCardViewState.imageUrl)
                        !it.movieCardViewState.isFavorite
                    else
                        it.movieCardViewState.isFavorite
                )
            )
        }
        val newReleasesMovies = newReleasesCategoryViewState.movies.map {
            HomeMovieViewState(
                it.id,
                MovieCardViewState(
                    it.movieCardViewState.imageUrl,
                    if (selectedMovie.imageUrl == it.movieCardViewState.imageUrl)
                        !it.movieCardViewState.isFavorite
                    else
                        it.movieCardViewState.isFavorite
                )
            )
        }

        trendingCategory = HomeMovieCategoryViewState(
            movieCategories = trendingCategoryViewState.movieCategories,
            movies = trendingMovies
        )
        newReleasesCategory = HomeMovieCategoryViewState(
            movieCategories = newReleasesCategoryViewState.movieCategories,
            movies = newReleasesMovies
        )
    }*/
    val trendingCategoryViewState: HomeMovieCategoryViewState by viewModel.trendingCategoryViewState.collectAsState()
    val newReleasesCategoryViewState: HomeMovieCategoryViewState by viewModel.newReleasesCategory.collectAsState()

    HomeScreen(
        homeMovieCategoryViewStateList = listOf(
            trendingCategoryViewState,
            newReleasesCategoryViewState
        ),
        onCategoryClick = viewModel::onCategoryClick,
        onFavoriteClick = viewModel::onFavoriteClick,
        onMovieCardClick = onNavigateToMovieDetails,
    )
}

@Composable
fun HomeScreen(
    homeMovieCategoryViewStateList: List<HomeMovieCategoryViewState>,
    onCategoryClick: (MovieCategoryLabelViewState) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    onMovieCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        CategoryComponent(
            homeMovieCategoryViewState = homeMovieCategoryViewStateList[0],
            onCategoryClick = onCategoryClick,
            onFavoriteClick = onFavoriteClick,
            onMovieCardClick = onMovieCardClick,
            title = stringResource(R.string.trending_movies),
            modifier = Modifier.padding(MaterialTheme.spacing.small)
        )
        CategoryComponent(
            homeMovieCategoryViewState = homeMovieCategoryViewStateList[1],
            onCategoryClick = onCategoryClick,
            onFavoriteClick = onFavoriteClick,
            onMovieCardClick = onMovieCardClick,
            title = stringResource(R.string.new_releases),
            modifier = Modifier.padding(MaterialTheme.spacing.small)
        )
    }
}

@Composable
fun CategoryComponent(
    homeMovieCategoryViewState: HomeMovieCategoryViewState,
    title: String,
    onCategoryClick: (MovieCategoryLabelViewState) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    onMovieCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            color = MaterialTheme.colors.primary,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(MaterialTheme.spacing.small)
        )
        LazyRow() {
            items(items = homeMovieCategoryViewState.movieCategories, itemContent = { category ->
                MovieCategoryLabel(
                    movieCategoryLabelViewState = category,
                    onClick = onCategoryClick,
                    modifier = Modifier
                        .padding(MaterialTheme.spacing.small)
                )
            })
        }
        LazyRow() {
            items(
                items = homeMovieCategoryViewState.movies,
                itemContent = { movie ->
                    Box(
                        Modifier
                            .width(125.dp)
                            .fillMaxSize()
                            .padding(MaterialTheme.spacing.extraSmall)
                    ) {
                        MovieCard(
                            movieCardViewState = movie.movieCardViewState,
                            onClick = { onMovieCardClick(movie.id) },
                            onFavoriteClick = { onFavoriteClick(movie.id) },
                            modifier = Modifier
                                .height(209.dp)
                        )
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    /*var trendingCategoryViewState by remember { mutableStateOf(trendingCategoryViewState) }
    var newReleasesCategoryViewState by remember { mutableStateOf(newReleasesCategoryViewState) }

    val onCategoryClick = { selectedMovieCategory: MovieCategoryLabelViewState ->
        when (selectedMovieCategory.itemId) {
            MovieCategory.POPULAR.ordinal, MovieCategory.TOP_RATED.ordinal -> {
                trendingCategoryViewState = HomeMovieCategoryViewState(
                    movieCategories = trendingCategoryViewState.movieCategories.map { movieCategory ->
                        MovieCategoryLabelViewState(
                            movieCategory.itemId,
                            selectedMovieCategory.itemId == movieCategory.itemId,
                            movieCategory.categoryText
                        )
                    },
                    movies = trendingCategoryViewState.movies
                )
            }
            MovieCategory.NOW_PLAYING.ordinal, MovieCategory.UPCOMING.ordinal -> {
                newReleasesCategoryViewState = HomeMovieCategoryViewState(
                    movieCategories = newReleasesCategoryViewState.movieCategories.map { movieCategory ->
                        MovieCategoryLabelViewState(
                            movieCategory.itemId,
                            selectedMovieCategory.itemId == movieCategory.itemId,
                            movieCategory.categoryText
                        )
                    },
                    movies = newReleasesCategoryViewState.movies
                )
            }
        }
    }

    val onFavoriteClick = { selectedMovie: MovieCardViewState ->

        val trendingMovies = trendingCategoryViewState.movies.map {
            HomeMovieViewState(
                it.id,
                MovieCardViewState(
                    it.movieCardViewState.imageUrl,
                    if (selectedMovie.imageUrl == it.movieCardViewState.imageUrl)
                        !it.movieCardViewState.isFavorite
                    else
                        it.movieCardViewState.isFavorite
                )
            )
        }
        val newReleasesMovies = newReleasesCategoryViewState.movies.map {
            HomeMovieViewState(
                it.id,
                MovieCardViewState(
                    it.movieCardViewState.imageUrl,
                    if (selectedMovie.imageUrl == it.movieCardViewState.imageUrl)
                        !it.movieCardViewState.isFavorite
                    else
                        it.movieCardViewState.isFavorite
                )
            )
        }

        trendingCategoryViewState = HomeMovieCategoryViewState(
            movieCategories = trendingCategoryViewState.movieCategories,
            movies = trendingMovies
        )
        newReleasesCategoryViewState = HomeMovieCategoryViewState(
            movieCategories = newReleasesCategoryViewState.movieCategories,
            movies = newReleasesMovies
        )
    }

    HomeScreen(
        homeMovieCategoryViewStateList = listOf(
            trendingCategoryViewState,
            newReleasesCategoryViewState
        ),
        onCategoryClick = onCategoryClick,
        onFavoriteClick = onFavoriteClick,
        onMovieCardClick = { movieId -> println("Clicked movie card with movieId $movieId") }
    )*/
}
