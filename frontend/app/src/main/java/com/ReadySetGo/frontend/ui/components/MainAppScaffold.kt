package com.ReadySetGo.frontend.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Główny szablon aplikacji stanowiący bazę dla innych ekranów.
 *
 * Komponent integruje stały, górny pasek nawigacji (TopBar) z zewnętrznym, szklanym
 * paskiem dolnym [GlassyBottomBar]. W strukturze paska górnego wykorzystywany jest
 * bezpośrednio komponent [HighlightedTitle].
 *
 * @param currentTab Identyfikator aktualnie wybranej i aktywnej zakładki systemu (np. "Home", "Map", "Start", "Journal", "Profile").
 * @param onTabSelected Callback wywoływany przy zmianie aktywnej zakładki dolnego paska nawigacji.
 * @param onBackClick Callback dla przycisku wstecz (lewa strona paska górnego).
 * @param onStatsClick Callback dla przycisku akcji statystyk (prawa strona paska górnego).
 * @param content Slot na główną, unikalną zawartość roboczą renderowanego ekranu.
 */
@Composable
fun MainAppScaffold(
    currentTab: String,
    onTabSelected: (String) -> Unit,
    onBackClick: () -> Unit,
    onStatsClick: () -> Unit,
    title: String = "TBD",
    content: @Composable (PaddingValues) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F9ED))
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .height(68.dp)
                        .background(Color.White)
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Przycisk wstecz
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .border(1.dp, Color(0xFFD9D9D9), CircleShape)
                            .background(Color(0xFFD9D9D9), CircleShape)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { onBackClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back Button",
                            tint = Color(0xFF333333),
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Tekst oraz podświetlenie gradientowe dopasowują się automatycznie do wyrazu
                    HighlightedTitle(
                        text = title
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // Przycisk Statystyk
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .border(1.dp, Color(0xFFD9D9D9), CircleShape)
                            .background(Color(0xFFD9D9D9), CircleShape)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { onStatsClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier.size(18.dp, 16.dp)) {
                            // Lewy słupek wykresu
                            drawRect(
                                color = Color(0xFF333333),
                                topLeft = androidx.compose.ui.geometry.Offset(0f, 6.dp.toPx()),
                                size = androidx.compose.ui.geometry.Size(4.dp.toPx(), 10.dp.toPx())
                            )
                            // Środkowy, najwyższy słupek wykresu
                            drawRect(
                                color = Color(0xFF333333),
                                topLeft = androidx.compose.ui.geometry.Offset(6.dp.toPx(), 0f),
                                size = androidx.compose.ui.geometry.Size(4.dp.toPx(), 16.dp.toPx())
                            )
                            // Prawy, najniższy słupek wykresu
                            drawRect(
                                color = Color(0xFF333333),
                                topLeft = androidx.compose.ui.geometry.Offset(12.dp.toPx(), 10.dp.toPx()),
                                size = androidx.compose.ui.geometry.Size(4.dp.toPx(), 6.dp.toPx())
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            // Obszar kontenera roboczego rozciągający się na pełną wysokość urządzenia pod navbarem
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = innerPadding.calculateTopPadding())
            ) {
                // Przekazujemy bezpieczny padding dolny wynoszący 95.dp (wysokość tła paska 83.dp + marginesy),
                // dzięki czemu treść innych ekranów chowa się pod szkłem, ale nie jest permanentnie zablokowana.
                content(PaddingValues(bottom = 95.dp))
            }
        }

        GlassyBottomBar(
            currentTab = currentTab,
            onTabSelected = onTabSelected,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Preview(showBackground = true, name = "Główny Szablon - Podgląd Systemowy")
@Composable
fun MainAppScaffoldPreview() {
    var selectedTab by remember { mutableStateOf("Journal") }

    MainAppScaffold(
        currentTab = selectedTab,
        onTabSelected = { selectedTab = it },
        onBackClick = {},
        onStatsClick = {}
    ) { paddingValues ->
        // Tu elementy ekranu
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

        }
    }
}