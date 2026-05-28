package com.ReadySetGo.frontend.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ReadySetGo.frontend.ui.theme.DarkNavy

/**
 * Karta postępu użytkownika z wagą oraz aktywnymi minutami.
 *
 * @param weightChange Zmiana wagi użytkownika.
 * @param activeMinutes Ilość aktywnych minut.
 * @param modifier Modyfikator.
 */
@Composable
fun ProfileStatsCard(
    weightChange: String,
    activeMinutes: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(352.dp)
            .height(177.dp)
    ) {

        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(y = 4.dp)
                .padding(horizontal = 2.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.Black.copy(alpha = 0.15f))
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(24.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFF6B58C),
                            Color(0xFFF89CA6)
                        )
                    )
                )
                .padding(horizontal = 28.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Weight",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = DarkNavy,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Canvas(
                    modifier = Modifier
                        .width(110.dp)
                        .height(70.dp)
                ) {

                    val path = Path().apply {
                        moveTo(0f, size.height * 0.45f)

                        quadraticBezierTo(
                            size.width * 0.15f,
                            size.height * 0.25f,
                            size.width * 0.32f,
                            size.height * 0.40f
                        )

                        quadraticBezierTo(
                            size.width * 0.48f,
                            size.height * 0.55f,
                            size.width * 0.65f,
                            size.height * 0.50f
                        )

                        quadraticBezierTo(
                            size.width * 0.82f,
                            size.height * 0.42f,
                            size.width,
                            size.height * 0.58f
                        )
                    }

                    drawPath(
                        path = path,
                        color = Color(0xFFFFF1E5),
                        style = Stroke(
                            width = 4f,
                            cap = StrokeCap.Round
                        )
                    )

                    listOf(
                        Offset(0f, size.height * 0.45f),
                        Offset(size.width * 0.32f, size.height * 0.40f),
                        Offset(size.width * 0.65f, size.height * 0.50f),
                        Offset(size.width, size.height * 0.58f)
                    ).forEach { point ->

                        drawCircle(
                            color = Color(0xFFFFF1E5),
                            radius = 5f,
                            center = point
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = weightChange,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = DarkNavy,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Active minutes",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = DarkNavy,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Bottom
                ) {

                    val barHeights = listOf(
                        36.dp,
                        58.dp,
                        44.dp,
                        50.dp,
                        38.dp,
                        52.dp
                    )

                    val labels = listOf(
                        "M", "T", "W", "T", "F", "S"
                    )

                    barHeights.forEachIndexed { index, height ->

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom
                        ) {

                            Box(
                                modifier = Modifier
                                    .width(12.dp)
                                    .height(height)
                                    .clip(RoundedCornerShape(50.dp))
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                Color(0xFFCC5B00),
                                                Color(0xFF7A2500)
                                            )
                                        )
                                    )
                            )

                            Spacer(modifier = Modifier.height(3.dp))

                            Text(
                                text = labels[index],
                                color = DarkNavy,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "↗$activeMinutes min",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = DarkNavy,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileStatsCardPreview() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE9E9E9))
            .padding(24.dp)
    ) {

        ProfileStatsCard(
            weightChange = "-2 kg",
            activeMinutes = 67
        )
    }
}