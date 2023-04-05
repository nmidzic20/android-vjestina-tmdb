package endava.codebase.android.movieapp.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import endava.codebase.android.movieapp.mock.MoviesMock
import endava.codebase.android.movieapp.ui.home.HomeMovieViewState
import endava.codebase.android.movieapp.ui.theme.spacing

data class MovieCardViewState(
    val imageUrl: String?,
    val isFavorite: Boolean,
)

@Composable
fun MovieCard(
    movieCardViewState: MovieCardViewState,
    onClick: () -> Unit,
    onFavoriteClick: (MovieCardViewState) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(MaterialTheme.spacing.medium),
        elevation = MaterialTheme.spacing.small,
        modifier = modifier
            .clickable { onClick() }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = movieCardViewState.imageUrl,
            contentScale = ContentScale.FillBounds,
                contentDescription = movieCardViewState.imageUrl,
                modifier = Modifier
            )
            FavoriteButton(
                isFavorite = movieCardViewState.isFavorite,
                onClick = { onFavoriteClick(movieCardViewState) },
                modifier = Modifier
                    .size(MaterialTheme.spacing.large)
                    .align(Alignment.TopStart)
                    .padding(MaterialTheme.spacing.extraSmall)
            )
        }
    }
}

@Preview
@Composable
private fun MovieCardPreview() {
    val movieDetails = MoviesMock.getMovieDetails()
    val _movieCardViewState = MovieCardViewState(movieDetails.movie.imageUrl, movieDetails.movie.isFavorite)
    val movieCardViewState = remember { mutableStateOf(_movieCardViewState) }

    val onClick = { println("Movie card clicked") }
    val onFavoriteClick = { _movieCardViewState: MovieCardViewState ->
        movieCardViewState.value = MovieCardViewState(
            _movieCardViewState.imageUrl,
            !_movieCardViewState.isFavorite
        )
    }

    MovieCard(
        movieCardViewState.value,
        onClick,
        onFavoriteClick,
        Modifier
            .size(width = 122.dp, height = 180.dp)
            .padding(MaterialTheme.spacing.extraSmall)
    )
}
