package com.shimhg02.sunrin.shooter

import android.app.Activity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager

class StartGameActivity : Activity() {
    lateinit var gameView : GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        this.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        gameView = GameView(this)
        setContentView(gameView)
    }
}