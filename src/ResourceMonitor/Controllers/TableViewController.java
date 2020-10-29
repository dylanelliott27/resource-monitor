package ResourceMonitor.Controllers;

import ResourceMonitor.Models.TableViewModel;
import ResourceMonitor.Utilities.UsageRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TableViewController implements Initializable {

    private TableViewModel model = new TableViewModel();

    @FXML
    private Button changeScene;

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
        System.out.println("second scene");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("logDate"));
        ramColumn.setCellValueFactory(new PropertyValueFactory<>("cpuUsage"));
        cpuColumn.setCellValueFactory(new PropertyValueFactory<>("ramUsage"));
        hddColumn.setCellValueFactory(new PropertyValueFactory<>("hddUsage"));


        usageTable.setItems(model.fetchAllAverageUsage());
    }
    @FXML
    private void switchScenes(ActionEvent event) throws Exception{
        Stage window = (Stage) changeScene.getScene().getWindow();
        // https://stackoverflow.com/questions/20507591/javafx-location-is-required-even-though-it-is-in-the-same-package
        Parent tableView = FXMLLoader.load(new File("src/ResourceMonitor/Views/averageUsageView.fxml").toURI().toURL());

        Scene scene = new Scene(tableView);
        window.setScene(scene);
        window.show();
    }
}
