package com.ReadySetGo.frontend.ui.sports

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ReadySetGo.frontend.R
import com.ReadySetGo.frontend.ui.components.MainAppScaffold
import com.ReadySetGo.frontend.ui.components.SportCardGrid
import com.ReadySetGo.frontend.ui.components.SportItem
/**
 * Widok wyboru sportów (SportsView) - czysty komponent UI.
 * Wyświetla ekran oparty o wspólny scaffold aplikacji oraz siatkę kart sportów.
 */
@Composable
fun SportsView(
    sports: List<SportItem>,
    currentTab: String,
    onTabSelected: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    MainAppScaffold(
        currentTab = currentTab,
        onTabSelected = onTabSelected,
        onBackClick = onBackClick,
        onStatsClick = {},
        title = "Sports"
    ) { paddingValues ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFF5F9ED))
                .padding(top = paddingValues.calculateTopPadding())
                .padding(bottom = paddingValues.calculateBottomPadding())
                .padding(horizontal = 24.dp, vertical = 42.dp)
        ) {
            SportCardGrid(
                sports = sports,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SportsViewPreview() {
    SportsView(
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
        currentTab = "Start",
        onTabSelected = {},
        onBackClick = {}
    )
}