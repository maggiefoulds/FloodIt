package uk.ac.bournemouth.ap.floodit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import uk.ac.bournemouth.ap.floodit.logic.FloodIt
import uk.ac.bournemouth.ap.floodit.logic.FlooditGame


class FloodItListener(private val expectedGame: FlooditGame,
    roundText: TextView, stateText: TextView
) :
    FlooditGame.GamePlayListener, FlooditGame.GameOverListener {

    private val roundText: TextView = roundText
    private val stateText: TextView = stateText

    override fun onGameChanged(game: FlooditGame, round: Int) {
        roundText.text = "Round: $round"
    }

    override fun onGameOver(game: FlooditGame, rounds: Int, isWon: Boolean) {
        if (isWon) {
            stateText.text = "State: Won"
        } else {
            stateText.text = "State: Lost"
        }
    }
}

class FloodItA : AppCompatActivity() {
    private lateinit var floodView: FloodView
    private lateinit var newGameButton: Button
    private lateinit var floodItGame: FloodIt
    private lateinit var roundText: TextView
    private lateinit var stateText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flood_it)
        floodView = findViewById<FloodView>(R.id.floodView)
        newGameButton = findViewById<Button>(R.id.newGameBtn)
        roundText = findViewById<TextView>(R.id.roundText)

        stateText = findViewById<TextView>(R.id.stateText)


        newGameButton.setOnClickListener {
            floodItGame = FloodIt(8, 25, 16, 16)
            roundText.text = "Round: 0"
            stateText.text = "State: Playing"

            val listener = FloodItListener(floodItGame, roundText, stateText)

            floodItGame.addGamePlayListener(listener)
            floodItGame.addGameOverListener(listener)

            floodView.setGame(floodItGame)
        }
    }
}