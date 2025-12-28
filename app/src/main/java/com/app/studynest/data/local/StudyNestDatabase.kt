package com.app.studynest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class, BookingEntity::class], version = 1, exportSchema = false)
abstract class StudyNestDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun bookingDao(): BookingDao
}
