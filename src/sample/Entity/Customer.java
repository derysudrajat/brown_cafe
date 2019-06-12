package sample.Entity;

public class Customer {
    private int id;
    private String nama;
    private int totalOrder;
    private int totalPay;
    private int menuNav;

    public Customer(int id, String nama, int totalOrder, int totalPay, int menuNav) {
        this.id = id;
        this.nama = nama;
        this.totalOrder = totalOrder;
        this.totalPay = totalPay;
        this.menuNav = menuNav;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(int totalOrder) {
        this.totalOrder = totalOrder;
    }

    public int getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(int totalPay) {
        this.totalPay = totalPay;
    }

    public int getMenuNav() {
        return menuNav;
    }

    public void setMenuNav(int menuNav) {
        this.menuNav = menuNav;
    }
}
