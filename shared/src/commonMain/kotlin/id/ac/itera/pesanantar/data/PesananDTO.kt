package id.ac.itera.pesanantar.data

import kotlinx.serialization.Serializable

@Serializable
data class PesananDto(
    val id: Int,
    val nama: String,
    val pesanan: String,
    val lokasi: String,
    val status: String,
    val catatan: String? = null // Boleh kosong (null)
)

// DTO khusus untuk form saat mengirim pesanan baru (karena ID dibuat oleh server)
@Serializable
data class PesananBaruDto(
    val nama: String,
    val pesanan: String,
    val lokasi: String,
    val catatan: String? = null
)