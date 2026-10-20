package id.ac.itera.pesanantar.model

data class Kantin(
    val id: Int,
    val namaKantin: String,
    val daftarMenu: MutableList<Menu> = mutableListOf()
) {
    fun tambahMenu(menu: Menu) {
        daftarMenu.add(menu)
    }

    fun lihatSemuaMenu() {
        println("=== Menu di $namaKantin ===")
        if (daftarMenu.isEmpty()) {
            println("Belum ada menu tersedia.")
        } else {
            for (menu in daftarMenu) {
                menu.tampilkanDetailMenu()
            }
        }
    }
}