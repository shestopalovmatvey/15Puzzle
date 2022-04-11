import javafx.application.Application
import javafx.application.Application.launch
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import java.io.File

fun main(args: Array<String>) {
    launch(Main::class.java)
}
class Main : Application() {
    override fun start(primaryStage: Stage?) {
        val loader = FXMLLoader(File("src/main/resources/game.fxml").toURI().toURL())
        val scene = Scene(loader.load(), 1200.0, 800.0)
        primaryStage?.title = "15 Puzzle"
        primaryStage?.scene = scene
        primaryStage?.show()

    }
}