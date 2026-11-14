package id.ac.itera.pesanantar

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.lifecycle.viewmodel.compose.viewModel
import id.ac.itera.pesanantar.data.PesananApi
import id.ac.itera.pesanantar.data.SumberPesananOffline
import id.ac.itera.pesanantar.db.DriverFactory
import id.ac.itera.pesanantar.db.PesanAntarDb
import id.ac.itera.pesanantar.nav.*
import id.ac.itera.pesanantar.ui.DaftarViewModel
import id.ac.itera.pesanantar.ui.DetailPesananViewModel
import id.ac.itera.pesanantar.ui.FormPesananViewModel
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
fun App() = App(driverFactory = null, apiBaseUrl = null)

@Composable
fun App(driverFactory: DriverFactory?, apiBaseUrl: String? = null) {
    if (driverFactory == null) return

    val sumber = remember(driverFactory, apiBaseUrl) {
        val db = PesanAntarDb(driverFactory.createDriver())
        val api = if (apiBaseUrl.isNullOrBlank()) PesananApi() else PesananApi(baseUrl = apiBaseUrl.trimEnd('/'))
        SumberPesananOffline(db = db, api = api)
    }

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
                    val daftarViewModel = viewModel { DaftarViewModel(sumber) }
                    val daftar by daftarViewModel.uiState.collectAsState()
                    val pesanOffline by daftarViewModel.pesan.collectAsState()
                    LayarUtama(
                        state = daftar,
                        pesanOffline = pesanOffline,
                        onCobaLagi = daftarViewModel::muat,
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

                composable<RuteForm> { formEntry ->
                    val beranda = remember(formEntry) { nav.getBackStackEntry<RuteBeranda>() }
                    val daftarViewModel = viewModel(viewModelStoreOwner = beranda) { DaftarViewModel(sumber) }
                    val formViewModel = viewModel { FormPesananViewModel(sumber) }
                    LayarTambahPesanan(
                        formViewModel = formViewModel,
                        onKembali = { nav.popBackStack() },
                        onBerhasilSimpan = {
                            daftarViewModel.muat()
                            nav.popBackStack()
                        }
                    )
                }

                composable<RuteDetail> { entry ->
                    val idPesanan = entry.toRoute<RuteDetail>().id
                    val beranda = remember(entry) { nav.getBackStackEntry<RuteBeranda>() }
                    val daftarViewModel = viewModel(viewModelStoreOwner = beranda) { DaftarViewModel(sumber) }
                    val detailViewModel = viewModel { DetailPesananViewModel(idPesanan, daftarViewModel.uiState) }
                    LayarDetailPesanan(
                        idPesanan = idPesanan,
                        onKembali = { nav.popBackStack() },
                        vm = detailViewModel
                    )
                }
            }
        }
    }
}
