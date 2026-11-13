package id.ac.itera.pesanantar.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ac.itera.pesanantar.data.SumberPesananJaringan
import id.ac.itera.pesanantar.data.kePesanIndonesia
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class DaftarViewModel(
    private val sumber: SumberPesananJaringan
) : ViewModel() {
    private val _uiState = MutableStateFlow<DaftarUiState>(DaftarUiState.Memuat)
    val uiState: StateFlow<DaftarUiState> = _uiState.asStateFlow()
    private var pemuatan: Job? = null

    init {
        muat()
    }

    fun muat() {
        pemuatan?.cancel()
        pemuatan = viewModelScope.launch {
            _uiState.value = DaftarUiState.Memuat
            try {
                val hasilPesanan = sumber.getDaftarPesanan()
                _uiState.value = DaftarUiState.Berhasil(hasilPesanan)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = DaftarUiState.Gagal(e.kePesanIndonesia())
            }
        }
    }
}
