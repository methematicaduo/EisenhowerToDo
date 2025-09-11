package com.example.eisenhowertodo.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.eisenhowertodo.model.Task
import com.example.eisenhowertodo.model.TaskCategory
import com.example.eisenhowertodo.ui.theme.BorderGray
import com.example.eisenhowertodo.ui.theme.CompletedTextGray
import com.example.eisenhowertodo.ui.theme.White
import com.example.eisenhowertodo.ui.utils.getCategoryColor

@Composable
fun DraggableTaskItem(
    task: Task,
    category: TaskCategory,
    onToggle: () -> Unit,
    onDelete: () -> Unit,
    onDragStart: (Task, androidx.compose.ui.geometry.Offset) -> Unit,
    onDragOffsetChange: (androidx.compose.ui.geometry.Offset) -> Unit,
    onDragEnd: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Animation for completed tasks
    val alpha by animateFloatAsState(
        targetValue = if (task.isCompleted) 0.6f else 1f,
        animationSpec = tween(durationMillis = 300),
        label = "task_alpha"
    )
    
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .alpha(alpha),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Checkbox
        Box(
            modifier = Modifier
                .size(32.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { 
                    onToggle() 
                },
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(
                        if (task.isCompleted) getCategoryColor(category)
                        else Color.Transparent
                    )
                    .border(
                        width = 2.dp,
                        color = if (task.isCompleted) getCategoryColor(category)
                        else BorderGray,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (task.isCompleted) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Completed",
                        tint = White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        // Task text
        Text(
            text = task.title,
            style = MaterialTheme.typography.bodyMedium,
            color = if (task.isCompleted) CompletedTextGray else Color.Black,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        
        // Delete button
        IconButton(
            onClick = onDelete,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.Gray,
                modifier = Modifier.size(16.dp)
            )
        }
    }
    
    // Drag detector - separate from buttons
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(task.id) {
                detectDragGestures(
                    onDragStart = { offset ->
                        println("DraggableTaskItem: Drag started at local offset: $offset")
                        onDragStart(task, offset)
                    },
                    onDragEnd = { 
                        onDragEnd()
                    },
                    onDrag = { change, _ ->
                        println("DraggableTaskItem: Drag change position: ${change.position}")
                        onDragOffsetChange(change.position)
                    }
                )
            }
    )
}

