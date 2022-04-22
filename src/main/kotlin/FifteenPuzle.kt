import java.util.*
import kotlin.math.abs

class FifteenPuzzle() {
    class TilePos(var x: Int, var y: Int)

    //Массив который хранит поле в виде матрицы
    var tiles: Array<IntArray> = Array(SIZE) { IntArray(SIZE) }
    private val displayWidth: Int
    private var blank: TilePos

    //Данный метод создает объект этого класса
    constructor(toClone: FifteenPuzzle) : this() {
        for (p in allTilePos()) {
            tiles[p.x][p.y] = toClone.tile(p)
        }
        blank = toClone.blank
    }

    //Метод, который заполняет список с TilePos с координатами позиций цифр
    private fun allTilePos(): List<TilePos> {
        val out = ArrayList<TilePos>()
        for (i in 0 until SIZE) {
            for (j in 0 until SIZE) {
                out.add(TilePos(i, j))
            }
        }
        return out
    }

    //Метод возвращает значение по позиции
    private fun tile(p: TilePos): Int {
        return tiles[p.x][p.y]
    }

    //Сравнение объектов
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as FifteenPuzzle
        return displayWidth == that.displayWidth && tiles.contentDeepEquals(that.tiles) && blank == that.blank
    }

    //Вычисление HashCode объекта
    override fun hashCode(): Int {
        var result = Objects.hash(displayWidth, blank)
        result = 31 * result + tiles.contentDeepHashCode()
        return result
    }

    //Метод, который возвращает лист со всеми возможными ходами
    private fun allValidMoves(): List<TilePos> {
        val out = ArrayList<TilePos>()
        for (dx in -1..1) {
            for (dy in -1..1) {
                val tp = TilePos(blank.x + dx, blank.y + dy)
                if (isValidMove(tp)) {
                    out.add(tp)
                }
            }
        }
        return out
    }

    //Метод, который проверяет является ли ход возможным
    private fun isValidMove(p: TilePos): Boolean {
        if (p.x < 0 || p.x >= SIZE) {
            return false
        }
        if (p.y < 0 || p.y >= SIZE) {
            return false
        }
        val dx = blank.x - p.x
        val dy = blank.y - p.y
        return !(abs(dx) + abs(dy) != 1 || dx * dy != 0)
    }

    //Метод, который отвечает за перемещение ячеек поля
    private fun move(p: TilePos) {
        if (!isValidMove(p)) {
            throw RuntimeException("Invalid move")
        }
        assert(tiles[blank.x][blank.y] == 0)
        tiles[blank.x][blank.y] = tiles[p.x][p.y]
        tiles[p.x][p.y] = 0
        blank = p
    }

    //Метод, который позволяет клонировать передвижение ячеек поля
    private fun moveClone(p: TilePos): FifteenPuzzle {
        val out = FifteenPuzzle(this)
        out.move(p)
        return out
    }

    //Метод, который перемешивает игровое поле
    private fun shuffle(howmany: Int) {
        for (i in 0 until howmany) {
            val possible = allValidMoves()
            val which = (Math.random() * possible.size).toInt()
            val move = possible[which]
            move(move)
        }
    }

    //Метод, который Возвращает количество чисел которые стоят не на своих местах
    private fun numberMisplacedTiles(): Int {
        var wrong = 0
        for (i in 0 until SIZE) {
            for (j in 0 until SIZE) {
                if (tiles[i][j] > 0 && tiles[i][j] != SOLVED.tiles[i][j]) {
                    wrong++
                }
            }
        }
        return wrong
    }

    //Метод, который проверяет закончено ли решение головоломки
    private val isSolved: Boolean
        get() = numberMisplacedTiles() == 0

    //Метод, который возвращает количество чисел которые стоят не на своих местах
    private fun estimateError(): Int {
        return numberMisplacedTiles()
    }

    //Мотод, который клонирует ходы из метода allValidMoves
    private fun allAdjacentPuzzles(): List<FifteenPuzzle> {
        val out = ArrayList<FifteenPuzzle>()
        for (move in allValidMoves()) {
            out.add(moveClone(move))
        }
        return out
    }

    //Метод, котрый решает головоломку при помощи алгоритма А*
    private fun aStarSolve(): List<FifteenPuzzle>? {
        val predecessor = HashMap<FifteenPuzzle, FifteenPuzzle?>()
        val depth = HashMap<FifteenPuzzle, Int>()
        val score = HashMap<FifteenPuzzle, Int>()
        val toVisit = PriorityQueue<FifteenPuzzle>(
            10000
        ) { a, b -> score[a]!! - score[b]!! }
        predecessor[this] = null
        depth[this] = 0
        score[this] = estimateError()
        toVisit.add(this)
        while (toVisit.size > 0) {
            val candidate = toVisit.remove()
            if (candidate.isSolved) {
                val solution = LinkedList<FifteenPuzzle>()
                var backtrace = candidate
                while (backtrace != null) {
                    solution.addFirst(backtrace)
                    backtrace = predecessor[backtrace]
                }
                return solution
            }
            for (fp in candidate.allAdjacentPuzzles()) {
                if (!predecessor.containsKey(fp)) {
                    predecessor[fp] = candidate
                    depth[fp] = depth[candidate]!! + 1
                    val estimate = fp.estimateError()
                    score[fp] = depth[candidate]!! + 1 + estimate
                    toVisit.add(fp)
                }
            }
        }
        return null
    }

    //Метод, который перемешивает наше поле, случайно от 0 до 30 раз
    fun solveShuffle() {
        this.shuffle((Math.random() + 30).toInt())
    }

    //Метод, который вызвает наше решение.
    fun solve(): List<FifteenPuzzle>? {
        return this.aStarSolve()
    }

    companion object {
        const val SIZE = 4
        val SOLVED = FifteenPuzzle()
    }

    //Блок инициализации поля игры
    init {
        var count = 1
        for (i in 0 until SIZE) {
            for (j in 0 until SIZE) {
                tiles[i][j] = count
                count++
            }
        }
        displayWidth = count.toString().length
        blank = TilePos(SIZE - 1, SIZE - 1)
        tiles[blank.x][blank.y] = 0
    }
}