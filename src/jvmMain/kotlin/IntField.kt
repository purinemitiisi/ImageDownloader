import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun IntField(int: Int, onValueChange: (Int) -> Unit, modifier: Modifier = Modifier,enable:Boolean = true) {
    TextField(
        int.toString(),
        { onValueChange(it.toIntOrNull()?:0) },
        modifier,
        enable,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}
