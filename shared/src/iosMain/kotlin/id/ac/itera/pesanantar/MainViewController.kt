package id.ac.itera.pesanantar

import androidx.compose.ui.window.ComposeUIViewController
import id.ac.itera.pesanantar.db.DriverFactory
import id.ac.itera.pesanantar.platform.PemberitahuIos

fun MainViewController() = ComposeUIViewController {
    App(
        driverFactory = DriverFactory(),
        pemberitahu = PemberitahuIos()
    )
}