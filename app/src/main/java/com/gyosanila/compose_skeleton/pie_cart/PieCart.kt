package com.gyosanila.compose_skeleton.pie_cart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Created by yovi.putra on 29/03/23"
 * Project name: compose_skeleton
 **/

@Composable
fun PieCart(
    modifier: Modifier,
    data: List<PieCartData>,
    animateDuration: Int = 1000
) {
    val animatedProgress = remember { Animatable(initialValue = 0f) }
    LaunchedEffect(true) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = animateDuration)
        )
    }

    Box(modifier = modifier) {
        Canvas(modifier = modifier.fillMaxSize()) {
            val total = data.sumOf { it.value.toDouble() }
            var currentAngle = -90f

            data.forEach { pieCartData ->
                val sweepAngle =
                    (pieCartData.value.toDouble() / total.toFloat()) * 360 * animatedProgress.value
                val color = pieCartData.color

                drawArc(
                    color = color,
                    startAngle = currentAngle,
                    sweepAngle = sweepAngle.toFloat(),
                    useCenter = true,
                    topLeft = Offset.Zero,
                    size = size
                )

                currentAngle += sweepAngle.toFloat()
            }
        }
    }
}

@Preview
@Composable
fun PieCartPreview() {
    PieCart(
        modifier = Modifier
            .size(width = 200.dp, height = 200.dp),
        data = listOf(
            PieCartData(15f, Color.Green),
            PieCartData(5f, Color.Yellow),
            PieCartData(35f, Color.Red)
        )
    )
}