package id.ac.itera.pesanantar.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.ac.itera.pesanantar.model.Pesanan

@Composable
fun LayarDaftarPesanan(
    daftarPesanan: List<Pesanan>,
    onPesananKlik: (Pesanan) -> Unit = {},
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(16.dp)
) {
    val pesananTerbaru = remember(daftarPesanan) {
        daftarPesanan.sortedByDescending { it.id }
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = pesananTerbaru,
            key = { it.id }
        ) { pesanan ->
            KartuPesanan(
                pesanan = pesanan,
                ringkas = false,
                onKlik = { onPesananKlik(pesanan) }
            )
        }
    }
}
