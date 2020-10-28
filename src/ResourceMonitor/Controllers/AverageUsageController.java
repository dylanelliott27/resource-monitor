package ResourceMonitor.Controllers;


import ResourceMonitor.Models.AverageUsageModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
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

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AverageUsageController implements Initializable {
    private AverageUsageModel model = new AverageUsageModel();
    XYChart.Series usageSeries = new XYChart.Series();
    @FXML
    private BarChart averageUsageChart;
    @FXML
    private CategoryAxis resourceTypeAxis;
    @FXML
    private Button changeButton;
    @FXML
    private ChoiceBox dateDropdown;
    @FXML
    private NumberAxis usageAxis;

    @FXML
    private void switchScenes(ActionEvent event) throws Exception{
        Stage window = (Stage) changeButton.getScene().getWindow();
        // https://stackoverflow.com/questions/20507591/javafx-location-is-required-even-though-it-is-in-the-same-package
        Parent tableView = FXMLLoader.load(new File("src/ResourceMonitor/Views/tableView.fxml").toURI().toURL());

        Scene scene = new Scene(tableView);
        window.setScene(scene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("first scene");
        initializeUI();

        dateDropdown.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            updateBarGraph((LocalDate) newValue);
        });

    }

    public void initializeUI(){
        HashMap<LocalDate, ArrayList> graphValues = model.fetchAverageValues();


        graphValues.forEach((date, values) -> {
            dateDropdown.getItems().add(date);

        });
        averageUsageChart.setLegendVisible(false);
        dateDropdown.getSelectionModel().selectFirst();
        LocalDate selectedDate = (LocalDate) dateDropdown.getSelectionModel().getSelectedItem();

        int cpuUsage = (int) graphValues.get(selectedDate).get(0);
        int ramUsage = (int) graphValues.get(selectedDate).get(1);
        int hddUsage = (int) graphValues.get(selectedDate).get(2);

        usageSeries.getData().add(new XYChart.Data("CPU", cpuUsage));
        usageSeries.getData().add(new XYChart.Data("RAM", ramUsage));
        usageSeries.getData().add(new XYChart.Data("HDD", hddUsage));

        averageUsageChart.getData().addAll(usageSeries);

    }

    public void updateBarGraph(LocalDate newDate){
        ArrayList<Number> values = model.getUsageForDate(newDate);
        usageSeries.getData().clear();
        usageSeries.getData().add(new XYChart.Data("CPU", values.get(0)));
        usageSeries.getData().add(new XYChart.Data("RAM", values.get(1)));
        usageSeries.getData().add(new XYChart.Data("HDD", values.get(2)));
    }
}
