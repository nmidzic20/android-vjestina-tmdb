package endava.codebase.android.movieapp.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import endava.codebase.android.movieapp.ui.theme.DarkGreen

class UserScore(_value: Float) {
    val value: Float
    val text: String
    val percentage: Float
    val progressSweepAngle: Float
    init {
        value = _value.coerceIn(0f, 10f)
        text = String.format("%.1f", value)
        percentage = (value / 10)
        progressSweepAngle = 360f * (value / 10f)
    }
}

@Composable
fun UserScoreProgressBar(userScore: UserScore, modifier: Modifier = Modifier) {

    Box(
        modifier = modifier
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawCircle(
                color = DarkGreen,
                style = Stroke(3.dp.toPx(), cap = StrokeCap.Round)
            )
            drawArc(
                color = Color.Green,
                startAngle = -90f,
                userScore.progressSweepAngle,
                useCenter = false,
                style = Stroke(3.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = userScore.text,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun UserScoreProgressBarPreview() {
    val userScore = UserScore(7.5f)
    UserScoreProgressBar(userScore, Modifier.size(42.dp))
}
