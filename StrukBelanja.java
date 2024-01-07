class StrukBelanja {
    private String nomorFaktur;
    private String kodeBarang;
    private String namaPelanggan;
    private String alamatPelanggan;
    private String noHpPelanggan;
    private String namaAlatMusik;
    private String merkAlatMusik;
    private int jumlah;
    private int harga;
    private double diskon;
    private double totalHarga;

    // Constructor
    public StrukBelanja(String nomorFaktur, String kodeBarang, String namaPelanggan, String alamatPelanggan,
            String noHpPelanggan, String namaAlatMusik, String merkAlatMusik,
            int jumlah, int harga, double diskon, double totalHarga) {
        this.nomorFaktur = nomorFaktur;
        this.kodeBarang = kodeBarang;
        this.namaPelanggan = namaPelanggan;
        this.alamatPelanggan = alamatPelanggan;
        this.noHpPelanggan = noHpPelanggan;
        this.namaAlatMusik = namaAlatMusik;
        this.merkAlatMusik = merkAlatMusik;
        this.jumlah = jumlah;
        this.harga = harga;
        this.diskon = diskon;
        this.totalHarga = totalHarga;
    }

    // Getter methods
    public String getNomorFaktur() {
        return nomorFaktur;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public String getNamaAlatMusik() {
        return namaAlatMusik;
    }

    public int getJumlah() {
        return jumlah;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    @Override
    public String toString() {
        return "===== Struk Belanja =====" +
                "\nNomor Faktur: " + nomorFaktur +
                "\nKode Barang: " + kodeBarang +
                "\nDetail Pelanggan" +
                "\nNama Pembeli: " + namaPelanggan +
                "\nAlamat: " + alamatPelanggan +
                "\nNo Hp: " + noHpPelanggan +
                "\nDetail Belanja" +
                "\nAlat Musik: " + namaAlatMusik +
                "\nMerk: " + merkAlatMusik +
                "\nJumlah: " + jumlah +
                "\nHarga Satuan : Rp " + harga +
                "\nHarga Keseluruhan : \nRp " + harga + " x " + jumlah + " : Rp " + totalHarga +
                "\nDiskon: " + (diskon * 100) + "%" +
                "\nTotal Harga setelah di diskon: Rp " + (totalHarga - (totalHarga * diskon));
    }
}
