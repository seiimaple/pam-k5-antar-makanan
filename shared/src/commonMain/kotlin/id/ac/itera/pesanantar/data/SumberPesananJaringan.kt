package id.ac.itera.pesanantar.data

import id.ac.itera.pesanantar.model.Pesanan
import id.ac.itera.pesanantar.model.StatusPesanan
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SumberPesananJaringan(
    private val api: PesananApi = PesananApi()
) : Sumber<StatusPesanan> {

    // Helper untuk mengubah representasi status dari string DTO ke StatusPesanan
    fun parseStatus(statusStr: String): StatusPesanan {
        val lower = statusStr.lowercase()
        return when {
            lower.contains("menunggu") -> StatusPesanan.MenungguKonfirmasi
            lower.contains("proses") || lower.contains("masak") -> {
                val nama = statusStr.substringAfter("oleh koki ", "").ifBlank { "Chef Juna" }
                StatusPesanan.Diproses(nama)
            }
            lower.contains("antar") -> {
                val nama = statusStr.substringAfter("oleh driver ", "").ifBlank { "Pak Budi" }
                StatusPesanan.Diantar(nama)
            }
            lower.contains("selesai") -> StatusPesanan.Selesai
            else -> StatusPesanan.MenungguKonfirmasi
        }
    }

    fun toModel(dto: PesananDto): Pesanan {
        return Pesanan(
            id = dto.id,
            namaPemesan = dto.nama,
            pesanan = dto.pesanan,
            lokasiAntar = dto.lokasi,
            catatanKhusus = dto.catatan,
            status = parseStatus(dto.status)
        )
    }

    suspend fun getDaftarPesanan(): List<Pesanan> {
        return api.ambilSemuaPesanan().map { toModel(it) }
    }

    suspend fun getDetailPesanan(id: Int): Pesanan {
        return toModel(api.ambilDetailPesanan(id))
    }

    suspend fun kirimPesananBaru(
        namaPemesan: String,
        pesanan: String,
        lokasiAntar: String,
        catatanKhusus: String? = null
    ): Pesanan {
        val dto = PesananBaruDto(
            nama = namaPemesan,
            pesanan = pesanan,
            lokasi = lokasiAntar,
            catatan = catatanKhusus
        )
        return toModel(api.kirimPesanan(dto))
    }

    override fun pantauStatus(id: Int): Flow<StatusPesanan> = flow {
        while (true) {
            val pesanan = getDetailPesanan(id)
            emit(pesanan.status)
            if (pesanan.status is StatusPesanan.Selesai) {
                break
            }
            delay(5000)
        }
    }
}

