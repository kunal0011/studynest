package com.app.studynest.di

import android.content.Context
import androidx.room.Room
import com.app.studynest.data.local.BookingDao
import com.app.studynest.data.local.StudyNestDatabase
import com.app.studynest.data.local.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): StudyNestDatabase {
        return Room.databaseBuilder(
            context,
            StudyNestDatabase::class.java,
            "studynest_db"
        ).build()
    }

    @Provides
    fun provideUserDao(database: StudyNestDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideBookingDao(database: StudyNestDatabase): BookingDao {
        return database.bookingDao()
    }
}
