package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppScreen
import com.example.ui.ScaffoldConfig
import com.example.ui.UnipointViewModel
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.UnipointAmber
import com.example.ui.theme.UnipointGreen
import com.example.ui.theme.UnipointNavy
import com.example.ui.theme.UnipointNavyDark
import com.example.ui.theme.UnipointSky
import kotlin.math.roundToInt

@Composable
fun ConfiguratorScreen(
    viewModel: UnipointViewModel,
    onProceedToBooking: (String, Double) -> Unit
) {
    val config by viewModel.scaffoldConfig.collectAsState()
    var showAddressDialog by remember { mutableStateOf(false) }
    var siteAddressInput by remember { mutableStateOf("") }
    val structureTypes = listOf("Facciata Dritta", "Angolare a L", "Copertura / Tetto", "Torre")
    val perspectives = listOf("Assonometria 3D", "Vista Frontale", "Sezione")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Slate100)
            .testTag("screen_configurator"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Header
        item {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ViewInAr,
                        contentDescription = null,
                        tint = UnipointNavy,
                        modifier = Modifier.size(26.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "CONFIGURATORE PONTEGGIO",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = UnipointNavy
                    )
                }
                Text(
                    text = "Personalizza dimensioni, campate e accessori con anteprima interattiva e calcolo materiali PIMUS.",
                    fontSize = 12.sp,
                    color = Slate600
                )
            }
        }

        // Live Graphic Blueprint Canvas Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(290.dp)
                    .testTag("scaffold_canvas_card"),
                colors = CardDefaults.cardColors(containerColor = Slate900),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(3.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // Custom Scaffolding Rendering Canvas
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .testTag("scaffold_canvas")
                    ) {
                        drawScaffoldingBlueprint(config)
                    }

                    // Perspective Selector overlay
                    Row(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        perspectives.forEach { p ->
                            val isSel = config.viewPerspective == p
                            Box(
                                modifier = Modifier
                                    .background(
                                        if (isSel) UnipointAmber else Color.Black.copy(alpha = 0.5f),
                                        RoundedCornerShape(6.dp)
                                    )
                                    .clickable {
                                        viewModel.updateScaffoldConfig { it.copy(viewPerspective = p) }
                                    }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = p,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSel) Color.Black else Color.White
                                )
                            }
                        }
                    }

                    // Bottom info bar on Canvas
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth()
                            .background(Color.Black.copy(alpha = 0.6f))
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${config.widthMeters.toInt()}m L x ${config.heightMeters.toInt()}m H (${config.totalAreaSqMeters.toInt()} m²)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = UnipointAmber
                        )
                        Text(
                            text = "${config.bayCount} Campate • ${config.deckLevels} Piani di Lavoro",
                            fontSize = 11.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // Structure Type Selector
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "TIPOLOGIA STRUTTURA",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate600
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        structureTypes.forEach { type ->
                            val selected = config.structureType == type
                            FilterChip(
                                selected = selected,
                                onClick = {
                                    viewModel.updateScaffoldConfig { it.copy(structureType = type) }
                                },
                                label = {
                                    Text(
                                        text = type,
                                        fontSize = 11.sp,
                                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = UnipointNavy,
                                    selectedLabelColor = Color.White,
                                    containerColor = Slate100,
                                    labelColor = Slate800
                                ),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }

        // Dimension Sliders
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "LARGHEZZA FACCIATA",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate600
                        )
                        Text(
                            text = "${config.widthMeters.toInt()} metri",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            color = UnipointNavy
                        )
                    }

                    Slider(
                        value = config.widthMeters,
                        onValueChange = { newWidth ->
                            viewModel.updateScaffoldConfig { it.copy(widthMeters = newWidth.roundToInt().toFloat()) }
                        },
                        valueRange = 5f..50f,
                        steps = 44,
                        colors = SliderDefaults.colors(
                            thumbColor = UnipointAmber,
                            activeTrackColor = UnipointNavy
                        ),
                        modifier = Modifier.testTag("slider_width")
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ALTEZZA FACCIATA",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate600
                        )
                        Text(
                            text = "${config.heightMeters.toInt()} metri",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            color = UnipointNavy
                        )
                    }

                    Slider(
                        value = config.heightMeters,
                        onValueChange = { newHeight ->
                            viewModel.updateScaffoldConfig { it.copy(heightMeters = newHeight.roundToInt().toFloat()) }
                        },
                        valueRange = 4f..30f,
                        steps = 25,
                        colors = SliderDefaults.colors(
                            thumbColor = UnipointAmber,
                            activeTrackColor = UnipointNavy
                        ),
                        modifier = Modifier.testTag("slider_height")
                    )
                }
            }
        }

        // Accessories & Security Options
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "ACCESSORI & DOTAZIONI DI SICUREZZA",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate600
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    AccessoryToggle(
                        title = "Rete Antigrandine / Antipolvere",
                        subtitle = "Protezione teli microforati ad alta resistenza",
                        checked = config.includeNetting,
                        onCheckedChange = { checked ->
                            viewModel.updateScaffoldConfig { it.copy(includeNetting = checked) }
                        }
                    )

                    HorizontalDivider(color = Slate100, modifier = Modifier.padding(vertical = 8.dp))

                    AccessoryToggle(
                        title = "Montacarichi da Cantiere 500kg",
                        subtitle = "Elevatore elettrico a bandiera con freno di emergenza",
                        checked = config.includeHoist,
                        onCheckedChange = { checked ->
                            viewModel.updateScaffoldConfig { it.copy(includeHoist = checked) }
                        }
                    )

                    HorizontalDivider(color = Slate100, modifier = Modifier.padding(vertical = 8.dp))

                    AccessoryToggle(
                        title = "Piani con Botola & Scaletta Interna",
                        subtitle = "Passaggio sicuro tra livelli senza arrampicata esterna",
                        checked = config.includeLadderHatch,
                        onCheckedChange = { checked ->
                            viewModel.updateScaffoldConfig { it.copy(includeLadderHatch = checked) }
                        }
                    )

                    HorizontalDivider(color = Slate100, modifier = Modifier.padding(vertical = 8.dp))

                    AccessoryToggle(
                        title = "Fermapiedi e Parapetti di Sicurezza",
                        subtitle = "Protezione caduta oggetti a norma D.Lgs 81/08",
                        checked = config.includeToeBoards,
                        onCheckedChange = { checked ->
                            viewModel.updateScaffoldConfig { it.copy(includeToeBoards = checked) }
                        }
                    )
                }
            }
        }

        // Bill of Materials & P.I.M.U.S. Estimator
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "DISTINTA MATERIALI STIMATA",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate600
                        )
                        Surface(
                            color = UnipointGreen.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "P.I.M.U.S. Ready",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF065F46),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    MaterialRow(label = "Telai / Montanti verticali:", value = "${config.framesCount} pz")
                    MaterialRow(label = "Piani di calpestio in acciaio zincato:", value = "${config.decksCount} tavole")
                    MaterialRow(label = "Croci e correnti diagonali antivento:", value = "${config.diagonalsCount} aste")
                    MaterialRow(label = "Punti di ancoraggio a parete:", value = "${config.anchorsCount} tasselli")
                    MaterialRow(label = "Peso complessivo stimato:", value = "~ ${config.estimatedWeightKg} kg")

                    HorizontalDivider(color = Slate200, modifier = Modifier.padding(vertical = 10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "STIMA NOLEGGIO MENSILE",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate600
                            )
                            Text(
                                text = "€ ${config.estimatedMonthlyCost.toInt()},00 / mese",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Black,
                                color = UnipointNavy
                            )
                        }

                        Text(
                            text = "IVA e montaggio escl.",
                            fontSize = 11.sp,
                            color = Slate600
                        )
                    }
                }
            }
        }

        // Actions: Save Quote or Book Now
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = {
                        val title = "Ponteggio ${config.structureType} (${config.widthMeters.toInt()}x${config.heightMeters.toInt()}m)"
                        onProceedToBooking(title, config.estimatedMonthlyCost / 30.0)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = UnipointAmber,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("btn_proceed_to_booking")
                ) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Prenota Noleggio Questa Configurazione", fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = { showAddressDialog = true },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("btn_save_quote")
                ) {
                    Icon(imageVector = Icons.Default.Bookmark, contentDescription = null, tint = UnipointNavy)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Salva Preventivo per Questo Cantiere", fontWeight = FontWeight.Bold, color = UnipointNavy)
                }
            }
        }
    }

    // Save Quote Address Dialog
    if (showAddressDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showAddressDialog = false },
            title = {
                Text(
                    text = "Salva Preventivo Struttura",
                    fontWeight = FontWeight.Bold,
                    color = UnipointNavy
                )
            },
            text = {
                Column {
                    Text(
                        text = "Inserisci l'indirizzo o comune del cantiere per salvare questo calcolo:",
                        fontSize = 13.sp,
                        color = Slate700
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = siteAddressInput,
                        onValueChange = { siteAddressInput = it },
                        label = { Text("Indirizzo cantiere") },
                        placeholder = { Text("es. Via Roma 12, Milano") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.saveCurrentConfigAsQuote(siteAddressInput)
                        showAddressDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = UnipointNavy)
                ) {
                    Text("Salva", color = Color.White)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showAddressDialog = false }) {
                    Text("Annulla")
                }
            }
        )
    }
}

@Composable
private fun AccessoryToggle(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Slate900)
            Text(text = subtitle, fontSize = 11.sp, color = Slate600)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = UnipointAmber,
                uncheckedThumbColor = Slate300,
                uncheckedTrackColor = Slate100
            )
        )
    }
}

@Composable
private fun MaterialRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 12.sp, color = Slate700)
        Text(text = value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Slate900)
    }
}

// Custom Live Blueprint Canvas Drawing Function
private fun DrawScope.drawScaffoldingBlueprint(config: ScaffoldConfig) {
    val canvasW = size.width
    val canvasH = size.height

    // Grid blueprint background lines
    val gridSpacing = 24.dp.toPx()
    var x = 0f
    while (x < canvasW) {
        drawLine(
            color = Color.White.copy(alpha = 0.05f),
            start = Offset(x, 0f),
            end = Offset(x, canvasH),
            strokeWidth = 1f
        )
        x += gridSpacing
    }
    var y = 0f
    while (y < canvasH) {
        drawLine(
            color = Color.White.copy(alpha = 0.05f),
            start = Offset(0f, y),
            end = Offset(canvasW, y),
            strokeWidth = 1f
        )
        y += gridSpacing
    }

    // Ground line
    val groundY = canvasH - 30.dp.toPx()
    drawLine(
        color = Color(0xFF64748B),
        start = Offset(10.dp.toPx(), groundY),
        end = Offset(canvasW - 10.dp.toPx(), groundY),
        strokeWidth = 3.dp.toPx(),
        cap = StrokeCap.Round
    )

    // Compute dimensions based on bays and levels
    val bays = config.bayCount.coerceIn(2, 10)
    val levels = config.deckLevels.coerceIn(1, 8)

    val scaffoldPaddingH = 36.dp.toPx()
    val availableW = canvasW - scaffoldPaddingH * 2
    val bayW = availableW / bays

    val scaffoldTopY = 36.dp.toPx()
    val scaffoldHeight = groundY - scaffoldTopY
    val levelH = scaffoldHeight / levels

    val is3D = config.viewPerspective == "Assonometria 3D"
    val isSection = config.viewPerspective == "Sezione"
    val isoOffset = if (is3D) Offset(14.dp.toPx(), -10.dp.toPx()) else Offset.Zero

    // If netting is enabled, draw translucent protective netting over the facade
    if (config.includeNetting && !isSection) {
        val netRect = Size(availableW + isoOffset.x, scaffoldHeight - isoOffset.y)
        drawRect(
            color = Color(0xFF10B981).copy(alpha = 0.18f),
            topLeft = Offset(scaffoldPaddingH, scaffoldTopY + isoOffset.y),
            size = netRect
        )
    }

    val steelBlue = Color(0xFF38BDF8)
    val safetyAmber = Color(0xFFF59E0B)
    val steelDeck = Color(0xFFE2E8F0)

    if (isSection) {
        // Draw Side Cross-section view
        val secX = canvasW / 2
        val secW = 60.dp.toPx()

        for (l in 0..levels) {
            val currY = groundY - l * levelH
            // Deck
            drawLine(
                color = steelDeck,
                start = Offset(secX - secW / 2, currY),
                end = Offset(secX + secW / 2, currY),
                strokeWidth = 4.dp.toPx()
            )
            // Parapet
            if (l < levels) {
                drawLine(
                    color = safetyAmber,
                    start = Offset(secX + secW / 2, currY - levelH * 0.45f),
                    end = Offset(secX + secW / 2, currY),
                    strokeWidth = 2.dp.toPx()
                )
            }
        }
        // Vertical uprights
        drawLine(
            color = steelBlue,
            start = Offset(secX - secW / 2, groundY),
            end = Offset(secX - secW / 2, groundY - levels * levelH),
            strokeWidth = 3.5.dp.toPx()
        )
        drawLine(
            color = steelBlue,
            start = Offset(secX + secW / 2, groundY),
            end = Offset(secX + secW / 2, groundY - levels * levelH),
            strokeWidth = 3.5.dp.toPx()
        )
        // Diagonals in section
        for (l in 0 until levels) {
            val y1 = groundY - l * levelH
            val y2 = y1 - levelH
            drawLine(
                color = Color.White.copy(alpha = 0.5f),
                start = Offset(secX - secW / 2, y1),
                end = Offset(secX + secW / 2, y2),
                strokeWidth = 1.5.dp.toPx()
            )
        }
        return
    }

    // Draw Front / 3D Scaffolding
    // 1. Back 3D Frame (if is3D)
    if (is3D) {
        for (b in 0..bays) {
            val colX = scaffoldPaddingH + b * bayW + isoOffset.x
            drawLine(
                color = steelBlue.copy(alpha = 0.45f),
                start = Offset(colX, groundY + isoOffset.y),
                end = Offset(colX, groundY - levels * levelH + isoOffset.y),
                strokeWidth = 2.dp.toPx()
            )
        }
        for (l in 0..levels) {
            val currY = groundY - l * levelH + isoOffset.y
            drawLine(
                color = steelDeck.copy(alpha = 0.4f),
                start = Offset(scaffoldPaddingH + isoOffset.x, currY),
                end = Offset(scaffoldPaddingH + bays * bayW + isoOffset.x, currY),
                strokeWidth = 2.dp.toPx()
            )
        }
        // Connect front and back levels
        for (b in 0..bays) {
            for (l in 0..levels) {
                val frontPt = Offset(scaffoldPaddingH + b * bayW, groundY - l * levelH)
                val backPt = frontPt + isoOffset
                drawLine(
                    color = steelBlue.copy(alpha = 0.35f),
                    start = frontPt,
                    end = backPt,
                    strokeWidth = 1.5.dp.toPx()
                )
            }
        }
    }

    // 2. Front Frame Uprights
    for (b in 0..bays) {
        val colX = scaffoldPaddingH + b * bayW
        // Base plate (piastra di base)
        drawRect(
            color = safetyAmber,
            topLeft = Offset(colX - 5.dp.toPx(), groundY - 3.dp.toPx()),
            size = Size(10.dp.toPx(), 4.dp.toPx())
        )
        // Vertical steel post
        drawLine(
            color = steelBlue,
            start = Offset(colX, groundY),
            end = Offset(colX, groundY - levels * levelH - 6.dp.toPx()),
            strokeWidth = 3.dp.toPx(),
            cap = StrokeCap.Round
        )
    }

    // 3. Horizontal Walkway Decks & Safety Rails
    for (l in 0..levels) {
        val currY = groundY - l * levelH
        // Metallic steel deck walkway
        drawLine(
            color = steelDeck,
            start = Offset(scaffoldPaddingH, currY),
            end = Offset(scaffoldPaddingH + bays * bayW, currY),
            strokeWidth = 3.5.dp.toPx()
        )

        // Safety Toe-board (fermapiede giallo da 15cm)
        if (config.includeToeBoards && l > 0) {
            drawRect(
                color = safetyAmber,
                topLeft = Offset(scaffoldPaddingH, currY - 5.dp.toPx()),
                size = Size(bays * bayW, 4.dp.toPx())
            )
            // Mid safety rail
            drawLine(
                color = safetyAmber.copy(alpha = 0.8f),
                start = Offset(scaffoldPaddingH, currY - levelH * 0.45f),
                end = Offset(scaffoldPaddingH + bays * bayW, currY - levelH * 0.45f),
                strokeWidth = 1.5.dp.toPx()
            )
        }
    }

    // 4. Diagonal Cross-braces (Croci di Sant'Andrea)
    for (b in 0 until bays) {
        if (b % 2 == 0) {
            for (l in 0 until levels) {
                val x1 = scaffoldPaddingH + b * bayW
                val x2 = x1 + bayW
                val y1 = groundY - l * levelH
                val y2 = y1 - levelH

                drawLine(
                    color = Color.White.copy(alpha = 0.55f),
                    start = Offset(x1, y1),
                    end = Offset(x2, y2),
                    strokeWidth = 1.5.dp.toPx()
                )
                drawLine(
                    color = Color.White.copy(alpha = 0.55f),
                    start = Offset(x2, y1),
                    end = Offset(x1, y2),
                    strokeWidth = 1.5.dp.toPx()
                )
            }
        }
    }

    // 5. Ladder Hatch (botola di risalita) in first bay
    if (config.includeLadderHatch) {
        val ladderX = scaffoldPaddingH + bayW * 0.5f
        for (l in 0 until levels) {
            val yBot = groundY - l * levelH
            val yTop = yBot - levelH
            // Draw ladder rungs
            for (rung in 1..4) {
                val ry = yBot - rung * (levelH / 5f)
                drawLine(
                    color = Color.White.copy(alpha = 0.7f),
                    start = Offset(ladderX - 7.dp.toPx(), ry),
                    end = Offset(ladderX + 7.dp.toPx(), ry),
                    strokeWidth = 1.5.dp.toPx()
                )
            }
        }
    }

    // 6. Hoisting crane / jib (montacarichi a bandiera)
    if (config.includeHoist) {
        val hoistX = scaffoldPaddingH + bays * bayW
        val hoistY = groundY - levels * levelH
        // Jib boom
        drawLine(
            color = safetyAmber,
            start = Offset(hoistX, hoistY),
            end = Offset(hoistX + 22.dp.toPx(), hoistY - 10.dp.toPx()),
            strokeWidth = 3.5.dp.toPx()
        )
        // Hoisting cable
        drawLine(
            color = Color.White.copy(alpha = 0.8f),
            start = Offset(hoistX + 22.dp.toPx(), hoistY - 10.dp.toPx()),
            end = Offset(hoistX + 22.dp.toPx(), hoistY + levelH * 0.8f),
            strokeWidth = 1.5.dp.toPx()
        )
        // Hook / basket
        drawCircle(
            color = safetyAmber,
            radius = 3.dp.toPx(),
            center = Offset(hoistX + 22.dp.toPx(), hoistY + levelH * 0.8f)
        )
    }
}
