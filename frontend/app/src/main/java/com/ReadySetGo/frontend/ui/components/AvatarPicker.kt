package com.ReadySetGo.frontend.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ReadySetGo.frontend.ui.theme.DarkNavy
import com.ReadySetGo.frontend.ui.theme.Orange
import com.ReadySetGo.frontend.ui.theme.White

/**
 * Komponent wyboru avatara profilowego.
 *
 * @param avatars Lista zasobów avatarów. Jeśli pusta, komponent pokazuje placeholdery.
 * @param selectedIndex Aktualnie wybrany avatar.
 * @param onAvatarSelected Akcja po wybraniu avatara.
 * @param onUploadClick Akcja kliknięcia upload.
 * @param modifier Opcjonalny modifier.
 */
@Composable
fun AvatarPicker(
    avatars: List<Int>,
    selectedIndex: Int?,
    onAvatarSelected: (Int) -> Unit,
    onUploadClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val itemsCount = if (avatars.isEmpty()) 8 else avatars.size

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE99494),
                        Orange
                    )
                )
            )
            .padding(horizontal = 18.dp, vertical = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "You can choose one of these",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = DarkNavy,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(14.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.height(116.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            userScrollEnabled = false
        ) {
            items(itemsCount) { index ->
                AvatarPickerItem(
                    avatarRes = avatars.getOrNull(index),
                    isSelected = selectedIndex == index,
                    onClick = { onAvatarSelected(index) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Or",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = DarkNavy,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(14.dp))

        Button(
            onClick = onUploadClick,
            modifier = Modifier
                .width(164.dp)
                .height(44.dp),
            shape = RoundedCornerShape(22.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFC09A),
                contentColor = DarkNavy
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "Upload",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
private fun AvatarPickerItem(
    avatarRes: Int?,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(White)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) DarkNavy else White,
                shape = CircleShape
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (avatarRes != null) {
            Image(
                painter = painterResource(id = avatarRes),
                contentDescription = null,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFC9A8))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AvatarPickerPreview() {
    AvatarPicker(
        avatars = emptyList(),
        selectedIndex = 2,
        onAvatarSelected = {},
        onUploadClick = {}
    )
}