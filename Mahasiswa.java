
public class Mahasiswa extends Anggota {
    // "extends Anggota" berarti Mahasiswa otomatis punya nama, id, jenis, dan seluruh

    private String jurusan; // field tambahan khusus milik Mahasiswa, contoh isi: "Teknik Informatika"

    // Constructor Mahasiswa menerima 3 data dari luar: nama, id, dan jurusan.
    public Mahasiswa(String nama, String id, String jurusan) {
        super(nama, id, JenisAnggota.MAHASISWA);
        // super(...) WAJIB menjadi baris pertama di constructor subclass.
        // Baris ini memanggil constructor Anggota untuk mengisi nama & id,
        // sekaligus otomatis mengunci jenis object ini sebagai JenisAnggota.MAHASISWA.

        this.jurusan = jurusan; // mengisi field jurusan yang hanya ada di class Mahasiswa ini
    }

    public String getJurusan() {
        return jurusan; // getter untuk mengambil isi field jurusan dari luar class
    }

    // Override method abstract dari Anggota: memberi tahu label info tambahan milik Mahasiswa.
    @Override
    public String getLabelTambahan() {
        return "Jurusan"; // label yang akan tampil saat object ini di-print, contoh: "Jurusan: ..."
    }

    // Override method abstract dari Anggota: memberi tahu nilai info tambahan milik Mahasiswa.
    @Override
    public String getNilaiTambahan() {
        return jurusan; // nilai mentah jurusan, dipakai baik untuk ditampilkan maupun disimpan ke file
    }
}
