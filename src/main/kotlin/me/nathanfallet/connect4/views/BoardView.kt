package me.nathanfallet.connect4.views

import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.event.EventHandler
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Rectangle
import javafx.util.Duration
import me.nathanfallet.connect4.models.Board
import me.nathanfallet.connect4.models.Player

class BoardView : Pane() {

    companion object {
        val backgroundColor: Color = Color.rgb(5, 103, 173)
        val blankColor: Color = Color.rgb(27, 59, 104)
        val redColor: Color = Color.rgb(214, 25, 43)
        val yellowColor: Color = Color.rgb(241, 190, 24)
    }

    private val boxes = Array(7) { Array(6) { Pane() } }
    private val animatedBox = Circle()

    private var isAnimating = false

    var onPlay: ((Int) -> Unit)? = null

    init {
        boxes.forEachIndexed { index, column ->
            val vbox = VBox()
            vbox.layoutX = index * 96.0
            column.forEach { box ->
                vbox.children.add(box)
                val rectangle = Rectangle()
                rectangle.width = 96.0
                rectangle.height = 96.0
                rectangle.fill = backgroundColor
                box.children.add(rectangle)
                val circle = Circle()
                circle.radius = 40.0
                circle.centerX = 48.0
                circle.centerY = 48.0
                circle.fill = blankColor
                box.children.add(circle)
            }
            vbox.onMouseClicked = EventHandler {
                if (isAnimating) return@EventHandler
                onPlay?.invoke(index)
            }
            children.add(vbox)
        }
        animatedBox.isVisible = false
        animatedBox.radius = 40.0
        children.add(animatedBox)
    }

    fun animatePlay(column: Int, line: Int, player: Player, onFinished: (() -> Unit)) {
        isAnimating = true
        animatedBox.centerX = 48.0 + column * 96.0
        animatedBox.centerY = -48.0
        animatedBox.fill = when (player) {
            Player.RED -> redColor
            Player.YELLOW -> yellowColor
        }
        animatedBox.isVisible = true
        val timeline = Timeline()
        timeline.keyFrames.addAll(
            KeyFrame(
                Duration.seconds(0.5 + 0.1 * (5 - line)),
                KeyValue(animatedBox.centerYProperty(), 48.0 + (5 - line) * 96.0)
            )
        )
        timeline.onFinished = EventHandler {
            animatedBox.isVisible = false
            isAnimating = false
            onFinished()
        }
        timeline.play()
    }

    fun update(board: Board) {
        boxes.forEachIndexed { index, panes ->
            panes.forEachIndexed { index2, pane ->
                val circle = pane.children[1] as Circle
                circle.fill = when (board.get(index, 5 - index2)) {
                    Player.RED -> redColor
                    Player.YELLOW -> yellowColor
                    else -> blankColor
                }
            }
        }
    }

}
