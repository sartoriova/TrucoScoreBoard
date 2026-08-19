package br.edu.ifsp.scl.sc3047822.trucoscoreboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.sc3047822.trucoscoreboard.databinding.ActivityMainBinding

enum class ScoreAction { PLUS_ONE, TRUCO }

const val MAX_SCORE = 12

class MainActivity : AppCompatActivity() {

    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var teamAPointing: Int = 0
    private var teamBPointing: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        setupListeners()
        updateUi()
    }

    private fun setupListeners() {
        with(activityMainBinding) {
            teamAPlusOneBt.setOnClickListener {
                teamAPointing = calculateNewScore(teamAPointing, ScoreAction.PLUS_ONE)
                updateUi()
            }
            teamATrucoBt.setOnClickListener {
                teamAPointing = calculateNewScore(teamAPointing, ScoreAction.TRUCO)
                updateUi()
            }

            teamBPlusOneBt.setOnClickListener {
                teamBPointing = calculateNewScore(teamBPointing, ScoreAction.PLUS_ONE)
                updateUi()
            }
            teamBTrucoBt.setOnClickListener {
                teamBPointing = calculateNewScore(teamBPointing, ScoreAction.TRUCO)
                updateUi()
            }

            restartBt.setOnClickListener {
                teamAPointing = 0
                teamBPointing = 0
                updateUi()
            }
        }
    }

    private fun calculateNewScore(currentValue: Int, action: ScoreAction): Int {
        return when {
            action == ScoreAction.PLUS_ONE -> currentValue + 1
            currentValue > 9 -> currentValue + 2
            else -> currentValue + 3
        }
    }

    private fun updateUi() {
        val gameIsOver = teamAPointing == MAX_SCORE || teamBPointing == MAX_SCORE

        with(activityMainBinding) {
            teamAScoreTv.text = teamAPointing.toString()
            teamBScoreTv.text = teamBPointing.toString()

            teamAChampionTv.alpha = if (teamAPointing == MAX_SCORE) 1f else 0f
            teamBChampionTv.alpha = if (teamBPointing == MAX_SCORE) 1f else 0f

            teamAPlusOneBt.isEnabled = !gameIsOver && teamAPointing < MAX_SCORE
            teamATrucoBt.isEnabled = !gameIsOver && teamAPointing < 11

            teamBPlusOneBt.isEnabled = !gameIsOver && teamBPointing < MAX_SCORE
            teamBTrucoBt.isEnabled = !gameIsOver && teamBPointing < 11
        }
    }
}