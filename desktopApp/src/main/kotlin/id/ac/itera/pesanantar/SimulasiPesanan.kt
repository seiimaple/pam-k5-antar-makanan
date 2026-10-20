package id.ac.itera.pesanantar

import id.ac.itera.pesanantar.data.SumberPesananPalsu
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking

object SimulasiPesanan {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        val idPesanan = 1
        val sumber = SumberPesananPalsu()

        println("Simulasi status pesanan #$idPesanan")

        sumber.pantauStatus(idPesanan).collect { status ->
            println(status.label())
        }

        println("Simulasi selesai.")
    }
}