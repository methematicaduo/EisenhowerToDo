package com.example.eisenhowertodo.model

import java.util.UUID

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String = "",
    val category: TaskCategory,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

enum class TaskCategory(val displayName: String, val tip: String) {
    URGENT_IMPORTANT(
        displayName = "Urgent & Important",
        tip = "Do these tasks first! They're both urgent and important."
    ),
    URGENT_NOT_IMPORTANT(
        displayName = "Urgent & Not Important", 
        tip = "These need quick attention but aren't critical. Delegate if possible."
    ),
    NOT_URGENT_IMPORTANT(
        displayName = "Not Urgent & Important",
        tip = "Schedule these for later. They're important for long-term goals."
    ),
    NOT_URGENT_NOT_IMPORTANT(
        displayName = "Not Urgent & Not Important",
        tip = "Consider eliminating these tasks. They might be time wasters."
    )
}
