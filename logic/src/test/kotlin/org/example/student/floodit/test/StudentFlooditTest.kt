package org.example.student.floodit.test

import uk.ac.bournemouth.ap.floodit.logic.FloodIt

import uk.ac.bournemouth.ap.floodit.logic.FlooditGame
import uk.ac.bournemouth.ap.floodit.test.FlooditTest
import uk.ac.bournemouth.ap.lib.matrix.int.IntMatrix
import kotlin.random.Random

/**
 * Class that drives the testing of the game implementation. The implementation of the tests is
 * provided in [FlooditTest].
 */
class StudentFlooditTest : FlooditTest() {

    /**
     * Factory function to create a game with the given colours, colour count and maxTurns.
     *
     * @param colours The matrix with colours to initialize the game with.
     * @param colourCount The amount of colours that are valid in a game.
     * @param maxTurns The maximum amount of turns possible before a loss is recorded if not won by
     *                 then.
     */
    override fun createGameFromGivenData(colours: IntMatrix, colourCount: Int, maxTurns:Int): FlooditGame {
        var game = FloodIt(colourCount, maxTurns, colours.width, colours.height)
        for (y in 0 until colours.height) {
            for (x in 0 until colours.width) {
                game[x, y] = colours[x, y]
            }
        }

        return game;
    }

    /**
     * Factory function to create a **random** game with the given dimensions, colour count and
     * maxTurns and random object (for repeatable generation).
     *
     * @param width The width of the game
     * @param height The height of the game
     * @param colourCount The amount of colours that are valid in a game.
     * @param maxTurns The maximum amount of turns possible before a loss is recorded if not won by
     *                 then.
     */
    override fun createRandomGame(width: Int, height: Int, colourCount: Int, maxTurns: Int, random: Random): FlooditGame {
        var game = FloodIt(colourCount, maxTurns, width, height)
        for (y in 0 until height) {
            for (x in 0 until width) {
                game[x, y] = random.nextInt(colourCount)
            }
        }

        return game;
    }
}
