package sample.Entity;

public class IndonesaianFood {
    private int id;
    private String nama;
    private String desc;
    private int harga;
    private int order;

    public IndonesaianFood(int id,String nama, String desc, int harga, int order) {
        this.id = id;
        this.nama = nama;
        this.desc = desc;
        this.harga = harga;
        this.order = order;
    }

    public IndonesaianFood(String nama, String desc, int harga, int order) {
        this.nama = nama;
        this.desc = desc;
        this.harga = harga;
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
