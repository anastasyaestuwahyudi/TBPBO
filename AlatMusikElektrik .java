class AlatMusikElektrik implements AlatMusik {
    private String nama;
    private String merk;
    private int harga;

    public AlatMusikElektrik(String nama, int harga, String merk) {
        this.nama = nama;
        this.harga = harga;
        this.merk = merk;
    }

    // Konstruktor dan metode lainnya...

    @Override
    public int getHarga() {
        return harga;
    }

    @Override
    public String getMerk() {
        return merk;
    }

    @Override
    public String getNama() {
        return nama;
    }

}
