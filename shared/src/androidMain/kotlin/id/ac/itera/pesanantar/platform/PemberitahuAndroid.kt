package id.ac.itera.pesanantar.platform

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class PemberitahuAndroid(
    private val context: Context
) : Pemberitahu {
    override fun tampilkan(judul: String, isi: String) {
        // Memanggil Notification Manager bawaan Android
        val nm = NotificationManagerCompat.from(context)

        // Cek dulu apakah user sudah memberi izin notifikasi
        if (!nm.areNotificationsEnabled()) return

        // Membangun bentuk notifikasinya
        val notif = NotificationCompat.Builder(context, "status")
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Memakai ikon info bawaan Android
            .setContentTitle(judul)
            .setContentText(isi)
            .build()

        // Menampilkan notifikasi ke layar
        nm.notify(judul.hashCode(), notif)
    }
}