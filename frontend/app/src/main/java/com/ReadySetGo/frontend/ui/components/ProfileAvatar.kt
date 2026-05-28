package com.ReadySetGo.frontend.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.Path
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ReadySetGo.frontend.ui.theme.DarkNavy
import com.ReadySetGo.frontend.ui.theme.InputGray
import com.ReadySetGo.frontend.ui.theme.Orange

/**
 * Komponent wyświetlający avatar użytkownika z przyciskiem edycji.
 *
 * @param selectedAvatarRes Id zasobu wybranego avatara. Jeśli null, pokazuje placeholder.
 * @param onClick Akcja kliknięcia avatara.
 * @param modifier Opcjonalny modifier.
 * @param isPickerVisible Czy AvatarPicker jest aktualnie widoczny.
 */
@Composable
fun ProfileAvatar(
    selectedAvatarRes: Int?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isPickerVisible: Boolean = false
) {
    BoxWithConstraints(
        modifier = modifier
            .size(118.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        val outerSize = maxWidth
        val avatarSize = outerSize * 0.88f
        val iconSize = avatarSize * 0.72f
        val editSize = outerSize * 0.17f
        val editOffset = -(outerSize * 0.13f)

        Box(
            modifier = Modifier
                .size(avatarSize)
                .clip(CircleShape)
                .background(InputGray),
            contentAlignment = Alignment.Center
        ) {
            if (selectedAvatarRes != null) {
                Image(
                    painter = painterResource(id = selectedAvatarRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Canvas(modifier = Modifier.size(iconSize)) {
                    val strokeWidth = size.width * 0.075f

                    drawCircle(
                        color = DarkNavy,
                        radius = size.width * 0.23f,
                        center = Offset(
                            x = size.width / 2f,
                            y = size.height * 0.30f
                        ),
                        style = Stroke(width = strokeWidth)
                    )

                    val path = Path().apply {
                        moveTo(size.width * 0.04f, size.height * 0.88f)

                        quadraticBezierTo(
                            size.width * 0.18f,
                            size.height * 0.68f,
                            size.width * 0.40f,
                            size.height * 0.68f
                        )

                        lineTo(size.width * 0.60f, size.height * 0.68f)

                        quadraticBezierTo(
                            size.width * 0.82f,
                            size.height * 0.68f,
                            size.width * 0.96f,
                            size.height * 0.88f
                        )
                    }

                    drawPath(
                        path = path,
                        color = DarkNavy,
                        style = Stroke(
                            width = strokeWidth,
                            cap = androidx.compose.ui.graphics.StrokeCap.Round,
                            join = androidx.compose.ui.graphics.StrokeJoin.Round
                        )
                    )
                }
            }
        }

        if (!isPickerVisible) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = editOffset, y = editOffset)
                    .size(editSize)
                    .clip(RoundedCornerShape(editSize * 0.28f))
                    .background(Orange)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileAvatarPreview() {
    ProfileAvatar(
        selectedAvatarRes = null,
        onClick = {},
        modifier = Modifier.size(150.dp)
    )
}