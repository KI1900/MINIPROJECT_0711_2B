import java.io.BufferedReader; // kelas untuk membaca teks dari file secara efisien, baris per baris
import java.io.BufferedWriter; // kelas untuk menulis teks ke file secara efisien, baris per baris
import java.io.File;           // kelas untuk merepresentasikan lokasi file/folder di penyimpanan
import java.io.FileReader;     // kelas untuk membuka aliran baca mentah dari sebuah file
import java.io.FileWriter;     // kelas untuk membuka aliran tulis mentah ke sebuah file
import java.io.IOException;    // jenis exception yang muncul kalau operasi file gagal (misal izin ditolak)
import java.util.ArrayList;    // implementasi List yang dipakai menampung hasil pembacaan file
import java.util.List;         // interface List, dipakai sebagai tipe data supaya kode lebih fleksibel


public class AnggotaService {

    private final String namaFile;
    // "final" artinya nilai namaFile tidak boleh berubah lagi setelah diisi sekali di constructor.
    // Ini mencegah kesalahan tidak sengaja menimpa nama file di tengah jalannya program.

    public AnggotaService(String namaFile) {
        this.namaFile = namaFile; // menyimpan nama file yang diberikan (contoh: "data_anggota.txt")
    }

    // Getter kecil untuk mengambil nama file yang sedang dipakai oleh service ini,
    // supaya class Main tidak perlu menuliskan ulang String nama file secara manual.
    public String getNamaFile() {
        return namaFile;
    }

    public void simpanAnggota(List<Anggota> daftarAnggota) {

        // "try-with-resources": variabel "penulis" otomatis di-close() begitu blok try selesai,
        // baik itu selesai normal maupun karena error. Ini mencegah file "tersangkut"/terkunci.
        try (BufferedWriter penulis = new BufferedWriter(new FileWriter(namaFile))) {

            for (Anggota anggota : daftarAnggota) {
                // perulangan ini mengambil object Anggota satu per satu dari daftarAnggota

                penulis.write(anggota.menjadiBarisFile());
                // menulis representasi 1 baris teks dari object (format: JENIS#ID#NAMA#NILAI)

                penulis.newLine();
                // pindah ke baris baru di file, supaya anggota berikutnya tidak nyambung di baris yang sama
            }

            System.out.println("[INFO] " + daftarAnggota.size() + " data anggota berhasil ditulis ke file '" + namaFile + "'.");
            // konfirmasi ke user berapa banyak data yang barusan berhasil disimpan

        } catch (IOException kesalahan) {
            // blok ini otomatis "menangkap" error kalau proses penulisan gagal (misal disk penuh, dsb)
            System.out.println("[ERROR] Gagal menyimpan data anggota: " + kesalahan.getMessage());
        }
    }

  
    public List<Anggota> bacaAnggota() {

        List<Anggota> hasilBaca = new ArrayList<>();
        // list kosong yang akan diisi object-object hasil pembacaan file, lalu dikembalikan di akhir method

        File file = new File(namaFile); // representasi lokasi file yang mau dibaca

        if (!file.exists()) {
            // jika file belum pernah dibuat sama sekali, tidak ada yang bisa dibaca
            System.out.println("[INFO] File '" + namaFile + "' tidak ditemukan, belum ada data tersimpan.");
            return hasilBaca; // kembalikan list kosong, hentikan method di sini
        }

        try (BufferedReader pembaca = new BufferedReader(new FileReader(file))) {
            // try-with-resources lagi, supaya "pembaca" otomatis ditutup setelah selesai dipakai

            String baris; // variabel sementara menampung 1 baris teks yang baru dibaca dari file

            while ((baris = pembaca.readLine()) != null) {
                // pembaca.readLine() membaca 1 baris & menggeser "kursor baca" ke baris berikutnya.
                // Method ini mengembalikan null kalau sudah mencapai akhir file (End Of File),
                // sehingga perulangan while otomatis berhenti saat itu terjadi.

                if (baris.trim().isEmpty()) {
                    continue; // lewati baris kosong (misal baris terakhir yang cuma newline)
                }

                String[] bagian = baris.split("#");
                // memecah 1 baris teks menjadi array String, dipisah setiap kali ketemu karakter '#'.
                // Contoh: "MAHASISWA#M001#Andi Saputra#Teknik Informatika"
                // menjadi -> ["MAHASISWA", "M001", "Andi Saputra", "Teknik Informatika"]

                if (bagian.length < 4) {
                    // kalau hasil split kurang dari 4 bagian, berarti format barisnya rusak/tidak lengkap
                    System.out.println("[WARNING] Baris berformat tidak valid, dilewati -> " + baris);
                    continue; // lanjut ke baris berikutnya tanpa menghentikan seluruh program
                }

                String jenisTeks = bagian[0];     // bagian ke-1: kode jenis anggota, misal "MAHASISWA"
                String id = bagian[1];            // bagian ke-2: id anggota
                String nama = bagian[2];          // bagian ke-3: nama anggota
                String nilaiTambahan = bagian[3]; // bagian ke-4: nilai tambahan (jurusan/mata kuliah/departemen)

                try {
                    JenisAnggota jenis = JenisAnggota.valueOf(jenisTeks);
                    // valueOf() mengubah teks murni (contoh "MAHASISWA") kembali menjadi konstanta enum
                    // JenisAnggota.MAHASISWA. Jika teksnya tidak cocok konstanta manapun, method ini
                    // melempar IllegalArgumentException, yang ditangkap oleh blok catch di bawah.

                    // switch di bawah ini adalah jantung dari logika "bedakan Mahasiswa/Dosen/Karyawan"
                    // yang diminta soal: setiap jenis dibentuk ulang menjadi object class yang sesuai.
                    switch (jenis) {
                        case MAHASISWA:
                            hasilBaca.add(new Mahasiswa(nama, id, nilaiTambahan));
                            // membuat object Mahasiswa baru dari data yang barusan dibaca, lalu
                            // memasukkannya ke hasilBaca
                            break;

                        case DOSEN:
                            hasilBaca.add(new Dosen(nama, id, nilaiTambahan));
                            // membuat object Dosen baru, prinsipnya sama seperti Mahasiswa di atas
                            break;

                        case KARYAWAN:
                            hasilBaca.add(new Karyawan(nama, id, nilaiTambahan));
                            // membuat object Karyawan baru, prinsipnya sama seperti dua jenis di atas
                            break;
                    }

                } catch (IllegalArgumentException kesalahanJenis) {
                    // ditangkap kalau jenisTeks di file tidak sesuai satu pun konstanta di enum JenisAnggota
                    System.out.println("[WARNING] Jenis '" + jenisTeks + "' tidak dikenali, baris dilewati.");
                }
            }

        } catch (IOException kesalahan) {
            // ditangkap kalau proses pembacaan file gagal di tengah jalan (misal file terhapus mendadak)
            System.out.println("[ERROR] Gagal membaca data anggota: " + kesalahan.getMessage());
        }

        return hasilBaca; // mengembalikan seluruh object Anggota yang berhasil dibentuk ulang dari isi file
    }

    public boolean fileSudahAda() {
        return new File(namaFile).exists();
    }

    public Anggota cariBerdasarkanId(List<Anggota> daftar, String idDicari) {
        for (Anggota anggota : daftar) {
            // memeriksa setiap object Anggota di dalam daftar satu per satu secara berurutan

            if (anggota.getId().equalsIgnoreCase(idDicari)) {
                // equalsIgnoreCase supaya pencarian tidak peduli huruf besar/kecil (contoh "m001" == "M001")
                return anggota; // langsung berhenti & kembalikan begitu ketemu yang cocok
            }
        }
        return null; // dikembalikan hanya jika sudah memeriksa semua anggota dan tidak ada yang cocok
    }
}
