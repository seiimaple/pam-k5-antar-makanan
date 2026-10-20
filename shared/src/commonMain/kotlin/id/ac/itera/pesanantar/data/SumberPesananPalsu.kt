package id.ac.itera.pesanantar.data

import id.ac.itera.pesanantar.model.StatusPesanan
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SumberPesananPalsu : Sumber<StatusPesanan> {
    override fun pantauStatus(id: Int): Flow<StatusPesanan> = flow {
        emit(StatusPesanan.MenungguKonfirmasi)
        delay(1500)

        emit(StatusPesanan.Diproses("Chef Juna"))
        delay(2000)

        emit(StatusPesanan.Diantar("Pak Budi"))
        delay(2000)

        emit(StatusPesanan.Selesai)
    }
}