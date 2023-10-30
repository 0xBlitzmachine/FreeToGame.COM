package com.blitzmachine.freetogamecom.utils

import android.content.Context
import android.graphics.Paint
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.blitzmachine.freetogamecom.R

object Utils {
    fun createCircularProgressDrawable(context: Context): CircularProgressDrawable {
        return CircularProgressDrawable(context).apply {
            this.strokeWidth = 5f
            this.centerRadius = 100f
            this.setColorSchemeColors(
                context.getColor(R.color.blue_main),
                context.getColor(R.color.white)
            )
            this.start()
        }
    }
}