package com.ReadySetGo.frontend.ui.journal

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ReadySetGo.frontend.R
import com.ReadySetGo.frontend.ui.components.Calendar
import com.ReadySetGo.frontend.ui.components.MainAppScaffold
import com.ReadySetGo.frontend.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Widok Dziennika Treningowego (JournalView) - Czysty komponent UI.
 * Zawiera kartę konfiguracji w formie karuzeli, kalendarz oraz sekcję z treningami na wybrany dzień.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalView(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    trainingEntries: Map<LocalDate, List<String>>,
    onAddTraining: (LocalDate, String) -> Unit,
    onDeleteTraining: (LocalDate, Int) -> Unit,
    step1Completed: Boolean,
    onStep1Toggle: () -> Unit,
    step2Completed: Boolean,
    onStep2Toggle: () -> Unit,
    step3Completed: Boolean,
    onStep3Toggle: () -> Unit,
    currentTab: String,
    onTabSelected: (String) -> Unit,
    onBackClick: () -> Unit,
    onStatsClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // Stan rozwijanej karty kroków konfiguracji
    var isStepsExpanded by remember { mutableStateOf(true) }

    val completedCount = (if (step1Completed) 1 else 0) +
            (if (step2Completed) 1 else 0) +
            (if (step3Completed) 1 else 0)
    val progress = completedCount / 3f

    // Stan dialogu dodawania treningu
    var showAddDialog by remember { mutableStateOf(false) }
    var newTrainingName by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    MainAppScaffold(
        currentTab = currentTab,
        onTabSelected = onTabSelected,
        onBackClick = onBackClick,
        onStatsClick = onStatsClick,
        title = "Training journal"
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(top = paddingValues.calculateTopPadding())
                .padding(bottom = paddingValues.calculateBottomPadding())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {

        //KARTA: Set up your journal
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(2.dp, RoundedCornerShape(16.dp))
                .background(Color(0xFFF4FBE4), RoundedCornerShape(16.dp))
                .border(1.dp, Color(0xFFE2F4B3), RoundedCornerShape(16.dp))
                .clickable { isStepsExpanded = !isStepsExpanded }
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Ikona gwiazdek/sparkles w jasnym kółku
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .background(Color(0xFFE5F7C0), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier.size(20.dp)) {
                            // Duża gwiazdka
                            drawSparkle(Offset(size.width * 0.4f, size.height * 0.55f), size.width * 0.35f, Color(0xFF6B8A1A))
                            // Mała gwiazdka
                            drawSparkle(Offset(size.width * 0.8f, size.height * 0.25f), size.width * 0.18f, Color(0xFF6B8A1A))
                        }
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Set up your journal in 3 steps",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkNavy
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Pasek postępu
                            LinearProgressIndicator(
                                progress = { progress },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = LimeGreen,
                                trackColor = Color(0xFFD6ECC0)
                            )
                            // Tekst ułamka
                            Text(
                                text = "$completedCount/3",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF5A7615)
                            )
                        }
                    }

                    // Przycisk rozwinięcia
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(Color(0xFFD7EFA4), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isStepsExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = "Expand Steps",
                            tint = Color(0xFF5A7615),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // Rozwinięta lista set upu
                AnimatedVisibility(
                    visible = isStepsExpanded,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    Column(
                        modifier = Modifier.padding(top = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        HorizontalDivider(color = Color(0xFFE2F4B3))

                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(end = 8.dp)
                        ) {
                            item {
                                SetupStepCard(
                                    title = "Add your fav sports",
                                    subtitle = "Tap + to add your first training",
                                    isCompleted = step1Completed,
                                    onClick = onStep1Toggle
                                )
                            }
                            item {
                                SetupStepCard(
                                    title = "Log your first training",
                                    subtitle = "Each entry builds your training history and fuels your activity streak.",
                                    isCompleted = step2Completed,
                                    onClick = onStep2Toggle
                                )
                            }
                            item {
                                SetupStepCard(
                                    title = "Track your progress",
                                    subtitle = "Check your statistics and see your improvement over time.",
                                    isCompleted = step3Completed,
                                    onClick = onStep3Toggle
                                )
                            }
                        }
                    }
                }
            }
        }

        //SEKCJA: Calendar
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Calendar",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DarkNavy
            )

            Calendar(
                selectedDate = selectedDate,
                onDateSelected = onDateSelected,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // ── SEKCJA: Today ────────────────────────────────────────
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            val isToday = selectedDate == LocalDate.now()
            val headerText = if (isToday) "Today" else selectedDate.format(DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH))

            Text(
                text = headerText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DarkNavy
            )

            val dayTrainings = trainingEntries[selectedDate] ?: emptyList()

            if (dayTrainings.isEmpty()) {
                // Stan pusty
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(2.dp, RoundedCornerShape(16.dp))
                        .background(White, RoundedCornerShape(16.dp))
                        .border(1.dp, BorderGray.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                        .padding(vertical = 32.dp, horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Ikona notesu w kółku
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(Color(0xFFEFF2F5), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_notes),
                                contentDescription = "Notes",
                                tint = Color(0xFF7D7F79),
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Text(
                            text = "No entries for this day",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkNavy
                        )

                        Text(
                            text = "Tap + to add your first training",
                            fontSize = 12.sp,
                            color = TextGray
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // Przycisk + Add
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color(0xFFFBEADB))
                                .clickable { showAddDialog = true }
                                .padding(horizontal = 24.dp, vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "+ Add",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF8D5B4C)
                            )
                        }
                    }
                }
            } else {
                // Lista wpisów treningowych
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    dayTrainings.forEachIndexed { index, training ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(2.dp, RoundedCornerShape(14.dp))
                                .background(White, RoundedCornerShape(14.dp))
                                .border(1.dp, BorderGray.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(Color(0xFFEFF8DE), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_trophy),
                                        contentDescription = "Trophy",
                                        tint = Color(0xFF8BBA25),
                                        modifier = Modifier.size(22.dp)
                                    )
                                }

                                Column {
                                    Text(
                                        text = training,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = DarkNavy
                                    )
                                    Text(
                                        text = "Completed workout",
                                        fontSize = 12.sp,
                                        color = TextGray
                                    )
                                }
                            }

                            // Przycisk Usuń
                            IconButton(
                                onClick = { onDeleteTraining(selectedDate, index) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete training",
                                    tint = ErrorRed.copy(alpha = 0.8f),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Szybki przycisk dodawania kolejnego treningu
                    Button(
                        onClick = { showAddDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = DarkNavy),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "+ Add another training",
                            fontWeight = FontWeight.Bold,
                            color = White
                        )
                    }
                }
            }
        }
    }

    // DIALOG: Dodaj trening
    if (showAddDialog) {
        Dialog(onDismissRequest = {
            showAddDialog = false
            newTrainingName = ""
        }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                border = BorderStroke(1.dp, BorderGray)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Add Training Entry",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkNavy
                    )

                    OutlinedTextField(
                        value = newTrainingName,
                        onValueChange = { newTrainingName = it },
                        placeholder = { Text("e.g. Morning Jog, Weightlifting") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DarkNavy,
                            unfocusedBorderColor = BorderGray,
                            cursorColor = DarkNavy
                        ),
                        singleLine = true
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                showAddDialog = false
                                newTrainingName = ""
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, BorderGray)
                        ) {
                            Text("Cancel", color = TextGray)
                        }

                        Button(
                            onClick = {
                                if (newTrainingName.isNotBlank()) {
                                    onAddTraining(selectedDate, newTrainingName.trim())
                                    showAddDialog = false
                                    newTrainingName = ""
                                }
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = DarkNavy)
                        ) {
                            Text("Save", color = White)
                        }
                    }
                }
            }
        }
    }
    }
}

/**
 * Pojedyncza karta kroku konfiguracji w karuzeli poziomej.
 */
@Composable
private fun SetupStepCard(
    title: String,
    subtitle: String,
    isCompleted: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .height(170.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCompleted) Color(0xFFF0FAE0) else White
        ),
        border = BorderStroke(1.dp, if (isCompleted) Color(0xFFBEE86B) else Color(0xFFE8EDE0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Ikona gwiazdek w jasnym kółku (top-left jak na mockupie)
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(Color(0xFFE5F7C0), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.size(18.dp)) {
                    drawSparkle(Offset(size.width * 0.4f, size.height * 0.55f), size.width * 0.35f, Color(0xFF6B8A1A))
                    drawSparkle(Offset(size.width * 0.8f, size.height * 0.25f), size.width * 0.18f, Color(0xFF6B8A1A))
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Tytuł pogrubiony
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = DarkNavy,
                lineHeight = 16.sp
            )

            // Opis szary, mniejszy
            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = TextGray,
                lineHeight = 13.sp
            )
        }
    }
}

/**
 * Funkcja rysująca gwiazdki w Canvas - wygenerowane - nie moglam pobrac ikony XD
 */
private fun DrawScope.drawSparkle(center: Offset, sparkleSize: Float, color: Color) {
    val path = Path().apply {
        moveTo(center.x, center.y - sparkleSize)
        quadraticTo(center.x, center.y, center.x + sparkleSize, center.y)
        quadraticTo(center.x, center.y, center.x, center.y + sparkleSize)
        quadraticTo(center.x, center.y, center.x - sparkleSize, center.y)
        quadraticTo(center.x, center.y, center.x, center.y - sparkleSize)
        close()
    }
    drawPath(path, color)
}

@Preview(showBackground = true)
@Composable
private fun JournalViewPreview() {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var trainingEntries by remember {
        mutableStateOf(
            mapOf<LocalDate, List<String>>()
        )
    }

    Box(
        modifier = Modifier
            .background(Color(0xFFF5F9ED))
            .fillMaxSize()
    ) {
        JournalView(
            selectedDate = selectedDate,
            onDateSelected = { selectedDate = it },
            trainingEntries = trainingEntries,
            onAddTraining = { date, name ->
                val current = trainingEntries[date] ?: emptyList()
                trainingEntries = trainingEntries + (date to (current + name))
            },
            onDeleteTraining = { date, idx ->
                val current = trainingEntries[date] ?: emptyList()
                val updated = current.filterIndexed { index, _ -> index != idx }
                trainingEntries = trainingEntries + (date to updated)
            },
            step1Completed = true,
            onStep1Toggle = {},
            step2Completed = false,
            onStep2Toggle = {},
            step3Completed = false,
            onStep3Toggle = {},
            currentTab = "Journal",
            onTabSelected = {},
            onBackClick = {}
        )
    }
}
