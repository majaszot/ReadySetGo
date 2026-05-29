package com.ReadySetGo.frontend.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ReadySetGo.frontend.ui.theme.DarkNavy
import com.ReadySetGo.frontend.ui.theme.LimeGreen
import com.ReadySetGo.frontend.ui.theme.TextGray
import com.ReadySetGo.frontend.ui.theme.White
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

/**
 * Komponent kalendarza z wyborem dnia i nawigacją między miesiącami.
 * Wyświetla miesiąc, dni tygodnia oraz dni miesiąca w układzie siatki.
 * Wybrany dzień jest wyróżniony zielonym tłem.
 *
 * @param selectedDate Aktualnie wybrana data.
 * @param onDateSelected Funkcja wywoływana po kliknięciu dnia.
 * @param modifier Modyfikator.
 */
@Composable
fun Calendar(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentMonth by remember {
        mutableStateOf(YearMonth.from(selectedDate))
    }

    val daysInMonth = currentMonth.lengthOfMonth()

    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(336.dp)
                .offset(y = 6.dp)
                .padding(horizontal = 2.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color.Black.copy(alpha = 0.08f))
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(336.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(White)
                .padding(horizontal = 28.dp, vertical = 22.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "<",
                    color = TextGray,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable {
                        currentMonth = currentMonth.minusMonths(1)
                    }
                )

                Text(
                    text = "${currentMonth.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH)} ${currentMonth.year}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = DarkNavy,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = ">",
                    color = TextGray,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable {
                        currentMonth = currentMonth.plusMonths(1)
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                listOf("SAN", "MON", "TUE", "WED", "THU", "FRI", "SAT").forEach { day ->
                    Text(
                        text = day,
                        modifier = Modifier.weight(1f),
                        color = TextGray,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                repeat(5) { row ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        repeat(7) { column ->
                            val day = row * 7 + column + 1

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(28.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                if (day <= daysInMonth) {
                                    val date = currentMonth.atDay(day)
                                    val isSelected = date == selectedDate

                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(
                                                if (isSelected) LimeGreen else Color.Transparent
                                            )
                                            .clickable { onDateSelected(date) },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = day.toString(),
                                            color = if (isSelected) White else DarkNavy,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CalendarPreview() {
    Box(
        modifier = Modifier
            .background(Color(0xFFEFF2E6))
            .padding(24.dp)
    ) {
        Calendar(
            selectedDate = LocalDate.of(2021, 9, 19),
            onDateSelected = {},
            modifier = Modifier
                .width(352.dp)
                .height(336.dp)
        )
    }
}