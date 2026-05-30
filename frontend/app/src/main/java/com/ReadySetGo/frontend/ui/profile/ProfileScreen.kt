package com.ReadySetGo.frontend.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.ReadySetGo.frontend.ui.components.AvatarPicker
import com.ReadySetGo.frontend.ui.components.GlassyBottomBar
import com.ReadySetGo.frontend.ui.components.ProfileHeaderSection
import com.ReadySetGo.frontend.ui.components.ProfileLevelCard
import com.ReadySetGo.frontend.ui.components.ProfileStatsCard
/**
 * Główny ekran profilu użytkownika.
 * Łączy sekcję nagłówka, wybór avatara, kartę poziomu gracza,
 * statystyki postępu oraz dolny pasek nawigacji.
 */
@Composable
fun ProfileScreen(
    currentTab: String = "Profile",
    onTabSelected: (String) -> Unit = {}
) {
    var selectedAvatarIndex by remember { mutableStateOf<Int?>(null) }
    var showAvatarPicker by remember { mutableStateOf(false) }

    val avatars = emptyList<Int>()
    val selectedAvatarRes = selectedAvatarIndex?.let { avatars.getOrNull(it) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 95.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(330.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFBBF246),
                                Color.White
                            )
                        )
                    )
            ) {
                ProfileHeaderSection(
                    name = "Jane Doe",
                    age = "23 lata",
                    levelName = "Beginner",
                    city = "Rzeszów",
                    selectedAvatarRes = selectedAvatarRes,
                    onAvatarClick = { showAvatarPicker = !showAvatarPicker },
                    isPickerVisible = showAvatarPicker
                )
            }

            Spacer(modifier = Modifier.height(26.dp))

            ProfileLevelCard(
                level = 1,
                currentXp = 20,
                nextLevelXp = 100,
                modifier = Modifier.padding(horizontal = 19.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))

            ProfileStatsCard(
                weightChange = "-2 kg",
                activeMinutes = 67,
                modifier = Modifier.padding(horizontal = 19.dp)
            )
        }

        if (showAvatarPicker) {
            AvatarPicker(
                avatars = avatars,
                selectedIndex = selectedAvatarIndex,
                onAvatarSelected = { index ->
                    selectedAvatarIndex = index
                    showAvatarPicker = false
                },
                onUploadClick = {},
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = 300.dp)
                    .padding(horizontal = 19.dp)
                    .zIndex(99f)
            )
        }

        GlassyBottomBar(
            currentTab = currentTab,
            onTabSelected = onTabSelected,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen()
}