package com.shimhg02.sunrin.shooter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class PurpleTarget(context: Context) : YellowTarget(context) {

    var targets = arrayOfNulls<Bitmap>(10)

    init {
        targets[0] = BitmapFactory.decodeResource(context.resources, R.drawable.purpletarget_1)
        targets[1] = BitmapFactory.decodeResource(context.resources, R.drawable.purpletarget_1)
        targets[2] = BitmapFactory.decodeResource(context.resources, R.drawable.purpletarget_1)
        targets[3] = BitmapFactory.decodeResource(context.resources, R.drawable.purpletarget_1)
        targets[4] = BitmapFactory.decodeResource(context.resources, R.drawable.purpletarget_1)
        targets[5] = BitmapFactory.decodeResource(context.resources, R.drawable.purpletarget_2)
        targets[6] = BitmapFactory.decodeResource(context.resources, R.drawable.purpletarget_2)
        targets[7] = BitmapFactory.decodeResource(context.resources, R.drawable.purpletarget_2)
        targets[8] = BitmapFactory.decodeResource(context.resources, R.drawable.purpletarget_2)
        targets[9] = BitmapFactory.decodeResource(context.resources, R.drawable.purpletarget_2)
        resetPosition()
    }


    override fun getBitmap(): Bitmap {
        return targets[targetFrame]!!
    }

    override fun getWidth(): Int {
        return targets[0]!!.getWidth()
    }

    override fun getHeight(): Int {
        return targets[0]!!.getHeight()
    }

    override fun resetPosition() {
        x = -(200 + random.nextInt(1500)).toFloat()
        y = random.nextInt(800).toFloat()
        velocity = 20

    }
}