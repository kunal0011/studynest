package com.app.studynest.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.studynest.ui.screens.CheckoutScreen
import com.app.studynest.ui.screens.DashboardScreen
import com.app.studynest.ui.screens.LoginScreen
import com.app.studynest.ui.screens.PaymentScreen
import com.app.studynest.ui.screens.SeatAvailabilityScreen
import com.app.studynest.ui.screens.SelectPlanScreen
import com.app.studynest.ui.screens.UserActionScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
    object UserAction : Screen("user_action")
    object SeatAvailability : Screen("seat_availability")
    object PlanSelection : Screen("plan_selection")
    object Checkout : Screen("checkout")
    object Payment : Screen("payment")
}

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ) {
        composable(Screen.Login.route) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onSelectAction = { navController.navigate(Screen.UserAction.route) },
                onSelectPlan = { navController.navigate(Screen.PlanSelection.route) },
                onSeatAvailability = { navController.navigate(Screen.SeatAvailability.route) },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.UserAction.route) {
            UserActionScreen(onNext = { navController.navigate(Screen.SeatAvailability.route) })
        }
        composable(Screen.SeatAvailability.route) {
            SeatAvailabilityScreen(
                onProceed = { navController.navigate(Screen.PlanSelection.route) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.PlanSelection.route) {
            SelectPlanScreen(
                onPlanSelected = { navController.navigate(Screen.Checkout.route) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Checkout.route) {
            CheckoutScreen(
                onPayment = { navController.navigate(Screen.Payment.route) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Payment.route) {
            PaymentScreen(
                onPaymentSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
