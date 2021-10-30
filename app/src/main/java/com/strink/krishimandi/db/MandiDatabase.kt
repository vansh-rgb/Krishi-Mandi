package com.strink.krishimandi.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.strink.krishimandi.model.OtherMandi

@Database(entities = [OtherMandi::class], version = 1)
abstract class MandiDatabase : RoomDatabase() {

    abstract fun mandiDao() : MandiDao

    companion object {
        @Volatile
        private var INSTANCE: MandiDatabase? = null

        fun getDatabase(context: Context): MandiDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        MandiDatabase::class.java,
                        "mandiDB"
                    )
                        .build()
                }
            }
            return INSTANCE!!
        }
    }

}