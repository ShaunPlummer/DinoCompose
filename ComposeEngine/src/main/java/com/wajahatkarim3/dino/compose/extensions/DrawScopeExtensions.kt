package com.wajahatkarim3.dino.compose.extensions

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import com.wajahatkarim3.dino.compose.EARTH_GROUND_STROKE_WIDTH
import com.wajahatkarim3.dino.compose.EARTH_Y_POSITION
import com.wajahatkarim3.dino.compose.drawBoundingBox
import com.wajahatkarim3.dino.compose.model.CactusState
import com.wajahatkarim3.dino.compose.model.CloudState
import com.wajahatkarim3.dino.compose.model.DinoState
import com.wajahatkarim3.dino.compose.model.EarthState

fun DrawScope.DinoView(dinoState: DinoState, color: Color) {
    withTransform({
        translate(
            left = dinoState.xPos,
            top = dinoState.yPos - dinoState.path.getBounds().height
        )
    }) {
        drawPath(
            path = dinoState.path,
            color = color,
            style = Fill
        )
        drawBoundingBox(color = Color.Green, rect = dinoState.path.getBounds())
    }
}

fun DrawScope.CloudsView(cloudState: CloudState, color: Color) {
    cloudState.cloudsList.forEach { cloud ->
        withTransform({
            translate(
                left = cloud.xPos.toFloat(),
                top = cloud.yPos.toFloat()
            )
        })
        {
            drawPath(
                path = cloudState.cloudsList.first().path,
                color = color,
                style = Stroke(2f)
            )

            drawBoundingBox(color = Color.Blue, rect = cloud.path.getBounds())
        }
    }
}

fun DrawScope.EarthView(earthState: EarthState, color: Color, deviceWidthInPixels: Int) {
    // Ground Line
    drawLine(
        color = color,
        start = Offset(x = 0f, y = EARTH_Y_POSITION),
        end = Offset(x = deviceWidthInPixels.toFloat(), y = EARTH_Y_POSITION),
        strokeWidth = EARTH_GROUND_STROKE_WIDTH
    )
    earthState.blocksList.forEach { block ->
        drawLine(
            color = color,
            start = Offset(x = block.xPos, y = EARTH_Y_POSITION + 20),
            end = Offset(x = block.size, y = EARTH_Y_POSITION + 20),
            strokeWidth = EARTH_GROUND_STROKE_WIDTH / 5,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 40f), 0f)
        )
        drawLine(
            color = color,
            start = Offset(x = block.xPos, y = EARTH_Y_POSITION + 30),
            end = Offset(x = block.size, y = EARTH_Y_POSITION + 30),
            strokeWidth = EARTH_GROUND_STROKE_WIDTH / 5,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 50f), 40f)
        )
    }
}

fun DrawScope.CactusView(cactusState: CactusState, color: Color) {
    cactusState.cactusList.forEach { cactus ->
        withTransform({
            scale(cactus.scale, cactus.scale)
            translate(
                left = cactus.xPos.toFloat(),
                top = cactus.getBounds().top * cactus.scale
            )
        })
        {
            drawPath(
                path = cactus.path,
                color = color,
                style = Fill
            )
            drawBoundingBox(color = Color.Red, rect = cactus.path.getBounds())
        }
    }
}
