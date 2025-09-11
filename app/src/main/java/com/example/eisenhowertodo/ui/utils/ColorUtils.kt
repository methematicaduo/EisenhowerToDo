package com.example.eisenhowertodo.ui.utils

import androidx.compose.ui.graphics.Color
import com.example.eisenhowertodo.model.TaskCategory
import com.example.eisenhowertodo.ui.theme.UrgentImportantRed
import com.example.eisenhowertodo.ui.theme.UrgentNotImportantBlue
import com.example.eisenhowertodo.ui.theme.NotUrgentImportantYellow
import com.example.eisenhowertodo.ui.theme.NotUrgentNotImportantGreen

fun getCategoryColor(category: TaskCategory): Color {
    return when (category) {
        TaskCategory.URGENT_IMPORTANT -> UrgentImportantRed
        TaskCategory.URGENT_NOT_IMPORTANT -> UrgentNotImportantBlue
        TaskCategory.NOT_URGENT_IMPORTANT -> NotUrgentImportantYellow
        TaskCategory.NOT_URGENT_NOT_IMPORTANT -> NotUrgentNotImportantGreen
    }
}
