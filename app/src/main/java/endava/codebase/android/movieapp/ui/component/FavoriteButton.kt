package endava.codebase.android.movieapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import endava.codebase.android.movieapp.R
import endava.codebase.android.movieapp.ui.theme.Gray700

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val resourceId = if (isFavorite)
        painterResource(R.drawable.icon_heart_filled)
    else
        painterResource(R.drawable.icon_heart_empty)
    Image(
        painter = resourceId,
        contentDescription = "Heart icon",
        modifier = modifier
            .fillMaxSize()
            .clip(CircleShape)
            .clickable { onClick() }
            .background(Gray700)
            .padding(7.dp)
    )
}

@Preview
@Composable
private fun FavoriteButtonPreview() {
    val isFavorite = remember { mutableStateOf(false) }
    val onClick = { isFavorite.value = !isFavorite.value }

    FavoriteButton(isFavorite.value, onClick, Modifier.size(27.dp))
}
