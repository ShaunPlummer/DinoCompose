package com.wajahatkarim3.dino.compose.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import com.wajahatkarim3.dino.compose.EARTH_SPEED
import com.wajahatkarim3.dino.compose.EARTH_Y_POSITION
import com.wajahatkarim3.dino.compose.drawBoundingBox
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


private const val url: String =
    "https://lh3.googleusercontent.com/nECZ-HcvioMs8aiWhOt8oOqeMWagKqYQZ2h-casdH-xvoSLbUh-RxKJt9J7hjB9zyBPcUlcb7wsTQ-mKexCqa56D9XLyQrSGWM-z9g=w600"

data class CactusState(
    val deviceWidthInPixels: Int,
    val cactusList: ArrayList<CactusModel> = ArrayList(),
    val cactusSpeed: Int = EARTH_SPEED,
    val cactusImageProvider: CactusImageProvider
) {

    private val distanceBetweenCactus = (deviceWidthInPixels * 0.4f).toInt()

    init {
        initCactus()
    }

    fun initCactus() {
        cactusList.clear()
        var startX = deviceWidthInPixels + 150
        var cactusCount = 3

        for (i in 0 until cactusCount) {
            var cactus = CactusModel(
                count = rand(1, 3),
                scale = rand(0.85f, 1.2f),
                xPos = startX,
                yPos = EARTH_Y_POSITION.toInt() + rand(20, 30),
                imageProvider = cactusImageProvider
            )
            cactusList.add(cactus)

            startX += distanceBetweenCactus
            startX += rand(0, distanceBetweenCactus)
        }
    }

    fun moveForward() {
        cactusList.forEach { cactus ->
            cactus.xPos -= cactusSpeed
        }

        if (cactusList.first().xPos < -250) {
            cactusList.removeAt(0)
            var cactus = CactusModel(
                count = rand(1, 3),
                scale = rand(0.85f, 1.2f),
                xPos = nextCactusX(cactusList.last().xPos),
                yPos = EARTH_Y_POSITION.toInt() + rand(20, 30),
                imageProvider = cactusImageProvider
            )
            cactusList.add(cactus)
        }
    }

    private fun nextCactusX(lastX: Int): Int {
        var nextX = lastX + distanceBetweenCactus
        nextX += rand(0, distanceBetweenCactus)
        if (nextX < deviceWidthInPixels)
            nextX += (deviceWidthInPixels - nextX)
        return nextX
    }
}

data class CactusModel(
    val count: Int = 1,
    val scale: Float = 1f,
    var xPos: Int = 0,
    var yPos: Int = 0,
    var path: Path = CactusPath(),
    val imageProvider: CactusImageProvider
) {

    fun getBounds(): Rect {
        return Rect(
            left = xPos.toFloat(),
            top = yPos.toFloat() - (path.getBounds().height * scale),
            right = xPos + (path.getBounds().width * scale),
            bottom = yPos.toFloat()
        )
    }

    fun draw(canvas: DrawScope, color: Color) {
        canvas.apply {
            withTransform({
                scale(scale, scale)
                translate(
                    left = xPos.toFloat(),
                    top = getBounds().top * scale
                )
            })
            {

                drawImage(
                    image = imageProvider.image!!.asImageBitmap()
                )
//
                //drawBoundingBox(color = Color.Red, rect = path.getBounds())
                drawBoundingBox(
                    color = Color.Red,
                    rect = Rect(offset = Offset.Zero, size = Size(50f, 50f))
                )
            }
        }
    }

    fun checkCollision(dinoState: DinoState): Boolean {
        return dinoState.getBounds()
            .deflate(DOUBT_FACTOR)
            .overlaps(
                path.getBounds()
                    .deflate(DOUBT_FACTOR)
            )
    }
}

fun getResizedBitmap(bm: Bitmap, newHeight: Int, newWidth: Int): Bitmap? {
    val width = bm.width
    val height = bm.height
    val scaleWidth = newWidth.toFloat() / width
    val scaleHeight = newHeight.toFloat() / height
    // CREATE A MATRIX FOR THE MANIPULATION
    val matrix = Matrix()
    // RESIZE THE BIT MAP
    matrix.postScale(scaleWidth, scaleHeight)

    // "RECREATE" THE NEW BITMAP
    return Bitmap.createBitmap(
        bm, 0, 0, width, height,
        matrix, false
    )
}

fun getBitmapFromURL(src: String?): Bitmap? {
    return try {
        val url = URL(src)
        val connection = url
            .openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val input = connection.inputStream
        BitmapFactory.decodeStream(input)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

interface CactusImageProvider {
    val image: Bitmap?
}

class CactusImageProviderImp : CactusImageProvider {

    private val rawImage: Bitmap? = getBitmapFromURL(url)
    override val image: Bitmap? = getResizedBitmap(rawImage!!, 50, 50)
}
