package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.data.model.BookingEntity
import com.example.data.model.ScaffoldQuoteEntity
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
fun BookingsScreen(
    viewModel: UnipointViewModel,
    prefilledItemTitle: String? = null,
    prefilledRate: Double? = null
) {
    val bookings by viewModel.bookings.collectAsState()
    val savedQuotes by viewModel.savedQuotes.collectAsState()
    val successMsg by viewModel.bookingSuccessMessage.collectAsState()

    var selectedTabIndex by remember {
        mutableIntStateOf(if (prefilledItemTitle != null) 1 else 0)
    }

    // Form inputs
    var itemTitleInput by remember { mutableStateOf(prefilledItemTitle ?: "Ponteggio a Telai Prefabbricati (Zincato)") }
    var startDateInput by remember { mutableStateOf("18/09/2026") }
    var endDateInput by remember { mutableStateOf("18/10/2026") }
    var durationDaysInput by remember { mutableStateOf("30") }
    var serviceTypeInput by remember { mutableStateOf("Noleggio + Consegna & Montaggio PIMUS") }
    var siteAddressInput by remember { mutableStateOf("") }
    var clientNameInput by remember { mutableStateOf("") }
    var phoneInput by remember { mutableStateOf("") }
    var paymentMethodInput by remember { mutableStateOf("Bonifico Bancario Istantaneo") }
    val dailyRate = prefilledRate ?: 22.0

    val tabs = listOf("Prenotazioni Attive", "Nuovo Ordine", "Preventivi 3D")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Slate100)
            .testTag("screen_bookings")
    ) {
        // Tabs
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.White,
            contentColor = UnipointNavy,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = UnipointAmber,
                    height = 3.dp
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 13.sp,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                )
            }
        }

        // Success Confirmation Message Banner
        AnimatedVisibility(visible = successMsg != null) {
            Surface(
                color = Color(0xFFECFDF5),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = UnipointGreen)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = successMsg ?: "",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF065F46)
                        )
                    }
                    IconButton(
                        onClick = { viewModel.clearBookingSuccess() },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Text("✕", color = Color(0xFF065F46), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        when (selectedTabIndex) {
            0 -> {
                // Active Bookings Tab
                if (bookings.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.ReceiptLong,
                                contentDescription = null,
                                tint = Slate600,
                                modifier = Modifier.size(54.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Nessuna prenotazione attiva",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate800
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Crea un nuovo ordine o richiedi un noleggio per visualizzare qui il contratto e la documentazione.",
                                fontSize = 12.sp,
                                color = Slate600,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { selectedTabIndex = 1 },
                                colors = ButtonDefaults.buttonColors(containerColor = UnipointAmber, contentColor = Color.Black),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Crea Nuovo Ordine", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        item {
                            Text(
                                text = "${bookings.size} NOLEGGI IN CORSO",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate600
                            )
                        }

                        items(bookings, key = { it.id }) { booking ->
                            BookingCard(
                                booking = booking,
                                onDelete = { viewModel.deleteBooking(booking.id) }
                            )
                        }
                    }
                }
            }

            1 -> {
                // New Booking Form Wizard
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "DATI DEL NOLEGGIO",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate600
                                )
                                Spacer(modifier = Modifier.height(10.dp))

                                OutlinedTextField(
                                    value = itemTitleInput,
                                    onValueChange = { itemTitleInput = it },
                                    label = { Text("Attrezzatura o tipologia ponteggio") },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    OutlinedTextField(
                                        value = startDateInput,
                                        onValueChange = { startDateInput = it },
                                        label = { Text("Data Inizio") },
                                        modifier = Modifier.weight(1f)
                                    )
                                    OutlinedTextField(
                                        value = endDateInput,
                                        onValueChange = { endDateInput = it },
                                        label = { Text("Data Fine") },
                                        modifier = Modifier.weight(1f)
                                    )
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                OutlinedTextField(
                                    value = durationDaysInput,
                                    onValueChange = { durationDaysInput = it },
                                    label = { Text("Durata stimata (giorni)") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }

                    // Service Level
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "LIVELLO DI SERVIZIO & MONTAGGIO",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate600
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                val services = listOf(
                                    "Noleggio + Consegna & Montaggio PIMUS",
                                    "Solo noleggio a freddo (Ritiro in sede)",
                                    "Noleggio con Operatore PLE Unipoint"
                                )

                                services.forEach { s ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { serviceTypeInput = s }
                                            .padding(vertical = 4.dp)
                                    ) {
                                        RadioButton(
                                            selected = serviceTypeInput == s,
                                            onClick = { serviceTypeInput = s },
                                            colors = RadioButtonDefaults.colors(selectedColor = UnipointNavy)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(text = s, fontSize = 13.sp, color = Slate800)
                                    }
                                }
                            }
                        }
                    }

                    // Site Address & Customer
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "INTESTAZIONE & LUOGO CANTIERE",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate600
                                )
                                Spacer(modifier = Modifier.height(10.dp))

                                OutlinedTextField(
                                    value = clientNameInput,
                                    onValueChange = { clientNameInput = it },
                                    label = { Text("Nome referente o Impresa") },
                                    placeholder = { Text("es. Impresa Edile Rossi SRL") },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                OutlinedTextField(
                                    value = phoneInput,
                                    onValueChange = { phoneInput = it },
                                    label = { Text("Recapito Telefonico") },
                                    placeholder = { Text("es. 345 1183711") },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                OutlinedTextField(
                                    value = siteAddressInput,
                                    onValueChange = { siteAddressInput = it },
                                    label = { Text("Indirizzo Cantiere / Consegna") },
                                    placeholder = { Text("es. Via Roma 45, Milano") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }

                    // Payment Method
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "METODO DI PAGAMENTO PREFERITO",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate600
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                val paymentMethods = listOf(
                                    "Bonifico Bancario Istantaneo",
                                    "Carta di Credito / Bancomat",
                                    "POS all'arrivo in Cantiere"
                                )

                                paymentMethods.forEach { method ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { paymentMethodInput = method }
                                            .padding(vertical = 4.dp)
                                    ) {
                                        RadioButton(
                                            selected = paymentMethodInput == method,
                                            onClick = { paymentMethodInput = method },
                                            colors = RadioButtonDefaults.colors(selectedColor = UnipointNavy)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(text = method, fontSize = 13.sp, color = Slate800)
                                    }
                                }
                            }
                        }
                    }

                    // Submit Button
                    item {
                        val duration = durationDaysInput.toIntOrNull() ?: 30
                        val montaggioExtra = if (serviceTypeInput.contains("Montaggio")) 450.0 else 0.0
                        val totalEstimate = (dailyRate * duration) + montaggioExtra

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = UnipointNavy),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(text = "TOTALE STIMATO CONTRATTO", fontSize = 10.sp, color = UnipointAmber)
                                        Text(
                                            text = "€ ${totalEstimate.toInt()},00",
                                            fontSize = 22.sp,
                                            fontWeight = FontWeight.Black,
                                            color = Color.White
                                        )
                                    }
                                    Text(
                                        text = "$duration giorni inclusi",
                                        fontSize = 11.sp,
                                        color = Color.White.copy(alpha = 0.8f)
                                    )
                                }
                            }

                            Button(
                                onClick = {
                                    viewModel.createBooking(
                                        itemTitle = itemTitleInput,
                                        category = "Noleggio Cantiere",
                                        startDate = startDateInput,
                                        endDate = endDateInput,
                                        durationDays = duration,
                                        serviceType = serviceTypeInput,
                                        siteAddress = siteAddressInput,
                                        contactName = clientNameInput,
                                        contactPhone = phoneInput,
                                        dailyRate = dailyRate,
                                        paymentMethod = paymentMethodInput
                                    )
                                    selectedTabIndex = 0
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = UnipointAmber,
                                    contentColor = Color.Black
                                ),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .testTag("btn_confirm_booking")
                            ) {
                                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Conferma Prenotazione & Blocca Mezzi", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            2 -> {
                // Saved 3D Quotes
                if (savedQuotes.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Nessun preventivo salvato dal configuratore 3D",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Slate600
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(savedQuotes, key = { it.id }) { quote ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = quote.title,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = UnipointNavy
                                        )
                                        IconButton(onClick = { viewModel.deleteQuote(quote.id) }) {
                                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Elimina", tint = Color(0xFFDC2626))
                                        }
                                    }

                                    Text(
                                        text = "Cantiere: ${quote.siteAddress}",
                                        fontSize = 12.sp,
                                        color = Slate700
                                    )
                                    Text(
                                        text = "${quote.bayCount} campate • ${quote.deckLevels} piani • Canone: € ${quote.estimatedMonthlyCost.toInt()}/mese",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = UnipointAmber
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BookingCard(
    booking: BookingEntity,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("booking_card_${booking.bookingCode}"),
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
                    color = UnipointNavy,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = booking.bookingCode,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Surface(
                    color = Color(0xFFECFDF5),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = booking.status,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF065F46),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = booking.itemTitle,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                color = Slate900
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = null, tint = Slate600, modifier = Modifier.size(15.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${booking.startDate} ➔ ${booking.endDate} (${booking.durationDays} giorni)",
                    fontSize = 12.sp,
                    color = Slate700
                )
            }

            Spacer(modifier = Modifier.height(3.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = Slate600, modifier = Modifier.size(15.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = booking.siteAddress,
                    fontSize = 12.sp,
                    color = Slate700
                )
            }

            Spacer(modifier = Modifier.height(3.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Security, contentDescription = null, tint = UnipointGreen, modifier = Modifier.size(15.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = booking.serviceType,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF047857)
                )
            }

            HorizontalDivider(color = Slate200, modifier = Modifier.padding(vertical = 10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Pagamento: ${booking.paymentMethod}",
                        fontSize = 11.sp,
                        color = Slate600
                    )
                    Text(
                        text = "€ ${booking.totalAmount.toInt()},00",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = UnipointNavy
                    )
                }

                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Cancella",
                        tint = Slate600
                    )
                }
            }
        }
    }
}
