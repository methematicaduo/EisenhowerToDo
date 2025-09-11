package com.example.eisenhowertodo.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.eisenhowertodo.model.Task
import com.example.eisenhowertodo.model.TaskCategory
import com.example.eisenhowertodo.ui.theme.White
import com.example.eisenhowertodo.ui.utils.getCategoryColor

@Composable
fun CategoryCard(
    category: TaskCategory,
    tasks: List<Task>,
    onTaskToggle: (String) -> Unit,
    onTaskDelete: (String) -> Unit,
    onDragStart: (Task, Offset) -> Unit,
    onDragOffsetChange: (Offset) -> Unit,
    onDragEnd: () -> Unit,
    onCategoryClick: (() -> Unit)? = null,
    isHighlighted: Boolean = false,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .then(
                if (onCategoryClick != null) {
                    Modifier.clickable { onCategoryClick() }
                } else {
                    Modifier
                }
            ),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isHighlighted) 
                getCategoryColor(category).copy(alpha = 0.3f) 
            else 
                getCategoryColor(category).copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isHighlighted) 8.dp else 2.dp
        ),
        border = if (isHighlighted) 
            BorderStroke(3.dp, getCategoryColor(category)) 
        else 
            BorderStroke(1.dp, getCategoryColor(category).copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Category header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Colored dot
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(getCategoryColor(category))
                )
                
                // Category title
                Text(
                    text = category.displayName,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        
            Spacer(modifier = Modifier.height(12.dp))
            
            // Tasks list
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tasks) { taskItem ->
                    DraggableTaskItem(
                        task = taskItem,
                        category = category,
                        onToggle = { onTaskToggle(taskItem.id) },
                        onDelete = { onTaskDelete(taskItem.id) },
                        onDragStart = { task, offset -> onDragStart(task, offset) },
                        onDragOffsetChange = onDragOffsetChange,
                        onDragEnd = onDragEnd
                    )
                }
            }
        }
    }
}

