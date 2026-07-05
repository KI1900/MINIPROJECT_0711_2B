# Sistem Penyimpanan Data Anggota (Write/Read File TXT) — Java

Mini project Java untuk menyimpan (Write) dan membaca (Read) data Anggota
(Mahasiswa / Dosen / Karyawan) dari sebuah file `data_anggota.txt`, dengan data
yang tetap ada walau program ditutup dan dibuka ulang.

## Struktur File

| File | Peran |
|---|---|
| `JenisAnggota.java` | Enum daftar jenis anggota yang valid: `MAHASISWA`, `DOSEN`, `KARYAWAN` |
| `Anggota.java` | Class abstract (induk) berisi `nama`, `id`, `jenis`, dan method umum |
| `Mahasiswa.java` | Turunan `Anggota`, punya field tambahan `jurusan` |
| `Dosen.java` | Turunan `Anggota`, punya field tambahan `mataKuliah` |
| `Karyawan.java` | Turunan `Anggota`, punya field tambahan `departemen` |
| `AnggotaService.java` | Berisi method wajib **`simpanAnggota()`** dan **`bacaAnggota()`** |
| `Main.java` | Entry point + pengujian: simpan 3 anggota → baca ulang → tampilkan |

Setiap baris kode sudah diberi komentar penjelasan (apa yang dikerjakan baris
itu dan kenapa) langsung di dalam file `.java`-nya masing-masing.

## Cara Menjalankan

Pastikan JDK sudah terpasang (`java -version`), lalu dari dalam folder ini:

```bash
javac *.java
java Main
```

Jalankan `java Main` sekali lagi untuk membuktikan data **tidak dibuat ulang**,
melainkan langsung dibaca dari file `data_anggota.txt` yang sudah tersimpan.

## Format Penyimpanan

Satu baris file = satu anggota, dipisah tanda pagar (`#`):

```
JENIS#ID#NAMA#NILAI_TAMBAHAN
MAHASISWA#M001#Andi Saputra#Teknik Informatika
DOSEN#D001#Budi Santoso, M.Kom.#Struktur Data
KARYAWAN#K001#Citra Dewi#Tata Usaha
```

Lihat `contoh_isi_data_anggota.txt` untuk contoh isi file setelah program
dijalankan (bukan file yang dipakai program, murni referensi).

## Fitur Tambahan (Bonus di Luar Minimum Requirement)

- **Enum** `JenisAnggota` — jenis anggota anti typo, tervalidasi compiler.
- **`Comparable<Anggota>`** — hasil `bacaAnggota()` bisa diurutkan otomatis
  (jenis dulu, lalu nama alfabetis) via `Collections.sort()`.
- **`fileSudahAda()`** — mengecek apakah file sudah pernah dibuat, supaya data
  lama tidak tertimpa tiap kali program dijalankan ulang.
- **`cariBerdasarkanId()`** — contoh pencarian 1 anggota dari hasil baca file.
- Ringkasan statistik jumlah anggota per jenis di akhir program.
