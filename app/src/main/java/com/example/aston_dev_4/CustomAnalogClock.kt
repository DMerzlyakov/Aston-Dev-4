package com.example.aston_dev_4

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.*
import kotlin.math.*

/** Custom View - Analog Watch  */
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

    /** Отрисовка циферблата  */
    private fun drawClockFace(canvas: Canvas) {
        // Границы циферблата
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, circlePaint)

        // Метки часов на циферблате
        hoursRange.onEach {
            drawLine(
                paint = marksPaint, canvas = canvas, angle = (PI / 6 * it).toFloat(),
                radiusStart = mRadius * 0.85F, radiusEnd = mRadius
            )
        }
    }

    private fun drawHands(canvas: Canvas) {
        val calendar = Calendar.getInstance()

        /** Секундная стрелка */
        drawLine(
            paint = secondPaint, canvas = canvas,
            angle = (PI * calendar.get(Calendar.SECOND) / 30 - PI / 2).toFloat(),
            radiusStart = mRadius * -0.4F, radiusEnd = mRadius * 0.7F
        )

        /** Минутная стрелка */
        drawLine(
            paint = minutePaint, canvas = canvas,
            angle = (PI * calendar.get(Calendar.MINUTE) / 30 - PI / 2).toFloat(),
            radiusStart = mRadius * -0.2F, radiusEnd = mRadius * 0.5F
        )

        /** Часовая стрелка */
        drawLine(
            paint = hourPaint, canvas = canvas,
            angle = (PI * getHourWithMinute(calendar) * 5F / 30 - PI / 2).toFloat(),
            radiusStart = mRadius * -0.1F, radiusEnd = mRadius * 0.4F
        )
    }

    /** Отрисовка линий по предоставленным значениям  */
    private fun drawLine(
        paint: Paint,
        canvas: Canvas,
        angle: Float,
        radiusStart: Float,
        radiusEnd: Float
    ) {
        canvas.drawLine(
            (width / 2f + cos(angle) * radiusStart),
            (height / 2f + sin(angle) * radiusStart),
            (width / 2f + cos(angle) * radiusEnd),
            (height / 2f + sin(angle) * radiusEnd),
            paint
        )

    }

    /** Получаем десятичное число часов, учитывая минуты*/
    private fun getHourWithMinute(calendar: Calendar) =
        (calendar.get(Calendar.HOUR) + calendar.get(Calendar.MINUTE) / 60F)

}