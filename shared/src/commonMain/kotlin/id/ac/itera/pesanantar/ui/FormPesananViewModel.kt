package id.ac.itera.pesanantar.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ac.itera.pesanantar.data.SumberPesananJaringan
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class FormPesananViewModel(
    private val sumber: SumberPesananJaringan = SumberPesananJaringan()
) : ViewModel() {
    private val _uiState = MutableStateFlow(FormPesananUiState())
    val uiState: StateFlow<FormPesananUiState> = _uiState.asStateFlow()

    fun ubahNamaPemesan(teks: String) =
        _uiState.update { it.copy(namaPemesan = teks.take(40), pesan = null) }

    fun ubahPesanan(teks: String) =
        _uiState.update { it.copy(pesanan = teks.take(60), pesan = null) }

    fun ubahLokasiAntar(teks: String) =
        _uiState.update { it.copy(lokasiAntar = teks.take(40), pesan = null) }

    fun ubahCatatanKhusus(teks: String) =
        _uiState.update { it.copy(catatanKhusus = teks.take(100), pesan = null) }

    fun simpan(onBerhasilSimpan: () -> Unit = {}) {
        val stateSaatIni = _uiState.value
        if (!stateSaatIni.bisaSimpan) return

        viewModelScope.launch {
            _uiState.update { it.copy(sedangMenyimpan = true) }

            try {
                sumber.kirimPesananBaru(
                    namaPemesan = stateSaatIni.namaPemesan.trim(),
                    pesanan = stateSaatIni.pesanan.trim(),
                    lokasiAntar = stateSaatIni.lokasiAntar.trim(),
                    catatanKhusus = stateSaatIni.catatanKhusus.trim().ifBlank { null }
                )

                _uiState.value = FormPesananUiState(pesan = "Pesanan baru berhasil ditambahkan")
                onBerhasilSimpan()

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        sedangMenyimpan = false,
                        pesan = "Gagal mengirim: ${e.message}"
                    )
                }
            }
        }
    }
}
