package endava.codebase.android.movieapp.ui.favorites

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import endava.codebase.android.movieapp.mock.MoviesMock
import endava.codebase.android.movieapp.model.MovieCategory
import endava.codebase.android.movieapp.ui.component.MovieCard
import endava.codebase.android.movieapp.ui.component.MovieCategoryLabel
import endava.codebase.android.movieapp.ui.component.MovieCategoryLabelViewState
import endava.codebase.android.movieapp.ui.home.HomeMovieCategoryViewState
import endava.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import endava.codebase.android.movieapp.ui.home.mapper.HomeScreenMapperImpl
import endava.codebase.android.movieapp.ui.moviedetails.movieDetailsViewState
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
    onCategoryClick: (MovieCategoryLabelViewState) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    modifier: Modifier = Modifier
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
    Column(modifier = modifier
        .verticalScroll(rememberScrollState())
    ) {
        CategoryComponent(
            homeMovieCategoryViewState = homeMovieCategoryViewStateList[0],
            onCategoryClick = onCategoryClick,
            onFavoriteClick = onFavoriteClick,
            title = "Trending movies",
            modifier = Modifier.padding(MaterialTheme.spacing.small)
        )
        CategoryComponent(
            homeMovieCategoryViewState = homeMovieCategoryViewStateList[1],
            onCategoryClick = onCategoryClick,
            onFavoriteClick = onFavoriteClick,
            title = "New releases",
            modifier = Modifier.padding(MaterialTheme.spacing.small)
        )
    }
}

@Composable
fun CategoryComponent(
    homeMovieCategoryViewState: HomeMovieCategoryViewState,
    onCategoryClick: (MovieCategoryLabelViewState) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    title: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
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
                .height(209.dp) //zbog vertical scroll
        ) {
            itemsIndexed(
                items = homeMovieCategoryViewState.movies,
                key = { _, movie ->
                    movie.id
                }
            ) { _, movie ->
                Box(Modifier
                    .width(125.dp)
                    //.size(width = 125.dp, height = 209.dp)
                    .fillMaxSize()
                    .padding(MaterialTheme.spacing.extraSmall)
                ) {
                    MovieCard(
                        movieCardViewState = movie.movieCardViewState,
                        onClick = {},
                        onFavoriteClick = {},

                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    //has to be the same variable name otherwise not working?
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
                    movies =trendingCategoryViewState.movies
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

    HomeScreen(
        homeMovieCategoryViewStateList = listOf(
            trendingCategoryViewState,
            newReleasesCategoryViewState
        ),
        onCategoryClick = onCategoryClick,
        onFavoriteClick = {}
    )

}
