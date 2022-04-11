package Controllers

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.event.ActionEvent


class Controller: AppView {

    @FXML
    lateinit var startButton: Button

    @FXML
    lateinit var closeButton: Button

    @FXML
    fun closeGame(event: ActionEvent) {
        closeWindow(closeButton)
    }

    @FXML
    fun startGame(event: ActionEvent) {
        startButton.scene.window.hide()
        loadWindow("gameWindow.fxml", "15 Puzzle")
    }
}