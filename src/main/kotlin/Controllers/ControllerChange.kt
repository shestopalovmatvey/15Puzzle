package Controllers

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button


class ControllerChange: AppView {

    @FXML
    lateinit var buttonChange: Button

    @FXML
    lateinit var exit: Button

    @FXML
    fun changed(event: ActionEvent) {
    }

    @FXML
    fun closeChangeWindow(event: ActionEvent) {
        closeWindow(exit)
    }


}