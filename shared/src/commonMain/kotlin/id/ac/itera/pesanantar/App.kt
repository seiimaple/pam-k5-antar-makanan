package id.ac.itera.pesanantar

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import id.ac.itera.pesanantar.nav.*
import id.ac.itera.pesanantar.data.DaftarPesananDummy
import id.ac.itera.pesanantar.ui.LayarUtama

@Composable
@Preview
fun App() {
    MaterialTheme {
        // 1. Membuat NavController (Si Pengemudi)
        val nav = rememberNavController()

        // 2. Membuat NavHost (Si Peta)
        NavHost(
            navController = nav,
            startDestination = RuteBeranda
        ) {

            // --- 3 Tab Menu Bawah ---
            composable<RuteBeranda> {
                // Memanggil LayarUtama yang sudah ada agar aplikasi tetap berjalan normal
                // Fadzilah akan mengubah ini untuk memasukkan Scaffold (Menu Bawah)
                LayarUtama(daftarPesanan = DaftarPesananDummy.listOfPesanan)
            }

            composable<RuteAsisten> {
                // TODO (Marvin): Masukkan LayarAsisten (Fitur AI) di sini
            }

            composable<RuteProfil> {
                // TODO (Marvin): Masukkan LayarProfil di sini
            }

            // --- Layar Tambahan ---
            composable<RuteForm> {
                // TODO (Fadzilah): Masukkan FormPesananScreen di sini
            }

            composable<RuteDetail> { entry ->
                // Menangkap id pesanan dari rute tujuan secara aman
                val rute = entry.toRoute<RuteDetail>()
                val idPesanan = rute.id

                // TODO (Daniel): Masukkan LayarDetailPesanan(id = idPesanan) di sini
            }
        }
    }
}