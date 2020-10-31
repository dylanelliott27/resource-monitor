package ResourceMonitor.Controllers;

import ResourceMonitor.Models.ResourceModel;
import ResourceMonitor.Utilities.CommandUtility;
import ResourceMonitor.Utilities.DBUtility;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ResourceController implements Initializable {
    private ResourceModel resourceValues = new ResourceModel(0, 0, 0);
    private Timeline updateUI = null;
    @FXML
    private Text cpuValue;
    @FXML
    private Text ramValue;
    @FXML
    private Text hddValue;

    /**
     * Sets up a javafx timeline instance that runs every second to call the method for retrieving OS resource usage levels
     * @param url = javafx
     * @param resourceBundle = javafx
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Making use of the JavaFX Timeline so I can schedule the OS query for resource values every second
        updateUI = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
            getValues(resourceValues);
        }));

        updateUI.setCycleCount(Timeline.INDEFINITE);
        updateUI.play();
    }
    /**
     * Changes scenes to the tableView scene
     * @throws Exception = file not found/error
     */
    @FXML
    public void switchToTableView() throws IOException {
        if(updateUI != null){ // stop the Timeline
            updateUI.stop();
        }
        loadView("tableView.fxml");
    }
    /**
     * Changes scenes to the barchart/graph scene
     * @throws Exception = file not found/error
     */
    public void switchToGraphView() throws IOException {
        if(updateUI != null){ // stop the Timeline
            updateUI.stop();
        }
        loadView("averageUsageView.fxml");
    }
    /**
     * Accepts the filename of the view you would like to load, then changes the scene to it using a reference to the window
     * created from any element on the current scene
     * @param viewName = The filename of the view located in the Views folder
     * @throws IOException = if file not found
     */
    private void loadView(String viewName) throws IOException {
        Stage window = (Stage) hddValue.getScene().getWindow(); // we need a reference to the window, to set the scene later
        // https://stackoverflow.com/questions/20507591/javafx-location-is-required-even-though-it-is-in-the-same-package
        // For some reason the views package couldn't be found without new File
        Parent tableView = FXMLLoader.load(new File("src/ResourceMonitor/Views/" + viewName).toURI().toURL());

        Scene scene = new Scene(tableView);
        window.setScene(scene);
        window.show();
    }

    /**
     * Grabs the current values of the instance (the values that would be presently displayed on screen) and inserts them to the database.
     */

    public void logToDB(){
        int cpu = resourceValues.getCpuValue();
        int ram = resourceValues.getRamValue();
        int hdd = resourceValues.getHddValue();
        DBUtility.update("INSERT into resourcehistory (logdate, cpuusage, hddspace, ramusage) VALUES ('" + LocalDate.now().toString() + "' ," + cpu + "," + ram + "," + hdd + ");");
    }

    /**
     * Uses the command utility class I created to retrieve a hashmap containg key value pairs of CPU, HDD and RAM usage for the OS
     * Majority of the method is done in another thread, as one of the OS commands takes a second or two, and blocks the UI thread.
     * The UI is then updated in the UI thread using platform.runlater with the values we get from the OS query
     * @param resourceInstance = The instance used to display values on UI
     */
    public void getValues(ResourceModel resourceInstance){
        CommandUtility command = new CommandUtility();
        // One of the commands to query the OS for CPU percentage takes like 2 seconds...
        // Because of that, it blocks the javaFX UI.. so I have to use a separate thread
        // then update the UI in Platform.runLater(), which executes on the application(javafx) thread
        new Thread(() -> {
            HashMap<String, Number> resourceValues = command.getAllValues();
            resourceInstance.setCpuValue((Integer) resourceValues.get("CPU"));
            resourceInstance.setRamValue((Integer) resourceValues.get("Memory"));
            resourceInstance.setHddValue((Integer) resourceValues.get("HDD"));
            Platform.runLater(() -> {
                cpuValue.setText(resourceInstance.getCpuValue() + "%");
                ramValue.setText(resourceInstance.getRamValue() + "%");
                hddValue.setText(resourceInstance.getHddValue() + "%");
            });
            return; // Close thread
        }).start();
    }
}
