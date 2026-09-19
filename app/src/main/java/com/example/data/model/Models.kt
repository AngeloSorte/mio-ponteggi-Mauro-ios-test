package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookings")
data class BookingEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
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
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sender: String,
    val text: String,
    val isFromUser: Boolean,
    val timestamp: Long = System.currentTimeMillis(),
    val badge: String? = null
)

@Entity(tableName = "scaffold_quotes")
data class ScaffoldQuoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
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
    val createdAt: Long = System.currentTimeMillis()
)

// In-memory catalog models
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
