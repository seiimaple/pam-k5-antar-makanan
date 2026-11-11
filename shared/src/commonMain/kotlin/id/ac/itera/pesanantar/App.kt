package id.ac.itera.pesanantar

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.consumeWindowInsets
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
import id.ac.itera.pesanantar.ui.LayarAsisten
import id.ac.itera.pesanantar.ui.LayarDetailPesanan
import id.ac.itera.pesanantar.ui.LayarProfil
import id.ac.itera.pesanantar.ui.LayarTambahPesanan
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

        val entry by nav.currentBackStackEntryAsState()
        val tujuan = entry?.destination

        Scaffold(
            bottomBar = {
                if (daftarTab.any { tujuan?.hasRoute(it.rute::class) == true }) {
                    NavigationBar {
                        daftarTab.forEach { tab ->
                            NavigationBarItem(
                                selected = tujuan?.hasRoute(tab.rute::class) == true,
                                onClick = { pindahTab(nav, tab.rute) },
                                icon = { Text(tab.label.take(1)) },
                                label = { Text(tab.label) }
                            )
                        }
                    }
                }
            }
        ) { padding ->
            NavHost(
                navController = nav,
                startDestination = RuteBeranda,
                modifier = Modifier.padding(padding).consumeWindowInsets(padding)
            ) {

                composable<RuteBeranda> {
                    LayarUtama(
                        daftarPesanan = DaftarPesananDummy.listOfPesanan,
                        onPesananKlik = { nav.navigate(RuteDetail(it.id)) },
                        onTambahPesanan = { nav.navigate(RuteForm) }
                    )
                }

                composable<RuteAsisten> {
                    LayarAsisten()
                }

                composable<RuteProfil> {
                    LayarProfil()
                }

                composable<RuteForm> {
                    LayarTambahPesanan(onKembali = { nav.popBackStack() })
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
