package ResourceMonitor.Controllers;

import ResourceMonitor.Models.TableViewModel;
import ResourceMonitor.Utilities.DBUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.sql.rowset.CachedRowSet;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TableViewController implements Initializable {

    @FXML
    private TableView usageTable;

    @FXML
    private TableColumn dateColumn;

    @FXML
    private TableColumn ramColumn;

    @FXML
    private TableColumn cpuColumn;

    @FXML
    private TableColumn hddColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("logDate"));
        ramColumn.setCellValueFactory(new PropertyValueFactory<>("cpuUsage"));
        cpuColumn.setCellValueFactory(new PropertyValueFactory<>("ramUsage"));
        hddColumn.setCellValueFactory(new PropertyValueFactory<>("hddUsage"));


        usageTable.setItems(fetchAllAverageUsage());
    }
    @FXML
    private void switchToGraphView() throws IOException {
        loadView("averageUsageView.fxml");
    }
    @FXML
    private void switchToRealTimeView() throws IOException {
        loadView("resourceView.fxml");
    }
    @FXML
    private void loadView(String viewName) throws IOException {
        Stage window = (Stage) usageTable.getScene().getWindow(); // we need a reference to the window, to set the scene later
        // https://stackoverflow.com/questions/20507591/javafx-location-is-required-even-though-it-is-in-the-same-package
        // For some reason the views package couldn't be found without new File
        Parent tableView = FXMLLoader.load(new File("src/ResourceMonitor/Views/" + viewName).toURI().toURL());

        Scene scene = new Scene(tableView);
        window.setScene(scene);
        window.show();
    }

    public static ObservableList fetchAllAverageUsage(){
        CachedRowSet results = DBUtility.fetch("SELECT logdate, AVG(cpuusage) as CPU, AVG(hddspace) as HDD, AVG(ramusage) as RAM from resourcehistory group by logdate;");
        ObservableList data = FXCollections.observableArrayList();

        try{
            while(results.next()){
                LocalDate logDate = results.getDate("logdate").toLocalDate();
                double cpuUsage = results.getDouble("CPU");
                double ramUsage = results.getDouble("HDD");
                double hddUsage = results.getDouble("RAM");

                data.add(new TableViewModel(logDate, (int) cpuUsage, (int) ramUsage, (int) hddUsage));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return data;
    }
}
