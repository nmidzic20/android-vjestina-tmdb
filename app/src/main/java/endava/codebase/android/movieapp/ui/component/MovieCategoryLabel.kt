package endava.codebase.android.movieapp.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import endava.codebase.android.movieapp.R

data class MovieCategoryLabelViewState(
    val itemId: Int,
    val isSelected: Boolean,
    val categoryText: MovieCategoryLabelTextViewState
)

sealed class MovieCategoryLabelTextViewState {
    data class TextString (val text: String) : MovieCategoryLabelTextViewState()
    data class TextStringResource (@StringRes val textRes: Int) : MovieCategoryLabelTextViewState()
}



@Composable
fun MovieCategoryLabel(
    movieCategoryLabelViewState: MovieCategoryLabelViewState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    selectedIndex: Int
) {

    val textContent : String = when(movieCategoryLabelViewState.categoryText) {
        is MovieCategoryLabelTextViewState.TextString -> movieCategoryLabelViewState.categoryText.text
        is MovieCategoryLabelTextViewState.TextStringResource -> stringResource(id = movieCategoryLabelViewState.categoryText.textRes)
    }

    Text(
        text = textContent,
        fontWeight =
            if (selectedIndex == movieCategoryLabelViewState.itemId)//(movieCategoryLabelViewState.isSelected)
                FontWeight.Bold
            else
                FontWeight.Normal,
        style =
            if (selectedIndex == movieCategoryLabelViewState.itemId)//(movieCategoryLabelViewState.isSelected)
                TextStyle(textDecoration = TextDecoration.Underline)
            else
                TextStyle(textDecoration = TextDecoration.None),
        modifier = modifier
            .padding(5.dp)
            .clickable { onClick() }
    )
}

@Composable
fun MovieCategoryLabelScreen()
{
    val movieCategoryLabelViewStateList = remember {
        mutableStateOf(
            listOf<MovieCategoryLabelViewState>(
                MovieCategoryLabelViewState(0,false, MovieCategoryLabelTextViewState.TextString("Movies1")),
                MovieCategoryLabelViewState(1,false, MovieCategoryLabelTextViewState.TextStringResource(
                    R.string.movie_category)),
                )
        )
    }
    val selectedIndex = remember {
        mutableStateOf(
            movieCategoryLabelViewStateList.value[0].itemId
        )
    }
    val onClick = { selectedIndex.value = if (selectedIndex.value == 0) 1 else 0}

    Row() {
        MovieCategoryLabel(
            movieCategoryLabelViewState = movieCategoryLabelViewStateList.value[0],
            Modifier,
            onClick = onClick,
            selectedIndex.value
        )
        MovieCategoryLabel(
            movieCategoryLabelViewState = movieCategoryLabelViewStateList.value[1],
            Modifier,
            onClick = onClick,
            selectedIndex.value
        )
    }
}

@Preview
@Composable
private fun MovieCategoryLabelPreview()
{
    MovieCategoryLabelScreen()
}