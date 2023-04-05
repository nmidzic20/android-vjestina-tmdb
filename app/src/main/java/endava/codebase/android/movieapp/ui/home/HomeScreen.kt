package endava.codebase.android.movieapp.ui.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import endava.codebase.android.movieapp.mock.MoviesMock
import endava.codebase.android.movieapp.model.MovieCategory
import endava.codebase.android.movieapp.ui.component.MovieCard
import endava.codebase.android.movieapp.ui.home.HomeMovieCategoryViewState
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
// actions
) {
    // val _homeMovieCategoryViewState by remember { mutableStateOf(trendingCategoryViewState) }
// ...
    // HomeScreen(_homeMovieCategoryViewState)
}

@Composable
fun HomeScreen(
    homeMovieCategoryViewStateList: List<HomeMovieCategoryViewState>,
    onMovieClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
    ) {
        items(
            homeMovieCategoryViewStateList.size
        ) { index ->
            CategoryComponent(
                homeMovieCategoryViewState = homeMovieCategoryViewStateList[index],
                onMovieClick = { },
                onFavoriteClick = { },
                modifier = Modifier.padding(MaterialTheme.spacing.small)
            )
        }
    }
}

@Composable
fun CategoryComponent(
    homeMovieCategoryViewState: HomeMovieCategoryViewState,
    onMovieClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Trending movies",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(MaterialTheme.spacing.small)
        )
        LazyRow() {
            items(items = homeMovieCategoryViewState.movieCategories, itemContent = { category ->
                Text(
                    text = category.categoryText.toString(),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(MaterialTheme.spacing.small)
                )
            })
        }
        LazyHorizontalGrid(
            rows = GridCells.Adaptive(minSize = 215.dp),
            modifier = Modifier
                .height(300.dp)
        ) {
            itemsIndexed(
                items = homeMovieCategoryViewState.movies,
                key = { _, movie ->
                    movie.id
                }
            ) { index, movie ->
                MovieCard(
                    movieCardViewState = movie.movieCardViewState,
                    onClick = {},
                    onFavoriteClick = {},
                    Modifier
                        .size(width = 125.dp, height = 209.dp)
                        .padding(MaterialTheme.spacing.extraSmall)
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
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
    /*val _homeMovieCategoryViewStateList = remember { mutableStateListOf(
        listOf(trendingCategoryViewState, newReleasesCategoryViewState)
    )}

    val onClick = { selectedId: Int -> println("Movie card $selectedId clicked") }

    val onFavoriteClick = { index: Int ->
        val favoriteMovies = favoritesViewState.favoritesMovieViewStateList.toMutableList()
        favoriteMovies[index] = FavoritesMovieViewState(
            id = favoriteMovies[index].id,
            movieCardViewState = MovieCardViewState(
                imageUrl = favoriteMovies[index].movieCardViewState.imageUrl,
                isFavorite = !favoriteMovies[index].movieCardViewState.isFavorite
            )
        )
        favoritesViewState = FavoritesViewState(favoriteMovies)
    }

    MovieAppTheme {
        HomeScreen(
            _homeMovieCategoryViewStateList,
            onClick,
            onFavoriteClick,
        )
    }*/
}
