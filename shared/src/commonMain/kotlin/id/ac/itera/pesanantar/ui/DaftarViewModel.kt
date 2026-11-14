package id.ac.itera.pesanantar.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ac.itera.pesanantar.data.SumberPesananOffline
import id.ac.itera.pesanantar.data.kePesanIndonesia
import id.ac.itera.pesanantar.model.Pesanan
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class DaftarViewModel(
    private val sumber: SumberPesananOffline
) : ViewModel() {

    val pesanan: StateFlow<List<Pesanan>> = sumber.daftarPesanan
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _pesan = MutableStateFlow<String?>(null)
    val pesan: StateFlow<String?> = _pesan.asStateFlow()

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
        segarkan()
    }

    fun segarkan() {
        pemuatan?.cancel()
        pemuatan = viewModelScope.launch {
            if (_uiState.value !is DaftarUiState.Berhasil) {
                _uiState.value = DaftarUiState.Memuat
            }
            _pesan.value = try {
                sumber.segarkan()
                if (_uiState.value !is DaftarUiState.Berhasil) {
                    _uiState.value = DaftarUiState.Berhasil(emptyList())
                }
                null
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                val msg = "Offline. Menampilkan data terakhir."
                if (_uiState.value !is DaftarUiState.Berhasil) {
                    _uiState.value = DaftarUiState.Gagal(e.kePesanIndonesia())
                }
                msg
            }
        }
    }

    fun muat() = segarkan()
}
