package endava.codebase.android.movieapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import endava.codebase.android.movieapp.R
import endava.codebase.android.movieapp.ui.theme.Gray700

@Composable
fun FavoriteButtonScreen()
{
    val isFavorited = remember { mutableStateOf(false) }
    val onClick = { isFavorited.value = !isFavorited.value }

    FavoriteButton(isFavorited.value, onClick, Modifier)
}

@Composable
fun FavoriteButton(isFavorited : Boolean, onClick : () -> Unit, modifier: Modifier = Modifier)
{
    Surface(
        shape = CircleShape,
        color = Gray700,
        modifier = modifier
            .clickable { onClick() }
    ) {
        Image(
            painter =
                if (isFavorited)
                    painterResource(R.drawable.icon_heart_filled)
                else
                    painterResource(R.drawable.icon_heart_empty),
            contentDescription = "Heart icon",
        )
    }

}

@Preview
@Composable
private fun FavoriteButtonPreview()
{
    FavoriteButtonScreen()
}