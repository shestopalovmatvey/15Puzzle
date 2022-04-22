package Controllers

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.event.ActionEvent

//Контроллер начального окна игры
class Controller: AppView {

    @FXML
    lateinit var startButton: Button

    @FXML
    lateinit var closeButton: Button

    //Метод срабатывает при нажатии на кнопку "Close game", закрывает окно
    @FXML
    fun closeGame(event: ActionEvent) {
        closeWindow(closeButton)
    }

    //Метод срабатывает при нажатии на кнопку "Start game", загружает новое окно
    @FXML
    fun startGame(event: ActionEvent) {
        startButton.scene.window.hide()
        loadWindow("gameWindow.fxml", "15 Puzzle")
    }
}