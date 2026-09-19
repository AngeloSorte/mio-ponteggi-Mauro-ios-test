package com.example.data.model

data class BookingEntity(
    val id: Long = 0,
    val bookingCode: String,
    val itemTitle: String,
    val category: String,
    val startDate: String,
    val endDate: String,
    val durationDays: Int,
    val serviceType: String,
    val siteAddress: String,
    val contactName: String,
    val contactPhone: String,
    val dailyRate: Double,
    val totalAmount: Double,
    val paymentMethod: String,
    val status: String = "Confermato",
    val createdAt: Long = 0L
)

data class ChatMessageEntity(
    val id: Long = 0,
    val sender: String,
    val text: String,
    val isFromUser: Boolean,
    val timestamp: Long = 0L,
    val badge: String? = null
)

data class ScaffoldQuoteEntity(
    val id: Long = 0,
    val title: String,
    val structureType: String,
    val widthMeters: Float,
    val heightMeters: Float,
    val deckLevels: Int,
    val bayCount: Int,
    val includeNetting: Boolean,
    val includeToeBoards: Boolean,
    val includeHoist: Boolean,
    val includeLadderHatch: Boolean,
    val estimatedMonthlyCost: Double,
    val siteAddress: String,
    val createdAt: Long = 0L
)

enum class EquipmentCategory(val label: String) {
    ALL("Tutti"),
    FACADE("Ponteggi Facciata"),
    MULTIDIRECTIONAL("Multidirezionali"),
    TOWERS("Trabattelli Mobili"),
    LIFTS("Mezzi Sollevamento"),
    SAFETY_NETS("Reti & Coperture")
}

data class EquipmentItem(
    val id: String,
    val title: String,
    val category: EquipmentCategory,
    val subtitle: String,
    val description: String,
    val dailyRate: Double,
    val monthlyRate: Double,
    val capacity: String,
    val maxWorkHeight: String,
    val certification: String,
    val features: List<String>,
    val badge: String? = null
)
