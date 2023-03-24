package endava.codebase.android.movieapp.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import endava.codebase.android.movieapp.mock.MoviesMock
import endava.codebase.android.movieapp.model.Movie
import endava.codebase.android.movieapp.ui.theme.spacing

data class MovieCardViewState(
    val movie: Movie,
)

@Composable
fun MovieCard(
    movieCardViewState: MovieCardViewState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isFavorite = remember { mutableStateOf(movieCardViewState.movie.isFavorite) }

    Card(
        shape = RoundedCornerShape(MaterialTheme.spacing.medium),
        elevation = MaterialTheme.spacing.small,
        modifier = modifier
            .clickable { onClick() }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = movieCardViewState.movie.imageUrl,
                contentDescription = movieCardViewState.movie.title,
                modifier = Modifier
                    .fillMaxWidth()
            )
            FavoriteButton(
                isFavorite = isFavorite.value,
                onClick = { isFavorite.value = !isFavorite.value },
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
    val movie = movieDetails.movie
    val onClick = { println("Movie card clicked") }

    MovieCard(
        MovieCardViewState(movie),
        onClick,
        Modifier
            .size(width = 122.dp, height = 180.dp)
            .padding(MaterialTheme.spacing.extraSmall)
    )
}
