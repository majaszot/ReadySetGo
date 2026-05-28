package com.ReadySetGo.frontend.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ReadySetGo.frontend.ui.theme.DarkNavy

@Composable
fun ProfileHeaderSection(
    name: String,
    age: String,
    levelName: String,
    city: String,
    selectedAvatarRes: Int?,
    onAvatarClick: () -> Unit,
    isPickerVisible: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(106.dp))

        ProfileAvatar(
            selectedAvatarRes = selectedAvatarRes,
            onClick = onAvatarClick,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = Color(0xFFBBF246),
                    shape = CircleShape
                ),
            isPickerVisible = isPickerVisible
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = DarkNavy,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(18.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ProfileInfoChip(text = age)
            ProfileInfoChip(text = levelName)
            ProfileInfoChip(text = city)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileHeaderSectionPreview() {
    ProfileHeaderSection(
        name = "Jane Doe",
        age = "23 lata",
        levelName = "Beginner",
        city = "Rzeszów",
        selectedAvatarRes = null,
        onAvatarClick = {},
        isPickerVisible = false
    )
}