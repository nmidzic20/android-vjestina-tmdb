package endava.codebase.android.movieapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import endava.codebase.android.movieapp.mock.MoviesMock

data class CrewItemViewState(
    val name: String,
    val job: String,
)

@Composable
fun CrewItem(
    crewItemViewState: CrewItemViewState,
    modifier: Modifier = Modifier,
) {
    Column() {
        Text(text = crewItemViewState.name,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .padding(5 .dp))
        Text(text = crewItemViewState.job,
            modifier = modifier
                .padding(5 .dp)
        )
    }
}

@Preview
@Composable
private fun CrewItemPreview() {
    val crewman = MoviesMock.getCrewman()
    CrewItem(CrewItemViewState(crewman.name, crewman.job) )
}