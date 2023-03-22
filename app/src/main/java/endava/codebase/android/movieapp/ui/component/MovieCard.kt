package endava.codebase.android.movieapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import endava.codebase.android.movieapp.mock.MoviesMock
import endava.codebase.android.movieapp.model.Movie
import endava.codebase.android.movieapp.ui.theme.Gray700

data class MovieCardViewState(
    val movie : Movie
)

@Composable
fun MovieCard(
    movieCardViewState: MovieCardViewState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val isFavorite = remember { mutableStateOf(movieCardViewState.movie.isFavorite) }

    Card(
        shape = RoundedCornerShape(20 .dp),
        elevation = 10 .dp,
        modifier = modifier
            .clickable { onClick() }
    ) {
        Box(
            modifier = modifier
                //.height(300.dp)
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = movieCardViewState.movie.imageUrl,
                contentDescription = movieCardViewState.movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
            )
            FavoriteButton(
                isFavorited = isFavorite.value,
                onClick = { isFavorite.value = !isFavorite.value },
                modifier = Modifier
                    .size(70.dp)
                    .align(Alignment.TopStart)
                    .padding(15.dp)
            )
        }

    }
}

@Preview
@Composable
private fun MovieCardPreview() {
    val movieDetails = MoviesMock.getMovieDetails()
    val movie = movieDetails.movie

    MovieCard(
        MovieCardViewState(movie),
        Modifier.size(width = 200 .dp, height = 300 .dp),
        { println("Movie card") }
    )
}