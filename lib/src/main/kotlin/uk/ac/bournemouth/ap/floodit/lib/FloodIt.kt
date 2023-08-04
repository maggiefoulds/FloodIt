package uk.ac.bournemouth.ap.floodit.logic

//The rules of the game are as follows:
//The game is played on a rectangular board with a given width and height.
//Each cell on the board can be one of a number of colours.
//The game starts with the top left cell being the "flow point" and all other cells being a random colour.
//The player can select a colour and all cells that are connected to the flow point by cells of the same colour will be changed to the selected colour.
//The game is won when all cells are the same colour.
//The game is lost when the player has used the maximum number of turns without winning.
//The game is played by calling the playColour method with the colour to play.
//The game can be queried for the current state, the number of turns used, the maximum number of turns, the width and height of the board, and the colour of a cell.


class FloodIt  (colourCount: Int, maxTurns: Int, width: Int, height: Int) : FlooditGame {
    override val maxTurns: Int = maxTurns
    override val width: Int = width
    override val height: Int = height
    override val colourCount: Int = colourCount
    override var round: Int = 0
    override var state: FlooditGame.State = FlooditGame.State.RUNNING
    private val board = Array(width) { IntArray(height) }

    private val gamePlayListeners: MutableList<FlooditGame.GamePlayListener> = mutableListOf()
    private val gameOverListeners: MutableList<FlooditGame.GameOverListener> = mutableListOf()

//    private val colours: IntMatrix = IntMatrix(width, height, 0)

    override fun get(x: Int, y: Int): Int {
        return board[x][y]
    }

    operator fun set(x: Int, y: Int, value: Int) {
        if (value < 0) {
            throw IllegalArgumentException("Colour cannot be negative")
        }

        /**
         * If the colourCount is lower than the actual maximum colour value this should throw an
         * exception.
         */
        if (value >= colourCount) {
            throw IllegalArgumentException("Colour cannot be higher than colourCount")
        }

        board[x][y] = value
    }

    private fun floodFill(x: Int, y: Int, oldColor: Int, newColor: Int) {
        if (board[x][y] != oldColor || board[x][y] == newColor) {
            return
        }

        board[x][y] = newColor

        if (x > 0) {
            floodFill(x - 1, y, oldColor, newColor)
        }

        if (x < width - 1) {
            floodFill(x + 1, y, oldColor, newColor)
        }

        if (y < height - 1) {
            floodFill(x, y + 1, oldColor, newColor)
        }

        if (y > 0) {
            floodFill(x, y - 1, oldColor, newColor)
        }
    }

    override fun playColour(clr: Int) {
        //increment the round
        this.round++

        //prevent negative colours
        if (clr < 0) {
            throw IllegalArgumentException("Colour cannot be negative")
        }

        //check if the game is still running
        if (this.state != FlooditGame.State.RUNNING) {
            throw IllegalStateException("Game is not running")
        }


        val oldClr = this.board[0][0]
        this.floodFill(0, 0, oldClr, clr)

        this.notifyMove(this.round)

        //check if the game has been won
        if (this.board.all { it.all { it == clr } } && this.round <= this.maxTurns ) {
            this.state = FlooditGame.State.WON
            this.notifyWin(this.round)

            return
        }

        //check if the game has been lost
        if (this.round >= this.maxTurns) {
            this.state = FlooditGame.State.LOST
            this.notifyWin(this.round)

            return
        }
    }

    override fun addGamePlayListener(listener: FlooditGame.GamePlayListener) {
        this.gamePlayListeners.add(listener)
    }

    override fun removeGamePlayListener(listener: FlooditGame.GamePlayListener) {
        this.gamePlayListeners.remove(listener)
    }

    override fun addGameOverListener(gameOverListener: FlooditGame.GameOverListener) {
        this.gameOverListeners.add(gameOverListener)
    }

    override fun removeGameOverListener(gameOverListener: FlooditGame.GameOverListener) {
        this.gameOverListeners.remove(gameOverListener)
    }

    override fun notifyMove(round: Int) {
        this.gamePlayListeners.forEach { it.onGameChanged(this, round) }
    }

    override fun notifyWin(round: Int) {
        val isWon = this.state == FlooditGame.State.WON
        this.gameOverListeners.forEach { it.onGameOver(
            this,
            this.round,
            isWon
        ) }
    }

    //prevent 0 and negative values for width and height
    //prevent negative color

    init {
        if (width <= 0 || height <= 0) {
            throw IllegalArgumentException("Width and height must be greater than 0")
        }

        if (colourCount <= 0) {
            throw IllegalArgumentException("Colour count must be greater than 0")
        }

        if (maxTurns <= 0) {
            throw IllegalArgumentException("Max turns must be greater than 0")
        }

        //randomize the board
        for (x in 0 until width) {
            for (y in 0 until height) {
                board[x][y] = (0 until colourCount).random()
            }
        }
    }
}