package com.ReadySetGo.frontend.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ReadySetGo.frontend.ui.theme.DarkNavy

/**
 * Karta aktywności/sportu używana w widokach wyboru dyscypliny.
 * Wyświetla placeholder ikony oraz nazwę aktywności.
 *
 * @param title Nazwa aktywności.
 * @param modifier Modyfikator.
 */
@Composable
fun SportCard(
    title: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(110.dp)
            .height(110.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(14.dp)
            )
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFFFAFCF4))
            .border(
                width = 1.dp,
                color = Color(0xFFE6E6E6),
                shape = RoundedCornerShape(14.dp)
            )
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .align(Alignment.TopCenter)
                .padding(top = 8.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFEAEAEA))
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(26.dp)
                .clip(RoundedCornerShape(9.dp))
                .background(Color(0xFFEAF8C9))
                .border(
                    width = 1.dp,
                    color = Color(0xFFBBF246),
                    shape = RoundedCornerShape(9.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = DarkNavy.copy(alpha = 0.55f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

/**
 * Siatka kart sportów.
 * Automatycznie tworzy odpowiednią liczbę rzędów na podstawie liczby elementów.
 *
 * @param sports Lista nazw sportów do wyświetlenia.
 * @param modifier Modyfikator.
 */
@Composable
fun SportCardGrid(
    sports: List<String>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        userScrollEnabled = false
    ) {
        items(sports) { sport ->
            SportCard(title = sport)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SportCardGridPreview() {
    SportCardGrid(
        sports = listOf(
            "Basketball",
            "Tennis",
            "Basketball",
            "Basketball",
            "Basketball",
            "Basketball",
            "Basketball",
            "Basketball",
            "Basketball"
        ),
        modifier = Modifier
            .background(Color(0xFFF3FAE8))
            .padding(16.dp)
            .height(350.dp)
    )
}