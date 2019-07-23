package com.shimhg02.sunrin.shooter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

 class Missile(context: Context) {
     var x: Int = 0
     var y: Int = 0
     var mVelocity: Int = 0
     var missile: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.missile)


     init {
         x = GameView.screenWidth / 2 - getWidth() / 2
         y = GameView.screenHeight - GameView.spaceShipHeight - getHeight() / 2
         mVelocity = 50
     }

     fun getWidth(): Int {
         return missile.width
     }

     fun getHeight(): Int {
         return missile.height
     }
 }