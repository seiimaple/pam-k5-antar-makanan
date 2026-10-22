package id.ac.itera.pesanantar.data

import id.ac.itera.pesanantar.model.Pesanan
import id.ac.itera.pesanantar.model.StatusPesanan

object DaftarPesananDummy {
    val listOfPesanan = listOf(
        Pesanan(1, "Daniel", "Sambal Manis Ayam", "Korpri", "Pake Alat Makan", StatusPesanan.MenungguKonfirmasi),
        Pesanan(2, "Rifka", "Nasi Goreng Spesial", "Lab Komputer 3", "Tanpa acar", StatusPesanan.Diproses("Chef Juna")),
        Pesanan(3, "Marvin", "Mie Ayam Bakso", "Gedung C Lt. 2", null, StatusPesanan.Diantar("Bang Ojol")),
        Pesanan(4, "Fadzilah", "Soto Ayam + Nasi", "Perpustakaan Lt. 1", "Jeruk nipis dipisah", StatusPesanan.Selesai),
        Pesanan(5, "Budi", "Batagor Kuah", "Gedung E", null, StatusPesanan.Diproses("Koki Santoso")),
        Pesanan(6, "Siti", "Pecel Lele + Nasi Uduk", "Asrama Putri TB 2", "Sambal banyak", StatusPesanan.Diantar("Pak Anto")),
        Pesanan(7, "Andi", "Kwetiau Goreng Seafood", "Kantin GKU", null, StatusPesanan.MenungguKonfirmasi),
        Pesanan(8, "Citra", "Nasi Padang Rendang", "Gedung Kuliah B R.201", "Kuah gulai dipisah", StatusPesanan.Selesai),
        Pesanan(9, "Eko", "Ayam Bakar Madu", "Lab Elektro", null, StatusPesanan.Diproses("Chef Arnold")),
        Pesanan(10, "Dewi", "Es Campur + Roti Bakar", "Student Center", null, StatusPesanan.Diantar("Pak Herman")),
        Pesanan(11, "Nanin", "Nasi Kuning Katsu", "Way Huwi", null, StatusPesanan.Selesai)
    )

    fun getPesananTerbaru(): List<Pesanan> = listOfPesanan.sortedByDescending { it.id }
    fun getPesananAktif(): List<Pesanan> = listOfPesanan.filter{ it.status !is StatusPesanan.Selesai }
}