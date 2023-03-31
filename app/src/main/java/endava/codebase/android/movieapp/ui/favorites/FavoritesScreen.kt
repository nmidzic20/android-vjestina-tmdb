package endava.codebase.android.movieapp.ui.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import endava.codebase.android.movieapp.R
import endava.codebase.android.movieapp.mock.MoviesMock
import endava.codebase.android.movieapp.ui.component.MovieCard
import endava.codebase.android.movieapp.ui.component.MovieCardViewState
import endava.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapper
import endava.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapperImpl
import endava.codebase.android.movieapp.ui.theme.MovieAppTheme
import endava.codebase.android.movieapp.ui.theme.spacing

private val favoritesMapper: FavoritesMapper = FavoritesMapperImpl()

// multiple view states if required
val favoritesViewState = favoritesMapper.toFavoritesViewState(MoviesMock.getMoviesList())

@Composable
fun FavoritesRoute(
// actions
) {
    // val _favoritesViewState by remember { mutableStateOf(favoritesViewState) }
// ...
    // FavoritesScreen(_favoritesViewState)
}

@Composable
fun FavoritesScreen(
    favoritesViewState: FavoritesViewState,
    onClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val favoriteMovies = favoritesViewState.favoritesMovieViewStateList.toMutableList()

    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.favorites),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(MaterialTheme.spacing.small)
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp)
        ) {
            itemsIndexed(favoriteMovies) { index, favoriteMovie ->
                MovieCard(
                    movieCardViewState = favoriteMovies[index].movieCardViewState,
                    onClick = { onClick(favoriteMovie.id) },
                    onFavoriteClick = { onFavoriteClick(index) },
                    modifier = Modifier.padding(MaterialTheme.spacing.small)
                )
            }
        }
    }
}

@Preview
@Composable
fun FavoritesScreenPreview() {

    val favoritesViewState = remember { mutableStateOf(favoritesViewState) }

    val onClick = { selectedId: Int -> println("Movie card $selectedId clicked") }

    val onFavoriteClick = { index: Int ->
        val favoriteMovies = favoritesViewState.value.favoritesMovieViewStateList.toMutableList()
        /*
        // if passing selectedId instead of index:
        val iterate = favoriteMovies.listIterator()
        while (iterate.hasNext()) {
            val oldValue = iterate.next()
            iterate.set(
                FavoritesMovieViewState(
                    id = oldValue.id,
                    movieCardViewState = MovieCardViewState(
                        imageUrl = oldValue.movieCardViewState.imageUrl,
                        isFavorite =
                        if (selectedId == oldValue.id)
                            !oldValue.movieCardViewState.isFavorite
                        else
                            oldValue.movieCardViewState.isFavorite
                    )
                )
            )
        }
        */
        favoriteMovies[index] = FavoritesMovieViewState(
            id = favoriteMovies[index].id,
            movieCardViewState = MovieCardViewState(
                imageUrl = favoriteMovies[index].movieCardViewState.imageUrl,
                isFavorite = !favoriteMovies[index].movieCardViewState.isFavorite
            )
        )
        favoritesViewState.value = FavoritesViewState(favoriteMovies)
    }

    MovieAppTheme {
        FavoritesScreen(
            favoritesViewState.value,
            onClick,
            onFavoriteClick,
        )
    }
}
