package id.ac.itera.pesanantar.ui

import id.ac.itera.pesanantar.model.Pesanan

sealed interface DaftarUiState {
    data object Memuat : DaftarUiState
    data class Berhasil(
        val pesanan: List<Pesanan>
    ) : DaftarUiState
    data class Gagal(
        val pesan: String
    ) : DaftarUiState
}