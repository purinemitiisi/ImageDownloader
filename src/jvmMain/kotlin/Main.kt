// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

@Composable
@Preview
fun App(window: ComposeWindow) {
    var saveDir by remember { mutableStateOf("C:/Users/yugop/Pictures/") }

    val downloadMap = remember { mutableStateMapOf<String, Int>() }

    MaterialTheme {
        Box(modifier = Modifier.size(1600.dp, 900.dp)) {
            Column {
                SelectDirButton(window) {
                    saveDir = it
                }

                DownloadButton(
                    saveDir,
                    startDownload = { downloadMap[it] = 0 },
                    downloaded = { downloadMap[it] = 1 },
                    error = { downloadMap[it] = -1 }
                )

                LazyColumn {
                    items(items = downloadMap.toList()) {
                        Box(
                            modifier = Modifier.background(
                                when (it.second) {
                                    1 -> Color.Green
                                    -1 -> Color.Red
                                    else -> Color.White
                                }
                            )
                        ) {
                            Text(it.first)
                        }
                    }
                }
            }
        }
    }
}

fun main() = application {
    Window(state = WindowState(width = 1600.dp, height = 900.dp),
        onCloseRequest = {
            properties.forEach { println(it) }
            properties.close()
            exitApplication()
        }
    ) {
        App(this.window)
    }
}
