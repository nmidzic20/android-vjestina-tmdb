package endava.codebase.android.movieapp.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import endava.codebase.android.movieapp.mock.MoviesMock
import endava.codebase.android.movieapp.ui.theme.spacing

data class CrewItemViewState(
    val name: String,
    val job: String,
)

@Composable
fun CrewItem(
    crewItemViewState: CrewItemViewState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = crewItemViewState.name,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(MaterialTheme.spacing.extraSmall)
        )
        Text(
            text = crewItemViewState.job,
            modifier = Modifier
                .padding(MaterialTheme.spacing.extraSmall)
        )
    }
}

@Preview
@Composable
private fun CrewItemPreview() {
    val crewman = MoviesMock.getCrewman()
    CrewItem(CrewItemViewState(crewman.name, crewman.job))
}
