package com.wajahatkarim3.dino.compose

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wajahatkarim3.dino.compose.extensions.CactusView
import com.wajahatkarim3.dino.compose.extensions.CloudsView
import com.wajahatkarim3.dino.compose.extensions.DinoView
import com.wajahatkarim3.dino.compose.extensions.EarthView
import com.wajahatkarim3.dino.compose.model.CactusState
import com.wajahatkarim3.dino.compose.model.CloudState
import com.wajahatkarim3.dino.compose.model.DOUBT_FACTOR
import com.wajahatkarim3.dino.compose.model.DinoState
import com.wajahatkarim3.dino.compose.model.EarthState
import com.wajahatkarim3.dino.compose.model.GameState
import com.washtechnologies.composeengine.R

const val EARTH_Y_POSITION = 500f
const val EARTH_GROUND_STROKE_WIDTH = 10f
private const val CLOUDS_SPEED = 1 // pixels per frame
private const val MAX_CLOUDS = 3
const val EARTH_OFFSET = 200
const val EARTH_SPEED = 9

var showBounds = mutableStateOf(false)

@Preview
@Composable
fun DinoGameScenePreview() {
    MaterialTheme {
        DinoGameScene(GameState(), 1920, false) {}
    }
}

@Composable
fun DinoGameScene(
    gameState: GameState,
    deviceWidthInPixels: Int,
    isDebuggable: Boolean = false,
    onFinished: () -> Unit
) {
    val cloudsState by remember {
        mutableStateOf(
            CloudState(
                deviceWidthInPixels,
                maxClouds = MAX_CLOUDS,
                speed = CLOUDS_SPEED
            )
        )
    }
    val earthState by remember {
        mutableStateOf(
            EarthState(
                deviceWidthInPixels,
                maxBlocks = 2,
                speed = EARTH_SPEED
            )
        )
    }
    val cactusState by remember {
        mutableStateOf(
            CactusState(
                deviceWidthInPixels = deviceWidthInPixels,
                cactusSpeed = EARTH_SPEED
            )
        )
    }
    val dinoState by remember { mutableStateOf(DinoState()) }
    val currentScore by gameState.currentScore.observeAsState()
    val highScore by gameState.highScore.observeAsState()

    val earthColor = MaterialTheme.colors.earthColor
    val cloudsColor = MaterialTheme.colors.cloudColor
    val dinoColor = MaterialTheme.colors.dinoColor
    val cactusColor = MaterialTheme.colors.cactusColor

    Log.d(
        "DinoGameScene",
        "currentScore:${gameState.currentScore.value}, isGameOver:${gameState.isGameOver}"
    )
    if (!gameState.isGameOver) {
        // Game Loop
        gameState.increaseScore()
        cloudsState.moveForward()
        earthState.moveForward()
        cactusState.moveForward()
        dinoState.move()

        // Collision Check
        cactusState.cactusList.forEach {
            if (dinoState.getBounds()
                    .deflate(DOUBT_FACTOR)
                    .overlaps(
                        it.getBounds()
                            .deflate(DOUBT_FACTOR)
                    )
            ) {
                gameState.isGameOver = true
                return@forEach
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clickable(
                onClick = {
                    if (!gameState.isGameOver)
                        dinoState.jump()
                    else {
                        cactusState.initCactus()
                        dinoState.init()
                        gameState.replay()
                    }
                }
            )
    ) {
        if (isDebuggable) {
            ShowBoundsSwitchView()
        }
        HighScoreTextViews(requireNotNull(currentScore), requireNotNull(highScore))
        Canvas(modifier = Modifier.weight(1f)) {
            EarthView(earthState, color = earthColor, deviceWidthInPixels)
            CloudsView(cloudsState, color = cloudsColor)
            DinoView(dinoState, color = dinoColor)
            CactusView(cactusState, color = cactusColor)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = onFinished) {
                Text("End Game")
            }
        }
    }
    GameOverTextView(
        modifier = Modifier
            .padding(top = 150.dp)
            .fillMaxWidth(),
        gameState.isGameOver,
    )
}

@Composable
fun HighScoreTextViews(currentScore: Int, highScore: Int) {
    Spacer(modifier = Modifier.padding(top = 50.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 20.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Text(text = "HI", style = TextStyle(color = MaterialTheme.colors.highScoreColor))
        Spacer(modifier = Modifier.padding(start = 10.dp))
        Text(
            text = "$highScore".padStart(5, '0'),
            style = TextStyle(color = MaterialTheme.colors.highScoreColor)
        )
        Spacer(modifier = Modifier.padding(start = 10.dp))
        Text(
            text = "$currentScore".padStart(5, '0'),
            style = TextStyle(color = MaterialTheme.colors.currentScoreColor)
        )
    }
}

@Composable
fun ShowBoundsSwitchView() {
    Spacer(modifier = Modifier.padding(top = 20.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 20.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Text(text = "Show Bounds")
        Spacer(modifier = Modifier.padding(start = 10.dp))
        Switch(checked = showBounds.value, onCheckedChange = {
            showBounds.value = it
        })
    }
}

@Composable
fun GameOverTextView(
    modifier: Modifier = Modifier,
    isGameOver: Boolean = true
) {
    Column(modifier = modifier) {
        Text(
            text = if (isGameOver) "GAME OVER" else "",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            letterSpacing = 5.sp,
            style = TextStyle(
                color = MaterialTheme.colors.gameOverColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
        if (isGameOver) {
            Icon(
                painter = painterResource(id = R.drawable.ic_replay),
                contentDescription = null, // decorative element
                modifier = Modifier
                    .size(40.dp)
                    .padding(top = 10.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            )
        }
    }
}

fun DrawScope.drawBoundingBox(color: Color, rect: Rect) {
    if (showBounds.value) {
        drawRect(color, rect.topLeft, rect.size, style = Stroke(3f))
        drawRect(
            color,
            rect.deflate(DOUBT_FACTOR).topLeft,
            rect.deflate(DOUBT_FACTOR).size,
            style = Stroke(
                width = 3f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(2f, 4f), 0f)
            )
        )
    }
}
