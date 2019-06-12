package sample.Helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.Entity.IndonesaianFood;
import sample.Entity.OrderList;

import java.sql.*;

public class DBHelper {
    private AnchorPane nPane;
    private Text qtyNotif;

    public AnchorPane getnPane() {
        return nPane;
    }

    public void setnPane(AnchorPane nPane) {
        this.nPane = nPane;
    }

    public Text getQtyNotif() {
        return qtyNotif;
    }

    public void setQtyNotif(Text qtyNotif) {
        this.qtyNotif = qtyNotif;
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Config config = new Config();
        String stringConnection;
        Connection connection=null;
            stringConnection = "jdbc:mysql://" + config.getDbHost() + ":"
                    + config.getDbPort() + "/" + config.getDbName() + "?autoReconnect=true&useSSL=false";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection =DriverManager.getConnection(stringConnection, config.getDbUser(), config.getDbPass());

        return connection;

    }
    public void updateNavPage(Connection connection, int nav) throws SQLException {
        String query2 = "UPDATE navigator SET nav_page =?;";
        PreparedStatement preparedStatement = connection.prepareStatement(query2);
        preparedStatement.setInt(1, nav);
        preparedStatement.executeUpdate();
    }
    public void updateNavMenu(Connection connection, int nav) throws SQLException {
        String query2 = "UPDATE navigator SET nav_manu =?;";
        PreparedStatement preparedStatement = connection.prepareStatement(query2);
        preparedStatement.setInt(1, nav);
        preparedStatement.executeUpdate();

    }
    public int getNavPage(Connection connection){
        String query1 = "SELECT * FROM navigator;";
        ResultSet resultSet;
        int nav=0;
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
            resultSet = preparedStatement1.executeQuery();
            while (resultSet.next()) {
                nav = resultSet.getInt("nav_page");
            }

        }catch (Exception e){
            System.out.println("Exception in getNavPage: "+e);

        }
        return nav;

    }
    public ObservableList<IndonesaianFood> readIndoDB(Connection connection, int nav) throws SQLException {
        String query = null;
        switch (nav){
            case 1:
                query = "SELECT * FROM makanan_indo";
                break;
            case 2:
                query = "SELECT * FROM makanan_indo ORDER BY populer DESC";
                break;
            case 3:
                query = "SELECT * FROM makanan_indo ORDER BY harga_mkn_indo DESC";
                break;
            case 4:
                query = "SELECT * FROM makanan_indo ORDER BY harga_mkn_indo ASC ";
                break;
        }
        ResultSet resultSet=null;
        ObservableList<IndonesaianFood> indonesaianFoods = null;

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();
                indonesaianFoods = FXCollections.observableArrayList();
                while (resultSet.next()) {
                    IndonesaianFood indonesaianFood = new IndonesaianFood(
                            resultSet.getInt("id_mknIndo"),
                            resultSet.getString("nama_makanan"),
                            resultSet.getString("deskripsi_mkn_indo"),
                            resultSet.getInt("Harga_mkn_indo"),
                            resultSet.getInt("foto_mkn_indo")
                    );
                    indonesaianFoods.add(indonesaianFood);
                }
        return indonesaianFoods;
    }

    public ObservableList<OrderList> readOrderListDB(Connection connection) {

        String query = "SELECT * FROM order_list";
        ResultSet resultSet =null;
        ObservableList<OrderList> orderLists =null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            orderLists = FXCollections.observableArrayList();

            while (resultSet.next()) {
                OrderList orderList =  new OrderList(
                        resultSet.getInt("id_menu"),
                        resultSet.getString("nama_menu"),
                        resultSet.getInt("qty_menu"),
                        resultSet.getInt("harga_menu"),
                        resultSet.getInt("foto_menu")
                );
                orderLists.add(orderList);
            }
        }catch (Exception e){

        }
        return orderLists;
    }

    public void addOrderList(Connection connection, int id, String name, int qty, int harga, int foto) {
        try {
            String query = "insert into order_list values (?,?,?,?,?);";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, qty);
            preparedStatement.setInt(4, harga);
            preparedStatement.setInt(5, foto);
            preparedStatement.executeUpdate();
        }catch (Exception e){

        }

    }
    public void updateCustomerName(Connection connection,String newName, int id){
        String query = " update customer set nama_customer = ? where id_customer =?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,newName);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println("Exception in updateQtyOrder : "+e);
        }
    }

    public void LoadNotif(AnchorPane notificatoinPane, Text qtyText) {
        try {
            nPane = notificatoinPane;
            qtyNotif=qtyText;
            setnPane(notificatoinPane);
            setQtyNotif(qtyText);
            theNotif();
        }catch (Exception e){

        }

    }
    public void theNotif() throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        int totalOder = sizeOfOrder(connection);
        if (totalOder==0){
            nPane.setVisible(false);
            qtyNotif.setVisible(false);
        }else{
            nPane.setVisible(true);
            qtyNotif.setVisible(true);
            qtyNotif.setText(String.valueOf(totalOder));
        }
    }

    public int getIdCustomer(Connection connection){
        String query1 = "SELECT * FROM customer ORDER BY id_customer DESC LIMIT 1;";
        ResultSet resultSet;
        int id_past=0;
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
            resultSet = preparedStatement1.executeQuery();
            while (resultSet.next()) {
                id_past = resultSet.getInt("id_customer");
            }

        }catch (Exception e){
            System.out.println("Exception in getIdCustomer: "+e);
            id_past=0;
        }
        return id_past;
    }
    public String getNamaCustomer(Connection connection){
        String query1 = "SELECT * FROM customer ORDER BY id_customer DESC LIMIT 1;";
        ResultSet resultSet;
        String namaCustomer = null;
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
            resultSet = preparedStatement1.executeQuery();
            while (resultSet.next()) {
                namaCustomer = resultSet.getString("nama_customer");
            }

        }catch (Exception e){
            System.out.println("Exception in getIdCustomer: "+e);
        }
        return namaCustomer;
    }
    public int getSumofOder(Connection connection){
        String query = "SELECT SUM(qty_menu*harga_menu) AS Total FROM order_list;";
        ResultSet resultSet;
        int sum=0;
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement(query);
            resultSet = preparedStatement1.executeQuery();
            while (resultSet.next()) {
                sum = resultSet.getInt("Total");
            }

        }catch (Exception e){
            System.out.println("Exception in getSumofOder: "+e);
        }
        return sum;
    }
    public int getTotalOrder(Connection connection){
        String query = "SELECT SUM(qty_menu) AS total_order FROM order_list;";
        ResultSet resultSet;
        int totalOrder=0;
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement(query);
            resultSet = preparedStatement1.executeQuery();
            while (resultSet.next()) {
                totalOrder = resultSet.getInt("total_order");
            }

        }catch (Exception e){
            System.out.println("Exception in getTotalOrder: "+e);
        }
        return totalOrder;
    }
    public int getIdOrderList(Connection connection, int id){
        String query = "SELECT COUNT(*) FROM order_list WHERE id_menu = ?;";
        ResultSet resultSet;
        int id_past = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id_past = resultSet.getInt("COUNT(*)");
                System.out.println("COUNT: "+id_past);
            }
        }catch (Exception e){
            System.out.println("Exception in getIdOrderList : "+e);
        }
        return id_past;
    }
    public void updateQtyOrder(Connection connection, int idMenu, int newQty){
        String query = " UPDATE order_list SET qty_menu=? WHERE id_menu=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,newQty);
            preparedStatement.setInt(2,idMenu);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println("Exception in updateQtyOrder : "+e);
        }
    }
    public void deleteOrderList(Connection connection){
        String query = "delete from order_list;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println("Exception in deleteOrderList : "+e);
        }
    }
    public void InsertCustomer(Connection connection, int totalOrder, int totalPay, int idCustomer){
        String query = "UPDATE customer SET total_order=?, total_pay=? WHERE id_customer=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,totalOrder);
            preparedStatement.setInt(2,totalPay);
            preparedStatement.setInt(3,idCustomer);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println("Exception in updateQtyOrder : "+e);
        }
    }
    public void deleteOnCart(Connection connection, int id){
        String query = "Delete FROM order_list WHERE id_menu = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println("Exception in updateQtyOrder : "+e);
        }
    }
    public void deleteAllOnCart(Connection connection){
        String query = "Delete FROM order_list";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println("Exception in updateQtyOrder : "+e);
        }
    }
    public int sizeOfOrder(Connection connection){
        String query = " SELECT COUNT(*) FROM order_list;";
        ResultSet resultSet;
        int sizeofOrder=0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sizeofOrder = resultSet.getInt("COUNT(*)");
            }
        }catch (Exception e){
            System.out.println("Exception in sizeOfOrder : "+e);
        }
        return sizeofOrder;
    }

}
