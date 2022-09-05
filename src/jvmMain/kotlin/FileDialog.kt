import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.awt.Frame
import java.io.File
import javax.swing.JFileChooser

@Composable
fun SelectDirButton(window: Frame, resultPath: (String) -> Unit) {
    var saveDir by remember { mutableStateOf(properties["saveDir"]?.toString()?:"C:") }

    Row() {
        TextField(saveDir, { saveDir = it }, Modifier.width(1000.dp))
        Button(onClick = {
            val dialog = JFileChooser()
            dialog.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
            dialog.currentDirectory = File(saveDir)
            dialog.showOpenDialog(window)
            dialog.selectedFile?.let {
                saveDir = it.absolutePath
                properties["saveDir"] = saveDir
                resultPath(saveDir)
            }
        }) {
            Text("Select save Folder")
        }
    }
}