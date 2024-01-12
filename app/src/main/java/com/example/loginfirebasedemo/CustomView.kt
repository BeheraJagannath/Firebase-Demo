package com.example.loginfirebasedemo

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class CustomView : View {
    var rect: Rect? = null
    var paint: Paint? = null

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    private fun init(set: AttributeSet?) {
        rect = Rect()
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint!!.color = Color.GREEN
        paint!!.textSize = 50f
        paint!!.setTextAlign(Paint.Align.CENTER);
    }


    fun swapColor() {
        paint!!.color = if (paint!!.color == Color.GREEN) Color.RED else Color.TRANSPARENT
        postInvalidate()

    }

    override fun onDraw(canvas: Canvas) {
       val radius = 50
        paint!!.style = Paint.Style.FILL
        canvas.drawCircle(150f, 150f, 50f, paint!!)
        canvas.drawCircle(300f, 300f, 100f, paint!!)
//        canvas.drawRoundRect(RectF(20f, 20f, 100f, 100f), 20f, 20f, paint!!)
//        canvas.rotate(-45f)
        canvas.drawText("TutorialRide", 50f, 50f, paint!!)

//        rect!!.left = 50
//        rect!!.right = rect!!.left + SQUARE_SIZE
//        rect!!.bottom = rect!!.top + SQUARE_SIZE
//        rect!!.top = 50
//        canvas.drawRect(rect!!, paint!!)
    }


    companion object {
        private const val SQUARE_SIZE = 200
    }
}