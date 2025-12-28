package com.app.studynest.data.model

import com.squareup.moshi.Json

data class User(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "email") val email: String,
    @Json(name = "profile_url") val profileUrl: String?
)

data class Seat(
    @Json(name = "id") val id: String,
    @Json(name = "label") val label: String,
    @Json(name = "type") val type: String, // "STANDARD", "WINDOW"
    @Json(name = "status") val status: String // "AVAILABLE", "OCCUPIED"
)

data class Booking(
    @Json(name = "id") val id: String,
    @Json(name = "seat_id") val seatId: String,
    @Json(name = "user_id") val userId: String,
    @Json(name = "start_time") val startTime: Long,
    @Json(name = "end_time") val endTime: Long,
    @Json(name = "status") val status: String
)

data class LoginResponse(
    @Json(name = "token") val token: String,
    @Json(name = "user") val user: User
)

data class DashboardStats(
    @Json(name = "hours_this_week") val hoursThisWeek: Int,
    @Json(name = "total_hours") val totalHours: Int,
    @Json(name = "current_booking") val currentBooking: Booking?
)

data class Plan(
    @Json(name = "id") val id: String,
    @Json(name = "title") val title: String,
    @Json(name = "price") val price: String,
    @Json(name = "description") val description: String
)
