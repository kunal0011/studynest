package com.app.studynest.data.repository

import com.app.studynest.api.StudyNestApi
import com.app.studynest.data.local.BookingDao
import com.app.studynest.data.local.BookingEntity
import com.app.studynest.data.local.UserDao
import com.app.studynest.data.local.UserEntity
import com.app.studynest.data.model.Booking
import com.app.studynest.data.model.DashboardStats
import com.app.studynest.data.model.Plan
import com.app.studynest.data.model.Seat
import com.app.studynest.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudyNestRepository @Inject constructor(
    private val api: StudyNestApi,
    private val userDao: UserDao,
    private val bookingDao: BookingDao
) {

    // Auth
    suspend fun login(email: String, password: String): Result<User> {
        return try {
            // Mocking API call for now since we don't have a real backend
            // val response = api.login(mapOf("email" to email, "password" to password))
            // userDao.insertUser(response.user.toEntity())
            // Result.success(response.user)
            
            // Mock Success
            val mockUser = User("1", "Test User", email, null)
            userDao.insertUser(mockUser.toEntity())
            Result.success(mockUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    val currentUser: Flow<User?> = userDao.getUser().map { it?.toDomain() }

    // Dashboard
    suspend fun getDashboardStats(): Result<DashboardStats> {
        return try {
            // val stats = api.getDashboardStats()
            // Result.success(stats)
            
            // Mock Stats
            Result.success(DashboardStats(24, 156, null))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Seats
    suspend fun getSeats(date: String, hallId: String): Result<List<Seat>> {
        return try {
            // val seats = api.getSeats(date, hallId)
            // Result.success(seats)
            
            // Mock Seats
            val seats = mutableListOf<Seat>()
            val rows = listOf("A", "B", "C", "D")
            val cols = 1..4
            rows.forEach { row ->
                cols.forEach { col ->
                    val status = if (row == "A" && col == 2) "OCCUPIED" else "AVAILABLE"
                    seats.add(Seat("${row}${col}", "${row}${col}", "STANDARD", status))
                }
            }
            Result.success(seats)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



    // Bookings
    val bookings: Flow<List<Booking>> = bookingDao.getBookingsForUser("1").map { entities ->
        entities.map { it.toDomain() }
    }

    suspend fun syncBookings(userId: String): Result<Unit> {
        return try {
            // val bookings = api.getBookings(userId)
            // bookingDao.insertBookings(bookings.map { it.toEntity() })
            
            // Mock Sync
            val bookings = listOf(
                Booking("101", "A1", userId, System.currentTimeMillis(), System.currentTimeMillis() + 3600000, "CONFIRMED"),
                Booking("102", "B2", userId, System.currentTimeMillis() - 86400000, System.currentTimeMillis() - 82800000, "COMPLETED")
            )
            bookingDao.insertBookings(bookings.map { it.toEntity() })
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createBooking(booking: Booking): Result<Booking> {
        return try {
            // val response = api.createBooking(booking)
            // bookingDao.insertBooking(response.toEntity())
            // Result.success(response)
            
            // Mock Booking
            bookingDao.insertBooking(booking.toEntity())
            Result.success(booking)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    // Plans
    suspend fun getPlans(): Result<List<Plan>> {
        return try {
            // val plans = api.getPlans()
            // Result.success(plans)
            
            // Mock Plans
            val plans = listOf(
                com.app.studynest.data.model.Plan("1", "Monthly Plan", "₹1,999/month", "Unlimited access, best value"),
                com.app.studynest.data.model.Plan("2", "Quarterly Plan", "₹5,499/3 months", "Save 8% compared to monthly"),
                com.app.studynest.data.model.Plan("3", "Half Yearly", "₹9,999/6 months", "Save 15% compared to monthly")
            )
            Result.success(plans)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

// Mappers
fun User.toEntity() = UserEntity(id, name, email, profileUrl)
fun UserEntity.toDomain() = User(id, name, email, profileUrl)

fun Booking.toEntity() = BookingEntity(id, seatId, userId, startTime, endTime, status)
fun BookingEntity.toDomain() = Booking(id, seatId, userId, startTime, endTime, status)
