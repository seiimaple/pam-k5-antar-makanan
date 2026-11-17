package id.ac.itera.pesanantar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat
import id.ac.itera.pesanantar.db.DriverFactory
import id.ac.itera.pesanantar.platform.Pemberitahu
import id.ac.itera.pesanantar.platform.PemberitahuAndroid

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // 1. Mendaftarkan Kanal Notifikasi (Wajib untuk Android)
        val kanal = NotificationChannelCompat.Builder(
            "status", NotificationManagerCompat.IMPORTANCE_DEFAULT
        ).setName("Status Pesanan").build()

        NotificationManagerCompat.from(applicationContext).createNotificationChannel(kanal)

        setContent {
            // 2. Menyuntikkan DriverFactory (dari P7) DAN PemberitahuAndroid (dari P8)
            App(
                driverFactory = DriverFactory(applicationContext),
                pemberitahu = PemberitahuAndroid(applicationContext)
            )
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    // Karena App() sekarang butuh parameter, kita berikan implementasi "kosongan" khusus untuk tampilan preview
    App(
        driverFactory = null, // Ganti sesuai kebutuhan preview atau biarkan null
        pemberitahu = object : Pemberitahu {
            override fun tampilkan(judul: String, isi: String) {
                // Kosongkan saja untuk preview
            }
        }
    )
}