public class Dosen extends Anggota {

    private String mataKuliah; // field tambahan khusus milik Dosen, contoh isi: "pemrograman 2"

    public Dosen(String nama, String id, String mataKuliah) {
        super(nama, id, JenisAnggota.DOSEN);
        // memanggil constructor induk (Anggota) sambil mengunci jenis object ini sebagai DOSEN

        this.mataKuliah = mataKuliah; // mengisi field mataKuliah khusus milik Dosen
    }

    public String getMataKuliah() {
        return mataKuliah; // getter untuk mengambil isi field mataKuliah dari luar class
    }

    @Override
    public String getLabelTambahan() {
        return "Mata Kuliah"; // label tampilan khusus untuk Dosen
    }

    @Override
    public String getNilaiTambahan() {
        return mataKuliah; // nilai mentah mata kuliah, dipakai untuk tampilan maupun penyimpanan file
    }
}