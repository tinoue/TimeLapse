package com.dan.timelapse.filters

import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat

class EndlessAverageFramesFilter(nextConsumer: FramesConsumer) : FramesFilter(nextConsumer) {
    private val sum = Mat()
    private val outputFrame = Mat()
    private var frameCounter = 0

    override fun consume(index: Int, frame: Mat) {
        frameCounter++

        if (sum.empty()) {
            frame.convertTo(sum, CvType.CV_32SC3)
            next(index, frame)
            return
        }

        Core.add( sum, frame, sum, Mat(), CvType.CV_32SC3)
        sum.convertTo(outputFrame, CvType.CV_8UC3, 1.0 / frameCounter)
        next(index, outputFrame)
    }

    override fun stopFilter() {
        frameCounter = 0
        sum.release()
        outputFrame.release()
        super.stopFilter()
    }
}