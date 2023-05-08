package endava.codebase.android.movieapp.ui.favorites

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import endava.codebase.android.movieapp.R
import endava.codebase.android.movieapp.ui.component.MovieCard
import endava.codebase.android.movieapp.ui.theme.spacing

@Composable
fun FavoritesRoute(
    onNavigateToMovieDetails: (Int) -> Unit,
    viewModel: FavoritesViewModel
) {
    val favoritesViewState: FavoritesViewState by viewModel.favoritesViewState.collectAsState()

    FavoritesScreen(
        favoritesViewState = favoritesViewState,
        onMovieCardClick = onNavigateToMovieDetails,
        onFavoriteClick = viewModel::onFavoriteClick,
    )
}

@Composable
fun FavoritesScreen(
    favoritesViewState: FavoritesViewState,
    onMovieCardClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val favoriteMovies = favoritesViewState.favoritesMovieViewStateList.toMutableList()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        modifier = modifier
    ) {
        item(
            span = {
                GridItemSpan(maxCurrentLineSpan)
            }
        ) {
            Text(
                text = stringResource(id = R.string.favorites),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(MaterialTheme.spacing.small)
            )
        }

        itemsIndexed(
            items = favoriteMovies,
            key = { _, movie ->
                movie.id
            }
        ) { index, movie ->
            MovieCard(
                movieCardViewState = favoriteMovies[index].movieCardViewState,
                onClick = { onMovieCardClick(movie.id) },
                onFavoriteClick = { onFavoriteClick(movie.id) },
                modifier = Modifier
                    .height(179.dp)
                    .padding(MaterialTheme.spacing.small)
            )
        }
    }
}
