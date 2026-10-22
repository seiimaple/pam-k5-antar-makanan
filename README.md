# Tema 5 - Pesan Antar Makanan Kantin

Repository ini berisi proyek aplikasi Kotlin Multiplatform (KMP) untuk mata kuliah Pengembangan Aplikasi Mobile. Proyek ini dibangun dari *template* Compose Multiplatform.

## Anggota Kelompok

* **Ketua:** Rifka Priseilla Br Silitonga (123140024)
* **Anggota:**
  * Daniel Calvin Simanjuntak (123140004)
  * Fadzilah Saputri (123140149)
  * Marvin Karyanda (123140185)

## Tangkapan Layar Aplikasi

### Modul 03 - Daftar Pesanan dan Tombol Tambah

![Daftar pesanan kantin dengan FAB tambah di kanan bawah](docs/daftar-pesanan-fab.jpg)

Layar menampilkan 11 data contoh menggunakan `LazyColumn` dan `KartuPesanan`.
Data diurutkan berdasarkan ID terbesar melalui `getPesananTerbaru()`
(`sortedByDescending`), sehingga pesanan terbaru tampil di atas. Setiap kartu
memuat nama pemesan, rincian pesanan, lokasi antar, catatan jika ada, dan label
status dari `StatusPesanan`.

`Scaffold` menempatkan `FloatingActionButton` berikon tambah di kanan bawah.
Ikon memiliki deskripsi aksesibilitas "Tambah pesanan". Tombol tetap terlihat
saat daftar digulir, dan daftar memiliki ruang bawah agar kartu terakhir dapat
digulir melewati tombol. Sesuai Modul 03, aksi tambah belum diimplementasikan;
menekan FAB belum membuka formulir atau menambah data.

### Pembagian Kerja Modul 03

| Anggota | Kontribusi |
| --- | --- |
| Rifka | Komponen `KartuPesanan` menggunakan `Card`, `Row`, dan `Column`, serta `LabelStatus`. |
| Daniel | 11 data dummy `Pesanan`, pengurutan `sortedByDescending`, dan penyaringan `filter`. |
| Fadzilah | `LayarDaftarPesanan` menggunakan `LazyColumn` dan memanggil `KartuPesanan`. |
| Marvin | Integrasi layar utama, FAB berikon tambah, screenshot aplikasi, dan dokumentasi. |

### Menjalankan Tampilan Desktop dari PowerShell

Jalankan dari folder utama repository. Contoh ini memakai JDK bawaan Android
Studio dan Android SDK pada lokasi instalasi standar Windows; sesuaikan jika
lokasi instalasi berbeda. Android Studio dan emulator tidak perlu dibuka.

```powershell
$env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"
$env:ANDROID_HOME = "$env:LOCALAPPDATA\Android\Sdk"
.\gradlew.bat :desktopApp:run --console=plain
```

Perintah membuka jendela aplikasi Desktop. Screenshot di atas diambil dari
jendela aplikasi, bukan terminal. Simulasi konsol dari Modul 02 masih dapat
dijalankan dengan perintah pada bagian "Pengujian Simulasi Status Pesanan".

### Verifikasi Modul 03

- Build Desktop (`:desktopApp:classes`) dan dua tes JVM bawaan
  (`:shared:jvmTest`) berhasil.
- Tampilan Desktop diperiksa langsung: pesanan terbaru muncul di atas,
  daftar dapat digulir hingga pesanan pertama, dan FAB tetap terlihat.
- FAB dapat diklik tanpa error; aksi tambah masih berupa callback kosong
  sesuai batas tugas Modul 03.

### Tampilan Kelompok Sebelumnya

![img.png](img.png)
![img_1.png](img_1.png)

This is a Kotlin Multiplatform project targeting Android, iOS, Desktop (JVM).

* [/iosApp](./iosApp/iosApp) contains an iOS application. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

* [/shared](./shared/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./shared/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./shared/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./shared/src/jvmMain/kotlin)
    folder is the appropriate location.

### Running the apps

Use the run configurations provided by the run widget in your IDE's toolbar. You can also use these commands and options:

- Android app: `./gradlew :androidApp:assembleDebug`
- Desktop app:
  - Hot reload: `./gradlew :desktopApp:hotRun --auto`
  - Standard run: `./gradlew :desktopApp:run`
- iOS app: open the [/iosApp](./iosApp) directory in Xcode and run it from there.

### Running tests

Use the run button in your IDE's editor gutter, or run tests using Gradle tasks:

- Android tests: `./gradlew :shared:testAndroidHostTest`
- Desktop tests: `./gradlew :shared:jvmTest`
- iOS tests: `./gradlew :shared:iosSimulatorArm64Test`

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…

## Pengujian Simulasi Status Pesanan

Program menggunakan `SumberPesananPalsu` untuk menghasilkan perubahan
status melalui `Flow`. Setiap status diterima dengan `collect`, kemudian
ditampilkan di konsol menggunakan fungsi `label()`.

### Cara Menjalankan

Buka PowerShell di folder utama project, lalu jalankan:

```powershell
$env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"
$env:ANDROID_HOME = "$env:LOCALAPPDATA\Android\Sdk"
.\gradlew.bat :desktopApp:runSimulasi --console=plain
```

### Hasil Pengujian

Status muncul berurutan sesuai jeda simulasi:

```text
Simulasi status pesanan #1
Menunggu Konfirmasi
Pesanan sedang diproses oleh koki Chef Juna
Pesanan sedang diantar oleh driver Pak Budi
Pesanan selesai
Simulasi selesai.
```

Program selesai tanpa error dan Gradle menampilkan `BUILD SUCCESSFUL`.

### Screenshot Hasil Konsol

![Hasil simulasi status pesanan](docs/hasil-simulasi.png)
