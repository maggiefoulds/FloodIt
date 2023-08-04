package uk.ac.bournemouth.ap.floodit

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import uk.ac.bournemouth.ap.floodit.logic.FloodIt
import kotlin.math.min

class FloodView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val scaleFactor = 1.0f
    private var originX = 20f
    private var originY = 200f
    private var cellSize = 130f

    var cols = 16
    var rows = 16

    private var colors = intArrayOf(
        Color.RED,
        Color.GREEN,
        Color.BLUE,
        Color.YELLOW,
        Color.CYAN,
        Color.MAGENTA,
        Color.BLACK,
        Color.WHITE,
    )

    private var floodItGame: FloodIt? = null

    private val lightColor = Color.parseColor("#EEEEEE")
    private val darkColor = Color.parseColor("#BBBBBB")


    private val paint = Paint()

    fun setGame(game: FloodIt) {
        this.floodItGame = game
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val smaller = min(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(smaller, smaller)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas ?: return

        //if no floodit game exit
        val floodItGame = this.floodItGame ?: return

        val floodItBoardSize = min(width, height) * scaleFactor

        cellSize = floodItBoardSize / cols

        originX = (width - floodItBoardSize) / 2f
        originY = (height - floodItBoardSize) / 2f

        drawFloodItBoard(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val floodItGame = this.floodItGame ?: return false

                val col = ((event.x - originX) / cellSize).toInt()
                val row = ((event.y - originY) / cellSize).toInt()

                if (col in 0 until this.cols && row in 0 until this.rows) {
                    floodItGame.playColour(floodItGame.get(col, row))
                    invalidate()
                }
            }

        }
        return true
    }


    private fun drawFloodItBoard(canvas: Canvas) {
        for (row in 0 until this.cols)
            for (col in 0 until this.rows) {
                val floodItGame = this.floodItGame ?: return
                val squareAt = floodItGame.get(col, row)
                drawSquareAt(canvas, col, row, squareAt)
            }
    }

    private fun drawSquareAt(canvas: Canvas, col: Int, row: Int, colorIndex: Int) {
        paint.color = this.colors[colorIndex]

        canvas.drawRect(originX + col * cellSize, originY + row * cellSize, originX + (col + 1)* cellSize, originY + (row + 1) * cellSize, paint)
    }
}