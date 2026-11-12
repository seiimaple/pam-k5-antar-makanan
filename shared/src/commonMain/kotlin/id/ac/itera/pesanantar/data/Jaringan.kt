package id.ac.itera.pesanantar.data

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

// Membuat satu HttpClient untuk seluruh aplikasi
fun buatClient() = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true // Abaikan data dari server yang tidak kita butuhkan
        })
    }
    install(HttpTimeout) {
        requestTimeoutMillis = 10_000 // Menyerah kalau server gak jawab dalam 10 detik
    }
    expectSuccess = true // Anggap error kalau server ngasih kode selain 200-an
}