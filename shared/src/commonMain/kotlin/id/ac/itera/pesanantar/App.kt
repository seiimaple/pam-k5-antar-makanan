package id.ac.itera.pesanantar

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import id.ac.itera.pesanantar.data.DaftarPesananDummy
import id.ac.itera.pesanantar.ui.LayarUtama

@Composable
@Preview
fun App() {
    MaterialTheme {
        val daftarPesanan = remember { DaftarPesananDummy.getPesananTerbaru() }
        LayarUtama(daftarPesanan = daftarPesanan)
    }
}
