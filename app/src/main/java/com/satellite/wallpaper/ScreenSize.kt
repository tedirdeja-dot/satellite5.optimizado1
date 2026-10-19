package com.satellite.wallpaper

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import kotlin.math.sqrt

/** Tamaño real de la pantalla en vertical (incluye barras del sistema): el mismo que usa el fondo animado. */
object ScreenSize {
    private const val MAX_CAPTURE_EDGE = 2560
    private const val MAX_CAPTURE_PIXELS = 3_000_000L

    fun real(context: Context): Pair<Int, Int> {
        val wm = context.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val w: Int
        val h: Int
        if (Build.VERSION.SDK_INT >= 30) {
            val b = wm.maximumWindowMetrics.bounds
            w = b.width(); h = b.height()
        } else {
            val m = DisplayMetrics()
            @Suppress("DEPRECATION")
            wm.defaultDisplay.getRealMetrics(m)
            w = m.widthPixels; h = m.heightPixels
        }
        return Pair(minOf(w, h), maxOf(w, h))
    }

    /**
     * Limita las capturas para reducir memoria y tiempo en móviles de alta resolución.
     * Conserva la proporción; el sistema escala la imagen al aplicarla como fondo.
     */
    fun capture(context: Context): Pair<Int, Int> {
        val (w, h) = real(context)
        val edgeScale = MAX_CAPTURE_EDGE.toFloat() / maxOf(w, h).coerceAtLeast(1)
        val pixelScale = sqrt(MAX_CAPTURE_PIXELS.toDouble() / (w.toLong() * h).coerceAtLeast(1L)).toFloat()
        val scale = minOf(1f, edgeScale, pixelScale)
        return Pair(
            (w * scale).toInt().coerceAtLeast(1),
            (h * scale).toInt().coerceAtLeast(1)
        )
    }

    /** Relación ancho/alto en vertical. */
    fun aspect(context: Context): Float {
        val (w, h) = real(context)
        return w.toFloat() / h.toFloat()
    }
}
