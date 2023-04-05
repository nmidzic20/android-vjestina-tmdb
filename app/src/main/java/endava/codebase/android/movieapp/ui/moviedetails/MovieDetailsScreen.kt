package endava.codebase.android.movieapp.ui.moviedetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import endava.codebase.android.movieapp.R
import endava.codebase.android.movieapp.mock.MoviesMock
import endava.codebase.android.movieapp.ui.component.ActorCard
import endava.codebase.android.movieapp.ui.component.CrewItem
import endava.codebase.android.movieapp.ui.component.FavoriteButton
import endava.codebase.android.movieapp.ui.component.UserScore
import endava.codebase.android.movieapp.ui.component.UserScoreProgressBar
import endava.codebase.android.movieapp.ui.moviedetails.mapper.MovieDetailsMapper
import endava.codebase.android.movieapp.ui.moviedetails.mapper.MovieDetailsMapperImpl
import endava.codebase.android.movieapp.ui.theme.MovieAppTheme
import endava.codebase.android.movieapp.ui.theme.spacing

private val movieDetailsMapper: MovieDetailsMapper = MovieDetailsMapperImpl()

// multiple view states if required
val movieDetailsViewState = movieDetailsMapper.toMovieDetailsViewState(MoviesMock.getMovieDetails())

@Composable
fun MovieDetailsRoute(
// actions
) {
    var movieDetailsViewState by remember { mutableStateOf(movieDetailsViewState) }
    val onFavoriteClick = {
        movieDetailsViewState = MovieDetailsViewState(
            id = movieDetailsViewState.id,
            imageUrl = movieDetailsViewState.imageUrl,
            voteAverage = movieDetailsViewState.voteAverage,
            title = movieDetailsViewState.title,
            overview = movieDetailsViewState.overview,
            isFavorite = !movieDetailsViewState.isFavorite,
            crew = movieDetailsViewState.crew,
            cast = movieDetailsViewState.cast
        )
    }

    MovieDetailsScreen(
        movieDetailsViewState,
        onFavoriteClick,
    )
}

@Composable
fun MovieDetailsScreen(
    movieDetailsViewState: MovieDetailsViewState,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        CoverImage(
            movieDetailsViewState,
            onFavoriteClick,
            Modifier.height(303.dp)
        )
        Overview(
            movieDetailsViewState,
            Modifier.padding(MaterialTheme.spacing.medium)
        )
        Crew(
            movieDetailsViewState,
            Modifier.padding(MaterialTheme.spacing.medium)
        )
        TopBilledCast(
            movieDetailsViewState,
            Modifier.padding(MaterialTheme.spacing.medium)
        )
    }
}

@Composable
fun CoverImage(
    movieDetailsViewState: MovieDetailsViewState,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.BottomStart,
        modifier = modifier
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = movieDetailsViewState.imageUrl,
            contentDescription = movieDetailsViewState.imageUrl,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .drawWithCache {
                    val gradient = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = size.height / 2,
                        endY = size.height
                    )
                    onDrawWithContent {
                        drawContent()
                        drawRect(gradient, blendMode = BlendMode.Multiply)
                    }
                }
        )
        CoverImageInfo(
            movieDetailsViewState,
            onFavoriteClick,
            Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium)
        )
    }
}

@Composable
fun CoverImageInfo(
    movieDetailsViewState: MovieDetailsViewState,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val userScore = UserScore(movieDetailsViewState.voteAverage)

    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            UserScoreProgressBar(
                userScore = userScore,
                Modifier
                    .size(42.dp)
            )
            Text(
                text = stringResource(id = R.string.user_score),
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.padding(MaterialTheme.spacing.medium)
            )
        }
        Text(
            text = movieDetailsViewState.title,
            color = MaterialTheme.colors.onPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
        )
        FavoriteButton(
            isFavorite = movieDetailsViewState.isFavorite,
            onClick = onFavoriteClick,
            modifier = Modifier
                .size(MaterialTheme.spacing.large)
                .padding(MaterialTheme.spacing.extraSmall)
        )
    }
}

@Composable
fun Overview(
    movieDetailsViewState: MovieDetailsViewState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.overview),
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
        Text(
            text = movieDetailsViewState.overview,
            color = MaterialTheme.colors.onBackground,
        )
    }
}

@Composable
fun Crew(
    movieDetailsViewState: MovieDetailsViewState,
    modifier: Modifier = Modifier
) {
    val crewmanViewStateList = movieDetailsViewState.crew

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
            .height(120.dp)
    ) {
        itemsIndexed(
            items = crewmanViewStateList,
            key = { _, crewman ->
                crewman.id
            }
        ) { _, crewmanViewState ->
            CrewItem(
                crewmanViewState.crewItemViewState,
                Modifier.padding(MaterialTheme.spacing.extraSmall)
            )
        }
    }
}

@Composable
fun TopBilledCast(
    movieDetailsViewState: MovieDetailsViewState,
    modifier: Modifier = Modifier
) {
    val actorViewStateList = movieDetailsViewState.cast

    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.top_billed_cast),
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
        LazyHorizontalGrid(
            rows = GridCells.Adaptive(minSize = 215.dp),
            modifier = Modifier
                .height(300.dp)
        ) {
            itemsIndexed(
                items = actorViewStateList,
                key = { _, actor ->
                    actor.id
                }
            ) { _, actorViewState ->
                ActorCard(
                    actorViewState.actorCardViewState,
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
fun MovieDetailsScreenPreview() {

    var movieDetailsViewState by remember { mutableStateOf(movieDetailsViewState) }
    val onFavoriteClick = {
        movieDetailsViewState = MovieDetailsViewState(
            id = movieDetailsViewState.id,
            imageUrl = movieDetailsViewState.imageUrl,
            voteAverage = movieDetailsViewState.voteAverage,
            title = movieDetailsViewState.title,
            overview = movieDetailsViewState.overview,
            isFavorite = !movieDetailsViewState.isFavorite,
            crew = movieDetailsViewState.crew,
            cast = movieDetailsViewState.cast
        )
    }

    MovieAppTheme {
        MovieDetailsScreen(
            movieDetailsViewState,
            onFavoriteClick,
        )
    }
}
