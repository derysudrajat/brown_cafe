package sample.Entity;

public class OrderList {
    int idOrder;
    String namaOrder;
    int qtyOrder;
    int hargaOrder;
    int fotoOder;

    public OrderList(int idOrder, String namaOrder, int qtyOrder, int hargaOrder, int fotoOder) {
        this.idOrder = idOrder;
        this.namaOrder = namaOrder;
        this.qtyOrder = qtyOrder;
        this.hargaOrder = hargaOrder;
        this.fotoOder = fotoOder;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public String getNamaOrder() {
        return namaOrder;
    }

    public void setNamaOrder(String namaOrder) {
        this.namaOrder = namaOrder;
    }

    public int getQtyOrder() {
        return qtyOrder;
    }

    public void setQtyOrder(int qtyOrder) {
        this.qtyOrder = qtyOrder;
    }

    public int getHargaOrder() {
        return hargaOrder;
    }

    public void setHargaOrder(int hargaOrder) {
        this.hargaOrder = hargaOrder;
    }

    public int getFotoOder() {
        return fotoOder;
    }

    public void setFotoOder(int fotoOder) {
        this.fotoOder = fotoOder;
    }
}
