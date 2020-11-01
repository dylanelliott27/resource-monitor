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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.sql.rowset.CachedRowSet;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TableViewController implements Initializable {

    @FXML
    private TableView<TableViewModel> usageTable;

    @FXML
    private TableColumn<String, LocalDate> dateColumn;

    @FXML
    private TableColumn<String, Number> ramColumn;

    @FXML
    private TableColumn<String, Number> cpuColumn;

    @FXML
    private TableColumn<String, Number> hddColumn;

    /**
     * Sets up the data sources for each column, which is an observablelist of TableViewModel instances generated
     * from the fetchAllAverageUsage method
     * @param url = javafx
     * @param resourceBundle = javafx
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("logDate"));
        ramColumn.setCellValueFactory(new PropertyValueFactory<>("cpuUsage"));
        cpuColumn.setCellValueFactory(new PropertyValueFactory<>("ramUsage"));
        hddColumn.setCellValueFactory(new PropertyValueFactory<>("hddUsage"));


        usageTable.setItems(fetchAllAverageUsage());
    }

    /**
     * Changes scenes to the barchart/graph scene
     * @throws Exception = file not found/error
     */
    @FXML
    private void switchToGraphView() throws IOException {
        loadView("averageUsageView.fxml");
    }

    /**
     * Changes scenes to the realtimeview scene
     * @throws Exception = file not found/error
     */
    @FXML
    private void switchToRealTimeView() throws IOException {
        loadView("resourceView.fxml");
    }

    /**
     * Accepts the filename of the view you would like to load, then changes the scene to it using a reference to the window
     * created from any element on the current scene
     * @param viewName = The filename of the view located in the Views folder
     * @throws IOException = if file not found
     */
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

    /**
     * Queries the DB for the average values of cpu, ram, and hdd for each date. Each row is parsed into a valid tableViewModel instance
     * @return = Observable list of all tableviewmodel instances for each date
     */
    public static ObservableList<TableViewModel> fetchAllAverageUsage(){
        CachedRowSet results = DBUtility.fetch("SELECT logdate, AVG(cpuusage) as CPU, AVG(hddspace) as HDD, AVG(ramusage) as RAM from resourcehistory group by logdate;");
        ObservableList<TableViewModel> data = FXCollections.observableArrayList();

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
