package endava.codebase.android.movieapp.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import endava.codebase.android.movieapp.mock.MoviesMock
import endava.codebase.android.movieapp.ui.theme.Gray700

data class ActorCardViewState(
    val imageUrl: String,
    val name: String,
    val character: String,
)

@Composable
fun ActorCard(
    actorCardViewState: ActorCardViewState,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(20 .dp),
        elevation = 10 .dp,
        modifier = modifier
            .padding(5.dp)
    ) {
        Column() {
            AsyncImage(
                model = actorCardViewState.imageUrl,
                contentDescription = actorCardViewState.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
            )
            Text(
                text = actorCardViewState.name,
                fontSize = 15 .sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(2 .dp)
            )
            Text(
                text = actorCardViewState.character,
                fontSize = 12 .sp,
                color = Gray700,
                modifier = Modifier
                    .padding(2 .dp)
            )
        }
    }
}

@Preview
@Composable
private fun ActorCardPreview() {
    val actor = MoviesMock.getActor()
    ActorCard(
        ActorCardViewState(actor.imageUrl!!, actor.name, actor.character),
        Modifier.size(width = 125 .dp, height = 209 .dp),
    )
}