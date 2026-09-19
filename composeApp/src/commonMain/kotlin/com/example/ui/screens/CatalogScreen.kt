package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.EquipmentCategory
import com.example.data.model.EquipmentItem
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

@Composable
fun CatalogScreen(
    viewModel: UnipointViewModel,
    onBookItem: (EquipmentItem) -> Unit
) {
    val selectedCat by viewModel.selectedCategory.collectAsState()
    val allItems = viewModel.catalogItems
    var itemForDetails by remember { mutableStateOf<EquipmentItem?>(null) }

    val filteredItems = remember(selectedCat) {
        if (selectedCat == EquipmentCategory.ALL) allItems
        else allItems.filter { it.category == selectedCat }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Slate100)
            .testTag("screen_catalog")
    ) {
        // Top categories scrollable row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            EquipmentCategory.entries.forEach { cat ->
                val isSelected = selectedCat == cat
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.selectCategory(cat) },
                    label = {
                        Text(
                            text = cat.label,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = UnipointNavy,
                        selectedLabelColor = Color.White,
                        containerColor = Slate100,
                        labelColor = Slate800
                    ),
                    modifier = Modifier.testTag("filter_chip_${cat.name.lowercase()}")
                )
            }
        }

        // Equipment list
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${filteredItems.size} ATTREZZATURE DISPONIBILI",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate600
                    )
                    Text(
                        text = "Tariffe Cantiere 2026",
                        fontSize = 11.sp,
                        color = Slate600
                    )
                }
            }

            items(filteredItems, key = { it.id }) { item ->
                EquipmentCard(
                    item = item,
                    onShowDetails = { itemForDetails = item },
                    onBook = { onBookItem(item) }
                )
            }
        }
    }

    // Technical Detail Dialog
    itemForDetails?.let { item ->
        Dialog(onDismissRequest = { itemForDetails = null }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = UnipointAmber.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = item.category.label,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF92400E),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        IconButton(
                            onClick = { itemForDetails = null },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Chiudi")
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = item.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = UnipointNavy
                    )

                    Text(
                        text = item.subtitle,
                        fontSize = 12.sp,
                        color = Slate600
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = item.description,
                        fontSize = 13.sp,
                        color = Slate700,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "CARATTERISTICHE PRINCIPALI",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate600
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    item.features.forEach { feat ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = UnipointGreen,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = feat, fontSize = 12.sp, color = Slate800)
                        }
                    }

                    HorizontalDivider(color = Slate200, modifier = Modifier.padding(vertical = 14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Tariffa base:", fontSize = 10.sp, color = Slate600)
                            Text(
                                text = "€ ${item.dailyRate.toInt()},00 / giorno",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = UnipointNavy
                            )
                        }

                        Button(
                            onClick = {
                                itemForDetails = null
                                onBookItem(item)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = UnipointAmber,
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.DateRange, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Prenota Ora", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EquipmentCard(
    item: EquipmentItem,
    onShowDetails: () -> Unit,
    onBook: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("equipment_card_${item.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = UnipointNavy.copy(alpha = 0.08f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = item.category.label,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = UnipointNavy,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                if (item.badge != null) {
                    Surface(
                        color = UnipointAmber,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = item.badge,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = item.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                color = Slate900
            )

            Text(
                text = item.subtitle,
                fontSize = 12.sp,
                color = Slate600
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Spec chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SpecBadge(icon = Icons.Default.FitnessCenter, label = item.capacity, modifier = Modifier.weight(1f))
                SpecBadge(icon = Icons.Default.Height, label = item.maxWorkHeight, modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Verified,
                    contentDescription = null,
                    tint = UnipointGreen,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Norma: ${item.certification}",
                    fontSize = 11.sp,
                    color = Slate700,
                    fontWeight = FontWeight.Medium
                )
            }

            HorizontalDivider(color = Slate100, modifier = Modifier.padding(vertical = 12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "€ ${item.dailyRate.toInt()},00 / gg",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = UnipointNavy
                    )
                    Text(
                        text = "o € ${item.monthlyRate.toInt()},00 / mese",
                        fontSize = 11.sp,
                        color = Slate600
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = onShowDetails,
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text("Dettagli", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = UnipointNavy)
                    }

                    Button(
                        onClick = onBook,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = UnipointAmber,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                        modifier = Modifier.testTag("book_button_${item.id}")
                    ) {
                        Text("Prenota", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun SpecBadge(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Slate100,
        shape = RoundedCornerShape(6.dp),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = Slate600, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = label, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = Slate800, maxLines = 1)
        }
    }
}
