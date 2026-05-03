package com.ReadySetGo.frontend.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ReadySetGo.frontend.R
import com.ReadySetGo.frontend.ui.theme.*

/**
 * Reużywalne pole tekstowe dla formularzy logowania i rejestracji.
 * Obsługuje pola email, hasła oraz potwierdzenia hasła.
 *
 * Zachowanie obramowania:
 * - Brak obramowania gdy pole jest puste
 * - Zielone obramowanie ([LimeGreen]) gdy pole ma wartość
 * - Czerwone obramowanie ([ErrorRed]) gdy pole ma błąd
 *
 * @param value Aktualna wartość pola tekstowego.
 * @param onValueChange Callback wywoływany przy każdej zmianie wartości.
 * @param label Etykieta wyświetlana nad polem tekstowym.
 * @param leadingIconRes Id zasobu ikony wyświetlanej po lewej stronie pola.
 * @param modifier Opcjonalny modifier dla dostosowania wyglądu.
 * @param isPassword Czy pole jest polem hasła.
 * @param isError Czy pole jest w stanie błędu.
 * @param errorMessage Opcjonalny komunikat błędu.
 * @param keyboardType Typ klawiatury.
 * @param validator Opcjonalna funkcja walidująca. Zwraca komunikat błędu albo null.
 */
@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIconRes: Int,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    isError: Boolean = false,
    errorMessage: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    validator: ((String) -> String?)? = null
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var wasFocused by remember { mutableStateOf(false) }
    var validationError by remember { mutableStateOf<String?>(null) }

    val currentErrorMessage = errorMessage ?: validationError
    val hasError = isError || currentErrorMessage != null

    val borderColor = when {
        hasError -> ErrorRed
        value.isNotEmpty() -> LimeGreen
        else -> Color.Transparent
    }

    Column(modifier = modifier) {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = DarkNavy,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (wasFocused && !focusState.hasFocus) {
                        validationError = validator?.invoke(value)
                    }
                    if (focusState.hasFocus) {
                        wasFocused = true
                    }
                }
                .clip(RoundedCornerShape(14.dp))
                .background(if (hasError) ErrorRedLight else InputGray)
                .border(1.5.dp, borderColor, RoundedCornerShape(14.dp))
        ) {
            TextField(
                value = value,
                onValueChange = {
                    onValueChange(it)
                    validationError = null
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                ),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = leadingIconRes),
                        contentDescription = null,
                        tint = TextGray,
                        modifier = Modifier.size(20.dp)
                    )
                },
                trailingIcon = if (isPassword) {
                    {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                painter = painterResource(
                                    id = if (passwordVisible)
                                        R.drawable.ic_eye_on
                                    else
                                        R.drawable.ic_eye_off
                                ),
                                contentDescription = if (passwordVisible) "Hide" else "Show",
                                tint = TextGray,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                } else null,
                visualTransformation = if (isPassword && !passwordVisible)
                    PasswordVisualTransformation()
                else
                    VisualTransformation.None,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                singleLine = true,
                isError = hasError
            )
        }

        if (hasError && currentErrorMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
            ErrorMessage(message = currentErrorMessage)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthTextFieldPreview() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AuthTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email Address",
            leadingIconRes = R.drawable.ic_email,
            keyboardType = KeyboardType.Email,
            validator = {
                when {
                    it.isBlank() -> "Email is required"
                    !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches() ->
                        "Invalid email address"
                    else -> null
                }
            }
        )

        AuthTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            leadingIconRes = R.drawable.ic_lock,
            isPassword = true,
            validator = {
                when {
                    it.isBlank() -> "Password is required"
                    it.length < 8 -> "Password must be at least 8 characters"
                    else -> null
                }
            }
        )

        AuthTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = "Confirm Password",
            leadingIconRes = R.drawable.ic_lock,
            isPassword = true,
            validator = {
                when {
                    it.isBlank() -> "Confirm password is required"
                    it != password -> "ERROR: Password Don’t Match!"
                    else -> null
                }
            }
        )
    }
}