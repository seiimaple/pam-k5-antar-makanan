package id.ac.itera.pesanantar.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ac.itera.pesanantar.data.DaftarPesananDummy
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FormPesananViewModel : ViewModel() {

    // Private MutableStateFlow: hanya ViewModel yang boleh mengubah state
    private val _uiState = MutableStateFlow(FormPesananUiState())

    // Public StateFlow: layar (View) hanya bisa membaca state ini
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
            delay(800) // Simulasi proses menyimpan data

            // Memanggil fungsi tambahPesanan di DaftarPesananDummy
            DaftarPesananDummy.tambahPesanan(
                namaPemesan = stateSaatIni.namaPemesan.trim(),
                pesanan = stateSaatIni.pesanan.trim(),
                lokasiAntar = stateSaatIni.lokasiAntar.trim(),
                catatanKhusus = stateSaatIni.catatanKhusus.trim().ifBlank { null }
            )

            // Reset isi form ke kosong dan tampilkan pesan konfirmasi
            _uiState.value = FormPesananUiState(pesan = "Pesanan baru berhasil ditambahkan")
            onBerhasilSimpan()
        }
    }
}