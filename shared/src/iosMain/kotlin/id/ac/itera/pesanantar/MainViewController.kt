package id.ac.itera.pesanantar

import androidx.compose.ui.window.ComposeUIViewController
import id.ac.itera.pesanantar.db.DriverFactory

fun MainViewController() = ComposeUIViewController { App(driverFactory = DriverFactory()) }