package com.ReadySetGo.frontend.ui.sports

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ReadySetGo.frontend.R
import com.ReadySetGo.frontend.ui.components.SportItem
/**
 * Główny ekran wyboru sportów.
 * Przygotowuje listę dostępnych dyscyplin wraz z ikonami
 * i przekazuje dane do warstwy widoku.
 */
@Composable
fun SportsScreen(
    currentTab: String = "Start",
    onTabSelected: (String) -> Unit = {},
    onNavigateBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val sports = listOf(
        SportItem("Basketball", R.drawable.ic_basketball),
        SportItem("Biking", R.drawable.ic_biking),
        SportItem("Table Tennis", R.drawable.ic_tabletennis),
        SportItem("Running", R.drawable.ic_running),
        SportItem("Lifting", R.drawable.ic_lifting),
        SportItem("Football", R.drawable.ic_football),
        SportItem("Volleyball", R.drawable.ic_volleyball),
        SportItem("Badminton", R.drawable.ic_badminton)
    )

    SportsView(
        sports = sports,
        currentTab = currentTab,
        onTabSelected = onTabSelected,
        onBackClick = onNavigateBack,
        modifier = modifier
    )
}