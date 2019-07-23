package com.shimhg02.sunrin.shooter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.media.AudioManager
import android.media.SoundPool
import android.os.Handler
import android.view.View
import java.util.*
import android.view.MotionEvent
import android.graphics.Bitmap

class GameView(context: Context) : View(context) {

    private var rect: Rect
    private var spaceShip: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.spaceship)

    private var yellowTargetList: ArrayList<YellowTarget>
    private var purpleTargetList: ArrayList<PurpleTarget>
    private var explosionList: ArrayList<Explosion>
    private var fireBombList: ArrayList<FireBomb>

    private var handlerObject: Handler
    private var runnable: Runnable

    private var missileList: ArrayList<Missile>
    private var contextObject: Context
    private var hitTargetCount = 0

    private var soundPool: SoundPool
    private var fireShotSoundId: Int = 0
    private var scorePaint: Paint
    private var healthPaint: Paint
    private var lives = 10

    private var spaceShipX :Float = 0f
    private var spaceShipY:Float = 0f

    var background :Bitmap

    companion object {
        private const val UPDATE_MILLI_SECONDS: Long = 30
        private const val TEXT_SIZE = 60
        var screenWidth: Int = 0
        var screenHeight: Int = 0
        @JvmStatic
        var spaceShipWidth: Int = 0
        @JvmStatic
        var spaceShipHeight: Int = 0
    }

    init {
        val display = (getContext() as Activity).windowManager.defaultDisplay
        val point = Point()
        display.getSize(point)
        screenWidth = point.x
        screenHeight = point.y
        rect = Rect(0, 0, screenWidth, screenHeight)

        yellowTargetList = ArrayList<YellowTarget>()
        purpleTargetList = ArrayList<PurpleTarget>()
        missileList = ArrayList<Missile>()
        explosionList = ArrayList<Explosion>()
        fireBombList = ArrayList<FireBomb>()

        this.contextObject = context

        for (i in 0..1) {
            yellowTargetList.add(YellowTarget(context))
            purpleTargetList.add(PurpleTarget(context))
            fireBombList.add(FireBomb(context))
        }

        handlerObject = Handler()
        runnable = object : Runnable {
            override fun run() {
                invalidate()
            }
        }

        spaceShipWidth = spaceShip.width
        spaceShipHeight = spaceShip.height

        soundPool = SoundPool(3, AudioManager.STREAM_MUSIC, 0)

        fireShotSoundId = soundPool.load(context, R.raw.fireshot, 1)
        scorePaint = Paint()
        scorePaint.color = Color.GREEN
        scorePaint.textSize = TEXT_SIZE.toFloat()
        scorePaint.textAlign = Paint.Align.LEFT
        healthPaint = Paint()
        healthPaint.color = Color.GREEN

        spaceShipX = (screenWidth / 2 - spaceShipWidth / 2).toFloat()
        spaceShipY = (screenHeight - spaceShipHeight).toFloat()

        background = BitmapFactory.decodeResource(resources, R.drawable.background_1)
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(background, null,rect, null)

        addAndMoveTargets(canvas)

        checkMissileTouchesTarget(canvas)

        checkExplodes(canvas)

        checkFireBombTouchesSpaceShip(canvas)

        canvas.drawBitmap(spaceShip, spaceShipX, spaceShipY, null)
        canvas.drawText("Score: " + (hitTargetCount * 10), 0f, TEXT_SIZE.toFloat(), scorePaint)
        canvas.drawRect((screenWidth - 130).toFloat(), 10f, (screenWidth - 130 + 10 * lives).toFloat(), TEXT_SIZE.toFloat(), healthPaint)
        handlerObject.postDelayed(runnable, UPDATE_MILLI_SECONDS)
    }


    private fun addAndMoveTargets(canvas: Canvas){
        for (i in 0..(yellowTargetList.size - 1)) {

            canvas.drawBitmap(yellowTargetList.get(i).getBitmap(), (yellowTargetList.get(i).x).toFloat(), (yellowTargetList.get(i).y).toFloat(), null)

            yellowTargetList.get(i).targetFrame++
            if (yellowTargetList.get(i).targetFrame > 9) {
                yellowTargetList.get(i).targetFrame = 0
            }
            yellowTargetList.get(i).x -= yellowTargetList.get(i).velocity
            if (yellowTargetList.get(i).x < -yellowTargetList.get(i).getWidth()) {  // targetList goes out of left edge
                yellowTargetList.get(i).resetPosition()
                lives--
                if (lives == 0) {
                    gameOver()
                }
            }

            canvas.drawBitmap(purpleTargetList.get(i).getBitmap(), (purpleTargetList.get(i).x).toFloat(), (purpleTargetList.get(i).y).toFloat(), null)
            purpleTargetList.get(i).targetFrame++

            if (purpleTargetList[i].targetFrame > 9) {
                purpleTargetList[i].targetFrame = 0

            }
            purpleTargetList[i].x += purpleTargetList[i].velocity
            if (purpleTargetList[i].x > screenWidth + purpleTargetList[i].getWidth()) {
                purpleTargetList[i].resetPosition()
                lives--
                if (lives == 0) {
                    gameOver()
                }
            }
        }


    }

    private fun checkMissileTouchesTarget(canvas: Canvas){
        missileList.add(Missile(context))

        for (i in 0..missileList.size - 1) {
            if (i < missileList.size && i % 7 == 0) {
                missileList.get(i).x = spaceShipX.toInt() + spaceShipWidth/2
                if ((missileList.get(i).y) > -(missileList[i].getHeight()).toFloat()) {// is inside  screen
                    missileList.get(i).y -= missileList[i].mVelocity
                    canvas.drawBitmap(missileList.get(i).missile, (missileList.get(i).x).toFloat(), (missileList.get(i).y).toFloat(), null)

                    if (missileList.get(i).x >= yellowTargetList.get(0).x && missileList.get(i).x + missileList.get(i).getWidth() <= yellowTargetList.get(0).x + yellowTargetList.get(0).getWidth() && missileList.get(i).y >= yellowTargetList.get(0).y &&
                            missileList.get(i).y <= yellowTargetList.get(0).y + yellowTargetList.get(0).getHeight()) {

                        val explosion = Explosion(context)
                        explosion.x = yellowTargetList.get(0).x + yellowTargetList.get(0).getWidth() / 2 - explosion.getWidth() / 2
                        explosion.y = yellowTargetList.get(0).y + yellowTargetList.get(0).getHeight() / 2 - explosion.getHeight() / 2
                        explosionList.add(explosion)

                        yellowTargetList[0].resetPosition()
                        hitTargetCount++
                        missileList.get(i).y = -100

                        if (fireShotSoundId != 0) {
                            soundPool.play(fireShotSoundId, 1f, 1f, 0, 0, 1f)
                        }

                    } else if (missileList.get(i).x >= yellowTargetList.get(1).x && missileList.get(i).x + missileList.get(i).getWidth() <= yellowTargetList.get(1).x + yellowTargetList[1].getWidth() && missileList.get(i).y >= yellowTargetList.get(1).y &&
                            missileList.get(i).y <= yellowTargetList[1].y + yellowTargetList[1].getHeight()) {

                        val explosion = Explosion(context)
                        explosion.x = yellowTargetList.get(1).x + yellowTargetList.get(1).getWidth() / 2 - explosion.getWidth() / 2
                        explosion.y = yellowTargetList.get(1).y + yellowTargetList.get(1).getHeight() / 2 - explosion.getHeight() / 2
                        explosionList.add(explosion)

                        yellowTargetList[1].resetPosition()
                        hitTargetCount++
                        missileList.get(i).y = -100
                        if (fireShotSoundId != 0) {
                            soundPool.play(fireShotSoundId, 1f, 1f, 0, 0, 1f)
                        }

                    } else if (missileList.get(i).x >= purpleTargetList.get(0).x && missileList.get(i).x + missileList.get(i).getWidth() <= purpleTargetList.get(0).x + purpleTargetList[0].getWidth() && missileList.get(i).y >= purpleTargetList.get(0).y &&
                            missileList.get(i).y <= purpleTargetList.get(0).y + purpleTargetList.get(0).getHeight()) {

                        val explosion = Explosion(context)
                        explosion.x = purpleTargetList.get(0).x + purpleTargetList.get(0).getWidth() / 2 - explosion.getWidth() / 2
                        explosion.y = purpleTargetList.get(0).y + purpleTargetList.get(0).getHeight() / 2 - explosion.getHeight() / 2
                        explosionList.add(explosion)

                        purpleTargetList[0].resetPosition()
                        hitTargetCount++
                        missileList.get(i).y = -100
                        if (fireShotSoundId != 0) {
                            soundPool.play(fireShotSoundId, 1f, 1f, 0, 0, 1f)
                        }

                    } else if (missileList.get(i).x >= purpleTargetList[1].x && missileList.get(i).x + missileList.get(i).getWidth() <= purpleTargetList[1].x + purpleTargetList[1].getWidth() && missileList.get(i).y >= purpleTargetList[1].y &&
                            missileList.get(i).y <= purpleTargetList[1].y + purpleTargetList[1].getHeight()) {

                        val explosion = Explosion(context)
                        explosion.x = purpleTargetList.get(1).x + purpleTargetList.get(1).getWidth() / 2 - explosion.getWidth() / 2
                        explosion.y = purpleTargetList.get(1).y + purpleTargetList.get(1).getHeight() / 2 - explosion.getHeight() / 2
                        explosionList.add(explosion)

                        purpleTargetList[1].resetPosition()
                        hitTargetCount++
                        missileList.get(i).y = -100
                        if (fireShotSoundId != 0) {
                            soundPool.play(fireShotSoundId, 1f, 1f, 0, 0, 1f)
                        }
                    }
                }
            }
        }

        if (missileList.size == 5000) {
            missileList.removeAll(missileList)
        }
    }

    private fun checkExplodes(canvas: Canvas){
        for (j in 0..explosionList.size - 1) {
            if (j < explosionList.size) {
                canvas.drawBitmap(explosionList[j].getExplosion(explosionList[j].explosionFrame), explosionList[j].x,
                        explosionList[j].y, null)
                explosionList[j].explosionFrame++
                if (explosionList[j].explosionFrame > 6) {
                    explosionList.removeAt(j)
                }
            }
        }
    }

    private fun checkFireBombTouchesSpaceShip(canvas: Canvas){
        for (i in 0..2) {
            if (i < fireBombList.size && i % 10 == 0) {
                canvas.drawBitmap(fireBombList.get(i).getBitmap(), (fireBombList.get(i).x).toFloat(), (fireBombList.get(i).y).toFloat(), null)
                fireBombList.get(i).y += fireBombList.get(i).velocity

                if (fireBombList.get(i).x + fireBombList.get(i).getWidth() >= spaceShipX && spaceShipX + spaceShipWidth >= fireBombList.get(i).x &&
                        fireBombList.get(i).y >= spaceShipY && fireBombList.get(i).y <= spaceShipY + spaceShipHeight) {
                    missileList.removeAll(missileList)
                    gameOver()

                }
                if (fireBombList.get(i).y > screenHeight) {
                    fireBombList.get(i).resetPosition()
                }
            }
        }
    }

    private fun gameOver(){
        val intent = Intent(context, GameOverActivity::class.java)
        intent.putExtra("score", hitTargetCount * 10)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        context.startActivity(intent)
        (context as Activity).finish()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (event.x < screenWidth - spaceShipX/5) {
                    spaceShipX = event.x
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (event.x < screenWidth - spaceShipX/5) {
                    spaceShipX = event.x
                }
            }
        }
        return true
    }
}


