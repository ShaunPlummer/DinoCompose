package com.wajahatkarim3.dino.compose

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import com.wajahatkarim3.dino.compose.model.GameState

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val deviceMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(deviceMetrics)
        val deviceWidthInPixels = deviceMetrics.widthPixels

        setContent {
            MaterialTheme(
                colors = if (isSystemInDarkTheme()) darkThemeColors else lightThemeColors
            ) {
                DinoGameScene(
                    gameState = GameState(),
                    deviceWidthInPixels = deviceWidthInPixels,
                    isDebuggable = true
                ) {}
            }
        }
    }
}
