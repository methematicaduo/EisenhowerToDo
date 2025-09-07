package com.example.eisenhowertodo.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eisenhowertodo.model.TaskCategory
import com.example.eisenhowertodo.ui.theme.UrgentImportantRed
import com.example.eisenhowertodo.ui.theme.UrgentNotImportantBlue
import com.example.eisenhowertodo.ui.theme.NotUrgentImportantYellow
import com.example.eisenhowertodo.ui.theme.NotUrgentNotImportantGreen
import com.example.eisenhowertodo.ui.theme.TextGray
import com.example.eisenhowertodo.ui.theme.White
import com.example.eisenhowertodo.ui.theme.Black
import com.example.eisenhowertodo.ui.theme.BorderGray

// Extension function to get category color
private fun getCategoryColor(category: TaskCategory): Color {
    return when (category) {
        TaskCategory.URGENT_IMPORTANT -> UrgentImportantRed
        TaskCategory.URGENT_NOT_IMPORTANT -> UrgentNotImportantBlue
        TaskCategory.NOT_URGENT_IMPORTANT -> NotUrgentImportantYellow
        TaskCategory.NOT_URGENT_NOT_IMPORTANT -> NotUrgentNotImportantGreen
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskDialog(
    onDismiss: () -> Unit,
    onAddTask: (String, String, TaskCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(TaskCategory.URGENT_IMPORTANT) }
    var isExpanded by remember { mutableStateOf(false) }
    
    val titleFocusRequester = remember { FocusRequester() }
    val descriptionFocusRequester = remember { FocusRequester() }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(20.dp)
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Black
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Header with title and close button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
            Text(
                    text = "Add New Task",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = White
                    )
                )
                
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = TextGray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))

            // Task title input
            ModernTextField(
                value = title,
                onValueChange = { title = it },
                placeholder = "Task title",
                focusRequester = titleFocusRequester,
                modifier = Modifier.fillMaxWidth()
            )

            // Task description input
            ModernTextField(
                value = description,
                onValueChange = { description = it },
                placeholder = "Description (optional)",
                focusRequester = descriptionFocusRequester,
                modifier = Modifier.fillMaxWidth(),
                isMultiline = true
            )

            // Category selection
            ModernDropdown(
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it },
                isExpanded = isExpanded,
                onExpandedChange = { isExpanded = it },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Cancel button
                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            width = 1.dp,
                            color = BorderGray,
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    Text(
                        text = "Cancel",
                        color = TextGray,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                // Add button
                Button(
                    onClick = {
                        if (title.isNotBlank()) {
                            onAddTask(title, description, selectedCategory)
                            onDismiss()
                        }
                    },
                    enabled = title.isNotBlank(),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (title.isNotBlank()) getCategoryColor(selectedCategory) else TextGray,
                        disabledContainerColor = TextGray
                    )
                ) {
                    Text(
                        text = "Add",
                        color = White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun ModernTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier,
    isMultiline: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    
    val borderColor by animateFloatAsState(
        targetValue = if (isFocused) 1f else 0.5f,
        animationSpec = tween(durationMillis = 200),
        label = "border_alpha"
    )
    
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = White.copy(alpha = borderColor),
                shape = RoundedCornerShape(12.dp)
            )
            .background(Black)
            .padding(16.dp)
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .focusable(),
            textStyle = TextStyle(
                color = White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            ),
            cursorBrush = SolidColor(White),
            singleLine = !isMultiline,
            maxLines = if (isMultiline) 3 else 1,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences
            ),
            interactionSource = interactionSource,
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = TextGray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
                innerTextField()
            }
        )
    }
}

@Composable
private fun ModernDropdown(
    selectedCategory: TaskCategory,
    onCategorySelected: (TaskCategory) -> Unit,
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = White.copy(alpha = 0.5f),
                shape = RoundedCornerShape(12.dp)
            )
            .background(Black)
            .clickable { onExpandedChange(!isExpanded) }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedCategory.displayName,
                color = getCategoryColor(selectedCategory),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Aç",
                tint = TextGray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
    
    // Dropdown menu
    if (isExpanded) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Black
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column {
                TaskCategory.values().forEach { category ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onCategorySelected(category)
                                onExpandedChange(false)
                            }
                            .padding(16.dp)
                    ) {
                        Text(
                            text = category.displayName,
                            color = getCategoryColor(category),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}
