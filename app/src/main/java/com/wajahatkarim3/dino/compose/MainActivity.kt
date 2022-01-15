package com.wajahatkarim3.dino.compose

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.lifecycle.lifecycleScope
import com.wajahatkarim3.dino.compose.model.CactusImageProviderImp
import com.wajahatkarim3.dino.compose.model.CactusState
import com.wajahatkarim3.dino.compose.model.GameState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val deviceWidthInPixels: Int by lazy {
        val deviceMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(deviceMetrics)
        deviceMetrics.widthPixels
    }

    private val cactusState: CactusState by lazy {
        CactusState(
            deviceWidthInPixels = deviceWidthInPixels,
            cactusSpeed = EARTH_SPEED,
            cactusImageProvider = CactusImageProviderImp()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch(Dispatchers.IO) {
            val c = cactusState
            withContext(Dispatchers.Main) {
                setContent {
                    MaterialTheme(
                        colors = if (isSystemInDarkTheme()) darkThemeColors else lightThemeColors
                    ) {
                        DinoGameScene(
                            gameState = GameState(),
                            deviceWidthInPixels = deviceWidthInPixels,
                            isDebuggable = true,
                            cactusStateRaw = c
                        ) {}
                    }
                }
            }
        }
    }
}
