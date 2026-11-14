package id.ac.itera.pesanantar.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import id.ac.itera.pesanantar.db.PesanAntarDb
import id.ac.itera.pesanantar.db.PesananEntity
import id.ac.itera.pesanantar.model.Pesanan
import id.ac.itera.pesanantar.model.StatusPesanan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SumberPesananOffline(
    val db: PesanAntarDb,
    private val api: PesananApi = PesananApi()
) : Sumber<StatusPesanan> {

    val daftarPesanan: Flow<List<Pesanan>> = db.pesananQueries
        .semua()
        .asFlow()
        .mapToList(Dispatchers.Default)
        .map { entities -> entities.map { it.toModel() } }

    suspend fun segarkan() {
        val dariJaringan = api.ambilSemuaPesanan()
        db.transaction {
            db.pesananQueries.hapusSemua()
            dariJaringan.forEach { dto ->
                db.pesananQueries.simpan(
                    id = dto.id.toLong(),
                    nama = dto.nama,
                    pesanan = dto.pesanan,
                    lokasi = dto.lokasi,
                    status = dto.status,
                    catatan = dto.catatan
                )
            }
        }
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
        val hasil = api.kirimPesanan(dto)
        db.pesananQueries.simpan(
            id = hasil.id.toLong(),
            nama = hasil.nama,
            pesanan = hasil.pesanan,
            lokasi = hasil.lokasi,
            status = hasil.status,
            catatan = hasil.catatan
        )
        return hasil.toModel()
    }

    override fun pantauStatus(id: Int): Flow<StatusPesanan> {
        return db.pesananQueries
            .byId(id.toLong())
            .asFlow()
            .mapToOneOrNull(Dispatchers.Default)
            .map { entity ->
                entity?.let { parseStatus(it.status) } ?: StatusPesanan.MenungguKonfirmasi
            }
    }

    private fun PesananEntity.toModel(): Pesanan = Pesanan(
        id = id.toInt(),
        namaPemesan = nama,
        pesanan = pesanan,
        lokasiAntar = lokasi,
        catatanKhusus = catatan,
        status = parseStatus(status)
    )

    private fun PesananDto.toModel(): Pesanan = Pesanan(
        id = id,
        namaPemesan = nama,
        pesanan = pesanan,
        lokasiAntar = lokasi,
        catatanKhusus = catatan,
        status = parseStatus(status)
    )

    private fun parseStatus(statusStr: String): StatusPesanan {
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
}

