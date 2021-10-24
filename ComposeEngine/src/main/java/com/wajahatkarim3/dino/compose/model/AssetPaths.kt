package com.wajahatkarim3.dino.compose.model

import android.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.vector.PathParser

val BASE_SCALE = 0.75f
val DOUBT_FACTOR = 20f

// Cloud
private var CLOUD_PATH_STR = "M169.895,88.699C167.27,71.887 152.695,58.984 135.156,58.984C128.559,58.984 122.219,60.809 116.719,64.215C108.355,50.156 93.293,41.406 76.563,41.406C50.715,41.406 29.688,62.434 29.688,88.281C29.688,88.441 29.688,88.609 29.691,88.766C13.082,91.566 0,106.047 0,123.438C0,142.824 16.16,158.594 35.547,158.594L164.453,158.594C183.84,158.594 200,142.824 200,123.438C200,105.898 186.707,91.324 169.895,88.699ZM169.895,88.699"
private var cloudPath = PathParser().parsePathString(CLOUD_PATH_STR)
fun CloudPath(): Path {
    var path = cloudPath.toPath()
    var scaleMatrix = Matrix()
    scaleMatrix.setScale(BASE_SCALE, BASE_SCALE, 0f, 0f)
    var androidPath = path.asAndroidPath()
    androidPath.transform(scaleMatrix)
    return androidPath.asComposePath()
}

// Cactus
private var CACTUS_PATH_STR = "M57.449,111.191L85.246,111.191L85.246,200.145L118.605,200.145L118.605,137.137L142.695,137.137C149.859,137.137 155.668,131.328 155.668,124.164L155.668,59.301C155.668,52.137 149.859,46.328 142.695,46.328C135.531,46.328 129.723,52.137 129.723,59.301L129.723,111.191L118.605,111.191L118.605,16.68C118.605,7.469 111.137,0 101.926,0C92.715,0 85.246,7.469 85.246,16.68L85.246,85.246L70.422,85.246L70.422,37.063C70.422,29.898 64.613,24.09 57.449,24.09C50.285,24.09 44.477,29.898 44.477,37.063L44.477,98.219C44.477,105.383 50.285,111.191 57.449,111.191ZM57.449,111.191"
private var cactusPath = PathParser().parsePathString(CACTUS_PATH_STR)
fun CactusPath(): Path {
    var path = cactusPath.toPath()
    var scaleMatrix = Matrix()
    scaleMatrix.setScale(BASE_SCALE, BASE_SCALE, 0f, 0f)
    var androidPath = path.asAndroidPath()
    androidPath.transform(scaleMatrix)
    return androidPath.asComposePath()
}

// T-Rex Dino
private var TREX_DINO_PATH_STR = "M93.027,18.996L173.41,18.996L173.41,60.836L93.027,60.836ZM93.027,18.996 M99.27,14.727L167.168,14.727L167.168,56.57L99.27,56.57ZM99.27,14.727 M93.027,37.715L127.531,37.715L127.531,79.555L93.027,79.555ZM93.027,37.715 M93.027,71.113L153.223,71.113L153.223,79.555L93.027,79.555ZM93.027,71.113 M107.113,25.676L115.113,25.676L115.113,33.676L107.113,33.676ZM107.113,25.676 M93.027,37.715L120.188,37.715L120.188,122.5L93.027,122.5ZM93.027,37.715 M93.027,98.848L136.707,98.848L136.707,107.289L93.027,107.289ZM93.027,98.848 M130.098,99.008L136.707,99.008L136.707,114.422L130.098,114.422ZM130.098,99.008 M86.629,84.328L113.789,84.328L113.789,137.914L86.629,137.914ZM86.629,84.328 M73.828,91.793L105.762,91.793L105.762,145.379L73.828,145.379ZM73.828,91.793 M63.16,99.262L90.324,99.262L90.324,152.848L63.16,152.848ZM63.16,99.262 M64.953,106.727L79.656,106.727L79.656,160.313L64.953,160.313ZM64.953,106.727 M86.629,106.727L96.539,106.727L96.539,160.313L86.629,160.313ZM86.629,106.727 M91.031,106.727L96.539,106.727L96.539,185.273L91.031,185.273ZM91.031,106.727 M91.031,179.766L106.082,179.766L106.082,185.273L91.031,185.273ZM91.031,179.766 M63.301,106.727L68.805,106.727L68.805,170.59L63.301,170.59ZM63.301,106.727 M63.301,165.086L78.348,165.086L78.348,170.59L63.301,170.59ZM63.301,165.086 M54.285,106.727L68.988,106.727L68.988,153.566L54.285,153.566ZM54.285,106.727 M45.695,106.727L58.324,106.727L58.324,146.301L45.695,146.301ZM45.695,106.727 M39.293,99.25L49.789,99.25L49.789,137.766L39.293,137.766ZM39.293,99.25 M31.828,91.781L42.324,91.781L42.324,130.301L31.828,130.301ZM31.828,91.781 M26.59,84.316L32.723,84.316L32.723,122.832L26.59,122.832ZM26.59,84.316 "
private var TREX_DINO_PATH_2_STR = "M93.027,18.996L173.41,18.996L173.41,60.836L93.027,60.836ZM93.027,18.996 M99.27,14.727L167.168,14.727L167.168,56.57L99.27,56.57ZM99.27,14.727 M93.027,37.715L127.531,37.715L127.531,79.555L93.027,79.555ZM93.027,37.715 M93.027,71.113L153.223,71.113L153.223,79.555L93.027,79.555ZM93.027,71.113 M107.113,25.676L115.113,25.676L115.113,33.676L107.113,33.676ZM107.113,25.676 M93.027,37.715L120.188,37.715L120.188,122.5L93.027,122.5ZM93.027,37.715 M93.027,98.848L136.707,98.848L136.707,107.289L93.027,107.289ZM93.027,98.848 M130.098,99.008L136.707,99.008L136.707,114.422L130.098,114.422ZM130.098,99.008 M86.629,84.328L113.789,84.328L113.789,137.914L86.629,137.914ZM86.629,84.328 M73.828,91.793L105.762,91.793L105.762,145.379L73.828,145.379ZM73.828,91.793 M63.16,99.262L90.324,99.262L90.324,152.848L63.16,152.848ZM63.16,99.262 M64.953,106.727L79.656,106.727L79.656,160.313L64.953,160.313ZM64.953,106.727 M86.629,106.727L96.539,106.727L96.539,160.313L86.629,160.313ZM86.629,106.727 M91.031,106.727L96.539,106.727L96.539,165.273L91.031,165.273ZM91.031,106.727 M91.031,164.766L106.082,164.766L106.082,170.273L91.031,170.273ZM91.031,179.766 M63.301,106.727L68.805,106.727L68.805,185.59L63.301,185.59ZM63.301,106.727 M63.301,187.086L78.348,187.086L78.348,180.59L63.301,180.59ZM63.301,165.086 M54.285,106.727L68.988,106.727L68.988,153.566L54.285,153.566ZM54.285,106.727 M45.695,106.727L58.324,106.727L58.324,146.301L45.695,146.301ZM45.695,106.727 M39.293,99.25L49.789,99.25L49.789,137.766L39.293,137.766ZM39.293,99.25 M31.828,91.781L42.324,91.781L42.324,130.301L31.828,130.301ZM31.828,91.781 M26.59,84.316L32.723,84.316L32.723,122.832L26.59,122.832ZM26.59,84.316 "
private var trexPath = PathParser().parsePathString(TREX_DINO_PATH_STR)
private var trexPath2 = PathParser().parsePathString(TREX_DINO_PATH_2_STR)
fun DinoPath(): Path {
    var path = trexPath.toPath()
    var scaleMatrix = Matrix()
    scaleMatrix.setScale(BASE_SCALE, BASE_SCALE, 0f, 0f)
    var androidPath = path.asAndroidPath()
    androidPath.transform(scaleMatrix)
    return androidPath.asComposePath()
}
fun DinoPath2(): Path {
    var path = trexPath2.toPath()
    var scaleMatrix = Matrix()
    scaleMatrix.setScale(BASE_SCALE, BASE_SCALE, 0f, 0f)
    var androidPath = path.asAndroidPath()
    androidPath.transform(scaleMatrix)
    return androidPath.asComposePath()
}

