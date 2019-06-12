package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import sample.Entity.IndonesaianFood;
import sample.Helper.DBHelper;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class JapanMenuController extends Controller implements Initializable {
    public MaterialDesignIconView sortHargaIndicatorJapan;
    @FXML
    private JFXListView<IndonesaianFood> listview;

    @FXML
    private StackPane japanStackpane;
    private ObservableList<IndonesaianFood> listofFood = FXCollections.observableArrayList();
    private IndonesaianFood food;
    Connection connection;
    boolean sortDSC = true;
    DBHelper dbHelper = new DBHelper();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sortHargaIndicatorJapan.setVisible(false);
        try {
            LoadData();
        } catch (SQLException e) {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/Layout/databaseErrorState.fxml"));
            } catch (IOException e1) {

            }
            japanStackpane.getChildren().add(root);
        } catch (ClassNotFoundException e) {

        }

    }

    public ObservableList<IndonesaianFood> readDB(Connection connection, int nav) throws SQLException {
        String query =null;
        switch (nav){
            case 1:
                query = "SELECT * FROM makanan_japan";
                break;
            case 2:
                query = "SELECT * FROM makanan_japan ORDER BY populer DESC";
                break;
            case 3:
                query = "SELECT * FROM makanan_japan ORDER BY harga_mkn_japan DESC";
                break;
            case 4:
                query = "SELECT * FROM makanan_japan ORDER BY harga_mkn_japan ASC ";
                break;
        }
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<IndonesaianFood> indonesaianFoods = FXCollections.observableArrayList();

        while (resultSet.next()) {
            IndonesaianFood indonesaianFood = new IndonesaianFood(
                    resultSet.getInt("id_mkn_japan"),
                    resultSet.getString("nama_mkn"),
                    resultSet.getString("deskripsi_mkn_japan"),
                    resultSet.getInt("Harga_mkn_japan"),
                    resultSet.getInt("foto_mkn_japan")
            );
            indonesaianFoods.add(indonesaianFood);
        }

        return indonesaianFoods;
    }

    private void LoadData() throws SQLException, ClassNotFoundException {
        connection = dbHelper.getConnection();
        listofFood = readDB(connection,1);
        listview.setCellFactory(param -> {
            return new Cell(listview,japanStackpane,2);
        });
        listview.setItems(listofFood);
    }

    public void sortPopuler(ActionEvent event) throws SQLException, ClassNotFoundException {
        connection = dbHelper.getConnection();
        listofFood = readDB(connection,2);
        listview.setCellFactory(param -> {
            return new Cell(listview,japanStackpane,2);
        });
        listview.setItems(listofFood);
    }

    public void sortHarga(ActionEvent event) throws SQLException {
        sortHargaIndicatorJapan.setVisible(true);
        if (sortDSC){
            sortHargaIndicatorJapan.setGlyphName("MENU_DOWN");
            listofFood = readDB(connection,3);
            listview.setCellFactory(param -> {
                return new Cell(listview,japanStackpane,2);
            });
            listview.setItems(listofFood);
            sortDSC=false;
        }else{
            sortHargaIndicatorJapan.setGlyphName("MENU_UP");
            listofFood = readDB(connection,4);
            listview.setCellFactory(param -> {
                return new Cell(listview,japanStackpane,2);
            });
            listview.setItems(listofFood);
            sortDSC=true;
        }
    }
}
