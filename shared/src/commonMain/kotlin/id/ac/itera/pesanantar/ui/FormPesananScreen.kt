package id.ac.itera.pesanantar.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FormPesananScreen(
    vm: FormPesananViewModel = viewModel { FormPesananViewModel() },
    onKembali: () -> Unit = {}
) {
    val state by vm.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        KolomTeks(
            label = "Nama Pemesan",
            value = state.namaPemesan,
            onValueChange = vm::ubahNamaPemesan
        )

        KolomTeks(
            label = "Pesanan",
            value = state.pesanan,
            onValueChange = vm::ubahPesanan
        )

        KolomTeks(
            label = "Lokasi Antar",
            value = state.lokasiAntar,
            onValueChange = vm::ubahLokasiAntar
        )

        KolomTeks(
            label = "Catatan Khusus (Opsional)",
            value = state.catatanKhusus,
            onValueChange = vm::ubahCatatanKhusus
        )

        Button(
            onClick = {
                vm.simpan(onBerhasilSimpan = onKembali)
            },
            enabled = state.bisaSimpan,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text(if (state.sedangMenyimpan) "Menyimpan..." else "Simpan Pesanan")
        }

        state.pesan?.let {
            Text(text = it, color = Color.Gray)
        }
    }
}