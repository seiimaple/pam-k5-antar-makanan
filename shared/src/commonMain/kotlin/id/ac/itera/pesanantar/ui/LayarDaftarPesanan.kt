package id.ac.itera.pesanantar.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.ac.itera.pesanantar.model.Pesanan

@Composable
fun LayarDaftarPesanan(
    state: DaftarUiState,
    onCobaLagi: () -> Unit,
    onPesananKlik: (Pesanan) -> Unit = {},
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(16.dp)
) {
    Box(modifier = modifier) {
        when (state) {
            DaftarUiState.Memuat -> Column(
                modifier = Modifier.align(Alignment.Center).padding(contentPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CircularProgressIndicator()
                Text("Memuat pesanan…")
            }

            is DaftarUiState.Berhasil -> {
                val pesananTerbaru = remember(state.pesanan) {
                    state.pesanan.sortedByDescending { it.id }
                }
                if (pesananTerbaru.isEmpty()) {
                    Text("Belum ada pesanan.", modifier = Modifier.align(Alignment.Center))
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = contentPadding,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(pesananTerbaru, key = { it.id }) { pesanan ->
                            KartuPesanan(
                                pesanan = pesanan,
                                ringkas = false,
                                onKlik = { onPesananKlik(pesanan) }
                            )
                        }
                    }
                }
            }

            is DaftarUiState.Gagal -> PesanGagal(
                pesan = state.pesan,
                onCobaLagi = onCobaLagi,
                modifier = Modifier.align(Alignment.Center).padding(contentPadding)
            )
        }
    }
}
