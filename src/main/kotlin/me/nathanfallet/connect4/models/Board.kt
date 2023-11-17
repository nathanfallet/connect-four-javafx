package me.nathanfallet.connect4.models

class Board private constructor(
    private val grid: Array<Array<Player?>>
) {

    constructor() : this(Array(7) { Array(6) { null } })

    val possibleMoves: List<Int>
        get() = grid.mapIndexedNotNull { index, value ->
            index.takeIf { value[5] == null }
        }

    val winner: Player?
        get() {
            // Horizontal alignment check
            for (line in 0 until 6) {
                for (column in 0 until 4) {
                    if (grid[column][line] == grid[column + 1][line] &&
                        grid[column + 1][line] == grid[column + 2][line] &&
                        grid[column + 2][line] == grid[column + 3][line] &&
                        grid[column][line] != null
                    ) {
                        return grid[column][line]
                    }
                }
            }
            // Vertical alignment check
            for (column in 0 until 7) {
                for (line in 0 until 3) {
                    if (grid[column][line] == grid[column][line + 1] &&
                        grid[column][line + 1] == grid[column][line + 2] &&
                        grid[column][line + 2] == grid[column][line + 3] &&
                        grid[column][line] != null
                    ) {
                        return grid[column][line]
                    }
                }
            }
            // Diagonal alignment check
            for (column in 0 until 4) {
                for (line in 0 until 3) {
                    if (grid[column][line] == grid[column + 1][line + 1] &&
                        grid[column + 1][line + 1] == grid[column + 2][line + 2] &&
                        grid[column + 2][line + 2] == grid[column + 3][line + 3] &&
                        grid[column][line] != null
                    ) {
                        return grid[column][line]
                    } else if (grid[column][5 - line] == grid[column + 1][4 - line] &&
                        grid[column + 1][4 - line] == grid[column + 2][3 - line] &&
                        grid[column + 2][3 - line] == grid[column + 3][2 - line] &&
                        grid[column][5 - line] != null
                    ) {
                        return grid[column][5 - line]
                    }
                }
            }
            // No winner
            return null
        }

    fun play(column: Int, player: Player): Int? {
        var line = 0
        while (line < 6 && grid[column][line] != null) {
            line++
        }
        if (line < 6) {
            grid[column][line] = player
            return line
        }
        return null
    }

    fun get(column: Int, line: Int): Player? {
        return grid[column][line]
    }

    fun reset() {
        for (column in 0 until 7) {
            for (line in 0 until 6) {
                grid[column][line] = null
            }
        }
    }

    fun clone(): Board {
        return Board(grid.map { it.clone() }.toTypedArray())
    }

}
