package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Roofing
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDamage
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppScreen
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
import com.example.ui.theme.UnipointSky

@Composable
fun RoofingScreen(
    viewModel: UnipointViewModel,
    onNavigate: (AppScreen) -> Unit,
    onCallPhone: () -> Unit
) {
    var clientName by remember { mutableStateOf("") }
    var clientPhone by remember { mutableStateOf("") }
    var siteAddress by remember { mutableStateOf("") }
    var selectedDamageType by remember { mutableStateOf("Tegole / Coppi rotti da grandine") }
    var selectedUrgency by remember { mutableStateOf("Emergenza Infiltrazione (24h)") }
    var requestSent by remember { mutableStateOf(false) }

    val damageTypes = listOf(
        "Tegole / Coppi rotti da grandine",
        "Infiltrazione d'acqua nel sottotetto",
        "Rifacimento completo copertura",
        "Posa linea vita e rete antigrandine"
    )

    val urgencies = listOf(
        "Emergenza Infiltrazione (24h)",
        "Urgente per Perizia Assicurativa (48h)",
        "Preventivo Programmato"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Slate100)
            .testTag("screen_roofing"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Header Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = UnipointNavyDark),
                elevation = CardDefaults.cardElevation(3.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Surface(
                            color = Color(0xFFDC2626),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "PRONTO INTERVENTO EMERGENZE",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "DANNI DA GRANDINE?",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            letterSpacing = 0.5.sp
                        )

                        Text(
                            text = "Ripristino tempestivo e messa in sicurezza della copertura",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = UnipointAmber
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Valutazione immediata, stesura teli impermeabili provvisori, noleggio ponteggio d'urgenza e perizia tecnica fotografica con drone per il rimborso assicurativo.",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.85f),
                            lineHeight = 17.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = onCallPhone,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = UnipointAmber,
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("roofing_call_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Chiama Geom. Fiorenza: 345/1183711", fontWeight = FontWeight.Bold, fontSize = 12.sp, maxLines = 1)
                        }
                    }
                }
            }
        }

        // Service Pillars from Flyer
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "I NOSTRI SERVIZI COPERTURE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate600
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    RoofServiceRow(
                        icon = Icons.Default.Roofing,
                        title = "Rifacimento Tetti Civili e Industriali",
                        description = "Smaltimento vecchie coperture, isolamento in fibra minerale, posa tegole portoghesi o marsigliesi, coppi antichizzati e lamiere coibentate."
                    )

                    HorizontalDivider(color = Slate100, modifier = Modifier.padding(vertical = 10.dp))

                    RoofServiceRow(
                        icon = Icons.Default.Shield,
                        title = "Rete Antigrandine & Isolamento Termico",
                        description = "Sistemi barriera certificati contro eventi estremi. Massimo risparmio energetico e protezione ermetica."
                    )

                    HorizontalDivider(color = Slate100, modifier = Modifier.padding(vertical = 10.dp))

                    RoofServiceRow(
                        icon = Icons.Default.Navigation,
                        title = "Perizie con Drone per Assicurazioni",
                        description = "Rilievi aerei millimetrici con foto 4K geolocalizzate e relazione asseverata per accelerare i rimborsi del sinistro.",
                        onAction = { onNavigate(AppScreen.DRONE) }
                    )

                    HorizontalDivider(color = Slate100, modifier = Modifier.padding(vertical = 10.dp))

                    RoofServiceRow(
                        icon = Icons.Default.Security,
                        title = "Linee Vita & Massima Sicurezza",
                        description = "Ancoraggi permanenti e temporanei certificati UNI EN 795 per la sicurezza delle manutenzioni future."
                    )
                }
            }
        }

        // Interactive Inspection Request Form
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(14.dp),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "RICHIEDI SOPRALLUOGO O MESSA IN SICUREZZA",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = UnipointNavy
                    )
                    Text(
                        text = "Un tecnico Unipoint effettuerà il controllo in loco o con drone entro poche ore.",
                        fontSize = 11.sp,
                        color = Slate600
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Tipologia di problema:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate700
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    damageTypes.forEach { type ->
                        val isSelected = selectedDamageType == type
                        Surface(
                            color = if (isSelected) UnipointNavy.copy(alpha = 0.08f) else Slate100,
                            shape = RoundedCornerShape(8.dp),
                            border = if (isSelected) androidx.compose.foundation.BorderStroke(1.5.dp, UnipointNavy) else null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                androidx.compose.material3.RadioButton(
                                    selected = isSelected,
                                    onClick = { selectedDamageType = type },
                                    colors = androidx.compose.material3.RadioButtonDefaults.colors(selectedColor = UnipointNavy)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = type,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) UnipointNavy else Slate800
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Priorità intervento:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate700
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        urgencies.forEach { urg ->
                            val isSel = selectedUrgency == urg
                            FilterChip(
                                selected = isSel,
                                onClick = { selectedUrgency = urg },
                                label = {
                                    Text(
                                        text = if (urg.contains("24h")) "24h Emergenza" else if (urg.contains("48h")) "48h Perizia" else "Ordinario",
                                        fontSize = 10.sp,
                                        fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = if (urg.contains("24h")) Color(0xFFDC2626) else UnipointNavy,
                                    selectedLabelColor = Color.White
                                ),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = siteAddress,
                        onValueChange = { siteAddress = it },
                        label = { Text("Indirizzo Tetto / Immobile") },
                        placeholder = { Text("es. Via Garibaldi 12, Monza") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = clientName,
                            onValueChange = { clientName = it },
                            label = { Text("Nome / Impresa") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = clientPhone,
                            onValueChange = { clientPhone = it },
                            label = { Text("Telefono") },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    AnimatedVisibility(visible = requestSent) {
                        Surface(
                            color = Color(0xFFECFDF5),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = UnipointGreen)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Richiesta ricevuta! Il Geom. Mauro Fiorenza ti richiamerà entro 15 minuti per coordinare il sopralluogo.",
                                    fontSize = 12.sp,
                                    color = Color(0xFF065F46),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    Button(
                        onClick = {
                            viewModel.sendUserMessage(
                                "RICHIESTA SOPRALLUOGO TETTO: $selectedDamageType a $siteAddress. Contatto: $clientName ($clientPhone) - Priorità: $selectedUrgency"
                            )
                            requestSent = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = UnipointNavy,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("submit_roof_request")
                    ) {
                        Icon(imageVector = Icons.Default.ElectricBolt, contentDescription = null, tint = UnipointAmber)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Invia Richiesta Intervento Immediato", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun RoofServiceRow(
    icon: ImageVector,
    title: String,
    description: String,
    onAction: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Surface(
            color = UnipointNavy.copy(alpha = 0.08f),
            shape = CircleShape,
            modifier = Modifier.size(36.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = UnipointNavy,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Slate900
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = description,
                fontSize = 11.sp,
                color = Slate700,
                lineHeight = 15.sp
            )

            if (onAction != null) {
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedButton(
                    onClick = onAction,
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    modifier = Modifier.height(30.dp)
                ) {
                    Text("Dettagli perizia drone ➔", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = UnipointNavy)
                }
            }
        }
    }
}
