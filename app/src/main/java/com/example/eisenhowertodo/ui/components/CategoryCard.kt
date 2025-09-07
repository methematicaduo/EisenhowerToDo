package com.example.eisenhowertodo.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import com.example.eisenhowertodo.model.Task
import com.example.eisenhowertodo.model.TaskCategory
import com.example.eisenhowertodo.ui.theme.UrgentImportantRed
import com.example.eisenhowertodo.ui.theme.UrgentNotImportantBlue
import com.example.eisenhowertodo.ui.theme.NotUrgentImportantYellow
import com.example.eisenhowertodo.ui.theme.NotUrgentNotImportantGreen
import com.example.eisenhowertodo.ui.theme.TextGray
import com.example.eisenhowertodo.ui.theme.CompletedTextGray
import com.example.eisenhowertodo.ui.theme.BorderGray
import com.example.eisenhowertodo.ui.theme.White
import com.example.eisenhowertodo.ui.theme.Black

// Extension function to get category-specific color for dots and accents
private fun getCategoryColor(category: TaskCategory): Color {
    return when (category) {
        TaskCategory.URGENT_IMPORTANT -> UrgentImportantRed
        TaskCategory.URGENT_NOT_IMPORTANT -> UrgentNotImportantBlue
        TaskCategory.NOT_URGENT_IMPORTANT -> NotUrgentImportantYellow
        TaskCategory.NOT_URGENT_NOT_IMPORTANT -> NotUrgentNotImportantGreen
    }
}

@Composable
fun CategoryCard(
    category: TaskCategory,
    tasks: List<Task>,
    onTaskToggle: (String) -> Unit,
    onTaskDelete: (String) -> Unit,
    onCategoryClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var showTooltip by remember { mutableStateOf(false) }
    
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
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Category header with white text and colored dot
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
                    
                    // Category title in white
                    Text(
                        text = category.displayName,
                        style = MaterialTheme.typography.titleMedium,
                        color = White,
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
                    items(tasks) { task ->
                        TaskItem(
                            task = task,
                            category = category,
                            onToggle = { onTaskToggle(task.id) },
                            onDelete = { onTaskDelete(task.id) }
                        )
                    }
                }
            }
            
            // Tip icon at bottom-left corner
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Black.copy(alpha = 0.7f))
                    .clickable { showTooltip = !showTooltip },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "💡",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            
            // Auto-hide tooltip after 3 seconds
            LaunchedEffect(showTooltip) {
                if (showTooltip) {
                    delay(3000)
                    showTooltip = false
                }
            }
            
            // Balloon-style speech bubble
            if (showTooltip) {
                SpeechBubble(
                    text = category.tip,
                    categoryColor = getCategoryColor(category),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .offset(x = 12.dp, y = (-60).dp)
                        .zIndex(1f)
                )
            }
        }
    }
}

@Composable
private fun SpeechBubble(
    text: String,
    categoryColor: Color,
    modifier: Modifier = Modifier
) {
    val bubbleColor = categoryColor.copy(alpha = 0.15f)
    val textColor = categoryColor.copy(alpha = 0.9f)
    
    Box(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        // Main bubble
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = bubbleColor
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                style = MaterialTheme.typography.bodySmall,
                color = textColor,
                fontWeight = FontWeight.Medium
            )
        }
        
        // Triangle pointer
        Box(
            modifier = Modifier
                .offset(x = 8.dp, y = 32.dp)
                .size(12.dp)
        ) {
            androidx.compose.foundation.Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                val path = Path().apply {
                    moveTo(size.width / 2, size.height)
                    lineTo(0f, 0f)
                    lineTo(size.width, 0f)
                    close()
                }
                drawPath(
                    path = path,
                    color = bubbleColor
                )
            }
        }
    }
}

@Composable
private fun TaskItem(
    task: Task,
    category: TaskCategory,
    onToggle: () -> Unit,
    onDelete: () -> Unit,
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
        // Checkbox - modern circular design with larger touch area
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
                        contentDescription = "Tamamlandı",
                        tint = White,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        // Task content
        Column(
            modifier = Modifier.weight(1f)
        ) {
            // Task title - white for incomplete, gray for completed
            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyLarge,
                color = if (task.isCompleted) 
                    CompletedTextGray
                else 
                    White,
                fontWeight = FontWeight.Bold,
                textDecoration = if (task.isCompleted) 
                    TextDecoration.LineThrough 
                else 
                    TextDecoration.None,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            // Task description/subtitle - gray for all
            if (task.description.isNotEmpty()) {
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (task.isCompleted) 
                        CompletedTextGray.copy(alpha = 0.6f)
                    else 
                        TextGray,
                    textDecoration = if (task.isCompleted) 
                        TextDecoration.LineThrough 
                    else 
                        TextDecoration.None,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        
        // Delete button - show for all tasks (completed and non-completed)
        IconButton(
            onClick = { 
                onDelete()
            },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Sil",
                tint = TextGray,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
