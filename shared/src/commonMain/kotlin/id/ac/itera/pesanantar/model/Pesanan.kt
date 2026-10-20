package id.ac.itera.pesanantar.model

// data class memberi fungsi otomatis seperti toString(), equals(), dan copy().
data class Pesanan(
    val id: Int,
    val namaPemesan: String,
    val pesanan: String,
    val lokasiAntar: String,
    val catatanKhusus: String?,
    val status: StatusPesanan
) {
    // Fungsi pembantu untuk mendapatkan teks status pesanan
    fun label(): String = status.label()
}