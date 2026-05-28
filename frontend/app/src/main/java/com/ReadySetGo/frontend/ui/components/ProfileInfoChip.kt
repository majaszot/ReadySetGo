package com.ReadySetGo.frontend.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ReadySetGo.frontend.ui.theme.DarkNavy

/**
 * Mały chip informacyjny używany na ekranie profilu użytkownika.
 * Wyświetla krótką informację, np. wiek, poziom lub miasto.
 *
 * @param text Tekst wyświetlany w chipie.
 * @param modifier Modyfikator.
 */
@Composable
fun ProfileInfoChip(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(99.dp)
            .height(26.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFEAF8C9))
            .border(
                width = 1.dp,
                color = Color(0xFFBBF246),
                shape = RoundedCornerShape(10.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            modifier = Modifier.offset(y = (-1).dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = DarkNavy,
                fontSize = 12.sp,
                lineHeight = 12.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileInfoChipPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE9E9E9))
            .padding(top = 120.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProfileInfoChip(text = "23 lata")
            ProfileInfoChip(text = "Beginner")
            ProfileInfoChip(text = "Rzeszów")
        }
    }
}