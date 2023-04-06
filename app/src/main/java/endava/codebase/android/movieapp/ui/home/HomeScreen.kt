package endava.codebase.android.movieapp.ui.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import endava.codebase.android.movieapp.ui.home.HomeMovieCategoryViewState
import endava.codebase.android.movieapp.ui.home.HomeMovieViewState
import endava.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import endava.codebase.android.movieapp.ui.home.mapper.HomeScreenMapperImpl
import endava.codebase.android.movieapp.ui.theme.spacing

private val homeScreenMapper: HomeScreenMapper = HomeScreenMapperImpl()

// multiple view states if required
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
    onNavigateToMovieDetails: (Int) -> Unit
) {
    // has to be the same variable name otherwise not working?
    var trendingCategory by remember { mutableStateOf(trendingCategoryViewState) }
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
    }

    HomeScreen(
        homeMovieCategoryViewStateList = listOf(
            trendingCategory,
            newReleasesCategory
        ),
        onCategoryClick = onCategoryClick,
        onFavoriteClick = onFavoriteClick,
        onMovieCardClick = onNavigateToMovieDetails,
    )
}

@Composable
fun HomeScreen(
    homeMovieCategoryViewStateList: List<HomeMovieCategoryViewState>,
    onCategoryClick: (MovieCategoryLabelViewState) -> Unit,
    onFavoriteClick: (MovieCardViewState) -> Unit,
    onMovieCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier,

) {
    /*LazyColumn(modifier = modifier) {
        items(
            homeMovieCategoryViewStateList.size
        ) { index ->
            //Text(text = homeMovieCategoryViewStateList[index].movieCategories[0].itemId.toString())
            CategoryComponent(
                homeMovieCategoryViewState = homeMovieCategoryViewStateList[index],
                onCategoryClick = onCategoryClick,
                onFavoriteClick = onFavoriteClick,
                title = "",
                modifier = Modifier.padding(MaterialTheme.spacing.small)
            )
        }
    }*/
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
    onCategoryClick: (MovieCategoryLabelViewState) -> Unit,
    onFavoriteClick: (MovieCardViewState) -> Unit,
    onMovieCardClick: (Int) -> Unit,
    title: String,
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
        LazyHorizontalGrid(
            rows = GridCells.Adaptive(minSize = 215.dp),
            modifier = Modifier
                .height(209.dp) // zbog vertical scroll
        ) {
            itemsIndexed(
                items = homeMovieCategoryViewState.movies,
                key = { _, movie ->
                    movie.id
                }
            ) { _, movie ->
                Box(
                    Modifier
                        .width(125.dp)
                        // .size(width = 125.dp, height = 209.dp)
                        .fillMaxSize()
                        .padding(MaterialTheme.spacing.extraSmall)
                ) {
                    MovieCard(
                        movieCardViewState = movie.movieCardViewState,
                        onClick = { onMovieCardClick(movie.id) },
                        onFavoriteClick = onFavoriteClick,

                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    // has to be the same variable name otherwise not working?
    var trendingCategoryViewState by remember { mutableStateOf(trendingCategoryViewState) }
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

        /*if (trendingCategoryViewState.movies.any { it.id == selectedMovie.id })
        {
            val movies = trendingCategoryViewState.movies.toMutableList()

            movies[selectedMovie.id] = HomeMovieViewState(
                id = movies[selectedMovie.id].id,
                movieCardViewState = MovieCardViewState(
                    imageUrl = movies[selectedMovie.id].movieCardViewState.imageUrl,
                    isFavorite = !movies[selectedMovie.id].movieCardViewState.isFavorite
                )
            )
            trendingCategoryViewState = HomeMovieCategoryViewState(
                trendingCategoryViewState.movieCategories,
                movies
            )
        }
        if (newReleasesCategoryViewState.movies.any { it.id == selectedMovie.id })
        {
            val movies = newReleasesCategoryViewState.movies.toMutableList()

            movies[selectedMovie.id] = HomeMovieViewState(
                id = movies[selectedMovie.id].id,
                movieCardViewState = MovieCardViewState(
                    imageUrl = movies[selectedMovie.id].movieCardViewState.imageUrl,
                    isFavorite = !movies[selectedMovie.id].movieCardViewState.isFavorite
                )
            )
            newReleasesCategoryViewState = HomeMovieCategoryViewState(
                newReleasesCategoryViewState.movieCategories,
                movies
            )
        }*/
    }

    HomeScreen(
        homeMovieCategoryViewStateList = listOf(
            trendingCategoryViewState,
            newReleasesCategoryViewState
        ),
        onCategoryClick = onCategoryClick,
        onFavoriteClick = onFavoriteClick,
        onMovieCardClick = { movieId -> println("Clicked movie card with movieId $movieId") }
    )
}
