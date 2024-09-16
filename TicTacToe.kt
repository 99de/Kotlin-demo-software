import kotlin.system.exitProcess

class TicTacToe {
    private val board: Array<CharArray> = Array(3) { CharArray(3) { ' ' } }
    private var currentPlayer: Char = 'X'

    fun printBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                print("${board[i][j]}")
                if (j < 2) print(" | ")
            }
            println()
            if (i < 2) println("---------")
        }
    }

    fun makeMove(row: Int, col: Int): Boolean {
        if (row !in 0..2 || col !in 0..2 || board[row][col] != ' ') {
            return false
        }
        board[row][col] = currentPlayer
        currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
        return true
    }

    fun checkWin(): Boolean {
        // Check rows, columns, and diagonals
        for (i in 0..2) {
            if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) return true
            if (board[0][i] != ' ' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) return true
        }
        if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) return true
        if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) return true
        return false
    }

    fun checkDraw(): Boolean {
        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j] == ' ') return false
            }
        }
        return true
    }

    fun getCurrentPlayer(): Char {
        return currentPlayer
    }
}

fun main() {
    val game = TicTacToe()
    var gameOver = false

    while (!gameOver) {
        game.printBoard()
        println("Player ${game.getCurrentPlayer()}, enter your move (row and column): ")
        val input = readLine()
        if (input == null || input.split(" ").size != 2) {
            println("Invalid input. Please enter row and column separated by a space.")
            continue
        }

        val (row, col) = input.split(" ").map { it.toIntOrNull() }
        if (row == null || col == null) {
            println("Invalid input. Please enter valid numbers.")
            continue
        }

        if (!game.makeMove(row - 1, col - 1)) {
            println("Invalid move. Please try again.")
            continue
        }

        if (game.checkWin()) {
            game.printBoard()
            println("Player ${if (game.getCurrentPlayer() == 'X') 'O' else 'X'} wins!")
            gameOver = true
        } else if (game.checkDraw()) {
            game.printBoard()
            println("It's a draw!")
            gameOver = true
        }
    }
}