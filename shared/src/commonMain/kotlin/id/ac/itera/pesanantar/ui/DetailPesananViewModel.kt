package id.ac.itera.pesanantar.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ac.itera.pesanantar.model.Pesanan
import id.ac.itera.pesanantar.platform.Pemberitahu
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailPesananViewModel(
    idPesanan: Int,
    daftar: StateFlow<DaftarUiState>,
    private val pemberitahu: Pemberitahu
) : ViewModel() {

    val pesanan: StateFlow<Pesanan?> = daftar
        .map { state -> (state as? DaftarUiState.Berhasil)?.pesanan?.find { it.id == idPesanan } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = (daftar.value as? DaftarUiState.Berhasil)?.pesanan?.find { it.id == idPesanan }
        )

    init {
        viewModelScope.launch {
            var statusSebelumnya = pesanan.value?.status

            pesanan.collect { pesananBaru ->
                val statusSekarang = pesananBaru?.status

                if (statusSekarang != null && statusSebelumnya != null && statusSekarang != statusSebelumnya) {
                    pemberitahu.tampilkan(
                        judul = "Status Pesanan Diperbarui",
                        isi = "Pesanan ${pesananBaru?.namaPemesan} sekarang: $statusSekarang"
                    )
                }

                statusSebelumnya = statusSekarang
            }
        }
    }
}