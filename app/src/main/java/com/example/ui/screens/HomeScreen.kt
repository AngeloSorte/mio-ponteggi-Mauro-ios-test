package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Roofing
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.AppScreen
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.UnipointAmber
import com.example.ui.theme.UnipointAmberLight
import com.example.ui.theme.UnipointGreen
import com.example.ui.theme.UnipointNavy
import com.example.ui.theme.UnipointNavyDark
import com.example.ui.theme.UnipointSky

@Composable
fun HomeScreen(
    onNavigate: (AppScreen) -> Unit,
    onCallPhone: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Slate100)
            .testTag("screen_home"),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        // Hero Card Section
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_scaffolding_hero),
                    contentDescription = "Ponteggi Unipoint in cantiere",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // High-fidelity dark gradient overlay for crystal clear contrast
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.35f),
                                    UnipointNavyDark.copy(alpha = 0.88f)
                                )
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Surface(
                        color = UnipointAmber,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "SPECIALISTI NOLEGGIO & COPERTURE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.Black,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "UNIPOINT PONTEGGI",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        letterSpacing = 0.5.sp
                    )

                    Text(
                        text = "Sicurezza, Qualità e Garanzia nel tempo",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = UnipointAmberLight
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Button(
                            onClick = { onNavigate(AppScreen.CUSTOMIZER) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = UnipointAmber,
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("hero_configure_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.ViewInAr,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Configura 3D", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }

                        Button(
                            onClick = { onNavigate(AppScreen.CATALOG) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White.copy(alpha = 0.2f),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("hero_catalog_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Build,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Catalogo", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
            }
        }

        // Stats Banner
        item {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(UnipointNavy)
                    .padding(vertical = 12.dp, horizontal = 8.dp)
            ) {
                val isCompact = maxWidth < 380.dp
                val valueSize = if (isCompact) 14.sp else 16.sp
                val labelSize = if (isCompact) 9.sp else 10.5.sp

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatItem(
                        value = "35+",
                        label = "Anni Esperienza",
                        valueSize = valueSize,
                        labelSize = labelSize,
                        modifier = Modifier.weight(1f)
                    )
                    StatItem(
                        value = "100%",
                        label = "P.I.M.U.S. Conforme",
                        valueSize = valueSize,
                        labelSize = labelSize,
                        modifier = Modifier.weight(1f)
                    )
                    StatItem(
                        value = "48h",
                        label = "Pronto Intervento",
                        valueSize = valueSize,
                        labelSize = labelSize,
                        modifier = Modifier.weight(1f)
                    )
                    StatItem(
                        value = "Propri",
                        label = "Mezzi Sollevamento",
                        valueSize = valueSize,
                        labelSize = labelSize,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Primary Feature Shortcuts Grid
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "SERVIZI PRINCIPALI",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate600,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionCard(
                        title = "Configuratore 3D",
                        subtitle = "Disegna e calcola ponteggio su misura",
                        icon = Icons.Default.ViewInAr,
                        iconTint = UnipointNavy,
                        badge = "Interattivo",
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(AppScreen.CUSTOMIZER) }
                    )

                    QuickActionCard(
                        title = "Catalogo Noleggio",
                        subtitle = "Telai, multidirezionali, piattaforme",
                        icon = Icons.Default.Build,
                        iconTint = UnipointAmber,
                        badge = "Disponibile",
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(AppScreen.CATALOG) }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionCard(
                        title = "Danni Grandine",
                        subtitle = "Ripristino tetto e messa in sicurezza",
                        icon = Icons.Default.Roofing,
                        iconTint = Color(0xFFDC2626),
                        badge = "Urgente",
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(AppScreen.ROOFING) }
                    )

                    QuickActionCard(
                        title = "Perizie con Drone",
                        subtitle = "Rilievi peritali per assicurazioni",
                        icon = Icons.Default.Navigation,
                        iconTint = UnipointSky,
                        badge = "Certificato",
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(AppScreen.DRONE) }
                    )
                }
            }
        }

        // Hail Damage Emergency Callout Card (Inspired by the prominent flyer section)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .testTag("hail_emergency_card"),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            color = Color(0xFFFEE2E2),
                            shape = CircleShape,
                            modifier = Modifier.size(42.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = null,
                                    tint = Color(0xFFDC2626)
                                )
                            }
                        }

                        Column {
                            Text(
                                text = "DANNI DA GRANDINE O TEMPESTA?",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF991B1B)
                            )
                            Text(
                                text = "Ripristino tempestivo e messa in sicurezza della copertura",
                                fontSize = 12.sp,
                                color = Slate700
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Valutazione e intervento rapido per proteggere la tua casa e il tuo investimento. Installazione immediata di reti antigrandine, teli impermeabili e perizia con drone per la tua compagnia assicurativa.",
                        fontSize = 13.sp,
                        color = Slate700,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { onNavigate(AppScreen.ROOFING) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = UnipointNavy,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Richiedi Sopralluogo", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = onCallPhone,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = UnipointNavy
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Chiama Ora", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = UnipointNavy)
                        }
                    }
                }
            }
        }

        // Specialization Highlights from Flyer
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "I NOSTRI PUNTI DI FORZA",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate600,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(12.dp))

                PillarItem(
                    icon = Icons.Default.Construction,
                    title = "Noleggio Ponteggi Completo",
                    description = "Telai prefabbricati, multidirezionale e tubo-giunto con certificazione ministeriale e piano PIMUS incluso.",
                    accentColor = UnipointNavy
                )

                Spacer(modifier = Modifier.height(10.dp))

                PillarItem(
                    icon = Icons.Default.Speed,
                    title = "Mezzi di Sollevamento Propri",
                    description = "Piattaforme aeree e sollevatori telescopici di proprietà Unipoint. Nessun costo di subappalto, massima celerità.",
                    accentColor = UnipointAmber
                )

                Spacer(modifier = Modifier.height(10.dp))

                PillarItem(
                    icon = Icons.Default.CameraAlt,
                    title = "Perizie con Drone ad Alta Definizione",
                    description = "Foto e video dettagliati 4K su tetti inaccessibili. Fascicolo peritale timbrato per liquidazione assicurativa.",
                    accentColor = UnipointSky
                )

                Spacer(modifier = Modifier.height(10.dp))

                PillarItem(
                    icon = Icons.Default.VerifiedUser,
                    title = "Massima Sicurezza & Garanzia",
                    description = "Rete antigrandine, isolamento termico, linea vita certificata e maestranze formate costantemente.",
                    accentColor = UnipointGreen
                )
            }
        }

        // Contact Geom. Fiorenza Mauro Box
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .testTag("geom_contact_card"),
                colors = CardDefaults.cardColors(containerColor = UnipointNavy),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "CONTATTO DIRETTO",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = UnipointAmber
                        )
                        Text(
                            text = "Geom. Fiorenza Mauro",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Cell: 345/1183711 • Assistenza h24 Cantieri",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }

                    Button(
                        onClick = onCallPhone,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = UnipointAmber,
                            contentColor = Color.Black
                        ),
                        shape = CircleShape,
                        modifier = Modifier.size(46.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = "Chiama Geometra",
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StatItem(
    value: String,
    label: String,
    valueSize: TextUnit = 16.sp,
    labelSize: TextUnit = 10.sp,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = valueSize,
            fontWeight = FontWeight.Black,
            color = UnipointAmber,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
        Text(
            text = label,
            fontSize = labelSize,
            fontWeight = FontWeight.Medium,
            color = Color.White.copy(alpha = 0.85f),
            textAlign = TextAlign.Center,
            lineHeight = 12.sp,
            maxLines = 2
        )
    }
}

@Composable
private fun QuickActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconTint: Color,
    badge: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable { onClick() }
            .testTag("action_card_${title.lowercase().replace(" ", "_")}"),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(1.5.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = iconTint.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = iconTint,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Surface(
                    color = Slate100,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = badge,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate700,
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Slate900
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = Slate600,
                lineHeight = 15.sp
            )
        }
    }
}

@Composable
private fun PillarItem(
    icon: ImageVector,
    title: String,
    description: String,
    accentColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Surface(
                color = accentColor.copy(alpha = 0.12f),
                shape = CircleShape,
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate900
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = Slate700,
                    lineHeight = 16.sp
                )
            }
        }
    }
}
