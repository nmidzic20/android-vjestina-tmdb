package endava.codebase.android.movieapp.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import endava.codebase.android.movieapp.ui.theme.DarkGreen

data class UserScore(var value: Float) {
    var text : String
    init {
        value = value.coerceIn(0f, 10f)
        text = value.toString()
    }
}

@Composable
fun UserScoreProgressBar(userScore: UserScore, modifier: Modifier = Modifier) {

    val progressPercentage : Float = 360f * (userScore.value / 10f)

    Box(
        modifier = modifier
    )
    {
        Canvas(
            modifier = Modifier
                .fillMaxSize(1f)
        ) {
            drawArc(
                color = DarkGreen,
                0f,
                360f,
                useCenter = false,
                style = Stroke(3.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        Canvas(
            modifier = Modifier
                .fillMaxSize(1f)
        ) {
            drawArc(
                color = Color.Green,
                -90f,
                progressPercentage,
                useCenter = false,
                style = Stroke(3.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(text = userScore.text,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }

}

@Preview
@Composable
fun UserScoreProgressBarPreview() {
    val userScore = UserScore(7.8f)
    UserScoreProgressBar(userScore, Modifier.size(42.dp))
}