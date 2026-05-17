package com.ReadySetGo.frontend.ui.components

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ReadySetGo.frontend.R
import androidx.compose.runtime.*

/**
 * Szklany pasek nawigacji dolnej (Glassmorphism) z ostrym UI i rozmytym tłem.
 * * Odwzorowuje parametry: frost 25, białe tło 50% przezroczystości, zaokrąglenie 20px (dp)
 * na górnych rogach oraz specyficzny układ przycisku START z efektem Highlighted Title.
 *
 * @param currentTab Identyfikator aktualnie zaznaczonej zakładki.
 * @param onTabSelected Callback informujący o kliknięciu danego przycisku.
 *
 */
@Composable
fun GlassyBottomBar(
    currentTab: String,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        contentAlignment = Alignment.BottomCenter
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(83.dp)
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .then(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        Modifier.graphicsLayer {
                            // Parametr frost: 25 -> promień rozmycia ustawiony na 25f
                            renderEffect = android.graphics.RenderEffect.createBlurEffect(
                                25f, 25f, android.graphics.Shader.TileMode.CLAMP
                            ).asComposeRenderEffect()
                        }
                    } else Modifier
                )
                // Białe tło o stałej przezroczystości 50%
                // Dodatkowo ukośny gardient - tak jakby światło
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.65f), // Doświetlenie z lewego górnego rogu
                            Color.White.copy(alpha = 0.50f)
                        )
                    )
                )
                // obramowanie
                .border(
                    width = 1.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.60f),
                            Color.White.copy(alpha = 0.15f)
                        )
                    ),
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                )
        )
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(83.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            GlassyNavigationItem(
                label = "Home",
                iconRes = R.drawable.ic_home,
                isSelected = currentTab == "Home",
                onClick = { onTabSelected("Home") }
            )
            GlassyNavigationItem(
                label = "Map",
                iconRes = R.drawable.ic_location,
                isSelected = currentTab == "Map",
                onClick = { onTabSelected("Map") }
            )

            // Przestrzeń dla przycisku Start
            Spacer(modifier = Modifier.width(80.dp))

            GlassyNavigationItem(
                label = "Journal",
                iconRes = R.drawable.ic_notes,
                isSelected = currentTab == "Journal",
                onClick = { onTabSelected("Journal") }
            )
            GlassyNavigationItem(
                label = "Profile",
                iconRes = R.drawable.ic_user,
                isSelected = currentTab == "Profile",
                onClick = { onTabSelected("Profile") }
            )
        }

        Box(
            modifier = Modifier
                .offset(y = (-16).dp)
                .wrapContentSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Główny przycisk start
                Box(
                    modifier = Modifier
                        .size(68.dp)
                        .shadow(6.dp, CircleShape)
                        .background(Color(0xFFBFF23A), CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onTabSelected("Start") },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_trophy),
                        contentDescription = "Start Icon",
                        tint = Color(0xFF2C3E50),
                        modifier = Modifier.size(45.dp) // wieksza
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Box(
                    modifier = Modifier.width(IntrinsicSize.Max),
                    contentAlignment = Alignment.BottomCenter
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                            .offset(y = (-2).dp)
                            .background(Color(0xFFBFF23A).copy(alpha = 0.8f), RoundedCornerShape(2.dp))
                    )

                    Text(
                        text = "Start",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2C3E50)
                    )
                }
            }
        }
    }
}

/**
 * Pojedynczy element nawigacyjny.
 * * Odzwierciedla kolory: aktywny pomarańczowy oraz nieaktywny szary.
 */
@Composable
private fun RowScope.GlassyNavigationItem(
    label: String,
    iconRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val activeColor = Color(0xFFF97316)
    val inactiveColor = Color(0xFF7D7F79)

    // Ikony
    Column(
        modifier = Modifier
            .weight(1f)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier.size(26.dp),
            tint = if (isSelected) activeColor else inactiveColor
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) activeColor else inactiveColor
        )
    }
}

@Preview(showBackground = true, name = "Szklany Pasek - Stan Statyczny")
@Composable
fun GlassyBottomBarStaticPreview() {
    var activeTab by remember { mutableStateOf("Journal") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F9ED))
            .padding(top = 40.dp)
    ) {
        GlassyBottomBar(
            currentTab = activeTab,
            onTabSelected = { activeTab = it }
        )
    }
}

@Preview(showBackground = true, name = "Szklany Pasek - Test Rozmycia Tła")
@Composable
fun GlassyBottomBarBlurTestPreview() {
    var activeTab by remember { mutableStateOf("Home") }


    Box(
        modifier = Modifier
            .width(390.dp)
            .height(160.dp)
    ) {

        Row(modifier = Modifier.fillMaxSize()) {

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .background(Color(0xFFBFF23A))
            )

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .background(Color(0xFFF5965D))
            )
        }

        GlassyBottomBar(
            currentTab = activeTab,
            onTabSelected = { activeTab = it },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}