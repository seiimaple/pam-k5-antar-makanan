package id.ac.itera.pesanantar.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.ac.itera.pesanantar.model.Pesanan
import id.ac.itera.pesanantar.model.StatusPesanan

/**
 * Composable untuk menampilkan label status.
 * Urutan Modifier: background dipasang dulu baru padding, supaya warna ikut melebar.
 */
@Composable
fun LabelStatus(status: StatusPesanan) {
    val (teks, warnaLatar, warnaTeks) = when (status) {
        StatusPesanan.MenungguKonfirmasi -> Triple("Menunggu", Color(0xFFE0E0E0), Color(0xFF424242))
        is StatusPesanan.Diproses -> Triple("Dimasak (${status.namaKoki})", Color(0xFFFFF3CD), Color(0xFF856404))
        is StatusPesanan.Diantar -> Triple("Diantar (${status.namaDriver})", Color(0xFFCCE5FF), Color(0xFF004085))
        StatusPesanan.Selesai -> Triple("Selesai", Color(0xFFD4EDDA), Color(0xFF155724))
    }

    Text(
        text = teks,
        color = warnaTeks,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .background(warnaLatar, RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}

/**
 * Composable kartu untuk satu entitas Pesanan.
 * Menerima parameter ringkas dan onKlik agar komponen bisa dipakai ulang.
 */
@Composable
fun KartuPesanan(
    pesanan: Pesanan,
    ringkas: Boolean = false,
    onKlik: () -> Unit = {}
) {
    Card(
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onKlik() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Column memakai weight(1f) agar mengambil sisa ruang kiri dan mendorong LabelStatus ke ujung kanan
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = pesanan.pesanan,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${pesanan.namaPemesan} • ${pesanan.lokasiAntar}",
                    color = Color.Gray,
                    fontSize = 14.sp
                )

                // Menangani catatanKhusus yang bertipe nullable
                if (!ringkas && !pesanan.catatanKhusus.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Catatan: ${pesanan.catatanKhusus}",
                        color = Color.DarkGray,
                        fontSize = 12.sp
                    )
                }
            }

            // Label status di sisi kanan kartu
            LabelStatus(pesanan.status)
        }
    }
}