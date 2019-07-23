package com.shimhg02.sunrin.shooter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView

class GameOverActivity : Activity() {

    private var txtScore: TextView? = null
    private var txtHighestScore: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_over)
        fillScore()
    }

    private fun fillScore(){
        val mScore = intent.extras!!.getInt("score")
        val msharedPreference = getSharedPreferences("ScoreInformation", 0)
        var mHighestScore = msharedPreference.getInt("HighestScore", 0)
        val editor = msharedPreference.edit()
        if (mScore > mHighestScore) {
            mHighestScore = mScore
            editor.putInt("HighestScore", mHighestScore)
            editor.apply()
        }
        txtScore = findViewById(R.id.txtScore)
        txtHighestScore = findViewById(R.id.txtHighestScore)
        txtScore!!.text = " " + mScore
        txtHighestScore!!.text = "" + mHighestScore
    }

    fun onRestartButtonClick(view: View) {
        val intent = Intent(this@GameOverActivity, StartGameActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    fun onExitButtonClick(view: View) {
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}