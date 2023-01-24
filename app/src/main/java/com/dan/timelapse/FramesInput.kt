package com.dan.timelapse

import org.opencv.core.Mat

interface FramesInput {
    companion object {
        fun fixName(original: String?): String {
            if (null == original) return "unknown"
            return original.split('.')[0]
        }
    }

    val fps: Int
    val name: String
    val width: Int
    val height: Int

    fun forEachFrame(callback: (Int, Int, Mat)->Unit)
}