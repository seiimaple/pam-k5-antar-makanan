package id.ac.itera.pesanantar.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import id.ac.itera.pesanantar.model.Pesanan
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.painterResource
import pesanantarmakanan.shared.generated.resources.Res
import pesanantarmakanan.shared.generated.resources.ic_add

@Composable
fun LayarUtama(
    daftarPesanan: StateFlow<List<Pesanan>>
) {
    val pesanan by daftarPesanan.collectAsState()
    var tampilkanForm by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            if (!tampilkanForm) {
                FloatingActionButton(onClick = { tampilkanForm = true }) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_add),
                        contentDescription = "Tambah pesanan"
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (tampilkanForm) {
                val formViewModel: FormPesananViewModel = viewModel {
                    FormPesananViewModel()
                }
                val formState by formViewModel.uiState.collectAsState()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = { tampilkanForm = false },
                        enabled = !formState.sedangMenyimpan
                    ) {
                        Text("Kembali")
                    }
                    Text(
                        text = "Tambah Pesanan",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                HorizontalDivider()

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .imePadding()
                        .verticalScroll(rememberScrollState())
                ) {
                    FormPesananScreen(
                        vm = formViewModel,
                        onKembali = { tampilkanForm = false }
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp)
                ) {
                    Text(
                        text = "Pesanan Kantin",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = "${pesanan.size} pesanan · Terbaru di atas",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                HorizontalDivider()

                LayarDaftarPesanan(
                    daftarPesanan = pesanan,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp,
                        bottom = 88.dp
                    )
                )
            }
        }
    }
}
