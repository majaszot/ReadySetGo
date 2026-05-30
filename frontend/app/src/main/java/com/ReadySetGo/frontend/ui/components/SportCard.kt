package com.ReadySetGo.frontend.ui.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ReadySetGo.frontend.R
import com.ReadySetGo.frontend.ui.theme.DarkNavy
/**
 * Karta sportu używana w widoku wyboru dyscypliny.
 * Wyświetla ikonę sportu oraz nazwę aktywności w harmonizowanym stylu UI.
 */
data class SportItem(
    val name: String,
    val iconRes: Int
)

@Composable
fun SportCard(
    title: String,
    iconRes: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(110.dp)
            .height(110.dp)
            .shadow(4.dp, RoundedCornerShape(14.dp))
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFFFAFCF4))
            .border(1.dp, Color(0xFFE6E6E6), RoundedCornerShape(14.dp))
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = title,
            modifier = Modifier
                .size(52.dp)
                .align(Alignment.TopCenter)
                .padding(top = 8.dp)
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(26.dp)
                .clip(RoundedCornerShape(9.dp))
                .background(Color(0xFFEAF8C9))
                .border(1.dp, Color(0xFFBBF246), RoundedCornerShape(9.dp)),
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

@Composable
fun SportCardGrid(
    sports: List<SportItem>,
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
            SportCard(
                title = sport.name,
                iconRes = sport.iconRes
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SportCardGridPreview() {
    SportCardGrid(
        sports = listOf(
            SportItem("Basketball", R.drawable.ic_basketball),
            SportItem("Biking", R.drawable.ic_biking),
            SportItem("Table Tennis", R.drawable.ic_tabletennis),
            SportItem("Running", R.drawable.ic_running),
            SportItem("Lifting", R.drawable.ic_lifting),
            SportItem("Football", R.drawable.ic_football),
            SportItem("Volleyball", R.drawable.ic_volleyball),
            SportItem("Badminton", R.drawable.ic_badminton)
        ),
        modifier = Modifier
            .background(Color(0xFFF3FAE8))
            .padding(16.dp)
            .height(350.dp)
    )
}