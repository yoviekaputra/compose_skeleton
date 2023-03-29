package com.gyosanila.compose_skeleton.loader

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

/**
 * Created by yovi.putra on 29/03/23"
 * Project name: compose_skeleton
 **/

@Composable
fun Loader(
    modifier: Modifier = Modifier
) {
    val animatedProgress = remember { Animatable(initialValue = 0f) }
    val animatedProgressSecondary = remember { Animatable(initialValue = 0f) }

    LaunchedEffect(true) {
        launch {
            awaitAll(
                async {
                    animatedProgress.animateTo(
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(1000),
                            repeatMode = RepeatMode.Reverse
                        )
                    )
                },
                async {
                    animatedProgressSecondary.animateTo(
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(1000),
                            repeatMode = RepeatMode.Reverse
                        )
                    )
                }
            )
        }
    }

    Box(modifier = modifier) {
        Canvas(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            animateCircleStroke(
                color = Color.Green,
                startAngle = 0f,
                angleRange = 0 downTo -360,
                animatedProgress = animatedProgress
            )
            animateCircleStroke(
                color = Color.Magenta,
                startAngle = 180f,
                angleRange = 0 downTo -360,
                animatedProgress = animatedProgressSecondary
            )
        }
    }
}

private fun DrawScope.animateCircleStroke(
    color: Color,
    startAngle: Float,
    angleRange: IntProgression,
    animatedProgress: Animatable<Float, AnimationVector1D>
) {
    angleRange.forEach { angle ->
        val sweepAngle = angle.toDouble() * animatedProgress.value.toDouble()

        drawCircleStroke(
            color = color,
            startAngle = startAngle,
            sweepAngle = sweepAngle.toFloat()
        )
    }
}


private fun DrawScope.drawCircleStroke(color: Color, startAngle: Float, sweepAngle: Float) {
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        topLeft = Offset.Zero,
        size = size,
        style = Stroke(32f)
    )
}
/*

*/
/**
 * Returns a [State] holding a local animation time in milliseconds and the current [AnimationPhase]
 * The local animation time always starts at `0L` and stops updating when the call
 * leaves the composition. The animation phase starts as [AnimationPhase.STROKE_STARTED], since
 * stroke always renders first, and gets updated according to the elapsed time.
 *//*

@Composable
private fun animationTimeMillis(
    strokeDrawingDuration: Long,
    fillDrawingDuration: Long
): Recomposer.State<AnimationState> {

    fun keepDrawing(elapsedTime: Long): Boolean =
        elapsedTime < (strokeDrawingDuration + fillDrawingDuration)

    val state = state { AnimationState(AnimationPhase.STROKE_STARTED, 0L) }
    val lifecycleOwner = LifecycleOwnerAmbient.current

    launchInComposition {
        val startTime = withFrameMillis { it }

        lifecycleOwner.whenStarted {
            while (true) {
                withFrameMillis { frameTime ->
                    val elapsedTime = frameTime - startTime
                    if (!keepDrawing(elapsedTime)) {
                        state.value = state.value.copy(animationPhase = AnimationPhase.FINISHED)
                    }

                    if (elapsedTime > strokeDrawingDuration) {
                        if (state.value.animationPhase < AnimationPhase.FILL_STARTED) {
                            state.value =
                                state.value.copy(animationPhase = AnimationPhase.FILL_STARTED)
                        }
                    }

                    state.value = state.value.copy(elapsedTime = frameTime - startTime)
                }
            }
        }
    }
    return state
}
*/

@Preview
@Composable
fun LoaderPreview() {
    Loader(
        modifier = Modifier.size(150.dp, 150.dp)
    )
}