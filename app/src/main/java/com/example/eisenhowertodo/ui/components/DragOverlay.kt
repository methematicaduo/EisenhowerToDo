package com.example.eisenhowertodo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.eisenhowertodo.model.Task
import com.example.eisenhowertodo.model.TaskCategory
import com.example.eisenhowertodo.ui.theme.White
import com.example.eisenhowertodo.ui.utils.getCategoryColor

@Composable
fun DragOverlay(
    isDragging: Boolean,
    draggedTask: Task?,
    dragOffset: androidx.compose.ui.geometry.Offset,
    modifier: Modifier = Modifier
) {
    if (isDragging && draggedTask != null) {
        println("DragOverlay: Showing task at offset: $dragOffset")
        Box(
            modifier = modifier
                .fillMaxSize()
                .zIndex(1000f)
        ) {
            // Dragged task that follows finger
            Row(
                modifier = Modifier
                    .offset(
                        x = dragOffset.x.dp,
                        y = dragOffset.y.dp
                    )
                    .scale(0.9f)
                    .background(
                        color = getCategoryColor(draggedTask.category),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp)
                    .shadow(8.dp, RoundedCornerShape(8.dp))
                    .zIndex(1001f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Checkbox
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(
                            color = if (draggedTask.isCompleted) Color.Green else Color.Transparent,
                            shape = CircleShape
                        )
                        .border(
                            width = 2.dp,
                            color = if (draggedTask.isCompleted) Color.Green else Color.Gray,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (draggedTask.isCompleted) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Completed",
                            tint = White,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                // Task text
                Text(
                    text = draggedTask.title,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

