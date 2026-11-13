package id.ac.itera.pesanantar.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class PesananApi(
    private val client: HttpClient = buatClient(),
    private val baseUrl: String = "https://pesanantar.itera.ac.id/api"
) {
    suspend fun ambilSemuaPesanan(): List<PesananDto> {
        return client.get("$baseUrl/pesanan").body()
    }

    suspend fun ambilDetailPesanan(id: Int): PesananDto {
        return client.get("$baseUrl/pesanan/$id").body()
    }

    suspend fun kirimPesanan(pesananBaru: PesananBaruDto): PesananDto {
        return client.post("$baseUrl/pesanan") {
            contentType(ContentType.Application.Json)
            setBody(pesananBaru)
        }.body()
    }
}

