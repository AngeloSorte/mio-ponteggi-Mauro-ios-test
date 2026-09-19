package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.UnipointViewModel
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate900
import com.example.ui.theme.UnipointAmber
import com.example.ui.theme.UnipointGreen
import com.example.ui.theme.UnipointNavy
import com.example.ui.theme.UnipointNavyDark

@Composable
fun DroneScreen(
    viewModel: UnipointViewModel,
    onCallPhone: () -> Unit
) {
    var requesterName by remember { mutableStateOf("") }
    var requesterPhone by remember { mutableStateOf("") }
    var insuranceCompany by remember { mutableStateOf("") }
    var claimNumber by remember { mutableStateOf("") }
    var siteLocation by remember { mutableStateOf("") }
    var bookedSuccess by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Slate100)
            .testTag("screen_drone"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Card with Drone Image
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = UnipointNavyDark),
                elevation = CardDefaults.cardElevation(3.dp)
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(170.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_drone_roof),
                            contentDescription = "Rilievo drone su copertura danneggiata",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Surface(
                            color = UnipointAmber,
                            shape = RoundedCornerShape(topStart = 0.dp, bottomEnd = 12.dp),
                            modifier = Modifier.align(Alignment.TopStart)
                        ) {
                            Text(
                                text = "PILOTA CERTIFICATO ENAC",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.Black,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "PERIZIE CON DRONE PER ASSICURAZIONI",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            lineHeight = 22.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Rilievi precisi • Documentazione dettagliata • Risparmio di tempo e costi",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = UnipointAmber
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "In caso di grandine, vento forte o incendio, accedere al tetto senza ponteggio può essere rischioso e rallentare i tempi. Con i nostri droni professionali ispezioniamo ogni millimetro del tetto in 30 minuti, producendo un fascicolo fotografico certificato e asseverato valido per qualsiasi perito assicurativo.",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.85f),
                            lineHeight = 17.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Button(
                            onClick = onCallPhone,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = UnipointAmber,
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("drone_call_button")
                        ) {
                            Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Parla con il Geom. Mauro Fiorenza", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // 4 Value Propositions
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "VANTAGGI DEL RILIEVO AEREO UNIPOINT",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate600
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    DroneAdvantageItem(
                        icon = Icons.Default.CameraAlt,
                        title = "Foto 4K e Zoom Macro",
                        desc = "Identificazione immediata di microfratture su tegole, coppi, lucernari velux e canne fumarie."
                    )

                    HorizontalDivider(color = Slate100, modifier = Modifier.padding(vertical = 10.dp))

                    DroneAdvantageItem(
                        icon = Icons.Default.Description,
                        title = "Fascicolo Peritale Valido al 100%",
                        desc = "Relazione tecnica asseverata da geometra iscritto all'Albo professionale con stima computometrica del danno."
                    )

                    HorizontalDivider(color = Slate100, modifier = Modifier.padding(vertical = 10.dp))

                    DroneAdvantageItem(
                        icon = Icons.Default.Speed,
                        title = "Intervento Rapido entro 24/48 Ore",
                        desc = "Niente attese prolungate: il sopralluogo aereo viene pianificato prontamente per anticipare la pioggia."
                    )

                    HorizontalDivider(color = Slate100, modifier = Modifier.padding(vertical = 10.dp))

                    DroneAdvantageItem(
                        icon = Icons.Default.GpsFixed,
                        title = "Coordinate GPS & Ortofotopiano 3D",
                        desc = "Georeferenziazione satellitare e calcolo esatto delle metrature danneggiate per il rimborso."
                    )
                }
            }
        }

        // Interactive Booking Form
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(14.dp),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "PRENOTA UNA PERIZIA CON DRONE",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = UnipointNavy
                    )
                    Text(
                        text = "Inserisci i dati del sinistro o dell'immobile per ricevere la visita del nostro pilota.",
                        fontSize = 11.sp,
                        color = Slate600
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = siteLocation,
                        onValueChange = { siteLocation = it },
                        label = { Text("Indirizzo dell'immobile da ispezionare") },
                        placeholder = { Text("es. Via Roma 45, Bergamo") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = insuranceCompany,
                            onValueChange = { insuranceCompany = it },
                            label = { Text("Compagnia Assicurativa") },
                            placeholder = { Text("es. UnipolSai, Generali") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = claimNumber,
                            onValueChange = { claimNumber = it },
                            label = { Text("N° Sinistro (opzionale)") },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = requesterName,
                            onValueChange = { requesterName = it },
                            label = { Text("Richiedente / Proprietario") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = requesterPhone,
                            onValueChange = { requesterPhone = it },
                            label = { Text("Telefono") },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    AnimatedVisibility(visible = bookedSuccess) {
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
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = UnipointGreen)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Prenotazione perizia registrata! Il pilota ti contatterà per confermare meteo e orario del sorvolo.",
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
                                "RICHIESTA PERIZIA DRONE: Immobile a $siteLocation. Assicurazione: $insuranceCompany (Sinistro: $claimNumber). Contatto: $requesterName ($requesterPhone)"
                            )
                            bookedSuccess = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = UnipointNavy,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("submit_drone_booking")
                    ) {
                        Icon(Icons.Default.FlightTakeoff, contentDescription = null, tint = UnipointAmber)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Prenota Rilievo Fotografico con Drone", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun DroneAdvantageItem(
    icon: ImageVector,
    title: String,
    desc: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Surface(
            color = UnipointAmber.copy(alpha = 0.15f),
            shape = CircleShape,
            modifier = Modifier.size(36.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFFB45309),
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
                text = desc,
                fontSize = 11.sp,
                color = Slate700,
                lineHeight = 15.sp
            )
        }
    }
}
