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

enum class TaskCategory(
    val displayName: String,
    val shortName: String,
    val tip: String
) {
    URGENT_IMPORTANT("Urgent & Important", "Urgent & Important", "Do it now"),
    URGENT_NOT_IMPORTANT("Urgent & Unimportant", "Urgent & Unimportant", "Delegate if possible"),
    NOT_URGENT_IMPORTANT("Not Urgent & Important", "Not Urgent & Important", "Plan and schedule"),
    NOT_URGENT_NOT_IMPORTANT("Not Urgent & Unimportant", "Not Urgent & Unimportant", "Eliminate or limit")
}
