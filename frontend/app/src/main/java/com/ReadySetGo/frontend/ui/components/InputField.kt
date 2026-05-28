package com.ReadySetGo.frontend.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ReadySetGo.frontend.ui.theme.DarkNavy
import com.ReadySetGo.frontend.ui.theme.ErrorRed
import com.ReadySetGo.frontend.ui.theme.InputGray
import com.ReadySetGo.frontend.ui.theme.LimeGreen
import com.ReadySetGo.frontend.ui.theme.TextGray
import androidx.compose.ui.tooling.preview.Preview
import com.ReadySetGo.frontend.ui.theme.ReadySetGoTheme

/**
 * Uniwersalne pole tekstowe używane w onboardingu aplikacji.
 * Obsługuje placeholder oraz stan błędu z dynamicznym kolorem obramowania.
 *
 * Kolor obramowania:
 * - ErrorRed gdy [isError] = true
 * - LimeGreen gdy pole zawiera tekst
 * - Transparent gdy pole jest puste
 *
 * @param value Aktualna wartość pola tekstowego.
 * @param onValueChange Callback wywoływany przy zmianie tekstu.
 * @param placeholder Tekst placeholdera wyświetlany gdy pole jest puste.
 * @param isError Czy wyświetlić stan błędu.
 */
@Composable
fun ProfileInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isError: Boolean = false
) {
    val borderColor = when {
        isError -> ErrorRed
        value.isNotEmpty() -> LimeGreen
        else -> androidx.compose.ui.graphics.Color.Transparent
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(
                color = InputGray,
                shape = RoundedCornerShape(18.dp)
            )
            .border(
                width = 1.5.dp,
                color = borderColor,
                shape = RoundedCornerShape(18.dp)
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = DarkNavy,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = TextGray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }

                innerTextField()
            }
        )
    }
}
@Preview(showBackground = true)
@Composable
private fun ProfileInputFieldPreview() {
    ReadySetGoTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProfileInputField(
                value = "",
                onValueChange = {},
                placeholder = "Name"
            )

            ProfileInputField(
                value = "John",
                onValueChange = {},
                placeholder = "Name"
            )

            ProfileInputField(
                value = "",
                onValueChange = {},
                placeholder = "Name",
                isError = true
            )
        }
    }
}