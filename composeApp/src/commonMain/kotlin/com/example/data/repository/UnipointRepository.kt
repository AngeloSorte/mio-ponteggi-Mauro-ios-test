package com.example.data.repository

import com.example.data.model.BookingEntity
import com.example.data.model.ChatMessageEntity
import com.example.data.model.EquipmentCategory
import com.example.data.model.EquipmentItem
import com.example.data.model.ScaffoldQuoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first

class UnipointRepository {

    private val _bookings = MutableStateFlow<List<BookingEntity>>(emptyList())
    val allBookings: Flow<List<BookingEntity>> = _bookings.asStateFlow()

    private val _messages = MutableStateFlow<List<ChatMessageEntity>>(emptyList())
    val allMessages: Flow<List<ChatMessageEntity>> = _messages.asStateFlow()

    private val _quotes = MutableStateFlow<List<ScaffoldQuoteEntity>>(emptyList())
    val allQuotes: Flow<List<ScaffoldQuoteEntity>> = _quotes.asStateFlow()

    private var nextBookingId = 1L
    private var nextMessageId = 1L
    private var nextQuoteId = 1L

    suspend fun insertBooking(booking: BookingEntity): Long {
        val id = nextBookingId++
        val item = booking.copy(id = id)
        _bookings.value = _bookings.value + item
        return id
    }

    suspend fun deleteBooking(id: Long) {
        _bookings.value = _bookings.value.filterNot { it.id == id }
    }

    suspend fun sendMessage(sender: String, text: String, isFromUser: Boolean, badge: String? = null): Long {
        val id = nextMessageId++
        val message = ChatMessageEntity(
            id = id,
            sender = sender,
            text = text,
            isFromUser = isFromUser,
            timestamp = 1741770000000L,
            badge = badge
        )
        _messages.value = _messages.value + message
        return id
    }

    suspend fun insertQuote(quote: ScaffoldQuoteEntity): Long {
        val id = nextQuoteId++
        val item = quote.copy(id = id)
        _quotes.value = _quotes.value + item
        return id
    }

    suspend fun deleteQuote(id: Long) {
        _quotes.value = _quotes.value.filterNot { it.id == id }
    }

    suspend fun seedInitialDataIfNeeded() {
        val messages = allMessages.first()
        if (messages.isEmpty()) {
            sendMessage(
                sender = "Geom. Mauro Fiorenza",
                text = "Benvenuto in Unipoint! Sono il Geom. Mauro Fiorenza. Siamo a tua disposizione per noleggio ponteggi certificati, interventi su tetti danneggiati da grandine e perizie tecniche con drone.",
                isFromUser = false,
                badge = "Assistenza Diretta"
            )
            sendMessage(
                sender = "Ufficio Tecnico Unipoint",
                text = "Hai bisogno di una stima rapida? Puoi usare la sezione 'Configuratore 3D' o inviarci qui le dimensioni della facciata per redigere subito il P.I.M.U.S.",
                isFromUser = false,
                badge = "P.I.M.U.S. & Sicurezza"
            )
        }

        val bookings = allBookings.first()
        if (bookings.isEmpty()) {
            insertBooking(
                BookingEntity(
                    bookingCode = "UP-2026-9281",
                    itemTitle = "Ponteggio a Telai Prefabbricati (240 m²)",
                    category = "Ponteggi Facciata",
                    startDate = "15/09/2026",
                    endDate = "15/10/2026",
                    durationDays = 30,
                    serviceType = "Noleggio + Consegna & Montaggio PIMUS",
                    siteAddress = "Via Manzoni 18, Monza (MB)",
                    contactName = "Impresa Edile Rossi & C.",
                    contactPhone = "345 1183711",
                    dailyRate = 22.0,
                    totalAmount = 880.0,
                    paymentMethod = "Bonifico Bancario Istantaneo",
                    status = "Confermato",
                    createdAt = 1741770000000L
                )
            )
        }
    }

    val catalogItems: List<EquipmentItem> = listOf(
        EquipmentItem(
            id = "scaffold_frame",
            title = "Ponteggio a Telai Prefabbricati",
            category = EquipmentCategory.FACADE,
            subtitle = "Zincato a caldo • Montaggio rapido a boccole",
            description = "Il sistema più impiegato e affidabile per facciate lineari civili e industriali. Struttura in acciaio zincato ad alta resistenza, tavole di calpestio metalliche antisdrucciolo, botole interne con scaletta integrata e fermapiedi di sicurezza.",
            dailyRate = 18.0,
            monthlyRate = 290.0,
            capacity = "300 kg/m² (Classe 4)",
            maxWorkHeight = "Fino a 50m",
            certification = "UNI EN 12810 / UNI EN 12811",
            features = listOf(
                "Campate standard passo 1,80m e 2,50m",
                "Piano di calpestio forato antiscivolo",
                "Fermapiede in alluminio/acciaio da 15cm",
                "Parapetto di sicurezza e mantovana parasassi",
                "Incluso schema di montaggio e relazione P.I.M.U.S."
            ),
            badge = "Più Richiesto"
        ),
        EquipmentItem(
            id = "scaffold_multi",
            title = "Ponteggio Multidirezionale",
            category = EquipmentCategory.MULTIDIRECTIONAL,
            subtitle = "Massima flessibilità per geometrie complesse",
            description = "Sistema modulare a montanti e rosette per edifici con sagome curve, aggetti, balconi sporgenti, chiese, campanili e strutture industriali pesanti. Permette configurazioni a 360° senza limiti dimensionali.",
            dailyRate = 26.0,
            monthlyRate = 420.0,
            capacity = "450 kg/m² (Classe 5)",
            maxWorkHeight = "Oltre 60m",
            certification = "UNI EN 12810 / UNI EN 12811",
            features = listOf(
                "Nodi con rosetta a 8 fori angolari",
                "Campate regolabili da 0.73m a 3.07m",
                "Ideale per coperture provvisorie e palchi",
                "Elevata rigidità torsionale e antisismica"
            ),
            badge = "Alta Portata"
        ),
        EquipmentItem(
            id = "scaffold_tube_clamp",
            title = "Ponteggio Tubo e Giunto",
            category = EquipmentCategory.FACADE,
            subtitle = "Struttura tradizionale per vincoli speciali",
            description = "Sistema artigianale classico per carichi gravosi, rinforzi strutturali, sottopassaggi e cantieri con sagome non convenzionali. Massima libertà di ancoraggio su ogni superficie.",
            dailyRate = 22.0,
            monthlyRate = 350.0,
            capacity = "500 kg/m² (Heavy Duty)",
            maxWorkHeight = "Personalizzabile",
            certification = "UNI EN 12811-1",
            features = listOf(
                "Tubi in acciaio zincato diametro 48,3mm",
                "Giunti ortogonali e girevoli forgiati a caldo",
                "Adattamento millimetrico a qualunque ostacolo",
                "Ideale per puntellamenti pesanti"
            )
        ),
        EquipmentItem(
            id = "scaffold_tower_mobile",
            title = "Trabattello Professionale Alluminio",
            category = EquipmentCategory.TOWERS,
            subtitle = "Mobile su ruote piroettanti con freno",
            description = "Torre mobile di lavoro ad innesto rapido costruita in lega leggera di alluminio aerospaziale. Rapido da spostare e montare in sicurezza anche da soli due operatori.",
            dailyRate = 38.0,
            monthlyRate = 240.0,
            capacity = "200 kg/m² (Classe 3)",
            maxWorkHeight = "Da 3.5m a 12.8m",
            certification = "UNI EN 1004",
            features = listOf(
                "Ruote in gomma antitraccia da 200mm con freno",
                "Stabilizzatori telescopici orientabili",
                "Piano di lavoro con botola di passaggio e chiusura di sicurezza",
                "Completamente richiudibile per trasporto su furgone"
            ),
            badge = "Leggero & Rapido"
        ),
        EquipmentItem(
            id = "lift_aerial_truck",
            title = "Piattaforma Aerea Cestello 24m",
            category = EquipmentCategory.LIFTS,
            subtitle = "Autocarrata patente B • Mezzo proprio Unipoint",
            description = "Piattaforma di lavoro elevabile su autocarro 35q con braccio telescopico e jib finale per superare ostacoli in copertura. Disponibile a nolo a freddo o con operatore specializzato.",
            dailyRate = 190.0,
            monthlyRate = 2100.0,
            capacity = "250 kg (2 operatori + utensili)",
            maxWorkHeight = "24.0 m (sbraccio 12.5m)",
            certification = "CE & Libretto ISPESL/INAIL",
            features = listOf(
                "Stabilizzazione automatica controllata",
                "Rotazione cestello 90° + 90°",
                "Presa elettrica 230V e condotta aria/acqua in navicella",
                "Disponibile operatore Unipoint con patentino PLE"
            ),
            badge = "Flotta Propria"
        ),
        EquipmentItem(
            id = "lift_telehandler_merlo",
            title = "Sollevatore Telescopico Roto",
            category = EquipmentCategory.LIFTS,
            subtitle = "Rotazione torretta 360° • Portata 40 quintali",
            description = "Macchina da cantiere polivalente con braccio da 18 metri, forche per bancali, gancio per carichi pesanti e navicella estensibile. Efficienza totale in fase di montaggio ponteggio e rifacimento tetto.",
            dailyRate = 250.0,
            monthlyRate = 2600.0,
            capacity = "4.000 kg",
            maxWorkHeight = "18.0 m",
            certification = "CE & Registro Verifiche Periodiche",
            features = listOf(
                "Radiocomando wireless a distanza",
                "Sistema antibalzo e limitatore di carico dinamico",
                "Trasmissione idrostatica 4x4 permanente",
                "Pneumatici antirottura per fango e macerie"
            ),
            badge = "Potenza Massima"
        ),
        EquipmentItem(
            id = "safety_hail_net",
            title = "Rete Antigrandine e Parasassi",
            category = EquipmentCategory.SAFETY_NETS,
            subtitle = "Rete ad alta tenacità per cantieri e tetti",
            description = "Rete monofilamento in polietilene ad alta densità (HDPE) con trattamento anti-UV. Fondamentale sia per la sicurezza pedoni sotto il ponteggio sia come telo protettivo provvisorio su tetti sfondati da grandine.",
            dailyRate = 5.0,
            monthlyRate = 45.0,
            capacity = "Resistenza urto > 300 Joule",
            maxWorkHeight = "Rotoli 2m x 50m / 3m x 100m",
            certification = "UNI 8056 / EN 1263",
            features = listOf(
                "Blocca grandine, detriti e gocce di cemento",
                "Permeabilità all'aria 40% (riduce effetto vela)",
                "Bordi rinforzati con asole per legacci rapidi",
                "Ignifugo Classe 1 a richiesta"
            ),
            badge = "Anti-Emergenza"
        ),
        EquipmentItem(
            id = "lift_scaffold_hoist",
            title = "Montacarichi da Cantiere 500kg",
            category = EquipmentCategory.LIFTS,
            subtitle = "Braccio a bandiera e freno automatico",
            description = "Elevatore elettrico monofase da fissare al ponteggio per la salita rapida di pannelli, tegole, tubi e sacchi di malta. Velocizza del 60% la logistica verticale del cantiere.",
            dailyRate = 28.0,
            monthlyRate = 290.0,
            capacity = "500 kg",
            maxWorkHeight = "Corsa utile fino a 40m",
            certification = "Direttiva Macchine 2006/42/CE",
            features = listOf(
                "Braccio estensibile girevole fino a 180°",
                "Freno autofrenante di sicurezza con arresto immediato",
                "Pulsantiera pensile con stop d'emergenza",
                "Cavo in acciaio antirotazione da 5mm"
            )
        )
    )
}
