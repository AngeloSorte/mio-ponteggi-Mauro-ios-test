package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.BookingEntity
import com.example.data.model.ChatMessageEntity
import com.example.data.model.ScaffoldQuoteEntity

@Database(
    entities = [
        BookingEntity::class,
        ChatMessageEntity::class,
        ScaffoldQuoteEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookingDao(): BookingDao
    abstract fun chatMessageDao(): ChatMessageDao
    abstract fun scaffoldQuoteDao(): ScaffoldQuoteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "unipoint_ponteggi.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
