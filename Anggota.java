
public abstract class Anggota implements Comparable<Anggota> {
    // "implements Comparable<Anggota>" membuat daftar Anggota bisa diurutkan otomatis
    // memakai Collections.sort(), berdasarkan aturan compareTo() yang didefinisikan di bawah.

    protected String nama;        // nama lengkap anggota; protected -> bisa diakses langsung oleh subclass
    protected String id;          // kode unik anggota, contoh: "M001", "D001", "K001"
    protected JenisAnggota jenis; // jenis anggota, memakai tipe enum JenisAnggota, bukan String biasa

    public Anggota(String nama, String id, JenisAnggota jenis) {
        this.nama = nama;   // menyimpan parameter nama ke field nama milik object ini
        this.id = id;       // menyimpan parameter id ke field id milik object ini
        this.jenis = jenis; // menyimpan parameter jenis ke field jenis milik object ini
    }


    public String getNama() {
        return nama; // mengembalikan nilai field nama apa adanya
    }

    public String getId() {
        return id; // mengembalikan nilai field id apa adanya
    }

    public JenisAnggota getJenis() {
        return jenis; // mengembalikan nilai field jenis apa adanya
    }

    
    public abstract String getLabelTambahan(); // contoh isi nanti: "Jurusan", "Mata Kuliah", "Departemen"

    // Method abstract kedua: setiap jenis anggota menjelaskan sendiri "nilai" info tambahannya.
    public abstract String getNilaiTambahan(); // contoh isi nanti: "Teknik Informatika", dst.

   
    public String menjadiBarisFile() {
        return jenis.name() + "#" + id + "#" + nama + "#" + getNilaiTambahan();
        // jenis.name() mengubah enum (contoh JenisAnggota.MAHASISWA) menjadi teks murni "MAHASISWA"
    }

    // Override total terhadap method bawaan Object#toString(), supaya setiap kali
    // object Anggota di-print (System.out.println(anggota)), tampilannya rapi & informatif,
    // bukan menampilkan alamat memori seperti "Mahasiswa@1b6d3586" (perilaku default Java).
    @Override
    public String toString() {
        return String.format(
                "%-9s",              // %-9s  -> teks jenis, rata kiri, lebar minimal 9 karakter
                jenis.name()) +
               String.format(
                "%-6s",              // %-6s  -> id, rata kiri, lebar minimal 6 karakter
                id) +
               String.format(
                "%-22s",             // %-22s -> nama, rata kiri, lebar minimal 22 karakter
                nama) +
               String.format(
                "%-15s",             // %-15s -> label info tambahan, contoh "Jurusan" (dipanggil polymorphic)
                getLabelTambahan()) +
               String.format(
                "%s",                // %s    -> nilai info tambahan, contoh "Teknik Informatika"
                getNilaiTambahan());
    }

    // Implementasi wajib dari interface Comparable<Anggota>.
    // Method ini menentukan URUTAN antar 2 object Anggota ketika sebuah daftar di-sort.
    @Override
    public int compareTo(Anggota lain) {
        int hasilBandingJenis = this.jenis.compareTo(lain.jenis);
        // enum otomatis bisa dibandingkan berdasarkan urutan deklarasinya (ordinal).
        // Contoh: MAHASISWA(0) dibanding DOSEN(1) akan menghasilkan angka negatif.

        if (hasilBandingJenis != 0) {
            return hasilBandingJenis; // kalau jenisnya beda, urutkan berdasarkan jenis dulu
        }
        return this.nama.compareToIgnoreCase(lain.nama);
        // kalau jenisnya SAMA, baru urutkan berdasarkan nama secara alfabetis (tidak case-sensitive)
    }
}
