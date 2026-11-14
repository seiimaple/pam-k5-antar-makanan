package id.ac.itera.pesanantar

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import id.ac.itera.pesanantar.db.DriverFactory

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "PesanAntarMakanan",
    ) {
        App(
            driverFactory = DriverFactory(),
            apiBaseUrl = System.getenv("PESANAN_API_URL")
        )
    }
}
