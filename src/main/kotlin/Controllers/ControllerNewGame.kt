package Controllers

import FifteenPuzzle
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.layout.GridPane


class ControllerNewGame : AppView {

    private lateinit var solve: FifteenPuzzle

    private var list: List<FifteenPuzzle>? = null

    private lateinit var matrixSolve: Array<IntArray>

    private var count = 0

    @FXML
    lateinit var leftCount: Button

    @FXML
    lateinit var rightCount: Button

    @FXML
    lateinit var startSolve: Button

    @FXML
    lateinit var closeGameButton: Button

    @FXML
    lateinit var listButton: GridPane

    @FXML
    lateinit var shuffleBT: Button

    //Метод который выполняет какое-то действие при инициализации этого окна,
    //в данном случае  значение 0 переводить в пустую клетку
    @FXML
    fun initialize() {
        makeInvisibleZero()
    }

    //Метод переводит полученные состояния поля из матрицы в лист
    private fun matrixToList(matrix: Array<IntArray>?): MutableList<Int> {
        val list = mutableListOf<Int>()

        for (i in matrix!!.indices) {
            for (j in matrix[i].indices) {
                list.add(matrix[i][j])
            }
        }
        return list
    }

    //Метод отрисовывает новое поле игры
    private fun showNewPane(elementMatrix: MutableList<Int>) {
        for ((count, i) in listButton.children.withIndex()) {
            val c = i as Button
            c.text = elementMatrix[count].toString()
        }
    }

    //Метод который позволяет значение 0, переводить в пустую клетку
    private fun makeInvisibleZero() {
        for (i in listButton.children) {
            val c = i as Button
            if (c.text == "0") c.isVisible = false
        }
    }

    //Метод который позволяет пустую клетку, переводить в 0 для того, чтобы не нарушать решение алгоритма
    private fun makeVisibleZero() {
        for (i in listButton.children) {
            val c = i as Button
            if (c.text == "0") c.isVisible = true
        }
    }

    //Метод срабатывает при нажатии на кнопку ">", показывает следующее состояние поля при решении
    @FXML
    fun moveRight(event: ActionEvent) {
        if(count + 1 < list!!.size){
            makeVisibleZero()
            showNewPane(matrixToList(list?.get(++count)?.tiles))
            makeInvisibleZero()
        }

    }

    //Метод срабатывает при нажатии на кнопку "<", показывает предыдущее состояние поля при решении
    @FXML
    fun moveLeft(event: ActionEvent) {
        if(count > 0){
            makeVisibleZero()
            showNewPane(matrixToList(list?.get(--count)?.tiles))
            makeInvisibleZero()
        }
    }

    //Метод срабатывает при нажатии на кнопку "Solve the puzzle", показывает решенное поле
    @FXML
    fun solvePuzzle(event: ActionEvent) {
        list = solve.solve()

        matrixSolve = list?.get(list!!.size - 1)?.tiles!!

        makeVisibleZero()
        showNewPane(matrixToList(matrixSolve))
        makeInvisibleZero()

        count = 0
    }

    //Метод срабатывает при нажатии на кнопку "shuffle", показывает варианты начального положения ячеек
    @FXML
    fun shuffleBT(event: ActionEvent) {
        solve = FifteenPuzzle()
        solve.solveShuffle()

        makeVisibleZero()
        showNewPane(matrixToList(solve.tiles))
        makeInvisibleZero()

        count = 0
    }

    ////Метод срабатывает при нажатии на кнопку "close game", закрывает окно
    @FXML
    fun closeGame(event: ActionEvent) {
        closeWindow(closeGameButton)
    }

}
