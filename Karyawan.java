public class Karyawan extends Anggota {

    private String departemen; // field tambahan khusus milik Karyawan, contoh isi: "Tata Usaha"

    public Karyawan(String nama, String id, String departemen) {
        super(nama, id, JenisAnggota.KARYAWAN);
        // memanggil constructor induk (Anggota) sambil mengunci jenis object ini sebagai KARYAWAN

        this.departemen = departemen; // mengisi field departemen khusus milik Karyawan
    }

    public String getDepartemen() {
        return departemen; // getter untuk mengambil isi field departemen dari luar class
    }

    @Override
    public String getLabelTambahan() {
        return "Departemen"; // label tampilan khusus untuk Karyawan
    }

    @Override
    public String getNilaiTambahan() {
        return departemen; // nilai mentah departemen, dipakai untuk tampilan maupun penyimpanan file
    }
}
