package com.example.ui.screens

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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun InfoLocationScreen(
    onCallPhone: () -> Unit,
    onOpenMap: () -> Unit = {}
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Slate100)
            .testTag("screen_info_location"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Corporate Header
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = UnipointNavyDark),
                elevation = CardDefaults.cardElevation(3.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Surface(
                            color = UnipointAmber,
                            shape = CircleShape,
                            modifier = Modifier.size(52.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Business,
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }

                        Column {
                            Text(
                                text = "UNIPOINT PONTEGGI",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "Responsabile Tecnico: Geom. Mauro Fiorenza",
                                fontSize = 12.sp,
                                color = UnipointAmber,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "P.IVA 03891720964 • C.C.I.A.A. Milano Monza Brianza",
                                fontSize = 10.sp,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Da oltre 20 anni siamo il partner di riferimento per imprese edili, restauratori e privati in tutta la Lombardia e Nord Italia. Offriamo noleggio a freddo e a caldo con montaggio P.I.M.U.S. certificato, autogru, cestelli e interventi di pronto soccorso coperture danneggiate da grandine.",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.85f),
                        lineHeight = 17.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = onCallPhone,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = UnipointAmber,
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("info_call_button")
                        ) {
                            Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("345/1183711", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = onOpenMap,
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Map, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Mappa Sede", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Headquarters & Depot Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "DOVE SIAMO & RECAPITI",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate600
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    LocationItem(
                        icon = Icons.Default.LocationOn,
                        title = "Sede Operativa & Logistica Ponteggi",
                        subtitle = "Via Cantiere Brianza 18, Meda (MB) - Lombardia",
                        note = "Ampio parco carico bilici, camion gru e magazzino elementi modulari."
                    )

                    HorizontalDivider(color = Slate100, modifier = Modifier.padding(vertical = 10.dp))

                    LocationItem(
                        icon = Icons.Default.Phone,
                        title = "Recapito Diretto Geometra",
                        subtitle = "+39 345 118 3711 (Geom. Mauro Fiorenza)",
                        note = "Disponibile per sopralluoghi tecnici immediati e computi metrici."
                    )

                    HorizontalDivider(color = Slate100, modifier = Modifier.padding(vertical = 10.dp))

                    LocationItem(
                        icon = Icons.Default.Email,
                        title = "Ufficio Tecnico & Preventivi",
                        subtitle = "info@unipointponteggi.it • preventivi@unipoint.it",
                        note = "Invio disegni CAD, capitolati e richieste perizie in giornata."
                    )

                    HorizontalDivider(color = Slate100, modifier = Modifier.padding(vertical = 10.dp))

                    LocationItem(
                        icon = Icons.Default.AccessTime,
                        title = "Orari & Disponibilità",
                        subtitle = "Lun - Ven: 07:00 - 19:00 | Sab: 07:30 - 13:00",
                        note = "Reperibilità cantieri e pronto intervento grandine h24 / 7 giorni su 7."
                    )
                }
            }
        }

        // Certifications & Safety compliance
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Security, contentDescription = null, tint = UnipointNavy)
                        Text(
                            text = "CONFORMITÀ & CERTIFICAZIONI A NORMA",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = UnipointNavy
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    CertificationRow(
                        title = "D.Lgs 81/2008 & P.I.M.U.S.",
                        desc = "Stesura completa del Piano di Montaggio, Uso e Smontaggio a firma del geometra abilitato."
                    )

                    CertificationRow(
                        title = "UNI EN 12810 - UNI EN 12811",
                        desc = "Attrezzature e ponteggi dotati di autorizzazione ministeriale in corso di validità e libretto d'uso."
                    )

                    CertificationRow(
                        title = "Piloti Droni Abilitati ENAC",
                        desc = "Personale certificato per operazioni aeree critiche in scenari standard urbani per perizie."
                    )

                    CertificationRow(
                        title = "Copertura R.C.T. e R.C.O.",
                        desc = "Polizza assicurativa primaria con massimale fino a € 5.000.000 a totale tutela del committente."
                    )
                }
            }
        }
    }
}

@Composable
private fun LocationItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    note: String
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
                text = subtitle,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = UnipointNavy
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = note,
                fontSize = 11.sp,
                color = Slate700,
                lineHeight = 15.sp
            )
        }
    }
}

@Composable
private fun CertificationRow(
    title: String,
    desc: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Verified,
            contentDescription = null,
            tint = UnipointGreen,
            modifier = Modifier.size(18.dp)
        )
        Column {
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Slate900
            )
            Text(
                text = desc,
                fontSize = 11.sp,
                color = Slate600,
                lineHeight = 15.sp
            )
        }
    }
}
