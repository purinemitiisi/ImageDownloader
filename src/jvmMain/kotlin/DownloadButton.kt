import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*
import java.io.File
import java.net.URL
import java.nio.file.Files

@Composable
fun DownloadButton(
    saveDir: String,
    startDownload: (url: String) -> Unit,
    downloaded: (url: String) -> Unit,
    error: (url: String) -> Unit
) {
    var downloadUrlBase by remember { mutableStateOf("https://") }
    var extensions by remember { mutableStateOf(".jpg|.png|.gif") }
    var fromNum by remember { mutableStateOf(1) }
    var toNum by remember { mutableStateOf(100) }
    var isRun by remember { mutableStateOf(false) }

    Column {

        Row(Modifier.padding(10.dp)) {
            TextField(downloadUrlBase, { downloadUrlBase = it }, Modifier.width(1000.dp), !isRun)
            TextField(extensions, { extensions = it }, Modifier.width(200.dp), !isRun)
            IntField(fromNum, { fromNum = it }, Modifier.width(80.dp), !isRun)
            IntField(toNum, { toNum = it }, Modifier.width(80.dp), !isRun)

            Button(enabled = !isRun,
                onClick = {
                    isRun = true
                    val extensions = extensions.split("|")

                    download(fromNum, toNum, downloadUrlBase, extensions, saveDir, startDownload, downloaded, error)

                    isRun = false
                }) {
                Text("Run")
            }
            Text(if (isRun) "Run" else "Wait")
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
private fun download(
    fromNum: Int,
    toNum: Int,
    downloadUrlBase: String,
    extensions: List<String>,
    saveDir: String,
    startDownload: (url: String) -> Unit,
    downloaded: (url: String) -> Unit,
    error: (url: String) -> Unit
) {
    for (i in fromNum..toNum) {
        val urlBase = downloadUrlBase.replace("*", i.toString())
        GlobalScope.launch(Dispatchers.IO) {
            for (ex in extensions) {
                val url = URL(urlBase + ex)
                val file = File("$saveDir${url.file}")
                val path = file.toPath()
                path.parent.toFile().mkdirs()

                try {
                    startDownload(url.toString())
                    url.openStream().use {
                        Files.copy(it, file.toPath())
                    }
                    downloaded(url.toString())

                    return@launch
                } catch (e: Exception) {
                    error(url.toString())
                }
            }
        }
    }
}
