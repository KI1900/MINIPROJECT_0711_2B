import java.util.ArrayList;    // struktur data list dinamis, dipakai menampung anggota baru sebelum disimpan
import java.util.Collections;  // menyediakan method sort() untuk mengurutkan isi sebuah List
import java.util.List;         // interface List, tipe data referensi yang fleksibel

/*
 * File: Main.java
 * Peran dalam project: pintu masuk (entry point) program. Di sinilah alur "Uji: simpan 3 anggota,
 * baca ulang, tampilkan hasilnya" yang diminta soal benar-benar dijalankan dan dibuktikan.
 */
public class Main {

    public static void main(String[] args) {
        // method main() adalah method PERTAMA yang dijalankan JVM saat program di-compile & di-run

        cetakBanner(); // menampilkan judul program di console sebelum proses lain berjalan

        AnggotaService layanan = new AnggotaService("data_anggota.txt");
        // membuat satu object AnggotaService yang akan mengurus semua baca-tulis ke file "data_anggota.txt"

        boolean sudahPernahAda = layanan.fileSudahAda();
        // mengecek LEBIH DULU (sebelum menulis apa pun) apakah file ini sudah pernah dibuat di run sebelumnya

        // ================================================================
        // TAHAP 1: SIMPAN ANGGOTA (memenuhi syarat "simpan 3 anggota")
        // Data awal HANYA dibuat kalau file belum pernah ada, supaya kita bisa membuktikan
        // bahwa menjalankan program berkali-kali TIDAK menghapus data lama secara sia-sia,
        // dan data memang benar-benar "diingat" lewat file, bukan cuma variabel sementara di memori
        // (inilah bukti nyata dari syarat "dibaca kembali saat program dibuka ulang").
        // ================================================================
        if (!sudahPernahAda) {
            System.out.println(">> File belum ditemukan. Membuat & menyimpan 3 data anggota baru...\n");

            List<Anggota> anggotaBaru = new ArrayList<>();
            // list sementara untuk menampung 3 object anggota sebelum dikirim ke simpanAnggota()

            anggotaBaru.add(new Mahasiswa("Andi Saputra", "M001", "Teknik Informatika"));
            // menambahkan 1 object Mahasiswa ke dalam list anggotaBaru

            anggotaBaru.add(new Dosen("Budi Santoso, M.Kom.", "D001", "Struktur Data"));
            // menambahkan 1 object Dosen ke dalam list anggotaBaru

            anggotaBaru.add(new Karyawan("Citra Dewi", "K001", "Tata Usaha"));
            // menambahkan 1 object Karyawan ke dalam list anggotaBaru, total kini genap 3 anggota

            layanan.simpanAnggota(anggotaBaru);
            // memanggil method WAJIB simpanAnggota() untuk menulis ketiga object di atas ke file txt

        } else {
            System.out.println(">> File sudah pernah ada sebelumnya. Data lama akan langsung dibaca ulang.\n");
        }

        // ================================================================
        // TAHAP 2: BACA ULANG (memenuhi syarat "baca ulang")
        // Sengaja membaca LANGSUNG dari file (bukan dari variabel anggotaBaru di atas),
        // untuk membuktikan bahwa bacaAnggota() benar-benar mengambil data dari disk.
        // ================================================================
        System.out.println(">> Membaca ulang seluruh data dari file '" + layanan.getNamaFile() + "'...\n");

        List<Anggota> hasilBaca = layanan.bacaAnggota();
        // memanggil method WAJIB bacaAnggota(), hasilnya berupa List<Anggota> yang sudah dibedakan jenisnya

        Collections.sort(hasilBaca);
        // mengurutkan hasil bacaan memakai aturan compareTo() yang sudah didefinisikan di class Anggota
        // (urut berdasarkan jenis dulu, baru berdasarkan nama alfabetis kalau jenisnya sama)

        // ================================================================
        // TAHAP 3: TAMPILKAN HASIL (memenuhi syarat "tampilkan hasilnya")
        // ================================================================
        System.out.println("===================== DAFTAR ANGGOTA =====================");

        int nomor = 1; // penghitung urutan tampilan, sengaja dimulai dari 1 supaya enak dibaca manusia
        for (Anggota anggota : hasilBaca) {
            System.out.println(nomor + ". " + anggota);
            // memanfaatkan method toString() yang sudah di-override di class Anggota,
            // sehingga cukup menulis "anggota" saja, Java otomatis memanggil toString()-nya

            nomor++; // menambah penghitung setiap 1 baris anggota selesai dicetak
        }
        System.out.println("============================================================\n");

        cetakStatistik(hasilBaca); // menampilkan ringkasan jumlah per jenis anggota

        // ================================================================
        // BONUS: contoh pemanfaatan lebih lanjut dari hasil bacaAnggota(), yaitu fitur pencarian.
        // ================================================================
        System.out.println("\n>> Contoh fitur tambahan: mencari anggota dengan id \"D001\"...");
        Anggota hasilCari = layanan.cariBerdasarkanId(hasilBaca, "D001");
        // mencoba mencari anggota berjenis Dosen yang tadi disimpan, berdasarkan id-nya

        if (hasilCari != null) {
            System.out.println("Ditemukan -> " + hasilCari);
        } else {
            System.out.println("Anggota dengan id tersebut tidak ditemukan.");
        }
    }

    // Method kecil terpisah khusus untuk menampilkan judul program, supaya method main() tidak terlalu panjang.
    private static void cetakBanner() {
        System.out.println("============================================================");
        System.out.println("   SISTEM PENYIMPANAN DATA ANGGOTA BERBASIS FILE TXT (JAVA) ");
        System.out.println("============================================================\n");
    }

    // Method kecil terpisah untuk menghitung & menampilkan jumlah anggota per jenis.
    private static void cetakStatistik(List<Anggota> daftar) {
        int totalMahasiswa = 0; // penghitung untuk jenis MAHASISWA, dimulai dari 0
        int totalDosen = 0;     // penghitung untuk jenis DOSEN, dimulai dari 0
        int totalKaryawan = 0;  // penghitung untuk jenis KARYAWAN, dimulai dari 0

        for (Anggota anggota : daftar) {
            // memeriksa jenis tiap anggota satu per satu, lalu menambah penghitung yang sesuai

            switch (anggota.getJenis()) {
                case MAHASISWA:
                    totalMahasiswa++; // tambah 1 karena jenisnya MAHASISWA
                    break;
                case DOSEN:
                    totalDosen++; // tambah 1 karena jenisnya DOSEN
                    break;
                case KARYAWAN:
                    totalKaryawan++; // tambah 1 karena jenisnya KARYAWAN
                    break;
            }
        }

        System.out.println("Ringkasan Data Anggota:");
        System.out.println("- Mahasiswa : " + totalMahasiswa + " orang");
        System.out.println("- Dosen     : " + totalDosen + " orang");
        System.out.println("- Karyawan  : " + totalKaryawan + " orang");
        System.out.println("- Total     : " + daftar.size() + " anggota");
    }
}
