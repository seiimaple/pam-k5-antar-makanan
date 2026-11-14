package id.ac.itera.pesanantar.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ac.itera.pesanantar.data.SumberPesananOffline
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

@OptIn(FlowPreview::class)
class FormPesananViewModel(
    private val sumber: SumberPesananOffline
) : ViewModel() {

    private val q = sumber.db.drafQueries

    private val _uiState = MutableStateFlow(FormPesananUiState())
    val uiState: StateFlow<FormPesananUiState> = _uiState.asStateFlow()

    init {
        q.ambilDraf().executeAsOneOrNull()?.let { d ->
            _uiState.update {
                it.copy(
                    namaPemesan = d.nama,
                    pesanan = d.pesanan,
                    lokasiAntar = d.lokasi,
                    catatanKhusus = d.catatan ?: ""
                )
            }
        }

        viewModelScope.launch {
            _uiState.debounce(500).collect { state ->
                q.simpanDraf(
                    nama = state.namaPemesan,
                    pesanan = state.pesanan,
                    lokasi = state.lokasiAntar,
                    catatan = state.catatanKhusus.ifBlank { null }
                )
            }
        }
    }

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

                q.hapusDraf()

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