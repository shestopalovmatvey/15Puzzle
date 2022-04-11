package Controllers

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.layout.GridPane
import java.io.IOException


class ControllerNewGame : AppView {

    @FXML
    fun initialize() {
        
    }

    @FXML
    lateinit var startSolve: Button

    @FXML
    lateinit var closeGameButton: Button

    @FXML
    lateinit var listButton: GridPane

    @FXML
    lateinit var textField: TextField

    @FXML
    fun closeGame(event: ActionEvent) {
        closeWindow(closeGameButton)
    }

    @FXML
    fun solvePuzzle(event: ActionEvent) {
        val button = event.target as Button
        val strField = textField.text
        val stringButton = button.text
        try {
            if (strField.toInt() in 1..15) {
                for (i in listButton.children) {
                    val c = i as Button
                    if (c.text == strField) {
                        c.text = stringButton
                        button.text = strField
                    }
                }
            }
        } catch (e: IOException) {
        }
        textField.clear()
    }

}
