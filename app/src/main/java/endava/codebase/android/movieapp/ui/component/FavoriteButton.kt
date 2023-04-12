package endava.codebase.android.movieapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import endava.codebase.android.movieapp.R
import endava.codebase.android.movieapp.ui.theme.Gray700
import endava.codebase.android.movieapp.ui.theme.spacing

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val resourceId = if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_outlined

    Image(
        painter = painterResource(resourceId),
        contentDescription = stringResource(id = R.string.favorites),
        modifier = modifier
            .fillMaxSize()
            .clip(CircleShape)
            .clickable { onClick() }
            .background(Gray700)
            .padding(MaterialTheme.spacing.small)
    )
}

@Preview
@Composable
private fun FavoriteButtonPreview() {
    var isFavorite by remember { mutableStateOf(false) }
    val onClick = { isFavorite = !isFavorite }

    FavoriteButton(isFavorite, onClick, Modifier.size(27.dp))
}
