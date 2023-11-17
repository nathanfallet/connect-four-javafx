package me.nathanfallet.connect4.controllers

import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.shape.Rectangle
import me.nathanfallet.connect4.models.ArtificialIntelligence
import me.nathanfallet.connect4.models.Board
import me.nathanfallet.connect4.models.Player
import me.nathanfallet.connect4.views.BoardView

class MainController {

    companion object {
        val humanPlayer = Player.RED
        val computerPlayer = humanPlayer.opponent
    }

    @FXML
    var boardView: BoardView? = null

    @FXML
    var playerLabel: Label? = null

    @FXML
    var newGameButton: Button? = null

    private val board = Board()
    private var player = SimpleObjectProperty<Player?>(null)
    private var winner = SimpleObjectProperty<Player?>(null)
    private var draw = SimpleBooleanProperty(false)

    fun initialize() {
        setClipping()
        boardView?.onPlay = this::onPlay
        playerLabel?.textProperty()?.bind(Bindings.createStringBinding({
            when {
                winner.value != null -> "Winner: ${winner.value!!.name}"
                draw.value -> "Draw!"
                player.value != null -> "Player: ${player.value!!.name}"
                else -> "Game not started"
            }
        }, player, winner, draw))
    }

    private fun setClipping() {
        val clip = Rectangle()
        boardView?.clip = clip
        boardView?.layoutBoundsProperty()?.addListener { _, _, bounds ->
            clip.width = bounds.width
            clip.height = bounds.height
        }
    }

    fun newGame() {
        board.reset()
        boardView?.update(board)
        player.value = Player.RED
        winner.value = null
        draw.value = false
    }

    private fun onPlay(column: Int) {
        if (player.value != humanPlayer) return
        play(column)
    }

    private fun play(column: Int) {
        if (player.value == null || winner.value != null || draw.value) return

        val line = board.play(column, player.value!!) ?: return
        boardView?.animatePlay(column, line, player.value!!) {
            boardView?.update(board)
            board.winner?.let {
                winner.value = it
                return@animatePlay
            }
            if (board.possibleMoves.isEmpty()) {
                draw.value = true
                return@animatePlay
            }
            player.value = player.value!!.opponent

            if (player.value == computerPlayer) {
                play(ArtificialIntelligence.move(board, player.value!!))
            }
        }
    }

}
