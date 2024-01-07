import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.*;

class App {

    private static Scanner scanner = new Scanner(System.in);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private static List<StrukBelanja> strukBelanjaList = new ArrayList<>();
    private static int totalBelanjaSemuaTransaksi;

    public static void main(String[] args) {
        boolean isLanjutBelanja = true;

        while (isLanjutBelanja) {
            Date now = new Date();
            String formattedDate = dateFormat.format(now);
            String formattedTime = timeFormat.format(now);

            System.out.println("Selamat datang di iMusic!");

            System.out.print("Nama Pembeli: ");
            String namaPembeli = scanner.nextLine();
            System.out.print("Alamat: ");
            String alamat = scanner.nextLine();
            System.out.print("No Hp: ");
            String noHp = scanner.nextLine();

            int totalHargaTransaksi = 0;

            boolean isLanjutBeli = true;

            while (isLanjutBeli) {
                tampilkanMenuAlatMusik();
                // Membaca pilihan alat musik
                System.out.print("Pilihan Anda: ");
                int pilihan;
                try {
                    pilihan = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Masukkan pilihan dengan angka.");
                    scanner.nextLine(); // Membersihkan buffer
                    continue;
                }

                // Membersihkan buffer setelah membaca pilihan alat musik
                scanner.nextLine();

                if (pilihan == 0) {
                    isLanjutBeli = false;
                    continue;
                }

                AlatMusikElektrik alatMusik = null;

                try {
                    alatMusik = pilihAlatMusik(pilihan);
                } catch (Exception e) {
                    System.out.println("Terjadi kesalahan: " + e.getMessage());
                    return;
                }

                if (alatMusik == null) {
                    System.out.println("Pilihan tidak valid.");
                    return;
                }

                System.out.println(
                        "Harga " + alatMusik.getMerk() + " " + alatMusik.getNama() + ": Rp " + alatMusik.getHarga());

                System.out.print("Masukkan jumlah " + alatMusik.getNama() + " yang ingin Anda beli: ");
                int jumlah;
                try {
                    jumlah = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Masukkan jumlah dengan angka.");
                    return;
                }

                int totalHarga = alatMusik.getHarga() * jumlah;
                totalHargaTransaksi += totalHarga;

                double diskon = (jumlah >= 5) ? 0.1 : 0.0;
                double hargaSetelahDiskon = totalHarga - (totalHarga * diskon);

                StrukBelanja strukBelanja = new StrukBelanja(
                        generateNomorFaktur(), generateKodeBarang(),
                        namaPembeli, alamat, noHp,
                        alatMusik.getNama(), alatMusik.getMerk(),
                        jumlah, alatMusik.getHarga(), diskon, hargaSetelahDiskon);

                // Menambahkan struk belanja ke dalam ArrayList
                strukBelanjaList.add(strukBelanja);

                // Menampilkan detail belanja pada struk
                System.out.println("-------------------------");
                System.out.println("Alat Musik: " + alatMusik.getNama());
                System.out.println("Merk: " + alatMusik.getMerk());
                System.out.println("Jumlah: " + jumlah);
                System.out.println("Harga Satuan : Rp " + alatMusik.getHarga());
                System.out.println("Harga Keseluruhan : ");
                System.out.println("Rp " + alatMusik.getHarga() + " x " + jumlah + " : Rp " + totalHarga);
                System.out.println("Diskon: " + (diskon * 100) + "%");
                System.out.println("Total Harga setelah di diskon: Rp " + hargaSetelahDiskon);
                System.out.println("-------------------------");

                // Meminta input untuk melanjutkan belanja atau tidak
                System.out.print("Apakah ingin membeli yang lain (ya/tidak)? ");
                scanner.nextLine(); // Membersihkan buffer
                String lanjutBelanja = scanner.next().toLowerCase();
                if (lanjutBelanja.equals("tidak")) {
                    isLanjutBeli = false;
                }
            }

            // Menambahkan total belanja transaksi ini ke total belanja semua transaksi
            totalBelanjaSemuaTransaksi += totalHargaTransaksi;

            // Menampilkan struk belanja
            tampilkanStrukBelanja(namaPembeli, alamat, noHp, formattedDate, formattedTime, totalHargaTransaksi);

            // Meminta input untuk melanjutkan belanja atau tidak
            System.out.print("Apakah Anda ingin melanjutkan belanja? (ya/tidak): ");
            String lanjutBelanja = scanner.next().toLowerCase();
            if (lanjutBelanja.equals("tidak")) {
                System.out.println("Oke, terimakasih! Silakan datang kembali lain waktu.");
                isLanjutBelanja = false;
            } else if (lanjutBelanja.equals("ya")) {
                isLanjutBelanja = true;
            } else {
                System.out.println("Masukkan pilihan yang valid (ya/tidak).");
                isLanjutBelanja = false;
            }
        }
    }

    private static void tampilkanMenuAlatMusik() {
        System.out.println("Pilih alat musik yang ingin Anda beli:");
        System.out.println("1. Gitar");
        System.out.println("2. Drum");
        System.out.println("3. Keyboard");
        System.out.println("0. Selesai Belanja");
    }

    private static void tampilkanStrukBelanja(String namaPembeli, String alamat, String noHp,
            String formattedDate, String formattedTime, int totalHarga) {
        System.out.println("\n============== Struk Belanja =============");
        System.out.println("                   iMUSIC");
        System.out.println(" Jl. Tunggang Bandes Kandang Padati, No.48");
        System.out.println("              WA : 08117677976 ");
        System.out.println("==========================================");
        System.out.println("Tanggal: " + formattedDate);
        System.out.println("Jam: " + formattedTime);
        System.out.println("\nNomor Faktur: " + generateNomorFaktur());
        System.out.println("Kode Barang: " + generateKodeBarang());
        System.out.println("------------- DATA PELANGGAN ------------");
        System.out.println("Nama Pembeli: " + namaPembeli);
        System.out.println("Alamat: " + alamat);
        System.out.println("No Hp: " + noHp);
        System.out.println("-------------- DETAIL BELANJA -----------");
        double totalHargaKeseluruhan = 0.0;

        int counter = 1;
        for (StrukBelanja strukBelanja : strukBelanjaList) {
            System.out.println("- " + strukBelanja.getNamaAlatMusik());
            System.out.println("  Jumlah : " + strukBelanja.getJumlah());
            System.out.println("  Total Harga : Rp " + strukBelanja.getTotalHarga());
            totalHargaKeseluruhan += strukBelanja.getTotalHarga();
            counter++;
        }

        System.out.println("Total Harga Keseluruhan: Rp " + totalHargaKeseluruhan);
        System.out.println("=========================");
    }

    private static String generateNomorFaktur() {
        // Implementasi penghasilan nomor faktur dapat disesuaikan
        return "NF" + new Random().nextInt(1000);
    }

    private static String generateKodeBarang() {
        // Implementasi penghasilan kode barang dapat disesuaikan
        return "KB" + new Random().nextInt(100);
    }

    private static AlatMusikElektrik pilihAlatMusik(int pilihan) throws Exception {
        AlatMusikElektrik alatMusik = null;

        switch (pilihan) {
            case 1:
                alatMusik = pilihGitar();
                break;
            case 2:
                alatMusik = pilihDrum();
                break;
            case 3:
                alatMusik = pilihKeyboard();
                break;
            default:
                throw new Exception("Pilihan tidak valid.");
        }

        return alatMusik;
    }

    private static AlatMusikElektrik pilihGitar() {
        System.out.println("Pilih merk Gitar:");
        System.out.println("1. Fender");
        System.out.println("2. Gibson");
        System.out.print("Pilihan Anda          : ");
        int pilihan = scanner.nextInt();

        switch (pilihan) {
            case 1:
                return new AlatGitar("Fender");
            case 2:
                return new AlatGitar("Gibson");
            default:
                System.out.println("Pilihan tidak valid.");
                return null;
        }
    }

    private static AlatMusikElektrik pilihDrum() {
        System.out.println("Pilih merk Drum:");
        System.out.println("1. Yamaha");
        System.out.println("2. Pearl");
        System.out.print("Pilihan Anda          : ");
        int pilihan = scanner.nextInt();

        switch (pilihan) {
            case 1:
                return new AlatDrum("Yamaha");
            case 2:
                return new AlatDrum("Pearl");
            default:
                System.out.println("Pilihan tidak valid.");
                return null;
        }
    }

    private static AlatMusikElektrik pilihKeyboard() {
        System.out.println("Pilih merk Keyboard:");
        System.out.println("1. Yamaha");
        System.out.println("2. Casio");
        System.out.print("Pilihan Anda          : ");
        int pilihan = scanner.nextInt();

        switch (pilihan) {
            case 1:
                return new AlatKeyboard("Yamaha");
            case 2:
                return new AlatKeyboard("Casio");
            default:
                System.out.println("Pilihan tidak valid.");
                return null;
        }
    }
}
