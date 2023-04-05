package endava.codebase.android.movieapp.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import endava.codebase.android.movieapp.R
import endava.codebase.android.movieapp.ui.theme.spacing

data class MovieCategoryLabelViewState(
    val itemId: Int,
    val isSelected: Boolean,
    val categoryText: MovieCategoryLabelTextViewState,
)

sealed class MovieCategoryLabelTextViewState {
    data class TextString(val text: String) : MovieCategoryLabelTextViewState()
    data class TextStringResource(@StringRes val textRes: Int) : MovieCategoryLabelTextViewState()
}

@Composable
fun MovieCategoryLabel(
    movieCategoryLabelViewState: MovieCategoryLabelViewState,
    modifier: Modifier = Modifier,
    onClick: (MovieCategoryLabelViewState) -> Unit,
) {

    val textContent: String =
        when (movieCategoryLabelViewState.categoryText) {
            is MovieCategoryLabelTextViewState.TextString ->
                movieCategoryLabelViewState.categoryText.text
            is MovieCategoryLabelTextViewState.TextStringResource ->
                stringResource(id = movieCategoryLabelViewState.categoryText.textRes)
        }

    val fontWeight =
        if (movieCategoryLabelViewState.isSelected)
            FontWeight.Bold
        else
            FontWeight.Normal

    val style =
        if (movieCategoryLabelViewState.isSelected)
            TextStyle(textDecoration = TextDecoration.Underline)
        else
            TextStyle(textDecoration = TextDecoration.None)

    Text(
        text = textContent,
        fontWeight = fontWeight,
        style = style,
        modifier = modifier
            .clickable { onClick(movieCategoryLabelViewState) }
    )
}

@Preview
@Composable
private fun MovieCategoryLabelPreview() {
    var movieCategoryLabelViewStateList = remember {
        mutableStateListOf(
            MovieCategoryLabelViewState(0, true, MovieCategoryLabelTextViewState.TextString("Movies1")),
            MovieCategoryLabelViewState(
                1, false,
                MovieCategoryLabelTextViewState.TextStringResource(
                    R.string.movie_category
                )
            ),
        )
    }

    val onClick = { selectedMovieCategory: MovieCategoryLabelViewState ->
        val iterate = movieCategoryLabelViewStateList.listIterator()
        while (iterate.hasNext()) {
            val oldValue = iterate.next()
            iterate.set(
                MovieCategoryLabelViewState(
                    itemId = oldValue.itemId,
                    categoryText = oldValue.categoryText,
                    isSelected = oldValue.itemId == selectedMovieCategory.itemId
                )
            )
        }
    }

    Row {
        MovieCategoryLabel(
            movieCategoryLabelViewState = movieCategoryLabelViewStateList[0],
            Modifier.padding(MaterialTheme.spacing.extraSmall),
            onClick = onClick,
        )
        MovieCategoryLabel(
            movieCategoryLabelViewState = movieCategoryLabelViewStateList[1],
            Modifier.padding(MaterialTheme.spacing.extraSmall),
            onClick = onClick,
        )
    }
}
