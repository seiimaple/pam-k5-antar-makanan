package id.ac.itera.pesanantar.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ac.itera.pesanantar.data.SumberPesananOffline
import id.ac.itera.pesanantar.data.kePesanIndonesia
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class DaftarViewModel(
    private val sumber: SumberPesananOffline
) : ViewModel() {
    private val _uiState = MutableStateFlow<DaftarUiState>(DaftarUiState.Memuat)
    val uiState: StateFlow<DaftarUiState> = _uiState.asStateFlow()
    private var pemuatan: Job? = null

    init {
        viewModelScope.launch {
            sumber.daftarPesanan.collect { daftar ->
                if (daftar.isNotEmpty() || _uiState.value is DaftarUiState.Berhasil) {
                    _uiState.value = DaftarUiState.Berhasil(daftar)
                }
            }
        }
        muat()
    }

    fun muat() {
        pemuatan?.cancel()
        pemuatan = viewModelScope.launch {
            if (_uiState.value !is DaftarUiState.Berhasil) {
                _uiState.value = DaftarUiState.Memuat
            }
            try {
                sumber.segarkan()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                if (_uiState.value !is DaftarUiState.Berhasil) {
                    _uiState.value = DaftarUiState.Gagal(e.kePesanIndonesia())
                }
            }
        }
    }
}
