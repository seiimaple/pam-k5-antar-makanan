package id.ac.itera.pesanantar.nav

import kotlinx.serialization.Serializable

// --- Rute untuk 3 Tab Menu Bawah ---
// Menggunakan 'object' karena layar-layar ini dibuka secara langsung tanpa perlu membawa data tambahan.
@Serializable
object RuteBeranda

@Serializable
object RuteAsisten

@Serializable
object RuteProfil

// --- Rute Layar Tambahan ---
@Serializable
object RuteForm

// Menggunakan 'data class' karena layar detail membutuhkan argumen (data) berupa ID pesanan.
// Saat dipanggil nanti bentuknya menjadi nav.navigate(RuteDetail(1))
@Serializable
data class RuteDetail(val id: Int)