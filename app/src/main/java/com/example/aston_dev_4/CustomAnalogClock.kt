package com.example.aston_dev_4

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.*
import kotlin.math.*

class CustomAnalogClock @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mHeight = 0F
    private var mWidth = 0F
    private var mPadding = 20F
    private var mRadius = 0F
    private var hoursRange = 1..12
    private var circlePaint = Paint()
    private var marksPaint = Paint()
    private var minutePaint = Paint()
    private var hourPaint = Paint()
    private var secondPaint = Paint()


    init {
        with(circlePaint) {
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeWidth = 12f
            isAntiAlias = true
        }

        with(marksPaint) {
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeWidth = 18f
            isAntiAlias = true
        }

        with(minutePaint) {
            color = Color.RED
            style = Paint.Style.STROKE
            strokeWidth = 12f
            isAntiAlias = true
        }

        with(hourPaint) {
            color = Color.BLUE
            style = Paint.Style.STROKE
            strokeWidth = 10f
            isAntiAlias = true
        }

        with(secondPaint) {
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeWidth = 18f
            isAntiAlias = true
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec).toFloat()
        mHeight = MeasureSpec.getSize(heightMeasureSpec).toFloat()

        mRadius = min(mWidth, mHeight) / 2 - mPadding

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawClockFace(canvas)
        drawHands(canvas)
        postInvalidateDelayed(100)
    }

    private fun drawClockFace(canvas: Canvas) {
        // Границы циферблата
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, circlePaint)

        // Метки часов на циферблате
        for (hour in hoursRange) {
            val angle = PI / 6 * hour

            canvas.drawLine(
                // Две точки ближе к центру
                (width / 2 + cos(angle) * mRadius * 0.85).toFloat(),
                (height / 2 + sin(angle) * mRadius * 0.85).toFloat(),
                // Две точки ближе к границам циферблата
                (width / 2 + cos(angle) * mRadius).toFloat(),
                (height / 2 + sin(angle) * mRadius).toFloat(),
                marksPaint
            )
        }
    }

    private fun drawHands(canvas: Canvas) {
        val calendar = Calendar.getInstance()

        // seconds
        val angleSeconds = PI * calendar.get(Calendar.SECOND) / 30 - PI / 2

        canvas.drawLine(
            (width / 2f - cos(angleSeconds) * mRadius * 0.4).toFloat(),
            (height / 2f - sin(angleSeconds) * mRadius * 0.4).toFloat(),
            (width / 2f + cos(angleSeconds) * mRadius * 0.7).toFloat(),
            (height / 2f + sin(angleSeconds) * mRadius * 0.7).toFloat(),
            secondPaint
        )

        // minutes
        val angleMinutes = PI * calendar.get(Calendar.MINUTE) / 30 - PI / 2

        canvas.drawLine(
            (width / 2f - cos(angleMinutes) * mRadius * 0.2).toFloat(),
            (height / 2f - sin(angleMinutes) * mRadius * 0.2).toFloat(),
            (width / 2f + cos(angleMinutes) * mRadius * 0.5).toFloat(),
            (height / 2f + sin(angleMinutes) * mRadius * 0.5).toFloat(),
            minutePaint
        )

        // hours
        val angleHours =
            PI * (calendar.get(Calendar.HOUR) + calendar.get(Calendar.MINUTE) / 60F) * 5F / 30 - PI / 2

        canvas.drawLine(
            (width / 2f - cos(angleHours) * mRadius * 0.1).toFloat(),
            (height / 2f - sin(angleHours) * mRadius * 0.1).toFloat(),
            (width / 2f + cos(angleHours) * mRadius * 0.4).toFloat(),
            (height / 2f + sin(angleHours) * mRadius * 0.4).toFloat(),
            hourPaint
        )
    }

}