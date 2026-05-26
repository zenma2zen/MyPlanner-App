package com.myplanner.app.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myplanner.app.domain.model.*
import com.myplanner.app.ui.theme.*
import java.time.format.DateTimeFormatter

@Composable
fun NeuBox(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(20.dp),
    backgroundColor: Color = DeepSpace,
    elevation: Dp = 6.dp,
    isPressed: Boolean = false,
    content: @Composable BoxScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val activePressed = isPressed || pressed

    val shadowColor1 = NeuDarkShadow
    val shadowColor2 = NeuLightShadow

    val animatedElevation by animateDpAsState(
        targetValue = if (activePressed) 2.dp else elevation,
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessLow)
    )

    Box(
        modifier = modifier
            .drawBehind {
                val shadowRadius = animatedElevation.toPx()
                val offset = if (activePressed) shadowRadius / 2 else shadowRadius

                if (!activePressed) {
                    drawIntoCanvas { canvas ->
                        val paint = Paint()
                        val frameworkPaint = paint.asFrameworkPaint()
                        frameworkPaint.color = shadowColor1.toArgb()
                        frameworkPaint.setShadowLayer(shadowRadius, offset, offset, shadowColor1.toArgb())
                        canvas.drawRoundRect(0f, 0f, size.width, size.height, shape.topStart.toPx(size, this), shape.topStart.toPx(size, this), paint)

                        frameworkPaint.color = shadowColor2.toArgb()
                        frameworkPaint.setShadowLayer(shadowRadius, -offset, -offset, shadowColor2.toArgb())
                        canvas.drawRoundRect(0f, 0f, size.width, size.height, shape.topStart.toPx(size, this), shape.topStart.toPx(size, this), paint)
                    }
                }
            }
            .clip(shape)
            .background(backgroundColor)
            .then(if (activePressed) Modifier.border(1.dp, NeonBlue.copy(0.3f), shape) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = RoundedCornerShape(20.dp)
    val baseModifier = modifier
        .clip(shape)
        .background(
            Brush.linearGradient(listOf(Color(0x1AFFFFFF), Color(0x0DFFFFFF))),
            shape
        )
        .border(1.dp, Color(0x26FFFFFF), shape)

    if (onClick != null) {
        Column(baseModifier.clickable(onClick = onClick).padding(16.dp), content = content)
    } else {
        Column(baseModifier.padding(16.dp), content = content)
    }
}

@Composable
fun PriorityBadge(priority: Priority) {
    val (color, label) = when (priority) {
        Priority.HIGH   -> PriorityHigh to "Tinggi"
        Priority.MEDIUM -> PriorityMed  to "Sedang"
        Priority.LOW    -> PriorityLow  to "Rendah"
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color.copy(alpha = 0.2f))
            .border(1.dp, color.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(label, color = color, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun StatusBadge(status: TaskStatus) {
    val (color, label, icon) = when (status) {
        TaskStatus.PENDING     -> Triple(Color(0xFF94A3B8), "Belum", Icons.Rounded.RadioButtonUnchecked)
        TaskStatus.IN_PROGRESS -> Triple(NeonBlue, "Dikerjakan", Icons.Rounded.Pending)
        TaskStatus.DONE        -> Triple(PriorityLow, "Selesai", Icons.Rounded.CheckCircle)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color.copy(alpha = 0.15f))
            .padding(horizontal = 8.dp, vertical = 3.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(icon, null, tint = color, modifier = Modifier.size(12.dp))
        Text(label, color = color, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun TaskCard(task: Task, onToggle: () -> Unit, onClick: () -> Unit, onDelete: () -> Unit) {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")
    var isChecked by remember { mutableStateOf(task.status == TaskStatus.DONE) }
    var isExpanded by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(if (isChecked) 0.98f else 1f)

    NeuBox(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .clickable { isExpanded = !isExpanded },
        backgroundColor = if (isChecked) DeepSpace.copy(0.7f) else SurfaceDark
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        isChecked = !isChecked
                        onToggle()
                    },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        if (isChecked) Icons.Rounded.CheckCircle else Icons.Rounded.RadioButtonUnchecked,
                        contentDescription = "Toggle",
                        tint = if (isChecked) PriorityLow else NeonBlue,
                        modifier = Modifier.size(26.dp)
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        task.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (isChecked) Color(0xFF64748B) else Color.White,
                        fontWeight = FontWeight.Bold,
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1
                    )
                    if (task.subject.isNotEmpty()) {
                        Text(task.subject, fontSize = 12.sp, color = NeonCyan.copy(0.8f))
                    }
                    if (!isExpanded && task.dueDate != null) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Rounded.Schedule, null, tint = Color(0xFF94A3B8), modifier = Modifier.size(12.dp))
                            Spacer(Modifier.width(4.dp))
                            Text(task.dueDate.format(DateTimeFormatter.ofPattern("dd MMM, HH:mm")), fontSize = 11.sp, color = Color(0xFF94A3B8))
                        }
                    }
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Rounded.DeleteOutline, null, tint = Color(0xFF64748B), modifier = Modifier.size(20.dp))
                }
            }

            if (isExpanded) {
                Spacer(Modifier.height(12.dp))
                NeonDivider()
                Spacer(Modifier.height(12.dp))
                
                if (task.description.isNotEmpty()) {
                    Text(
                        "Deskripsi:",
                        style = MaterialTheme.typography.labelMedium,
                        color = NeonBlue,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        task.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFE2E8F0),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Spacer(Modifier.height(8.dp))
                }

                if (task.dueDate != null) {
                    Text(
                        "Tenggat Waktu:",
                        style = MaterialTheme.typography.labelMedium,
                        color = NeonBlue,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Icon(Icons.Rounded.Event, null, tint = Color.White, modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(6.dp))
                        Text(
                            task.dueDate.format(formatter),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    PriorityBadge(task.priority)
                    StatusBadge(task.status)
                }
            } else if (!isExpanded) {
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    PriorityBadge(task.priority)
                    StatusBadge(task.status)
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, icon: ImageVector, action: (@Composable () -> Unit)? = null) {
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = NeonBlue, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(8.dp))
        Text(title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.weight(1f))
        action?.invoke()
    }
}

@Composable
fun NeonDivider() {
    Box(
        Modifier.fillMaxWidth().height(1.dp)
            .background(Brush.horizontalGradient(listOf(Color.Transparent, NeonBlue.copy(0.4f), Color.Transparent)))
    )
}

@Composable
fun SubjectScheduleCard(schedule: SubjectSchedule, onDelete: () -> Unit) {
    val color = Color(schedule.color)
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(48.dp).clip(RoundedCornerShape(14.dp)).background(color.copy(0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(schedule.subjectName.take(2).uppercase(), color = color, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(schedule.subjectName, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
                if (schedule.teacherName.isNotEmpty())
                    Text("👨‍🏫 ${schedule.teacherName}", fontSize = 11.sp, color = Color(0xFF94A3B8))
                Text(
                    "🕐 ${schedule.startTime} - ${schedule.endTime}${if (schedule.room.isNotEmpty()) " · 🚪 ${schedule.room}" else ""}",
                    fontSize = 11.sp, color = NeonBlue
                )
            }
            IconButton(onClick = onDelete, modifier = Modifier.size(36.dp)) {
                Icon(Icons.Rounded.DeleteOutline, null, tint = Color(0xFF64748B), modifier = Modifier.size(18.dp))
            }
        }
    }
}
