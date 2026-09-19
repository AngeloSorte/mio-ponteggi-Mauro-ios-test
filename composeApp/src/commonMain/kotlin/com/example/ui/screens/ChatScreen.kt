package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.example.data.model.ChatMessageEntity
import com.example.ui.UnipointViewModel
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.UnipointAmber
import com.example.ui.theme.UnipointGreen
import com.example.ui.theme.UnipointNavy
import com.example.ui.theme.UnipointNavyDark

@Composable
fun ChatScreen(
    viewModel: UnipointViewModel,
    onCallPhone: () -> Unit
) {
    val messages by viewModel.chatMessages.collectAsState()
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    val quickQuestions = listOf(
        "Preventivo cantiere urgente",
        "Danni da grandine sul tetto",
        "Perizia drone per assicurazione",
        "P.I.M.U.S. e certificazioni"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Slate100)
            .testTag("screen_chat")
    ) {
        // Top direct contact bar
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(0.dp),
            elevation = CardDefaults.cardElevation(1.dp)
        ) {
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val isCompact = maxWidth < 380.dp
                val isUltraCompact = maxWidth < 330.dp

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = if (isUltraCompact) 8.dp else if (isCompact) 12.dp else 16.dp,
                            vertical = 10.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(if (isCompact) 8.dp else 10.dp)
                    ) {
                        Box {
                            Surface(
                                color = UnipointNavy,
                                shape = CircleShape,
                                modifier = Modifier.size(if (isCompact) 36.dp else 42.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Engineering,
                                        contentDescription = null,
                                        tint = UnipointAmber,
                                        modifier = Modifier.size(if (isCompact) 20.dp else 24.dp)
                                    )
                                }
                            }
                            // Online status badge
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(UnipointGreen)
                                    .align(Alignment.BottomEnd)
                            )
                        }

                        Column(modifier = Modifier.padding(end = 4.dp)) {
                            Text(
                                text = "Geom. Mauro Fiorenza",
                                fontSize = if (isUltraCompact) 12.sp else if (isCompact) 13.sp else 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate900,
                                maxLines = 1
                            )
                            Text(
                                text = "Resp. Tecnico • Online",
                                fontSize = if (isCompact) 10.sp else 11.sp,
                                color = UnipointGreen,
                                fontWeight = FontWeight.Medium,
                                maxLines = 1
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    Button(
                        onClick = onCallPhone,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = UnipointAmber,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(
                            horizontal = if (isUltraCompact) 6.dp else if (isCompact) 8.dp else 12.dp,
                            vertical = 6.dp
                        ),
                        modifier = Modifier.heightIn(min = 38.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = "Chiama",
                            modifier = Modifier.size(if (isCompact) 14.dp else 16.dp)
                        )
                        Spacer(modifier = Modifier.width(if (isCompact) 3.dp else 5.dp))
                        Text(
                            text = "345/1183711",
                            fontSize = if (isUltraCompact) 10.sp else if (isCompact) 11.sp else 12.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                    }
                }
            }
        }

        // Quick prompts chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White.copy(alpha = 0.7f))
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            quickQuestions.take(2).forEach { prompt ->
                Surface(
                    color = Slate100,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = prompt,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = UnipointNavy,
                        maxLines = 1,
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 6.dp)
                    )
                }
            }
        }

        HorizontalDivider(color = Slate200)

        // Chat Message List
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(messages, key = { it.id }) { msg ->
                ChatBubble(message = msg)
            }
        }

        // Bottom text field and send button
        Surface(
            color = Color.White,
            shadowElevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = { Text("Scrivi una richiesta o domanda tecnica...", fontSize = 13.sp) },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("chat_input_field"),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = UnipointNavy,
                        unfocusedBorderColor = Slate200
                    ),
                    maxLines = 3,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            if (inputText.isNotBlank()) {
                                viewModel.sendUserMessage(inputText)
                                inputText = ""
                            }
                        }
                    )
                )

                IconButton(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            viewModel.sendUserMessage(inputText)
                            inputText = ""
                        }
                    },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(if (inputText.isNotBlank()) UnipointNavy else Slate200)
                        .testTag("chat_send_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Invia",
                        tint = if (inputText.isNotBlank()) UnipointAmber else Slate600,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ChatBubble(message: ChatMessageEntity) {
    val isUser = message.isFromUser

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        if (!isUser) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(bottom = 2.dp)
            ) {
                Text(
                    text = message.sender,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = UnipointNavy
                )
                if (message.badge != null) {
                    Surface(
                        color = UnipointAmber.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = message.badge,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF92400E),
                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                        )
                    }
                }
            }
        }

        Surface(
            color = if (isUser) UnipointNavy else Color.White,
            shape = RoundedCornerShape(
                topStart = 14.dp,
                topEnd = 14.dp,
                bottomStart = if (isUser) 14.dp else 2.dp,
                bottomEnd = if (isUser) 2.dp else 14.dp
            ),
            shadowElevation = if (isUser) 1.dp else 1.5.dp,
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = message.text,
                    fontSize = 13.sp,
                    color = if (isUser) Color.White else Slate900,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                val timeStr = remember(message.timestamp) {
                    SimpleDateFormat("HH:mm", Locale.ITALIAN).format(Date(message.timestamp))
                }

                Text(
                    text = timeStr,
                    fontSize = 10.sp,
                    color = if (isUser) Color.White.copy(alpha = 0.65f) else Slate600,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}
