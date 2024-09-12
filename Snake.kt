import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.util.*
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.Timer

class SnakeGame : JPanel() {

    private val width = 400
    private val height = 400
    private val dotSize = 10
    private val allDots = 900
    private val randPos = 29
    private val delay = 140

    private val x = IntArray(allDots)
    private val y = IntArray(allDots)

    private var dots = 3
    private var appleX = 0
    private var appleY = 0

    private var leftDirection = false
    private var rightDirection = true
    private var upDirection = false
    private var downDirection = false
    private var inGame = true

    private val timer: Timer

    init {
        preferredSize = Dimension(width, height)
        background = Color.black
        isFocusable = true
        addKeyListener(MyKeyAdapter())
        initGame()
        timer = Timer(delay) { step() }
        timer.start()
    }

    private fun initGame() {
        dots = 3

        for (z in 0 until dots) {
            x[z] = 50 - z * dotSize
            y[z] = 50
        }

        locateApple()
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        if (inGame) {
            g.color = Color.red
            g.fillRect(appleX, appleY, dotSize, dotSize)

            for (z in 0 until dots) {
                if (z == 0) {
                    g.color = Color.green
                } else {
                    g.color = Color.darkGray
                }
                g.fillRect(x[z], y[z], dotSize, dotSize)
            }
        } else {
            gameOver(g)
        }
    }

    private fun gameOver(g: Graphics) {
        val msg = "Game Over"
        val small = java.awt.Font("Helvetica", java.awt.Font.BOLD, 14)
        val metrics = getFontMetrics(small)

        g.color = Color.white
        g.font = small
        g.drawString(msg, (width - metrics.stringWidth(msg)) / 2, height / 2)
    }

    private fun checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++
            locateApple()
        }
    }

    private fun move() {
        for (z in dots downTo 1) {
            x[z] = x[z - 1]
            y[z] = y[z - 1]
        }

        if (leftDirection) {
            x[0] -= dotSize
        }

        if (rightDirection) {
            x[0] += dotSize
        }

        if (upDirection) {
            y[0] -= dotSize
        }

        if (downDirection) {
            y[0] += dotSize
        }
    }

    private fun checkCollision() {
        for (z in dots downTo 1) {
            if (z > 4 && x[0] == x[z] && y[0] == y[z]) {
                inGame = false
            }
        }

        if (y[0] >= height) {
            inGame = false
        }

        if (y[0] < 0) {
            inGame = false
        }

        if (x[0] >= width) {
            inGame = false
        }

        if (x[0] < 0) {
            inGame = false
        }

        if (!inGame) {
            timer.stop()
        }
    }

    private fun locateApple() {
        val rand = Random()
        val r = rand.nextInt(randPos)
        appleX = r * dotSize
        val r2 = rand.nextInt(randPos)
        appleY = r2 * dotSize
    }

    private fun step() {
        if (inGame) {
            checkApple()
            checkCollision()
            move()
        }

        repaint()
    }

    inner class MyKeyAdapter : KeyAdapter() {
        override fun keyPressed(e: KeyEvent) {
            val key = e.keyCode

            if (key == KeyEvent.VK_LEFT && !rightDirection) {
                leftDirection = true
                upDirection = false
                downDirection = false
            }

            if (key == KeyEvent.VK_RIGHT && !leftDirection) {
                rightDirection = true
                upDirection = false
                downDirection = false
            }

            if (key == KeyEvent.VK_UP && !downDirection) {
                upDirection = true
                rightDirection = false
                leftDirection = false
            }

            if (key == KeyEvent.VK_DOWN && !upDirection) {
                downDirection = true
                rightDirection = false
                leftDirection = false
            }
        }
    }
}

fun main() {
    val frame = JFrame("Snake Game")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.contentPane.add(SnakeGame())
    frame.pack()
    frame.isVisible = true
}