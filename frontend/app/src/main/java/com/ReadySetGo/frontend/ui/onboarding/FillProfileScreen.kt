package com.ReadySetGo.frontend.ui.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ReadySetGo.frontend.ui.components.AdaptiveButton
import com.ReadySetGo.frontend.ui.components.AvatarPicker
import com.ReadySetGo.frontend.ui.components.OnboardingScaffold
import com.ReadySetGo.frontend.ui.components.ProfileAvatar
import com.ReadySetGo.frontend.ui.components.ProfileInputField
import com.ReadySetGo.frontend.ui.theme.ErrorRed
import com.ReadySetGo.frontend.ui.theme.LimeGreen
import com.ReadySetGo.frontend.ui.theme.Orange

/**
 * Ekran uzupełniania profilu użytkownika.
 *
 * Umożliwia:
 * - wybór lub upload avatara,
 * - wpisanie imienia oraz nazwiska,
 * - walidację wymaganego pola imienia,
 * - rozpoczęcie korzystania z aplikacji.
 *
 * Widok korzysta z:
 * - [OnboardingScaffold] jako wspólnego layoutu onboardingowego,
 * - [ProfileAvatar] do wyświetlania aktualnego avatara,
 * - [AvatarPicker] do wyboru avatara,
 * - [ProfileInputField] jako pól formularza,
 * - [AdaptiveButton] jako przycisków akcji.
 *
 * @param onBackClick Callback wywoływany po kliknięciu przycisku Back.
 * @param onStartClick Callback wywoływany po poprawnym uzupełnieniu formularza.
 * Zwraca imię, nazwisko oraz wybrany avatar.
 * @param onUploadAvatarClick Callback uruchamiający upload własnego avatara.
 */
@Composable
fun FillProfileScreen(
    onBackClick: () -> Unit,
    onStartClick: (name: String, lastName: String, avatarRes: Int?) -> Unit,
    onUploadAvatarClick: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf(false) }

    var showAvatarPicker by remember { mutableStateOf(false) }
    var selectedAvatarIndex by remember { mutableStateOf<Int?>(null) }

    val avatars = emptyList<Int>()
    val selectedAvatarRes = selectedAvatarIndex?.let { avatars.getOrNull(it) }

    OnboardingScaffold(
        title = "Fill Your Profile",
        description = "Fill in to start your journey.\nYou can always change it later.",
        onContinueClick = {
            if (name.isBlank()) {
                nameError = true
                return@OnboardingScaffold
            }

            nameError = false

            onStartClick(
                name.trim(),
                lastName.trim(),
                selectedAvatarRes
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(54.dp))

            ProfileAvatar(
                selectedAvatarRes = selectedAvatarRes,
                onClick = { showAvatarPicker = !showAvatarPicker },
                modifier = Modifier.size(150.dp),
                isPickerVisible = showAvatarPicker
            )

            Spacer(modifier = Modifier.height(42.dp))

            if (showAvatarPicker) {
                AvatarPicker(
                    avatars = avatars,
                    selectedIndex = selectedAvatarIndex,
                    onAvatarSelected = { index ->
                        selectedAvatarIndex = index
                        showAvatarPicker = false
                    },
                    onUploadClick = onUploadAvatarClick,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))
            }

            if (!showAvatarPicker) {
                ProfileInputField(
                    value = name,
                    onValueChange = {
                        name = it
                        if (it.isNotBlank()) nameError = false
                    },
                    placeholder = "Name*",
                    isError = nameError
                )

                if (nameError) {
                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Name is required",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 12.sp,
                            color = ErrorRed
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                ProfileInputField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    placeholder = "Last Name"
                )
            }

            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 120.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AdaptiveButton(
                    text = "Back",
                    onClick = onBackClick,
                    modifier = Modifier.weight(1f)
                )

                AdaptiveButton(
                    text = "Start",
                    onClick = {
                        if (name.isBlank()) {
                            nameError = true
                            return@AdaptiveButton
                        }

                        nameError = false

                        onStartClick(
                            name.trim(),
                            lastName.trim(),
                            selectedAvatarRes
                        )
                    },
                    modifier = Modifier.weight(1f),
                    background = Brush.horizontalGradient(
                        colors = listOf(
                            Orange,
                            LimeGreen
                        )
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FillProfileScreenPreview() {
    FillProfileScreen(
        onBackClick = {},
        onStartClick = { _, _, _ -> },
        onUploadAvatarClick = {}
    )
}