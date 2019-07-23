package com.shimhg02.sunrin.shooter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.util.*

class FireBomb(context: Context) {

    var random: Random
    var fireBomb = arrayOfNulls<Bitmap>(1)
    var x: Int = 0
    var y: Int = 0
    var velocity: Int = 0


    init {
        fireBomb[0] = BitmapFactory.decodeResource(context.resources, R.drawable.firebomb)
        random = Random()
        resetPosition()
    }

    fun getBitmap(): Bitmap {
        return fireBomb[0]!!
    }

    fun getWidth(): Int {
        return fireBomb[0]!!.getWidth()
    }

    fun resetPosition() {
        x =  0 + random.nextInt(GameView.screenWidth - 100)
        y =  100  + random.nextInt(GameView.screenHeight - (GameView.spaceShipHeight * 1.5).toInt())
        velocity = 20
    }
}