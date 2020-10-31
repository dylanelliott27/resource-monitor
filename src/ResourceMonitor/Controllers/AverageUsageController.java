package ResourceMonitor.Controllers;

import ResourceMonitor.Models.AverageUsageModel;
import ResourceMonitor.Utilities.DBUtility;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import javax.sql.rowset.CachedRowSet;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AverageUsageController implements Initializable {

    XYChart.Series usageSeries = new XYChart.Series();
    @FXML
    private BarChart averageUsageChart;
    @FXML
    private CategoryAxis resourceTypeAxis;
    @FXML
    private NumberAxis usageAxis;
    @FXML
    private Button changeButton;
    @FXML
    private ChoiceBox<AverageUsageModel> dateDropdown;

    @FXML
    private void switchToTableView() throws Exception{
        loadView("tableView.fxml");
    }
    @FXML
    private void switchToRealTimeView() throws Exception{
        loadView("resourceView.fxml");
    }

    private void loadView(String viewName) throws IOException {
        // Eventually move this method to a utility class as it is re-used in all three controllers
        Stage window = (Stage) changeButton.getScene().getWindow(); // we need reference to window, to change scene with
        // https://stackoverflow.com/questions/20507591/javafx-location-is-required-even-though-it-is-in-the-same-package
        Parent tableView = FXMLLoader.load(new File("src/ResourceMonitor/Views/" + viewName).toURI().toURL());

        Scene scene = new Scene(tableView);
        window.setScene(scene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeUI();
        // Set listener for when a new value in the dropdown is chosen, then update the bargraph with the corresponding model instance values
        dateDropdown.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            updateBarGraph(newValue);
        });

    }

    public void initializeUI(){
        // TODO:: MOVE DB METHODS TO CONTROLLER
        ArrayList<AverageUsageModel> graphValues = fetchAverageValues();

        graphValues.forEach((valueObj) -> {
            // Add each Model instance to the dropdown, where it's toString method will be represented
            dateDropdown.getItems().add(valueObj);

        });

        // Right now the DB query returns the furthest date away first, so we select the last item inputted into the dropdown list
        // By selecting it here, we can display the values for that date on application launch.
        dateDropdown.getSelectionModel().selectLast();
        AverageUsageModel selectedDate = dateDropdown.getSelectionModel().getSelectedItem();

        int cpuUsage = selectedDate.getCpuUsage();
        int ramUsage = selectedDate.getRamUsage();
        int hddUsage = selectedDate.getHddUsage();

        resourceTypeAxis.setLabel("Resource Type");
        usageAxis.setLabel("Average percentage/load amount");

        usageSeries.setName("Average Resource load %");
        usageSeries.getData().add(new XYChart.Data("CPU", cpuUsage));
        usageSeries.getData().add(new XYChart.Data("RAM", ramUsage));
        usageSeries.getData().add(new XYChart.Data("HDD", hddUsage));

        averageUsageChart.getData().addAll(usageSeries);

    }

    public void updateBarGraph(AverageUsageModel newDate){
        // Set the bar graph values to those of the selected instance in the dropdown
        usageSeries.getData().clear();
        usageSeries.getData().add(new XYChart.Data("CPU", newDate.getCpuUsage()));
        usageSeries.getData().add(new XYChart.Data("RAM", newDate.getRamUsage()));
        usageSeries.getData().add(new XYChart.Data("HDD", newDate.getHddUsage()));
    }

    public static ArrayList fetchAverageValues(){
        CachedRowSet result = DBUtility.fetch("SELECT logdate, AVG(cpuusage) as CPU, AVG(hddspace) as HDD, AVG(ramusage) as RAM from resourcehistory group by logdate;");
        ArrayList values = new ArrayList();

        try{
            while (result.next()) {
                LocalDate logDate = result.getDate("logdate").toLocalDate();
                double cpuUsage = result.getDouble("CPU");
                double ramUsage = result.getDouble("RAM");
                double hddUsage = result.getDouble("HDD");
                // Not sure if I should be showing double values or not, so cast to int for now
                values.add(new AverageUsageModel(logDate, (int) cpuUsage, (int) ramUsage, (int) hddUsage));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return values;
    }

}
