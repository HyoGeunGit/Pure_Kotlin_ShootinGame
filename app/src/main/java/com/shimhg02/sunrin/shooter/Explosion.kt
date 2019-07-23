package com.shimhg02.sunrin.shooter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory


class Explosion(context: Context) {

    internal var explosion = arrayOfNulls<Bitmap>(7)
    internal var explosionFrame = 0
    internal var x: Float = 0f
    internal var y: Float = 0f

    init {
        explosion[0] = BitmapFactory.decodeResource(context.resources, R.drawable.explosion0)
        explosion[1] = BitmapFactory.decodeResource(context.resources, R.drawable.explosion1)
        explosion[2] = BitmapFactory.decodeResource(context.resources, R.drawable.explosion2)
        explosion[3] = BitmapFactory.decodeResource(context.resources, R.drawable.explosion3)
        explosion[4] = BitmapFactory.decodeResource(context.resources, R.drawable.explosion4)
        explosion[5] = BitmapFactory.decodeResource(context.resources, R.drawable.explosion5)
        explosion[6] = BitmapFactory.decodeResource(context.resources, R.drawable.explosion6)
    }

    fun getExplosion(explosionFrame: Int): Bitmap {
        return explosion[explosionFrame]!!
    }

    fun getWidth(): Int {
        return explosion[0]!!.width
    }

    fun getHeight(): Int {
        return explosion[0]!!.height
    }
}