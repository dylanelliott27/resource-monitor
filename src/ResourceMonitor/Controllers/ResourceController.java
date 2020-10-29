package ResourceMonitor.Controllers;

import ResourceMonitor.Models.ResourceModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ResourceController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ResourceModel resourceValues = new ResourceModel(0, 0, 0);

        Timeline updateUI = new Timeline(new KeyFrame(Duration.seconds(5), actionEvent -> System.out.println("test")));

        updateUI.setCycleCount(Timeline.INDEFINITE);
        updateUI.play();
    }

    public void getValues(){

    }
}
