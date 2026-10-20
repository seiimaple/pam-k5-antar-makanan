package id.ac.itera.pesanantar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource

import pesanantarmakanan.shared.generated.resources.Res
import pesanantarmakanan.shared.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(16.dp))

            // Judul aplikasi diganti nama tema
            Text("Tema 5 - Pesan Antar Makanan Kantin")

            Spacer(Modifier.height(16.dp))

            Button(onClick = { showContent = !showContent }) {
                Text("Tentang Kelompok")
            }

            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)

                    // Nama platform ikut tampil
                    Text("Berjalan di: $greeting")

                    Spacer(Modifier.height(16.dp))

                    Text("=== Daftar Anggota ===")
                    Text("Rifka Priseilla Br Silitonga (123140024)")
                    Text("Marvin Karyanda (123140185)")
                    Text("Fadzilah-Saputri (123140149)")
                    Text("Daniel Calvin Simanjuntak (123140004)")
                }
            }
        }
    }
}
