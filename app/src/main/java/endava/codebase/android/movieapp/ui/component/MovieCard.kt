package endava.codebase.android.movieapp.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import endava.codebase.android.movieapp.ui.theme.spacing

data class MovieCardViewState(
    val imageUrl: String?,
    val isFavorite: Boolean,
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieCard(
    movieCardViewState: MovieCardViewState,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(MaterialTheme.spacing.medium),
        elevation = MaterialTheme.spacing.small,
        onClick = onClick,
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = movieCardViewState.imageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = movieCardViewState.imageUrl,
                modifier = Modifier
            )
            FavoriteButton(
                isFavorite = movieCardViewState.isFavorite,
                onClick = onFavoriteClick,
                modifier = Modifier
                    .size(MaterialTheme.spacing.large)
                    .align(Alignment.TopStart)
                    .padding(MaterialTheme.spacing.extraSmall)
            )
        }
    }
}
