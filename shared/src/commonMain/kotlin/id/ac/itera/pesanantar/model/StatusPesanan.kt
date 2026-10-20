package id.ac.itera.pesanantar.model

// Sealed class memastikan pilihan status tertutup dan compiler tahu semua turunannya
sealed class StatusPesanan {
    // Memakai data object untuk status yang tidak membawa data tambahan
    data object MenungguKonfirmasi : StatusPesanan()

    data class Diproses(val namaKoki: String) : StatusPesanan()
    data class Diantar(val namaDriver: String) : StatusPesanan()

    data object Selesai : StatusPesanan()

    // Fungsi label() untuk menampilkan status dalam bentuk teks
    fun label(): String = when (this) {
        is MenungguKonfirmasi -> "Menunggu Konfirmasi"
        is Diproses -> "Pesanan sedang diproses oleh koki $namaKoki"
        is Diantar -> "Pesanan sedang diantar oleh driver $namaDriver"
        is Selesai -> "Pesanan selesai"
    }
}

fun label(status: StatusPesanan): String = status.label()