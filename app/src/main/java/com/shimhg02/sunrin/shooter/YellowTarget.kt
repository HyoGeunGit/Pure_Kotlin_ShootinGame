package com.shimhg02.sunrin.shooter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.util.*

open class YellowTarget(context: Context) {

    var random: Random
    var targetList = arrayOfNulls<Bitmap>(15)
    var x: Float = 0f
    var y: Float = 0f
    var velocity: Int = 0
    var targetFrame: Int = 0

    init {
        targetList[0] = BitmapFactory.decodeResource(context.resources, R.drawable.yellowtarget_1)
        targetList[1] = BitmapFactory.decodeResource(context.resources, R.drawable.yellowtarget_1)
        targetList[2] = BitmapFactory.decodeResource(context.resources, R.drawable.yellowtarget_1)
        targetList[3] = BitmapFactory.decodeResource(context.resources, R.drawable.yellowtarget_1)
        targetList[4] = BitmapFactory.decodeResource(context.resources, R.drawable.yellowtarget_1)
        targetList[5] = BitmapFactory.decodeResource(context.resources, R.drawable.yellowtarget_2)
        targetList[6] = BitmapFactory.decodeResource(context.resources, R.drawable.yellowtarget_2)
        targetList[7] = BitmapFactory.decodeResource(context.resources, R.drawable.yellowtarget_2)
        targetList[8] = BitmapFactory.decodeResource(context.resources, R.drawable.yellowtarget_2)
        targetList[9] = BitmapFactory.decodeResource(context.resources, R.drawable.yellowtarget_2)

        random = Random()
        resetPosition()
    }

    open fun getBitmap(): Bitmap {
        return targetList[targetFrame]!!
    }

    open fun getWidth(): Int {
        return targetList[0]!!.getWidth()
    }

    open fun getHeight(): Int {
        return targetList[0]!!.getHeight()
    }

    open fun resetPosition() {
        x = GameView.screenWidth + random.nextInt(1200).toFloat()
        y = random.nextInt(700).toFloat()
        velocity = 20
        targetFrame = 0

    }
}
