package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.model.BookingEntity
import com.example.data.model.ChatMessageEntity
import com.example.data.model.EquipmentCategory
import com.example.data.model.EquipmentItem
import com.example.data.model.ScaffoldQuoteEntity
import com.example.data.repository.UnipointRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.ceil
import kotlin.random.Random

enum class AppScreen(val title: String, val subtitle: String) {
    HOME("Home Panoramica", "Sicurezza, Qualità e Garanzia nel tempo"),
    CATALOG("Noleggio Ponteggi & Mezzi", "Catalogo attrezzature e schede tecniche"),
    CUSTOMIZER("Configuratore Ponteggio 3D", "Personalizzazione architettonica e calcolo PIMUS"),
    BOOKINGS("Prenotazioni & Pagamenti", "Gestione cantieri e riepilogo contratti"),
    ROOFING("Rifacimento Tetti & Grandine", "Pronto intervento coperture e messa in sicurezza"),
    DRONE("Perizie con Drone", "Rilievi aerei certificati per assicurazioni"),
    CHAT("Assistenza Tecnica & Preventivi", "Filo diretto con Geom. Mauro Fiorenza"),
    INFO_LOCATION("Sede & Certificazioni", "Dove trovarci, autorizzazioni e contatti")
}

data class ScaffoldConfig(
    val structureType: String = "Facciata Dritta",
    val widthMeters: Float = 18f,
    val heightMeters: Float = 10f,
    val includeNetting: Boolean = true,
    val includeToeBoards: Boolean = true,
    val includeHoist: Boolean = true,
    val includeLadderHatch: Boolean = true,
    val viewPerspective: String = "Assonometria 3D"
) {
    val bayCount: Int get() = (widthMeters / 2.5f).toInt().coerceAtLeast(2)
    val deckLevels: Int get() = (heightMeters / 2.0f).toInt().coerceAtLeast(1)
    val totalAreaSqMeters: Float get() = widthMeters * heightMeters

    val framesCount: Int get() = (bayCount + 1) * deckLevels * 2
    val decksCount: Int get() = bayCount * deckLevels * 2
    val diagonalsCount: Int get() = (bayCount * deckLevels * 0.5f).toInt().coerceAtLeast(2)
    val anchorsCount: Int get() = (totalAreaSqMeters / 22f).toInt().coerceAtLeast(4)
    val estimatedWeightKg: Int get() = (framesCount * 18.5f + decksCount * 14.2f + anchorsCount * 3.5f).toInt()

    val estimatedMonthlyCost: Double get() {
        val baseAreaRate = totalAreaSqMeters * 6.5
        val netExtra = if (includeNetting) totalAreaSqMeters * 1.5 else 0.0
        val hoistExtra = if (includeHoist) 290.0 else 0.0
        return baseAreaRate + netExtra + hoistExtra
    }
}

class UnipointViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UnipointRepository

    init {
        val db = AppDatabase.getInstance(application)
        repository = UnipointRepository(db)
        viewModelScope.launch {
            repository.seedInitialDataIfNeeded()
        }
    }

    val bookings: StateFlow<List<BookingEntity>> = repository.allBookings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val chatMessages: StateFlow<List<ChatMessageEntity>> = repository.allMessages
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedQuotes: StateFlow<List<ScaffoldQuoteEntity>> = repository.allQuotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val catalogItems: List<EquipmentItem> = repository.catalogItems

    private val _currentScreen = MutableStateFlow(AppScreen.HOME)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val _selectedCategory = MutableStateFlow(EquipmentCategory.ALL)
    val selectedCategory: StateFlow<EquipmentCategory> = _selectedCategory.asStateFlow()

    private val _selectedEquipment = MutableStateFlow<EquipmentItem?>(null)
    val selectedEquipment: StateFlow<EquipmentItem?> = _selectedEquipment.asStateFlow()

    private val _scaffoldConfig = MutableStateFlow(ScaffoldConfig())
    val scaffoldConfig: StateFlow<ScaffoldConfig> = _scaffoldConfig.asStateFlow()

    private val _bookingSuccessMessage = MutableStateFlow<String?>(null)
    val bookingSuccessMessage: StateFlow<String?> = _bookingSuccessMessage.asStateFlow()

    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    fun selectCategory(category: EquipmentCategory) {
        _selectedCategory.value = category
    }

    fun selectEquipment(item: EquipmentItem?) {
        _selectedEquipment.value = item
    }

    fun updateScaffoldConfig(transform: (ScaffoldConfig) -> ScaffoldConfig) {
        _scaffoldConfig.value = transform(_scaffoldConfig.value)
    }

    fun clearBookingSuccess() {
        _bookingSuccessMessage.value = null
    }

    fun createBooking(
        itemTitle: String,
        category: String,
        startDate: String,
        endDate: String,
        durationDays: Int,
        serviceType: String,
        siteAddress: String,
        contactName: String,
        contactPhone: String,
        dailyRate: Double,
        paymentMethod: String
    ) {
        viewModelScope.launch {
            val codeNum = Random.nextInt(1000, 9999)
            val code = "UP-2026-$codeNum"
            val total = (dailyRate * durationDays) + if (serviceType.contains("Montaggio")) 450.0 else 0.0
            val booking = BookingEntity(
                bookingCode = code,
                itemTitle = itemTitle,
                category = category,
                startDate = startDate,
                endDate = endDate,
                durationDays = durationDays,
                serviceType = serviceType,
                siteAddress = siteAddress.ifBlank { "Sede Cantiere Cliente" },
                contactName = contactName.ifBlank { "Cliente Unipoint" },
                contactPhone = contactPhone.ifBlank { "345 1183711" },
                dailyRate = dailyRate,
                totalAmount = total,
                paymentMethod = paymentMethod,
                status = "Confermato"
            )
            repository.insertBooking(booking)
            _bookingSuccessMessage.value = "Prenotazione $code registrata con successo! Il Geom. Mauro Fiorenza ti contatterà a breve."
        }
    }

    fun saveCurrentConfigAsQuote(address: String) {
        viewModelScope.launch {
            val cfg = _scaffoldConfig.value
            val quote = ScaffoldQuoteEntity(
                title = "Configurazione ${cfg.structureType} (${cfg.widthMeters.toInt()}m x ${cfg.heightMeters.toInt()}m)",
                structureType = cfg.structureType,
                widthMeters = cfg.widthMeters,
                heightMeters = cfg.heightMeters,
                deckLevels = cfg.deckLevels,
                bayCount = cfg.bayCount,
                includeNetting = cfg.includeNetting,
                includeToeBoards = cfg.includeToeBoards,
                includeHoist = cfg.includeHoist,
                includeLadderHatch = cfg.includeLadderHatch,
                estimatedMonthlyCost = cfg.estimatedMonthlyCost,
                siteAddress = address.ifBlank { "Richiesta Preventivo Cantiere" }
            )
            repository.insertQuote(quote)
            _bookingSuccessMessage.value = "Preventivo e calcolo materiali salvati con successo!"
        }
    }

    fun deleteBooking(id: Long) {
        viewModelScope.launch {
            repository.deleteBooking(id)
        }
    }

    fun deleteQuote(id: Long) {
        viewModelScope.launch {
            repository.deleteQuote(id)
        }
    }

    fun sendUserMessage(text: String) {
        if (text.isBlank()) return
        viewModelScope.launch {
            repository.sendMessage(
                sender = "Tu",
                text = text,
                isFromUser = true
            )
            // Smart auto-reply after slight delay
            delay(1000)
            val lower = text.lowercase()
            val replyText: String
            val badge: String
            when {
                lower.contains("preventivo") || lower.contains("costo") || lower.contains("prezzo") -> {
                    replyText = "Per calcolare un preventivo dettagliato abbiamo bisogno dell'indirizzo del cantiere e dell'altezza stimata. Puoi anche usare la sezione 'Configuratore 3D' dell'app per calcolare i metri quadri e salvare la stima!"
                    badge = "Ufficio Preventivi"
                }
                lower.contains("grandine") || lower.contains("tetto") || lower.contains("emergenza") || lower.contains("infiltrazion") -> {
                    replyText = "Per danni da grandine eseguiamo subito un sopralluogo con drone per la perizia assicurativa e mettiamo in sicurezza con teli e ponteggi entro 24/48 ore. Se urgente chiamaci direttamente al 345/1183711."
                    badge = "Pronto Intervento"
                }
                lower.contains("drone") || lower.contains("perizi") || lower.contains("assicura") -> {
                    replyText = "I nostri piloti certificati ENAC eseguono rilievi fotometrici ad alta risoluzione del tetto, fornendo un fascicolo fotografico timbrato valido per tutte le compagnie assicurative."
                    badge = "Perizie Drone"
                }
                lower.contains("pimus") || lower.contains("sicurezza") || lower.contains("montaggio") -> {
                    replyText = "Tutti i nostri montaggi includono la stesura del P.I.M.U.S. (Piano di Montaggio, Uso e Smontaggio) a cura del nostro geometra abilitato e calcolo statico ministeriale."
                    badge = "Sicurezza Cantieri"
                }
                else -> {
                    replyText = "Grazie per il messaggio! Abbiamo preso in carico la tua richiesta. Il Geom. Mauro Fiorenza o un tecnico del nostro staff ti risponderà entro 30 minuti."
                    badge = "Geom. Mauro Fiorenza"
                }
            }

            repository.sendMessage(
                sender = "Geom. Mauro Fiorenza",
                text = replyText,
                isFromUser = false,
                badge = badge
            )
        }
    }
}
