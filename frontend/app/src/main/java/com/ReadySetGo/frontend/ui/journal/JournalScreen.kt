package com.ReadySetGo.frontend.ui.journal

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import java.time.LocalDate

/**
 * Główny ekran Dziennika Treningowego.
 * Zarządza stanem wybranej daty oraz wpisami treningowymi dla poszczególnych dni.
 */
@Composable
fun JournalScreen(
    currentTab: String = "Journal",
    onTabSelected: (String) -> Unit = {},
    onNavigateBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    
    // Seed initial training entries for today to look premium right away
    var trainingEntries by remember {
        mutableStateOf(
            mapOf(
                LocalDate.now() to listOf("Morning Jog", "Strength Training")
            )
        )
    }

    // ststus dla set up journal
    var step1Completed by remember { mutableStateOf(false) }
    var step2Completed by remember { mutableStateOf(false) }
    var step3Completed by remember { mutableStateOf(false) }

    // Auto-complete log first training step when there is at least one entry
    LaunchedEffect(trainingEntries) {
        val totalEntries = trainingEntries.values.flatten().size
        step2Completed = totalEntries > 0
    }

    JournalView(
        selectedDate = selectedDate,
        onDateSelected = { selectedDate = it },
        trainingEntries = trainingEntries,
        onAddTraining = { date, name ->
            val list = trainingEntries[date] ?: emptyList()
            trainingEntries = trainingEntries + (date to (list + name))
        },
        onDeleteTraining = { date, index ->
            val list = trainingEntries[date] ?: emptyList()
            val newList = list.filterIndexed { idx, _ -> idx != index }
            trainingEntries = trainingEntries + (date to newList)
        },
        step1Completed = step1Completed,
        onStep1Toggle = { step1Completed = !step1Completed },
        step2Completed = step2Completed,
        onStep2Toggle = { step2Completed = !step2Completed },
        step3Completed = step3Completed,
        onStep3Toggle = { step3Completed = !step3Completed },
        currentTab = currentTab,
        onTabSelected = onTabSelected,
        onBackClick = onNavigateBack,
        modifier = modifier
    )
}

