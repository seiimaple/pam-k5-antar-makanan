package id.ac.itera.pesanantar.ui

/**
 * Satu data class untuk menampung seluruh state layar form pesanan.
 * Setiap properti memiliki nilai bawaan (default value).
 */
data class FormPesananUiState(
    val namaPemesan: String = "",
    val pesanan: String = "",
    val lokasiAntar: String = "",
    val catatanKhusus: String = "",
    val sedangMenyimpan: Boolean = false,
    val pesan: String? = null
) {
    /**
     * Properti terhitung (computed property): tidak disimpan sebagai state terpisah.
     * Tombol simpan hanya aktif jika kolom wajib terisi dan tidak sedang proses menyimpan.
     */
    val bisaSimpan: Boolean
        get() = namaPemesan.isNotBlank() &&
                pesanan.isNotBlank() &&
                lokasiAntar.isNotBlank() &&
                !sedangMenyimpan
}