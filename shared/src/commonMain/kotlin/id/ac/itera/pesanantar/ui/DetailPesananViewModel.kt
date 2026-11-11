package id.ac.itera.pesanantar.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ac.itera.pesanantar.data.DaftarPesananDummy
import id.ac.itera.pesanantar.model.Pesanan
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DetailPesananViewModel(
    val idPesanan: Int
) : ViewModel() {

    val pesanan: StateFlow<Pesanan?> = DaftarPesananDummy.listOfPesanan
        .map { daftar -> daftar.find { it.id == idPesanan } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DaftarPesananDummy.listOfPesanan.value.find { it.id == idPesanan }
        )
}
