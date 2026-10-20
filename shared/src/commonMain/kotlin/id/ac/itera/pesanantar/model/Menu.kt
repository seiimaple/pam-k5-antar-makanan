package id.ac.itera.pesanantar.model


data class Menu(
    val id: Int,
    val namaMenu: String,
    val harga: Long,
    val jumlahMenu: Int = 1,
    val catatanKhusus: String? = null,
    val status: StatusPesanan? = null
) {
    fun tampilkanDetailMenu() {
        println("- $namaMenu: Rp$harga")
    }

    // Fungsi label() untuk menampilkan status atau detail ringkas menu
    fun label(): String = status?.label() ?: "$namaMenu (Rp$harga)"
}