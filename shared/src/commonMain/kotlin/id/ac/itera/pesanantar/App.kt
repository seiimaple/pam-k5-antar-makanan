package id.ac.itera.pesanantar

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import id.ac.itera.pesanantar.data.DaftarPesananDummy
import id.ac.itera.pesanantar.nav.*
import id.ac.itera.pesanantar.ui.FormPesananScreen
import id.ac.itera.pesanantar.ui.LayarDetailPesanan
import id.ac.itera.pesanantar.ui.LayarUtama

// Struktur data pembantu tanpa icon bawaan
data class TabItem(
    val label: String,
    val rute: Any
)

@Composable
@Preview
fun App() {
    MaterialTheme {
        val nav = rememberNavController()

        // 3 Tab tanpa mendefinisikan icon
        val daftarTab = listOf(
            TabItem("Beranda", RuteBeranda),
            TabItem("Asisten", RuteAsisten),
            TabItem("Profil", RuteProfil)
        )

        Scaffold(
            bottomBar = {
                NavigationBar {
                    val entry by nav.currentBackStackEntryAsState()

                    daftarTab.forEach { tab ->
                        NavigationBarItem(
                            selected = entry?.destination?.hasRoute(tab.rute::class) == true,
                            onClick = { pindahTab(nav, tab.rute) },
                            icon = { Text(tab.label.take(1)) },
                            label = { Text(tab.label) }
                        )
                    }
                }
            }
        ) { padding ->
            NavHost(
                navController = nav,
                startDestination = RuteBeranda,
                modifier = Modifier.padding(padding)
            ) {

                composable<RuteBeranda> {
                    LayarUtama(daftarPesanan = DaftarPesananDummy.listOfPesanan)
                }

                composable<RuteAsisten> {
                    // TODO (Marvin): Masukkan LayarAsisten (Fitur AI) di sini
                }

                composable<RuteProfil> {
                    // TODO (Marvin): Masukkan LayarProfil di sini
                }

                composable<RuteForm> {
                    composable<RuteForm> {
                        FormPesananScreen(
                            onKembali = { nav.popBackStack() }
                        )
                    }
                }

                composable<RuteDetail> { entry ->
                    val rute = entry.toRoute<RuteDetail>()
                    val idPesanan = rute.id

                    LayarDetailPesanan(
                        idPesanan = idPesanan,
                        onKembali = { nav.popBackStack() }
                    )
                }
            }
        }
    }
}