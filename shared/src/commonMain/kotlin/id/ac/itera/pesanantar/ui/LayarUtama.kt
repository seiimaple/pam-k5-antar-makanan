package id.ac.itera.pesanantar.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.ac.itera.pesanantar.model.Pesanan
import org.jetbrains.compose.resources.painterResource
import pesanantarmakanan.shared.generated.resources.Res
import pesanantarmakanan.shared.generated.resources.ic_add

@Composable
fun LayarUtama(
    daftarPesanan: List<Pesanan>,
    // Modul 03 hanya meminta tampilan FAB; aksi tambah dibuat pada tahap berikutnya.
    onTambahPesanan: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = onTambahPesanan) {
                Icon(
                    painter = painterResource(Res.drawable.ic_add),
                    contentDescription = "Tambah pesanan"
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
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
                    text = "${daftarPesanan.size} pesanan · Terbaru di atas",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            HorizontalDivider()

            LayarDaftarPesanan(
                daftarPesanan = daftarPesanan,
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
