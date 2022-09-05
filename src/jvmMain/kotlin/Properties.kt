import java.io.File
import java.util.*

object properties : Properties(), AutoCloseable {
    val file = File("application.properties")

    init {
        file.inputStream().use { load(it) }
    }

    override fun close() {
        this.store(file.outputStream(), null)
    }
}
