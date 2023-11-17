package me.nathanfallet.connect4.models

object ArtificialIntelligence {

    private const val MAX_DEPTH = 6

    private fun utility(victory: Player?, isMe: Boolean): Double {
        return victory?.let { if (isMe) -1.0 else 1.0 } ?: 0.0
    }

    private fun minimaxValue(
        board: Board,
        initialTurn: Player,
        turn: Int,
        parentAlpha: Double,
        parentBeta: Double,
        isMax: Boolean
    ): Pair<Double, Int?> {
        // Local variables
        var alpha = parentAlpha
        var beta = parentBeta

        // Terminal state
        val moves = board.possibleMoves
        val victory = board.winner
        if (victory != null || moves.isEmpty() || turn > MAX_DEPTH) {
            return Pair(utility(victory, turn % 2 == 0), null)
        }

        // Normal iteration
        var v = if (isMax) Double.NEGATIVE_INFINITY else Double.POSITIVE_INFINITY
        var currentMove: Int? = null
        moves.forEach { move ->
            val newBoard = board.clone()
            newBoard.play(move, if (isMax) initialTurn else initialTurn.opponent)
            val (newV, _) = minimaxValue(newBoard, initialTurn, turn + 1, alpha, beta, !isMax)
            if ((isMax && newV > v) || (!isMax && newV < v) || currentMove == null || (newV == v && Math.random() < 0.4)) {
                v = newV
                currentMove = move
            }

            // Alpha-Beta magic
            if ((isMax && v >= beta) || (!isMax && v <= alpha)) {
                return Pair(v, currentMove)
            }
            if (isMax) alpha = maxOf(alpha, v)
            else beta = minOf(beta, v)
        }
        return Pair(v, currentMove)
    }

    fun move(board: Board, player: Player): Int {
        return minimaxValue(board, player, 0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true).second
            ?: board.possibleMoves.random() // This random case should never happen
    }

}
