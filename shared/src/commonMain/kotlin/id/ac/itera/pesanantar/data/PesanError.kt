package id.ac.itera.pesanantar.data

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.utils.io.errors.IOException

fun Throwable.kePesanIndonesia(): String {
    return when (this) {
        is HttpRequestTimeoutException -> "Koneksi batas waktu habis (timeout). Silakan periksa jaringan Anda."
        is ClientRequestException -> {
            when (response.status.value) {
                404 -> "Data pesanan tidak ditemukan."
                400 -> "Permintaan tidak valid."
                else -> "Terjadi kesalahan pada permintaan (${response.status.value})."
            }
        }
        is ServerResponseException -> "Terjadi gangguan pada server. Silakan coba beberapa saat lagi."
        is IOException -> "Gagal terhubung ke internet. Periksa koneksi data Anda."
        else -> message ?: "Terjadi kesalahan yang tidak terduga."
    }
}

