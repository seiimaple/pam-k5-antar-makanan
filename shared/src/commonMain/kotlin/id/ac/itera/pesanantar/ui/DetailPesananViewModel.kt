package id.ac.itera.pesanantar.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ac.itera.pesanantar.model.Pesanan
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DetailPesananViewModel(
    idPesanan: Int,
    daftar: StateFlow<DaftarUiState>
) : ViewModel() {

    val pesanan: StateFlow<Pesanan?> = daftar
        .map { state -> (state as? DaftarUiState.Berhasil)?.pesanan?.find { it.id == idPesanan } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = (daftar.value as? DaftarUiState.Berhasil)?.pesanan?.find { it.id == idPesanan }
        )
}
