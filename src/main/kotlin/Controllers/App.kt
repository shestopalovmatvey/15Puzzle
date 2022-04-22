package Controllers

import Main
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Scene
import javafx.stage.Modality
import javafx.stage.Stage

// Интерефейс которые наследуюется всеми контроллерами
interface AppView {
    //Метод позволяет закрывать окно приложения
    fun closeWindow(node: Node) {
        val stage = node.scene.window as Stage
        stage.close()
    }

    //Метод позволяет загружать новое окно приложения
    fun loadWindow(name: String, title: String) {
        val fxmlLoad = FXMLLoader(Main::class.java.getResource(name))
        val scene = Scene(fxmlLoad.load())
        val stage = Stage()
        stage.title = title
        stage.scene = scene
        stage.isResizable = false
        stage.initModality(Modality.APPLICATION_MODAL)
        stage.show()
    }
}