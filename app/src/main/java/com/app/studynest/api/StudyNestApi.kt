package com.app.studynest.api

import com.app.studynest.data.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface StudyNestApi {

    @POST("auth/login")
    suspend fun login(@Body request: Map<String, String>): LoginResponse

    @GET("user/dashboard-stats")
    suspend fun getDashboardStats(): DashboardStats

    @GET("seats")
    suspend fun getSeats(
        @Query("date") date: String,
        @Query("hall") hallId: String
    ): List<Seat>

    @POST("bookings/create")
    suspend fun createBooking(@Body request: Booking): Booking

    @GET("plans")
    suspend fun getPlans(): List<Plan>

    @GET("bookings")
    suspend fun getBookings(@Query("user_id") userId: String): List<Booking>
}
