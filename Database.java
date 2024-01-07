import java.sql.*;
import java.util.Scanner;

public class Database {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/imusic";
    static final String USER = "root";
    static final String PASS = "";

    static Connection conn;
    static Statement stmt;
    static ResultSet rs;

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            System.out.print("Masukkan Nomor Faktur: ");
            String nomorFaktur = scanner.nextLine();

            System.out.print("Masukkan Nama Pelanggan: ");
            String namaPelanggan = scanner.nextLine();

            System.out.print("Masukkan Nomor HP: ");
            String nomorHp = scanner.nextLine();

            System.out.print("Masukkan Alamat: ");
            String alamat = scanner.nextLine();

            String sql = "INSERT INTO tbpbo (no_faktur, nama, no_hp, alamat) VALUE ('%s', '%s', '%s', '%s' )";
            sql = String.format(sql, nomorFaktur, namaPelanggan, nomorHp, alamat);
            stmt.execute(sql);

            System.out.print("Masukkan Kode Barang: ");
            String kodeBarang = scanner.nextLine();

            System.out.print("Masukkan Nama Barang: ");
            String namaBarang = scanner.nextLine();

            System.out.print("Masukkan Harga Barang: ");
            Integer hargaBarang = Integer.parseInt(scanner.nextLine());
            if (hargaBarang > 1000000) {
                throw new IllegalArgumentException("Harga barang tidak boleh lebih dari 1000000");
            }

            System.out.print("Masukkan Jumlah Beli: ");
            Integer jumlahBeli = Integer.parseInt(scanner.nextLine());
            if (jumlahBeli > 100) {
                throw new IllegalArgumentException("Jumlah beli tidak boleh lebih dari 100");
            }

            System.out.print("Masukkan Metode Pembayaran (hanya untuk FakturOnline): ");
            String metodePembayaran = scanner.nextLine();

            while (!conn.isClosed()) {
                showMenu();
            }

            stmt.close();
            conn.close();

        } catch (NumberFormatException e) {
            System.out.println("Input harus berupa angka");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Kasir : ");
        }
        scanner.close();
    }

    static Scanner scanner = new Scanner(System.in);

    static void showMenu() {
        System.out.println("\n========= MENU UTAMA =========");
        System.out.println("1. Show Data");
        System.out.println("2. Edit Data");
        System.out.println("3. Delete Data");
        System.out.println("0. Keluar");
        System.out.println("");
        System.out.print("PILIHAN> ");

        try {
            Integer pilihan = Integer.parseInt(scanner.nextLine());

            switch (pilihan) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    showData();
                    break;
                case 2:
                    updateData();
                    break;
                case 3:
                    deleteData();
                    break;
                default:
                    System.out.println("Pilihan salah!");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void showData() {
        String sql = "SELECT * FROM tbpbo";

        try {
            rs = stmt.executeQuery(sql);

            System.out.println("+--------------------------------+");
            System.out.println("|    DATA PELANGGAN DI iMUSIC    |");
            System.out.println("+--------------------------------+");

            while (rs.next()) {
                String no_faktur = rs.getString("no_faktur");
                String nama = rs.getString("nama");
                String no_hp = rs.getString("no_hp");
                String alamat = rs.getString("alamat");

                System.out.println(String.format("%s. %s -- %s -- (%s)", no_faktur, nama, alamat, no_hp));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void updateData() {
        try {

            System.out.print("Faktur yang mau diedit : ");
            String no_faktur = scanner.nextLine();
            System.out.print("Nama Pelanggan : ");
            String nama = scanner.nextLine().trim();
            System.out.print("Nomor HP : ");
            String no_hp = scanner.nextLine().trim();
            System.out.print("Alamat : ");
            String alamat = scanner.nextLine().trim();

            String sql = "UPDATE pelanggan SET nama='%s', no_hp='%s', alamat='%s' WHERE no_faktur='%s'";
            sql = String.format(sql, nama, no_hp, alamat, no_faktur);

            stmt.execute(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void deleteData() {
        try {

            // ambil input dari user
            System.out.print("Faktur yang mau dihapus : ");
            String no_faktur = (scanner.nextLine());

            // buat query hapus
            String sql = String.format("DELETE FROM tbpbo WHERE no_faktur='%s'", no_faktur);

            // hapus data
            stmt.execute(sql);

            System.out.println("Data telah terhapus...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}